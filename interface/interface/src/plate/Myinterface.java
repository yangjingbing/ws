package plate;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import redis.clients.jedis.Jedis;
import util.ConvertUtil;

public class Myinterface
{
	private static int platport = -1;
	private static InetAddress platip = null;
	private static long lasttime = System.currentTimeMillis();
	public static JdbcTemplate jdbcTemplate;
	private static HashMap sysmap = new HashMap();

	public static void sysinfo() {
		List xtsz = jdbcTemplate.queryForList("SELECT bm,sjs FROM t_wh_xtsz ");
		for (int i = 0; i < xtsz.size(); i++) {
			try {
				int mst = 0;

				Map m = (Map) xtsz.get(i);

				String str = (String) m.get("BM");

				sysmap.put(m.get("BM"), m.get("SJS"));
			} catch (Exception e) {
				e.printStackTrace();
			}
    }
		platport = Integer.valueOf(sysmap.get("testport").toString()).intValue();
		try
    {
			String ipstr = String.valueOf(sysmap.get("testipstr"));
			platip = InetAddress.getByName(ipstr);
    }
		catch (UnknownHostException e)
    {
			e.printStackTrace();
    }
	}

	private static void ParseJson(String jsonString) throws JSONException {
		JSONObject jObject = new JSONObject(jsonString);
		System.out.println(jObject.get("name"));
	}

	private static void CreateJson_alarm(DatagramSocket client, long lastt) throws JSONException {
		String listSql = "SELECT cardid, messagetype,dt  from  t_alarm  WHERE   dt>=?  AND  status=0 ";
		Date date1 = new Date(lastt);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date1);
		rightNow.add(13, -20);
		Date date = rightNow.getTime();
		String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		Timestamp tt = Timestamp.valueOf(nowTime);

