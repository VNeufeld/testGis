package com.dev.http.server;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
// http://wiki.eclipse.org/Jetty/Tutorial/Embedding_Jetty
public class HttpServer {
	
	protected final static Logger logger = Logger.getLogger(HttpServer.class);

	private Server server;
	
	private ServerCallback callback;

	
	public void startup(ServerCallback callback) {
		try {
			this.callback = callback;
			int port = 7777;
			InetSocketAddress address = new InetSocketAddress(port);
			server = new Server(address);
			server.setHandler(new RemoteControlHttpHandler(callback));

			server.start();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	public void shutdown() {
		try {
			server.stop();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}	
}
