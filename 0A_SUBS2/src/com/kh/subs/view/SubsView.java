package com.kh.subs.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.kh.subs.controller.SubsController;
import com.kh.subs.exception.DuplicateNameException;
import com.kh.subs.exception.InvalidPaymentDayException;
import com.kh.subs.exception.SubscriptionNotFoundException;
import com.kh.subs.model.dto.Subscription;

public class SubsView {
	private Scanner sc = new Scanner(System.in);
	private SubsController ctrl = new SubsController();
	
	
	public void mainMenu() {
	while(true) {
		System.out.println();
		System.out.println("구독 서비스 관리 프로그램입니다.");
		System.out.println("1. 구독 전체 조회하기.");
		System.out.println("2. 구독 추가하기");
		System.out.println("3. 구독 해지하기");
		System.out.println("4. 카테고리로 검색하기");
		System.out.println("5. 월 요금 변경하기");
		System.out.println("0. 프로그램 종료하기");
		System.out.println();
		System.out.print("사용하실 메뉴번호를 입력해주세요 > ");
		int menu = 0;
		
		try {
			menu = sc.nextInt();
		} catch(InputMismatchException e) {
			System.out.println("숫자만 입력해주세요.");
			continue;
		} finally {
			sc.nextLine();
		}
		
		switch(menu) {
		case 1 : readSubscriptions(); break;
		case 2 : save(); break;
		case 3 : delete(); break;
		case 4 : findByCategory(); break;
		case 5 : updateFee(); break;
		case 0 : System.out.print("프로그램을 종료합니다"); 
		sc.close(); return;
		default : System.out.println("없는 메뉴를 선택하셨습니다.");
		}
		} 
	}
	
	private void readSubscriptions() {
		List<Subscription> list = ctrl.readSubscriptions();		
		
		if(!list.isEmpty()) {
			System.out.println();
			System.out.println("구독 정보를 확인하세요!");
			System.out.println();
			for(Subscription s : list) {
				System.out.println("서비스명 : " + s.getName());
				System.out.println("------------------------------");
				System.out.println("월 요금 : " + s.getMonthlyFee() + "원");
				System.out.println("------------------------------");
				System.out.println("결제일 : 매월 " + s.getPaymentDay() + "일");
				System.out.println("------------------------------");
				System.out.println("카테고리 : " + s.getCategory());
				System.out.println("------------------------------");
				System.out.println();
			}
		} else {
			System.out.println("================================");
			System.out.println("아직 구독 중인 서비스가 없습니다");
			System.out.println("새 구독을 추가해보세요!");
			System.out.println("================================");
		}
	}
	
	private void save() {
		System.out.println();
		System.out.print("서비스명을 입력해주세요 > ");
		String name = sc.nextLine();
		
		System.out.print("월 요금을 입력해주세요(원) > ");
		int monthlyFee = sc.nextInt();
		sc.nextLine();
		
		System.out.print("결제일을 입력해주세요(1~31) > ");
		int paymentDay = sc.nextInt();
		sc.nextLine();
		
		System.out.print("카테고리를 입력해주세요(OTT/음악/쇼핑/기타) > ");
		String category = sc.nextLine();
		
	 try {
		ctrl.save(name, monthlyFee, paymentDay, category);
		System.out.println("구독이 등록되었습니다!");
	} catch(DuplicateNameException e) {
		System.out.println(e.getMessage());
		System.out.println("다른 서비스명을 입력해수제요!");
	} catch(InvalidPaymentDayException e) {
		System.out.println(e.getMessage());
	}
	}
	
	public void delete() {
		System.out.println();
		System.out.println("구독을 해지하겠습니다.");
		readSubscriptions();
		System.out.print("해지할 서비스명을 입력해주세요 > ");
		String name = sc.nextLine();
		
		if(name.isBlank()) {
			System.out.println("서비스명을 입력하지 않았습니다.");
			return;
		}
		
		try {
			ctrl.delete(name);
			System.out.println(name + "구독을 해지했습니다.");
		} catch(SubscriptionNotFoundException e) {
			System.out.println(e.getMessage());
			
		}
	}
	
	public void findByCategory() {
		System.out.println();
		System.out.print("검색할 카테고리를 입력해주세여(OTT/음악/쇼핑/기타) > ");
		String category = sc.nextLine();
		
		List<Subscription> searched = ctrl.findByCategory(category);
		
		if(searched.isEmpty()) {
			System.out.println(category + "카테고리에 구독 중인 서비스가 없습니다.");
			return;
		}
		
		System.out.println();
		System.out.println(category + " 카테고리 검색 결과입니다.");
		for(Subscription s : searched) {
			System.out.println(s.getName() + " / " + s.getMonthlyFee() + "원/ 매월" + s.getPaymentDay() + "일");
		}
	}
	
	public void updateFee() {
		System.out.println();
		System.out.print("요금을 변경할 서비스명을 입력해주세요 > ");
		String name = sc.nextLine();
		System.out.print("변경할 월 요금을 입력해주세요(원) > ");
		int newFee = sc.nextInt();
		sc.nextLine();
		
		try {
			ctrl.updateFee(name,  newFee);
			System.out.println(name + " 요금이 " + newFee + "원으로 변경되었습니다.");
		} catch(SubscriptionNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
	
}
