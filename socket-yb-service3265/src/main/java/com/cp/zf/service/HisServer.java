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

        String listSql = "SELECT a.card_sn,a.geo_x,a.geo_y,a.dt,a.nearly_station1,b.uuid,b.mc FROM t_his a LEFT JOIN t_ry_jbxx b ON a.card_sn = b.card_sn  WHERE dt >= DATE_ADD(NOW(),INTERVAL -60 SECOND) GROUP BY a.card_sn";
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
                    row.put("stationId", rs.getString("nearly_station1"));
                    return row;
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
            String url = "http://172.16.1.191:8083/craftsman-admin/api/accept/pass/personTrajectory";
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