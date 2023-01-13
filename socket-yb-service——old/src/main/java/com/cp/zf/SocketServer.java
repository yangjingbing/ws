package com.cp.zf;

import com.alibaba.fastjson.JSONObject;
import com.cp.zf.bean.Layer;
import com.cp.zf.bean.Voltage;
import com.cp.zf.util.ConvertUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Description TODO .</br>
 * <></>
 * @Author gu
 * @Date 2021/1/23 15:43
 * @Version 1.0.0
 **/
public class SocketServer {
    private static JdbcTemplate jdbcTemplate = null;
    private static int port = 3858;
    private static long lasttime = System.currentTimeMillis();


    public static void main(String[] args) {
        ApplicationContext factory = new FileSystemXmlApplicationContext(".//applicationContext.xml");

        jdbcTemplate = (JdbcTemplate) factory.getBean("jdbcTemplate");

        jdbcTemplate.setSkipUndeclaredResults(true);


        try {
            //创建一个socket服务端
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("服务启动成功。");
            while (true) {
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

                // 等待socket客户端的请求。accept方法在有连接请求时才会返回
                Socket socket = serverSocket.accept();

                // 获取socket输入流
                InputStream inputStream = socket.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                System.out.println("接受到客户端数据：" + bufferedReader.readLine());
               /* // 读取请求内容的缓冲区
                byte[] bytes = new byte[1024];
                int length = 0;
                StringBuilder sb = new StringBuilder();
                //获取客户端请求的内容
                while ((length = inputStream.read(bytes)) != -1) {
                    sb.append(new String(bytes, 0, length, "utf-8"));
                }
                System.out.println("客户端请求数据为：" + sb.toString());
                */

                // 获取socket输出流
                OutputStream outputStream = socket.getOutputStream();

                long lastt = lasttime;
                lasttime = System.currentTimeMillis();

                carInfo(outputStream, lastt);

                fastener(outputStream);


                //关闭资源
                close(socket, inputStream, outputStream, null);
            }


        } catch (IOException e) {
            System.err.println("socket监听失败！" + e);
        }
    }

