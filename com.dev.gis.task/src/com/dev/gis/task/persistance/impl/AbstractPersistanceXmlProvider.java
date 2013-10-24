package com.dev.gis.task.persistance.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.dev.gis.task.execution.locationSearch.JoiTask;
import com.dev.gis.task.execution.locationSearch.impl.LocationSearchTask;

abstract class AbstractPersistanceXmlProvider {
	private static Logger logger = Logger.getLogger(AbstractPersistanceXmlProvider.class.getName());
	
	private static final String REQUEST_FOLDER_URI_DEFAULT = "file:/E:/Temp/AppGisTest/Requests";
	static URI responseFolder;
	static URI requestFolder = null;
	
	static {
		
		try {
			requestFolder = new URI(REQUEST_FOLDER_URI_DEFAULT);
		} catch (URISyntaxException e) {
			logger.error("URI Error" ,e );
		}
	}
	
	public URI getResponseFolder() {
		return responseFolder;
	}
	public void setResponseFolder(URI responseFolder) {
		responseFolder = responseFolder;
	}
	public URI getRequestFolder() {
		return requestFolder;
	}
	public void setRequestFolder(URI requestFolder) {
		requestFolder = requestFolder;
	}

	
	protected void saveTask(JoiTask task) throws JAXBException, IOException {
		
		logger.info(" saveTask "+task);

		JAXBContext context = JAXBContext.newInstance(task.getClass());
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		File file = createFileRequest(task.getName());
		
		Writer out = new BufferedWriter(new OutputStreamWriter(
			    	new FileOutputStream(file), "UTF-8"));		
		marshaller.marshal(task, out);
		
	}
	

	protected File findFile(String name) {
		File file = createFileRequest(name);
		if ( file != null && file.exists())
			return file;
		return null;
		
	}
	
	private File createFileRequest(String name) {
		File file = new File(requestFolder);
		if ( file.exists() && file.isDirectory()) {
			String filePath = file.getAbsolutePath()+"/"+name+".request";
			File fileRequest = new File(filePath);
			return fileRequest;
		}
		return null;
	}
	
	protected  LocationSearchTask loadTask(String name,
			Class<?> class1) throws JAXBException {

		logger.info(" loadTask "+ name);

		JAXBContext context = JAXBContext.newInstance(class1);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		
		File file = findFile(name);
		if ( file != null)
			return  (LocationSearchTask) unmarshaller.unmarshal(file);
		return null;
	}

	
}
