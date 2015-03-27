package com.dev.gis.connector.sunny;

import java.net.URI;
import java.util.List;



/**
 * @author bre
 *         The class provides the minimal content of a response in the JOI
 *         context.
 */
public class Response extends BasicProtocol {

	private URI link;

	private Long requestId;

	private List<Error> errors ;
	
	public List<Error> getErrors() {
		return errors;
	}

	public URI getLink() {
		return link;
	}

	public Long getRequestId() {
		return requestId;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}
	
	public void setLink(URI link) {
		this.link = link;
	}
	
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	@Override
	public String toString() {
		return "Response [link=" + link + ", requestId=" + requestId + ", errors=" + errors + "]";
	}
}