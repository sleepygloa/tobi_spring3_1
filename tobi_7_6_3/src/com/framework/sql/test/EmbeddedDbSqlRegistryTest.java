package com.framework.sql.test;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

import org.junit.After;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import com.framework.embeddedDb.service.EmbeddedDbSqlRegistry;
import com.framework.sql.interfaces.UpdatableSqlRegistry;

public class EmbeddedDbSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {
	EmbeddedDatabase db;
	
	protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
		db = new EmbeddedDatabaseBuilder()
				.setType(HSQL)
				.addScript("classpath:/com/framework/embeddedDb/xml/sqlRegistrySchema.sql")
				.build();
		
		EmbeddedDbSqlRegistry embeddedDbSqlRegistry = new EmbeddedDbSqlRegistry();
		embeddedDbSqlRegistry.setDataSource(db);
		
		return embeddedDbSqlRegistry;
	}
	
	@After
	public void tearDown() {
		db.shutdown();
	}
	
}
