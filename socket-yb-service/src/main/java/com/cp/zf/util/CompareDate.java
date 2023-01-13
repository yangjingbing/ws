package com.cp.zf.util;

import java.util.Date;

/**
 * @author yjb
 * @date 2022/7/4 18:46
 */
public class CompareDate {
    public static boolean compareDate(Date d1, Date d2) {
                if (d1 == null || d2 == null) {
                       throw new RuntimeException("d1 or d2 is null");
                    }
               long t1 = d1.getTime();
                 long t2 = d2.getTime();
                long l = t1 - t2;
                if (l == 0) {
                        return true;
                     } else if (l > 0) {
                         return false;
                    } else {
                       return true;
                   }
            }
}
