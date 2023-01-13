package com.cp.zf.task;

import com.cp.zf.service.THisBackupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Description 设备位置上报task .</br>
 * <></>
 * @Author gu
 * @Date 2021/01/12 15:25
 * @Version 1.0.0
 **/
@Slf4j
@Component
@Configuration
@EnableScheduling   // 开启定时任务
public class DevicePositionReportTask {


    @Autowired
    private THisBackupService tHisBackupService;

    @Scheduled(cron = "0 0/3 * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void job() {
        LocalDateTime now = LocalDateTime.now();
        if (log.isDebugEnabled()) {
//            log.debug("设备位置上报job：{}", now);
        }
        try {
           // tHisBackupService.uploadPosition();
        } catch (Exception e) {
//            log.error("设备位置上报失败", e);
        }

    }
}
