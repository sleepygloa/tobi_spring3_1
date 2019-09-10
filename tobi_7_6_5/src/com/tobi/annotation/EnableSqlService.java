package com.tobi.annotation;

import org.springframework.context.annotation.Import;

import com.tobi.xml.SqlServiceContext;

@Import(value=SqlServiceContext.class)
public @interface EnableSqlService {

}
