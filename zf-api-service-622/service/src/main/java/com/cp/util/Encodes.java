package com.cp.util;


import org.apache.commons.codec.binary.Hex;

/**
 * 封装各种格式的编码解码工具类.<br>
 * 
 * 1.Commons-Codec的 hex/base64 编码<br>
 * 2.Commons-Lang的xml/html escape<br>
 * 3.JDK提供的URLEncoder
 * 
 */
public class Encodes {

    /**
     * Hex编码.
     */
    public static String encodeHex(byte[] input) {
        return Hex.encodeHexString(input);
    }
}
