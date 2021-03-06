package com.dev.gis.connector;

import java.io.File;

//http://stackoverflow.com/questions/15578429/why-is-httpclient-is-refreshing-the-jsession-id-for-every-request
//http://stackoverflow.com/questions/4166129/apache-httpclient-4-0-3-how-do-i-set-cookie-with-sessionid-for-post-request
	
	
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.ext.BusinessException;

public class GisHttpClient {
	
	static final String HASH_PREFIX = "BPCS JOI interface";
	
	protected Logger logger = Logger.getLogger(Class.class);
	
	private final static String CHARSET_UTF8 = "UTF-8";
	
	int timeout = 10000;
	RequestConfig requestConfig = RequestConfig.custom()
			.setConnectTimeout(timeout)
			.setConnectionRequestTimeout(timeout)
			.setSocketTimeout(timeout)
			.setCookieSpec(CookieSpecs.STANDARD)
			.build();

	CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();

	HttpClientContext localContext = createContext();

	//private CloseableHttpClient httpclient = HttpClients.createDefault();
	
	// Create a response handler
	private ResponseHandler<String> responseHandler = new BasicResponseHandler();
	
	private String AWSALB_Cookie = null;
	
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
			
			CookieStore cookieStore = localContext.getCookieStore();
			List<Cookie> cookies = cookieStore.getCookies();
			for( Cookie cookie : cookies) {
				logger.info("cookie " + cookie.getName() + " value : " +cookie.getValue());
			}
			
			String response = httpclient.execute(httpget, getResponseHandler(),
					localContext);
			
//			logger.info("response = " + response);
			logger.debug("localContext " + localContext.toString());
			
			return response;

		} 
		catch(Exception err) {
			logger.info("localContext " + localContext.toString());
			logger.info("responseHandler " + responseHandler.toString());
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
			
			return response;

		} 
		catch(Exception err) {
			logger.info("localContext " + localContext.toString());
			logger.info("responseHandler " + responseHandler.toString());
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
			
			HttpPost httpPost = createHttpPost(uri, jsonString);
			
			createSecurityHash(jsonString, httpPost);
			
			logger.info("httpclient "+httpclient + ". Executing POST request " + httpPost.getURI());
	
			String response = httpclient.execute(httpPost, getResponseHandler(),
					localContext);
			logger.info("localContext " + localContext.toString());
			
			CookieStore cookieStore = localContext.getCookieStore();
			List<Cookie> cookies = cookieStore.getCookies();
			for( Cookie cookie : cookies) {
				logger.info("cookie " + cookie.getName() + " value : " +cookie.getValue());
				if ( cookie.getName().equals("AWSALB")) {
					AWSALB_Cookie = cookie.getValue();
				}
			}

	
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

	public String startUpload(URI uri, byte[] image, String fileName)
			throws  IOException {
		
		try {
			
			//uri = new URI("http://localhost:8080/cm-web-joi/upload");
			
			HttpPost httpPost = new HttpPost(uri);
			
			ContentType contentType = ContentType.create("image/jpeg");
			
			 MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			    builder.addBinaryBody(fileName, image,contentType, " xf3");
			 
		    HttpEntity multipart = builder.build();
			httpPost.setEntity(multipart);
	
			//httpPost.addHeader(new BasicHeader("Content-Type", "multipart/form-data"));
			
//			List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
//			nvps.add(new BasicNameValuePair("XML-Request", "xder"));
//			nvps.add(new BasicNameValuePair("callerCode", "caller"));
//			nvps.add(new BasicNameValuePair("password", "pwd"));
//			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
//
//			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");			
			
			logger.info("httpclient "+httpclient + ". Executing Upload request " + httpPost.getURI());
	
			@SuppressWarnings("restriction")
			String response = httpclient.execute(httpPost, getResponseHandler(),
					localContext);
			
			logger.info("localContext " + localContext.toString());
			
			String sessionId = (String)localContext.getAttribute("JSESSIONID");
			logger.info("sessionId " + sessionId);
	
			return response;
		}
		catch(Exception err) {
			logger.info("localContext " + localContext.toString());
			logger.info("responseHandler " + responseHandler.toString());
			logger.info(err.getMessage(), err);
			if ( err instanceof BusinessException) {
				logger.info(err.getMessage());
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

	private HttpClientContext createContext() {
		HttpClientContext context = HttpClientContext.create();

		// Use custom cookie store if necessary.
		CookieStore cookieStore = new BasicCookieStore();
		context.setCookieStore(cookieStore);

		return context;
	}
	protected HttpPost createHttpPost(URI uri, String xml) {
		HttpPost httpPost = new HttpPost(uri);

		StringEntity entity = new StringEntity(xml, "utf-8");

		httpPost.setEntity(entity);
		httpPost.addHeader(new BasicHeader("Content-Type", "application/json;charset=utf-8"));			
		httpPost.addHeader(new BasicHeader("Accept", "application/json;charset=utf-8"));
		
		return httpPost;
	}

}
