package com.dev.gis.connector.sunny;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class HitGroup extends BasicProtocol{
	
	private int nr;
	
	private int totalHits;

	private List<Hit> hitGroup;
	
	private HitType type; 

	
	
	public void addHit (Hit hit) {
		if (hitGroup == null)
			hitGroup = new ArrayList<Hit> ();
		hitGroup.add(hit);
		nr = hitGroup.size();
	}
	
	public List<Hit> getHitGroup() {
		return hitGroup;
	}

	public int getNr() {
		return nr;
	}

	public int getTotalHits() {
		return totalHits;
	}

	public HitType getType() {
		return type;
	}
	
	public void setHitGroup(List<Hit> hitList) {
		this.hitGroup = hitList;
	}
	
	public void setNr(int nr) {
		this.nr = nr;
	}

	public void setTotalHits(int totalHits) {
		this.totalHits = totalHits;
	}

	@JsonDeserialize(using = HitTypeJsonDesirializer.class)
	public void setType(HitType type) {
		this.type = type;
	}
}