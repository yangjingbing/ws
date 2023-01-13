package com.cp.zf.util;

import com.alibaba.fastjson.JSON;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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

    public static String httpPost2(String url, Map<String, String> map, String cookie) throws IOException {
        System.out.println("post url:" + url + " body:" + map);
        //获取请求连接
        Connection con = Jsoup.connect(url).ignoreContentType(true);
        //遍历生成参数
        if (map != null) {
            //application/x-www-form-urlencoded 方式
            for (Map.Entry<String, String> entry : map.entrySet()) {
                //添加参数
                con.data(entry.getKey(), entry.getValue());
            }
            con.requestBody(JSON.toJSONString(map));
        }
        if (Objects.isNull(cookie)) {
            con.header("Cookie", cookie);
        }
        con.header("Content-Type", "application/json;charset=UTF-8");

        Document doc = con.post();
        System.out.println("res:" + doc);
        return doc.toString();
    }

    public static String httpPost(String url, Map<String, Object> reqData, String xx) throws Exception {
        System.out.println("post url:" + url + " body:" + reqData);

        String UTF8 = "UTF-8";
        String reqBody = JSON.toJSONString(reqData);
        URL httpUrl = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
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

    public static void main(String[] args) {
        String url ="http://127.0.0.1:39898/api/v1/csjj/payment";
        Map<String,Object> map=new HashMap<>();
        map.put("type","1");
        map.put("userId","1");
        map.put("orderType","1");
        map.put("totalAmount","1");
        map.put("subject","订单标题");
        map.put("body","订单描述");

        Map<String,String> extrasMap = new HashMap<>();
        extrasMap.put("yecount","23");
        try {
        //    map.put("extras",extrasMap);
        //    httpPost(url,map,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List< Map<String,Object>> list = new ArrayList<>();
        list.add(map);

        for (Iterator<Map<String,Object>> iterator= list.iterator();iterator.hasNext();){
            Map<String,Object> map1=iterator.next();
            System.out.println(map1);
        }



    }

}
