
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
 *         &lt;element name="postAdr" type="{http://ADAC.ITP.WebServices/}PostAdresse" minOccurs="0"/>
 *         &lt;element name="AnzVorschlaege" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="vListe" type="{http://ADAC.ITP.WebServices/}ArrayOfPostAdresse" minOccurs="0"/>
 *         &lt;element name="karte" type="{http://ADAC.ITP.WebServices/}Karte"/>
 *         &lt;element name="erweiterteSuche" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SQLSuche" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Koordinatenformat" type="{http://ADAC.ITP.WebServices/}KoordinatenFormat"/>
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
    "postAdr",
    "anzVorschlaege",
    "vListe",
    "karte",
    "erweiterteSuche",
    "sqlSuche",
    "koordinatenformat",
    "fehlerText"
})
@XmlRootElement(name = "GeoCode")
public class GeoCode {

    protected PostAdresse postAdr;
    @XmlElement(name = "AnzVorschlaege")
    protected int anzVorschlaege;
    protected ArrayOfPostAdresse vListe;
    @XmlElement(required = true)
    protected Karte karte;
    protected boolean erweiterteSuche;
    @XmlElement(name = "SQLSuche")
    protected boolean sqlSuche;
    @XmlElement(name = "Koordinatenformat", required = true)
    protected KoordinatenFormat koordinatenformat;
    @XmlElement(name = "FehlerText")
    protected String fehlerText;

    /**
     * Gets the value of the postAdr property.
     * 
     * @return
     *     possible object is
     *     {@link PostAdresse }
     *     
     */
    public PostAdresse getPostAdr() {
        return postAdr;
    }

    /**
     * Sets the value of the postAdr property.
     * 
     * @param value
     *     allowed object is
     *     {@link PostAdresse }
     *     
     */
    public void setPostAdr(PostAdresse value) {
        this.postAdr = value;
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
     * Gets the value of the karte property.
     * 
     * @return
     *     possible object is
     *     {@link Karte }
     *     
     */
    public Karte getKarte() {
        return karte;
    }

    /**
     * Sets the value of the karte property.
     * 
     * @param value
     *     allowed object is
     *     {@link Karte }
     *     
     */
    public void setKarte(Karte value) {
        this.karte = value;
    }

    /**
     * Gets the value of the erweiterteSuche property.
     * 
     */
    public boolean isErweiterteSuche() {
        return erweiterteSuche;
    }

    /**
     * Sets the value of the erweiterteSuche property.
     * 
     */
    public void setErweiterteSuche(boolean value) {
        this.erweiterteSuche = value;
    }

    /**
     * Gets the value of the sqlSuche property.
     * 
     */
    public boolean isSQLSuche() {
        return sqlSuche;
    }

    /**
     * Sets the value of the sqlSuche property.
     * 
     */
    public void setSQLSuche(boolean value) {
        this.sqlSuche = value;
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
