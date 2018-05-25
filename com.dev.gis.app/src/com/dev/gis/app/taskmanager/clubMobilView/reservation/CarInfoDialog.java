package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.ClubMobilModelProvider;

public class CarInfoDialog extends AbstractReservationDialog {
	
	private ObjectTextControl reservationNo;

	private ObjectTextControl carInfo;
	
	private OutputTextControls carId;

	private OutputTextControls licensePlateCtl;
	

	private final Shell shell;
	
	public CarInfoDialog(Shell parentShell) {
		super(parentShell);
		this.shell = parentShell;
	}

	protected void fillDialogArea(Composite composite) {
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).hint(900, 500).applyTo(composite);
		
		reservationNo = new ObjectTextControl(composite, 300, true, "ReservationNo");

		Composite ccc = createComposite(composite, 3, -1, true);
		
		carId = new OutputTextControls(ccc, "carId", 300);
		
		carInfo = new ObjectTextControl(ccc, 300, true, "Car Info");
		
		licensePlateCtl = new OutputTextControls(ccc, "licensePlate", 300,1);
		
		if ( ClubMobilModelProvider.INSTANCE.selectedReservation  != null) {
			reservationNo.setSelectedValue(ClubMobilModelProvider.INSTANCE.selectedReservation.getReservationNo());
			
			carId.setValue(""+ClubMobilModelProvider.INSTANCE.selectedReservation.getCarId());
			
			carInfo.setSelectedValue(ClubMobilModelProvider.INSTANCE.selectedReservation.getReservationCar().getAcrissCode());
			
			String licensePlate = ClubMobilModelProvider.INSTANCE.selectedReservation.getReservationCar().getLicensePlate();
			
			licensePlateCtl.setValue(licensePlate);
			
		}

	}

	protected void okPressed() {
		setReturnCode(OK);
		close();
	}
	
}
