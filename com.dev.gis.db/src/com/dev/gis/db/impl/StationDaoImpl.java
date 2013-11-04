package com.dev.gis.db.impl;

import java.io.InputStream;
import java.net.URL;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.dev.gis.db.api.IStationDao;

public class StationDaoImpl extends AbstractJdbcDaoImpl implements IStationDao {
	
	private static DataSource ds = null;
	
	public StationDaoImpl(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub
	}

	private static IStationDao  stationDao = null;
	private static ApplicationContext bf = null;
	
	public static IStationDao   getStationDao() {
		if (stationDao == null )
			stationDao = new StationDaoImpl(ds);
		
		String appContextName = "applicationContext.xml";
		
		URL url = Activator.getDefault().getBundle().getResource(appContextName);
		System.out.println("url = "+url.getFile());
		
//		if ( bf == null) {
//			//bf = new FileSystemXmlApplicationContext(url.getFile());
//			bf = new ClassPathXmlApplicationContext(url.toExternalForm());
//			System.out.println("bf = "+bf);
//		}
		
		

		return stationDao;
	}

	@Override
	public String getStationName() {
		return "hallo";
	}

}
