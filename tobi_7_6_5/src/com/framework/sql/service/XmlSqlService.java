package com.framework.sql.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.framework.exception.SqlNotFoundException;
import com.framework.sql.interfaces.SqlReader;
import com.framework.sql.interfaces.SqlRegistry;
import com.framework.sql.interfaces.SqlService;
import com.framework.sql.service.jaxb.SqlType;
import com.framework.sql.service.jaxb.Sqlmap;
import com.tobi.user.interfaces.UserDao;

public class XmlSqlService implements SqlService, SqlRegistry, SqlReader {
	
	private SqlReader sqlReader;
	private SqlRegistry sqlRegistry;
	
	public void setSqlReader(SqlReader sqlReader) {
		this.sqlReader = sqlReader;
	}

	public void setSqlRegistry(SqlRegistry sqlRegistry) {
		this.sqlRegistry = sqlRegistry;
	}

	public String findSql(String key) throws RuntimeException {
		String sql = sqlMap.get(key);
		if(sql == null) {
			 throw new SqlNotFoundException("No found SQL for the "+ key);
		} else {
			return sql;
		}
	}

	public void registerSql(String key, String sql) {
		sqlMap.put(key, sql);
	}




	private String sqlmapFile;
	
	public void setSqlmapFile(String sqlmapFile) {
		this.sqlmapFile = sqlmapFile;
	}
	
	public void read(SqlRegistry sqlRegistry) {
		String contextPath = Sqlmap.class.getPackage().getName();
		
		try {
			
			JAXBContext context = JAXBContext.newInstance(contextPath);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			InputStream is = UserDao.class.getResourceAsStream(this.sqlmapFile);
			Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(is);
			
			for(SqlType sql : sqlmap.getSql()) {
//				sqlMap.put(sql.getKey(), sql.getValue());
				sqlRegistry.registerSql(sql.getKey(), sql.getValue());
			}
			
			
		}catch(JAXBException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	@PostConstruct
	public void loadSql() {
		this.sqlReader.read(this.sqlRegistry);
//		String contextPath = Sqlmap.class.getPackage().getName();
//		
//		try {
//			
//			JAXBContext context = JAXBContext.newInstance(contextPath);
//			Unmarshaller unmarshaller = context.createUnmarshaller();
//			InputStream is = UserDao.class.getResourceAsStream(this.sqlmapFile);
//			Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(is);
//			
//			for(SqlType sql : sqlmap.getSql()) {
//				sqlMap.put(sql.getKey(), sql.getValue());
//			}
//			
//			
//		}catch(JAXBException e) {
//			throw new RuntimeException(e);
//		}
	}
	
	
	//Service 마다의 xml 내 sql문을 작성하여 로드하는 메소드
	private Map<String, String> sqlMap = new HashMap<String, String>();
	
	public XmlSqlService() {

		String contextPath = Sqlmap.class.getPackage().getName();
		
		try {
			
			JAXBContext context = JAXBContext.newInstance(contextPath);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			InputStream is = UserDao.class.getResourceAsStream("sqlmap.xml");
			Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(is);
			
			for(SqlType sql : sqlmap.getSql()) {
				sqlMap.put(sql.getKey(), sql.getValue());
			}
			
			
		}catch(JAXBException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public String getSql(String key) throws SqlRetrivalFailureException {
		
		try {
			return this.sqlRegistry.findSql(key);
		}catch(SqlNotFoundException e) {
			throw new SqlRetrivalFailureException(e);
		}
		
//		String sql = sqlMap.get(key);
//		if(sql == null) {
//			throw new SqlRetrivalFailureException("No found SQL for the "+ key);
//		}else {
//			return sql;
//		}
	}

}
