package com.dev.gis.task.execution.api;

import java.net.URI;

public abstract class JoiTask {
	URI haJoiServiceLink;
	String name;
	
	JoiTask() {
		
	}
	
	JoiTask(String name) {
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
