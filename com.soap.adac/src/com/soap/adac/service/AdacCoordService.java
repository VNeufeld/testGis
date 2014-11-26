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
import javax.xml.ws.Holder;

import com.soap.adac.service.geoCode.ArrayOfPostAdresse;
import com.soap.adac.service.geoCode.GeocodeService;
import com.soap.adac.service.geoCode.GeocodeServiceSoap;
import com.soap.adac.service.geoCode.Karte;
import com.soap.adac.service.geoCode.KoordinatenFormat;
import com.soap.adac.service.geoCode.PostAdresse;
import com.soap.adac.service.geoCode.Quality;
import com.soap.adac.service.geoCode.ReturnCode;

public class AdacCoordService {
//http://harryjoy.com/2011/10/20/soap-client-in-java/
	public String adac_geocode_url = "http://routenplaner.adac.de/Webservice/Geocodeservice.asmx?op=GeoCode";
	public static void main(String[] args) {
		System.out.println("OK");
	      try {
	            // Create SOAP Connection	
//	       SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
//           SOAPConnection soapConnection = soapConnectionFactory.createConnection();
//
//           // Send SOAP Message to SOAP Server
//           String url = "http://www.webservicex.net/geoipservice.asmx";
//           SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);
//
//           // Process the SOAP Response
//           printSOAPResponse(soapResponse);
//
//           soapConnection.close();
           
           long startTime = System.currentTimeMillis();
           GeocodeService service = new GeocodeService();
           GeocodeServiceSoap port =  service.getGeocodeServiceSoap();

           System.out.println("OK. Time needed 1 : "+ ( System.currentTimeMillis() - startTime) + " ms." );
           
           PostAdresse postAdr = new PostAdresse();
           postAdr.setOrt("Oberhaching");
           postAdr.setPLZ("82041");
           postAdr.setStrasse("Keltenring");
           postAdr.setHausNr("9");
           postAdr.setQuality(Quality.PERFECT);
           postAdr.setKoordinatenformat(KoordinatenFormat.GEODEZIMAL);
           postAdr.setLKZ("DE");
           
           Holder<String>  fehlerText = new Holder<String>();
           Holder<ReturnCode> geoCodeResult = new Holder<ReturnCode>();
           Holder<ArrayOfPostAdresse> vlist = new Holder<ArrayOfPostAdresse>();
           Holder<Integer>  countResults = new Holder<Integer>();
           
           
           port.geoCode(postAdr, countResults, vlist, Karte.EUROPA, true, false, KoordinatenFormat.GEODEZIMAL, fehlerText, geoCodeResult);
   		   
           System.out.println(" Result code = " + geoCodeResult.value);

           System.out.println(" fehlerText = " + fehlerText.value);

           System.out.println(" countResults = " + countResults.value);
           
           ArrayOfPostAdresse result = vlist.value;
           if ( result != null) {
	           for (PostAdresse pa :  result.getPostAdresse()) {
	        	   System.out.println("Adresse "+pa.getOrt()+ "/"+pa.getStrasse()+ "/"+pa.getHausNr()+ ":   Koord "+ pa.getX()+ "," + pa.getY() + " proj : "+pa.getKoordinatenformat());
	           }
           }
           
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
 
        String serverURI = "http://www.webservicex.net/";
 
        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("web", serverURI);
 
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
        SOAPElement soapBodyElem = soapBody.addChildElement("GetGeoIP", "web");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("IPAddress", "web");
        soapBodyElem1.addTextNode("192.168.1.2");
        //example of adding another element    
          //SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("LicenseKey", "web");
          //soapBodyElem2.addTextNode("123");
 
         
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverURI  + "GetGeoIP");
 
        soapMessage.saveChanges();
 
        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();
 
        return soapMessage;
    }


    private static SOAPMessage createSOAPRequest() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
 
        String serverURI = "http://www.webservicex.net/";
 
        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("web", serverURI);
 
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
        SOAPElement soapBodyElem = soapBody.addChildElement("GetGeoIP", "web");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("IPAddress", "web");
        soapBodyElem1.addTextNode("192.168.1.2");
        //example of adding another element    
          //SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("LicenseKey", "web");
          //soapBodyElem2.addTextNode("123");
 
         
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverURI  + "GetGeoIP");
 
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
