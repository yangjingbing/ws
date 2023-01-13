package com.ws.geodetic;

public class ConUtil {
    public static Double[]  ConUtil(Double x, Double y){
        Double L = 6381372 * Math.PI * 2;
        Double W = L;
        Double H = L /2;
        Double mill = 2.3;
        y = 1.25 * Math.log(Math.tan(0.25 * Math.PI + 0.4 * y));
        Double x1 = (x - (W /2) ) /(W / (2* Math.PI));
        double y1 = ((H/2) - y) /(H / (2* mill));
        Double lon = x1 * 180 / Math.PI;
        Double lat = y1 * 180 /Math.PI;
        Double [] s = {lon,lat};
        return s;
    }

    public static void main(String[] args) {
        Double[] a = {70.50459*0.23/1000,72.38887*0.23/1000};
        Double[] num = ConUtil(a[0],a[1]);
        for (int i = 0; i < num.length; i++) {
            System.out.println(num[i]);
        }
    }
}

