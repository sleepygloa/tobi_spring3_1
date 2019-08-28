package com.tobi;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JUnitTest {
	//1차
//	static JUnitTest testObject;
//	
//	@Test
//	public void test1() {
//		assertThat(this, is(not(sameInstance(testObject))));
//		testObject = this;
//	}
//	
//	@Test
//	public void test2() {
//		assertThat(this, is(not(sameInstance(testObject))));
//		testObject = this;
//	}
//	
//	@Test
//	public void test3() {
//		assertThat(this, is(not(sameInstance(testObject))));
//		testObject = this;
//	}
	
	//2차
//	static Set<JUnitTest> testObject = new HashSet<JUnitTest>();
//	
//	@Test
//	public void test1() {
//		assertThat(testObject, not(hasItem(this)));
//		testObject.add(this);
//	}
//	
//	@Test
//	public void test2() {
//		assertThat(testObject, not(hasItem(this)));
//		testObject.add(this);
//	}
//	
//	@Test
//	public void test3() {
//		assertThat(testObject, not(hasItem(this)));
//		testObject.add(this);
//	}
	
	@Autowired
	private ApplicationContext context;
	
	static Set<JUnitTest> testObject = new HashSet<JUnitTest>();
	static ApplicationContext contextObject = null;
	
	@Test
	public void test1() {
		assertThat(testObject, not(hasItem(this)));
		testObject.add(this);
		
		assertThat(contextObject == null || contextObject == this.context, is(true));
		contextObject = this.context;
	}
	
	@Test
	public void test2() {
		assertThat(testObject, not(hasItem(this)));
		testObject.add(this);
		
		assertThat(contextObject == null || contextObject == this.context, is(true));
		contextObject = this.context;
	}
	
	@Test
	public void test3() {
		assertThat(testObject, not(hasItem(this)));
		testObject.add(this);
		
		assertThat(contextObject == null || contextObject == this.context, is(true));
		contextObject = this.context;
	}

}