		List<Map> result = jdbcTemplate.query(listSql, new Object[] { tt }, new RowMapper()
    {
			@Override
			public Map mapRow(ResultSet rs, int rowNum) throws SQLException {
				Map row = new LinkedHashMap();
        try
        {
					row.put("sn", Integer.valueOf(rs.getInt("cardid")));
					row.put("messagetype", Integer.valueOf(rs.getInt("messagetype")));
					long tt = rs.getTimestamp("dt").getTime();
					row.put("timestamp", Long.valueOf(tt));
					return row;
        }
				catch (Exception e2)
        {
					e2.printStackTrace();
        }
				return null;
			}
		});
		for (int i = 0; i < result.size(); i++)
    {
			LinkedHashMap mm = (LinkedHashMap) result.get(i);

			JSONObject jObject = new JSONObject();
			int mt = ((Integer) mm.get("messagetype")).intValue();
			if ((mt & 0x1) == 1) {
				jObject.put("MsgType", "PressKey");
			} else if ((mt & 0x4) != 0) {
				jObject.put("MsgType", "LowerBattry");
			} else if ((mt & 0x8) != 0) {
				jObject.put("MsgType", "Stoped");
			}
			mm.remove("messagetype");
			jObject.put("Content", mm);

			System.out.println(jObject.toString());

			byte[] buffer = jObject.toString().getBytes();
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, platip, platport);
			try {
				client.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
    }
	}

	private static synchronized void CreateJson_his(DatagramSocket client, long lastt) throws JSONException {
		String listSql = "SELECT a.card_sn, a.geo_x,a.geo_y,a.layer_id, a.dt,b.floor  from  t_his a LEFT JOIN t_dm_layer b ON a.layer_id = b.id where dt> DATE_ADD(NOW(),INTERVAL -5 SECOND) group by a.card_sn";
		Date date1 = new Date(lastt);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date1);
		rightNow.add(13, -2);
		Date date = rightNow.getTime();
		String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date1);
		Timestamp tt = Timestamp.valueOf(nowTime);

		List<Map> result = jdbcTemplate.query(listSql, new RowMapper()
    {
			@Override
			public Map mapRow(ResultSet rs, int rowNum) throws SQLException {
				Map row = new LinkedHashMap();
				try
        {
					row.put("sn", Integer.valueOf(rs.getInt("card_sn")));
					long tt = rs.getTimestamp("dt").getTime();
					row.put("timestamp", Long.valueOf(tt));
					double x = rs.getDouble("geo_x");
					double y = rs.getDouble("geo_y");
					ConvertUtil convertUtil = new ConvertUtil();
					double[] latxy = ConvertUtil.xy2lonlat(x, y);
					row.put("lon", Double.valueOf(latxy[0]));
					row.put("lat", Double.valueOf(latxy[1]));
					row.put("buildId", rs.getInt("layer_id"));
					row.put("floor", rs.getInt("floor"));
					return row;
        }
				catch (Exception e2)
        {
					e2.printStackTrace();
        }
				return null;
			}
		});
		for (int i = 0; i < result.size(); i++)
    {
			LinkedHashMap mm = (LinkedHashMap) result.get(i);

			JSONObject jObject = new JSONObject();
			jObject.put("MsgType", "Location");
			jObject.put("Content", mm);
			System.out.println("----------������������--------------" + jObject.toString());
			byte[] buffer = jObject.toString().getBytes();
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, platip, platport);
			try {
				client.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
    }
	}

	private static void CreateJson_lowPower(DatagramSocket client, long lastt) throws JSONException {
		String listSql = "SELECT cardID,dt from t_alarm WHERE  dt>=? and messageType = 4 and status=0";
		Date date1 = new Date(lastt);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date1);
		rightNow.add(13, -20);
		Date date = rightNow.getTime();
		String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		Timestamp tt = Timestamp.valueOf(nowTime);

		List<Map> result = jdbcTemplate.query(listSql, new Object[] { tt }, new RowMapper()
    {
			@Override
			public Map mapRow(ResultSet rs, int rowNum) throws SQLException {
				Map row = new LinkedHashMap();
        try
        {
					row.put("sn", Integer.valueOf(rs.getInt("cardId")));
					long tt = rs.getTimestamp("dt").getTime();
					row.put("timestamp", Long.valueOf(tt));
					return row;
        }
				catch (Exception e2)
        {
					e2.printStackTrace();
        }
				return null;
			}
		});
		for (int i = 0; i < result.size(); i++)
    {
			LinkedHashMap mm = (LinkedHashMap) result.get(i);
			JSONObject jObject = new JSONObject();
			jObject.put("MsgType", "LowPower");
			jObject.put("Content", mm);
			System.out.println("**********����������*****************" + jObject.toString());

			byte[] buffer = jObject.toString().getBytes();
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, platip, platport);
			try {
				client.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
    }
	}

	private static void CreateJson_CardPower(DatagramSocket client, long lastt) throws JSONException {
		String listSql = "SELECT cardID,v,MAX(dt) AS dt  FROM t_rssi WHERE  dt>=? group by cardId ORDER BY dt";
		Date date1 = new Date(lastt);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date1);
		rightNow.add(13, -20);
		Date date = rightNow.getTime();
		String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		Timestamp tt = Timestamp.valueOf(nowTime);

		List<Map> result = jdbcTemplate.query(listSql, new Object[] { tt }, new RowMapper()
    {
			@Override
			public Map mapRow(ResultSet rs, int rowNum) throws SQLException {
				Map row = new LinkedHashMap();
				try
        {
					row.put("sn", Integer.valueOf(rs.getInt("cardId")));
					long tt = rs.getTimestamp("dt").getTime();
					row.put("timestamp", Long.valueOf(tt));
					Integer v = Integer.valueOf(rs.getInt("v"));
					double V = v.intValue() / 10;
					if (V > 3.9D) {
						row.put("V", "100%");
					} else if ((V > 3.8D) && (V < 4.0D)) {
						row.put("V", "70%");
					} else if ((V > 3.7D) && (V < 3.9D)) {
						row.put("V", "50%");
					} else if (V < 3.7D) {
						row.put("V", "20%");
					}
					return row;
        }
				catch (Exception e2)
        {
					e2.printStackTrace();
        }
				return null;
			}
		});
		for (int i = 0; i < result.size(); i++)
    {
			LinkedHashMap mm = (LinkedHashMap) result.get(i);
			JSONObject jObject = new JSONObject();
			jObject.put("MsgType", "CardPower");
			jObject.put("Content", mm);
			System.out.println("=========��������������===============" + jObject.toString());
			byte[] buffer = jObject.toString().getBytes();
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, platip, platport);
			try {
				client.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
    }
	}

	private static void CreateJson_JZXX(DatagramSocket client, long lastt, final Jedis jedis) throws JSONException {
		String listSql = "SELECT station_id,geo_x,geo_y,now() as dt FROM t_jz_jbxx";
		Date date1 = new Date(lastt);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date1);
		rightNow.add(13, -20);

		Date date = rightNow.getTime();
		String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		Timestamp tt = Timestamp.valueOf(nowTime);

		List<Map> result = jdbcTemplate.query(listSql, new RowMapper()
    {
			@Override
			public Map mapRow(ResultSet rs, int rowNum) throws SQLException {
				Map row = new LinkedHashMap();
        try
        {
					row.put("stationId", Integer.valueOf(rs.getInt("station_id")));
					double x = rs.getDouble("geo_x");
					double y = rs.getDouble("geo_y");
					ConvertUtil convertUtil = new ConvertUtil();
					double[] latxy = ConvertUtil.xy2lonlat(x, y);
					row.put("lon", Double.valueOf(latxy[0]));
					row.put("lat", Double.valueOf(latxy[1]));
					long tt = rs.getTimestamp("dt").getTime();
					row.put("timestamp", Long.valueOf(tt));

					String v = jedis.get("sstatus-" + rs.getInt("station_id"));
					System.out.println(String.format("statusId:%d redisV:%s", rs.getInt("station_id"), v));
					if ((Objects.isNull(v)) || (Objects.equals("", v))) {
						row.put("status", "1");
					} else {
						row.put("status", "0");
					}
					return row;
        }
				catch (Exception e2)
        {
					e2.printStackTrace();
        }
				return null;
			}
		});
		for (int i = 0; i < result.size(); i++)
    {
			LinkedHashMap mm = (LinkedHashMap) result.get(i);
			JSONObject jObject = new JSONObject();
			jObject.put("MsgType", "JZXX");
			jObject.put("Content", mm);
			System.out.println("=========基站位置以及实时状态===============" + jObject.toString());
			byte[] buffer = jObject.toString().getBytes();
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, platip, platport);
			try {
				client.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
    }
	}

	public static void main(String[] args) {
		DatagramSocket client = null;
		DatagramPacket packet = null;
		byte[] data = null;

		ApplicationContext factory = new FileSystemXmlApplicationContext(".//applicationContext.xml");

		jdbcTemplate = (JdbcTemplate) factory.getBean("jdbcTemplate");

		jdbcTemplate.setSkipUndeclaredResults(true);
		sysinfo();

		Jedis jedis = new Jedis("127.0.0.1", 6379);
		jedis.select(13);
		System.out.println("redis连接成功");
		for (;;)
    {
			try {
				Thread.sleep(3000L);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			try {
				client = new DatagramSocket(20008);
        try
        {
					long lastt = lasttime;

					CreateJson_alarm(client, lastt);
					CreateJson_his(client, lastt);
					CreateJson_CardPower(client, lastt);
					CreateJson_JZXX(client, lastt, jedis);
        }
				catch (JSONException e2)
        {
					e2.printStackTrace();
        }
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
    }
	}
}