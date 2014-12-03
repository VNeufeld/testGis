package com.dev.http.server.marshaling;


import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;


public class XmlMarshaller {
	private static Logger logger = Logger.getLogger(XmlMarshaller.class);
	
	private final String filterExclusion;
	
	public XmlMarshaller(String filterExclusion) {
		this.filterExclusion = filterExclusion;
	}

	public String marshallToXML(Object requestorRequest) {
		
		assert (requestorRequest != null);
		
		
		Marshaller marshaller;
		try {
			marshaller = JAXBContext.newInstance(requestorRequest.getClass()).createMarshaller();
		} catch (JAXBException e) {
			throw new MarshallingException("could not create JAXBContext", e);
		}
	
		try {
			marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		} catch (PropertyException e) {
			throw new MarshallingException("could not setup formatted output for marshalling", e);
		}
	
		StringWriter writer = new StringWriter();
	
		try {
			marshaller.marshal(requestorRequest, writer);
		} catch (JAXBException e) {
			throw new MarshallingException("could not marshall", e);
		}
	
		String xmlRequest = writer.toString();
	
		if (logger.isDebugEnabled()) {
			String[] keys = new String[] { "account"};
			if ( filterExclusion != null )
				keys = filterExclusion.split(",");
			
			String xmlrequestClone = new String(xmlRequest);
			
			//xmlrequestClone = FilterUtils.shadowKeyWordsXml(xmlrequestClone,keys);
			
			logger.debug("xmlRequest " + xmlrequestClone);
		}
		return xmlRequest;
	}

	public <T> T unmarshallFromXML(String hsgwXmlresponse, Class<T> responseClass){

		assert (responseClass != null);
		assert (hsgwXmlresponse != null);
		
		JAXBContext context = createContext(responseClass);
	
		javax.xml.bind.Unmarshaller unmarshaller = createUnmarshaller(context);
		StringReader reader = new StringReader(hsgwXmlresponse);
		@SuppressWarnings("unchecked")
		T result = (T)unmarshall(unmarshaller, reader);
		
		return result;
	}
	
	public <T> T unmarshallFromXMLNode(Node node, Class<T> responseClass){

		assert (responseClass != null);
		assert (node != null);
		
		JAXBContext context = createContext(responseClass);
	
		javax.xml.bind.Unmarshaller unmarshaller = createUnmarshaller(context);
		@SuppressWarnings("unchecked")
		T result = (T)unmarshall(unmarshaller, node);
		
		return result;
	}
	private Object unmarshall(javax.xml.bind.Unmarshaller unmarshaller, Node node) {
		Object result = null;
		try{
			result = unmarshaller.unmarshal(node);
		}
		catch(JAXBException e){
			throw new MarshallingException("could not unmarshall", e);
		}
		return result;
	}
	

	private Object unmarshall(javax.xml.bind.Unmarshaller unmarshaller, StringReader reader) {
		Object result = null;
		try{
			result = unmarshaller.unmarshal(reader);
		}
		catch(JAXBException e){
			throw new MarshallingException("could not unmarshall", e);
		}
		return result;
	}

	private javax.xml.bind.Unmarshaller createUnmarshaller(JAXBContext context) {
		javax.xml.bind.Unmarshaller unmarshaller;
		try{
			unmarshaller = context.createUnmarshaller();
		}
		catch(JAXBException e){
			throw new MarshallingException("could not unmarshall", e);
		}
		return unmarshaller;
	}

	private JAXBContext createContext(Class<?> responseClass) {
		JAXBContext context;
		try{
			context = JAXBContext.newInstance(responseClass);
		}
		catch(JAXBException e){
			throw new MarshallingException("could not create JAXBContext", e);
		}
		return context;
	}
}
