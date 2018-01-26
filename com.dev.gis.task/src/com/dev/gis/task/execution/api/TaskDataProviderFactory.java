package com.dev.gis.task.execution.api;

import javax.xml.bind.JAXBException;

import com.dev.gis.task.execution.impl.ClubMobilReservationDataProvider;
import com.dev.gis.task.execution.impl.ClubMobilReservationTask;
import com.dev.gis.task.execution.impl.ClubMobilDataProvider;
import com.dev.gis.task.execution.impl.ClubMobilDefectsDataProvider;
import com.dev.gis.task.execution.impl.ClubMobilDispositionDataProvider;
import com.dev.gis.task.execution.impl.DefaultTaskDataProvider;
import com.dev.gis.task.execution.impl.EmlDataProvider;
import com.dev.gis.task.execution.impl.GoogleMapDataProvider;
import com.dev.gis.task.execution.impl.LocationSearchTaskDataProvider;
import com.dev.gis.task.execution.impl.LoggingDataProvider;
import com.dev.gis.task.execution.impl.TestADACAppDataProvider;
import com.dev.gis.task.execution.impl.TestADACAppPaymentDataProvider;
import com.dev.gis.task.execution.sunnycars.SunnyCarsDataProvider;

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
		else if ( name.equals("SearchSession")) {
			LoggingDataProvider dataProvider = new LoggingDataProvider(1);
			return dataProvider;
		}
		else if ( name.equals("Splitt")) {
			LoggingDataProvider dataProvider = new LoggingDataProvider(2);
			return dataProvider;
		}
		else if ( name.equals("EML_Creator")) {
			EmlDataProvider dataProvider = new EmlDataProvider();
			return dataProvider;
		}
		else if ( name.contains("3.SunnyCars")) {
			SunnyCarsDataProvider dataProvider = new SunnyCarsDataProvider();
			return dataProvider;
		}
		else if ( name.contains("ClubMobil")) {
			ClubMobilDataProvider dataProvider = new ClubMobilDataProvider();
			return dataProvider;
		}
		else if ( name.contains("CM Reservation")) {
			ClubMobilReservationDataProvider dataProvider = new ClubMobilReservationDataProvider();
			return dataProvider;
		}
		else if ( name.contains("CM Disposition")) {
			ClubMobilDispositionDataProvider dataProvider = new ClubMobilDispositionDataProvider();
			return dataProvider;
		}
		else if ( name.contains("CM Defects")) {
			ClubMobilDefectsDataProvider dataProvider = new ClubMobilDefectsDataProvider();
			return dataProvider;
		}
		

		return createDefaultTaskDataProvider();
	}

}
