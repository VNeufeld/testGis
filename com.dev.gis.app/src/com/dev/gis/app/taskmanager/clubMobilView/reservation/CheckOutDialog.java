package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.joda.time.DateTime;

import com.bpcs.mdcars.json.protocol.CheckInRequest;
import com.bpcs.mdcars.json.protocol.CheckOutRequest;
import com.bpcs.mdcars.json.protocol.MissingPartsListResponse;
import com.bpcs.mdcars.json.protocol.MissingPartsRequest;
import com.bpcs.mdcars.json.protocol.ReservationResponse;
import com.bpcs.mdcars.model.CarRentalInfo;
import com.bpcs.mdcars.model.DayAndHour;
import com.bpcs.mdcars.model.Extra;
import com.bpcs.mdcars.model.ReservationDetails;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilCustomerControl;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilReservationExtrasControl;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilUtils;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.OfferDo;
import com.dev.gis.connector.joi.protocol.BookingResponse;

public class CheckOutDialog extends AbstractReservationDialog {
	
	private static Logger logger = Logger.getLogger(CheckOutDialog.class);
	
	private ObjectTextControl reservationNo;

	private OutputTextControls carInfo;

	private OutputTextControls missingParts;
	
	private ObjectTextControl carKmInfo;

	private ObjectTextControl carFuelInfo;

	
	private final Shell shell;
	
	public CheckOutDialog(Shell parentShell) {
		super(parentShell);
		this.shell = parentShell;
	}

	protected void fillDialogArea(Composite composite) {
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).hint(900, 500).applyTo(composite);
		
		try {
		
			logger.info("fillDialogArea");
			
			reservationNo = new ObjectTextControl(composite, 300, true, "ReservationNo");
	
			new ClubMobilCustomerControl(composite);
			
			Composite ccc = createComposite(composite, 3, -1, true);
			
			carInfo = new OutputTextControls(ccc, "Car Info",500, 1);
			new ButtonControl(ccc, "show Car", 0,  new ClubMobilCarInfoListener(getShell()));
			
			carKmInfo = new ObjectTextControl(ccc, 300, true, "Car KMInfo");
			carKmInfo.setSelectedValue("");

			carFuelInfo = new ObjectTextControl(ccc, 300, true, "Car FuelInfo");
			carFuelInfo.setSelectedValue("");


			Composite c2= createComposite(composite, 4, -1, true);

			missingParts = new OutputTextControls(c2, "MissingParts after CheckIn",500, 1);
			new ButtonControl(c2, "show missingParts", 0,  new ShowMissingPartsInfoListener(getShell()));
			
			new ClubMobilReservationExtrasControl(composite);
			
			if ( ClubMobilModelProvider.INSTANCE.selectedReservation  != null) {
				logger.info("rentalId : " + ClubMobilModelProvider.INSTANCE.selectedReservation.getRentalId());
				ReservationDetails rd = ClubMobilModelProvider.INSTANCE.selectedReservation;
				reservationNo.setSelectedValue(rd.getReservationNo());
				
				String carInfoText = "booked car id = ";
				carInfoText = carInfoText + rd.getCarId();
				if ( rd.getCoRentalInfo() != null) {
					if ( rd.getCoRentalInfo().getCurrentMileage() != null)
						carKmInfo.setSelectedValue(rd.getCoRentalInfo().getCurrentMileage().toString());
					if ( rd.getCoRentalInfo().getTankFillingProc() != null)
					carFuelInfo.setSelectedValue(rd.getCoRentalInfo().getTankFillingProc().toString());
				}
				if (ClubMobilModelProvider.INSTANCE.selectedReservation.getReservationCar() != null )
					carInfoText = carInfoText + " " + ClubMobilModelProvider.INSTANCE.selectedReservation.getReservationCar().getProducer();
				
				carInfo.setValue(carInfoText);
			}
			logger.info("fillDialogArea end");
		}
		catch(Exception ex) {
			logger.error(ex.getMessage(),ex);
		}

	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		
		org.eclipse.swt.widgets.Button detailsButton3 = createButton(parent, IDialogConstants.DETAILS_ID,
                "GetBookInfo CheckOut",
                false);
        
        detailsButton3.addSelectionListener(new SelectionAdapter() {
            @Override
    			public void widgetSelected(SelectionEvent e) {
            	logger.info("GetBookInfo CheckOut");
            	executeGetBookingInfo(2);
                }
            });

