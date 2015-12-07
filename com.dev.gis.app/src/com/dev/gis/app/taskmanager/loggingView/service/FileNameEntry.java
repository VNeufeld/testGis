package com.dev.gis.app.taskmanager.loggingView.service;

import java.io.File;

public class FileNameEntry implements Comparable<FileNameEntry>{

	private boolean select;
	private String fileName;
	private String dir;
	private long   size;
	private String status;
	private int    number;
	
	private File file;
	
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
	public static FileNameEntry create(File file, int index) {
		
		FileNameEntry fn = new FileNameEntry();
		fn.fileName = file.getName();
		fn.size = file.length();
		fn.dir = file.getParent();
		fn.file = file;
		fn.select = true;
		fn.number = index;
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
	public boolean isSelect() {
		return select;
	}
	public void setSelect(boolean select) {
		this.select = select;
	}
	public File getFile() {
		return file;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}	
}
