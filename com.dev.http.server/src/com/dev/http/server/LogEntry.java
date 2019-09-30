package com.dev.http.server;

import java.util.Date;
import java.util.Random;

public class LogEntry implements Comparable<LogEntry> {
	
	private long id;
	private Date start;
	private Date end;
	private long duration;
	private String request;
	private String response;
	
	public LogEntry(Date time) {
		id = new Random().nextLong();
		this.start = time;
	}
	@Override
	public int compareTo(LogEntry arg) {
		return start.compareTo(arg.start);
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public long getId() {
		return id;
	}
	

}
