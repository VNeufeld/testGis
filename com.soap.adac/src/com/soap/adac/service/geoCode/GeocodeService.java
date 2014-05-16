
package com.soap.adac.service.geoCode;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "GeocodeService", targetNamespace = "http://ADAC.ITP.WebServices/", wsdlLocation = "http://routenplaner.adac.de/Webservice/Geocodeservice.asmx?WSDL")
public class GeocodeService
    extends Service
{

    private final static URL GEOCODESERVICE_WSDL_LOCATION;
    private final static WebServiceException GEOCODESERVICE_EXCEPTION;
    private final static QName GEOCODESERVICE_QNAME = new QName("http://ADAC.ITP.WebServices/", "GeocodeService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://routenplaner.adac.de/Webservice/Geocodeservice.asmx?WSDL");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        GEOCODESERVICE_WSDL_LOCATION = url;
        GEOCODESERVICE_EXCEPTION = e;
    }

    public GeocodeService() {
        super(__getWsdlLocation(), GEOCODESERVICE_QNAME);
    }

    public GeocodeService(WebServiceFeature... features) {
        super(__getWsdlLocation(), GEOCODESERVICE_QNAME, features);
    }

    public GeocodeService(URL wsdlLocation) {
        super(wsdlLocation, GEOCODESERVICE_QNAME);
    }

    public GeocodeService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, GEOCODESERVICE_QNAME, features);
    }

    public GeocodeService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public GeocodeService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns GeocodeServiceSoap
     */
    @WebEndpoint(name = "GeocodeServiceSoap")
    public GeocodeServiceSoap getGeocodeServiceSoap() {
        return super.getPort(new QName("http://ADAC.ITP.WebServices/", "GeocodeServiceSoap"), GeocodeServiceSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns GeocodeServiceSoap
     */
    @WebEndpoint(name = "GeocodeServiceSoap")
    public GeocodeServiceSoap getGeocodeServiceSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://ADAC.ITP.WebServices/", "GeocodeServiceSoap"), GeocodeServiceSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (GEOCODESERVICE_EXCEPTION!= null) {
            throw GEOCODESERVICE_EXCEPTION;
        }
        return GEOCODESERVICE_WSDL_LOCATION;
    }

}
