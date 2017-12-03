package com.dev.gis.app.taskmanager.clubMobilView;

import java.util.Calendar;
import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.dev.gis.app.taskmanager.rentcars.RentCarOfferDetailBasicView;
import com.dev.gis.app.taskmanager.testAppView.AdacDropoffStationControl;
import com.dev.gis.app.taskmanager.testAppView.AdacExtraListTable;
import com.dev.gis.app.taskmanager.testAppView.AdacInclusivesListTable;
import com.dev.gis.app.taskmanager.testAppView.AdacPickupStationControl;
import com.dev.gis.app.taskmanager.testAppView.AdacVehicleInfoControl;
import com.dev.gis.app.taskmanager.testAppView.BookingView;
import com.dev.gis.app.taskmanager.testAppView.PromotionCodeTextControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.AdacVehicleHttpService;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.ModelProvider;
import com.dev.gis.connector.api.OfferDo;
import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.api.SunnyOfferDo;
import com.dev.gis.connector.ext.BusinessException;
import com.dev.gis.connector.joi.protocol.BookingResponse;
import com.dev.gis.connector.joi.protocol.DayAndHour;
import com.dev.gis.connector.joi.protocol.Error;
import com.dev.gis.connector.joi.protocol.Extra;
import com.dev.gis.connector.joi.protocol.ExtraResponse;
import com.dev.gis.connector.joi.protocol.Offer;
import com.dev.gis.connector.joi.protocol.TravelInformation;
import com.dev.gis.connector.joi.protocol.VehicleResponse;
import com.dev.gis.connector.joi.protocol.VehicleResult;
import com.dev.gis.connector.sunny.OfferInformation;

public class ClubMobilOfferDetailView extends RentCarOfferDetailBasicView {
	public static final String ID = "com.dev.gis.app.view.OfferDetailView";

	protected AdacVehicleInfoControl vehicleInfo;

	//protected AdacLocationInfoControl locationInfoControl;
	
	protected Offer selectedOffer = null;

	protected AdacExtraListTable extraListTable;
	
	protected AdacInclusivesListTable inclusivesListTable;
	
	protected OutputTextControls priceText = null;

	protected OutputTextControls priceStd = null;

	protected OutputTextControls priceDaily = null;

	protected OutputTextControls serviceCatalog = null;

	protected OutputTextControls supplier = null;

	protected OutputTextControls station = null;

	protected OutputTextControls location = null;
	
	protected OutputTextControls recalculateResponse = null;

	

	@Override
	protected void createPrice(Group groupStamp) {
		
		final Composite priceComposite = createComposite(groupStamp, 6, -1,  true);
		
		priceText = new OutputTextControls(priceComposite, "Preis:", 200, 1 );
		priceStd = new OutputTextControls(priceComposite, "PreisStd:", 200, 1 );
		priceDaily = new OutputTextControls(priceComposite, "PreisDaily:", 200, 1 );
	}

	@Override
	protected void createServcatSupplierOutput(Group groupStamp) {
		final Composite composite = createComposite(groupStamp, 4, -1,  true);
		supplier = new OutputTextControls(composite, "Supplier:", 500, 1 );
		serviceCatalog = new OutputTextControls(composite, "ServiceCatalog:", 500, 1 );
		
		station = new OutputTextControls(composite, "Stations :", 1500, 3 );

		PromotionCodeTextControl.createPromotionCodeControl(composite);
		
		final Composite station_composite = createComposite(groupStamp, 10, -1,  true);
		AdacPickupStationControl pickupStationControl = new AdacPickupStationControl();
		pickupStationControl.create(station_composite);

		AdacDropoffStationControl dropoffStationControl = new AdacDropoffStationControl();
		dropoffStationControl.create(station_composite);

		
		//location = new OutputTextControls(composite, "Locations :", 1500, 3 );		
		
	}
	
