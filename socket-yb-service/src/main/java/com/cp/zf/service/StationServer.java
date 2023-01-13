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
 * @Description 获取人员分站基本信息数据.</br>
 * <></>
 * @Author gu
 * @Date 2021/1/26 11:16
 * @Version 1.0.0
 **/
public class StationServer {

    static Pattern r = Pattern.compile("\\d+");
//    private static Logger log = Logger.getLogger(CarServer.class.getClass());


    public synchronized static void StationInfo(JdbcTemplate jdbcTemplate, long lastt) {

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
        String listSql = "SELECT b.`number1`,b.`dmz`,c.`id`,c.`mc`,a.`station_id`,a.`mc`,b.`dm`,a.`geo_x`,a.`geo_y`,a.`geo_z`,NOW() AS dt\n" +
                "FROM t_jz_jbxx a LEFT JOIN t_dm_layer b \n" +
                "ON a.`layer_id` = b.`id`\n" +
                "LEFT JOIN t_qy_jbxx c \n" +
                "ON a.`layer_id` = c.`layer_id`" +
                "where sendzt = 1";
        List<Map<String, Object>> result = jdbcTemplate.query(listSql, new RowMapper() {
            public Map mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                Map<String, Object> row = new LinkedHashMap();
                try {
                    row.put("MineID",rs.getInt("number1"));
                    row.put("MineName",rs.getString("dmz"));
                    row.put("AreaID",rs.getInt("id"));
                    row.put("AreaName",rs.getString("mc"));
                    row.put("StationID",rs.getInt("station_id"));
                    row.put("StationName",rs.getString("StationName"));
                    row.put("X",rs.getDouble("geo_x"));
                    row.put("Y",rs.getDouble("geo_y"));
                    row.put("Z",rs.getDouble("geo_z"));
                    row.put("SnapTime",rs.getString("dt"));
                    return row;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                return null;
            }
        });
        if (!result.isEmpty()) {
            //log.info("开始上传实时轨迹数据。");
            System.out.println("开始上传人员分站数据。");
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("StationInfo", result);
            try {
                System.out.println("人员分站数据开始推送");
//                String url = "http://61.177.174.53:9889/solar/dangerData/accept";
                String url = "http://60.168.134.123:29021/v1/location/personSubstation";
                HttpClient.httpPost(url, resultMap, null);
                //String updateSQL = "update t_alarm a set a.sendzt=2 where a.messageType = 1 and a.status = 0";
                jdbcTemplate.update("update t_jz_jbxx  set sendzt=2");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // log.info("无人员轨迹可以推送");
            System.out.println("无数据可以推送");
        }

    }
}
