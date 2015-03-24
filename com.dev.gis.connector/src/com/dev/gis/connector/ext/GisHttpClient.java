package com.dev.gis.connector.ext;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

public class GisHttpClient {
	
	private final String TAG = "GisHttpClient";

	private static final String APPLICATION_JSON = "application/json";
	private final static String CHARSET_UTF8 = "UTF-8";
	private final CookieStore cookieStore = new BasicCookieStore();
	
	BasicHttpContext localContext = new BasicHttpContext();
	
	private ResponseHandler<String> myResponseHandler = new ResponseHandler<String>() {
	    public String handleResponse(final HttpResponse response)
	        throws HttpResponseException, IOException {
	        StatusLine statusLine = response.getStatusLine();
	        if (statusLine.getStatusCode() >= 300) {
	            throw new HttpResponseException(statusLine.getStatusCode(),
	                    statusLine.getReasonPhrase());
	        }

	        HttpEntity entity = response.getEntity();
	        return entity == null ? null : EntityUtils.toString(entity, CHARSET_UTF8);
	    }
	}; 
	
	private static GisHttpClient  gisHttpClient = null;
	
	public static GisHttpClient getInstance() {
		if ( gisHttpClient == null ) {
			gisHttpClient = new GisHttpClient();
		}
		return gisHttpClient;
	}
	
	private GisHttpClient() {
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);	
	}

	public String sendPostRequest(URI uri, String request)
			throws ClientProtocolException, IOException {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			
			HttpPost httpPost = new HttpPost(uri);
			//Log.i(TAG," executing request " + httpPost.getURI());
			
			httpPost.setHeader("Content-Type", APPLICATION_JSON);
			httpPost.addHeader("Accept", APPLICATION_JSON);			
			
			StringEntity entity = new StringEntity(request, CHARSET_UTF8);

			httpPost.setEntity(entity);

			return  httpclient.execute(httpPost, myResponseHandler,
					localContext);


		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}
	}
	
	public String sendGetRequest(URI uri)
			throws ClientProtocolException, IOException {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {

			HttpGet httpget = new HttpGet(uri);
			
			//Log.i(TAG," executing request " + httpget.getURI());

			httpget.addHeader("Accept", APPLICATION_JSON);
			httpget.setHeader("Content-Type", APPLICATION_JSON);

			return  httpclient.execute(httpget, myResponseHandler,
					localContext);


		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}
	}
	
	private void testHttp(  HttpResponse resp ) {
//		HttpResponse resp = httpclient.execute(httpget,localContext);
//		HttpEntity entity = resp.getEntity();
//		String result = EntityUtils.toString(entity, "UTF-8");
		
////		InputStream is = resp.getEntity().getContent();
//		
//		StringBuilder builder = new StringBuilder(); 
////		
//		BufferedReader reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent(),"utf-8"));
////		BufferedReader reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
//        String line;
//        while ((line = reader.readLine()) != null) {
//          builder.append(line);
//        }			
//		
//		String result = builder.toString();	
	}
}
