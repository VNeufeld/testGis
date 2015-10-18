package com.dev.gis.app.taskmanager.testAppView;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.dev.gis.app.taskmanager.rentcars.RentCarOfferDetailBasicView;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.AdacVehicleHttpService;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.OfferDo;
import com.dev.gis.connector.joi.protocol.Extra;
import com.dev.gis.connector.joi.protocol.ExtraResponse;
import com.dev.gis.connector.joi.protocol.Offer;
import com.dev.gis.connector.joi.protocol.TravelInformation;
import com.dev.gis.connector.joi.protocol.VehicleResult;
import com.dev.gis.task.execution.api.JoiVehicleConnector;

public class OfferDetailView extends RentCarOfferDetailBasicView {
	public static final String ID = "com.dev.gis.app.view.OfferDetailView";

	protected AdacVehicleInfoControl vehicleInfo;

	protected AdacLocationInfoControl locationInfoControl;
	
	protected Offer selectedOffer = null;

	protected TravelInformation travelInformation;
	
	protected AdacExtraListTable extraListTable;
	
	protected AdacInclusivesListTable inclusivesListTable;
	
	protected OutputTextControls priceText = null;

	protected OutputTextControls priceStd = null;

	protected OutputTextControls priceDaily = null;
	

	@Override
	protected void createPrice(Group groupStamp) {
		
		final Composite priceComposite = createComposite(groupStamp, 6, -1,  true);
		
		priceText = new OutputTextControls(priceComposite, "Preis:", 200, 1 );
		priceStd = new OutputTextControls(priceComposite, "PreisStd:", 200, 1 );
		priceDaily = new OutputTextControls(priceComposite, "PreisDaily:", 200, 1 );
	}

	
	public void showOffer(OfferDo offerDo) {
		
		VehicleResult vehicleResult = offerDo.getModel();
		
		AdacModelProvider.INSTANCE.setSelectedOffer(offerDo);
		
		if ( vehicleResult == null )
			return;
		
		Offer offer = getOffer(vehicleResult);
		
		if ( offer != null) {

			this.selectedOffer = offer;

			textOfferLink.setValue(offer.getBookLink().toString());
		
			priceText.setValue(offer.getPrice().toString());

			priceStd.setValue(offer.getStdPrice().toString());

			priceDaily.setValue(offer.getDailyPrice().toString());
			
			inclusivesListTable.update(selectedOffer.getInclusives());
		}
		
		vehicleInfo.update(vehicleResult.getVehicle());

		this.travelInformation = offerDo.getTravelInformation();

		this.travelInformation.setPickUpLocation(vehicleResult
				.getPickUpLocation());
		this.travelInformation.setDropOffLocation(vehicleResult
				.getDropOffLocation());
		
		locationInfoControl.update(offerDo.getTravelInformation());
		

	}


	private com.dev.gis.connector.joi.protocol.Offer getOffer(VehicleResult vehicleResult) {
		com.dev.gis.connector.joi.protocol.Offer offer = null;
		if ( vehicleResult != null & vehicleResult.getOfferList() != null && vehicleResult.getOfferList().size() > 0)
			offer = vehicleResult.getOfferList().get(0);
		
		return offer;
	}


	@Override
	protected void createVehicleInfoOutput(Group groupStamp) {
		vehicleInfo = new AdacVehicleInfoControl(groupStamp);
		
	}


	@Override
	protected void createLocationGroup(Group groupStamp) {
		locationInfoControl = new AdacLocationInfoControl(groupStamp);
		
	}
	
	@Override
	protected  void addExtraGroup(Composite composite) {
		final Group group = new Group(composite, SWT.TITLE);
		group.setText("Extras:");
		group.setLayout(new GridLayout(1, true));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
		.grab(true, true).applyTo(group);
		
		new ButtonControl(group, "Get Extras", 0,  new AddGetExtrasListener());
		
		extraListTable = new AdacExtraListTable(getSite(), group, null);
		

	}
	
	@Override
	protected void addInclusivesGroup(Composite composite) {
		final Group group = new Group(composite, SWT.TITLE);
		group.setText("Inclusives:");
		group.setLayout(new GridLayout(1, true));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
		.grab(true, true).applyTo(group);
		
		inclusivesListTable = new AdacInclusivesListTable(getSite(), group, null);


	}

	

	@Override
	protected SelectionListener getRecalculateSelectionListener() {
		AddRecalculateListener listener = new AddRecalculateListener();
		return listener;
		
	}

	@Override
	protected SelectionListener getBookingViewSelectionListener() {
		AddBookingListener listener = new AddBookingListener();
		return listener;
	}

	@Override
	protected SelectionListener getPickupStationSelectionListener() {
		AddPickupStationsListener listener = new AddPickupStationsListener();
		return listener;
	}
	
	protected class AddRecalculateListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			recalculateResponse.setText("running....");
			
			String dropOffStationId = locationInfoControl.getDropOffStationId();

			if (StringUtils.isNotEmpty(dropOffStationId))
				travelInformation.getDropOffLocation().setStationId(
						Long.valueOf(dropOffStationId));

			String response = JoiVehicleConnector.recalculate(selectedOffer,
					travelInformation);
			recalculateResponse.setText(response);

		}

	}

	protected class AddBookingListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			List<Extra> extras = extraListTable.getSelectedExtras();
			BookingView.updateView(extras);

		}

	}

	protected class AddPickupStationsListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			pickupStationsResponse.setText("running....");
			
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			AdacVehicleHttpService service = serviceFactory.getAdacVehicleJoiService();
			String response  = service.getPickupStations(selectedOffer);
			pickupStationsResponse.setText(response);
			
		}

	}

	protected class AddGetExtrasListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			AdacVehicleHttpService service = serviceFactory.getAdacVehicleJoiService();
			ExtraResponse response  = service.getExtras(selectedOffer);
			extraListTable.refresh(response);
		}
	}

	

}
