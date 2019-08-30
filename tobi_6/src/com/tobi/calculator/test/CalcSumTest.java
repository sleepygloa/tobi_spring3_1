package com.tobi.calculator.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.tobi.calculator.service.Calculator;

public class CalcSumTest {
	
	
	Calculator calculator;
	String numFileapth;
	
	@Before
	public void setUp() {
		this.calculator = new Calculator();
		this.numFileapth = getClass().getResource("/com/tobi/resource/numbers.txt").getPath();
	}

	@Test
	public void sumOfNumbers() throws IOException {
		assertThat(calculator.calcSum(this.numFileapth), is(10));
	}
	
	@Test
	public void multiplyOfNumbers() throws IOException {
		assertThat(calculator.calcMultiply(this.numFileapth), is(24));
	}
	
	@Test
	public void concentrateStrings() throws IOException {
		assertThat(calculator.concentrate(this.numFileapth), is("1234"));
	}	
	
	
	
	
}
