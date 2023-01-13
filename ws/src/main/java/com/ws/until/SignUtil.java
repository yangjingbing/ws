package com.ws.until;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * appKey+appSecret+timeStamp生成唯一标识编码工具类
 */

@Component
public class SignUtil {

    //String appKey = "123";// 江苏淮安工业园区主软件系统分配的公用参数- appKey
//    String appSecret = "111";// 江苏淮安工业园区主软件系统分配的公用参数- appSecret
//    String timestamp = "1591239905133";// 公用参数-当前系统的时间戳字符串

    public static String getSign(String appKey, String appSecret, String timestamp){
        // 第一步：将目前需要传递的公共参数值初始化入Hashmap内
        Map<String,String> map = new HashMap<String,String>();
        map.put("appKey",appKey);
        map.put("timestamp",timestamp);

        // 第二步：把字典按Key的字母顺序排序
        Map<String,String> sortMap = new TreeMap<String,String>(map);
        Set<Map.Entry<String, String>> sortEntry = sortMap.entrySet();

        // 第三步：把所有参数名和参数值串在一起
        StringBuffer sb = new StringBuffer();
        sb.append(appSecret);
        for (Map.Entry<String, String> entry : sortEntry) {
           if((!" ".equals(entry.getKey()) || entry.getKey() != null) && (!" ".equals(entry.getValue()) || entry.getValue() != null)){
               sb.append(entry.getKey()).append(entry.getValue());
           }
        }
        sb.append(appSecret);

        // 第四步：使用sha1加密,最终生成sign参数值
        String sign = Encodes.encodeHex(Digests.sha1(sb.toString().getBytes())).toUpperCase();
        return sign;
    }

    public static void main(String[] args) {
        String appKey = "123";
        String appSecret = "111";
        String timestamp = "1591239905133";
        String sign = SignUtil.getSign(appKey, appSecret, timestamp);
        System.out.println(sign);
    }
}
