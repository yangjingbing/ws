package com.cp.zf.service;

import com.alibaba.fastjson.JSONObject;
import com.cp.zf.bean.CarColor;
import com.cp.zf.bean.CarIp;
import com.cp.zf.bean.Voltage;
import com.cp.zf.util.HttpClient;
import org.jsoup.helper.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Description TODO .</br>
 * <></>
 * @Author gu
 * @Date 2021/1/26 11:16
 * @Version 1.0.0
 **/
public class CarServer {

    static Pattern r = Pattern.compile("\\d+");


    public static void carInfo(JdbcTemplate jdbcTemplate, long lastt) {

        Date date1 = new Date(lastt);

        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date1);
        rightNow.add(13, -3);
        Date date = rightNow.getTime();

        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

        Timestamp tt = Timestamp.valueOf(nowTime);

        //t_car
//        String listSql = "SELECT carnum,COUNT(1) n,description,dt,sip FROM t_car t WHERE LEFT(t.description,2)<>'卡号' and dt>=? GROUP  BY t.description ";
        String listSql = "SELECT carnum,COUNT(1) n,description,dt,sip,pasted FROM t_car t WHERE sendzt=1 and dt>=? GROUP  BY t.description,t.carnum ";
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
                    row.put("sip", rs.getString("sip"));
                    row.put("pasted", rs.getString("pasted"));
                    //获取sn(人员卡卡号)
                    row.put("sn", "");
                    System.out.println("当前description：" + rs.getString("description"));
                    if (!Objects.isNull(rs.getString("description")) && !Objects.equals("", rs.getString("description"))) {
                        Matcher matcher = r.matcher(rs.getString("description"));
                        List<String> snList = new ArrayList<>();
                        while (matcher.find()) {
                            snList.add(matcher.group());
                        }
                        System.out.println("sn before:"+StringUtil.join(snList, ","));
                        row.put("sn", StringUtil.join(snList, ","));
                    }

