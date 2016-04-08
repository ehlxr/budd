package osc.git.eh3.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;
import osc.git.eh3.redis.JedisUtil;

public class TestJdbc {
	private static Connection getConn() {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://192.168.3.11:3306/wins-dsp-new?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
		String username = "root";
		String password = "pxene";
		Connection conn = null;
		try {
			Class.forName(driver); // classLoader,加载对应驱动
			conn = (Connection) DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	private static Integer getAll() {
		Connection conn = getConn();
		String sql = "SELECT v.* FROM dsp_v_app_motionclick_day_count v WHERE  v.time BETWEEN 1433088000000 AND 1453046400000 ";
		// String sql = "SELECT * FROM dsp_t_ad_group_adx_creative WHERE groupid
		// = 'd092c630-abfd-45a1-92f3-d0530c1caee8' LIMIT 1,3;";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			while (rs.next()) {
				JSONObject jsonObject = new JSONObject();
				String time = "";
				String mapid = "";
				String appid = "";
				String adxtype = "";
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					String columnName = metaData.getColumnName(i);
					if ("time".equals(columnName)) {
						time = rs.getString(i);
					}
					if ("mapid".equals(columnName)) {
						mapid = rs.getString(i);
					}
					if ("appid".equals(columnName)) {
						appid = rs.getString(i);
					}
					if ("adxtype".equals(columnName)) {
						adxtype = rs.getString(i);
					}

					jsonObject.put(columnName, rs.getString(i));
				}
				Map<String, String> map = new HashMap<>();
				map.put(time + "_" + appid + "_" + adxtype, jsonObject.toString());
				JedisUtil.hset("HistoryAPPData_" + mapid, map);

				// JedisUtil.lpush("HistoryAPPData_"+mapid+"_"+time,
				// jsonObject.toString());
				System.out.println("HistoryAPPData_" + mapid);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		
		insertData();
		
		// getAll();
//		 JedisUtil.deleteByPattern("HistoryAPPData_*");
		/*
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date start = new Date();
		
		List<String> mapids = getMapid();
		// String[] keys = JedisUtil.getKeys("HistoryAPPData_*");
		List<String> alldata = new ArrayList<String>();
		for (String mapid : mapids) {
			String key = "HistoryAPPData_" + mapid;
			String[] hkeys = JedisUtil.hkeys(key);
//			for (String hkey : hkeys) {
//				System.out.println(JedisUtil.hget(key, hkey));
//			}
			if(hkeys.length>0){
				List<String> hmget = JedisUtil.hmget(key, hkeys);
				alldata.addAll(hmget);
			}
		}
		System.out.println(alldata.size());
		Date end = new Date();
		System.out.println(sdf.format(start));
		System.out.println(sdf.format(end));
		*/
	}

	private static List<String> getMapid() {
		List<String> mapids = new ArrayList<String>();
		Connection conn = getConn();
		String sql = "SELECT t2.id AS mapid FROM dsp_t_ad_group_creative t2 LEFT JOIN dsp_t_ad_group t3 ON t2.groupid = t3.id LEFT JOIN dsp_t_campaign t4 ON t3.campaignid = t4.id LEFT JOIN dsp_t_advertiser_account t5 ON t4.accountid = t5.id LEFT JOIN dsp_t_advertiser t6 ON t5.advertiserid = t6.id WHERE ( t4.accountid IN ( SELECT id FROM dsp_t_advertiser_account t6 WHERE t6.advertiserid IN ( SELECT id FROM dsp_t_advertiser t7 WHERE t7.parentid = 'dfecbd8a-2d7e-4941-bd89-e39c576c5ee5' ) ) OR t4.accountid = 'dfecbd8a-2d7e-4941-bd89-e39c576c5ee5' )";
		// String sql = "SELECT * FROM dsp_t_ad_group_adx_creative WHERE groupid
		// = 'd092c630-abfd-45a1-92f3-d0530c1caee8' LIMIT 1,3;";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			while (rs.next()) {
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					mapids.add(rs.getString(i));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapids;
	}

	private static void insertData(){
		Connection conn = getConn();
		System.out.println(new Date());
		for (int i = 0; i > -1; i++) {
			String cid = UUID.randomUUID().toString();
			String sql = "INSERT INTO `dsp_t_statis_by_day` (`time`, `creativeid`, `category`, `imprs`, `clks`, `cost`, `downloads`, `regists`, `flag`, `createtime`) VALUES ('2014-12-06 00:00:00', '"+cid+"', '2', '961', '9', '201860.7000', '0', '0', '0', '2015-09-14 15:07:42');";
			PreparedStatement pstmt;
			try {
				pstmt = (PreparedStatement) conn.prepareStatement(sql);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println(new Date());
	}
}
