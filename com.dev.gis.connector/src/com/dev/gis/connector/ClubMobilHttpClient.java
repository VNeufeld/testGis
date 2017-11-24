package com.dev.gis.connector;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicHeader;

import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.joi.protocol.Token;

public class ClubMobilHttpClient extends GisHttpClient {
	public static String CLUBMOBIL_CREDENTIALS_GETTOKEN = "/credentials/getToken";
	public static String API_KEY = "BPCS_POST";

	@Override
	protected void createSecurityHash(String jsonString,HttpPost httpPost)
			throws UnsupportedEncodingException {
		boolean encode = ClubMobilModelProvider.INSTANCE.authorization;
		if ( encode ) {
			logger.info("createSecurityHash ");
			Token token = getToken();
			if  ( token != null) {
				logger.info("Token = " + token.getToken());
				String stringToHash = API_KEY + token.getToken();
				String hashValue = Hash.calculateMD5Hash(stringToHash);
				httpPost.addHeader(new BasicHeader(HttpHeaders.AUTHORIZATION, "BPCS:"+hashValue));
				httpPost.addHeader(new BasicHeader("Token", token.getToken()));
				logger.info("Hash Value = " + hashValue);
			}
			
		}
	}
	private URI getServerURI(String param ) throws URISyntaxException {
		
		String server = ClubMobilModelProvider.INSTANCE.serverUrl;
		
		URI uri = new URI(server+param);
		return uri;
		
	}
	
	@Override
	protected void createSecurityHashForGet(HttpGet httpget) throws UnsupportedEncodingException {
		boolean encode = ClubMobilModelProvider.INSTANCE.authorization;
		if ( encode ) {
			logger.info("createSecurityHash ");
			Token token = getToken();
			if  ( token != null) {
				logger.info("Token = " + token.getToken());
				String stringToHash = API_KEY + token.getToken();
				String hashValue = Hash.calculateMD5Hash(stringToHash);
				httpget.addHeader(new BasicHeader(HttpHeaders.AUTHORIZATION, "BPCS:"+hashValue));
				httpget.addHeader(new BasicHeader("Token", token.getToken()));
				logger.info("Hash Value = " + hashValue);
			}
			
		}
		
	}

	
	private Token getToken() {
		String response = " ";
		try {

			URI uri = getServerURI(CLUBMOBIL_CREDENTIALS_GETTOKEN);
			
			logger.info("getToken Request = "+uri);
			
			response =  sendGetRequestWithoutToken(uri);
			logger.info("response = "+response);
			return JsonUtils.createResponseClassFromJson(response, Token.class);
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;
	}
}
