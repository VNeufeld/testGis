//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.17 at 02:34:10 PM CET 
//


package com.dev.gis.app.xmlutils.transform.request.getCars;

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
 *         &lt;element ref="{common}UserType"/>
 *         &lt;element ref="{common}LanguageId"/>
 *         &lt;element ref="{common}OperatorId" minOccurs="0"/>
 *         &lt;element ref="{common}SalesChannelId"/>
 *         &lt;element ref="{common}Position" minOccurs="0"/>
 *         &lt;element ref="{common}Amount" minOccurs="0"/>
 *         &lt;element ref="{common}Provider" minOccurs="0"/>
 *         &lt;element ref="{common}ProviderId" minOccurs="0"/>
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
    "userType",
    "languageId",
    "operatorId",
    "salesChannelId",
    "position",
    "amount",
    "provider",
    "providerId"
})
@XmlRootElement(name = "Administration")
public class HsgwRequestAdministration {

    @XmlElement(name = "UserType", namespace = "common", required = true)
    protected String userType;
    @XmlElement(name = "LanguageId", namespace = "common", required = true)
    protected String languageId;
    @XmlElement(name = "OperatorId", namespace = "common")
    protected Integer operatorId;
    @XmlElement(name = "SalesChannelId", namespace = "common")
    protected int salesChannelId;
    @XmlElement(name = "Position", namespace = "common")
    protected Integer position;
    @XmlElement(name = "Amount", namespace = "common")
    protected Integer amount;
    @XmlElement(name = "Provider", namespace = "common")
    protected String provider;
    @XmlElement(name = "ProviderId", namespace = "common")
    protected Integer providerId;

    /**
     * Gets the value of the userType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Sets the value of the userType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserType(String value) {
        this.userType = value;
    }

    /**
     * Gets the value of the languageId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguageId() {
        return languageId;
    }

    /**
     * Sets the value of the languageId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguageId(String value) {
        this.languageId = value;
    }

    /**
     * Gets the value of the operatorId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOperatorId() {
        return operatorId;
    }

    /**
     * Sets the value of the operatorId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOperatorId(Integer value) {
        this.operatorId = value;
    }

    /**
     * ID of saleschannel as defined by MasterData
     * 
     */
    public int getSalesChannelId() {
        return salesChannelId;
    }

    /**
     * Sets the value of the salesChannelId property.
     * 
     */
    public void setSalesChannelId(int value) {
        this.salesChannelId = value;
    }

    /**
     * Gets the value of the position property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPosition(Integer value) {
        this.position = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAmount(Integer value) {
        this.amount = value;
    }

    /**
     * Gets the value of the provider property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvider() {
        return provider;
    }

    /**
     * Sets the value of the provider property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvider(String value) {
        this.provider = value;
    }

    /**
     * Gets the value of the providerId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getProviderId() {
        return providerId;
    }

    /**
     * Sets the value of the providerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setProviderId(Integer value) {
        this.providerId = value;
    }

}
