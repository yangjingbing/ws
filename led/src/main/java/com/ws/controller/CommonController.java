package com.ws.controller;

import com.ws.entity.LoginUP;
import com.ws.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommonController {

    @Autowired
    private CommonService commonService;
    /**
     * 登录信息
     */
//    @RequestMapping("/save")
//    public void Login(LoginUP login){
//        commonService.save(login);
//    }
}
