package com.dev.gis.app.view.sunny.requestUtils;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.dev.gis.app.taskmanager.SunnyCarsView.SunnyOfferViewUpdater;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.ModelProvider;
import com.dev.gis.connector.api.VehicleHttpService;
import com.dev.gis.connector.sunny.VehicleRequest;
import com.dev.gis.connector.sunny.VehicleResponse;

public class GetOffersOperation implements IRunnableWithProgress {

	private final VehicleRequest request;
	private final int pageSize;

	private VehicleResponse response;

	/**
	 * LongRunningOperation constructor
	 * 
	 * @param indeterminate
	 *            whether the animation is unknown
	 */
	public GetOffersOperation(VehicleRequest request, int pageSize) {
		this.request = request;
		this.pageSize = pageSize;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {

		String sessionId = ModelProvider.INSTANCE.sessionID;
		
		new SunnyOfferViewUpdater().clearView();
		
		

		JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
		VehicleHttpService service = serviceFactory.getVehicleJoiService();

		monitor.setTaskName("create Vehicle...");
		monitor.beginTask(" running HSGW GetCars ", 15000);

		ShowTimer showTimer = new ShowTimer(monitor);
		new Thread(showTimer).start();

		this.response = service.getOffers(request, false, pageSize, sessionId);
		showTimer.setWork(false);
		monitor.done();

	}

	public VehicleResponse getResponse() {
		return response;
	}
}
