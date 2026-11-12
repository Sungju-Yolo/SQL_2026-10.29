package com.kh.plant.controller;

import java.util.List;

import com.kh.plant.model.dto.HousePlantDto;
import com.kh.plant.model.service.PlantService;

public class PlantController { // dto를 필드에 생성하지 않는 이유는?
	// 소프트웨어 설계 기본 원칙: YAGNI 아직 필요하지 않는건 정말 간절히 필요해지기 전까지는 만들지 않는다.
	private PlantService ps = new PlantService();
	public int save(String plantName, String plantType) {
		HousePlantDto hpd = 
				new HousePlantDto(plantType, plantName);
		return ps.save(hpd);
		
	}
	public List<HousePlantDto> view() {
		return ps.view();
	}
	
	public int edit(int plantNo, String plantName, String plantType) {
		HousePlantDto hpd = new HousePlantDto(plantNo, plantType, plantName);
		
		
		return ps.edit(hpd);
		
		
	}
	
	public int delete(int plantNo) {
		return ps.delete(plantNo);
		
	}
	
	public HousePlantDto oneView(int plantNo) {
		return ps.oneView(plantNo);
	}
	
	public List<HousePlantDto> search(String keyword) {
		return ps.search(keyword);
		
		
		
	}
}
