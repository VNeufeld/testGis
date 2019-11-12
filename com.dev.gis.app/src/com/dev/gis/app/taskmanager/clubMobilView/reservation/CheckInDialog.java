package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import java.math.BigDecimal;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.joda.time.DateTime;

import com.bpcs.mdcars.json.protocol.CheckInRequest;
import com.bpcs.mdcars.json.protocol.ReservationResponse;
import com.bpcs.mdcars.model.CarRentalInfo;
import com.bpcs.mdcars.model.DayAndHour;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilCustomerControl;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilEquipmentsControl;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilUtils;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.joi.protocol.Extra;

public class CheckInDialog extends AbstractReservationDialog {
	
	private ObjectTextControl reservationNo;

	private ObjectTextControl carInfoText;

	private ObjectTextControl carKmInfo;

	private ObjectTextControl carFuelInfo;
	
	private final Shell shell;
	
	private ClubMobilReservationListControl clubMobilResControl;
	
	private ClubMobilEquipmentsControl clubMobilEquipmentsControl;

	
	public CheckInDialog(Shell parentShell, ClubMobilReservationListControl clubMobilResControl) {
		super(parentShell);
		this.shell = parentShell;
		this.clubMobilResControl =  clubMobilResControl;
		
	}

	protected void fillDialogArea(Composite composite) {
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).hint(900, 500).applyTo(composite);
		
		logger.info("fillDialogArea");

		
		reservationNo = new ObjectTextControl(composite, 300, true, "ReservationNo");

		Composite ccc = createComposite(composite, 3, -1, true);
		new ClubMobilCustomerControl(ccc);

		carInfoText = new ObjectTextControl(ccc, 300, true, "Car Info");

		carKmInfo = new ObjectTextControl(ccc, 300, true, "Car KMInfo");

		carFuelInfo = new ObjectTextControl(ccc, 300, true, "Car FuelInfo");
		
		clubMobilEquipmentsControl = new ClubMobilEquipmentsControl(composite);
		
		try {
		
			if ( ClubMobilModelProvider.INSTANCE.selectedReservation  != null) {
				reservationNo.setSelectedValue(ClubMobilModelProvider.INSTANCE.selectedReservation.getReservationNo());
				
				String carInfo = "booked car id = ";
				carInfo = carInfo + ClubMobilModelProvider.INSTANCE.selectedReservation.getCarId();
						//" Type :" + ClubMobilModelProvider.INSTANCE.selectedReservation.getReservationCar().getAcrissCode();
				
				carInfoText.setSelectedValue(carInfo);
				
				if ( ClubMobilModelProvider.INSTANCE.selectedReservation.getCoRentalInfo() != null ) {
			
					if ( ClubMobilModelProvider.INSTANCE.selectedReservation.getCoRentalInfo().getCurrentMileage() != null)
						carKmInfo.setSelectedValue(""+ClubMobilModelProvider.INSTANCE.selectedReservation.getCoRentalInfo().getCurrentMileage().toString());
		
					if ( ClubMobilModelProvider.INSTANCE.selectedReservation.getCoRentalInfo().getTankFillingProc() != null)
						carFuelInfo.setSelectedValue(""+ClubMobilModelProvider.INSTANCE.selectedReservation.getCoRentalInfo().getTankFillingProc().toString());
				}
				
			}
		}
		catch(Exception err) {
			logger.error(err.getMessage(),err);
		}
		
		logger.info("fillDialogArea end");


		//PaymentControlControl.createControl(composite);
		
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		
		createButton(parent, IDialogConstants.OK_ID, "Check In",
				false);

		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, true);

	}

	protected void okPressed() {
		
		ClubMobilModelProvider.INSTANCE.checkInCarKm = carKmInfo.getSelectedValue();

		ClubMobilModelProvider.INSTANCE.checkInCarFuel = carFuelInfo.getSelectedValue();
		if ( executeUpdateCheckinReservationService())
			setReturnCode(OK);
		else
			setReturnCode(CANCEL);
		close();
	}
	
	protected boolean executeUpdateCheckinReservationService() {
		try {
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
			
			
			List<Extra> equipments = clubMobilEquipmentsControl.getSelectedExtras();
			service.putEquipments(equipments);
			
			
			CheckInRequest checkInDetails = new CheckInRequest();
			
			CarRentalInfo carRentalInfo =  new CarRentalInfo();
			
			carRentalInfo.setCurrentMileage(BigDecimal.valueOf(Double.valueOf(carKmInfo.getSelectedValue())));
			carRentalInfo.setTankFillingProc(BigDecimal.valueOf(Double.valueOf(carFuelInfo.getSelectedValue())));

			checkInDetails.setCarRentalInfo(carRentalInfo);
			DateTime dt = new DateTime();
			checkInDetails.setCheckInDate(new DayAndHour(dt));
			
			ReservationResponse reservationResponse = service.updateReservation(checkInDetails);
			
			ClubMobilModelProvider.INSTANCE.selectedReservation = reservationResponse.getReservationDetails();
			MessageDialog.openInformation(null,"Info"," Update Reservation successfull");
			return true;
			
		}
		catch(Exception err) {
			ClubMobilUtils.showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
			
		}
		return false;

	}

	
}
