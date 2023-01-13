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
 * @Description 获取人员全部信息数据.</br>
 * <></>
 * @Author gu
 * @Date 2021/1/26 11:16
 * @Version 1.0.0
 **/
public class RyJbxxServer {

    static Pattern r = Pattern.compile("\\d+");
//    private static Logger log = Logger.getLogger(CarServer.class.getClass());


    public synchronized static void RyInfo(JdbcTemplate jdbcTemplate, long lastt) {

        Date date1 = new Date(lastt);

        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date1);
        rightNow.add(13, -3);
        Date date = rightNow.getTime();

        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

        Timestamp tt = Timestamp.valueOf(nowTime);

        long startTime = System.currentTimeMillis();

        String listSql = "SELECT a.`knum`,a.`kname`,c.sn,a.`gh`,a.`mc`,a.`zwmc`,b.`mc` as bmmc,\n" +
                "a.`gzqy`,a.`xb`,a.`xl`,a.`dhhm`,a.`sjh1`,a.`zz`,NOW() AS dt\n" +
                "FROM t_ry_jbxx a \n" +
                "LEFT JOIN t_bm_jbxx b ON a.`bmid` = b.`id`\n" +
                "LEFT JOIN t_ryk_jbxx c ON a.`id` = c.`ryid` where sendzt = 1" ;
        List<Map<String, Object>> result = jdbcTemplate.query(listSql, new RowMapper() {
            public Map mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                Map<String, Object> row = new LinkedHashMap();
                try {
                    row.put("MineId",rs.getString("knum"));
                    row.put("MineName",rs.getString("kname"));
                    row.put("CardId",rs.getString("sn"));
                    row.put("PersonID",rs.getString("gh"));
                    row.put("PersonName",rs.getString("mc"));
                    row.put("Duty",rs.getString("zwmc"));
                    row.put("Department",rs.getString("bmmc"));
                    row.put("WorkPlace",rs.getString("gzqy"));
                    row.put("Sex",rs.getInt("xb"));
                    row.put("Education",rs.getString("xl"));
                    row.put("Telephone",rs.getString("dhhm"));
                    row.put("Mobile",rs.getString("sjh1"));
                    row.put("Address",rs.getString("zz"));
                    row.put("SnamTime",rs.getString("dt"));
                    return row;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                return null;
            }
        });
        if (!result.isEmpty()) {
            //log.info("开始上传实时轨迹数据。");
            System.out.println("开始上传人员信息数据。");
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("AlarmInfo", result);
            try {
                System.out.println("人员信息数据开始推送");
                String url = "http://60.168.134.123:29021/v1/location/person";
                HttpClient.httpPost(url, resultMap, null);
                //String updateSQL = "update t_alarm a set a.sendzt=2 where a.messageType = 1 and a.status = 0";
                jdbcTemplate.update("update t_ry_jbxx set sendzt=2 ");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // log.info("无人员轨迹可以推送");
            System.out.println("无数据可以推送");
        }

    }
}
