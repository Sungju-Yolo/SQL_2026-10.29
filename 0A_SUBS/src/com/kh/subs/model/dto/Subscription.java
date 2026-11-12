package com.kh.subs.model.dto;

public class Subscription {
	private String name;
	private int monthlyFee;
	private int paymentDay;
	private String category;
	
	
	public Subscription() {}
	public Subscription(String name, int monthlyFee, int paymentDay, String category) {
		this.name = name;
		this.monthlyFee = monthlyFee;
		this.paymentDay = paymentDay;
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMonthlyFee() {
		return monthlyFee;
	}
	public void setMonthlyFee(int monthlyFee) {
		this.monthlyFee = monthlyFee;
	}
	public int getPaymentDay() {
		return paymentDay;
	}
	public void setPaymentDay(int paymentDay) {
		this.paymentDay = paymentDay;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	@Override
	public String toString() {
		return "Subscription [name=" + name + ", monthlyFee=" + monthlyFee + ", paymentDay=" + paymentDay
				+ ", category=" + category + "]";
	}
	
	
	
	
	
	
	
	

}
