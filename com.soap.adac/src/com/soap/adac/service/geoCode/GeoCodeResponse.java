
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
 *         &lt;element name="GeoCodeResult" type="{http://ADAC.ITP.WebServices/}ReturnCode"/>
 *         &lt;element name="AnzVorschlaege" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="vListe" type="{http://ADAC.ITP.WebServices/}ArrayOfPostAdresse" minOccurs="0"/>
 *         &lt;element name="FehlerText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "geoCodeResult",
    "anzVorschlaege",
    "vListe",
    "fehlerText"
})
@XmlRootElement(name = "GeoCodeResponse")
public class GeoCodeResponse {

    @XmlElement(name = "GeoCodeResult", required = true)
    protected ReturnCode geoCodeResult;
    @XmlElement(name = "AnzVorschlaege")
    protected int anzVorschlaege;
    protected ArrayOfPostAdresse vListe;
    @XmlElement(name = "FehlerText")
    protected String fehlerText;

    /**
     * Gets the value of the geoCodeResult property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnCode }
     *     
     */
    public ReturnCode getGeoCodeResult() {
        return geoCodeResult;
    }

    /**
     * Sets the value of the geoCodeResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnCode }
     *     
     */
    public void setGeoCodeResult(ReturnCode value) {
        this.geoCodeResult = value;
    }

    /**
     * Gets the value of the anzVorschlaege property.
     * 
     */
    public int getAnzVorschlaege() {
        return anzVorschlaege;
    }

    /**
     * Sets the value of the anzVorschlaege property.
     * 
     */
    public void setAnzVorschlaege(int value) {
        this.anzVorschlaege = value;
    }

    /**
     * Gets the value of the vListe property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPostAdresse }
     *     
     */
    public ArrayOfPostAdresse getVListe() {
        return vListe;
    }

    /**
     * Sets the value of the vListe property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPostAdresse }
     *     
     */
    public void setVListe(ArrayOfPostAdresse value) {
        this.vListe = value;
    }

    /**
     * Gets the value of the fehlerText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFehlerText() {
        return fehlerText;
    }

    /**
     * Sets the value of the fehlerText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFehlerText(String value) {
        this.fehlerText = value;
    }

}
