package com.kh.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcTemplate {
	/*
	 * JDBC 과정에 사용되는 반복적으로 작성해야 하는 코드
	 * 메소드로 분리할 클래스
	 */
	public static Connection getConnection() {
		try {
			Properties prop = new Properties();
			
			prop.load(new FileInputStream("resources/connection.properties"));
			
			System.out.println(prop.getProperty("URL")); // 반환 타입이 String
			
			Connection conn = DriverManager.getConnection(prop.getProperty("URL")
													 	 ,prop.getProperty("USERNAME")
													 	 ,prop.getProperty("PASSWORD"));
			return conn;
			// 외부 파일은 STREAM으로 입력을 받아온다
			// 소스코드의 변경 => 컴파일 => 다시 램에 올려야 함
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
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