package com.dev.http.server;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;


public class RemoteControlHttpHandler extends AbstractHandler {

	protected final static Logger logger = Logger.getLogger(RemoteControlHttpHandler.class);

	private static final String commandKey = "command";
	
	private final ServerCallback callback;


	public RemoteControlHttpHandler() {
		callback = null;
	}



	public RemoteControlHttpHandler(ServerCallback callback) {
		// TODO Auto-generated constructor stub
		this.callback = callback;
	}



	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {

		Map paramMap = request.getParameterMap();
		String command = ((String[]) paramMap.get(commandKey))[0];

		logger.info("HTTP Request : "+command);
		
		callback.receive();
		
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/xml");

		response.getWriter().write(
				"{\"RESULTCODE\":0,\"RESULTTEXT\":\"Modulator fertig: " + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())
						+ "\"}");
		response.getWriter().flush();

	}


}