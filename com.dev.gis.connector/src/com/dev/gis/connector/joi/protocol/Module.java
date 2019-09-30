package com.dev.gis.connector.joi.protocol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.xml.bind.annotation.XmlEnumValue;


public enum Module {
	
	@XmlEnumValue(value="-1") 
	UNINITIALIZED(-1),

	@XmlEnumValue(value="1") 	CAR(1),
	@XmlEnumValue(value="11")	TRUCK(11);

	private int ordinal;

	
	
	Module(int ordinal) {
		this.ordinal = ordinal;
	}

	public int getOrdinal () {
		return ordinal;
	}
	
	public static Collection<Integer> getOrdinals (Module[] moduleArray) {
		ArrayList<Integer> a = new ArrayList<Integer>();
		for (Module m : moduleArray) {
			a.add(m.ordinal);
		}
		
		return a;
	}
	
	public static Collection<Integer> getOrdinals (Module module) {
		return Collections.singletonList(module.getOrdinal());
	}
}
