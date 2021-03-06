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
 *         &lt;element ref="{}Stations" minOccurs="0"/>
 *         &lt;element ref="{}CarCategories" minOccurs="0"/>
 *         &lt;element ref="{}CarForms" minOccurs="0"/>
 *         &lt;element ref="{}Operators" minOccurs="0"/>
 *         &lt;element ref="{}ServiceCatalogs" minOccurs="0"/>
 *         &lt;element ref="{}Suppliers" minOccurs="0"/>
 *         &lt;element ref="{common}AirportStationsOnly" minOccurs="0"/>
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
    "stations",
    "carCategories",
    "carForms",
    "operators",
    "serviceCatalogs",
    "suppliers",
    "airportStationsOnly"
})
@XmlRootElement(name = "Filter")
public class HsgwFilter {

    @XmlElement(name = "Stations")
    protected HsgwStations stations;
    @XmlElement(name = "CarCategories")
    protected HsgwCarCategories carCategories;
    @XmlElement(name = "CarForms")
    protected HsgwCarForms carForms;
    @XmlElement(name = "Operators")
    protected HsgwOperators operators;
    @XmlElement(name = "ServiceCatalogs")
    protected HsgwServiceCatalogs serviceCatalogs;
    @XmlElement(name = "Suppliers")
    protected HsgwSuppliers suppliers;
    @XmlElement(name = "AirportStationsOnly", namespace = "common")
    protected Integer airportStationsOnly;

    /**
     * Contains Station objects. Ignored if PickUpStationId is set
     * 
     * @return
     *     possible object is
     *     {@link HsgwStations }
     *     
     */
    public HsgwStations getStations() {
        return stations;
    }

    /**
     * Sets the value of the stations property.
     * 
     * @param value
     *     allowed object is
     *     {@link HsgwStations }
     *     
     */
    public void setStations(HsgwStations value) {
        this.stations = value;
    }

    /**
     * Gets the value of the carCategories property.
     * 
     * @return
     *     possible object is
     *     {@link HsgwCarCategories }
     *     
     */
    public HsgwCarCategories getCarCategories() {
        return carCategories;
    }

    /**
     * Sets the value of the carCategories property.
     * 
     * @param value
     *     allowed object is
     *     {@link HsgwCarCategories }
     *     
     */
    public void setCarCategories(HsgwCarCategories value) {
        this.carCategories = value;
    }

    /**
     * Gets the value of the carForms property.
     * 
     * @return
     *     possible object is
     *     {@link HsgwCarForms }
     *     
     */
    public HsgwCarForms getCarForms() {
        return carForms;
    }

    /**
     * Sets the value of the carForms property.
     * 
     * @param value
     *     allowed object is
     *     {@link HsgwCarForms }
     *     
     */
    public void setCarForms(HsgwCarForms value) {
        this.carForms = value;
    }

    /**
     * Gets the value of the operators property.
     * 
     * @return
     *     possible object is
     *     {@link HsgwOperators }
     *     
     */
    public HsgwOperators getOperators() {
        return operators;
    }

    /**
     * Sets the value of the operators property.
     * 
     * @param value
     *     allowed object is
     *     {@link HsgwOperators }
     *     
     */
    public void setOperators(HsgwOperators value) {
        this.operators = value;
    }

    /**
     * Gets the value of the serviceCatalogs property.
     * 
     * @return
     *     possible object is
     *     {@link HsgwServiceCatalogs }
     *     
     */
    public HsgwServiceCatalogs getServiceCatalogs() {
        return serviceCatalogs;
    }

    /**
     * Sets the value of the serviceCatalogs property.
     * 
     * @param value
     *     allowed object is
     *     {@link HsgwServiceCatalogs }
     *     
     */
    public void setServiceCatalogs(HsgwServiceCatalogs value) {
        this.serviceCatalogs = value;
    }

    /**
     * Contains Supplier objects. Ignored if PickUpSupplierId is set
     * 
     * @return
     *     possible object is
     *     {@link HsgwSuppliers }
     *     
     */
    public HsgwSuppliers getSuppliers() {
        return suppliers;
    }

    /**
     * Sets the value of the suppliers property.
     * 
     * @param value
     *     allowed object is
     *     {@link HsgwSuppliers }
     *     
     */
    public void setSuppliers(HsgwSuppliers value) {
        this.suppliers = value;
    }

    /**
     * Gets the value of the airportStationsOnly property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAirportStationsOnly() {
        return airportStationsOnly;
    }

    /**
     * Sets the value of the airportStationsOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAirportStationsOnly(Integer value) {
        this.airportStationsOnly = value;
    }

}
