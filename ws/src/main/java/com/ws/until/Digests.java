package com.ws.until;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;

/**
 * 加密算法工具类
 */
public class Digests {
    private static final String SHA1 = "SHA-1";
    private static final String MD5 = "MD5";


    /**
     * 对输入字符串进行sha1散列.
     */
    public static byte[] sha1(byte[] input) {
        return digest(input, SHA1, null, 1);
    }

    /**
     * 对输入字符串进行MD5散列
     */
    public static byte[] md5(byte[] input) {
        return digest(input, MD5, null, 1);
    }

    /**
     * 对字符串进行散列, 支持md5与sha1算法.
     */
    private static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);

            if (salt != null) {
                digest.update(salt);
            }

            byte[] result = digest.digest(input);

            for (int i = 1; i < iterations; i++) {
                digest.reset();
                result = digest.digest(result);
            }
            return result;
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

}
