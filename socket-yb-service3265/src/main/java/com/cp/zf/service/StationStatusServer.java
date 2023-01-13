package com.cp.zf.service;

import com.cp.zf.util.HttpClient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import redis.clients.jedis.Jedis;

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
public class StationStatusServer {
    static Pattern r = Pattern.compile("\\d+");
//    private static Logger log = Logger.getLogger(CarServer.class.getClass());



    public synchronized static void StationStatusServer(JdbcTemplate jdbcTemplate, long lastt,Jedis jedis) {



        Date date1 = new Date(lastt);

        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date1);
        rightNow.add(13, -2);
        Date date = rightNow.getTime();

        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

        Timestamp tt = Timestamp.valueOf(nowTime);

        long startTime = System.currentTimeMillis();

        List SendList = new ArrayList();

//        String listSql = "SELECT station_id from t_jz_jbxx where station_id < 1000";
        String listSql = "SELECT station_id,status from t_jz_status where station_id < 1000";
        List<Map<String, Object>> result = jdbcTemplate.query(listSql, new RowMapper() {
            @Override
            public Map mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                Map<String, Object> row = new LinkedHashMap();
                try{
                        row.put("stationId",rs.getInt("station_id"));
//                        row.put("status",rs.getInt("status"));
                        String v = jedis.get("sstatus-" + rs.getInt("station_id"));
                        System.out.println(String.format("statusId:%d redisV:%s", rs.getInt("station_id"), v));
                        if (Objects.isNull(v) || Objects.equals("", v)) {
                            row.put("status", "1");// 异常
                        } else {
                            row.put("status", "0");// 正常
                        }
                        return row;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                return null;
            }
        });
    if (!result.isEmpty()) {
//        log.info("开始上传实时轨迹数据。");
        System.out.println("开始上传基站状态数据。");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("StationStatus", result);
        try {
            System.out.println("基站状态数据开始推送");
            String url = "http://172.16.1.191:8080/park/api/stationStatus";
            HttpClient.httpPost(url, resultMap, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    } else {
//        log.info("无人员轨迹可以推送");
        System.out.println("无基站状态数据可推送");
    }
    }

}