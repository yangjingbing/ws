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
 * @Description 人员卡基本信息上传</br>
 * <></>
 * @Author gu
 * @Date 2021/1/26 11:16
 * @Version 1.0.0
 **/
public class RykJbxxServer {

    static Pattern r = Pattern.compile("\\d+");
//    private static Logger log = Logger.getLogger(CarServer.class.getClass());


    public synchronized static void RykInfo(JdbcTemplate jdbcTemplate, long lastt) {

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
        String listSql = "SELECT a.`id`,a.`sn`,a.`ryid`,a.`zt`,a.`lrsj`,a.`lrr`,NOW() AS dt\n" +
                "FROM t_ryk_jbxx a LEFT JOIN t_ry_jbxx b\n" +
                "ON a.`ryid` = b.`id` where sendzt =1 ";
        List<Map<String, Object>> result = jdbcTemplate.query(listSql, new RowMapper() {
            public Map mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                Map<String, Object> row = new LinkedHashMap();
                try {
                    row.put("id",rs.getInt("id"));
                    row.put("cardId",rs.getString("sn"));
                    row.put("Ryid",rs.getInt("ryid"));
                    row.put("Status",rs.getInt("zt"));
                    row.put("InputTime",rs.getString("lrsj"));
                    row.put("inputPerson",rs.getString("lrr"));
                    row.put("SnapTome",rs.getString("dt"));
                    return row;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                return null;
            }
        });
        if (!result.isEmpty()) {
            //log.info("开始上传实时轨迹数据。");
            System.out.println("开始上传人员卡基本数据。");
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("RykInfo", result);
            try {
                System.out.println("人员卡基本数据开始推送");
                String url = "http://60.168.134.123:29021/v1/location/personCard";
                HttpClient.httpPost(url, resultMap, null);
                //String updateSQL = "update t_alarm a set a.sendzt=2 where a.messageType = 1 and a.status = 0";
                jdbcTemplate.update("update t_ryk_jbxx set sendzt=2");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // log.info("无人员轨迹可以推送");
            System.out.println("无数据可以推送");
        }

    }
}
