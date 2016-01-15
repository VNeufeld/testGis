package com.dev.gis.app.taskmanager.SunnyCarsView;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
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
import com.dev.gis.app.view.elements.GetDropoffStationControl;
import com.dev.gis.app.view.elements.GetPickupStationControl;
import com.dev.gis.app.view.sunny.requestUtils.CreateVehicleRequestUtils;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.api.SunnyOfferDo;
import com.dev.gis.connector.api.VehicleHttpService;
import com.dev.gis.connector.sunny.Address;
import com.dev.gis.connector.sunny.Extra;
import com.dev.gis.connector.sunny.Location;
import com.dev.gis.connector.sunny.Offer;
import com.dev.gis.connector.sunny.OfferInformation;
import com.dev.gis.connector.sunny.Station;
import com.dev.gis.connector.sunny.TravelInformation;
import com.dev.gis.connector.sunny.VehicleRequest;
import com.dev.gis.connector.sunny.VehicleResponse;

public class SunnyOfferDetailView extends TaskViewAbstract {
	public static final String ID = "com.dev.gis.app.view.SunnyOfferDetailView";

	Calendar checkInDate = Calendar.getInstance();
	Calendar dropOffDate = Calendar.getInstance();

	private Text textOfferLink = null;

	private Text priceText = null;

	private Text carDescription = null;

	private SunnyInclusivesListTable inclusivesListTable;
	
	private SunnyExtraListTable extrasListTable;

	private SunnyOfferDo selectedOffer = null;

	private Text serviceCatalog;
	
	private Text pickUpStation;

	private Text dropOffStation;

	private Text recalculateResponse;
	
	private GetPickupStationControl pickupStationControl; 	
	
	private GetDropoffStationControl dropoffStationControl;

	@Override
	public void createPartControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		// Offer - Group
		Composite offerGroup = createOfferGroup(composite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(offerGroup);


		Composite rbComp = createRequestButtons(composite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(rbComp);

		
		// Inclusives
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(addInclusivesGroup(composite));
		
		// Extras
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(addExtraGroup(composite));
		

		// Booking
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(addBookingButton(composite));

	}
	
	private Composite createOfferGroup(Composite composite) {

		final Group groupStamp = new Group(composite, SWT.TITLE);
		groupStamp.setText("Sunny Offer:");
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(groupStamp);

		Label cityLabel = new Label(groupStamp, SWT.NONE);
		cityLabel.setText("Offer");
		textOfferLink = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(textOfferLink);

		Label priceLabel = new Label(groupStamp, SWT.NONE);
		priceLabel.setText("Preis:");
		priceText = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);

		Label carDescriptionLabel = new Label(groupStamp, SWT.NONE);
		carDescriptionLabel.setText("Fahrzeug:");
		carDescription = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.FILL).hint(300, 16).grab(false, false).applyTo(carDescription);

