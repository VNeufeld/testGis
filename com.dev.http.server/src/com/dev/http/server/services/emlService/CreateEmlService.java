package com.dev.http.server.services.emlService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.dev.http.protocoll.createEmlResponse.RequestorResult;
import com.dev.http.protocoll.createEmlResponse.Result;
import com.dev.http.server.marshaling.XmlMarshaller;
import com.dev.http.server.services.HsgwService;

// http://blog.smartbear.com/how-to/how-to-send-email-with-embedded-images-using-java/
public class CreateEmlService implements HsgwService {

	protected final static Logger logger = Logger.getLogger(CreateEmlService.class);

	private static String outputDir = "C:/Temp";

	private final EmlInputTO inputTO;

	public CreateEmlService(final EmlInputTO inputTO) {
		this.inputTO = inputTO;
	}

	public String executeService() {
		
		long start = System.currentTimeMillis();
		
		File[] attachmentFiles = getFiles(inputTO.getAttachmentDir());
			
		String xml = "";
		try {
			String body = getBody();
			String resultFileName = savelAsEml(inputTO.getTo(), inputTO.getFrom(), inputTO.getSubject(), body, attachmentFiles);
			xml = createResult(resultFileName);

		} catch (Exception e) {
			logger.error("error in CreateEmlService : "+e.getMessage(), e);
			xml = createError(e);
		}

		logger.info("CreateEmlService executed in " + (System.currentTimeMillis() - start) + " ms.");

		return xml;
	}

	private String getBody() throws IOException {
		String body = "";
		if ( StringUtils.isNotBlank(inputTO.getBodyFileName())) {
			File file = new File(inputTO.getBodyFileName());
			body = FileUtils.readFileToString(file);
		}
		else
			body = inputTO.getBody();
		return body;
	}

	private String createError(Exception e) {
		return e.getMessage();
	}

	private File[] getFiles(String attachmentDir) {
		if ( StringUtils.isBlank(attachmentDir))
			return null;
		
		File dir = new File(attachmentDir);
		if ( dir.exists() && dir.isDirectory() ) {
			Collection<File> files = FileUtils.listFiles(dir,null, true);
			return files.toArray(new File[0]);
		}
		
		return null;
	}

	private String createResult(String resultFileName) {

		XmlMarshaller marshaller = new XmlMarshaller("");
		
		RequestorResult requestorResult = new RequestorResult();
		
		Result result = new Result();
		result.setCode("OK");
		result.setFile(resultFileName);
		requestorResult.setResult(result);

		String xml = marshaller.marshallToXML(requestorResult);
		return xml;
	}

	// http://blog.smartbear.com/how-to/how-to-send-email-with-embedded-images-using-java/
	private String savelAsEml(String to, String from, String subject,
			String body, File[] attachments) throws Exception {

		// Get system properties
		//Properties properties = System.getProperties();

		// Setup mail server
		// properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		//Session session = Session.getDefaultInstance(properties, null);

		MimeMessage message = new MimeMessage(Session.getInstance(System.getProperties(), null));
		
		message.setFrom(new InternetAddress(from));
		
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		
		message.setSubject(subject);
		
		// create the message part
		MimeBodyPart content = new MimeBodyPart();
		// fill message
		content.setText(body);
		MimeMultipart multipart = new MimeMultipart();
		multipart.addBodyPart(content);

		// // add attachments
		for (File file : attachments) {
			MimeBodyPart attachment = new MimeBodyPart();
			attachment.attachFile(file);
			// FileDataSource source = new FileDataSource(file);
			// attachment.setDataHandler(new DataHandler(source));
			// attachment.setFileName(file.getName());
			multipart.addBodyPart(attachment);
		}
		// // integration
		message.setContent(multipart);
		// store file
		
		String name = UUID.randomUUID().toString() + ".eml";
		String newFile = FilenameUtils.concat(outputDir,name);
		File result = null;
		if ( StringUtils.isNotBlank(inputTO.getOutputFileName())) {
			result = new File(inputTO.getOutputFileName());
		}
		else
			result = new File ( newFile);
		
		FileOutputStream fos = new FileOutputStream(result);
		
		message.writeTo(fos);
		
		fos.close();
		
		return result.getAbsolutePath();

	}

	public static void setOutputDir(String outputDir) {
		CreateEmlService.outputDir = outputDir;
	}

}
