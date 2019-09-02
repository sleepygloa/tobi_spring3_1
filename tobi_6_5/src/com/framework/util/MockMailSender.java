package com.framework.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

import com.framework.interfaces.MailSender;

public class MockMailSender implements MailSender{
	
	private List<String> requests = new ArrayList<String>();
	
	public List<String> getRequest(){
		return requests;
	}
	
	public void send(SimpleMailMessage mailMessage ) throws MailException{
		requests.add(mailMessage.getTo()[0]);
	}
	
	public void send(SimpleMailMessage[] mailMessage) throws MailException{
		
	}
	
	

}
