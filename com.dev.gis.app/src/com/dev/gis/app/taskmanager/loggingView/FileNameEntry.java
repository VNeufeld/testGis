package com.dev.gis.app.taskmanager.loggingView;

import java.io.File;

public class FileNameEntry implements Comparable<FileNameEntry>{
	
	private String fileName;
	private String dir;
	private long   size;
	private String status;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public static FileNameEntry create(File file) {
		
		FileNameEntry fn = new FileNameEntry();
		fn.fileName = file.getName();
		fn.size = file.length();
		fn.dir = file.getParent();
		return fn;
		
	}
	public String getDir() {
		return dir;
	}

	@Override
	public int compareTo(FileNameEntry arg) {
		return fileName.compareToIgnoreCase(arg.fileName);
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
}
