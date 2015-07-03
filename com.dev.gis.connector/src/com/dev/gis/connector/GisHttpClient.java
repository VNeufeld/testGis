package com.dev.gis.connector;

//http://stackoverflow.com/questions/15578429/why-is-httpclient-is-refreshing-the-jsession-id-for-every-request
//http://stackoverflow.com/questions/4166129/apache-httpclient-4-0-3-how-do-i-set-cookie-with-sessionid-for-post-request
	
	
import java.io.IOException;
import java.net.URI;

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

import com.dev.gis.connector.api.VehicleHttpService;

public class GisHttpClient {
	
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
			System.out.println("executing request " + httpget.getURI());

			
			String response = httpclient.execute(httpget, responseHandler,
					localContext);
			return response;

		} 
		catch(Exception err) {
			System.out.println("localContext " + localContext.toString());
			System.out.println("responseHandler " + responseHandler.toString());
			logger.info(err.getMessage(), err);
			
		}
		return null;
	}
	

	public String startPostRequestAsJson(URI uri, String jsonString)
			throws  IOException {
		try {
			HttpPost httpPost = new HttpPost(uri);
	
			StringEntity entity = new StringEntity(jsonString, CHARSET_UTF8);
	
			httpPost.setEntity(entity);
			httpPost.addHeader("Accept", "application/json");
			httpPost.setHeader("Content-Type", "application/json");
	
			String response = httpclient.execute(httpPost, responseHandler,
					localContext);
			System.out.println("response = " + response);
			System.out.println("localContext " + localContext.toString());
	
			CookieStore cookieStore = httpclient.getCookieStore();
			System.out.println("cookieStore " + cookieStore);
			System.out.println("----------------------------------------");
	
			return response;
		}
		catch(Exception err) {
			System.out.println("localContext " + localContext.toString());
			System.out.println("responseHandler " + responseHandler.toString());
			logger.info(err.getMessage(), err);
			
		}
		return null;
	}
	
	public String startPutRequestAsJson(URI uri, String jsonString)
			throws  IOException {
		HttpPut httput = new HttpPut(uri);

		StringEntity entity = new StringEntity(jsonString, CHARSET_UTF8);

		httput.setEntity(entity);
		httput.addHeader("Accept", "application/json");
		httput.setHeader("Content-Type", "application/json");

		String response = httpclient.execute(httput, responseHandler,
				localContext);

		System.out.println("response = " + response);
		System.out.println("localContext " + localContext.toString());

		CookieStore cookieStore = httpclient.getCookieStore();
		System.out.println("cookieStore " + cookieStore);
		System.out.println("----------------------------------------");

		return response;
	}


	private String startGetRequest(URI uri) throws ClientProtocolException,
			IOException {

		HttpGet httpget = new HttpGet(uri);
		System.out.println("executing request " + httpget.getURI());

		httpclient.getParams().setParameter(
				CoreProtocolPNames.HTTP_CONTENT_CHARSET,CHARSET_UTF8);
		httpclient.getParams().setParameter(
				CoreProtocolPNames.HTTP_ELEMENT_CHARSET, CHARSET_UTF8);

//		httpget.getParams().setParameter(
//				CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");
//		httpget.getParams().setParameter(
//				CoreProtocolPNames.HTTP_ELEMENT_CHARSET, "UTF-8");

//		httpget.addHeader("charset", "utf-8");
//		httpget.addHeader("Accept-Charset", "utf-8");
		String responseBody = httpclient.execute(httpget, responseHandler,
				localContext);
		System.out.println("localContext " + localContext.toString());
		System.out.println(responseBody);

		CookieStore cookieStore = httpclient.getCookieStore();
		System.out.println("cookieStore " + cookieStore);
		System.out.println("----------------------------------------");

		return responseBody;
	}

}
