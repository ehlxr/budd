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

public class TestJdbc3 {
	private static Connection getConn() {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://115.182.33.143:3306/wins-dsp-new?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
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

	public static void main(String[] args) {
		insertData();
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
