package com.dev.gis.db.impl;

import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.convert.Property;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.dev.gis.db.api.IStationDao;

public class StationDaoImpl  extends AbstractJdbcDaoImpl implements IStationDao {
	
	private static DataSource ds ; //getMyDataSource();
	
	
	public StationDaoImpl(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub
	}

	private static IStationDao  stationDao = null;
	private static ApplicationContext bf = null;
	
	public static IStationDao   getStationDao() throws IOException, SQLException {
		if (stationDao == null )
			stationDao = new StationDaoImpl(getMyXDataSource());
		
		String appContextName = "applicationContext.xml";
		
		URL url = Activator.getDefault().getBundle().getResource(appContextName);
		System.out.println("url = "+url.getFile());
		
//		if ( bf == null) {
//			//bf = new FileSystemXmlApplicationContext(url.getFile());
//			bf = new ClassPathXmlApplicationContext(url.toExternalForm());
//			System.out.println("bf = "+bf);
//		}
		
		org.springframework.beans.factory.config.PropertyPlaceholderConfigurer pp = new PropertyPlaceholderConfigurer();
		

		return stationDao;
	}

	@Override
	public String getStationName() {
		return "hallo";
	}
	
	private static DataSource getMyDataSource() throws IOException {
		
		
		URL url = Activator.getDefault().getBundle().getResource("hsgw-database.properties");
		Properties p = new Properties();
		p.load(url.openStream());
		try {
			Class.forName(p.getProperty("jdbc.driverClassName")); // "oracle.jdbc.OracleDriver"
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(p.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(p.getProperty("jdbc.url"));
		dataSource.setUsername(p.getProperty("jdbc.username"));
		dataSource.setPassword(p.getProperty("jdbc.password"));
		return dataSource;
			

		
	}

	private static DataSource getMyXDataSource() throws IOException, SQLException {
		
		
		URL url = Activator.getDefault().getBundle().getResource("hsgw-database.properties");
		Properties p = new Properties();
		p.load(url.openStream());
		try {
			Class.forName(p.getProperty("jdbc.driverClassName")); // "oracle.jdbc.OracleDriver"
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

//		DriverManagerConnectionFactory dm = new DriverManagerConnectionFactory(p.getProperty("jdbc.url"), p.getProperty("jdbc.username"),p.getProperty("jdbc.password"));
//		dm.createConnection();
		

		BasicDataSource  bfs = new BasicDataSource();
		bfs.setUrl(p.getProperty("jdbc.url"));
		bfs.setUsername(p.getProperty("jdbc.username"));
		bfs.setPassword(p.getProperty("jdbc.password"));
		
		return bfs;
		
		
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName(p.getProperty("jdbc.driverClassName"));
//		dataSource.setUrl(p.getProperty("jdbc.url"));
//		dataSource.setUsername(p.getProperty("jdbc.username"));
//		dataSource.setPassword(p.getProperty("jdbc.password"));
//		return dataSource;
			

		
	}


}
