package com.ws.geodetic;

/**
 * @author isHello
 */
public class Util {
    static double PI = Math.acos(-1.0D);

    public Util() {
    }

    public static double Deg2dian(double degree) {
        return degree * PI / 180.0D;
    }

    public static double dian2Deg(double radian) {
        return radian * 180.0D / PI;
    }
}
