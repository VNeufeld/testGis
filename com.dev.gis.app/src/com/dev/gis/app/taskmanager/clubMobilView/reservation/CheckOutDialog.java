package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
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
import com.bpcs.mdcars.json.protocol.MissingPartsListResponse;
import com.bpcs.mdcars.json.protocol.MissingPartsRequest;
import com.bpcs.mdcars.json.protocol.ReservationResponse;
import com.bpcs.mdcars.model.CarRentalInfo;
import com.bpcs.mdcars.model.DayAndHour;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilCustomerControl;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilReservationExtrasControl;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilUtils;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;

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

			missingParts = new OutputTextControls(c2, "MissingParts",500, 1);
			new ButtonControl(c2, "show missingParts", 0,  new ShowMissingPartsInfoListener(getShell()));
			
			new ClubMobilReservationExtrasControl(composite);
			
			if ( ClubMobilModelProvider.INSTANCE.selectedReservation  != null) {
				logger.info("rentalId : " + ClubMobilModelProvider.INSTANCE.selectedReservation.getRentalId());
				
				reservationNo.setSelectedValue(ClubMobilModelProvider.INSTANCE.selectedReservation.getReservationNo());
				
				String carInfoText = "booked car id = ";
				carInfoText = carInfoText + ClubMobilModelProvider.INSTANCE.selectedReservation.getCarId();
				if ( ClubMobilModelProvider.INSTANCE.selectedReservation.getCarRentalInfo() != null)
					carInfoText = carInfoText + ClubMobilModelProvider.INSTANCE.selectedReservation.getCarRentalInfo().getCarChargeId();
				if (ClubMobilModelProvider.INSTANCE.selectedReservation.getReservationCar() != null )
					carInfoText = carInfoText + ClubMobilModelProvider.INSTANCE.selectedReservation.getReservationCar().getProducer();
				
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
		
		
		
		createButton(parent, IDialogConstants.OK_ID, "Check Out",
				false);

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
			mpd.open();

		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			
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
