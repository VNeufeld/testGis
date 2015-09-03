package com.dev.gis.app.view.sunny.requestUtils;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.dev.gis.app.taskmanager.testAppView.OfferViewUpdater;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.AdacVehicleHttpService;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.ModelProvider;
import com.dev.gis.connector.api.VehicleHttpService;
import com.dev.gis.connector.joi.protocol.VehicleRequest;
import com.dev.gis.connector.joi.protocol.VehicleResponse;
import com.dev.gis.task.execution.api.JoiVehicleConnector;

public class AdacGetOffersOperation implements IRunnableWithProgress {

	private final VehicleRequest request;
	private final int pageSize;

	private VehicleResponse response;

	/**
	 * LongRunningOperation constructor
	 * 
	 * @param indeterminate
	 *            whether the animation is unknown
	 */
	public AdacGetOffersOperation(VehicleRequest request, int pageSize) {
		this.request = request;
		this.pageSize = pageSize;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {

		new OfferViewUpdater().clearView();

		JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
		AdacVehicleHttpService service = serviceFactory.getAdacVehicleJoiService();

		monitor.setTaskName("create Vehicle...");
		monitor.beginTask(" running HSGW GetCars ", 15000);

		ShowTimer showTimer = new ShowTimer(monitor);
		new Thread(showTimer).start();
		
		String crossOfferOperator = AdacModelProvider.INSTANCE.crossOfferOperator;

		this.response = service.getOffers(request, false, pageSize, crossOfferOperator);
		showTimer.setWork(false);
		monitor.done();

	}

	public VehicleResponse getResponse() {
		return response;
	}
}
