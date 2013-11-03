package com.dev.gis.db.api;

import com.dev.gis.db.impl.StationDaoImpl;

public class DaoFactory {
	public IStationDao  getStationDao() {
		return StationDaoImpl.getStationDao();
	}

}
