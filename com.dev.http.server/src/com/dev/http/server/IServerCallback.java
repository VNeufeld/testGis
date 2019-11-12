package com.dev.http.server;


public interface IServerCallback {
	
	public void receive ( String param );

	public void update ( LogEntry entry );
	
}
