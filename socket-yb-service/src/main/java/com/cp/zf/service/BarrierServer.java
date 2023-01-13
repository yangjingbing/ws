package com.cp.zf.service;

import com.cp.zf.util.HttpClient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @Description 获取实时报警数据.</br>
 * <></>
 * @Author gu
 * @Date 2021/1/26 11:16
 * @Version 1.0.0
 **/
public class BarrierServer {

    static Pattern r = Pattern.compile("\\d+");
//    private static Logger log = Logger.getLogger(CarServer.class.getClass());


    public synchronized static void BarrierInfo(JdbcTemplate jdbcTemplate, long lastt) {

        Date date1 = new Date(lastt);

        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date1);
        rightNow.add(13, -3);
        Date date = rightNow.getTime();

        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

        Timestamp tt = Timestamp.valueOf(nowTime);

        long startTime = System.currentTimeMillis();

//        String listSql = "SELECT carnum,COUNT(1) n,description,dt,sip FROM t_car t WHERE LEFT(t.description,2)<>'卡号' and dt>=? GROUP  BY t.description ";
//        String listSql = "SELECT a.mc,a.cardID,a.DT,a.messageType,a.inside_qymcs,a.sourceStationID FROM t_alarm a WHERE a.`status` = 0 AND a.`messageType` = 1 and sendzt = 1";
        String listSql = "SELECT d.`id`,e.`mc`,a.x11,a.y11,a.x22,a.y22,a.x33,a.y33,a.x44,a.y44,NOW() AS dt\n" +
                "FROM t_qy_xy a \n" +
                "LEFT JOIN t_dm_layer b ON a.`layer_id` = b.`id`\n" +
                "LEFT JOIN t_qy_jbxx c ON c.`layer_id` = b.`id`\n" +
                "LEFT JOIN t_qy_bjsz d ON c.`id` = d.`qyid`\n" +
                "LEFT JOIN t_dm_bjzd e ON d.`bjlx` = e.`id`" +
                "where a.sendzt = 1";
        List<Map<String, Object>> result = jdbcTemplate.query(listSql, new RowMapper() {
            public Map mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                Map<String, Object> row = new LinkedHashMap();
                try {
                    row.put("barrierid",rs.getInt("id"));
                    row.put("barriernm",rs.getString("mc"));
                    Map map = new HashMap();
                    List list = new ArrayList();
                    map.put("X11",rs.getDouble("x11"));
                    map.put("Y11",rs.getDouble("x11"));
                    map.put("X22",rs.getDouble("x11"));
                    map.put("Y22",rs.getDouble("x11"));
                    map.put("X33",rs.getDouble("x11"));
                    map.put("Y33",rs.getDouble("x11"));
                    map.put("X44",rs.getDouble("x11"));
                    map.put("Y44",rs.getDouble("x11"));
                    list.add(map);
                    row.put("list",list);
                    return row;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                return null;
            }
        });
        if (!result.isEmpty()) {
            //log.info("开始上传实时轨迹数据。");
            System.out.println("开始上传电子围栏点位信息数据。");
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("BarrierInfo", result);
            try {
                System.out.println("电子围栏点位信息数据开始推送");
                String url = "http://60.168.134.123:29021/v1/location/electronicFencePoint";
                HttpClient.httpPost(url, resultMap, null);
                //String updateSQL = "update t_alarm a set a.sendzt=2 where a.messageType = 1 and a.status = 0";
                jdbcTemplate.update("update t_qy_xy set sendzt=2 ");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // log.info("无人员轨迹可以推送");
            System.out.println("无数据可以推送");
        }

    }
}
