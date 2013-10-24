package com.dev.gis.task.execution.locationSearch;

import java.net.URI;

public abstract class JoiTask {
	protected URI haJoiServiceLink;
	protected String name;
	
	protected JoiTask() {
		
	}
	
	protected JoiTask(String name) {
		this.name = name;
	}
	
	public URI getHaJoiServiceLink() {
		return haJoiServiceLink;
	}
	public void setHaJoiServiceLink(URI haJoiServiceLink) {
		this.haJoiServiceLink = haJoiServiceLink;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
