package com.framework.sql.service.jaxm.test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import com.framework.sql.service.jaxm.SqlType;
import com.framework.sql.service.jaxm.Sqlmap;

//XML 을 읽어 JAXB 로 변환하는..
public class JaxbTest {

	@Test
	public void readSqlMap() throws JAXBException, IOException{
		String contextPath = Sqlmap.class.getPackage().getName();
		JAXBContext context = JAXBContext.newInstance(contextPath);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		
		Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(
				getClass().getResourceAsStream("sqlmap.xml"));
		
		List<SqlType> sqlList = sqlmap .getSql();
		
		assertThat(sqlList.size(), is(3));
		assertThat(sqlList.get(0).getKey(), is("add"));
		assertThat(sqlList.get(0).getValue(), is("insert"));
		assertThat(sqlList.get(1).getKey(), is("get"));
		assertThat(sqlList.get(1).getValue(), is("select"));
		assertThat(sqlList.get(2).getKey(), is("delete"));
		assertThat(sqlList.get(2).getValue(), is("delete"));
	}
	
}
