package com.kh.start.ward;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectRun {

	public static void main(String[] args) {
		// 각자 작업중인 자신의 DB서버에서
		// 각자 자신의 계정에 존재하는 COFFEE 테이블의 모든행을 조회해서
		// 이클립스의 콘솔창에 조회
		
		// 0) 필요한 변수들 세팅
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null; //조회 된 행들의 집합
		
		String sql = """
						SELECT
						       COFFEE_NO
						     , COFFEE_NAME
						     , CREATE_DATE  
						  FROM
						       TB_COFFEE
						 ORDER
						    BY
						       CREATE_DATE DESC     
					 """;
		
		try {
		// 1. JDBC Driver 등록
		// 리플랙션을 이용한 드라이버 클래스 로딩
		// 실행 시점에 필요한 데이터베이스 드라이버를 동적으로 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 등록!");
			// 2.Connection 객체 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521/XEPDB1",
											   "HOSPITAL",
					    					   "oracle");
			System.out.println("연결 성공~~!");
			
			// 3. Statement 객체 생성
			stmt = conn.createStatement();
			
			// 4. SQL문 실행
			// stmt.executeUpdate(sql);
			// stmt.executeUpdate(sql) -> INSERT, UPDATE, DELETE -> DDL
			// SELECET문 
			rset = stmt.executeQuery(sql); // resultSet 타입 반환
			// System.out.println(rset);
			
			// ResultSet 반환됨
			// rset.next() : 커서를 조작
			while(rset.next()) {
				// 커서를 조작했을 때 행이 존재한다면 true / 없다 false
				// 
				
				int coffeeNo = rset.getInt("COFFEE_NO");
				String coffeeName = rset.getString("COFFEE_NAME");
				Date createDate = rset.getDate("CREATE_DATE");
				
				System.out.println("커피 번호 : " + coffeeNo
								  +", 커피 이름 : " + coffeeName
								  +", 커피 만든날 : "+ createDate);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// JDBC
		// JAVA + SQL(SELCET, INSERT, UPDATE, DELETE)
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
