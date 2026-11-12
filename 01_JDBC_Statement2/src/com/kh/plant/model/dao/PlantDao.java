package com.kh.plant.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kh.plant.model.dto.HousePlantDto;

public class PlantDao {
	/*
	 * JDBC용 인터페이스 
	 * 
	 * - "Connection" : 데이터베이스와의 세션(연결)을 나타내는 인터페이스
	 * -> 데이터베이스와의 통신 채널 연결
	 * -> SQL문의 실행을 위한 Statement 객체 생성
	 * -> 트랜잭션 관리
	 * 
	 * - "Statement" : SQL문을 실행하고 결과를 받아오기 위한 인터페이스 
	 * 1. Statement : 정적 SQL문(완성된 SQL문) 실행
	 * 2. PreparedStatement : 파라미터화 된 SQL문을 실행
	 * 3. CallableStatement : 저장 프로시저 호출
	 * 
	 * - "ResultSet" : SELCET문 수행결과를 담은 테이블 형태의 데이터 셋
	 * -> 커서(cursor)라는 개념을 이용해서 데이터에 접근
	 * -> 다양한 데이터타입을 변환해주는 메소드를 제공
	 * 
	 * 
	 * 1. JDBC 드라이버 등록
	 * 1. 첫번째 작업 - JDBC 드라이버 등록 -> 드라이버 매니저라는 클래스를 이용해서 커낵션 타입의 객체 생성 -> 객체를 생성할 때는 여러가지 값이 필요한데 IP 주소, 포트 번호, 유저 이름, 비밀 번호가 필요하다 -> 이후 statement 타입의 객체를 생성할 수 있다. 커낵션 없이 statement 타입의 객체를 생성할 수 없다. 
	 * 2. SQL문에 따라 statement 분기가 갈린다. dml(insert, update, delete 의 경우 sql문을 실행해야 한다 -> int 형으로 행의 개수가 반환이 된다. -> 0보다 커진 경우 commit(확정) 이 된다 (트랜잭션 처리)
	 * 3. SELCET의 경우 똑같이 sql문을 실행하나 익스큐트 쿼리를 가지고 실행 -> ResultSet 반환 행이 여러개고 컬럼도 여러개다. 수많은 데이터를 view 까지 들고가야 할 때 한개밖에 못돌아가는데, -> 나온 데이터를 반환할 수 있는 형태로 가공을 해야 한다.
	 * 4. 이후 작업이 모두 끝나면 SELCET 문에서는 가공 된 형태로 결과 반환, DML에서는 int 자료형으로 변환 된 행의 개수를 결과 반환한다.
	 */
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public int save(HousePlantDto hpd) {
		//필요한 변수 선언 및 null값으로 초기화
		Connection conn = null;
		Statement stmt = null;
		int result = 0;
		
		String sql = "INSERT "
					 + "INTO "
					 	  + "HOUSEPLANT "
				   + "VALUES "
					 	  + "("
					 	  + "SEQ_PLANT_NO.NEXTVAL"
					 	+ ", '" + hpd.getPlantType() + "'" 
					 	+ ", '" + hpd.getPlantName() + "'" 
					 	  + ")";
		// SQL문에 문법적인 문제가 존재한다.
		// SQLSyntaxErrorException이 발생
		
		try {
			// 1) JDBC Driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 1. 패키지 경로 / 클래스 식별자에 오타가 발생한 경우
			// 2. 프로젝트에 라이버리를 추가하지 않아서 진짜 없는 경우
			// -> ClassNotFoundException이 발생
			
			// 2) Connection 타입 객체 생성 (URL, username, password)
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521/XEPDB1"
									   ,"HOSPITAL"
									   ,"oracle");
			// 3) Statement 타입의 객체 생성
			stmt = conn.createStatement();
			
			// 4) SQL문 실행 요청 및 응답 반환
			result = stmt.executeUpdate(sql);
			// INSERT 시 값에 문제가 있을 수 있음
			// 자료형이 문제
			// 제약조건 위배
			// 데이터의 크기가 컬럼의 크기보다 큼
			// DB서버에 문제
			// SQLException이 발생
			// 1. 받아두는 의미 트랜잭션 처리
			// 2. View 로 보내기 위해
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 5) 자원반납
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		// DML 수행결과 반환
		return result;
		
