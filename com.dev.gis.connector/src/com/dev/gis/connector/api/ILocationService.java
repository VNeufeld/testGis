package com.dev.gis.connector.api;

import com.dev.gis.connector.sunny.HitType;
import com.dev.gis.connector.sunny.LocationSearchResult;

public interface ILocationService {
	public LocationSearchResult joiLocationSearch(String searchString, HitType filter, String country);

}
