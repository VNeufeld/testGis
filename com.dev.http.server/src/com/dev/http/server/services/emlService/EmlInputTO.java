package com.dev.http.server.services.emlService;

public class EmlInputTO {
	
	private String from;
	private String to;
	private String subject;
	private String body;
	private String attachmentDir;
	private String bodyFileName;
	private String outputFileName;
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getAttachmentDir() {
		return attachmentDir;
	}
	public void setAttachmentDir(String attachmentDir) {
		this.attachmentDir = attachmentDir;
	}
	public String getBodyFileName() {
		return bodyFileName;
	}
	public void setBodyFileName(String bodyFileName) {
		this.bodyFileName = bodyFileName;
	}
	public String getOutputFileName() {
		return outputFileName;
	}
	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

}
