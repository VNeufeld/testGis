
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
 *         &lt;element name="Ort" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="karte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="erweiterteSuche" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SQLSuche" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Koordinatenformat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="europeOnly" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "ort",
    "karte",
    "erweiterteSuche",
    "sqlSuche",
    "koordinatenformat",
    "europeOnly"
})
@XmlRootElement(name = "GeoCodeUngenauASP")
public class GeoCodeUngenauASP {

    @XmlElement(name = "Ort")
    protected String ort;
    protected String karte;
    protected String erweiterteSuche;
    @XmlElement(name = "SQLSuche")
    protected String sqlSuche;
    @XmlElement(name = "Koordinatenformat")
    protected String koordinatenformat;
    protected String europeOnly;

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
     * Gets the value of the karte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKarte() {
        return karte;
    }

    /**
     * Sets the value of the karte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKarte(String value) {
        this.karte = value;
    }

    /**
     * Gets the value of the erweiterteSuche property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErweiterteSuche() {
        return erweiterteSuche;
    }

    /**
     * Sets the value of the erweiterteSuche property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErweiterteSuche(String value) {
        this.erweiterteSuche = value;
    }

    /**
     * Gets the value of the sqlSuche property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSQLSuche() {
        return sqlSuche;
    }

    /**
     * Sets the value of the sqlSuche property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSQLSuche(String value) {
        this.sqlSuche = value;
    }

    /**
     * Gets the value of the koordinatenformat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKoordinatenformat() {
        return koordinatenformat;
    }

    /**
     * Sets the value of the koordinatenformat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKoordinatenformat(String value) {
        this.koordinatenformat = value;
    }

    /**
     * Gets the value of the europeOnly property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEuropeOnly() {
        return europeOnly;
    }

    /**
     * Sets the value of the europeOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEuropeOnly(String value) {
        this.europeOnly = value;
    }

}
