package com.ws.until;

import com.ws.entity.XTZS;
import com.ws.mapper.CommonMapper;
import com.ws.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
/**
 * 通过读取数据库的值来进行经纬度的转换，在初期核准经纬度时使用
 */
/*
@Component
public class ConvertUtil {
//	经纬度转换为平面坐标系中的x,y 利用米勒坐标系
//	:param lon: 经度
//	:param lat: 维度
//	"""

//"
//	将平面坐标系中的x,y转换为经纬度，利用米勒坐标系
//	:param x: x轴
//	:param y: y轴
//	:return:
//	"""

    private static double[] millerToXY(double lon, double lat) {
        double L = 6381372 * Math.PI * 2;// 地球周长
        double W = L;// 平面展开后，x轴等于周长
        double H = L / 2;// y轴约等于周长一半
        double mill = 2.3;// 米勒投影中的一个常数，范围大约在正负2.3之间
        double x = lon * Math.PI / 180;// 将经度从度数转换为弧度
        double y = lat * Math.PI / 180;// 将纬度从度数转换为弧度
        y = 1.25 * Math.log(Math.tan(0.25 * Math.PI + 0.4 * y));// 米勒投影的转换
        // 弧度转为实际距离
        x = (W / 2) + (W / (2 * Math.PI)) * x;
        y = (H / 2) - (H / (2 * mill)) * y;
        double[] result = new double[2];
        result[0] = x;
        result[1] = y;
        return result;
    }

    public static double[] millerToLonLat(double x, double y) {
        double L = 6381372 * Math.PI * 2;
        double W = L;
        double H = L / 2;
        double mill = 2.3;
        double lat = ((H / 2 - y) * 2 * mill) / (1.25 * H);
        lat = ((Math.atan(Math.exp(lat)) - 0.25 * Math.PI) * 180) / (0.4 * Math.PI);
        double lon = (x - W / 2) * 360 / W;
        // TODO 最终需要确认经纬度保留小数点后几位
        double lonlat_coordinate[] = new double[] { 0, 0 };
        lonlat_coordinate[0] =
Double.parseDouble(String.format("%.6f",lon));
SplitAndRound(lon,6);
        lonlat_coordinate[1] =
Double.parseDouble(String.format("%.6f",lat));
SplitAndRound(lat,6);

        return lonlat_coordinate;
    }

    public static double SplitAndRound(double a, int n) {
        a = a * Math.pow(10, n);
        return (Math.round(a)) / (Math.pow(10, n));
    }

    public double TransDegrees(double degree, double minu) {
        return degree + minu / 60.0;
    }

    //	"""
//	平面坐标系旋转
//	:param x: x轴
//	:param y: y轴
//	:param angel: 逆时针旋转的角度(单位°)
//	:return:新 xy坐标
//	"""
    public static double[] Newxy(double x, double y, double angle) {
        double radians = Math.toRadians(angle);
        double x_rotate = x * Math.cos(radians) + y * Math.sin(radians);
        double y_rotate = y * Math.cos(radians) - x * Math.sin(radians);
        return new double[] { x_rotate, y_rotate };
    }

    public static double[] get_k_b(double[] xy1, double[] lonlat1, double[] xy2, double[] lonlat2, double angle)
    {
        double[] point1_rotate = Newxy(xy1[0], xy1[1], angle);
        double[] point2_rotate = Newxy(xy2[0], xy2[1], angle);
        // #print("point1_rotate :", point1_rotate)
        // #print("point2_rotate :", point2_rotate)
        double[] xy1_miller = millerToXY(lonlat1[0], lonlat1[1]);
        double[] xy2_miller = millerToXY(lonlat2[0], lonlat2[1]);
        // print("point1_miller:",xy1_miller)
        // print("point2_miller:",xy2_miller)
        double kx = (xy2_miller[0] - xy1_miller[0]) / (point2_rotate[0] - point1_rotate[0]);
        // #print("kx:", kx)
        double ky = (xy2_miller[1] - xy1_miller[1]) / (point2_rotate[1] - point1_rotate[1]);
        // #print("ky:", ky)
        double bx = xy1_miller[0] - kx * point1_rotate[0];
        double by = xy1_miller[1] - ky * point1_rotate[1];
        // #print("bx, by:", bx, by)
        return new double[] { kx, bx, ky, by };
    }

//    @Resource
//    private CommonMapper commonMapper;
    public static double[] xy2lonlat(double x, double y,Map map) {

        double angle = (double) map.get("angle");
        double[] xy1 = (double[]) map.get("xy1");
        double[] xy2 = (double[]) map.get("xy2");
        double[] lonlat1 = (double[]) map.get("lonlat1");
        double[] lonlat2 = (double[]) map.get("lonlat2");
        double[] xy_rotate = Newxy(x, y, angle);
        double[] k = get_k_b(xy1, lonlat1, xy2, lonlat2, angle);

        // #print("xy_rotate:", xy_rotate)
        double[] xy_miller = new double[] { k[0] * xy_rotate[0] + k[1], k[2] * xy_rotate[1] + k[3] };
        // #print("miller_xy:", xy_miller)
        double[] lonlat_coordinate = millerToLonLat(xy_miller[0], xy_miller[1]);
        return lonlat_coordinate;
    }



//    @Autowired
//    private CommonService commonService;
//    public static void main(String[] args) {
//        double[] lanxy = xy2lonlat(1154.3039999999985,390.0479999999997);
//        double x = lanxy[0];
//        double y = lanxy[1];
//        System.out.println(x+"------"+y);
//    }
}*/
//huaxing38.14,34.75,996.86,331.54,116.3297145771,36.4776031120,116.3407576634,36.4791272992,7.4843053
//xinfa138.61,932.33,1084.11,270.07,116.1874972979,36.5882597401,116.1781700717,36.5955423105,0
//yanjv24.77,15.13,213.91,698.72,116.3408519036,36.4798916981,116.3439319358,36.4858127248,7.4843053
//package com.ws.until;


