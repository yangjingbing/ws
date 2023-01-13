package com.cp.zf.controller;

import com.cp.framwork.core.ApiResponse;
import com.cp.framwork.core.ApiResponseFactory;
import com.cp.zf.service.THisBackupService;
import com.cp.zf.service.TRykJbxxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/{version}/zf/test")
public class TestController {

    @Autowired
    private TRykJbxxService tRykJbxxService;
    @Autowired
    private THisBackupService tHisBackupService;

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/start")
    public ApiResponse start() throws Exception {
        return ApiResponseFactory.get("ok");
    }

    @GetMapping("/upload-device")
    public ApiResponse uploadDevice() throws Exception {
        tRykJbxxService.uploadDeviceCard();
        return ApiResponseFactory.get("ok");
    }

    @GetMapping("/upload-position")
    public ApiResponse uploadPosition() throws Exception {
        tHisBackupService.uploadPosition();
        return ApiResponseFactory.get("ok");
    }

}
