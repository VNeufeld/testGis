
package com.soap.adac.service.geoCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PostAdresse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PostAdresse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LKZ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PLZ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Ort" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Strasse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HausNr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="X" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Y" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Bezeichnung" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Quality" type="{http://ADAC.ITP.WebServices/}Quality"/>
 *         &lt;element name="Koordinatenformat" type="{http://ADAC.ITP.WebServices/}KoordinatenFormat"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PostAdresse", propOrder = {
    "lkz",
    "plz",
    "ort",
    "strasse",
    "hausNr",
    "x",
    "y",
    "bezeichnung",
    "quality",
    "koordinatenformat"
})
public class PostAdresse {

    @XmlElement(name = "LKZ")
    protected String lkz;
    @XmlElement(name = "PLZ")
    protected String plz;
    @XmlElement(name = "Ort")
    protected String ort;
    @XmlElement(name = "Strasse")
    protected String strasse;
    @XmlElement(name = "HausNr")
    protected String hausNr;
    @XmlElement(name = "X")
    protected String x;
    @XmlElement(name = "Y")
    protected String y;
    @XmlElement(name = "Bezeichnung")
    protected String bezeichnung;
    @XmlElement(name = "Quality", required = true)
    protected Quality quality;
    @XmlElement(name = "Koordinatenformat", required = true)
    protected KoordinatenFormat koordinatenformat;

    /**
     * Gets the value of the lkz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLKZ() {
        return lkz;
    }

    /**
     * Sets the value of the lkz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLKZ(String value) {
        this.lkz = value;
    }

    /**
     * Gets the value of the plz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPLZ() {
        return plz;
    }

    /**
     * Sets the value of the plz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPLZ(String value) {
        this.plz = value;
    }

    /**
     * Gets the value of the ort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrt() {
        return ort;
    }

    /**
     * Sets the value of the ort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrt(String value) {
        this.ort = value;
    }

    /**
     * Gets the value of the strasse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrasse() {
        return strasse;
    }

    /**
     * Sets the value of the strasse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrasse(String value) {
        this.strasse = value;
    }

    /**
     * Gets the value of the hausNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHausNr() {
        return hausNr;
    }

    /**
     * Sets the value of the hausNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHausNr(String value) {
        this.hausNr = value;
    }

    /**
     * Gets the value of the x property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getX() {
        return x;
    }

    /**
     * Sets the value of the x property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setX(String value) {
        this.x = value;
    }

    /**
     * Gets the value of the y property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getY() {
        return y;
    }

    /**
     * Sets the value of the y property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setY(String value) {
        this.y = value;
    }

    /**
     * Gets the value of the bezeichnung property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBezeichnung() {
        return bezeichnung;
    }

    /**
     * Sets the value of the bezeichnung property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBezeichnung(String value) {
        this.bezeichnung = value;
    }

    /**
     * Gets the value of the quality property.
     * 
     * @return
     *     possible object is
     *     {@link Quality }
     *     
     */
    public Quality getQuality() {
        return quality;
    }

    /**
     * Sets the value of the quality property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quality }
     *     
     */
    public void setQuality(Quality value) {
        this.quality = value;
    }

    /**
     * Gets the value of the koordinatenformat property.
     * 
     * @return
     *     possible object is
     *     {@link KoordinatenFormat }
     *     
     */
    public KoordinatenFormat getKoordinatenformat() {
        return koordinatenformat;
    }

    /**
     * Sets the value of the koordinatenformat property.
     * 
     * @param value
     *     allowed object is
     *     {@link KoordinatenFormat }
     *     
     */
    public void setKoordinatenformat(KoordinatenFormat value) {
        this.koordinatenformat = value;
    }

}
