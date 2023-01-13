package com.ws.post;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import java.net.URL;
import java.net.HttpURLConnection;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.*;
import java.util.*;

public class HttpTool {
    public static JSONObject post(String url, JSONObject json, Map<String, String> headers) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        if (headers != null) {
            Set<String> keys = headers.keySet();

            for (Iterator<String> i = keys.iterator(); i.hasNext(); ) {
                String key = i.next();
                post.addHeader(key, headers.get(key));
            }
        }

        try {
            json = new JSONObject();
            json.put("email", "test@1231.com");
            json.put("password", "123");
            StringEntity s = new StringEntity(json.toString(), "UTF-8");
            s.setContentEncoding("HTTP.UTF_8");
            s.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(s);

            HttpResponse httpResponse = client.execute(post);
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();
//          Log.i("MobilpriseActivity", strber.toString());
//            LogUtil.info("MobilpriseActivity",strber.toString());
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = httpResponse.getEntity();
                String charset = EntityUtils.getContentCharSet(entity);
//                response = new JSONObject(new JSONTokener(new InputStreamReader(entity.getContent(),charset)));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }
    public static String httpGet(String url, String xx) throws Exception {
        System.out.println("get url:" + url);
        String UTF8 = "UTF-8";
        URL httpUrl = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
        // httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("GET");
//        httpURLConnection.setConnectTimeout(36000);
//        httpURLConnection.setReadTimeout(15000);
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
    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        String url = "localhost:8080/save";
        Map map = new HashMap();
        map.put("appkey","123");
//        map.put()
        String sql = "select * from t_his_backup";
        JdbcTemplate jdbcTemplate = null;
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> stringObjectMap : maps) {

            Iterator<Map.Entry<String, Object>> iterator = stringObjectMap.entrySet().iterator();
            boolean b = iterator.hasNext();
            while(b){
                Map.Entry<String, Object> next = iterator.next();
                jsonObject.put(next.getKey(),next.getValue());
            }
        }
        post(url,jsonObject,map);

    }
}
