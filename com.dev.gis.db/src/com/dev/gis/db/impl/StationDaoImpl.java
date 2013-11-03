package com.dev.gis.db.impl;

import java.io.InputStream;
import java.net.URL;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.dev.gis.db.api.IStationDao;

public class StationDaoImpl implements IStationDao {
	
	private static IStationDao  stationDao = null;
	private static ApplicationContext bf = null;
	
	public static IStationDao   getStationDao() {
		if (stationDao == null )
			stationDao = new StationDaoImpl();
		
		String appContextName = "applicationContext.xml";
		
		URL url = Activator.getDefault().getBundle().getResource(appContextName);
		System.out.println("url = "+url.getFile());
		
		if ( bf == null) {
			//bf = new FileSystemXmlApplicationContext(url.getFile());
			bf = new ClassPathXmlApplicationContext(appContextName);
			System.out.println("bf = "+bf);
		}
		
		

		return stationDao;
	}

	@Override
	public String getStationName() {
		return "hallo";
	}

}
