package com.pojo.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pojo.interfaces.Printer;

@Configuration
public class HelloConfig {
	
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
