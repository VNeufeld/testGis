package com.dev.gis.app.taskmanager.testAppView;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.app.taskmanager.bookingView.BookingView;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.connector.api.OfferDo;
import com.dev.gis.connector.joi.protocol.Extra;
import com.dev.gis.connector.joi.protocol.ExtraResponse;
import com.dev.gis.connector.joi.protocol.Offer;
import com.dev.gis.connector.joi.protocol.TravelInformation;
import com.dev.gis.connector.joi.protocol.VehicleResult;
import com.dev.gis.task.execution.api.ITaskResult;
import com.dev.gis.task.execution.api.JoiVehicleConnector;

public class OfferDetailView extends TaskViewAbstract {
	public static final String ID = "com.dev.gis.app.view.OfferDetailView";

	Calendar checkInDate = Calendar.getInstance();
	Calendar dropOffDate = Calendar.getInstance();

	private Text textOfferLink = null;

	private Text priceText = null;

	private Text carDescription = null;

	private Offer selectedOffer = null;

	private TravelInformation travelInformation;

	private Text pickUpCityId;

	private Text dropOffCityId;

	private Text dropOffStationId;

	private Text pickupStationsResponse;

	private Text recalculateResponse;
	
	private AdacExtraListTable extraListTable;
	
	private AdacInclusivesListTable inclusivesListTable;
	
	
	private Composite parent;

	@Override
	public void createPartControl(Composite parent) {
		
		this.parent = parent;

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		// Offer - Group
		Composite offerGroup = createOfferGroup(composite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, false).applyTo(offerGroup);

		Composite rbComp = createRequestButtons(composite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, false).applyTo(rbComp);

		// Extras
		Composite inclusivesGroup = addInclusivesGroup(composite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, true).applyTo(inclusivesGroup);
		
		// Extras
		Composite extraGroup = addExtraGroup(composite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, true).applyTo(extraGroup);

		// Booking
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, false).applyTo(addBookingButton(composite));

	}

	private Composite createOfferGroup(Composite composite) {

		final Group groupStamp = new Group(composite, SWT.TITLE);
		groupStamp.setText("Offer:");
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false)
				.applyTo(groupStamp);

		Label cityLabel = new Label(groupStamp, SWT.NONE);
		cityLabel.setText("Offer");
		textOfferLink = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, false).applyTo(textOfferLink);

		Label priceLabel = new Label(groupStamp, SWT.NONE);
		priceLabel.setText("Preis:");
		priceText = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);

		Label carDescriptionLabel = new Label(groupStamp, SWT.NONE);
		carDescriptionLabel.setText("Fahrzeug:");
		carDescription = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.FILL)
				.hint(300, 16).grab(false, false).applyTo(carDescription);

		Label pickUpCityIdLabel = new Label(groupStamp, SWT.NONE);
		pickUpCityIdLabel.setText("PickUpCityId:");
		pickUpCityId = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);

		Label dropOffCityIdLabel = new Label(groupStamp, SWT.NONE);
		dropOffCityIdLabel.setText("DropOff CityId:");
		dropOffCityId = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);

		Label dropOffStation = new Label(groupStamp, SWT.NONE);
		dropOffStation.setText("DropOff StationId:");
		dropOffStationId = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);

		return groupStamp;
	}

	private Composite addExtraGroup(Composite composite) {
		final Group group = new Group(composite, SWT.TITLE);
		group.setText("Extras:");
		group.setLayout(new GridLayout(1, true));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		new ButtonControl(group, "Get Extras", 0,  new AddGetExtrasListener());
		
		extraListTable = new AdacExtraListTable(getSite(), group, null);

		return group;
	}
	
	private Composite addInclusivesGroup(Composite composite) {
		final Group group = new Group(composite, SWT.TITLE);
		group.setText("Inclusives:");
		group.setLayout(new GridLayout(1, true));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		inclusivesListTable = new AdacInclusivesListTable(getSite(), group, null);

		return group;
	}
	

	private Composite addBookingButton(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false)
				.applyTo(composite);

		final Button buttonBooking = new Button(composite, SWT.PUSH | SWT.LEFT);
		buttonBooking.setText("Booking");

		buttonBooking.addSelectionListener(new AddBookingListener());

		return composite;
	}

	protected class AddGetExtrasListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			// ExtraResponse response =
			// JoiVehicleConnector.getExtras(selectedOffer);
			ExtraResponse response = JoiVehicleConnector.getExtrasDummy();
			extraListTable.refresh(response);
		}
	}

	protected class AddBookingListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			List<Extra> extras = extraListTable.getSelectedExtras();
			BookingView.updateView(selectedOffer, extras);

		}

	}

	protected class AddRecalculateListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			recalculateResponse.setText("running....");

			if (StringUtils.isNotEmpty(dropOffStationId.getText())
					&& StringUtils.isNumeric(dropOffStationId.getText()))
				travelInformation.getDropOffLocation().setStationId(
						Long.valueOf(dropOffStationId.getText()));
			// travelInformation.getDropOffLocation().setStationId(167956);420222

			String response = JoiVehicleConnector.recalculate(selectedOffer,
					travelInformation);
			recalculateResponse.setText(response);

		}

	}

	protected class AddPickupStationsListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			pickupStationsResponse.setText("running....");
			String response = JoiVehicleConnector
					.getPickupStations(selectedOffer);
			pickupStationsResponse.setText(response);
		}

	}

	private Composite createRequestButtons(final Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false)
				.applyTo(composite);

		final Button buttonRecalculate = new Button(composite, SWT.PUSH
				| SWT.LEFT);
		buttonRecalculate.setText("POST Recalculate");

		buttonRecalculate.addSelectionListener(new AddRecalculateListener());

		recalculateResponse = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, false).applyTo(recalculateResponse);

		final Button buttonGetPickupStations = new Button(composite, SWT.PUSH
				| SWT.LEFT);
		buttonGetPickupStations.setText("Get PickupStations");
		buttonGetPickupStations
				.addSelectionListener(new AddPickupStationsListener());

		pickupStationsResponse = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, false).applyTo(pickupStationsResponse);

		return composite;

	}


	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(ITaskResult result) {
		// TODO Auto-generated method stub

	}


	public void showOffer(OfferDo offerDo) {
		VehicleResult vehicleResult = offerDo.getModel();
		textOfferLink.setText(vehicleResult.getOfferList().get(0).getBookLink()
				.toString());
		priceText.setText(vehicleResult.getOfferList().get(0).getPrice()
				.getAmount());
		carDescription.setText(vehicleResult.getVehicle().getManufacturer()
				+ " Group : " + vehicleResult.getVehicle().getCategoryId());

		this.selectedOffer = vehicleResult.getOfferList().get(0);

		this.travelInformation = offerDo.getTravelInformation();

		this.travelInformation.setPickUpLocation(vehicleResult
				.getPickUpLocation());
		this.travelInformation.setDropOffLocation(vehicleResult
				.getDropOffLocation());

		pickUpCityId.setText(String.valueOf(this.travelInformation
				.getPickUpLocation().getCityId()));
		dropOffCityId.setText(String.valueOf(this.travelInformation
				.getDropOffLocation().getCityId()));
		
		inclusivesListTable.update(selectedOffer.getInclusives());

	}

}
