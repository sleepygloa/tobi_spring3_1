package com.pojo.service;

import com.pojo.interfaces.Printer;

public class ConsolePrinter implements Printer {
	public void print(String message) {
		System.out.println(message);
	}
}
