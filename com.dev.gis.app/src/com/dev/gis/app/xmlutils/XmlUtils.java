package com.dev.gis.app.xmlutils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.IOUtils;

import com.dev.gis.app.xmlutils.transform.request.getCars.HsgwRequestorRequest;

public class XmlUtils {


	public String loadXml(String name) {
		checkResource();
		return null;
	}

	private void checkResource() {
		String file = "/resources/GetCarsRequest/GetCarsRequest.xml";
		// InputStream stream =
		// Class.class.getClassLoader().getResourceAsStream("/GetCarsRequest/GetCarsRequest.xml");
		InputStream is = this.getClass().getClassLoader()
				.getResourceAsStream(file);

		StringWriter writer = new StringWriter();

		try {
			IOUtils.copy(is, writer, "utf-8");
			String result = writer.toString();
			System.out.println("result = " + result);
			
			Object object = convertLegacyGetCarsRequestFromXml(result);
			System.out.println("object = " + object);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static Object convertLegacyGetCarsRequestFromXml( String xml) {

		JAXBContext context;
		try {
			context = JAXBContext.newInstance(HsgwRequestorRequest.class);

			javax.xml.bind.Unmarshaller unmarshaller2 = context.createUnmarshaller();
			StringReader reader = new StringReader(xml);

			return unmarshaller2.unmarshal(reader);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

}
