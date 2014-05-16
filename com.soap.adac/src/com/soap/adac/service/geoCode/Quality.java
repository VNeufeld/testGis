
package com.soap.adac.service.geoCode;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Quality.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Quality">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Perfect"/>
 *     &lt;enumeration value="High"/>
 *     &lt;enumeration value="Medium"/>
 *     &lt;enumeration value="Low"/>
 *     &lt;enumeration value="None"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Quality")
@XmlEnum
public enum Quality {

    @XmlEnumValue("Perfect")
    PERFECT("Perfect"),
    @XmlEnumValue("High")
    HIGH("High"),
    @XmlEnumValue("Medium")
    MEDIUM("Medium"),
    @XmlEnumValue("Low")
    LOW("Low"),
    @XmlEnumValue("None")
    NONE("None");
    private final String value;

    Quality(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Quality fromValue(String v) {
        for (Quality c: Quality.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
