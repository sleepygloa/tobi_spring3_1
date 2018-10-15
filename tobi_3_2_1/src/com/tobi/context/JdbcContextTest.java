package com.tobi.context;

import com.tobi.config.AppConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JdbcContextTest {

	@Test
	public void testDeleteAll() throws Exception {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		JdbcContext jdbcContext = applicationContext.getBean("jdbcContext", JdbcContext.class);

		jdbcContext.deleteAll();
	}
}