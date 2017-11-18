package com.dev.gis.app.taskmanager.clubMobilView;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.dev.gis.app.view.sunny.requestUtils.ShowTimer;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.joi.protocol.VehicleRequest;
import com.dev.gis.connector.joi.protocol.VehicleResponse;

public class ClubMobilGetOffersOperation implements IRunnableWithProgress {

	private final VehicleRequest request;
	private final int pageSize;

	private VehicleResponse response;

	/**
	 * LongRunningOperation constructor
	 * 
	 * @param indeterminate
	 *            whether the animation is unknown
	 */
	public ClubMobilGetOffersOperation(VehicleRequest request, int pageSize) {
		this.request = request;
		this.pageSize = pageSize;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {

		new ClubMobilOfferViewUpdater().clearView();

		JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
		ClubMobilHttpService service = serviceFactory
				.getClubMobilleJoiService();

		monitor.setTaskName("create Vehicle...");
		monitor.beginTask(" running HSGW GetCars ", 15000);

		ShowTimer showTimer = new ShowTimer(monitor);
		new Thread(showTimer).start();

		boolean crossOfferFlag = ClubMobilModelProvider.INSTANCE.crossOfferFlag;

		this.response = service.getOffers(request, false, pageSize,
				crossOfferFlag);
		showTimer.setWork(false);
		monitor.done();

	}

	public VehicleResponse getResponse() {
		return response;
	}
}
