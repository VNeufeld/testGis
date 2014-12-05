package com.bpcs.app.view;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

import com.dev.http.server.LogEntry;

public class LogEntryModel   {
	private static LogEntryModel  instance = null;
	
	private Deque<LogEntry> entries = new ArrayDeque<LogEntry>();
	
	//private List<LogEntry> entries = new ArrayList<LogEntry>();
	
	public static LogEntryModel getInstance() {
		if (instance == null) {
			instance = new LogEntryModel();
		}
		return instance;
	}
	
	public void clear() {
		entries.clear();
	}

	public Collection<LogEntry> getEntries() {
		return entries;
	}

	public void update(LogEntry find_entry) {
		boolean found = false;
		for (LogEntry entry : entries ) {
			if ( entry.getId() == find_entry.getId()) {
				entry.setEnd(find_entry.getEnd());
				entry.setDuration(find_entry.getDuration());
				entry.setResponse(find_entry.getResponse());
				found = true;
				break;
			}
		}
		if ( !found) {
			if (entries.size() > 500)
				entries.removeLast();
			entries.addFirst(find_entry);
			
		}
		
	}


}
