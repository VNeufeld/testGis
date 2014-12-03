package com.dev.http.server.services;

import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMStringList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

import com.dev.http.server.services.emlService.CreateEmlServiceFactory;

public class ServiceFactory {
	
	public static final String REQUESTOR_NAME = "RequestorName";
	public static final int DEMANDED_OBJECT_CREATE_EML = 22040;
	
	private static Logger LOG = Logger.getLogger(ServiceFactory.class);
	

	public HsgwService createService(HttpServletRequest httpRequest,
			Document doc) {

		int demandedObjectId = getDemandedObjectId(doc);

		String sessionId = getSessionId(httpRequest,doc);
		
		
		if ( demandedObjectId < 0 )
			throw new ServiceFactoryException("no demanded object found. ",null);
		
		Node requestNode = getRequestNode(doc);
		if (requestNode == null )
			throw new ServiceFactoryException("no <Request> Elment found. ",null);
		
		LOG.info(" Receive document. Demanded object : "+ demandedObjectId+ "\n"+toXML(doc) );
		
		
		return getService(demandedObjectId,requestNode,sessionId);
	}
	
	private HsgwService getService(int demandedObjectId,Node requestNode, String sessionId) {
		HsgwServiceFactory factory = null;		
		if (demandedObjectId == DEMANDED_OBJECT_CREATE_EML ) {
			factory = CreateEmlServiceFactory.createFactory(requestNode,sessionId);
		}
		
		if ( factory == null)
			throw new ServiceFactoryException("no factory created. ",null);
		return factory.getService();
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


	private Node getRequestNode(Document doc) {
		Node requestNode = null;
		NodeList nl  = doc.getElementsByTagName("Request");
		if ( nl.getLength() == 1 ) {
			requestNode = nl.item(0);
		}
		
		return requestNode;
	}

	private int getDemandedObjectId(Document doc) {
		String dob = "";
		NodeList nl = doc.getElementsByTagName("DemandedObject");
		if ( nl.getLength() == 1 ) {
			Node node = nl.item(0);
			if ( node.getNodeType() == Node.ELEMENT_NODE) {
				dob = node.getFirstChild().getNodeValue();
			}
		}
	    if ( !StringUtils.isBlank(dob))
	    	return Integer.valueOf(dob);
	    return -1;
	}
	
	private String xmlDocToStr(Document doc) {
		String output ="";
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			//output = writer.toString().replaceAll("\n|\r", "");
			output = writer.toString();
		}
		catch(Exception err) {
			LOG.error(err.getMessage(),err);	
			output = err.toString();
		}
		return output;
	}
	
	private String toXML(Document source) {

	    String subscrXML=null;
	    StringWriter stringWriter=new StringWriter();
	     try {
	        //Get the implementations

	        DOMImplementationRegistry registry =  DOMImplementationRegistry.newInstance();

	        DOMImplementationLS impls =  (DOMImplementationLS)registry.getDOMImplementation("LS");


	        //Prepare the output
	        LSOutput domOutput = impls.createLSOutput();
	        domOutput.setEncoding(java.nio.charset.Charset.defaultCharset().name());            
	        domOutput.setCharacterStream(stringWriter);
	        domOutput.setEncoding("utf-8");
	        //Prepare the serializer
	        LSSerializer domWriter = impls.createLSSerializer();            
	        DOMConfiguration domConfig = domWriter.getDomConfig();
	        domConfig.setParameter("format-pretty-print", true);
	        domConfig.setParameter("element-content-whitespace", true);
	        domWriter.setNewLine("\r\n");     
	        domConfig.setParameter("cdata-sections", Boolean.TRUE);
	        //And finaly, write
	        domWriter.write(source, domOutput);
	        subscrXML = domOutput.getCharacterStream().toString();
	     } catch (Exception e) {
			LOG.error(e.getMessage(),e);	
	     }
	    return subscrXML;
	 }

}
