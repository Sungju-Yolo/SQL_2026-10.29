package com.kh.plant.model.service;

import java.util.List;

import com.kh.plant.model.dao.PlantDao;
import com.kh.plant.model.dto.HousePlantDto;

public class PlantService {
	
	public int save(HousePlantDto hpd) {
		// 비즈니스로직 / 의사결정코드
		return new PlantDao().save(hpd); // 이 친구의 정체는 0 아니면 1 (resultSet)
	}
	
	public List<HousePlantDto> view() {
		return new PlantDao().view();
	}
	public int edit(HousePlantDto hpd) {
		return new PlantDao().edit(hpd);
	}
	
	public int delete(int plantNo) { // 정수형 반환 타입 - 
		if(plantNo < 1) {
			throw new RuntimeException("식물번호는 1보다 작을 수 없습니다.");
		}
		return new PlantDao().delete(plantNo);
		
	}
	
	public HousePlantDto oneView(int plantNo) { //매핑을 한 dto 타입 - 단일 매핑 객체
		return new PlantDao().oneView(plantNo);
		
	}
	
	public List<HousePlantDto> search(String keyword) { //매핑을 한 dto 리스트타입 - 다중 매핑 객체
		return new PlantDao().search(keyword);
		
		
	}

}
