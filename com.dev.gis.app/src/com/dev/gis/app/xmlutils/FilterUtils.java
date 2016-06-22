package com.dev.gis.app.xmlutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;



public class FilterUtils {

	private static Logger logger = Logger.getLogger(FilterUtils.class);

	
	
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
	
	
	public static String shadowKeyWords(String xmlString, String[] shadowkeys, String[] shadowPhonekeys) {
		try {
			String[] tags =getXmlTags(xmlString);
			if ( tags != null) {
				
				String[] shadowPhoneValues = findValuesToShadow(xmlString,tags, shadowPhonekeys);
				String[] replacedPhoneValues = createReplacePhoneValues(shadowPhoneValues);
				
				xmlString = StringUtils.replaceEach(xmlString, shadowPhoneValues, replacedPhoneValues);
				
				
				String[] shadowValues = findValuesToShadow(xmlString,tags, shadowkeys);
				String[] replacedValues = createReplaceValues(shadowValues);
				
				xmlString = StringUtils.replaceEach(xmlString, shadowValues, replacedValues);
			}
		}
		catch ( Exception err) {
			logger.error(err.getMessage(),err);
		}
		return xmlString;
	}
	
	
	
	private static String[] createReplaceValues(String[] values) {
		List<String> replaceValues = new ArrayList<String>();
		for ( String value : values) {
			if (StringUtils.isNotBlank(value) && value.length() > 2) {
				String replaceValue = value;
				String parts[] = StringUtils.split(value, "@");
				if ( parts.length == 2) {
					replaceValue = StringUtils.repeat("*", parts[0].length());
					replaceValue = replaceValue + "@" + parts[1];
				}
				else {
					replaceValue = StringUtils.left(value, 2)+StringUtils.repeat("*", value.length()-2);
				}
				replaceValues.add(replaceValue);
			}
			else
				replaceValues.add("XX0");
		}
		String[] replValues = replaceValues.toArray(new String[0]);

		return replValues;
	}

	private static String[] createReplacePhoneValues(String[] values) {
		List<String> replaceValues = new ArrayList<String>();
		for ( String value : values) {
			if (StringUtils.isNotBlank(value) && value.length() > 1) {
				String replaceValue = value;
				replaceValue = StringUtils.repeat("*", value.length());
				replaceValues.add(replaceValue);
			}
			else
				replaceValues.add("XX0");
		}
		String[] replValues = replaceValues.toArray(new String[0]);

		return replValues;
	}

	
	private static String[] findValuesToShadow(String xmlString, String[] tags, String[] shadowKeys) {
		Set<String> values = new HashSet<String>();
		Set<String> keySet = new HashSet<String>(Arrays.asList(shadowKeys));

		int count = tags.length;
		for ( int i = 0; i < count; i++) {
			if ( i < count -1 && ( tags[i+1].equals("/"+tags[i])  && contain(keySet, tags[i]))) {
				String value = StringUtils.substringBetween(xmlString,tags[i]+">", "<"+tags[i+1]);
				if ( StringUtils.isNotBlank(value) && value.length() > 2) {
					if ( !values.contains(value))
						values.add(value);
				}
				i++;
			}
			else {
				if ( tags[i].contains("=")) {
					for ( String key : shadowKeys) {
						String value = getAttributeValue(tags[i],key);
						if ( StringUtils.isNotBlank(value) && value.length() > 2) {
							if ( !values.contains(value))
								values.add(value);
						}
					}
				}
			}
		}
		List<String> valueList = new ArrayList<String>(values);
		Collections.sort(valueList,new StringLengthComparator());
		return valueList.toArray(new String[0]);
	}
	
	private static String[] getCriticalMailValues(String xmlString, String[] tags, String[] keys) {
		List<String> values = new ArrayList<String>();
		Set<String> keySet = new HashSet<String>(Arrays.asList(keys));
		int count = tags.length;
		for ( int i = 0; i < count; i++) {
			if ( i < count -1 && ( tags[i+1].equals("/"+tags[i])  && contain(keySet, tags[i]))) {
				String value = StringUtils.substringBetween(xmlString,tags[i]+">", "<"+tags[i+1]);
				if ( StringUtils.isNotBlank(value) && value.length() > 2 && value.contains("@")) {
					values.add(value);
				}
				i++;
			}
			else {
				if ( tags[i].contains("=")) {
					for ( String key : keys) {
						String value = getAttributeValue(tags[i],key);
						if ( StringUtils.isNotBlank(value) && value.length() > 2 && value.contains("@")) {
							values.add(value);
						}
					}
				}
			}
		}
		return values.toArray(new String[0]);
	}


	private static boolean contain(Set<String> keySet, String tag) {
		if ( keySet.contains(tag))
			return true;
		if ( tag.contains(":")) {
			String[] parts = tag.split(":");
			tag = parts[1];
		}
		if ( keySet.contains(tag))
			return true;
		return false;
	}

	private static String[] getXmlTags(String xmlString) {
		String[] tags = StringUtils.substringsBetween(xmlString, "<", ">");
		return tags;
	}
	private static String getAttributeValue(String xmlElement, String shadowKey) {
		if ( StringUtils.isNotBlank(shadowKey) && shadowKey.length() > 2) {
			// Attribute hat immer ein Leerzeichen vorher
			int posAttribute = StringUtils.indexOf(xmlElement, " "+shadowKey);
			if ( posAttribute >= 0) {
				int pos1 =StringUtils.indexOf(xmlElement, "\"",posAttribute);
				if ( pos1 > 0) {
					int pos2 =StringUtils.indexOf(xmlElement, "\"",pos1+1);
					if ( pos2 > 0 ) {
						String value = StringUtils.substring(xmlElement,pos1+1, pos2);
						return value;
					}
				}
					
			}
		}
		return null;
	}
	

	private static class StringLengthComparator implements Comparator<String>
	{

	    public int compare(String s1, String s2)
	    {
	    	if ( s1.length() > s2.length() )
	    		return -1;
	    	else if ( s1.length() < s2.length() )
	    		return 1;

	    	return 0;
	    }

	}
	
}