	protected Composite createRequestButtons(final Composite parent) {

		final Composite composite = createComposite(parent, 4, -1,  true);

		final Button buttonRecalculate = new Button(composite, SWT.PUSH	| SWT.LEFT);
		buttonRecalculate.setText("Recalculate");

		buttonRecalculate.addSelectionListener(getRecalculateSelectionListener());

		recalculateResponse = new OutputTextControls(composite, "", -1, 2 );


		return composite;

	}


	
	public void showOffer(OfferDo offerDo) {
		
		VehicleResult vehicleResult = offerDo.getModel();
		
		AdacModelProvider.INSTANCE.setSelectedOffer(offerDo);
		
		if ( vehicleResult == null )
			return;
		
		Offer offer = offerDo.getOffer();  //getOffer(vehicleResult);
		
		if ( offer != null) {

			this.selectedOffer = offer;

			textOfferLink.setValue(offer.getBookLink().toString());
		
			priceText.setValue(offer.getPrice().toString());

			priceStd.setValue(offer.getStdPrice().toString());

			if ( offer.getDailyPrice() != null)
				priceDaily.setValue(offer.getDailyPrice().toString());
			
			inclusivesListTable.update(selectedOffer.getInclusives());
			
			if ( offerDo.getSupplier() != null)
				supplier.setValue(offerDo.getSupplier().getName() + " id : "+offerDo.getSupplier().getId() + " sgr : " + offerDo.getSupplier().getSupplierGroupId());
			
			serviceCatalog.setValue(offerDo.getServiceCatalogCode() + " id : "+offerDo.getServiceCatalogId());
			
			if ( offerDo.getPickupStation() != null) {
				String text = offerDo.getPickupStation().getId() + " Name : " + offerDo.getPickupStation().getStationName();
				if ( offerDo.getDropoffStation() != null) {
					text += ". DropOff : " + offerDo.getDropoffStation().getId() + " Name : " + offerDo.getDropoffStation().getStationName();
				}
				station.setValue(text);
			}
			
			
		}
		
		vehicleInfo.update(vehicleResult.getVehicle());

//		this.travelInformation = offerDo.getTravelInformation();
//
//		this.travelInformation.setPickUpLocation(vehicleResult
//				.getPickUpLocation());
//		this.travelInformation.setDropOffLocation(vehicleResult
//				.getDropOffLocation());
		
		//locationInfoControl.update(offerDo.getTravelInformation());
		
		extraListTable.refresh(new ExtraResponse());

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
		//locationInfoControl = new AdacLocationInfoControl(groupStamp);
		
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

	
	protected class AddRecalculateListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			long dropOffStationId = AdacModelProvider.INSTANCE.selectedDropoffStationId; 
			long pickupStationId = AdacModelProvider.INSTANCE.selectedPickupStationId;
			
			String promotionCode = AdacModelProvider.INSTANCE.promotionCode;
			
			if ( dropOffStationId <= 0 && pickupStationId > 0) {
				dropOffStationId = pickupStationId;
			}
			
			if ( pickupStationId > 0 && dropOffStationId > 0) {

				OfferDo offerDo = AdacModelProvider.INSTANCE.getSelectedOffer();
				
				long selectedServCat = offerDo.getServiceCatalogId(); 

			
				TravelInformation travelInformation = new TravelInformation(pickupStationId,dropOffStationId);
				
				Calendar pickupDate = ModelProvider.INSTANCE.pickupDateTime;
				Calendar dropoffDate = ModelProvider.INSTANCE.dropoffDateTime;
				
				travelInformation.setDates(pickupDate,dropoffDate);
	
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				AdacVehicleHttpService service = serviceFactory.getAdacVehicleJoiService();
	
				try {
					VehicleResult response = service.recalculate(selectedOffer,travelInformation,promotionCode);
					if ( response != null ) {
						
						Offer recalculatedOffer = null;
						for ( Offer ofr : response.getOfferList()) {
							if ( ofr.getServiceCatalogId() == selectedServCat) {
								recalculatedOffer = ofr;
							}
								
						}
						if ( recalculatedOffer != null) {
	
							recalculateResponse.setValue(recalculatedOffer.getLink().toString());
						
							logger.info("recalculate offer success. offerID = "+recalculatedOffer.getId().toString() + " price = " + recalculatedOffer.getPrice());
							
							OfferDo of = new OfferDo(recalculatedOffer,response);
							
							showOffer(of);
						}
						else 
							showErrors(response);
						
					}
				}
				catch(BusinessException ec){
					recalculateResponse.setValue(ec.getMessage());
				}
			}
			else {
				recalculateResponse.setValue("please, select pickupStationId and dropOffStationId!");
			}

		}

	}
	
	private void showErrors(VehicleResult response) {
		String message = "";
		if ( response.getErrors() != null) {
			for ( Error error : response.getErrors()) {
				message = message + error.getErrorNumber()+ "  "+ error.getErrorText() + " " + error.getErrorType() + " ;";
			}
			
		}
		if ( message.length() > 0 ) {
			recalculateResponse.setValue(message);
		}
		
	}
	

	protected class AddBookingListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			List<Extra> extras = extraListTable.getSelectedExtras();
			BookingView.updateView(extras);

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
