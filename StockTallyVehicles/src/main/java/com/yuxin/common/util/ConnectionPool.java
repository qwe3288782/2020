package com.yuxin.common.util;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.sql.*;

public class ConnectionPool {
	public ConnectionPool() {
	}

	public static Connection getConn() {
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String dbURL = "jdbc:sqlserver://192.168.100.50;DatabaseName=AIS20180802201137";
		String dbName = "sa";
		String dbpw = "YU*xin2018";
		Connection conn = null;

		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(dbURL, dbName, dbpw);
			if (conn != null) {
				System.out.println("connect success");
			}
		} catch (Exception var6) {
			var6.printStackTrace();
		}

		return conn;
	}

	public static void close(PreparedStatement pstmt, Connection conn) {
		try {
			if (pstmt != null) {
				pstmt.close();
			}

			if (conn != null) {
				conn.close();
			}
		} catch (SQLException var3) {
			var3.printStackTrace();
		}

	}

	public static void close(PreparedStatement pstmt, ResultSet rs, Connection conn) {
		try {
			if (pstmt != null) {
				pstmt.close();
			}

			if (rs != null) {
				rs.close();
			}

			if (conn != null) {
				conn.close();
			}
		} catch (SQLException var4) {
			var4.printStackTrace();
		}

	}
}
