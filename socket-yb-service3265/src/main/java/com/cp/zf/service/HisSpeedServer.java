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

/**
 * @author yjb
 * @date 2021/10/9 9:09
 */
public class HisSpeedServer {
    static Pattern r = Pattern.compile("\\d+");
//    private static Logger log = Logger.getLogger(CarServer.class.getClass());


    public synchronized static void HisSpeedInfo(JdbcTemplate jdbcTemplate, long lastt) {

        Date date1 = new Date(lastt);

        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date1);
        rightNow.add(13, -2);
        Date date = rightNow.getTime();

        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

        Timestamp tt = Timestamp.valueOf(nowTime);

        long startTime = System.currentTimeMillis();

        List SendList = new ArrayList();

        String listSql = "SELECT a.card_sn,a.geo_x,a.geo_y,a.dt,b.uuid,b.mc,c.`speed` FROM t_his a LEFT JOIN t_ry_jbxx b ON a.card_sn = b.card_sn LEFT JOIN t_speed c ON a.`card_sn` = c.`cardid`  WHERE a.dt >= DATE_ADD(NOW(),INTERVAL -60 SECOND) and c.speed > 60 GROUP BY a.card_sn";
        List<Map<String, Object>> result = jdbcTemplate.query(listSql, new RowMapper() {
            @Override
            public Map mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                Map<String, Object> row = new LinkedHashMap();
                try {
                    row.put("uuid", rs.getString("uuid"));
                    row.put("cardSn", rs.getString("card_sn"));
                    row.put("ryName", rs.getString("mc"));
                    long tt = rs.getTimestamp("dt").getTime();
                    double x = rs.getDouble("geo_x");
                    double y = rs.getDouble("geo_y");
                    ConvertUtil convertUtil = new ConvertUtil();
                    double[] xy = convertUtil.xy2lonlat(x,y);
                    row.put("lng", xy[0]);
                    row.put("lat", xy[1]);
                    row.put("time", rs.getString("dt"));
                    row.put("speed",rs.getDouble("speed"));
                    return row;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                return null;
            }
        });
    if (!result.isEmpty()) {
//        log.info("开始上传实时轨迹数据。");
        System.out.println("开始上传超速数据。");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("personPoints", result);
        try {
            System.out.println("实时数据开始推送");
//            String url = "http://172.16.1.191:8082/park/api/speed/event";
            String url = "http://172.16.1.191:8083/craftsman-admin/api/speed/event";
            HttpClient.httpPost(url, resultMap, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    } else {
//        log.info("无人员轨迹可以推送");
        System.out.println("无超速数据可以推送");
    }
    }

}