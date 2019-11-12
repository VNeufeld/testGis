package com.bpcs.eml.app.view;

import com.dev.http.server.IServerCallback;
import com.dev.http.server.LogEntry;

public class ServerCallback implements IServerCallback {
	

	@Override
	public void receive(String param) {
		EmlViewUpdater.updateTempView(param);
		
	}

	@Override
	public void update(LogEntry entry) {
		LogEntryModel.getInstance().update(entry);
		EmlViewUpdater.updateTable(entry);
	}


}
