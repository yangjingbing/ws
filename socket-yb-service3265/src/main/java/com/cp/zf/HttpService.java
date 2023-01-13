package com.cp.zf;

import com.cp.zf.service.*;
import lombok.val;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;

/**
 * @Description http方式服务 .</br>
 * <></>
 * @Author gu
 * @Date 2021/2/2 13:34
 * @Version 1.0.0
 **/
public class HttpService {

    private static JdbcTemplate jdbcTemplate = null;
    private static long lasttime = System.currentTimeMillis();

    public static void main(String[] args) {
        ApplicationContext factory = new FileSystemXmlApplicationContext(".//applicationContext.xml");

        jdbcTemplate = (JdbcTemplate) factory.getBean("jdbcTemplate");

        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.select(13);
        System.out.println("redis连接成功");
        System.out.println("---------------------");
        System.out.println("---------------------");
        System.out.println("---------------------");

        jdbcTemplate.setSkipUndeclaredResults(true);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long lastt = lasttime;
                lasttime = System.currentTimeMillis();
                System.out.println(LocalDateTime.now());

               /* CompletableFuture.runAsync(() -> {
                    try {
                        PersonService.personInfo(jdbcTemplate);
                        System.out.println("PersonService cost=" + (System.currentTimeMillis() - lastt));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });*/


               /* CompletableFuture.runAsync(() -> {
                    try {
                        CarServer.carInfo(jdbcTemplate, lastt);
                        System.out.println("CarServer cost=" + (System.currentTimeMillis() - lastt));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });*/
                /*CompletableFuture.runAsync(() -> {
                    try {
                        CarScjServer.carscjInfo(jdbcTemplate, lastt);
                        System.out.println("CarScjServer cost=" + (System.currentTimeMillis() - lastt));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });*/
                CompletableFuture.runAsync(() -> {
                    try {
                        HisServer.HisTraInfo(jdbcTemplate, lastt);
                        System.out.println("HisTraServer cost=" + (System.currentTimeMillis() - lastt));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                /*CompletableFuture.runAsync(() -> {
                    try {
//                        HisSpeedServer.HisSpeedInfo(jdbcTemplate, lastt);
//                        System.out.println("HisSpeedServer cost=" + (System.currentTimeMillis() - lastt));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });*/
               /* CompletableFuture.runAsync(() -> {
                    try {
                        AlarmServer.AlarmInfo(jdbcTemplate,lastt);
                        System.out.println("AlarmServer cost=" + (System.currentTimeMillis() - lastt));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });*/
                CompletableFuture.runAsync(() -> {
                    try {
                        VoltServer.VoltsServer(jdbcTemplate, lastt);
                        System.out.println("VoltServer cost=" + (System.currentTimeMillis() - lastt));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                CompletableFuture.runAsync(() -> {
                    try {
                        StationStatusServer.StationStatusServer(jdbcTemplate, lastt, jedis);
//                          StationStatusServer.StationStatusServer(jdbcTemplate, lastt);
                          System.out.println("StationStatusServer cost=" + (System.currentTimeMillis() - lastt));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }, 1000, 3000);

    }
}
