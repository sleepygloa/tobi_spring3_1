package com.pojo.as;

import org.springframework.beans.factory.annotation.Autowired;


//vo
//Request 시에 주입된 cutomerDao를 통해 Customer customer 에 데이터를 입력해줌.
public class ServiceRequest {
	//문자열
	String customerNo;  //고객번호
	String productNo;   //제품번호
	String description; //기타
	
	//고객 객체
	Customer customer;
	
	//이메일 객체
	@Autowired EmailService emailService;
	
	public void notifyServiceRequestRegistration() {
		if(this.customer.serviceNotificationMethod == NotificationMethod.EMAIL) {
			this.emailService.sendEmail(customer.getEmail(), "A/S 접수가 정상적으로 처리되었습니다.");
		}
	}
	
	//dao 주입
	@Autowired CustomerDao customerDao;
	
	public void setCustomerByCustomerNo(String customerNo) {
		this.customer = customerDao.findCustomerByNo(customerNo);
	}
	
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
