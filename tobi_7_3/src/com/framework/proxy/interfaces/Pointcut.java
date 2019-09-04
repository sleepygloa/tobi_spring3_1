package com.framework.proxy.interfaces;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;

public interface Pointcut {
	ClassFilter getClassFilter();
	MethodMatcher getMethodMathcer();
}
