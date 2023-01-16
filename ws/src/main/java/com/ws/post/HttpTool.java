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
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.*;
import java.util.*;

/**|
 * Http接口工具类
 */
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
            LogUtil.info("MobilpriseActivity",strber.toString());
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
