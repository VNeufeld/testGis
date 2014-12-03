package com.bpcs.eml.app.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;

import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class EmlService implements Callable<String> {
	private static Logger logger = Logger.getLogger(EmlService.class);

	private final File logFile;
	private final String outputDir;
	private long maxFileSize;
	
	private boolean canceled = false;

	public EmlService(String fileName, String outputDir,
			String maxFileSizeText) {

		if (StringUtils.isNotEmpty(fileName)) {
			logFile = new File(fileName);
			logger.info("  size = " + FileUtils.sizeOf(logFile));
		} else {
			logFile = null;
		}

		this.outputDir = outputDir;
		
		
		if (StringUtils.isNotEmpty(maxFileSizeText))
			maxFileSize = Integer.valueOf(maxFileSizeText);
		else
			maxFileSize = 100;
		
		maxFileSize = maxFileSize * 1000000;
		
		canceled = false;

	}


	private void writeFile(File original, int counter,List<String> rows) 
			throws IOException {
//		String ext = FilenameUtils.getExtension(logFile.getAbsolutePath());
//		String path = FilenameUtils.getFullPath(logFile.getAbsolutePath());
		String name = FilenameUtils.getName(logFile.getAbsolutePath())+ "_" + counter + ".plog";
		
		String newFile = FilenameUtils.concat(outputDir,name);
		logger.info("write file "+newFile + " rows = "+ rows.size());

		File of = new File(newFile);
		
		FileUtils.writeLines(of, rows);
		
		logger.info("exit write file "+newFile + " ");
		
	}
	


	@Override
	public String call() throws Exception {
		
		long start = System.currentTimeMillis();
		logger.info("start splitt file  " + logFile.getAbsolutePath());
		try {
			createEml(logFile);
			logger.info("end splitt in  " + (System.currentTimeMillis() - start) + " ms.");
		}
		catch(Exception err) {
			err.printStackTrace();
		}
		return "";
	}


	private void createEml(File file) throws Exception {
		
		   String to = "abcd@gmail.com";

	      // Sender's email ID needs to be mentioned
	      String from = "web@gmail.com";
		
		
		 createMessage(to, from, "test", " body", new ArrayList<File>());
	}
	
	
	private void createMessage(String to, String from, String subject, String body, List<File> attachments) throws Exception{
	    	  // Get system properties
	        Properties properties = System.getProperties();

	        // Setup mail server
	        //properties.setProperty("mail.smtp.host", host);

	        // Get the default Session object.
	        Session session = Session.getDefaultInstance(properties,null);	    	
	    	
	        MimeMessage message = new MimeMessage(Session.getInstance(System.getProperties(),null));
//	        message.setFrom(new InternetAddress(from));
//	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
	        message.setSubject(subject);
	        // create the message part 
	        MimeBodyPart content = new MimeBodyPart();
	        // fill message
	        content.setText(body);
	        MimeMultipart multipart = new MimeMultipart();
	        multipart.addBodyPart(content);
//	        // add attachments
//	        for(File file : attachments) {
//	            MimeBodyPart attachment = new MimeBodyPart();
//	            FileDataSource source = new FileDataSource(file);
//	            attachment.setDataHandler(new DataHandler(source));
//	            attachment.setFileName(file.getName());
//	            multipart.addBodyPart(attachment);
//	        }
//	        // integration
	        message.setContent(multipart);
	        // store file
	        message.writeTo(new FileOutputStream(new File("c:/temp/mail.eml")));
	        
	}


	public boolean isCanceled() {
		return canceled;
	}


	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

}
