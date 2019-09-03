package com.framework.sql.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.framework.sql.interfaces.SqlService;
import com.framework.sql.service.jaxm.SqlType;
import com.framework.sql.service.jaxm.Sqlmap;
import com.tobi.user.interfaces.UserDao;

public class XmlSqlService implements SqlService {
	
	private Map<String, String> sqlMap = new HashMap<String, String>();
	
	public void XmlSqlService() {

		String contextPath = Sqlmap.class.getPackage().getName();
		
		try {
			
			JAXBContext context = JAXBContext.newInstance(contextPath);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			InputStream is = UserDao.class.getResourceAsStream("sqlmap.xml");
			Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(is);
			
			for(SqlType sql : sqlmap.getSql()) {
				((Map<String, String>) sqlmap).put(sql.getKey(), sql.getValue());
			}
			
			
		}catch(JAXBException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public String getSql(String key) throws SqlRetrivalFailureException {
		String sql = sqlMap.get(key);
		if(sql == null) {
			throw new SqlRetrivalFailureException("No found SQL for the "+ key);
		}else {
			return sql;
		}
	}

}
