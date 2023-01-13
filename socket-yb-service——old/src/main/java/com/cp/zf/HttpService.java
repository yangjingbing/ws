package com.cp.zf;

import com.cp.zf.service.CarServer;
import com.cp.zf.service.PersonService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Description http方式服务 .</br>
 * <></>
 * @Author yjb
 * @Date 2021/2/2 13:34
 * @Version 1.0.0
 **/
public class HttpService {

    private static JdbcTemplate jdbcTemplate = null;
    private static long lasttime = System.currentTimeMillis();

    public static void main(String[] args) {
        ApplicationContext factory = new FileSystemXmlApplicationContext(".//applicationContext.xml");

        jdbcTemplate = (JdbcTemplate) factory.getBean("jdbcTemplate");

        jdbcTemplate.setSkipUndeclaredResults(true);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long lastt = lasttime;
                lasttime = System.currentTimeMillis();
                System.out.println(LocalDateTime.now());

                try {
                    PersonService.personInfo(jdbcTemplate);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    CarServer.carInfo(jdbcTemplate, lastt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1000, 3000);


    }
}
