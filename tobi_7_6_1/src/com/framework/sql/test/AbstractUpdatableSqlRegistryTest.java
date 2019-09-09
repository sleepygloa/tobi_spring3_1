package com.framework.sql.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.annotation.Before;
import org.junit.Test;

import com.framework.exception.SqlUpdateFailureException;
import com.framework.sql.interfaces.UpdatableSqlRegistry;
import com.framework.sql.service.SqlNotFoundException;

public abstract class AbstractUpdatableSqlRegistryTest {
	UpdatableSqlRegistry sqlRegistry;
	
	@Before(value = "")
	public void setUp() {
		sqlRegistry = createUpdatableSqlRegistry();
//		sqlRegistry = new ConcurrentHashMapSqlRegistry();
		sqlRegistry.registerSql("KEY1", "SQL1");
		sqlRegistry.registerSql("KEY2", "SQL2");
		sqlRegistry.registerSql("KEY3", "SQL3");
	}
	
	abstract protected UpdatableSqlRegistry createUpdatableSqlRegistry();
	
	protected void checkFindResult(String expected1, String expected2, String expected3) {
		assertThat(sqlRegistry.findSql("KEY1"), is(expected1));
		assertThat(sqlRegistry.findSql("KEY2"), is(expected2));
		assertThat(sqlRegistry.findSql("KEY3"), is(expected3));
	}
	
//	@Test
//	public void find() {
//		checkFindResult("SQL1", "SQL2", "SQL3");
//	}
//	
//
//	@Test(expected=SqlNotFoundException.class)
//	public void unknownKey() {
//		sqlRegistry.findSql("SQL9999!@#$");
//	}
//	
//	
//	@Test
//	public void updateSingle() {
//		sqlRegistry.updateSql("KEY2", "Modified2");
//		checkFindResult("SQL1", "Modified2", "SQL3");
//	}
//	
//	@Test
//	public void updateMulti() {
//		Map<String, String> sqlmap = new HashMap<String, String>();
//		sqlmap.put("KEY1", "Modified1");
//		sqlmap.put("KEY3", "Modified3");
//		
//		sqlRegistry.updateSql(sqlmap);
//		checkFindResult("Modified1", "SQL2", "Modified3");
//	}
//	
//	@Test(expected=SqlUpdateFailureException.class)
//	public void updateWithNotExistingKey() {
//		sqlRegistry.updateSql("SQL9999!@#$", "Modified2");
//	}
	
}
