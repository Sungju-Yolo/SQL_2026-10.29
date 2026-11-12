package com.kh.subs.model.service;

import java.util.ArrayList;
import java.util.List;

import com.kh.subs.exception.DuplicateNameException;
import com.kh.subs.exception.InvalidPaymentDayException;
import com.kh.subs.exception.SubscriptionNotFoundException;
import com.kh.subs.model.dto.Subscription;

public class SubsService {
	private List<Subscription> subscriptions = new ArrayList<>();{
	subscriptions.add(new Subscription("넷플릭스", 13500, 5, "OTT"));
	subscriptions.add(new Subscription("유튜브 프리미엄", 14900, 12, "OTT"));
	subscriptions.add(new Subscription("멜론", 10900, 20, "음악"));
	}
	
	public List<Subscription> readSubscriptions() {
		return subscriptions;
	}
	
	public void save(Subscription s) {
		nameCheck(s.getName());
		paymentDayCheck(s.getPaymentDay());
		subscriptions.add(s);
	}
	
	private void nameCheck(String name) {
		for(Subscription s : subscriptions) {
			if(s.getName().equals(name)) {
				throw new DuplicateNameException("이미 구독 중인 서비스입니다.");
			}
		}
	}
	
	private void paymentDayCheck(int day) {
		if(day < 1 || day > 31) {
			throw new InvalidPaymentDayException("결제일은 1일부터 31일 사이여야 합니다.");
		}
	}
	
	public void delete(String name) {
		for(int i = 0; i < subscriptions.size(); i++) {
			if(subscriptions.get(i).getName().equals(name)) {
				subscriptions.remove(i);			
			}
		}
		
		throw new SubscriptionNotFoundException("해당 서비스명의 구독이 없습니다.");
	}
	
	public List<Subscription> findByCategory(String category) {
		return subscriptions.stream().filter(s -> s.getCategory().equals(category)).toList();
	}
	public void updateFee(String name, int newFee) {
		for(Subscription s : subscriptions) {
			if(s.getName().equals(name)) {
				s.setMonthlyFee(newFee);
				return;
			}
		}
		throw new SubscriptionNotFoundException("해당 서비스명의 구독이 없습니다.");
	}
}
