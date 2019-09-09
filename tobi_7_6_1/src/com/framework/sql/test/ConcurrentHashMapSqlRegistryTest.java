package com.framework.sql.test;

import com.framework.sql.interfaces.UpdatableSqlRegistry;
import com.framework.sql.service.ConcurrentHashMapSqlRegistry;

public class ConcurrentHashMapSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {
	protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
		return new ConcurrentHashMapSqlRegistry();
	}
}
