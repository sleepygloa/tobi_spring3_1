package com.tobi.user.test;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

import com.framework.interfaces.MailSender;

public class DamperMailSender implements MailSender{
	
	public void send(SimpleMailMessage mailMessage) throws MailException{
		
	}
	
	public void send(SimpleMailMessage[] mailMessage) throws MailException{
		
	}

}
