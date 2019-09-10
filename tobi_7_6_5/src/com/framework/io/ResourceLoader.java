package com.framework.io;

//file : file:/c:/packagename/file.txt
//classpath : classpath:file.txt
// X : WEB-INF/file.txt
//http : http://www.naver.com/file.txt

//context.xml <property name="varName" value="classPath:...." />

public interface ResourceLoader {
	Resource getResource(String location);
	
	
}
