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
 *         &lt;element ref="{common}CalledFrom"/>
 *         &lt;element ref="{}DemandedObjects"/>
 *         &lt;element ref="{}Administration"/>
 *         &lt;element ref="{}Agency"/>
 *         &lt;element ref="{}Customer" minOccurs="0"/>
 *         &lt;element ref="{}Travel"/>
 *         &lt;element ref="{}Payment" minOccurs="0"/>
 *         &lt;element ref="{}Filter" minOccurs="0"/>
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
    "calledFrom",
    "demandedObjects",
    "administration",
    "agency",
    "customer",
    "travel",
    "payment",
    "filter"
})
@XmlRootElement(name = "Request")
public class HsgwGetCarsRequest {

    @XmlElement(name = "CalledFrom", namespace = "common")
    protected short calledFrom;
    @XmlElement(name = "DemandedObjects", required = true)
    protected HsgwDemandedObjects demandedObjects;
    @XmlElement(name = "Administration", required = true)
    protected HsgwRequestAdministration administration;
    @XmlElement(name = "Agency", required = true)
    protected HsgwAgency agency;
    @XmlElement(name = "Customer")
    protected HsgwCustomer customer;
    @XmlElement(name = "Travel", required = true)
    protected HsgwTravel travel;
    @XmlElement(name = "Payment")
    protected HsgwPayment payment;
    @XmlElement(name = "Filter")
    protected HsgwFilter filter;

    /**
     * Gets the value of the calledFrom property.
     * 
     */
    public short getCalledFrom() {
        return calledFrom;
    }

    /**
     * Sets the value of the calledFrom property.
     * 
     */
    public void setCalledFrom(short value) {
        this.calledFrom = value;
    }

    /**
     * Gets the value of the demandedObjects property.
     * 
     * @return
     *     possible object is
     *     {@link HsgwDemandedObjects }
     *     
     */
    public HsgwDemandedObjects getDemandedObjects() {
        return demandedObjects;
    }

    /**
     * Sets the value of the demandedObjects property.
     * 
     * @param value
     *     allowed object is
     *     {@link HsgwDemandedObjects }
     *     
     */
    public void setDemandedObjects(HsgwDemandedObjects value) {
        this.demandedObjects = value;
    }

    /**
     * Gets the value of the administration property.
     * 
     * @return
     *     possible object is
     *     {@link HsgwRequestAdministration }
     *     
     */
    public HsgwRequestAdministration getAdministration() {
        return administration;
    }

    /**
     * Sets the value of the administration property.
     * 
     * @param value
     *     allowed object is
     *     {@link HsgwRequestAdministration }
     *     
     */
    public void setAdministration(HsgwRequestAdministration value) {
        this.administration = value;
    }

    /**
     * Gets the value of the agency property.
     * 
     * @return
     *     possible object is
     *     {@link HsgwAgency }
     *     
     */
    public HsgwAgency getAgency() {
        return agency;
    }

    /**
     * Sets the value of the agency property.
     * 
     * @param value
     *     allowed object is
     *     {@link HsgwAgency }
     *     
     */
    public void setAgency(HsgwAgency value) {
        this.agency = value;
    }

    /**
     * Gets the value of the customer property.
     * 
     * @return
     *     possible object is
     *     {@link HsgwCustomer }
     *     
     */
    public HsgwCustomer getCustomer() {
        return customer;
    }

    /**
     * Sets the value of the customer property.
     * 
     * @param value
     *     allowed object is
     *     {@link HsgwCustomer }
     *     
     */
    public void setCustomer(HsgwCustomer value) {
        this.customer = value;
    }

    /**
     * Gets the value of the travel property.
     * 
     * @return
     *     possible object is
     *     {@link HsgwTravel }
     *     
     */
    public HsgwTravel getTravel() {
        return travel;
    }

    /**
     * Sets the value of the travel property.
     * 
     * @param value
     *     allowed object is
     *     {@link HsgwTravel }
     *     
     */
    public void setTravel(HsgwTravel value) {
        this.travel = value;
    }

    /**
     * Contains the payment. Type 1 = credit card, 2 = debit note, 3 = agency. Has influence to car price because  some payment types have additional discounts. Changing this type in a following bookingRequest can cause a price change
     * 
     * @return
     *     possible object is
     *     {@link HsgwPayment }
     *     
     */
    public HsgwPayment getPayment() {
        return payment;
    }

    /**
     * Sets the value of the payment property.
     * 
     * @param value
     *     allowed object is
     *     {@link HsgwPayment }
     *     
     */
    public void setPayment(HsgwPayment value) {
        this.payment = value;
    }

    /**
     * Gets the value of the filter property.
     * 
     * @return
     *     possible object is
     *     {@link HsgwFilter }
     *     
     */
    public HsgwFilter getFilter() {
        return filter;
    }

    /**
     * Sets the value of the filter property.
     * 
     * @param value
     *     allowed object is
     *     {@link HsgwFilter }
     *     
     */
    public void setFilter(HsgwFilter value) {
        this.filter = value;
    }

}
