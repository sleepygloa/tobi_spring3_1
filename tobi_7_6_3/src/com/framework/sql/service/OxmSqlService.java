package com.framework.sql.service;


import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;

import com.framework.sql.interfaces.SqlReader;
import com.framework.sql.interfaces.SqlRegistry;
import com.framework.sql.interfaces.SqlService;
import com.framework.sql.service.jaxb.SqlType;
import com.framework.sql.service.jaxb.Sqlmap;
import com.tobi.user.interfaces.UserDao;

public class OxmSqlService implements SqlService {

	private final OxmSqlReader oxmSqlReader = new OxmSqlReader();
	
	private SqlRegistry sqlRegistry = new HashMapSqlRegistry();
	
	private final BaseSqlService baseSqlService = new BaseSqlService();
	
	public void setSqlmap(Resource sqlmap) {
		this.oxmSqlReader.setSqlmap(sqlmap);
	}
	
	public void setSqlRegistry(SqlRegistry sqlRegistry) {
		this.sqlRegistry = sqlRegistry;
	}

	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.oxmSqlReader.setUnmarshaller(unmarshaller);
	}
	
	
	
	
	@PostConstruct
	public void loadSql() {
//		this.oxmSqlReader.read(sqlRegistry);
		
		this.baseSqlService.setSqlReader(this.oxmSqlReader);
		this.baseSqlService.setSqlRegistry(this.sqlRegistry);
		
		this.baseSqlService.loadSql();
	}
	
	@Override
	public String getSql(String key) throws SqlRetrivalFailureException {
		return this.baseSqlService.getSql(key);
		
//		try {
//			return this.sqlRegistry.findSql(key);
//		}catch(SqlNotFoundException e) {
//			throw new SqlRetrivalFailureException(e);
//		}
	}
	
	private class OxmSqlReader implements SqlReader{
		private Unmarshaller unmarshaller;
		private String sqlmapFile;
		
		private Resource sqlmap = new ClassPathResource("/com/tobi/user/xml/sqlmap.xml", UserDao.class);
		
		public void setSqlmap(Resource sqlmap) {
			this.sqlmap = sqlmap;
		}

		public void setUnmarshaller(Unmarshaller unmarshaller) {
			this.unmarshaller = unmarshaller;
		}

		public void setSqlmapFile(String sqlmapFile) {
			this.sqlmapFile = sqlmapFile;
		}

		@Override
		public void read(SqlRegistry sqlregistry) {
			try {
				Source source = new StreamSource(sqlmap.getInputStream());
				Sqlmap sqlmap = (Sqlmap) this.unmarshaller.unmarshal(source);
				
				for(SqlType sql : sqlmap.getSql()) {
					sqlregistry.registerSql(sql.getKey(), sql.getValue());
				}
				
			}catch(IOException e) {
//				throw new IllegalArgumentException(this.sqlmapFile+ "파일을 가져올수 없습니다.");
				throw new IllegalArgumentException(this.sqlmap.getFilename()+ "파일을 가져올수 없습니다.");
			}
		}
			
	}
	
	
	

	
	
}
