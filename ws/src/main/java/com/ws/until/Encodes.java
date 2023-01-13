package com.ws.until;

import org.apache.commons.codec.binary.Hex;

public class Encodes {
    /**
     * Hex编码.
     */
    public static String encodeHex(byte[] input) {
        return Hex.encodeHexString(input);
    }
}
