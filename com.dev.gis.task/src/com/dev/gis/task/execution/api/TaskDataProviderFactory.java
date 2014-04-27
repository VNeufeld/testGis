package com.dev.gis.task.execution.api;

import javax.xml.bind.JAXBException;

import com.dev.gis.task.execution.impl.DefaultTaskDataProvider;
import com.dev.gis.task.execution.impl.GoogleMapDataProvider;
import com.dev.gis.task.execution.impl.LocationSearchTaskDataProvider;
import com.dev.gis.task.execution.impl.LoggingDataProvider;
import com.dev.gis.task.execution.impl.TestADACAppDataProvider;
import com.dev.gis.task.execution.impl.TestADACAppPaymentDataProvider;

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
		}else if ( name.equals(ITaskDataProvider.TASK_APP_BOOKING)) {
			TestADACAppDataProvider dataProvider = new TestADACAppDataProvider();
			dataProvider.loadTask(name);
			return dataProvider;
		}else if ( name.equals(ITaskDataProvider.TASK_APP_PAYMENT)) {
			TestADACAppPaymentDataProvider dataProvider = new TestADACAppPaymentDataProvider();
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
