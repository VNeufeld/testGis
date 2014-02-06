package com.dev.gis.task.execution.api;

import javax.xml.bind.JAXBException;

import com.dev.gis.task.execution.impl.DefaultTaskDataProvider;
import com.dev.gis.task.execution.impl.GoogleMapDataProvider;
import com.dev.gis.task.execution.impl.LocationSearchTaskDataProvider;
import com.dev.gis.task.execution.impl.LoggingDataProvider;
import com.dev.gis.task.execution.impl.TestADACAppDataProvider;

public class TaskDataProviderFactory {

	private static ITaskDataProvider createDefaultTaskDataProvider() {
		DefaultTaskDataProvider dataProvider = new DefaultTaskDataProvider();
		return dataProvider;
	}

	public static ITaskDataProvider createTaskDataProvider(String name) throws JAXBException  {
		if (name.equals("locationSearch")) {
			LocationSearchTaskDataProvider dataProvider = new LocationSearchTaskDataProvider();
			dataProvider.loadTask(name);
			return dataProvider;
		}else if ( name.equals("testADAC_App")) {
			TestADACAppDataProvider dataProvider = new TestADACAppDataProvider();
			dataProvider.loadTask(name);
			return dataProvider;
		}else if ( name.equals("googleMap")) {
			GoogleMapDataProvider dataProvider = new GoogleMapDataProvider();
			return dataProvider;
		}
		else if ( name.equals("Splitt")) {
			LoggingDataProvider dataProvider = new LoggingDataProvider();
			return dataProvider;
		}

		return createDefaultTaskDataProvider();
	}

}