/**
 * 固定写死经纬度转换的值，在调试经纬度准确时使用
 */
@Component
public class ConvertUtil {
    /*private static double[] xy1 = {38.14,34.75};
    private static double[] xy2 = {996.86,331.54};
    private static double[] lonlat1 = {116.3297145771,36.4776031120};
    private static double[] latlon = {116.3407576634,36.4791272992};
    private static double angle = 7.4843053;


private static double[] xy1 = {239.93, -141.81};
    private static double[] xy2 = {-184.39, 105.55};
    private static double[] lonlat1 = {30.00904161804092, 91.06517151796983};
    private static double[] latlon = {30.011197631647402, 91.06019635328235};
    private static double angle =-90.5;

    private static double[] xy1 = {121.12806,73.79953};
    private static double[] xy2 = {1677.65590,808.36888};
    private static double[] lonlat1 = {118.863769,38.088071};
    private static double[] latlon = {118.872554,38.091320};
    private static double angle =0;*/


/**
     * 戴瑞克
     * 右下：204.04,-222.50
     * 右上：-265.91,303.34
     * 左上：229.21,290.61
     */


private static double[] xy1 = {253.36980039866276,-222.49999994659424};
    private static double[] xy2 = {-265.8049430265563,303.0180253468042};
    private static double[] lonlat1 = {118.459896,37.974265};
    private static double[] latlon = {118.454308,37.978632};
    private static double angle = -6;



/*private static double[] xy1 = { 121.12806, 73.79953 };
    private static double[] xy2 = { 1677.6559, 808.36888 };
    private static double[] lonlat1 = { 118.863769, 38.088071 };
    private static double[] latlon = { 118.872554, 38.09132 };
    private static double angle = 0.0;

    private static double[] xy1 = { 164.04, -157.95 };
    private static double[] xy2 = { -135.58, 202.80 };
    private static double[] lonlat1 = { 118.867636,37.423239 };
    private static double[] latlon = { 118.867745,37.427447 };
    private static double angle = -339.92;*/


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

    public static void main(String[] args) {

        double[] lanxy = xy2lonlat(-5.71, 25.99);
        double x = lanxy[0];
        double y = lanxy[1];
        System.out.println(x + "------" + y);
    }
//}
}


