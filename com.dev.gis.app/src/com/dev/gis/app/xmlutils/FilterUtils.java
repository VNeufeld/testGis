package com.dev.gis.app.xmlutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;



public class FilterUtils {
	private static final String[] special = new String[] { "cardNumber", "account", "ownerName", "bankCode",":crcardnumber",":accountNumber"};

	
	public static String shadowKeyWords(String json, String[] keys) {
		
		Set<String> userKeys = new HashSet<String>(Arrays.asList(keys));
		for ( String keySpecial : special) {
			userKeys.add(keySpecial);
		}
		
		for ( String key : userKeys) 
			json = shadow(json,key);
		return json;
		
	}
	
    private static String shadow(String json, String template) {
    	json = StringUtils.deleteWhitespace(json);
    	
    	if (!template.startsWith("\""))
    		template = "\""+template;
    	if (!template.endsWith("\":"))
    		template = template + "\":";
    	
    	// check "account":{
    	String templateGroup= template+"{";
    	json = json.replace(templateGroup,StringUtils.left(templateGroup, template.length()-2)+"\"__{");
    	
    	int index = json.indexOf(template);
    	if ( index >= 0) {
    		index = index + template.length();
    		int index1 = json.indexOf('"',index);

    		int index2 = json.indexOf('"',index1+1);
    		
    		String ss = json.substring(index1+1, index2);
    		if ( ss.length() > 0)
    			json= json.replaceAll(ss,"XXXXXXX"+StringUtils.right(ss,2));
    	}
    	return json;
    	
    }

	public static String shadowKeyWordsXml(String xmlRequest, String[] keys) {

		Set<String> userKeys = new HashSet<String>(Arrays.asList(keys));
		for ( String keySpecial : special) {
			userKeys.add(keySpecial);
		}
		
		for ( String key : userKeys) {
			xmlRequest = shadowXmlTag(xmlRequest,key);
			xmlRequest = shadowXmlAttr(xmlRequest,key);
		}
		return xmlRequest;
	}

	private static String shadowXml(String xmlRequest, String template) {
		
    	if (!template.endsWith(">"))
    		template = template + ">";
    	int index = StringUtils.indexOfIgnoreCase(xmlRequest, template);
    	if ( index >= 0) {
    		String prefix = xmlRequest.substring(index,index + template.length()); 
    		
    		index = index + template.length();
    		int index1 = xmlRequest.indexOf('<',index);
    		
    		String ss = xmlRequest.substring(index, index1);
    		if ( ss.length() > 0) {
    			ss = prefix + ss;
    			xmlRequest= xmlRequest.replaceAll(ss,prefix+StringUtils.left(ss,2) + "XXXXXXX");
    		}
    	}
		
		return xmlRequest;
	}

	private static String shadowXmlTag(String xmlRequest, String key) {
		String firstTag = "<"+key+">";
		String endTag = "</"+key+">";
		String[] values = StringUtils.substringsBetween(xmlRequest,firstTag, endTag);
		if ( values != null && values.length > 0) {
			List<String> replaceValues = new ArrayList<String>();
			for ( String value : values) {
				if (StringUtils.isNotBlank(value) && value.length() > 2) {
					String replaceValue = value;
					String parts[] = StringUtils.split(value, "@");
					if ( parts.length == 2) {
						replaceValue = StringUtils.repeat("X", parts[0].length()-2);
						replaceValue = replaceValue + "@" + parts[1];
					}
					else {
						replaceValue = StringUtils.left(value, 2)+StringUtils.repeat("X", value.length()-2);
					}
					replaceValues.add(replaceValue);
				}
				else
					replaceValues.add("XX0");
			}
			String[] replValues = replaceValues.toArray(new String[0]);
			xmlRequest = StringUtils.replaceEach(xmlRequest, values, replValues);
		}
		return xmlRequest;
	}

	private static String shadowXmlAttr(String xmlRequest, String key) {
		String firstTag = key+">";
		String endTag = "</"+key+">";
		String[] values = StringUtils.substringsBetween(xmlRequest,firstTag, endTag);
		if ( values != null && values.length > 0) {
			List<String> replaceValues = new ArrayList<String>();
			for ( String value : values) {
				if (StringUtils.isNotBlank(value) && value.length() > 2) {
					String replaceValue = value;
					String parts[] = StringUtils.split(value, "@");
					if ( parts.length == 2) {
						replaceValue = StringUtils.repeat("X", parts[0].length()-2);
						replaceValue = replaceValue + "@" + parts[1];
					}
					else {
						replaceValue = StringUtils.left(value, 2)+StringUtils.repeat("X", value.length()-2);
					}
					replaceValues.add(replaceValue);
				}
				else
					replaceValues.add("XX0");
			}
			String[] replValues = replaceValues.toArray(new String[0]);
			xmlRequest = StringUtils.replaceEach(xmlRequest, values, replValues);
		}
		return xmlRequest;
	}
	

}
