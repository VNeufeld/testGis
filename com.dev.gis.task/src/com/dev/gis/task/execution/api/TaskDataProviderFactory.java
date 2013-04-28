package com.dev.gis.task.execution.api;

import com.dev.gis.task.execution.impl.DefaultTaskDataProvider;
import com.dev.gis.task.execution.impl.LocationSearchTaskDataProvider;

public class TaskDataProviderFactory {
	
	private static ITaskDataProvider createDefaultTaskDataProvider() {
		DefaultTaskDataProvider dataProvider = new DefaultTaskDataProvider();
		return dataProvider;
	}

	public static ITaskDataProvider createTaskDataProvider(String name) {
		if ( name.equals("locationSearch")) {
			return new LocationSearchTaskDataProvider();
		}
		return createDefaultTaskDataProvider();
	}

}
