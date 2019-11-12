
package com.soap.adac.service.geoCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GeoCodeASPResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "geoCodeASPResult"
})
@XmlRootElement(name = "GeoCodeASPResponse")
public class GeoCodeASPResponse {

    @XmlElement(name = "GeoCodeASPResult")
    protected String geoCodeASPResult;

    /**
     * Gets the value of the geoCodeASPResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGeoCodeASPResult() {
        return geoCodeASPResult;
    }

    /**
     * Sets the value of the geoCodeASPResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGeoCodeASPResult(String value) {
        this.geoCodeASPResult = value;
    }

}
