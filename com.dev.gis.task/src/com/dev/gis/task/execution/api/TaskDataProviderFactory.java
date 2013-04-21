package com.dev.gis.task.execution.api;

import com.dev.gis.task.execution.impl.DefaultTaskDataProvider;

public class TaskDataProviderFactory {
	public static ITaskDataProvider createDefaultTaskDataProvider() {
		DefaultTaskDataProvider dataProvider = new DefaultTaskDataProvider();
		return dataProvider;
	}

}
