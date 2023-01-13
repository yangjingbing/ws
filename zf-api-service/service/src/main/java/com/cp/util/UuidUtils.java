package com.cp.util;

import java.util.Date;
import java.util.UUID;

public class UuidUtils {

    public static String getUuid() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }
    private static Date date = new Date();
    private static StringBuilder buf = new StringBuilder();
    private static int seq = 0;
    private static final int ROTATION = 99999;

    public static synchronized long nextNum() {
        if (seq > ROTATION)
            seq = 0;
        buf.delete(0, buf.length());
        date.setTime(System.currentTimeMillis());
        String str = String.format("%1$tY%1$tm%1$td%1$tk%1$tM%1$tS%2$05d", date, seq++);
        return Long.parseLong(str);
    }

}
