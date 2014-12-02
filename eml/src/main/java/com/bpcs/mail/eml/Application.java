package com.bpcs.mail.eml;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	private void createMessage(String to, String from, String subject,
			String body, File[] attachments) throws Exception {
		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		// properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties, null);

		MimeMessage message = new MimeMessage(Session.getInstance(
				System.getProperties(), null));
		// message.setFrom(new InternetAddress(from));
		// message.setRecipients(Message.RecipientType.TO,
		// InternetAddress.parse(to));
		message.setSubject(subject);
		// create the message part
		MimeBodyPart content = new MimeBodyPart();
		// fill message
		content.setText(body);
		MimeMultipart multipart = new MimeMultipart();
		multipart.addBodyPart(content);

		// MimeBodyPart imagePart = new MimeBodyPart();
		//
		// imagePart.attachFile("resources/teapot.jpg");

		// multipart.addBodyPart(imagePart);

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
		message.writeTo(new FileOutputStream(new File("c:/temp/mail.eml")));

	}

}