    private static void fastener(OutputStream outputStream) {
        //区域
        String listSql = "SELECT id,mc,layer_id FROM t_qy_jbxx";
        List<Map<String, Object>> qyResult = jdbcTemplate.query(listSql, new RowMapper() {
            public Map mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                Map<String, Object> row = new LinkedHashMap();
                try {
                    row.put("id", Integer.valueOf(rs.getInt("id")));
                    row.put("mc", rs.getString("mc"));
                    row.put("layerId", Integer.valueOf(rs.getInt("layer_id")));
                    return row;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                return null;
            }
        });
        if(!CollectionUtils.isEmpty(qyResult)){
            List<String> cardIdList = new ArrayList<>();
            for (Iterator<Map<String, Object>> iterator = qyResult.iterator(); iterator.hasNext(); ) {
                cardIdList.add(iterator.next().get("layerId").toString());
            }
            //minx和miny
            //maxx和maxy
            listSql = "SELECT id, min_x,min_y,max_x,max_y FROM t_dm_layer where id in ('"+cardIdList.stream().collect(Collectors.joining("','"))+"')";
            List<Layer> xyResult = jdbcTemplate.query(listSql, new RowMapper() {
                public Layer mapRow(ResultSet rs, int rowNum)
                        throws SQLException {
                    Layer row = new Layer();
                    try {
                        row.setMinx(rs.getDouble("min_x"));
                        row.setMiny(rs.getDouble("min_y"));
                        row.setMaxx(rs.getDouble("max_x"));
                        row.setMaxy(rs.getDouble("max_y"));
                        row.setId(rs.getInt("id"));
                        return row;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    return null;
                }
            });
            Map<Integer,List<Map<String,Double>>> positionMap = new HashMap<>();
            xyResult.stream().forEach(info->{
                double[] arr = ConvertUtil.xy2lonlat(info.getMinx(), info.getMiny());

                List<Map<String,Double>> list = new ArrayList<>();

                Map<String,Double> xyMap = new HashMap<>();
                xyMap.put("Lng",arr[0]);
                xyMap.put("Lat",arr[1]);
                list.add(xyMap);

                Map<String,Double> xyMaxMap = new HashMap<>();
                arr = ConvertUtil.xy2lonlat(info.getMaxy(), info.getMaxy());
                xyMaxMap.put("Lng",arr[0]);
                xyMaxMap.put("Lat",arr[1]);
                list.add(xyMaxMap);


                positionMap.put(info.getId(),list);

            });
            Map<String, Object> sendResultMap = new HashMap<>();
            for (Iterator<Map<String, Object>> iterator = qyResult.iterator(); iterator.hasNext(); ) {
                Map<String,Object>  map = iterator.next();
                sendResultMap.put("Fastener", map.get("id"));  //卡口编号
                sendResultMap.put("FastenerName", map.get("mc"));  //卡扣名称
                sendResultMap.put("Position", positionMap.get("layerId"));  //卡扣位置
                sendResultMap.put("Fastener_Status", "1");  //卡扣车辆进出信息


                JSONObject jObject = new JSONObject();
                jObject.put("MsgType", "fastener");
                jObject.put("Content", sendResultMap);
                PrintWriter printWriter = new PrintWriter(outputStream);
                try {
                    printWriter.write(new String(jObject.toJSONString().getBytes(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                printWriter.flush();
                printWriter.close();
            }

        }
    }

    static Pattern r = Pattern.compile("\\d*");

    private static void carInfo(OutputStream outputStream ,long lastt) {

        Date date1 = new Date(lastt);

        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date1);
        rightNow.add(13, -20);
        Date date = rightNow.getTime();

        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

        Timestamp tt = Timestamp.valueOf(nowTime);

        String listSql = "SELECT carnum,COUNT(1) n,description,dt FROM t_car t WHERE dt>=? GROUP  BY t.description ";
        List<Map<String, Object>> result = jdbcTemplate.query(listSql, new Object[]{tt}, new RowMapper() {
            public Map mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                Map<String, Object> row = new LinkedHashMap();
                try {
                    row.put("carnum", rs.getString("carnum"));
                    if (!Objects.isNull(rs.getString("carnum"))) {
                        row.put("carColor", rs.getString("carnum").substring(0, 1));
                    }
                    row.put("description", rs.getString("description"));
                    row.put("n", Integer.valueOf(rs.getInt("n")));
                    long tt = rs.getTimestamp("dt").getTime();
                    row.put("timestamp", Long.valueOf(tt));
                    //获取sn(人员卡卡号)
                    Matcher matcher = r.matcher(rs.getString("description"));
                    if (matcher.find()) {
                        row.put("sn", matcher.group(0));
                    }

                    return row;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                return null;
            }
        });

        if (!result.isEmpty()) {
            List<String> cardIdList = new ArrayList<>();
            for (Iterator<Map<String, Object>> iterator = result.iterator(); iterator.hasNext(); ) {
                cardIdList.add(iterator.next().get("sn").toString());
            }
            //t_ryk_jbxx
            listSql = "SELECT sn,ryid FROM t_ryk_jbxx where sn in ('" + cardIdList.stream().collect(Collectors.joining("','")) + "') ";
            List<Map<String, Object>> personList= jdbcTemplate.query(listSql, new RowMapper() {
                public Map mapRow(ResultSet rs, int rowNum)
                        throws SQLException {
                    Map row = new LinkedHashMap();
                    try {
                        row.put("sn", rs.getInt("sn"));
                        row.put("ryid", Integer.valueOf(rs.getInt("ryid")));
                        return row;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    return null;
                }
            });

            //t_rssi
            listSql = "SELECT cardid, v FROM t_rssi WHERE cardid in ('" + cardIdList.stream().collect(Collectors.joining("','")) + "')";
            List<Voltage> voltageResult = jdbcTemplate.query(listSql, new RowMapper() {
                public Voltage mapRow(ResultSet rs, int rowNum)
                        throws SQLException {
                    Voltage voltage = new Voltage();
                    try {
                        voltage.setV(rs.getInt("v") / 10);
                        voltage.setCardid(rs.getInt( "cardid"));
                        return voltage;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    return null;
                }
            });
            Map<Integer, Integer> voltageMap = new HashMap<>();//电压值
            if (!voltageResult.isEmpty()) {
                voltageMap = voltageResult.stream().collect(Collectors.toMap(Voltage::getCardid, Voltage::getV, (x1, x2) -> x1));
            }


            Map<String, Object> sendResultMap = new HashMap<>();
            for (Iterator<Map<String, Object>> iterator = result.iterator(); iterator.hasNext(); ) {
                Map<String, Object> map = iterator.next();
                sendResultMap.put("CarId", map.get("carnum"));  //车牌号
                sendResultMap.put("Car_color", map.get("carColor"));  //车牌颜色
                sendResultMap.put("Car_PeoNum", map.get("n"));  //车上人员卡数量

                //7天未进入园区，进入的话传送一个状态为0，否则的话传送为1
                Instant timestamp = Instant.ofEpochMilli(Long.parseLong(map.get("timestamp").toString()));
                ZonedDateTime losAngelesTime = timestamp.atZone(ZoneId.of("Asia/Shanghai"));
                long between1DAYS = ChronoUnit.DAYS.between(losAngelesTime.toLocalDate(), LocalDate.now());
                sendResultMap.put("Status", "0");  //正常通行
                if (between1DAYS >= 7) {
                    sendResultMap.put("Status", "1");  //车辆通行状况
                }
                sendResultMap.put("Timestamp", losAngelesTime.toLocalDateTime().toString());
                //人员卡具体信息
                List<Map<String, Object>> personPoints = new ArrayList<>();
                if (!CollectionUtils.isEmpty(personList)) {
                    for (Map<String, Object> info : personList) {
                        Map<String,Object> personCardMap =new HashMap<>();
                        personCardMap.put("User_id",info.get("ryid"));
                        personCardMap.put("Card_sn",info.get("sn"));
                        personCardMap.put("Volit",voltageMap.get(info.get("sn")));
                        personCardMap.put("CardType","定位卡");
                        personPoints.add(personCardMap);
                    }
                }
                sendResultMap.put("PersonPoints",personPoints);

                JSONObject jObject = new JSONObject();
                jObject.put("MsgType", "car");
                jObject.put("Content", sendResultMap);
                PrintWriter printWriter = new PrintWriter(outputStream);
                try {
                    printWriter.write(new String(jObject.toJSONString().getBytes(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                printWriter.flush();
                printWriter.close();

            }
        }
    }

    private static void close(Socket socket, InputStream inputStream, OutputStream outputStream, PrintWriter printWriter) {
        try {
            if (null != printWriter)
                printWriter.close();
        } catch (Exception io) {
            System.out.println("关闭资源失败");
        }
        try {
            if (null != outputStream)
                outputStream.close();
        } catch (Exception io) {
            System.out.println("关闭资源失败");
        }
        try {
            if (null != inputStream)
                inputStream.close();
        } catch (Exception io) {
            System.out.println("关闭资源失败");
        }
        try {
            socket.close();
        } catch (Exception io) {
            System.out.println("关闭资源失败");
        }


    }
}
