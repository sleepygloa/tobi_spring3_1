package com.tobi.calculator.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.tobi.calculator.interfaces.BufferedReaderCallback;
import com.tobi.calculator.interfaces.LineCallback;

public class Calculator {
	
	//파일 읽는 로직
//	public Integer calcSum(String filepath) throws IOException{
//		BufferedReader br = new BufferedReader(new FileReader(filepath));
//		
//		try {
//			Integer sum = 0;
//			String line = null;
//			while((line = br.readLine()) != null) {
//				sum += Integer.valueOf(line);
//			}
//			
//			br.close();
//			return sum;			
//		}catch(IOException e) {
//			System.out.println(e.getMessage());
//			throw e;
//			
//		}finally {
//			if(br != null) { try { br.close(); }catch(IOException e) { System.out.println(e.getMessage()); } }
//		}
//	}
	
	//계산로직
	//인터페이스메소드 사용 후 거기다가 콜백함수를 던져 계산함.
//	public Integer calcSum(String filepath) throws IOException{
//		BufferedReaderCallback sumCallback = 
//			new BufferedReaderCallback() {
//				public Integer doSomethingWithReader(BufferedReader br) throws IOException{
//					Integer sum = 0;
//					String line = null;
//					while((line = br.readLine()) != null) {
//						sum += Integer.valueOf(line);
//					}
//					return sum;	
//				}
//		};
//		return fileReadTemplate(filepath, sumCallback); 
//	}
//	
//	public Integer calcMultiply(String filepath) throws IOException{
//		BufferedReaderCallback multiplyCallback = 
//			new BufferedReaderCallback() {
//				public Integer doSomethingWithReader(BufferedReader br) throws IOException{
//					Integer multiply = 1;
//					String line = null;
//					while((line = br.readLine()) != null) {
//						multiply *= Integer.valueOf(line);
//					}
//					return multiply;	
//				}
//		};
//		return fileReadTemplate(filepath, multiplyCallback); 
//	}
	public Integer calcSum(String filepath) throws IOException{
		LineCallback<Integer> sumCallback = 
			new LineCallback<Integer>() {
				public Integer doSomethingWithLine(String line, Integer value){
					return value + Integer.valueOf(line);
				}
		};
		return lineReadTemplate(filepath, sumCallback, 0); 
	}
	
	public Integer calcMultiply(String filepath) throws IOException{
		LineCallback<Integer> multiplyCallback = 
			new LineCallback<Integer>() {
				public Integer doSomethingWithLine(String line, Integer value){
					return value * Integer.valueOf(line);
				}
		};
		return lineReadTemplate(filepath, multiplyCallback, 1); 
	}
	
	public String concentrate(String filepath) throws IOException{
		LineCallback<String> concentrateCallback = 
			new LineCallback<String>() {
				public String doSomethingWithLine(String line, String value){
					return value + line;
				}
		};
		return lineReadTemplate(filepath, concentrateCallback, ""); 
	}
	
	//
	public <T> T lineReadTemplate(String filepath, LineCallback<T> callback, T initVal) throws IOException {
		BufferedReader br = null;
		
		try {
			
			br = new BufferedReader(new FileReader(filepath));
			T res = initVal;
			String line = null;
			while((line = br.readLine()) != null) {
				res = callback.doSomethingWithLine(line, res);
			}
			return res;
			
		}catch(IOException e) {
			System.out.println(e.getMessage());
			throw e;
			
		}finally {
			if(br != null) { try { br.close(); }catch(IOException e) { System.out.println(e.getMessage()); } }
		}
	}
	
	
	//파일 읽는 로직
	//콜백있음.
	public Integer fileReadTemplate(String filepath, BufferedReaderCallback callback) throws IOException{
		BufferedReader br = null;
		
		try {
			
			br = new BufferedReader(new FileReader(filepath));
			int ret = callback.doSomethingWithReader(br);
			return ret;
			
		}catch(IOException e) {
			System.out.println(e.getMessage());
			throw e;
			
		}finally {
			if(br != null) { try { br.close(); }catch(IOException e) { System.out.println(e.getMessage()); } }
		}		
	}
	

}
