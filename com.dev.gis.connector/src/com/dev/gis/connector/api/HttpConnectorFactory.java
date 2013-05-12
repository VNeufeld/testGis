package com.dev.gis.connector.api;

import com.dev.gis.connector.LocationSearchConnector;
import com.dev.gis.task.execution.api.LocationSearchTask;

public class HttpConnectorFactory {
	
	public static IHttpConnector createLocationSearchConnector(LocationSearchTask locationSearchTask) {
		return new LocationSearchConnector(locationSearchTask);
	}

	
}
