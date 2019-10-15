package com.pojo.as;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Provider;

import org.springframework.beans.factory.annotation.Autowired;

public class ServiceRequestWebController {
	//1
	ServiceRequestService serviceRequestService;

	//3
//	@Resource
//	private ObjectFactory<ServiceRequest> serviceRequestFactory;
	
	//4
	@Autowired
	ServiceRequestFactory serviceRequestFactory;
	
	//5
	//public abstract ServiceRequest getServiceRequest();
	
	//6
//	@Inject
//	Provider<ServiceRequest> serviceRequestProvider;
	
	public void serviceRequestFormSubmit(HttpServletRequest request) {
		//1,2
		//ServiceRequest serviceRequest = new ServiceRequest();
		//3
//		ServiceRequest serviceRequest = this.serviceRequestFactory.getObject();
		//4
		//ServiceRequest serviceRequest = this.serviceRequestFactory.getServiceFactory();
		//5
		ServiceRequest serviceRequest = this.getServiceRequest();
		serviceRequest.setCustomerNo(request.getParameter("custno"));
		
		this.serviceRequestService.addNewServiceRequest(serviceRequest);
	}
}
