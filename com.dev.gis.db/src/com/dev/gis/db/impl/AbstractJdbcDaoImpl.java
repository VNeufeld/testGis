package com.dev.gis.db.impl;

import javax.sql.DataSource;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public abstract class AbstractJdbcDaoImpl extends JdbcDaoSupport  {

	public AbstractJdbcDaoImpl(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

}
