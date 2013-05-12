package com.dev.gis.connector;

import org.apache.log4j.Logger;

import com.dev.gis.connector.api.IHttpConnector;

public abstract class HttpConnectorImpl implements IHttpConnector {
		protected  Logger logger = Logger.getLogger(this.getClass());
	
		protected GisHttpClient httpClient = new GisHttpClient();;


}