		Label servcatLable = new Label(groupStamp, SWT.NONE);
		servcatLable.setText("Servcat:");
		serviceCatalog = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.FILL).hint(400, 16).grab(true, false).applyTo(serviceCatalog);

		Label pickUpLabel = new Label(groupStamp, SWT.NONE);
		pickUpLabel.setText("pickUp:");
		pickUpStation = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).hint(300, 16).grab(true, false).applyTo(pickUpStation);

		Label dropOffLabel = new Label(groupStamp, SWT.NONE);
		dropOffLabel.setText("DropOff:");
		dropOffStation = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(dropOffStation);


		return groupStamp;
	}
	

	private Composite addExtraGroup(Composite composite) {
		final Group group = new Group(composite, SWT.TITLE);
		group.setText("Extras:");
		group.setLayout(new GridLayout(1, true));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		extrasListTable = new SunnyExtraListTable(getSite(), group, null);

		//createTableExtras(group);
		
		return group;
	}

	private Composite addInclusivesGroup(Composite composite) {
		final Group group = new Group(composite, SWT.TITLE);
		group.setText("Inclusives:");
		group.setLayout(new GridLayout(1, true));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		inclusivesListTable = new SunnyInclusivesListTable(getSite(), group, null);


		//createTableInclusives(group);
		
		return group;
	}
	

	private Composite addBookingButton(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(composite);
		
		final Button buttonBooking = new Button(composite, SWT.PUSH	| SWT.LEFT);
		buttonBooking.setText("Booking");

		buttonBooking.addSelectionListener(new AddBookingListener() );
		
		return composite;
	}
	
	

	protected class AddBookingListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			List<Extra> extras = extrasListTable.getSelectedExtras();
			SunnyBookingView.updateView(selectedOffer, extras);

		}

	}


	protected class AddRecalculateListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			recalculateResponse.setText("running....");

			Long pickupStationId = null;
			Long dropofStationId = null;
			
			String pu = pickupStationControl.getPuTextControls().getValue();
			String[] pparts = pu.split(" ");
			if ( pparts.length > 0 && StringUtils.isNotEmpty(pparts[0]))
				pickupStationId = Long.valueOf(pparts[0]);

			String drops = dropoffStationControl.getDoTextControls().getValue();
			pparts = drops.split(" ");
			if ( pparts.length > 0 && StringUtils.isNotEmpty(pparts[0]))
				dropofStationId = Long.valueOf(pparts[0]);
			
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			VehicleHttpService service = serviceFactory.getVehicleJoiService();
			
			VehicleRequest request = CreateVehicleRequestUtils.createVehicleRequest();
			
			TravelInformation travel = request.getTravel();
			
			travel.getPickUpLocation().setCityId(null);
			travel.getPickUpLocation().setAirport(null);
			travel.getPickUpLocation().setStationId(pickupStationId);

			travel.getDropOffLocation().setCityId(null);
			travel.getDropOffLocation().setAirport(null);
			travel.getDropOffLocation().setStationId(dropofStationId);
			
			String acriss = selectedOffer.getVehicle().getACRISS();
			
			VehicleResponse response = service.recalculate(selectedOffer, request);

			if ( response != null) {
				recalculateResponse.setText(response.getSessionId());
				
				SunnyOfferDo offer = SunnyModelProvider.INSTANCE.getOfferDO(response,acriss);
				
				if ( offer != null) {
					logger.info("recalculate offer success. offerID = "+offer.getId().toString());
					OfferInformation offerInformation = service.selectOffer(offer.getLink());
					if (offerInformation != null)
						offer.addOfferInformation(offerInformation);
					
					showOffer(offer);
				}
				else
					logger.info("recalculate offer get error..");
				
			}
			else
				recalculateResponse.setText("Error ...");

		}

	}
	
	
	
	
	protected Composite createRequestButtons(final Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(4).equalWidth(false).applyTo(composite);
		
		pickupStationControl = new GetPickupStationControl();
		pickupStationControl.create(composite);
		
		dropoffStationControl = new GetDropoffStationControl();
		dropoffStationControl.create(composite);
		
		final Button buttonRecalculate = new Button(composite, SWT.PUSH | SWT.LEFT);
		buttonRecalculate.setText("POST Recalculate");
		
		buttonRecalculate.addSelectionListener(new AddRecalculateListener());
		
		recalculateResponse = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(recalculateResponse);
		
		
		return composite;

		
	}



	public void showOffer(SunnyOfferDo offer) {
		
		textOfferLink.setText(offer.getBookLink().toString());

		priceText.setText(offer.getPrice().toString());
		
		carDescription.setText(offer.getGroup()+ " : " + offer.getVehicle().getACRISS()
				+ " Model : " + offer.getVehicle().getVehicleModel());
		
		String station = "station :"+ offer.getPickUpStationId() + ""+offer.getPickupStation().getIdentifier()+ " supplier : "+offer.getSupplierId() ;
		Address address = offer.getPickupStation().getAddres();
		if ( address != null)
			station = station + " adress : "+address.getCity()+ " "+address.getStreet();	
		pickUpStation.setText(station);

		if ( offer.getDropOffStation() != null)
			dropOffStation.setText(String.valueOf(offer.getDropOffStation().getIdentifier()));
		
		serviceCatalog.setText(offer.getServiceCatalogCode() + " : "+offer.getServiceCatalogId()+ " "+offer.getServiceCatalogName()+ " . Prio ="+offer.getServiceCatalogPrio());
		
		changeModelInclusives(offer);

		changeModelExtras(offer);

		this.selectedOffer = offer;
		
		SunnyModelProvider.INSTANCE.updateOffer(offer);

		// vehicleResult.getSupplierId();
		// vehicleResult.getPickUpStationId();
		// vehicleResult.getSupplierId();
		// vehicleResult.getServiceCatalogCode();

	}
	
	private void changeModelExtras(SunnyOfferDo offer) {
		SunnyModelProvider.INSTANCE.updateExtras(offer);
		extrasListTable.update();
		
	}

	private void changeModelInclusives(SunnyOfferDo offer) {
		SunnyModelProvider.INSTANCE.updateInclusives(offer);
		inclusivesListTable.update(SunnyModelProvider.INSTANCE.getInclusives());
		
		
	}

}
