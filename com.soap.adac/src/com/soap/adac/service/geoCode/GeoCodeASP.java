
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
 *         &lt;element name="LKZ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PLZ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Ort" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Strasse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="karte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="erweiterteSuche" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SQLSuche" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Koordinatenformat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "lkz",
    "plz",
    "ort",
    "strasse",
    "karte",
    "erweiterteSuche",
    "sqlSuche",
    "koordinatenformat"
})
@XmlRootElement(name = "GeoCodeASP")
public class GeoCodeASP {

    @XmlElement(name = "LKZ")
    protected String lkz;
    @XmlElement(name = "PLZ")
    protected String plz;
    @XmlElement(name = "Ort")
    protected String ort;
    @XmlElement(name = "Strasse")
    protected String strasse;
    protected String karte;
    protected String erweiterteSuche;
    @XmlElement(name = "SQLSuche")
    protected String sqlSuche;
    @XmlElement(name = "Koordinatenformat")
    protected String koordinatenformat;

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

}
