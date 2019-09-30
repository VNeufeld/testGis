package com.soap.adac.service;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

public class AdacSoapCoordService {

	public static void main(String[] args) {
		System.out.println("OK");
        long startTime = System.currentTimeMillis();		
	      try {
	            // Create SOAP Connection	
	         SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
	         SOAPConnection soapConnection = soapConnectionFactory.createConnection();
//
//           Send SOAP Message to SOAP Server
	         String url = "http://routenplaner.adac.de/Webservice/Geocodeservice.asmx";
	         SOAPMessage soapResponse = soapConnection.call(createAdacGeoCodeSOAPRequest(), url);
//
//            Process the SOAP Response
	         printSOAPResponse(soapResponse);

	         soapConnection.close();
	    	  
	 		   System.out.println("OK. Time needed : "+ ( System.currentTimeMillis() - startTime) + " ms." );
	           
	       } catch (Exception e) {
	           System.err.println("Error occurred while sending SOAP Request to Server");
	           e.printStackTrace();
	       }

	}
	
    private static SOAPMessage createAdacGeoCodeSOAPRequest() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
 
        String serverURI = "http://ADAC.ITP.WebServices";
 
        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("soap", serverURI);
 
        /*
        Constructed SOAP Request Message:
        <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:example="http://www.webservicex.net/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <web:GetGeoIP>
                    <web:IPAddressl>192.168.1.2</web:IPAddress>
                    <web:LicenseKey>123</web:LicenseKey>
                </web:GetGeoIP>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
         */
 
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("GeoCode");
        SOAPElement soapBodyPostAddr = soapBodyElem.addChildElement("postAdr");
        soapBodyPostAddr.addChildElement("LKZ").addTextNode("DE");
        soapBodyPostAddr.addChildElement("PLZ").addTextNode("82041");
        soapBodyPostAddr.addChildElement("Ort").addTextNode("Oberhaching");
        soapBodyPostAddr.addChildElement("Strasse").addTextNode("Keltenring");
        soapBodyPostAddr.addChildElement("HausNr").addTextNode("9");
        soapBodyPostAddr.addChildElement("Quality").addTextNode("Perfect");
         
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", "GeoCode");
 
        soapMessage.saveChanges();
 
        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();
 
        return soapMessage;
    }
    private static void printSOAPResponse(SOAPMessage soapResponse) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = soapResponse.getSOAPPart().getContent();
        System.out.print("\nResponse SOAP Message = ");
        StreamResult result = new StreamResult(System.out);
        transformer.transform(sourceContent, result);
    }


}
