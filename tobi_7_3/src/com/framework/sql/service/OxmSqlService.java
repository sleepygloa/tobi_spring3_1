package com.framework.sql.service;


import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

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
	
	public void setSqlRegistry(SqlRegistry sqlRegistry) {
		this.sqlRegistry = sqlRegistry;
	}

	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.oxmSqlReader.setUnmarshaller(unmarshaller);
	}
	
	
	
	
	@PostConstruct
	public void loadSql() {
		this.oxmSqlReader.read(sqlRegistry);
	}
	
	@Override
	public String getSql(String key) throws SqlRetrivalFailureException {
		try {
			return this.sqlRegistry.findSql(key);
		}catch(SqlNotFoundException e) {
			throw new SqlRetrivalFailureException(e);
		}
	}
	
	private class OxmSqlReader implements SqlReader{
		private Unmarshaller unmarshaller;
		private String sqlmapFile;

		public void setUnmarshaller(Unmarshaller unmarshaller) {
			this.unmarshaller = unmarshaller;
		}

		public void setSqlmapFile(String sqlmapFile) {
			this.sqlmapFile = sqlmapFile;
		}

		@Override
		public void read(SqlRegistry sqlregistry) {
			try {
				Source source = new StreamSource(
					UserDao.class.getResourceAsStream(this.sqlmapFile)
					);
				Sqlmap sqlmap = (Sqlmap) this.unmarshaller.unmarshal(source);
				
				for(SqlType sql : sqlmap.getSql()) {
					sqlregistry.registerSql(sql.getKey(), sql.getValue());
				}
				
			}catch(IOException e) {
				throw new IllegalArgumentException(this.sqlmapFile+ "파일을 가져올수 없습니다.");
			}
		}
			
	}
	
	
	

	
	
}
