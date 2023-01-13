package com.cp.util;

public class ConvertUtil {
    private static double[] xy1 = {259.32087, 1060.84037};
    private static double[] xy2 = {1784.44087, 19.56037};
    private static double[] lonlat1 = {119.0441565, 33.39800634};
    private static double[] latlon = {119.0459586, 33.3964018};
    private static double angle = 0.751;


    private static double[] millerToXY(double lon, double lat) {
        double L = 4.009534279004721E7D;
        double W = L;
        double H = L / 2.0D;
        double mill = 2.3D;
        double x = lon * 3.141592653589793D / 180.0D;
        double y = lat * 3.141592653589793D / 180.0D;
        y = 1.25D * Math.log(Math.tan(0.7853981633974483D + 0.4D * y));

        x = W / 2.0D + W / 6.283185307179586D * x;
        y = H / 2.0D - H / (2.0D * mill) * y;
        double[] result = new double[2];
        result[0] = x;
        result[1] = y;
        return result;
    }

    public static double[] millerToLonLat(double x, double y) {
        double L = 4.009534279004721E7D;
        double W = L;
        double H = L / 2.0D;
        double mill = 2.3D;
        double lat = (H / 2.0D - y) * 2.0D * mill / (1.25D * H);
        lat = (Math.atan(Math.exp(lat)) - 0.7853981633974483D) * 180.0D / 1.2566370614359172D;
        double lon = (x - W / 2.0D) * 360.0D / W;

        double[] lonlat_coordinate = {0.0D, 0.0D};
        lonlat_coordinate[0] = SplitAndRound(lon, 6);
        lonlat_coordinate[1] = SplitAndRound(lat, 6);

        return lonlat_coordinate;
    }

    public static double SplitAndRound(double a, int n) {
        a *= Math.pow(10.0D, n);
        return Math.round(a) / Math.pow(10.0D, n);
    }

    public double TransDegrees(double degree, double minu) {
        return degree + minu / 60.0D;
    }

    public static double[] Newxy(double x, double y, double angle) {
        double radians = Math.toRadians(angle);
        double x_rotate = x * Math.cos(radians) + y * Math.sin(radians);
        double y_rotate = y * Math.cos(radians) - x * Math.sin(radians);
        return new double[]{x_rotate, y_rotate};
    }

    public static double[] get_k_b(double[] xy1, double[] lonlat1, double[] xy2, double[] latlon, double angle) {
        double[] point1_rotate = Newxy(xy1[0], xy1[1], angle);
        double[] point2_rotate = Newxy(xy2[0], xy2[1], angle);

        double[] xy1_miller = millerToXY(lonlat1[0], lonlat1[1]);
        double[] xy2_miller = millerToXY(latlon[0], latlon[1]);

        double kx = (xy2_miller[0] - xy1_miller[0]) / (point2_rotate[0] - point1_rotate[0]);

        double ky = (xy2_miller[1] - xy1_miller[1]) / (point2_rotate[1] - point1_rotate[1]);

        double bx = xy1_miller[0] - kx * point1_rotate[0];
        double by = xy1_miller[1] - ky * point1_rotate[1];

        return new double[]{kx, bx, ky, by};
    }

    public static double[] xy2lonlat(double x, double y) {
        double[] xy_rotate = Newxy(x, y, angle);
        double[] k = get_k_b(xy1, lonlat1, xy2, latlon, angle);

        double[] xy_miller = {k[0] * xy_rotate[0] + k[1], k[2] * xy_rotate[1] + k[3]};

        double[] lonlat_coordinate = millerToLonLat(xy_miller[0], xy_miller[1]);
        return lonlat_coordinate;
    }
}
