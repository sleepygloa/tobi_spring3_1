package com.pojo.service;

import org.springframework.context.annotation.Bean;

import com.pojo.interfaces.Printer;

public class HelloService {
	private Printer printer;

	public void setPrinter(Printer printer) {
		this.printer = printer;
	}
	
	@Bean
	public Hello HelloConfig() {
		Hello hello = new Hello();
		hello.setName("Spring");
		hello.setPrinter(printer());
		return hello;
	}

	@Bean
	public Hello hello2() {
		Hello hello = new Hello();
		hello.setName("Spring2");
		hello.setPrinter(printer());
		return hello;
	}
	
	@Bean
	public Printer printer() {
		return new StringPrinter();
	}
}
