package com.cp.zf.util;

import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yjb
 * @date 2022/7/4 18:32
 */
public class VisitURL {
    public static String VisitURL(String url) {
        String urlDate = null;
        try {
            URL url1 = new URL(url);
            URLConnection conn = url1.openConnection(); // 生成连接对象
            conn.connect(); // 连接对象网页
            Date date = new Date(conn.getDate()); // 获取对象网址时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置日期格式
            urlDate = df.format(date);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return urlDate;
    }
}
