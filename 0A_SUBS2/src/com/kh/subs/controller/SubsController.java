package com.kh.subs.controller;

import java.util.List;

import com.kh.subs.model.dto.Subscription;
import com.kh.subs.model.service.SubsService;

public class SubsController {
	private SubsService ss = new SubsService();
	
	public List<Subscription> readSubscriptions() {	
		return ss.readSubscriptions();		
	}
	
	public void save(String name, int monthlyFee, int paymentDay, String category) {
		ss.save(new Subscription(name, monthlyFee, paymentDay, category));
	}
	
	public void delete(String name) {
		ss.delete(name);
	}
	public List<Subscription> findByCategory(String category) {
		return ss.findByCategory(category);
	}
	public void updateFee(String name, int newFee) {
		ss.updateFee(name, newFee);
	}

}
