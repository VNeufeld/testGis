package com.dev.http.server.services.emlService;

import org.w3c.dom.Node;

import com.dev.http.protocoll.createEmlRequest.Request;
import com.dev.http.server.marshaling.XmlMarshaller;
import com.dev.http.server.services.HsgwServiceFactory;

public class CreateEmlServiceFactory  implements HsgwServiceFactory {
	
	private final CreateEmlService service;
	
	private CreateEmlServiceFactory(EmlInputTO inputTO) {
		service = new CreateEmlService(inputTO);
	}

	public CreateEmlService getService() {
		return service;
	}

	public static HsgwServiceFactory createFactory(Node requestNode, String sessionId) {
		XmlMarshaller marshaller = new XmlMarshaller("");
		Request request = (Request)marshaller.unmarshallFromXMLNode(requestNode, Request.class);
		
		EmlInputTO emlInputTO = new EmlInputTO();
		
		emlInputTO.setFrom(request.getMail().getFrom());
		emlInputTO.setTo(request.getMail().getTo());
		emlInputTO.setBody(request.getMail().getBody());
		emlInputTO.setSubject(request.getMail().getSubject());
		emlInputTO.setAttachmentDir(request.getMail().getAttachmentDir());
		
		CreateEmlServiceFactory getCamperFactory = new CreateEmlServiceFactory(emlInputTO);
		
		return getCamperFactory;
	}

}
