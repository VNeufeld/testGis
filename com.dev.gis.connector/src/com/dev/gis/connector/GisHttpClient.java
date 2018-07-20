package com.dev.gis.connector;

//http://stackoverflow.com/questions/15578429/why-is-httpclient-is-refreshing-the-jsession-id-for-every-request
//http://stackoverflow.com/questions/4166129/apache-httpclient-4-0-3-how-do-i-set-cookie-with-sessionid-for-post-request
	
	
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
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
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.ext.BusinessException;

public class GisHttpClient {
	
	static final String HASH_PREFIX = "BPCS JOI interface";
	
	protected Logger logger = Logger.getLogger(Class.class);
	
	private final static String CHARSET_UTF8 = "UTF-8";
	private DefaultHttpClient httpclient = new DefaultHttpClient();
	private BasicHttpContext localContext = new BasicHttpContext();
	// Create a response handler
	private ResponseHandler<String> responseHandler = new BasicResponseHandler();
	
	private String savedSessionId = null;
	
	protected ResponseHandler<String> getResponseHandler() {

		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

			@Override
			public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					for ( Header header : response.getAllHeaders()) {
						logger.info(" header : " + header.getName() + " value : " + header.getValue());
					}
					
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				} else {
					String errorText = response.getStatusLine().getReasonPhrase();
					String errorCode = String.valueOf(status);
					logger.error(" errorCode : " +errorCode + " errorText " + errorText);
					
					HttpEntity entity = response.getEntity();
					if (entity == null )
						throw new ClientProtocolException("Unexpected response status: " + status);
					else {
						String xml = EntityUtils.toString(entity);
						logger.error(" error response : " + xml );
						throw new BusinessException(xml);
					}
				}
			}

		};
		return responseHandler;
	}		
	

	public String sendGetRequest(URI uri)
			throws IOException {

		try {
			
			HttpGet httpget = new HttpGet(uri);
			httpget.addHeader(new BasicHeader("Content-Type", "application/json;charset=utf-8"));			
			httpget.addHeader(new BasicHeader("Accept", "application/json;charset=utf-8"));
			
			logger.info("httpclient "+httpclient + ". Executing GET request " + httpget.getURI());
			
			createSecurityHashForGet( httpget);
			
			
			
			String response = httpclient.execute(httpget, getResponseHandler(),
					localContext);
			
//			logger.info("response = " + response);
			logger.debug("localContext " + localContext.toString());
			
			return response;

		} 
		catch(Exception err) {
			logger.info("localContext " + localContext.toString());
			logger.info("responseHandler " + responseHandler.toString());
			CookieStore cookieStore = httpclient.getCookieStore();
			logger.info("cookieStore " + cookieStore);
			if ( err instanceof BusinessException) {
				logger.info(err.getMessage());
				throw err;
			}
			else
				logger.info(err.getMessage(), err);			
			
		}
		return null;
	}

	public String sendGetRequestWithoutToken(URI uri)
			throws IOException {

		try {
			
			HttpGet httpget = new HttpGet(uri);
			httpget.addHeader(new BasicHeader("Content-Type", "application/json;charset=utf-8"));			
			httpget.addHeader(new BasicHeader("Accept", "application/json;charset=utf-8"));
			
			logger.info("httpclient "+httpclient + ". Executing GET request without token " + httpget.getURI());

			String response = httpclient.execute(httpget, getResponseHandler(),
					localContext);
			
			//logger.info("localContext " + localContext.toString());
			
			String sessionId = (String)localContext.getAttribute("JSESSIONID");
			
			CookieStore cookieStore = httpclient.getCookieStore();
			//logger.info("cookieStore " + cookieStore);
			//logger.info("response = " + response);
			
			return response;

		} 
		catch(Exception err) {
			logger.info("localContext " + localContext.toString());
			logger.info("responseHandler " + responseHandler.toString());
			CookieStore cookieStore = httpclient.getCookieStore();
			logger.info("cookieStore " + cookieStore);
			if ( err instanceof BusinessException) {
				logger.info(err.getMessage());
				throw err;
			}
			else
				logger.info(err.getMessage(), err);			
			
		}
		return null;
	}
	

	protected void createSecurityHashForGet(HttpGet httpget) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		
	}


	public String startPostRequestAsJson(URI uri, String jsonString)
			throws  IOException {
		try {
			
			
			
			
			HttpPost httpPost = new HttpPost(uri);
	
			StringEntity entity = new StringEntity(jsonString, CHARSET_UTF8);
	
			httpPost.setEntity(entity);

			httpPost.addHeader(new BasicHeader("Content-Type", "application/json;charset=utf-8"));			
			httpPost.addHeader(new BasicHeader("Accept", "application/json;charset=utf-8"));
			
			createSecurityHash(jsonString, httpPost);
			
			logger.info("httpclient "+httpclient + ". Executing POST request " + httpPost.getURI());
	
			String response = httpclient.execute(httpPost, getResponseHandler(),
					localContext);
			logger.debug("localContext " + localContext.toString());
			
//			String sessionId = (String)localContext.getAttribute("JSESSIONID");
//			logger.info("sessionId " + sessionId);
	
			return response;
		}
		catch(Exception err) {
			logger.info("localContext " + localContext.toString());
			logger.info("responseHandler " + responseHandler.toString());
			logger.info(err.getMessage(), err);
			if ( err instanceof BusinessException) {
				logger.info(err.getMessage());
				throw err;
			}
			else
				logger.info(err.getMessage(), err);
			
		}
		return null;
	}


	protected void createSecurityHash(String jsonString, HttpPost httpPost)
			throws UnsupportedEncodingException {
		boolean encode = AdacModelProvider.INSTANCE.authorization;
		if ( encode ) {
			String stringToHash = HASH_PREFIX + "POST" + jsonString;
			String hashValue = Hash.calculateMD5Hash(stringToHash);
			httpPost.addHeader(new BasicHeader(HttpHeaders.AUTHORIZATION, "BPCS:"+hashValue));
			logger.info("Hash Value = " + hashValue);
			
		}
	}
	
	public String startPutRequestAsJson(URI uri, String jsonString)
			throws  IOException {
		HttpPut httput = new HttpPut(uri);

		
		StringEntity entity = new StringEntity(jsonString, CHARSET_UTF8);

		httput.setEntity(entity);
		httput.addHeader("Accept", "application/json;charset=utf-8");
		httput.setHeader("Content-Type", "application/json;charset=utf-8");

		try {
			String response = httpclient.execute(httput, getResponseHandler(),
					localContext);
	
			logger.debug("localContext " + localContext.toString());
	
			CookieStore cookieStore = httpclient.getCookieStore();
	
			return response;
		}
		catch(Exception err) {
			logger.info("localContext " + localContext.toString());
			logger.info("responseHandler " + responseHandler.toString());
			logger.info(err.getMessage(), err);
			if ( err instanceof BusinessException) {
				logger.info(err.getMessage());
				throw err;
			}
			else
				logger.info(err.getMessage(), err);
			
		}
		return null;		
	}

}
