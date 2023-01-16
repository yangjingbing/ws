package com.ws;

/**
 * 测试用例
 */

import com.ws.until.SignUtil;

public class TestSignUtil {
    public static void main(String[] args) {
        String sign = SignUtil.getSign("123", "456", "1620568500000");
        System.out.println(sign);
    }
}
