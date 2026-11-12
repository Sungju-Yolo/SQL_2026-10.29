package com.kh.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {
	/*
	 * JDBC 과정에 사용되는 반복적으로 작성해야 하는 코드
	 * 메소드로 분리할 클래스
	 */
	public static Connection getConnection() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521/XEPDB1"
													 	 ,"HOSPITAL"
													 	 ,"oracle");
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return null;	
	}
	
	public static void close(Connection conn) {
		try {
			if(conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(ResultSet rset) {
		try {
			if(rset != null) {
				rset.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	

	
	
	
	
}