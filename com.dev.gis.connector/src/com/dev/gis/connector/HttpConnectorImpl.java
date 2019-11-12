package com.dev.gis.connector;

import org.apache.log4j.Logger;

public abstract class HttpConnectorImpl  {
		protected  Logger logger = Logger.getLogger(this.getClass());
	
		protected GisHttpClient httpClient = new GisHttpClient();;


}