		// 세가지 내가 제어할 수 있는 오류의 경우
					// 1. URL을 잘못 적었을 경우
					// 2. 사용자 이름을 잘목 적었을 경우
					// 3. 비밀번호를 잘못 적었을 경우
					// ---------------------------
					// 4. 서버가 안 열려(켜져)있을 수 있음
					// 5. 접속권한을 부여받지 못했을 경우
					// 6. 비밀번호 갱신을 안했을 수 있음
					// SQLException이 발생
	}
	
	public List<HousePlantDto> view() {
		
		// SELECT
		// 1. Connection
		// 2. Statement
		// 3. ResultSet
		
		// 0)
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		List<HousePlantDto> list = new ArrayList<>();
		
		
		String sql = """
						SELECT
							   PLANT_NO
							 , PLANT_NAME
							 , PLANT_TYPE
						  FROM
						       HOUSEPLANT
						 ORDER
						    BY
						       PLANT_NO DESC         
					 """;
		// 1)
		// Class.forName("~ 드라이버");
		
		// 2) Connection 객체 생성
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521/XEPDB1"
											  ,"HOSPITAL"
					  						  ,"oracle");
		// 3) Statement 객체 생성
		stmt = conn.createStatement();
		// 4) SQL문 (SELECT) 실행요청 후 응답반환 받기
		rset = stmt.executeQuery(sql);
			
		// 5) ★SELECT문의 핵심★ - view 까지 (하나에 담아서) 가공해서 돌아가야 하는데 돌아갈 수 있는건 하나
		// 1 관엽식물 금전수	
		// List, Set, Map 중 Map을 사용한다	plantNo 즉 primary key 앞 부분만 따오면 되기 때문
		// 오에키 PLANT의 값을 담을거니까 PLANT 모양에 담아야겠다
		// 테이블 형태의 값음 하나의 하우스 plantDto 객체의 필드
		// RDBMS == 테이블형태
        // Mapping 매핑한다
		// 서로 "다른 형태의 데이터 모델간의 연결"을 정의하는 과정 (패러다임 불일치) / 관계지향성과 객체 지향성은 상성이 최악이다라고 한다
		// JAVA  == 객체형태
			
		// rset.next();	
		// 조회결과를 담은 ResultSet이 돌아옴
		// => 한 행씩 접근해서 데이터를 뽑아내고 => DTO/VO 필드에 담기
		// => 커서를 한 줄 아래로 옮기고 존재한다 true / false 따라서 조건식으로 쓸 수 있다.
		while(rset.next()) {
			//현재 rset의 커서가 가리키고 있는 행의 데이터를
			// 하나씩 뽑아서 HousePlantDTO객체의 필드에 대입
			HousePlantDto h = new HousePlantDto();
			// ResultSet 객체로부터
			// 어떤 컬럼의 값을 뽑을건지 메소드를 호출 인자값으로
			// 컬럼명을 전달
			// rest.getInt("컬럼명");
			// 컬럼값이 정수여서 int로 매핑해야 할 때
			// rest.getString("컬럼명");
			// 컬럼값이 문자열이기 때문에 String으로 매핑할 때
			// rest.getDate("컬럼명");
			// 컬럼값이 날짜값이어서 java.sql.Date로 매핑할 때
			h.setPlantNo(rset.getInt("PLANT_NO"));
			h.setPlantName(rset.getString("PLANT_NAME"));
			h.setPlantType(rset.getString("PLANT_TYPE"));
			// 옮겨 담는 것을 매핑
			
			// 한 행의 모든 컬럼값을
			// 각각의 필드에 담아 DTO 객체로 옮겨닮으면 매핑 완료!
			// 몇 개? => 조회된 개수만큼!
			
			// List, Set, Map
			// SELECT ORDER BY 정렬
			// 배열 X 개수를 몰른다
			// List
			
			//List<HousePlantDto> list = new ArrayList();
			//list.add(h);
			list.add(h);
			
		}
		
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			try {
			rset.close();
			stmt.close();
			conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		// 조회된 결과물을 매핑해놓은 DTO 객체들의 주소값을 요소로 가지고 있는
		// List의 주소값을 반환
		return list;
	}
	
	public int edit(HousePlantDto hpd) {
		// UPDATE 문을 쓰자 -> updatedRows
		int result = 0;
		String sql = "UPDATE "
						  + "HOUSEPLANT "
				      + "SET "
						  + "PLANT_NAME = '" + hpd.getPlantName() + "'"
						 + ",PLANT_TYPE = '" + hpd.getPlantType() + "' "
					 +"WHERE "
						 + "PLANT_NO = " + hpd.getPlantNo();
		
		try(Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521/XEPDB1"
														 ,"HOSPITAL"	
														 ,"oracle");
			Statement stmt = conn.createStatement()) {
			result = stmt.executeUpdate(sql);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int delete(int plantNo) {
		int result = 0; // 0으로 초기화및 선언
		String sql = """
						DELETE
						  FROM
						       HOUSEPLANT
						 WHERE
						       PLANT_NO =       
 					 """;
		sql += plantNo;
		try(Connection conn =
				DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521/XEPDB1"
										   ,"HOSPITAL"
										   ,"oracle");
			Statement stmt = conn.createStatement()){
			//여기서부터 새로 시작
			result = stmt.executeUpdate(sql);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return result; // 결국 plantNo는 정수 값으로 선언 int 형 정수 result 를 반환하는구나~
	}
	
	public HousePlantDto oneView(int plantNo) {
		String sql = """
						 SELECT
						        PLANT_NO
						      , PLANT_NAME
						      , PLANT_TYPE
						   FROM
						        HOUSEPLANT
						  WHERE
						        PLANT_NO =           
				     """;
		sql += plantNo;
		HousePlantDto hpd = null;
		
		try(Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521/XEPDB1"
														  ,"HOSPITAL"
														  ,"oracle");
				Statement stmt = conn.createStatement();
				ResultSet rset = stmt.executeQuery(sql)){
			    
				if(rset.next()) {
					// PK를 가지고 조회했을 때 결과가 존재한다.
					// 매핑
					hpd = new HousePlantDto(rset.getInt("PLANT_NO"),
									  rset.getString("PLANT_TYPE"),
									  rset.getString("PLANT_NAME"));
				}
			
			
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return hpd;
		
		
	}
	
	public List<HousePlantDto> search(String keyword) {
		//돌아갈 때
		List<HousePlantDto> plants = new ArrayList();
		
		
		String sql = """
						SELECT
						       PLANT_NO
						     , PLANT_NAME
						     , PLANT_TYPE
						  FROM
						       HOUSEPLANT
						 WHERE
					 """;
		sql += "PLANT_TYPE LIKE '%" + keyword + "%' ORDER BY PLANT_NO DESC";		    
		
		try(Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521/XEPDB1",
				                                          "HOSPITAL",
				                                          "oracle");
				Statement stmt = conn.createStatement();
				ResultSet rset = stmt.executeQuery(sql)){
			
			// 매핑
			while(rset.next()) {
				plants.add(new HousePlantDto(
											rset.getInt("PLANT_NO")
										   ,rset.getString("PLANT_TYPE")
										   ,rset.getString("PLANT_NAME")
										   ));
			}
		} catch(SQLException e) {
				e.printStackTrace();
		}		
		return plants;
	}

}