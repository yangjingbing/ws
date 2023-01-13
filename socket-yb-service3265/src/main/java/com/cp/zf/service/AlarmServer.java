package com.cp.zf.service;

import com.alibaba.fastjson.JSON;
import com.cp.zf.bean.CarColor;
import com.cp.zf.bean.CarIp;
import com.cp.zf.bean.Voltage;
import com.cp.zf.util.HttpClient;
import org.jsoup.helper.StringUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
 * @Description 自动上传sos报警信息 .</br>
 * <></>
 * @Author gu
 * @Date 2021/1/26 11:16
 * @Version 1.0.0
 **/
public class AlarmServer {

    static Pattern r = Pattern.compile("\\d+");
//    private static Logger log = Logger.getLogger(CarServer.class.getClass());


    public synchronized static void AlarmInfo(JdbcTemplate jdbcTemplate, long lastt) {

        Date date1 = new Date(lastt);

        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date1);
        rightNow.add(13, -3);
        Date date = rightNow.getTime();

        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

        Timestamp tt = Timestamp.valueOf(nowTime);

        long startTime = System.currentTimeMillis();

//        String listSql = "SELECT carnum,COUNT(1) n,description,dt,sip FROM t_car t WHERE LEFT(t.description,2)<>'卡号' and dt>=? GROUP  BY t.description ";
        String listSql = "SELECT a.mc,a.cardID,a.DT,a.messageType,a.inside_qymcs,a.sourceStationID FROM t_alarm a WHERE a.`status` = 0 AND a.`messageType` = 1 and sendzt = 1";
        List<Map<String, Object>> result = jdbcTemplate.query(listSql, new RowMapper() {
            public Map mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                Map<String, Object> row = new LinkedHashMap();
                try {
                    row.put("cardId", rs.getInt("cardID"));
                    row.put("mc", rs.getString("mc"));
                    row.put("dt", rs.getString("DT"));
                    row.put("messageType", rs.getInt("messageType"));
                    row.put("inside_qymcs", rs.getString("inside_qymcs"));
                    row.put("sourceStationID", rs.getInt("sourceStationID"));
                    return row;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                return null;
            }
        });
        if (!result.isEmpty()) {
            //log.info("开始上传实时轨迹数据。");
            System.out.println("开始上传sos报警数据。");
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("AlarmInfo", result);
            try {
                System.out.println("报警数据开始推送");
                String url = "http://61.177.174.53:9889/solar/dangerData/accept";
                HttpClient.httpPost(url, resultMap, null);
                //String updateSQL = "update t_alarm a set a.sendzt=2 where a.messageType = 1 and a.status = 0";
                jdbcTemplate.update("update t_alarm a set a.sendzt=2 where a.messageType = 1 and a.status = 0");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // log.info("无人员轨迹可以推送");
            System.out.println("无sos数据可以推送");
        }

    }
}
