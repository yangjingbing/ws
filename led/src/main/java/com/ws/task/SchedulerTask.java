package com.ws.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ws.entity.LoginUP;
import com.ws.mapper.CommonMapper;
//import com.ws.until.ConvertUtil;
import com.ws.post.HttpTool;
import com.ws.until.HttpClient;
import org.apache.commons.io.IOUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * @author isHello
 */
@Component
public class SchedulerTask {

    @Resource
    private CommonMapper commonMapper;

    public static final String url = "192.168.16.100:8098";


    @Scheduled(cron = "*/10 * * * * ?")
    public void process() throws Exception{
        LoginUP login = new LoginUP();
        /*for (int i=0;i<1;i++) {

            long currentTimeMillis = System.currentTimeMillis();
            String timestamp = String.valueOf(currentTimeMillis);
        }*/
        String timestamp = "1666602180002";
        String token = "7F58D2902D7EEFF3139D1FCB343B82327F49F646B0595BDA737B050EFED75C8A";
        String saveUrl = "http://" + url + "/api/transaction/monitor?timestamp="+timestamp+"&access_token="+token+"";
//        String saveUrl = "http://localhost:8000/getAll";
        try {
            String s = HttpClient.httpGet(saveUrl, null);
            JSONObject jsonObject = JSON.parseObject(s);
//            String data = jsonObject.getString("data");
//            JSONObject object = JSON.parseObject(data);
//            System.out.println(object);
//            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            for (int i = 0; i < jsonArray.size(); i++) {
//                System.out.println(jsonArray.getString(1));
                JSONObject jsonObject2 = (JSONObject) jsonArray.get(i);
                login.setEventTime(jsonObject2.get("eventTime").toString());
                login.setPin(jsonObject2.get("pin").toString());
                login.setAreaName(jsonObject2.get("areaName").toString());
                login.setCardNo(jsonObject2.get("cardNo").toString());
                login.setDevSn(jsonObject2.get("devSn").toString());
                login.setVerifyModeName(jsonObject2.get("verifyModeName").toString());
                login.setEventName(jsonObject2.get("eventName").toString());
                login.setEventPointName(jsonObject2.get("eventPointName").toString());
                login.setReaderName(jsonObject2.get("readerName").toString());
                String status =jsonObject2.get("readerName").toString().substring(5,6);
                login.setZt(status);
                login.setDevName(jsonObject2.get("devName").toString());
                commonMapper.saveLogin(login);
            }
            /*login.setEventTime(object.get("eventTime").toString());
            login.setPin(object.get("pin").toString());
            login.setAreaName(object.get("areaName").toString());
            login.setCardNo(object.get("cardNo").toString());
            login.setDevSn(object.get("devSn").toString());
            login.setVerifyModeName(object.get("verifyModeName").toString());
            login.setEventName(object.get("eventName").toString());
            login.setEventPointName(object.get("eventPointName").toString());
            login.setReaderName(object.get("readerName").toString());
            login.setDevName(object.get("devName").toString());
            System.out.println(login.toString());
            commonMapper.saveLogin(login);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
