
package com.soap.adac.service.geoCode;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for KoordinatenFormat.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="KoordinatenFormat">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Mercator"/>
 *     &lt;enumeration value="Geodezimal"/>
 *     &lt;enumeration value="Conform"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "KoordinatenFormat")
@XmlEnum
public enum KoordinatenFormat {

    @XmlEnumValue("Mercator")
    MERCATOR("Mercator"),
    @XmlEnumValue("Geodezimal")
    GEODEZIMAL("Geodezimal"),
    @XmlEnumValue("Conform")
    CONFORM("Conform");
    private final String value;

    KoordinatenFormat(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static KoordinatenFormat fromValue(String v) {
        for (KoordinatenFormat c: KoordinatenFormat.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
