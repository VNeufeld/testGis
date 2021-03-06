package com.dev.gis.app.task.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dev.gis.app.taskmanager.loggingView.service.FileNameEntry;

public class FileNameEntryModel {
	private static FileNameEntryModel  instance = null;

	private List<FileNameEntry> entries = new ArrayList<FileNameEntry>();

	public static FileNameEntryModel getInstance() {
		if (instance == null) {
			instance = new FileNameEntryModel();
		}
		return instance;
	}
	
	public void clear() {
		entries.clear();
	}

	public List<FileNameEntry> getEntries() {
		return entries;
	}
	
	public List<FileNameEntry> getSelectedFiles() {
		List<FileNameEntry> files = new ArrayList<FileNameEntry>();
		for ( FileNameEntry entry : entries  )
		{
			if ( entry.isSelect()) {
				if ( entry.getFile() != null) {
					files.add(entry);
				}
			}
				
		}
		return files;
	}
	

	public void sort() {
		Collections.sort(entries);
		// TODO Auto-generated method stub
		
	}

	public void create(File[] files) {
		int index = 0;
		for (File file : files ) {
			if ( file.length() > 0) {
				FileNameEntry fn = FileNameEntry.create(file,++index);
				getEntries().add(fn);
			}
		}
		sort();
		
	}


	public void setStatus(File logFile, String status) {
		for ( FileNameEntry entry : entries  )
		{
			if ( entry.getFileName().equalsIgnoreCase(logFile.getName())) {
				entry.setStatus(status);
			}
		}
		
	}

}
