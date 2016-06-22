package com.dev.gis.connector;

//http://stackoverflow.com/questions/15578429/why-is-httpclient-is-refreshing-the-jsession-id-for-every-request
//http://stackoverflow.com/questions/4166129/apache-httpclient-4-0-3-how-do-i-set-cookie-with-sessionid-for-post-request
	
	
import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.log4j.Logger;

import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.VehicleHttpService;

public class GisHttpClient {
	
	static final String HASH_PREFIX = "BPCS JOI interface";
	
	private static Logger logger = Logger.getLogger(GisHttpClient.class);
	
	private final static String CHARSET_UTF8 = "UTF-8";
	private DefaultHttpClient httpclient = new DefaultHttpClient();
	private BasicHttpContext localContext = new BasicHttpContext();
	// Create a response handler
	private ResponseHandler<String> responseHandler = new BasicResponseHandler();

	public String sendGetRequest(URI uri)
			throws IOException {

		try {
			
			HttpGet httpget = new HttpGet(uri);
			httpget.addHeader(new BasicHeader("Content-Type", "application/json;charset=utf-8"));			
			httpget.addHeader(new BasicHeader("Accept", "application/json;charset=utf-8"));			
			logger.info("httpclient "+httpclient + ". Executing GET request " + httpget.getURI());
			
			String response = httpclient.execute(httpget, responseHandler,
					localContext);
			logger.info("response = " + response);
			logger.info("localContext " + localContext.toString());
			CookieStore cookieStore = httpclient.getCookieStore();
			logger.info("cookieStore " + cookieStore);
			
			return response;

		} 
		catch(Exception err) {
			logger.info("localContext " + localContext.toString());
			logger.info("responseHandler " + responseHandler.toString());
			CookieStore cookieStore = httpclient.getCookieStore();
			logger.info("cookieStore " + cookieStore);

			logger.info(err.getMessage(), err);
			
		}
		return null;
	}
	

	public String startPostRequestAsJson(URI uri, String jsonString)
			throws  IOException {
		try {
			
			boolean encode = AdacModelProvider.INSTANCE.authorization;
			
			
			
			HttpPost httpPost = new HttpPost(uri);
	
			StringEntity entity = new StringEntity(jsonString, CHARSET_UTF8);
	
			httpPost.setEntity(entity);

			httpPost.addHeader(new BasicHeader("Content-Type", "application/json;charset=utf-8"));			
			httpPost.addHeader(new BasicHeader("Accept", "application/json;charset=utf-8"));
			
			if ( encode ) {
				String stringToHash = HASH_PREFIX + "POST" + jsonString;
				String hashValue = Hash.calculateMD5Hash(stringToHash);
				httpPost.addHeader(new BasicHeader(HttpHeaders.AUTHORIZATION, "BPCS:"+hashValue));
				logger.info("Hash Value = " + hashValue);
				
			}
			
			
			logger.info("httpclient "+httpclient + ". Executing POST request " + httpPost.getURI());
			
	
			String response = httpclient.execute(httpPost, responseHandler,
					localContext);
			logger.info("response = " + response);
			logger.info("localContext " + localContext.toString());
	
			CookieStore cookieStore = httpclient.getCookieStore();
			logger.info("cookieStore " + cookieStore);
			logger.info("----------------------------------------");
	
			return response;
		}
		catch(Exception err) {
			logger.info("localContext " + localContext.toString());
			logger.info("responseHandler " + responseHandler.toString());
			logger.info(err.getMessage(), err);
			
		}
		return null;
	}
	
	public String startPutRequestAsJson(URI uri, String jsonString)
			throws  IOException {
		HttpPut httput = new HttpPut(uri);

		StringEntity entity = new StringEntity(jsonString, CHARSET_UTF8);

		httput.setEntity(entity);
		httput.addHeader("Accept", "application/json;charset=utf-8");
		httput.setHeader("Content-Type", "application/json;charset=utf-8");

		String response = httpclient.execute(httput, responseHandler,
				localContext);

		logger.info("response = " + response);
		logger.info("localContext " + localContext.toString());

		CookieStore cookieStore = httpclient.getCookieStore();
		logger.info("cookieStore " + cookieStore);
		logger.info("----------------------------------------");

		return response;
	}

}
