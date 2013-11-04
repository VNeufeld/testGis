package com.dev.gis.db.impl;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.osgi.service.component.ComponentContext;

import com.dev.gis.db.api.IDaoFactory;
import com.dev.gis.db.api.IStationDao;

public class DaoFactory implements IDaoFactory{
	private static Logger logger = Logger.getLogger(DaoFactory.class);

	public IStationDao  getStationDao() {
		try {
			return StationDaoImpl.getStationDao();
		} catch (IOException e) {
			logger.error(e.getMessage(),e);;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	protected void startup(ComponentContext componentContext) {
		logger.info("started DaoFactory");
	}

	protected void shutdown(ComponentContext componentContext) {
		logger.info("closing DaoFactory");
	}

}
