package com.kh.plant.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.plant.model.dto.HousePlantDto;

public class PlantDao {
	private Properties prop = new Properties();	
	
	public PlantDao() {
		try {
			prop.loadFromXML(new FileInputStream("resources/houseplant-mapper.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public int save(Connection conn, HousePlantDto hpd) {
		int result = 0;
		Properties prop = new Properties();
		//확장자가 properties 일때는 그냥 load, XML 일때는 loadFromXml
		try {
			prop.loadFromXML(new FileInputStream("resources/houseplant-mapper.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(prop.getProperty("save"));

		// ? 위치 홀더
		
		return result;
	}
	
	public List<HousePlantDto> view(Connection conn) {
		List<HousePlantDto> plants = new ArrayList(); //나중에 돌아갈 list 작성
		//Properties prop = new Properties();
		// prop.loadFromXML
		// 메소드를 호출할 때마다
		// XML 파일로부터 Properties 객체로 입력받는 코드를 작성해야 함
		
		String sql = prop.getProperty("view");
		
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
		String sql = prop.getProperty("edit");
		
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
		String sql = prop.getProperty("delete");
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