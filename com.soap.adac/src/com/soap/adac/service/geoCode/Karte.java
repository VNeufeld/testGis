
package com.soap.adac.service.geoCode;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Karte.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Karte">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Europa"/>
 *     &lt;enumeration value="Nordamerika"/>
 *     &lt;enumeration value="Australien"/>
 *     &lt;enumeration value="Welt"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Karte")
@XmlEnum
public enum Karte {

    @XmlEnumValue("Europa")
    EUROPA("Europa"),
    @XmlEnumValue("Nordamerika")
    NORDAMERIKA("Nordamerika"),
    @XmlEnumValue("Australien")
    AUSTRALIEN("Australien"),
    @XmlEnumValue("Welt")
    WELT("Welt");
    private final String value;

    Karte(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Karte fromValue(String v) {
        for (Karte c: Karte.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
