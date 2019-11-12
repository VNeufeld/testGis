package com.dev.gis.connector.joi.protocol;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * @author bre
 *         The class provides the minimal content of a response in the JOI
 *         context.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlTransient
public class Response extends BasicProtocol {

	private URI link;

	private long requestId;
	
	private String sessionId;
	

	private List<Error> errors = new ArrayList<>();

	
	
	public List<Error> getErrors() {
		return errors;
	}

	@XmlElement(required=false)
	public URI getLink() {
		return link;
	}

	public long getRequestId() {
		return requestId;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}
	
	public void setLink(URI link) {
		this.link = link;
	}
	
	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}
	
	@XmlElement(required=false)
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
}