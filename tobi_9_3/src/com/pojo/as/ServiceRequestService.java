package com.pojo.as;

import org.springframework.beans.factory.annotation.Autowired;

public class ServiceRequestService {
	
	//2
	@Autowired ServiceRequestDao serviceRequestDao;

	public void addNewServiceRequest(ServiceRequest serviceRequest) {
		//1
//		Customer customer = this.customerDao.findCustomerByNo(serviceRequest.getCustomerNo());
		//...
		this.serviceRequestDao.add(serviceRequest);
		
		//1
		//this.emailService.sendEmail(customer.getEmail(), "A/S 접수가 정상적으로 처리되었습니다.");
		//2
		//this.emailService.sendEmail(serviceRequest.getCustomer().getEmail(), "A/S 접수가 정상적으로 처리되었습니다.");
		serviceRequest.notifyServiceRequestRegistration();
	}
	
}
