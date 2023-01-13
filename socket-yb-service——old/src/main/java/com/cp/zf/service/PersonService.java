package com.cp.zf.service;

import com.alibaba.fastjson.JSONObject;
import com.cp.zf.util.HttpClient;
import org.jsoup.helper.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Description 人员 .</br>
 * <></>
 * @Author yjb
 * @Date 2021/1/26 16:07
 * @Version 1.0.0
 **/
public class PersonService {

    static Pattern r = Pattern.compile("\\d*");


    public static void personInfo(JdbcTemplate jdbcTemplate) {

        List<String> sqlList = new ArrayList<>();
        sqlList.add("SELECT a.id,a.`uuid`,a.`mc`,a.`zp`,a.`zpsmall`,a.`zpbig`,a.`gh`,a.`zwmc`,");
        sqlList.add("a.`xb`,a.`xx`,a.`zz`,a.`hkszd`,a.`gddh`,a.`sjh1`,");
        sqlList.add("a.`dzyx`,a.`zjlx`,a.`zjh`,a.`zt`,a.`lrsj`,a.`lrr`,a.`GZ_show`,a.`color`,");
//        sqlList.add("a.`gz`,a.`fjh`,a.`mark`,c.`MC` AS gzmc,e.sn,b.`dwid`,");
        sqlList.add("a.`gz`,a.`fjh`,a.`mark`,c.`MC` AS gzmc,e.sn,a.`entId`,");
        sqlList.add("a.sendzt,a.sendztbd,a.sendztjb,f.icId,g.id as loss_id");//新增字段
        sqlList.add("FROM t_ry_jbxx a LEFT JOIN t_bm_jbxx b ON a.`bmid` = b.`id`");
        sqlList.add("LEFT JOIN t_dm_gz c ON a.`gz` = c.`id`");
        sqlList.add("LEFT JOIN t_dm_zw d ON a.`zwmc` = d.`mc`");
        sqlList.add("LEFT JOIN t_ryk_jbxx e ON a.id = e.`ryid`");
        sqlList.add("LEFT JOIN t_ic_card f ON f.card_sn = e.sn");//新增表结构
        sqlList.add("LEFT JOIN t_loss_card g ON g.card_sn = e.sn");//新增表结构
        sqlList.add("");


        String listSql = sqlList.stream().collect(Collectors.joining(" "));
        List<Integer> idList = new ArrayList<>();
        List<Integer> jbIdList = new ArrayList<>();
        List<Map<String, Object>> dbResult = jdbcTemplate.query(listSql, new RowMapper() {
            public Map mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                Map<String, Object> row = new LinkedHashMap();
                try {
                    row.put("card_sn", rs.getString("sn"));
                    row.put("color", rs.getString("color"));
                    row.put("dzyx", rs.getString("dzyx"));
                    row.put("fjh", rs.getString("fjh"));
                    row.put("gddh", rs.getString("gddh"));
                    row.put("gh", rs.getString("gh"));
                    row.put("gz", rs.getString("gz"));
                    row.put("gz_show", rs.getString("GZ_show"));
                    row.put("gzmc", rs.getString("gzmc"));
                    row.put("hkszd", rs.getString("hkszd"));
                    row.put("id", rs.getString("uuid"));
                    row.put("lrr", rs.getString("lrr"));
                    long lrsj = rs.getTimestamp("lrsj").getTime();
                    Instant timestamp = Instant.ofEpochMilli(lrsj);
                    ZonedDateTime locTime = timestamp.atZone(ZoneId.of("Asia/Shanghai"));
                    row.put("lrsj", locTime.toLocalDateTime().toString());
                    row.put("mark", rs.getString("mark"));
                    row.put("mc", rs.getString("mc"));
                    row.put("sex", rs.getString("xb"));
                    row.put("sjh1", rs.getString("sjh1"));
                    row.put("xx", rs.getString("xx"));
                    row.put("zjh", rs.getString("zjh"));
                    row.put("zjlx", rs.getString("zjlx"));
                    row.put("zp", rs.getString("zp"));
                    row.put("zpbig", rs.getString("zpbig"));
                    row.put("zpsmall", rs.getString("zpsmall"));
                    row.put("zwmc", rs.getString("zwmc"));
                    row.put("zz", rs.getString("zz"));
                    row.put("dwid", rs.getString("entId"));

                    row.put("icId", rs.getString("icId"));
                    int sendzt = rs.getInt("sendzt");//发送状态
                    int sendztbd = rs.getInt("sendztbd");//绑定状态
                    int sendztjb = rs.getInt("sendztjb");//解绑状态
                    //绑定状态1：a.sendzt=1 or (a.sendztbd=1 and a.sendztjb=1)
                    if (sendzt == 1) {
                        row.put("rykStatus", "1");
                    } else if (sendzt == 2 && sendztjb != 2) {
                        //解绑条件2：t_ry_jbxx中id不在t_ryk_jbxx 中ryid，并且t_ry_jbxx中sendzt=2的情况。
                        if (Objects.isNull(rs.getString("sn")) || Objects.equals("", rs.getString("sn"))) {
                            row.put("rykStatus", "2");
                            jbIdList.add(rs.getInt("id"));

                        }
                        //挂失条件3：sendzt=2 ，但是在t_loss_card 中，说明是挂失
                        else if (!Objects.isNull(rs.getInt("loss_id")) && rs.getInt("loss_id") != 0) {
                            row.put("rykStatus", "3");
                            jbIdList.add(rs.getInt("id"));

                        }

                    }


                    idList.add(rs.getInt("id"));

                    return row;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                return null;
            }
        });

        dbResult = dbResult.stream().filter(f -> !Objects.isNull(f.get("rykStatus"))).collect(Collectors.toList());

        if (!dbResult.isEmpty()) {
            System.out.println("开始上传人员数据。");
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("MsgType", "person");
            resultMap.put("Content", dbResult);
            try {
                String url = "http://172.16.1.191:8080/park/api/getPersonList";
                HttpClient.httpPost(url, resultMap, null);

                //修改t_ry_jbxx 中 sendzt = 2已发送
                jdbcTemplate.update("update t_ry_jbxx set sendzt=2 where id in(" + idList.stream().map(f -> String.valueOf(f)).collect(Collectors.joining(",")) + ")");

                if (!jbIdList.isEmpty()) {
                    jdbcTemplate.update("update t_ry_jbxx set sendztjb=2 where id in(" + jbIdList.stream().map(f -> String.valueOf(f)).collect(Collectors.joining(",")) + ")");

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("无人员数据可以推送");

        }
    }

}
