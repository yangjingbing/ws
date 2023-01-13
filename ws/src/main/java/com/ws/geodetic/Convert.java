package com.ws.geodetic;

/**
 * @author isHello
 */
public class Convert {
    //    private  double agle = 40.44707465D;
//    private  double agle = 337D;
//    private  double Lon = 121.055346D;
//    private  double Lat = 32.545068D;
    protected double agle = 40.44707465D;
    protected double Lon = 119.45666845205D;
    protected double Lat = 34.6225146838557D;
//    private  double agle = 337D;
//    private  double Lon = 121.058924D;
//    private  double Lat = 32.546257D;
//    private  double Lat = 34.10338601214847D;
//    private  double Lon = 118.3554230879845D;

    public Convert() {
    }

    public Point Lonlat2local(Point pointInt) {
        double angle = Util.Deg2dian(this.agle);
        Point Lonlat = new Point(this.Lon, this.Lat, pointInt.get(2));
        Point origin = this.Lonlat2xy(Lonlat, this.Lon);
        Point local = this.Lonlat2xy(pointInt, this.Lon);
        double dx = local.get(0) - origin.get(0);
        double dy = local.get(1) - origin.get(1);
        double x = Math.cos(-angle) * dx - Math.sin(-angle) * dy;
        double y = Math.sin(-angle) * dx + Math.cos(-angle) * dy;
        Point pointOutPut = new Point(x, y, pointInt.get(2));
        return pointOutPut;
    }
    public Point Lonlat2xy(Point pointInt, double center) {
        Point pointOutPut = new Point();
        double L = Util.Deg2dian(pointInt.get(0));
        double B = Util.Deg2dian(pointInt.get(1));
        double dCL = Util.Deg2dian(center);
        L -= dCL;
        double sinB = Math.sin(B);
        double cosB = Math.cos(B);
        double a = 6378137.0D;
        double e = Math.sqrt(0.0066943799013D);
        double e2 = Math.pow(e, 2.0D);
        double e4 = Math.pow(e, 4.0D);
        double e6 = Math.pow(e, 6.0D);
        double e8 = Math.pow(e, 8.0D);
        double e10 = Math.pow(e, 10.0D);
        double t = Math.tan(B);
        double m0 = L * cosB;
        double N = a / Math.sqrt(1.0D - e2 * Math.pow(sinB, 2.0D));
        double n2 = e2 * Math.pow(cosB, 2.0D) / (1.0D - e2);
        double A0 = 1.0D + 3.0D * e2 / 4.0D;
        A0 += 45.0D * e4 / 64.0D;
        A0 += 175.0D * e6 / 256.0D;
        A0 += 11025.0D * e8 / 16384.0D;
        A0 += 43659.0D * e10 / 65536.0D;
        double B0 = 3.0D * e2 / 4.0D + 15.0D * e4 / 16.0D + 525.0D * e6 / 512.0D;
        B0 = B0 + 2205.0D * e8 / 2048.0D + 72765.0D * e10 / 65536.0D;
        double C0 = 15.0D * e4 / 64.0D + 105.0D * e6 / 256.0D + 2205.0D * e8 / 4096.0D;
        C0 += 10395.0D * e10 / 16384.0D;
        double D0 = 35.0D * Math.pow(e2, 3.0D) / 512.0D + 315.0D * Math.pow(e2, 4.0D) / 2048.0D + 31185.0D * Math.pow(e2, 5.0D) / 131072.0D;
        double E0 = 315.0D * e8 / 16384.0D + 3465.0D * e10 / 65536.0D;
        double Afer = A0 * (1.0D - e2);
        double Bda = -B0 * (1.0D - e2) / 2.0D;
        double Gma = C0 * (1.0D - e2) / 4.0D;
        double Dta = -D0 * (1.0D - e2) / 6.0D;
        double ema = E0 * (1.0D - e2) / 8.0D;
        double C10 = Afer * a;
        double C11 = (2.0D * Bda + 4.0D * Gma + 6.0D * Dta) * a;
        double C12 = -1.0D * (8.0D * Gma + 32.0D * Dta) * a;
        double C13 = (32.0D * Dta + 64.0D * ema) * a;
        double x = C10 * B + cosB * (C11 * sinB + C12 * Math.pow(sinB, 3.0D) + C13 * Math.pow(sinB, 5.0D));
        x = x + N * t * m0 * m0 / 2.0D + (5.0D - t * t + 9.0D * n2 + 4.0D * n2 * n2) * N * t * Math.pow(m0, 4.0D) / 24.0D;
        x += (61.0D - 58.0D * t * t + Math.pow(t, 4.0D) + 270.0D * n2 - 330.0D * n2 * t * t) * N * t * Math.pow(m0, 6.0D) / 720.0D;
        double y = N * m0 + (1.0D - t * t + n2) * N * m0 * m0 * m0 / 6.0D;
        y += (5.0D - 18.0D * t * t + Math.pow(t, 4.0D) + 14.0D * n2 - 58.0D * n2 * t * t) * N * Math.pow(m0, 6.0D) / 120.0D;
        pointOutPut.set(1, x);
        pointOutPut.set(0, y);
        pointOutPut.set(2, pointInt.get(2));
        return pointOutPut;
    }

