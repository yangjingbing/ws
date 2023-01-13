package com.ws.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ws.entity.LoginUP;
import com.ws.mapper.CommonMapper;
import com.ws.service.CommonService;
import com.ws.until.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommonServiceImpl implements CommonService {

   /* @Autowired
    private CommonMapper commonMapper;*/

    /*public static final String url = "47.110.127.110:8080";

    @Override
    public void save(LoginUP login) {
        String saveUrl = "http://" + url + "/api/v1/auth/login";
        Map<String, Object> map = new HashMap<>();
        map.put("captcha", "string");
        map.put("password", "888888");
        map.put("username", "zfjs2022");
        map.put("uuid", "string");
        try {
            String s = HttpClient.httpPost(saveUrl, map, null);
            JSONObject jsonObject = JSON.parseObject(s);
            String data = jsonObject.getString("data");
            JSONObject object = JSON.parseObject(data);
            System.out.println(object);
            login.setJwt(object.get("jwt").toString());
            login.setExpireAt(object.get("expireAt").toString());
            login.setRole(object.get("role").toString());
            System.out.println(login.toString());
            commonMapper.saveLogin(login);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}

