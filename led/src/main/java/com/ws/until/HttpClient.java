package com.ws.until;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ws.entity.LoginUP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * @Description TODO .</br>
 * <></>
 * @Author gu
 * @Date 2021/1/26 12:54
 * @Version 1.0.0
 **/
public class HttpClient {

    public static String httpPost(String url, Map<String, Object> reqData, String xx) throws Exception {
        System.out.println("post url:" + url + " body:" + reqData);

        String UTF8 = "UTF-8";
        String reqBody = JSON.toJSONString(reqData);
        URL httpUrl = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setConnectTimeout(36000);
        httpURLConnection.setReadTimeout(15000);
        httpURLConnection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
        httpURLConnection.setRequestProperty("accept","application/json");

        httpURLConnection.connect();
        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write(reqBody.getBytes(UTF8));
        outputStream.flush();
        outputStream.close();


        // if (httpURLConnection.getResponseCode()!= 200) {
        //     throw new Exception(String.format("HTTP response code is %d, not 200", httpURLConnection.getResponseCode()));
        // }

        //获取内容
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, UTF8));
        final StringBuffer stringBuffer = new StringBuffer();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }
        String resp = stringBuffer.toString();
        if (stringBuffer != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // if (httpURLConnection!=null) {
        //     httpURLConnection.disconnect();
        // }
        System.out.println("res:" + resp);
        return resp;
    }
    public static String httpGet(String url, String xx) throws Exception {
        System.out.println("get url:" + url);
        String UTF8 = "UTF-8";
        URL httpUrl = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
        // httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setConnectTimeout(36000);
        httpURLConnection.setReadTimeout(15000);
        httpURLConnection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
        httpURLConnection.setRequestProperty("accept","application/json");
//        httpURLConnection.setRequestProperty("authorization","bearer "+xx);
//        System.out.println("authorization是："+"bearer "+xx);
        httpURLConnection.connect();
        //OutputStream outputStream = httpURLConnection.getOutputStream();
        //outputStream.write(reqBody.getBytes(UTF8));
        //  outputStream.flush();
        //  outputStream.close();

        //获取内容
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, UTF8));
        final StringBuffer stringBuffer = new StringBuffer();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }
        String resp = stringBuffer.toString();
        if (stringBuffer != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        if (outputStream != null) {
//            try {
//                outputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        // if (httpURLConnection!=null) {
        //     httpURLConnection.disconnect();
        // }
        System.out.println("res:" + resp);
        return resp;
    }
   /* public static void main(String[] args) {
        String url ="http://47.110.127.110:8080/api/v1/auth/login";
        Map<String,Object> map=new HashMap<>();
        map.put("captcha","string");
        map.put("password","888888");
        map.put("username","zfjs2022");
        map.put("uuid","string");

        JdbcTemplate jdbcTemplate = JDBCUtil.getJdbcTemplate();

        try {
            String s = httpPost(url, map, null);
            JSONObject jsonObject = JSON.parseObject(s);
            String data = jsonObject.getString("data");
            JSONObject object = JSON.parseObject(data);
            System.out.println(object);
            LoginUP login = new LoginUP();
            login.setJwt(object.get("jwt").toString());
            login.setExpireAt(object.get("expireAt").toString());
            login.setRole(object.get("role").toString());
            jdbcTemplate.update("insert into t_login values(?,?,?)",login.getJwt(),login.getExpireAt(),login.getRole());

        } catch (Exception e) {
            e.printStackTrace();
        }
        List< Map<String,Object>> list = new ArrayList<>();
        list.add(map);

        for (Iterator<Map<String,Object>> iterator= list.iterator();iterator.hasNext();){
            Map<String,Object> map1=iterator.next();
            System.out.println(map1);
        }



    }*/

}
