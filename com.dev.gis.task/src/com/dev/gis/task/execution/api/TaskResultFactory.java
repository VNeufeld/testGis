package com.dev.gis.task.execution.api;

import java.util.Date;
import java.util.List;

import com.bpcs.mdcars.protocol.Hit;
import com.bpcs.mdcars.protocol.HitGroup;
import com.bpcs.mdcars.protocol.LocationSearchResult;
import com.dev.gis.task.persistance.impl.TaskResultImpl;

public class TaskResultFactory {
	
	public static ITaskResult createResult(String name) {
		TaskResultImpl result = new TaskResultImpl(name);
		return result;
	}

	public static ITaskResult createLocationResult(LocationSearchResult locationSearchResult, Date startDate) {
		
		TaskResultImpl result = new TaskResultImpl("LocationSearchResult");
		TaskResultItem headerItem = new TaskResultItem("Location search for ");
		result.getTaskList().add( headerItem );
		headerItem.date = startDate;
		
		List<HitGroup>   groups =locationSearchResult.getGroupedList();
		for ( HitGroup group : groups ) {
			List<Hit>  hits = group.getHitGroup();
			for ( Hit hit : hits) {
				StringBuffer searchHit = new StringBuffer(group.getType().name());
				searchHit.append(" : ");
				searchHit.append(hit.getId());
				searchHit.append(" : ");
				searchHit.append(hit.getIdentifier());
				searchHit.append(" : ");
				searchHit.append(hit.getCountry());
						
				TaskResultItem item = new TaskResultItem(searchHit.toString());
				result.getTaskList().add( item );
			}
		}
		
		return result;
	}

}
