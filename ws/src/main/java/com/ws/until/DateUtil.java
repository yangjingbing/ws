package com.ws.until;

import java.text.*;
import java.util.*;

/**
 * 判断两个时间中间的时间差（以小时未单位）
 */
public class DateUtil {

    public static Double TimeCov(String t1,String t2) {

        Date d1 = null;
        Date d2 = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            d1 = sdf.parse(t1);
            d2 = sdf.parse(t2);
        } catch (ParseException pe) {
            System.out.println(pe.getMessage());
        }

        long dd1 = d1.getTime();
        long dd2 = d2.getTime();
        double hours = (double) (dd2 - dd1) / 3600 / 1000;
//        System.out.println("时间差是：" + hours + "（小时）");
        return hours;
    }
}
