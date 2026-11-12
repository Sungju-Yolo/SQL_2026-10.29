package com.kh.plant.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kh.plant.model.dto.HousePlantDto;

public class PlantDao {
	/*
	 * DAO(Data Access Object)
	 * 
	 * DB에 직접 접근해서 SQL문을 실행 후 결과 받아오는 메소드들을 선언
	 * 
	 * 하나의 메소드는 하나의 SQL문
	 */
	
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public int save(Connection conn, HousePlantDto hpd) {
		int result = 0;
		String sql = """
						INSERT
						  INTO
						       HOUSEPLANT
						VALUES
						       (  
						       SEQ_PLANT_NO.NEXTVAL
						     , ?
						     , ?
						       ) 
					 """;
		// ? 위치 홀더
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setString(1, hpd.getPlantType());
			pstmt.setString(2, hpd.getPlantName());
			result = pstmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/*
	 * PreparedStatement : SQL문을 미리 전달하는 개념
	 * 					미완성 된 SQL문을 미리 전달한 뒤 실행하기 전
	 * 					실제 값을 Bind해서 실행함 (묶어준다)
	 * 					전달 시 SQL문에 사용자가 입력한 값이 들어갈 수 있도록 공간확보를 해놀음 ==> ?(placeholder)/위치홀더
	 * 
	 * - 차이점
	 * 
	 * 1) Statement => 완성된 SQL문 / PreparedStatement => 미완성된 SQL문을 보낸다
	 * 
	 * 2) conn.createStatement() / conn.prepareStatement(SQL문) 인자로 SQL문을 미리 보낸다. < - 요게 핵심 !
	 * 
	 * 3) stmt.executeXXX(SQL문) / pstmt.executeXXX() SQL문을 미리 보내놨다. 그래서 보내지 않는다
	 * 
	 * ?에 실제 값을 Binding 해준 뒤 실행한다.
	 * setString(홀더 순번, 값) setString 으로 쓰면
	 * => '값' (양 옆에 홀따옴표를 감싼 상태로 알아서 Bind 해준다)
	 * setInt(홀더순번, 값)
	 * => 값 (알아서 잘 들어감)
	 *  
	 * PreparedStatement가 Statement보다 좋은 점
	 *  
	 * 1. 구문분석 및 컴파일 최적화에 유리하다
	 * 
	 * Statement는 매 번 SQL문을 파싱하고 실행하는데
	 * PreparedStatement는 SQL쿼리를 최초 1회만 파싱하고 실행계획을 캐싱(메모리에 올림) 
	 * 재사용적인 측면에서 효율적이다.
	 *  
	 * 2. DB서버에 대한 트래픽 감소
	 * 
	 *  쿼리 자체는 한 번만 전송하고 이후에는 바인딩할 값만 보내기 때문에
	 *  동일 쿼리를 반복 실행할 때, 인기많은 애플리케이션일 때 더욱더 효율적임
	 *  DB의 작업 -> 실행 계획 세울 때 리소스를 많이 잡아먹음
	 *  
	 *  PreparedStatement는 SQL 인젝션 방지 => 보안적인 측면이 좋음
	 */
	public List<HousePlantDto> view(Connection conn) {
		List<HousePlantDto> plants = new ArrayList(); //나중에 돌아갈 list 작성
		String sql = """
					    SELECT
						 	   PLANT_NO
					   		 , PLANT_NAME	 
					   		 , PLANT_TYPE
					      FROM
					           HOUSEpLANT
					     ORDER
					        BY
					           PLANT_NO ASC      
					 """;
		try(PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rset = pstmt.executeQuery()){
			// RsultSet이 오긴 왔는데 조회 결과가 있을 수도 있으나 0행일 수도 있음
			// 매핑
			while(rset.next()) {
				plants.add(new HousePlantDto(rset.getInt("PLANT_NO")
											,rset.getString("PLANT_TYPE")
											,rset.getString("PLANT_NAME")));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return plants;
	}
	
	public int edit(Connection conn, HousePlantDto hpd) {
		int result = 0;
		String sql = """
						UPDATE
						       HOUSEPLANT
						   SET
						       PLANT_NAME = ?
						     , PLANT_TYPE = ?
						 WHERE
						       PLANT_NO = ?          
				     """;
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, hpd.getPlantName());
			pstmt.setString(2, hpd.getPlantType());
			pstmt.setInt(3, hpd.getPlantNo());
			
			result = pstmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int delete(Connection conn, int plantNo) { // 3. delete 즉 DML 이므로 반환 타입이 int 
		int result = 0; // 4. 0으로 초기화
		// 5. SQL문 작성
		String sql = """  
						 DELETE
						   FROM
						        HOUSEPLANT
						  WHERE
						        PLANT_NO = ?      
		
 					 """;
		// DQL 이 아니기 때문에 매핑을 하지 않고 try catch
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1,  plantNo);
			result = pstmt.executeUpdate();
		} catch(SQLException e ) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
	
	
	
	

}