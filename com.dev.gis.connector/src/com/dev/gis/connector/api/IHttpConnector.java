package com.dev.gis.connector.api;

import com.bpcs.mdcars.protocol.LocationSearchResult;

public interface IHttpConnector {
	LocationSearchResult joiLocationSearch(String searchString);

}
