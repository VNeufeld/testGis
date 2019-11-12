package com.dev.http.server.services;

import java.io.IOException;
import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SessionControllerAdvanced {
	
	public static final String REQUESTOR_NAME = "RequestorName";

	private static Logger LOG = Logger.getLogger(SessionController.class);



	public SessionControllerAdvanced() {
	}

	public String executeXmlRequest(HttpServletRequest httpRequest, String xmlRequest, boolean isIntern)  {
		long start = System.currentTimeMillis();
		
		String xmlResponseStr;

		
		try {
			Document doc = parseXml(xmlRequest);
			
			String sessionId = getSessionId(httpRequest,doc);
			
			HsgwService service = new ServiceFactory().createService(httpRequest, doc);
	
			xmlResponseStr = service.executeService();
			
			// neu : is not implemented yet!
			ResponseValidator.validateResponseIfRequested(xmlResponseStr);
			
			
		}
		catch(Exception e){
			LOG.error(e.getMessage(), e);
			
			xmlResponseStr = createErrorXml();
	
		}
		
		LOG.info("request executed in "+ (System.currentTimeMillis() - start) +" ms." );
		
		
		return xmlResponseStr;
		
	}

	private String createErrorXml() {
		return null;
	}

	private Document parseXml(String xmlRequest) throws ParserConfigurationException, SAXException, IOException {
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		InputSource is = new InputSource(new StringReader(xmlRequest));		

		return db.parse(is);
	}
	
	private String getSessionId(HttpServletRequest httpRequest, Document doc) {
		String sessionId = null;
		NodeList nl  = doc.getElementsByTagName(REQUESTOR_NAME);
		if ( nl.getLength() == 1 ) {
			Node node = nl.item(0);
			if ( node.getNodeType() == Node.ELEMENT_NODE) {
				Element elem = (Element) node;
				sessionId =elem.getAttribute("sessionId");
			}
		}
		if ( StringUtils.isBlank(sessionId))
			sessionId = httpRequest.getSession(true).getId();
		
		return sessionId;
	}


}
