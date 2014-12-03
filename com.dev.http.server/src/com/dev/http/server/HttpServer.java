package com.dev.http.server;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;

import com.dev.http.server.services.emlService.CreateEmlService;

// http://wiki.eclipse.org/Jetty/Tutorial/Embedding_Jetty
public class HttpServer {
	
	protected final static Logger logger = Logger.getLogger(HttpServer.class);

	private Server server;
	
	private String outputDir;
	
	private RemoteControlHttpHandler remoteControlHttpHandler;
	
	public void startup(IServerCallback callback) {
		try {
			int port = 7777;
			
			remoteControlHttpHandler = new RemoteControlHttpHandler(callback);
			
			InetSocketAddress address = new InetSocketAddress(port);
			server = new Server(address);
			server.setHandler(remoteControlHttpHandler);

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

	public String getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
		CreateEmlService.setOutputDir(outputDir);

	}	
}
