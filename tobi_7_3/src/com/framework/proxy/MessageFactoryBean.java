package com.framework.proxy;

import com.framework.proxy.interfaces.FactoryBean;

public class MessageFactoryBean implements FactoryBean<Message> {
	
	String text;
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Message getObject() throws Exception{
		return Message.newMessage(text);
	}
	
	public Class<? extends Message> getObjectType(){
		return Message.class;
	}
	
	public boolean isSingleton() {
		return false;
	}

}
