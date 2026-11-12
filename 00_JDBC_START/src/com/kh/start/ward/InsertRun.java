  package com.kh.start.ward;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InsertRun {
	
	
	
	public static void main(String[] args) {
		// Java 코드를 작성해서 WARD 테이블에 한 행 INSERT 해보기
		/*
		 * WARD 테이블에 INSERT를 하기 위한 절차??
		 * 
		 * 1. 디비버 실행(클라이언트 프로그램)
		 * 
		 * 2. 접속하기(콘센트) 누름
		 * 
		 * 3. Oracle Database 선택
		 * 
		 * 4. IP주소, PORT번호, 사용자이름, 비밀번호를 가지고 연결
		 * 
		 * 5. 새 SQL 편집기 생성
		 * 
		 * 6. INSERT문 작성 => INSERT INTO WARD VALUES('값', '값', '값', '값')
		 * 
		 * 7. SQL문을 실행
		 * 
		 * 8. UpdatedRows : 1
		 * 
		 * 9. COMMIT
		 */
		// 0) 필요한 변수 선언
		Connection conn = null;
		Statement stmt = null;
		int result = 0;
		// Java DataBase Connectivity : 자바랑 데이터베이스를 연결해주는 애 
		// DBMS 가 인터페이스로 상속 받아서 작동 statement, connection 은 인터페이스
		// DBMS 개발자와 개발사가 쓰라고 만든 함수 모음이 라이브러리
		
		//String sql = "INSERT INTO WARD VALUES('W7', 'KH병동', '특실', 4)";
		// 사용자가 입력한 값을 DBSM로 전달 => COFFEE TABLE INSERT
		Scanner sc = new Scanner(System.in);
		System.out.print("커피명을 입력하세요 > ");
		String coffeeName = sc.nextLine();
		/*
		String sql = "INSERT "
				     + "INTO "
				          + "COFFEE "
				   + "VALUES "
				          + "("
				          + "SEQ_COFFEE.NEXTVAL"
				        + ", '" + coffeeName + "'"
				        + ", SYSDATE)";
		*/
		
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT ");
		sb.append("  INTO ");
		sb.append("       TB_COFFEE ");
		sb.append("VALUES ");
		sb.append("       (");
		sb.append("		  SEQ_COFFEE.NEXTVAL");
		sb.append("     , '" + coffeeName + "'");
		sb.append("     , SYSDATE");
		sb.append("       )");
		String sql = sb.toString();
		System.out.println(sql);
		// 오라클 DBMS에다 작업을 하겠다!!
		// 리플랙션 : 객체를 생성하지 않고 클래스에서 정보를 빼옴
		
		try {
			// 리플랙션을 이용해서 동적으로 클래스 로드
			// 1) JDBC Driver 등록 -> ORACLE -> ojdbc.jar
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver등록성공!");
		
			// 2) DB서버랑 연결(IP주소, PORT번호, 사용자이름, 비밀번호)
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521/XEPDB1",
										"HOSPITAL",
										"oracle");
			System.out.println("DB 연결 성공!");
			// 3) 새 SQL 편집기
			stmt = conn.createStatement();
			System.out.println("Statement 객체 생성!");
			
			//4) SQL문 실행
			result = stmt.executeUpdate(sql);
			System.out.println("SQL문 실행 성공!");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("1. 프로젝트에 ojdbc.jar 파일을 추가하지 않을 경우");
			System.out.println("2. oracle.jdbc.driver.OracleDriver에 오타");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("1. SQLSyntaxErrorException - > SQL문을 문법적으로 잘못 적었다.");
			System.out.println("2. username/password; logon denied -> 사용자 이름, 비밀번호");
			System.out.println("3. NullPointException -> JDBC 객체들 확인하기");
			System.out.println("4. SQLIntegrityConstraintViolation -> 테이블제약조건확인");
			System.out.println("5. URL을 잘못 적었구나 : IP주소, 포트번호");
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
		

	}
}
