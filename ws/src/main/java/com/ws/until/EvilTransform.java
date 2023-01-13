package com.ws.until;

public class EvilTransform {

	private static double pi = 3.14159265358979324;
	private static double a = 6378245.0;
	private static double ee = 0.00669342162296594323;

	/**
	 * WGS-84 到 GCJ-02 的转换（即 GPS 加偏）
	 * @param wgLon
	 * @param wgLat
	 * @return
	 */
	public static double[] transform(double wgLon,double wgLat ) {

	  double mgLat;
	  double mgLon;

	  if (outOfChina(wgLat, wgLon)) {
	   mgLat = wgLat;
	   mgLon = wgLon;
	   return null;
	  }
	  double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
	  double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
	  double radLat = wgLat / 180.0 * pi;
	  double magic = Math.sin(radLat);
	  magic = 1 - ee * magic * magic;
	  double sqrtMagic = Math.sqrt(magic);
	  
	  dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
	  dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
	  
	  mgLat = wgLat + dLat;
	  mgLon = wgLon + dLon;
	  
	  return new double[]{mgLon,mgLat};
	  
	 }

	/**
	 * 判断是否在国内区域
	 * @param lat
	 * @param lon
	 * @return
	 */
	public static boolean outOfChina(double lat, double lon) {
	  if (lon < 72.004 || lon > 137.8347)
	   return true;
	  if (lat < 0.8293 || lat > 55.8271)
	   return true;
	  return false;
	 }

	/**
	 * 纬度转换
	 * @param x
	 * @param y
	 * @return
	 */
	public static double transformLat(double x, double y) {
	  double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
	    + 0.2 * Math.sqrt(Math.abs(x));
	  ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
	  ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
	  ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
	  return ret;
	 }

	/**
	 * 经度转换
	 * @param x
	 * @param y
	 * @return
	 */
	public static double transformLon(double x, double y) {
	 double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
	   * Math.sqrt(Math.abs(x));
	 ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
	 ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
	 ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0
	   * pi)) * 2.0 / 3.0;
	 return ret;
	}
	/**
	 * WGS84转地心坐标系
	 **/
	public static double[] WGS84toECEF(double latitude, double longitude, double height) {
		double X;
		double Y;
		double Z;
		double a = 6378137.0;
		double b = 6356752.31424518;
		double E = (a * a - b * b) / (a * a);
		double COSLAT = Math.cos(latitude * Math.PI / 180);
		double SINLAT = Math.sin(latitude * Math.PI / 180);
		double COSLONG = Math.cos(longitude * Math.PI / 180);
		double SINLONG = Math.sin(longitude * Math.PI / 180);
		double N = a / (Math.sqrt(1 - E * SINLAT * SINLAT));
		double NH = N + height;
		X = NH * COSLAT * COSLONG;
		Y = NH * COSLAT * SINLONG;
		Z = (b * b * N / (a * a) + height) * SINLAT;
		return new double[] { X, Y, Z };
	}

	/**
	 * 地心坐标系转WGS84
	 */
	public static String ECEFtoWGS84(double x, double y, double z){
		double a, b, c, d;
		double Longitude;// 经度
		double Latitude;// 纬度
		double Altitude;// 海拔高度
		double p, q;
		double N;
		a = 6378137.0;
		b = 6356752.31424518;
		c = Math.sqrt(((a * a) - (b * b)) / (a * a));
		d = Math.sqrt(((a * a) - (b * b)) / (b * b));
		p = Math.sqrt((x * x) + (y * y));
		q = Math.atan2((z * a), (p * b));
		Longitude = Math.atan2(y, x);
		Latitude = Math.atan2((z + (d * d) * b * Math.pow(Math.sin(q), 3)),
				(p - (c * c) * a * Math.pow(Math.cos(q), 3)));
		N = a / Math.sqrt(1 - ((c * c) * Math.pow(Math.sin(Latitude), 2)));
		Altitude = (p / Math.cos(Latitude)) - N;
		Longitude = Longitude * 180.0 / Math.PI;
		Latitude = Latitude * 180.0 / Math.PI;
		return Longitude + "," + Latitude + "," + Altitude;
	}

	public static void main(String[] args) {
		double x = 118.3540012753763;
		double y = 34.10204006918888;
		double s[];
		s = transform(x,y);
//		for (int i = 0; i < s.length; i++) {
			System.out.println(s[0]);
		System.out.println(s[1]);
//		}
	}
}
