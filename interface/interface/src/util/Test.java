//package util;
//
//import java.sql.Timestamp;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//
//import java.util.Date;
//
//public class Test {
//
//    public static void main(String[] args) throws ParseException {
//        long lasttime = System.currentTimeMillis();
//        Date date1 = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////        Date date1 = new Date();// 测试用.
//        Calendar rightNow = Calendar.getInstance();
//        rightNow.setTime(date1);
//        rightNow.add(Calendar.SECOND, -20);
//        //rightNow.add(Calendar.SECOND, -5555);// 测试用.
//        Date date = rightNow.getTime();
//        String nowTime1 = sdf.format(date);//
//        Date date2 = sdf.parse(nowTime1);
//        long nowTime = date2.getTime();
//        String t = String.valueOf(nowTime);
//        String s = Long.toHexString(nowTime);
//        int length = s.length();
//        System.out.println(length);
//        StringBuffer sb1 = new StringBuffer();
//        while(length<16){
//            sb1.append("0");
//            length += 1;
//        }
//        sb1.append(s);
//        System.out.println(sb1.toString());
//        Timestamp tt = new Timestamp(Long.valueOf(sb1.toString()));
//        System.out.println(tt);
//        long lastt = lasttime;
//        System.out.println(lastt);
//
//    }
//
//
//
//
//        // String currentTime = new Date();
//
////        Timestamp tt = Timestamp.valueOf(nowTime);
//
//
////        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////        String format = sdf.format(date);
////        SimpleDateFormat sdf1 = new SimpleDateFormat();
////        int time = (int)sdf1.parse(format).getTime() /1000;
//
////        Timestamp tt = Timestamp.valueOf()
////        System.out.println(tt);
////        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);//
////        Timestamp tt = Timestamp.valueOf(nowTime);
////        while(nowTime.length() <16){
////            StringBuffer sb1 = new StringBuffer();
////            StringBuffer time = sb1.append("0").append(nowTime);// 左补0
////            String s = time.toString();
////
////            System.out.println(tt);
////        }
//        // String currentTime = new Date();
//
//    }
