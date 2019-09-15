package com.framework.proxy.pointcut;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;


/**
 * AOP 란 무엇인가?
 * 1. 트랜젝션 서비스 추상황
 *  - 특정 트랜젝션 기술의 종속화를 구체적인 방법와 서버환경에 유동적으로 DI 시킴
 * 2. 프록시와 데코레이터 패턴
 *  - 비즈니스 로직 과 트랜젝션 코드에서의 분리
 * 3. 다이내믹 프록시와 프록시 팩토리 빈
 *  - 다이나믹하게 비즈니스로직에 접근함으로써, 비즈니스 로직당 DI하던 트랜젝션 코드를 추상화함.
 * 4. 자동 프록시 생성과 포인트컷
 *  - 스프링 빈 생성 이후의 후처리 방법
 *  - 트랜젝션을 DI 할 전략을 설정
 * 5. 부가기능 모듈화
 *  - 부가기능의 자료를 한데 모음으로써 프로그램파일들 마다 종속적이지 않고, 따로 유연하게 관리가능하다.
 * 6. AOP : 애스펙트 지향 프로그래밍t
 *  - OOP와 햇갈릴 수 잇지만, 프레임워크나 애플리케이션 측면에서 면단위로 기능을 유연하게 부가할 수 있다는 점에서 말하는 방법이다. 
 * */

public class PointcutExpressionTest{
	
	@Test
	public void methodSignaturePointcut()  throws SecurityException, NoSuchMethodException{
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("execution(public int com.framework.proxy.pointcut.Target.minus(int, int) throws java.lang.RuntimeException)");
		
		//Target.minus()
		assertThat(pointcut.getClassFilter().matches(Target.class) && pointcut.getMethodMatcher().matches(Target.class.getMethod("minus", int.class, int.class), null), is(true));
		
		//Target.plus()
		assertThat(pointcut.getClassFilter().matches(Target.class) && pointcut.getMethodMatcher().matches(Target.class.getMethod("plus", int.class, int.class), null), is(false));
		
		//Bean.method()
		assertThat(pointcut.getClassFilter().matches(Bean.class) && pointcut.getMethodMatcher().matches(Target.class.getMethod("method"), null), is(false));
		
		//반환형 제한없음, 모든 반환형
		//"execution(public * com.framework.proxy.pointcut.Target.minus(int, int) throws java.lang.RuntimeException)"
		
		//파라메터 무제한
		//"execution(public * com.framework.proxy.pointcut.Target.minus(..) throws java.lang.RuntimeException)"
		
		//모든 메서드
		//"execution(public * *(..) throws java.lang.RuntimeException)"
	}
	
	//외부에서 execution 내용을 받음.
	public void pointcutMatches(String expression, Boolean expected, Class<?> clazz, String methodName, Class<?>... args) throws Exception{
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(expression);
		
		assertThat(pointcut.getClassFilter().matches(clazz) && pointcut.getMethodMatcher().matches(clazz.getMethod(methodName, args), null), is(expected));
	}
	
	
	public void targetClassPointcutMatches(String expression, boolean... expected) throws Exception{
		pointcutMatches(expression, expected[0], Target.class, "hello");
		pointcutMatches(expression, expected[1], Target.class, "hello", String.class);
		pointcutMatches(expression, expected[2], Target.class, "plus", int.class, int.class);
		pointcutMatches(expression, expected[3], Target.class, "minus", int.class, int.class);
		pointcutMatches(expression, expected[4], Target.class, "method");
		pointcutMatches(expression, expected[5], Bean.class, "method");
	}
	
	@Test
	public void pointcut() throws Exception{
		targetClassPointcutMatches("execution(* *(..))", true, true, true, true, true, true);
	}
	

}