		org.eclipse.swt.widgets.Button detailsButtonCOOR = createButton(parent, IDialogConstants.DETAILS_ID,
                "CheckOut Ohne Res",
                false);
		detailsButtonCOOR.addSelectionListener(new SelectionAdapter() {
            @Override
    			public void widgetSelected(SelectionEvent e) {
            	logger.info("CheckOut Ohne Res.");
            	executeCheckOutService(5);
                }

            });

		org.eclipse.swt.widgets.Button detailsButtonCOOC = createButton(parent, IDialogConstants.DETAILS_ID,
                "CheckOut Ohne Check",
                false);
		detailsButtonCOOC.addSelectionListener(new SelectionAdapter() {
            @Override
    			public void widgetSelected(SelectionEvent e) {
            	logger.info("CheckOut Ohne Check.");
            	executeCheckOutService(6);
                }

            });

		org.eclipse.swt.widgets.Button detailsButtonCO = createButton(parent, IDialogConstants.DETAILS_ID,
                "CheckOut End",
                false);
		detailsButtonCO.addSelectionListener(new SelectionAdapter() {
            @Override
    			public void widgetSelected(SelectionEvent e) {
            	logger.info("CheckOut End.");
            	executeCheckOutService(2);
                }

            });

		org.eclipse.swt.widgets.Button detailsButton = createButton(parent, IDialogConstants.DETAILS_ID,
                "CheckIn OC",
                false);
        
        detailsButton.addSelectionListener(new SelectionAdapter() {
            @Override
    			public void widgetSelected(SelectionEvent e) {
            	logger.info("CheckIn OC");
            	executeCheckInService(4);
                }
            });

		org.eclipse.swt.widgets.Button detailsButton2 = createButton(parent, IDialogConstants.DETAILS_ID,
                "CheckIn",
                false);
        
