package com.cp.zf.service;

import com.cp.zf.util.ConvertUtil;
import com.cp.zf.util.HttpClient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author yjb
 * @date 2021/10/9 9:09
 */
public class HisServer {
    static Pattern r = Pattern.compile("\\d+");
//    private static Logger log = Logger.getLogger(CarServer.class.getClass());


    public synchronized static void HisTraInfo(JdbcTemplate jdbcTemplate, long lastt) {

        Date date1 = new Date(lastt);

        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date1);
        rightNow.add(13, -2);
        Date date = rightNow.getTime();

        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

        Timestamp tt = Timestamp.valueOf(nowTime);

        long startTime = System.currentTimeMillis();

        List SendList = new ArrayList();

//        String listSql = "SELECT a.card_sn,a.geo_x,a.geo_y,a.dt,a.nearly_station1,b.uuid,b.mc FROM t_his a LEFT JOIN t_ry_jbxx b ON a.card_sn = b.card_sn  WHERE dt >= DATE_ADD(NOW(),INTERVAL -60 SECOND) GROUP BY a.card_sn";
        String listSql = "SELECT a.`number1`,a.`dmz`,COUNT(DISTINCT(b.`card_sn`)) AS personCount,\n" +
                "b.`card_sn`,d.`gh`,d.mc as ryName,d.`zwmc`,e.`mc` as bmmc,g.`in_time`,g.`out_time`,\n" +
                "f.`id`,f.`mc`,g.`in_time` as timInArea,b.`one_station_id`,h.`mc` as jzmc,COUNT(one_station_id) AS stationNum,\n" +
                "MIN(b.`dt`) AS timeInStation,b.`geo_x`,b.`geo_y`,NOW() AS dt\n" +
                "FROM t_dm_layer a\n" +
                "LEFT JOIN t_his b ON a.`id` = b.`layer_id`\n" +
                "LEFT JOIN t_ryk_jbxx c ON b.`card_sn` = c.`sn`\n" +
                "LEFT JOIN t_ry_jbxx d ON c.`ryid` = d.`id`\n" +
                "LEFT JOIN t_bm_jbxx e ON d.`bmid` = e.`id`\n" +
                "LEFT JOIN t_qy_jbxx f ON a.`id` = f.`layer_id`\n" +
                "LEFT JOIN t_qy_inorout g ON f.`id` = g.`qyid`\n" +
                "LEFT JOIN t_jz_jbxx h ON a.`id` = h.`layer_id`\n" +
                "Group by b.card_sn";
        List<Map<String, Object>> result = jdbcTemplate.query(listSql, new RowMapper() {
            @Override
            public Map mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                Map<String, Object> row = new LinkedHashMap();
                try {
//                    row.put("uuid", rs.getString("uuid"));
//                    row.put("cardSn", rs.getString("card_sn"));
//                    row.put("ryName", rs.getString("mc"));
//                    long tt = rs.getTimestamp("dt").getTime();
//                    double x = rs.getDouble("geo_x");
//                    double y = rs.getDouble("geo_y");
//                    ConvertUtil convertUtil = new ConvertUtil();
//                    double[] xy = convertUtil.xy2lonlat(x,y);
//                    row.put("lng", xy[0]);
//                    row.put("lat", xy[1]);
//                    row.put("time", rs.getString("dt"));
//                    row.put("stationId", rs.getString("nearly_station1"));
//                    return row;
                    row.put("MineID",rs.getInt("number1"));
                    row.put("MineName",rs.getString("dmz"));
                    row.put("PersonCountInWell",rs.getInt("personCount"));
                    row.put("CardID",rs.getString("card_sn"));
                    row.put("personID",rs.getInt("gh"));
                    row.put("PersonName",rs.getString("ryName"));
                    row.put("Duty",rs.getString("zwmc"));
                    row.put("Department",rs.getString("bmmc"));
                    row.put("TimeInWell",rs.getString("in_time"));
                    row.put("TimeOutWell",rs.getString("out_time"));
                    row.put("AreaID",rs.getInt("id"));
                    row.put("AreaName",rs.getString("mc"));
                    row.put("TimeInArea",rs.getString("timeInArea"));
                    row.put("StationId",rs.getString("one_station_id"));
                    row.put("StationName",rs.getString("jzmc"));
                    row.put("PersonCountInStation",rs.getString("stationNum"));
                    row.put("TimeInStation",rs.getString("timeInStation"));
                    row.put("X",rs.getString("geo_x"));
                    row.put("Y",rs.getString("geo_y"));
                    row.put("SnapTime",rs.getString("dt"));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                return null;
            }
        });
    if (!result.isEmpty()) {
//        log.info("开始上传实时轨迹数据。");
        System.out.println("开始上传实时轨迹数据。");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("personPoints", result);
        try {
            System.out.println("实时轨迹开始推送");
//            String url = "http://172.16.1.191:8083/craftsman-admin/api/accept/pass/personTrajectory";
            String url = "http://60.168.134.123:29021/v1/location/personReal";
            HttpClient.httpPost(url, resultMap, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    } else {
//        log.info("无人员轨迹可以推送");
        System.out.println("无人员轨迹可以推送");
    }
    }

}