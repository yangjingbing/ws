package com.cp.zf.service;

import com.cp.zf.bean.Voltage;
import com.cp.zf.util.ConvertUtil;
import com.cp.zf.util.HttpClient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
public class VoltServer {
    static Pattern r = Pattern.compile("\\d+");
//    private static Logger log = Logger.getLogger(CarServer.class.getClass());


    public synchronized static void VoltsServer(JdbcTemplate jdbcTemplate, long lastt) {

        Date date1 = new Date(lastt);

        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date1);
        rightNow.add(13, -2);
        Date date = rightNow.getTime();

        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

        Timestamp tt = Timestamp.valueOf(nowTime);

        long startTime = System.currentTimeMillis();

        List sendList = new ArrayList();

        String listSql = "SELECT CardID,v FROM t_rssi_backup where dt > date_add(now(),interval -300 second ) and zt =1 GROUP BY CARDID";
        List<Map<String, Object>> result = jdbcTemplate.query(listSql, new RowMapper() {
            @Override
            public Map mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                Map<String, Object> row = new LinkedHashMap();
                try{
                        double V = rs.getInt("v");
                        double v = V /10;
                        row.put("volt",v);
                        row.put("cardSn",rs.getInt("CardID"));
                        sendList.add(rs.getInt("CardID"));
                        return row;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                return null;
            }
        });
    if (!result.isEmpty()) {
//        log.info("开始上传实时轨迹数据。");
        System.out.println("开始上传定位卡电量数据。");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("cardVolts", result);
        try {
            System.out.println("电量数据开始推送");
            String url = "http://172.16.1.191:8080/park/api/updateVolt";
            HttpClient.httpPost(url, resultMap, null);
            if(!sendList.isEmpty()) {
                jdbcTemplate.update("update t_rssi_backup set zt = 2");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    } else {
//        log.info("无人员轨迹可以推送");
        System.out.println("无人员轨迹可以推送");
    }
    }

}