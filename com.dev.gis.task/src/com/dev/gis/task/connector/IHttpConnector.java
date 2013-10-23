package com.dev.gis.task.connector;

import com.bpcs.mdcars.protocol.LocationSearchResult;

public interface IHttpConnector {
	LocationSearchResult joiLocationSearch(String searchString);

}
