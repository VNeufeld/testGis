package com.dev.gis.app;


public class Components {
	private static Components instance;
	
	
	public Components() {
		instance = this;
	}

	public static Components getInstance() {
		return instance;
	}
	
	

}
