package com.kh.plant.model.service;

import java.sql.Connection;
import java.util.List;

import com.kh.common.JdbcTemplate;
import com.kh.plant.model.dao.PlantDao;
import com.kh.plant.model.dto.HousePlantDto;

public class PlantService {
	/*
	 * 비즈니스 로직 작성(의사결정 코드) -> 데이터 가공, 중복 체크, 연산처리, 암호화
	 * 트랜잭션 관리, 여러 DAO 호출, 예외 처리 및 예외 발생, 보안 및 권한 검사
	 * 
	 * 모든 실행 절차는 서비스에서 실행한다.
	 */
	public int save(HousePlantDto hpd) {
		Connection conn = JdbcTemplate.getConnection();
		int result = new PlantDao().save(conn, hpd);
		JdbcTemplate.close(conn); // 사용이 모두 끝난 Connection 타입의 객체를 반납
		return result; // 
		
	}
	public List<HousePlantDto> view() {
		// 1. 커넥션 객체 받아오기
		Connection conn = JdbcTemplate.getConnection();
		
		// 2. 컨트롤러로부터 전달받은 인자값과 커넥션 타입의 객체를 DAO로 전달
		List<HousePlantDto> plants = new PlantDao().view(conn);
		// 3. 할 일이 모두 끝난 Connection 자원 반납
		JdbcTemplate.close(conn);
		// 4. 결과값 반환
		return plants;
		
	}
	public int edit(HousePlantDto hpd) {
		Connection conn = JdbcTemplate.getConnection();
		int result = new PlantDao().edit(conn, hpd);
		JdbcTemplate.close(conn);
		return result;
	}
	
	public int delete(int plantNo) { // 정수형 반환 타입 - 
		if(plantNo < 1) {
			throw new RuntimeException("식물번호는 1보다 작을 수 없습니다.");
		}
		Connection conn = JdbcTemplate.getConnection(); // 1. Jdbc 의 get커넥션
		int result = new PlantDao().delete(conn, plantNo); // 2. 커낵션 겍체 주소 컨트롤에서 받은 인자값과 함께 dao한테 전달한다
		
		JdbcTemplate.close(conn);
		
		return result;
		
		
		
	}
	
	public HousePlantDto oneView(int plantNo) { //매핑을 한 dto 타입 - 단일 매핑 객체
		return new PlantDao().oneView(plantNo);
		
	}
	
	public List<HousePlantDto> search(String keyword) { //매핑을 한 dto 리스트타입 - 다중 매핑 객체
		return new PlantDao().search(keyword);
	}

}
