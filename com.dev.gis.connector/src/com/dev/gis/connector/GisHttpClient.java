package com.dev.gis.connector;

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
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.BasicHttpContext;

public class GisHttpClient {
	private final static String CHARSET_UTF8 = "UTF-8";
	private DefaultHttpClient httpclient = new DefaultHttpClient();
	private BasicHttpContext localContext = new BasicHttpContext();
	// Create a response handler
	private ResponseHandler<String> responseHandler = new BasicResponseHandler();

	public String sendPostRequest(URI uri, String request)
			throws IOException {

		httpclient = new DefaultHttpClient();
		localContext = new BasicHttpContext();
		// Create a response handler
		responseHandler = new BasicResponseHandler();
		try {
			HttpPost httpPost = new HttpPost(uri);
			httpPost.setHeader("Content-Type", "text/plain");

			StringEntity entity = new StringEntity(request, CHARSET_UTF8);

			httpPost.setEntity(entity);

			String response = httpclient.execute(httpPost, responseHandler,
					localContext);

			return response;

		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}
	}
	
	public String sendGetRequest(URI uri)
			throws IOException {

		httpclient = new DefaultHttpClient();
		localContext = new BasicHttpContext();
		// Create a response handler
		responseHandler = new BasicResponseHandler();
		try {
			
			HttpGet httpget = new HttpGet(uri);
			System.out.println("executing request " + httpget.getURI());

			httpclient.getParams().setParameter(
					CoreProtocolPNames.HTTP_CONTENT_CHARSET,CHARSET_UTF8);
			httpclient.getParams().setParameter(
					CoreProtocolPNames.HTTP_ELEMENT_CHARSET, CHARSET_UTF8);

			String response = httpclient.execute(httpget, responseHandler,
					localContext);
			return response;

		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}
	}
	

	public String startPostRequestAsJson(URI uri, String jsonString)
			throws  IOException {
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