		detailsButton2.addSelectionListener(new SelectionAdapter() {
            @Override
    			public void widgetSelected(SelectionEvent e) {
            	logger.info("CheckIn");
            	executeCheckInService(3);
                }
            });
        

		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, true);

	}

	protected void executeCheckInService(int rentalStatus) {
		try {
			logger.info("call checkInRequest");
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
			
			CarRentalInfo carRentalInfo =  new CarRentalInfo();
			
			carRentalInfo.setCurrentMileage(BigDecimal.valueOf(Double.valueOf(carKmInfo.getSelectedValue())));
			carRentalInfo.setTankFillingProc(BigDecimal.valueOf(Double.valueOf(carFuelInfo.getSelectedValue())));
			
			CheckInRequest checkInRequest = new CheckInRequest();
			checkInRequest.setCustomer(ClubMobilModelProvider.INSTANCE.customer);
			checkInRequest.setDriver(ClubMobilModelProvider.INSTANCE.driver);
			checkInRequest.setBookingId(ClubMobilModelProvider.INSTANCE.selectedReservation.getRentalId());

			List<Extra> eqs = createEquipments();
			if ( eqs != null)
				checkInRequest.getBookingEquipments().addAll(eqs);

			List<Extra> adds = createAdditionals();
			if ( adds != null)
				checkInRequest.getBookingAdditionals().addAll(adds);
			
			checkInRequest.setCarRentalInfo(carRentalInfo);
			DateTime dt = new DateTime();
			checkInRequest.setCheckInDate(new DayAndHour(dt));
			
			checkInRequest.setRentalStatus(rentalStatus);
			
			ReservationResponse reservationResponse = service.checkInReservation(checkInRequest);
			
			if (reservationResponse.getErrors() != null && !reservationResponse.getErrors().isEmpty()) {
				MessageDialog.openInformation(null,"Error"," CheckIn Error : " + reservationResponse.getErrors().get(0).getErrorText());
			}
			else {
				ClubMobilModelProvider.INSTANCE.selectedReservation = reservationResponse.getReservationDetails();
				MessageDialog.openInformation(null,"Info"," CheckIn successfull");
			}
			
		}
		catch(Exception err) {
			ClubMobilUtils.showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
			
		}

	}

	private List<Extra> createAdditionals() {
		return convert(ClubMobilModelProvider.INSTANCE.getSelectedAdditionals());
	}

	private List<Extra> createEquipments() {
		return convert(ClubMobilModelProvider.INSTANCE.getSelectedEquipments());
	}

	private static List<com.bpcs.mdcars.model.Extra> convert(List<com.dev.gis.connector.joi.protocol.Extra> list) {
		if ( list != null && !list.isEmpty()) {
			List<com.bpcs.mdcars.model.Extra> extras = new ArrayList<com.bpcs.mdcars.model.Extra>();
			for ( com.dev.gis.connector.joi.protocol.Extra extra : list) {
				com.bpcs.mdcars.model.Extra ex = new com.bpcs.mdcars.model.Extra();
				ex.setId(extra.getId());
				extras.add(ex);
			}
			return extras;
		}
		return null;
	}

	protected void executeCheckOutService(int rentalStatus) {
		try {
			logger.info("call checkOutRequest");
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
			
			CheckOutRequest checkOutRequest = null;
			checkOutRequest = ClubMobilUtils.createCheckOutRequest();

			if ( rentalStatus == 2)
				checkOutRequest.setRentalStatus(2);
			else
				checkOutRequest.setRentalStatus(5);
				
			
			CarRentalInfo carRentalInfo = new CarRentalInfo();
			carRentalInfo.setCurrentMileage(BigDecimal.valueOf(Double.valueOf(carKmInfo.getSelectedValue())));
			carRentalInfo.setTankFillingProc(BigDecimal.valueOf(100));
			checkOutRequest.setCarRentalInfo(carRentalInfo);
			
			logger.info("call checkOutRequest " + checkOutRequest);
			ReservationResponse resp = null;
			if ( checkOutRequest.getRentalStatus() == 5)
				resp = service.checkOutReservation(checkOutRequest);
			else
				resp = service.checkOutReservationFin(checkOutRequest);
			
			if ( resp.getErrors() != null && !resp.getErrors().isEmpty()) {
				String errorText = ""+resp.getErrors().get(0).getErrorNumber();
				errorText = errorText + " : "+resp.getErrors().get(0).getErrorText();
				
				MessageDialog.openError(null,"ReservationError"," Error CheckOut " + errorText);
			}
			else
				MessageDialog.openInformation(null,"Info"," CheckOut successfull RentalNo = " + resp.getReservationDetails().getRentalNo());
			
		}
		catch(Exception err) {
			ClubMobilUtils.showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
			
		}

	}

	
	protected void executeGetBookingInfo(int rentalStatus) {
		try {
			logger.info("call checkInRequest");
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
			
			BookingResponse reservationResponse = service.getReservationInfo();
			
			if (reservationResponse.getErrors() != null && !reservationResponse.getErrors().isEmpty()) {
				MessageDialog.openInformation(null,"Error"," executeGetBookingInfo Error : " + reservationResponse.getErrors().get(0).getErrorText());
			}
			else {
				MessageDialog.openInformation(null,"Info"," executeGetBookingInfo successfull");
			}
			
		}
		catch(Exception err) {
			ClubMobilUtils.showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
			
		}

	}


	protected void okPressed() {
		setReturnCode(OK);
		close();
	}

	private class ClubMobilCarInfoListener implements SelectionListener {
		
		private final Shell shell;

		public ClubMobilCarInfoListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			CarInfoDialog mpd = new CarInfoDialog(shell);
			
			if (mpd.open() == Dialog.OK) {
				updateInfo();
			}
			

		}


		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			
		}

	}

	private void updateInfo() {
		OfferDo offer = ClubMobilModelProvider.INSTANCE.getSelectedOffer();
		if ( offer != null) {
			carInfo.setValue(offer.getModel().getVehicle().getCarId());
		}
		
		
	}

	private class ShowMissingPartsInfoListener implements SelectionListener {
		
		private final Shell shell;

		public ShowMissingPartsInfoListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {

			try {
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
				
				Integer carId = null;
				if ( ClubMobilModelProvider.INSTANCE.selectedReservation != null) {
					carId = ClubMobilModelProvider.INSTANCE.selectedReservation.getCarId();
				}

				
				MissingPartsRequest missingPartsRequest = new MissingPartsRequest();
				missingPartsRequest.setCarId(carId);
				
				MissingPartsListResponse missingPartsListResponse = service.getMissingParts(missingPartsRequest);
				
				if ( missingPartsListResponse.getMissingPartsDescriptions().isEmpty())
					missingParts.setValue("No missing parts found");
				else
					missingParts.setValue("found " +missingPartsListResponse.getMissingPartsDescriptions().size() + " missing parts.");
				
			}
			catch(Exception err) {
				ClubMobilUtils.showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
				
			}

		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			
		}

	}
	
}
