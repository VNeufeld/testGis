
package com.soap.adac.service.geoCode;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfPostAdresse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfPostAdresse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PostAdresse" type="{http://ADAC.ITP.WebServices/}PostAdresse" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfPostAdresse", propOrder = {
    "postAdresse"
})
public class ArrayOfPostAdresse {

    @XmlElement(name = "PostAdresse", nillable = true)
    protected List<PostAdresse> postAdresse;

    /**
     * Gets the value of the postAdresse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the postAdresse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPostAdresse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PostAdresse }
     * 
     * 
     */
    public List<PostAdresse> getPostAdresse() {
        if (postAdresse == null) {
            postAdresse = new ArrayList<PostAdresse>();
        }
        return this.postAdresse;
    }

}