    public Point local2Lonlat(Point pointInt) {
        double angle = Util.Deg2dian(this.agle);
        double dx = Math.cos(angle) * pointInt.get(0) - Math.sin(angle) * pointInt.get(1);
        double dy = Math.sin(angle) * pointInt.get(0) + Math.cos(angle) * pointInt.get(1);
        Point Lonlat = new Point(this.Lon, this.Lat, pointInt.get(2));
        Point origin = this.Lonlat2xy(Lonlat, this.Lon);
        origin.set(0, origin.get(0) + dx);
        origin.set(1, origin.get(1) + dy);
        Point pointOutPut = this.xy2Lonlat(origin, this.Lon);
        return pointOutPut;
    }



    public Point xy2Lonlat(Point pointInt, double center) {
        Point pointOutPut = new Point();
        double a = 6378137.0D;
        double e = Math.sqrt(0.0066943799013D);
        double ee = Math.pow(e, 2.0D);
        double AP = 1.0D + 0.75D * ee + 0.703125D * ee * ee + 0.68359375D * ee * ee * ee + 0.67291259765625D * ee * ee * ee * ee;
        double BP = 0.75D * ee + 0.9375D * ee * ee + 1.025390625D * ee * ee * ee + 1.07666015625D * ee * ee * ee * ee;
        double CP = 0.234375D * ee * ee + 0.41015625D * ee * ee * ee + 0.538330078125D * ee * ee * ee * ee;
        double DP = 0.068359375D * ee * ee * ee + 0.15380859375D * ee * ee * ee * ee;
        double EP = 0.01922607421875D * ee * ee * ee * ee;
        double A2 = BP / (2.0D * AP);
        double A4 = -CP / (4.0D * AP);
        double A6 = DP / (6.0D * AP);
        double A8 = -EP / (8.0D * AP);
        double B2 = A2 - A2 * A4 - 0.5D * A2 * A2 * A2 - A2 * A4 * A4 + 0.5D * A2 * A2 * A6 - 18.3D * A2 * A2 * A2 * A4;
        double B4 = A4 + A2 * A2 - 2.0D * A2 * A6 - 4.0D * A2 * A2 * A4 - 1.3D * A2 * A2 * A2 * A2;
        double B6 = A6 + 3.0D * A2 * A4 - 3.0D * A2 * A8 + 1.5D * A2 * A2 * A2 - 4.5D * A2 * A4 * A4 - 9.0D * A2 * A2 * A6 - 12.5D * A2 * A2 * A2 * A4;
        double B8 = A8 + 2.0D * A4 * A4 + 4.0D * A2 * A6 + 8.0D * A2 * A2 * A4 + 2.7D * A4 * A4 * A4 * A4;
        double Lb0 = pointInt.get(1) / (a * (1.0D - ee) * AP);
        double Bf = Lb0 + B2 * Math.sin(2.0D * Lb0) + B4 * Math.sin(4.0D * Lb0) + B6 * Math.sin(6.0D * Lb0) + B8 * Math.sin(8.0D * Lb0);
        double sinBf = Math.sin(Bf);
        double cosBf = Math.cos(Bf);
        double tf = Math.tan(Bf);
        double nf2 = ee * Math.pow(Math.cos(Bf), 2.0D) / (1.0D - ee);
        double Vf2 = 1.0D + nf2;
        double Nf = a / Math.sqrt(1.0D - ee * Math.pow(Math.sin(Bf), 2.0D));
        double yN = pointInt.get(0) / Nf;
        double temp = -1.0D * Vf2 * tf * yN * yN / 2.0D;
        temp += (5.0D + 3.0D * tf * tf + nf2 - 9.0D * nf2 * tf * tf) * Vf2 * tf * Math.pow(yN, 4.0D) / 24.0D;
        temp -= (61.0D + 90.0D * tf * tf + 45.0D * Math.pow(tf, 4.0D)) * Vf2 * tf * Math.pow(yN, 6.0D) / 720.0D;
        double B = Bf + temp;
        temp = 1.0D / cosBf * yN;
        temp -= (1.0D + 2.0D * tf * tf + nf2) * Math.pow(yN, 3.0D) / 6.0D / cosBf;
        temp += (5.0D + 28.0D * tf * tf + 24.0D * Math.pow(tf, 4.0D) + 6.0D * nf2 + 8.0D * nf2 * tf * tf) * Math.pow(yN, 5.0D) / 120.0D / cosBf;
        double dCL = Util.Deg2dian(center);
        pointOutPut.set(1, Util.dian2Deg(B));
        pointOutPut.set(0, Util.dian2Deg(temp + dCL));
        pointOutPut.set(2, pointInt.get(2));
        return pointOutPut;
    }

    public static void main(String[] arg) {
        Convert tf = new Convert();
        Point local = new Point(175.35088815319057D, 110.31156499154224D, 0.0D);
        Point Lonlat = tf.local2Lonlat(local);
        System.out.print("x:" + String.valueOf(Lonlat.get(0)) + "\n" + "y:" + Lonlat.get(1) + "\n" + "z:" + Lonlat.get(2) + "\n");
        Point local1 = tf.Lonlat2local(Lonlat);
        System.out.print("x:" + String.valueOf(local1.get(0)) + "\n" + "y:" + local1.get(1) + "\n" + "z:" + local1.get(2) + "\n");
    }
}
