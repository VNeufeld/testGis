package com.dev.gis.app;

import com.dev.gis.db.api.IDaoFactory;

public class Components {
	private static Components instance;
	
	private IDaoFactory daoFactory;
	
	public Components() {
		instance = this;
	}

	public static Components getInstance() {
		return instance;
	}
	public void setDaoFactory(IDaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public void unsetDaoFactory(IDaoFactory daoFactory) {
		this.daoFactory = null;
	}

	public IDaoFactory getDaoFactory() {
		return daoFactory;
	}
	
	

}
