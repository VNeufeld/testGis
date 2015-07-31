package com.dev.gis.app.view.sunny.requestUtils;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.dev.gis.app.taskmanager.offerDetailView.OfferViewUpdater;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.VehicleHttpService;
import com.dev.gis.connector.joi.protocol.VehicleRequest;
import com.dev.gis.connector.joi.protocol.VehicleResponse;

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
		VehicleHttpService service = serviceFactory.getVehicleJoiService();

		monitor.setTaskName("create Vehicle...");
		monitor.beginTask(" running HSGW GetCars ", 15000);

		ShowTimer showTimer = new ShowTimer(monitor);
		new Thread(showTimer).start();

		//this.response = service.getOffers(request, false, pageSize);
		showTimer.setWork(false);
		monitor.done();

	}

	public VehicleResponse getResponse() {
		return response;
	}
}
