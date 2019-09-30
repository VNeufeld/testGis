
package com.soap.adac.service.geoCode;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReturnCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReturnCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="ERR_PARAM"/>
 *     &lt;enumeration value="ERR_SYSTEM"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ReturnCode")
@XmlEnum
public enum ReturnCode {

    OK,
    ERR_PARAM,
    ERR_SYSTEM;

    public String value() {
        return name();
    }

    public static ReturnCode fromValue(String v) {
        return valueOf(v);
    }

}
