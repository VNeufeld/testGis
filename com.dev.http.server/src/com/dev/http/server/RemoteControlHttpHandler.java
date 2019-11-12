package com.dev.http.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.dev.http.server.services.SessionControllerAdvanced;



public class RemoteControlHttpHandler extends AbstractHandler {

	protected final static Logger logger = Logger.getLogger(RemoteControlHttpHandler.class);

	private final IServerCallback callback;
	

	public RemoteControlHttpHandler() {
		callback = null;
	}



	public RemoteControlHttpHandler(IServerCallback callback) {
		// TODO Auto-generated constructor stub
		this.callback = callback;
	}



	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		
		String xml = getBpcsXmlRequest(request);
		String xmlResponseStr ="";
		logger.info("HTTP Request : "+xml);
		if ( StringUtils.isNotBlank(xml)) {
		
			callback.receive(xml);
			
			long start = System.currentTimeMillis();
			
			LogEntry entry = new LogEntry(new Date());
			entry.setRequest(xml);
	
			callback.update(entry);
			
			
			xmlResponseStr = new SessionControllerAdvanced().executeXmlRequest(request, xml, false);
			
			entry.setResponse(xmlResponseStr);
			entry.setEnd(new Date());
			entry.setDuration(System.currentTimeMillis() - start);
			
			callback.update(entry);
		}
		else
			xmlResponseStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + " OK for empty request";
		
		response.setStatus(HttpServletResponse.SC_OK);
		
		//response.setContentType("text/plain;charset=utf-8");
		response.setContentType("text/xml;charset=utf-8");

		response.getWriter().write(xmlResponseStr);
		response.getWriter().flush();

		callback.receive(" execution completed ");
		
		logger.info("Response : "+xmlResponseStr);

	}
	
	private String getBpcsXmlRequest(HttpServletRequest request) {
		
		String xml = "";
		String parameterName = "";
		String[] parameterValues;
		Enumeration<String> parameterNames = request.getParameterNames();

		if(request.getParameterMap().containsKey("Message")){

			xml = request.getParameter("Message");

		}
		else if(request.getParameterMap().containsKey("request")){
			xml = request.getParameter("request");
		}

		else if(!parameterNames.hasMoreElements()){
			try {
				BufferedReader br = request.getReader();
				String line = null;
				StringBuffer envelope = new StringBuffer();
				while((line = br.readLine()) != null){
					envelope.append(line);
				}
				br.close();
	
				xml = new String(envelope);
				if(xml.indexOf("filename=") > 0 && xml.indexOf("Content-Type:") > 0){
					xml = xml.substring(xml.indexOf("<?xml version"), xml.indexOf("</RequestorRequest>") + 19);
					logger.info("File Received Request: " + xml);
				}
				else{
					logger.info("Body Received Request: " + xml);
				}
			}
			catch(Exception err) {
				
			}

		}

		else{

			while(parameterNames.hasMoreElements()){

				parameterName = parameterNames.nextElement();
				if(parameterName != null){
					xml += parameterName;
					if(request.getParameterValues(parameterName) != null){
						parameterValues = request.getParameterValues(parameterName);
						for(int i = 0; i < parameterValues.length; i++){

							xml = xml + parameterValues[i];
						}

					}

				}

				logger.info("Param as request Received Request: " + xml);

			}

		}
		return xml;

	}

}