                    return row;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                return null;
            }
        });

        if (!result.isEmpty()) {
//            List<String> descriptionWaitUpdateList = new ArrayList<>();
//            List<String> carnumWaitUpdateList = new ArrayList<>();

            List<String> cardIdList = new ArrayList<>();
            List<String> ipList = new ArrayList<>();
            for (Iterator<Map<String, Object>> iterator = result.iterator(); iterator.hasNext(); ) {
                Map<String, Object> tmpMap = iterator.next();
                String[] arr = tmpMap.getOrDefault("sn", "").toString().split(",");
                if (null != arr && arr.length > 0) {
                    Arrays.stream(arr).forEach(cardId -> {
                        cardIdList.add(cardId);
                    });
                }
                ipList.add(tmpMap.getOrDefault("sip", "").toString());

                //待修改con
//                descriptionWaitUpdateList.add(tmpMap.getOrDefault("description", "").toString());
//                carnumWaitUpdateList.add(tmpMap.getOrDefault("carnum", "").toString().trim().replace(" ",""));

                System.out.println("sn=" + cardIdList.stream().collect(Collectors.joining("','")));
                //t_ryk_jbxx
                listSql = "SELECT sn,ryid FROM t_ryk_jbxx where sn in ('" + cardIdList.stream().collect(Collectors.joining("','")) + "') ";
                List<Map<String, Object>> personList = jdbcTemplate.query(listSql, new RowMapper() {
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
                //t_ry_jbxx
                Map<Integer, String> idWithUuidMap = new HashMap<>();
                if (!CollectionUtils.isEmpty(personList)) {
                    List<String> userIdList = personList.stream().map(f -> f.getOrDefault("ryid", "0").toString()).collect(Collectors.toList());
                    listSql = "SELECT id,uuid FROM t_ry_jbxx where id in ('" + userIdList.stream().collect(Collectors.joining("','")) + "') ";
                    List<Map<String, Object>> personInfoList = jdbcTemplate.query(listSql, new RowMapper() {
                        public Map mapRow(ResultSet rs, int rowNum)
                                throws SQLException {
                            Map row = new LinkedHashMap();
                            try {
                                row.put("ryid", rs.getInt("id"));
                                row.put("uuid", rs.getString("uuid"));
                                return row;
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                            return null;
                        }
                    });
                    if (!CollectionUtils.isEmpty(personInfoList)) {
                        for (Map<String, Object> info : personInfoList) {
                            idWithUuidMap.put(Integer.parseInt(info.get("ryid").toString()), info.get("uuid").toString());
                        }
                    }
                }

                //t_rssi
                listSql = "SELECT cardid, v FROM t_rssi WHERE cardid in ('" + cardIdList.stream().collect(Collectors.joining("','")) + "')";
                List<Voltage> voltageResult = jdbcTemplate.query(listSql, new RowMapper() {
                    public Voltage mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        Voltage voltage = new Voltage();
                        try {
                            voltage.setV(rs.getInt("v") / 10);
                            voltage.setCardid(rs.getInt("cardid"));
                            return voltage;
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        return null;
                    }
                });

                //t_car_ip
                listSql = "SELECT ip, qyid FROM t_car_ip WHERE ip in ('" + ipList.stream().collect(Collectors.joining("','")) + "')";
                List<CarIp> carIpResult = jdbcTemplate.query(listSql, new RowMapper() {
                    public CarIp mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        CarIp carIp = new CarIp();
                        try {
                            carIp.setIp(rs.getString("ip"));
                            carIp.setQyid(rs.getInt("qyid"));
                            return carIp;
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        return null;
                    }
                });

                //t_car_color
                listSql = "SELECT id, mc FROM t_car_color";
                List<CarColor> carColorResult = jdbcTemplate.query(listSql, new RowMapper() {
                    public CarColor mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        CarColor carColor = new CarColor();
                        try {
                            carColor.setId(rs.getInt("id"));
                            carColor.setMc(rs.getString("mc"));
                            return carColor;
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        return null;
                    }
                });

                //电压值
                Map<Integer, Integer> voltageMap = new HashMap<>();
                if (!voltageResult.isEmpty()) {
                    voltageMap = voltageResult.stream().collect(Collectors.toMap(Voltage::getCardid, Voltage::getV, (x1, x2) -> x1));
                }

                //卡扣编号
                Map<String, Integer> kkbhMap = new HashMap<>();
                if (!carIpResult.isEmpty()) {
                    kkbhMap = carIpResult.stream().collect(Collectors.toMap(CarIp::getIp, CarIp::getQyid, (x1, x2) -> x1));
                }

                //颜色编码
                Map<String, Integer> colorCodeMap = new HashMap<>();
                String allColorStr = "";
                if (!carColorResult.isEmpty()) {
                    colorCodeMap = carColorResult.stream().collect(Collectors.toMap(CarColor::getMc, CarColor::getId));
                    allColorStr = carColorResult.stream().map(f -> f.getMc()).collect(Collectors.joining("|"));
                }

                Map<String, Object> sendResultMap = new HashMap<>();
                sendResultMap.put("CarId", tmpMap.getOrDefault("carnum","").toString().replaceAll(allColorStr,""));  //车牌号
                sendResultMap.put("Car_color", colorCodeMap.getOrDefault(tmpMap.getOrDefault("carColor", ""), null));  //车牌颜色


                Instant timestamp = Instant.ofEpochMilli(Long.parseLong(tmpMap.get("timestamp").toString()));
                ZonedDateTime losAngelesTime = timestamp.atZone(ZoneId.of("Asia/Shanghai"));
                sendResultMap.put("Status", tmpMap.get("pasted"));
                sendResultMap.put("Timestamp", losAngelesTime.toLocalDateTime().toString());
                //人员卡具体信息
                List<Map<String, Object>> personPoints = new ArrayList<>();
                sendResultMap.put("Car_PeoNum", 0);  //车上人员卡数量
                if (!CollectionUtils.isEmpty(personList)) {
                    long between1DAYS = ChronoUnit.DAYS.between(losAngelesTime.toLocalDate(), LocalDate.now());
                    for (Map<String, Object> info : personList) {
                        Map<String, Object> personCardMap = new HashMap<>();
                        personCardMap.put("User_id", idWithUuidMap.get(info.get("ryid")));
                        personCardMap.put("Card_sn", info.get("sn"));
                        personCardMap.put("Volit", voltageMap.getOrDefault(info.get("sn"), null));
                        //7天未进入园区，进入的话传送一个状态为0，否则的话传送为1
                        personCardMap.put("S_status", "0");  //正常通行
                        if (between1DAYS >= 7) {
                            sendResultMap.put("S_status", "1");  //车辆通行状况
                        }
                        personCardMap.put("CardType", "定位卡");
                        personPoints.add(personCardMap);
                    }
                    sendResultMap.put("Car_PeoNum", personPoints.size());  //车上人员卡数量
                }
                sendResultMap.put("FastenerId", kkbhMap.getOrDefault(tmpMap.getOrDefault("sip", ""), null));
                sendResultMap.put("PersonPoints", personPoints);

                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("MsgType", "car");
                resultMap.put("Content", sendResultMap);
                try {
                    System.out.println("通行记录数据。");
                    HttpClient.httpPost("http://172.16.1.191:8080/park/api/accept/pass", resultMap, null);

                    jdbcTemplate.update("update t_car set sendzt=2 where description ='" + tmpMap.getOrDefault("description","").toString() + "' and carnum like '%" + tmpMap.getOrDefault("carnum", "").toString().trim().replace(" ","") + "%'");

//                    jdbcTemplate.update("update t_car set sendzt=2 where description in('" + descriptionWaitUpdateList.stream().collect(Collectors.joining("','")) + "') and carnum in('" + carnumWaitUpdateList.stream().collect(Collectors.joining("','")) + "')");
//                    for (int i = 0; i < descriptionWaitUpdateList.size(); i++) {
//                        jdbcTemplate.update("update t_car set sendzt=2 where description ='" + descriptionWaitUpdateList.get(i) + "' and carnum like '%" + carnumWaitUpdateList.get(i) + "%'");
//                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }



        } else {
            System.out.println("无通行记录数据。");
        }
    }

    public static void main(String[] args) {

        //    Matcher matcher = rn.matcher("卡号：8059,8100,8191,8193绑定的车辆符合放行条件  进入");
        Matcher matcher = r.matcher("卡号：6307,6087,6361,6083,6080,6365所在的车辆符合放行条件  离开");
        // String result = matcher.replaceAll("");
        //    System.out.println(result);


        System.out.println("=========");
        while (matcher.find()) {
            System.out.println(matcher.group());
        }

        System.out.println("=========");
        Matcher matcher2 = r.matcher("卡号：6307,6087,6361,6083,6080,6365所在的车辆符合放行条件  离开");
        while (matcher2.find()) {
            System.out.println(matcher2.group());
        }

     }
}
