
package com.soap.adac.service.geoCode;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the geoCode package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _String_QNAME = new QName("http://ADAC.ITP.WebServices/", "string");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: geoCode
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GeoCodeUngenauASPResponse }
     * 
     */
    public GeoCodeUngenauASPResponse createGeoCodeUngenauASPResponse() {
        return new GeoCodeUngenauASPResponse();
    }

    /**
     * Create an instance of {@link GeoCodeUngenauASP }
     * 
     */
    public GeoCodeUngenauASP createGeoCodeUngenauASP() {
        return new GeoCodeUngenauASP();
    }

    /**
     * Create an instance of {@link GeoCodeASPResponse }
     * 
     */
    public GeoCodeASPResponse createGeoCodeASPResponse() {
        return new GeoCodeASPResponse();
    }

    /**
     * Create an instance of {@link GeoCodeResponse }
     * 
     */
    public GeoCodeResponse createGeoCodeResponse() {
        return new GeoCodeResponse();
    }

    /**
     * Create an instance of {@link ArrayOfPostAdresse }
     * 
     */
    public ArrayOfPostAdresse createArrayOfPostAdresse() {
        return new ArrayOfPostAdresse();
    }

    /**
     * Create an instance of {@link GeoCode }
     * 
     */
    public GeoCode createGeoCode() {
        return new GeoCode();
    }

    /**
     * Create an instance of {@link PostAdresse }
     * 
     */
    public PostAdresse createPostAdresse() {
        return new PostAdresse();
    }

    /**
     * Create an instance of {@link GeoCodeASP }
     * 
     */
    public GeoCodeASP createGeoCodeASP() {
        return new GeoCodeASP();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ADAC.ITP.WebServices/", name = "string")
    public JAXBElement<String> createString(String value) {
        return new JAXBElement<String>(_String_QNAME, String.class, null, value);
    }

}
