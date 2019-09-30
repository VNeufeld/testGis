package com.dev.gis.connector.api;

import java.net.URI;
import java.net.URISyntaxException;

import com.dev.gis.connector.sunny.HitType;

public abstract class AbstractJoiService {
	

	private final URI uri;
	private final Long operator;
	private final long language;

	
	public AbstractJoiService(Long operator, URI uri, long language) {
		this.uri = uri;
		this.operator = operator;
		this.language = language;		
	}
	

	protected String createLocationQuery(String searchString, HitType filter,
			String country) {
		String query = "operator=" + operator + "&lang=" + language
				+ "&search=" + searchString;
		
		if ( country != null && !country.isEmpty()) {
			query = query + "&country="+country;
		}

		if ( filter != null && filter != HitType.UNINITIALIZED)
			if ( filter == HitType.RAILWAY_STATION ) {
				query = query + "&resultType=2;6";
			}
			else
				query = query + "&resultType="+filter.getOrdinal();

		return query;
	}

	protected URI createUri(String query) throws URISyntaxException {
		String path = uri.getPath() + "/location";;

		return new URI(uri.getScheme(), null, uri.getHost(), uri.getPort(),path, query, null);

	}

}
