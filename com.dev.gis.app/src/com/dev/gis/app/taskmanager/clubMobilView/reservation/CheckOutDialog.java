package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.taskmanager.clubMobilView.CustomerNoTextControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.ClubMobilModelProvider;

public class CheckOutDialog extends AbstractReservationDialog {
	
	private CustomerNoTextControl customerNoTextControl;
	
	private ObjectTextControl reservationNo;

	private ObjectTextControl carInfo;

	private ObjectTextControl extras;
	
	public CheckOutDialog(Shell parentShell, ClubMobilReservationListControl clubMobilResControl) {
		super(parentShell);
	}

	protected void fillDialogArea(Composite composite) {
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).hint(500, 500).applyTo(composite);
		
		reservationNo = new ObjectTextControl(composite, 300, true, "ReservationNo");

		Composite ccc = createComposite(composite, 3, -1, true);
		customerNoTextControl = new CustomerNoTextControl(ccc, 300, true);
		new ButtonControl(ccc, "edit Customer", 0,  null);

		Composite cc = createComposite(composite, 3, -1, true);
		carInfo = new ObjectTextControl(cc, 300, false, "Car Info");
		new ButtonControl(cc, "select Car   ", 0,  null);

		Composite cce = createComposite(composite, 3, -1, true);
		extras = new ObjectTextControl(cce, 300, false, "Extras");
		new ButtonControl(cce, "select Extras", 0,  null);
		

		if ( ClubMobilModelProvider.INSTANCE.selectedReservationInfo  != null)
			reservationNo.setSelectedValue(ClubMobilModelProvider.INSTANCE.selectedReservationInfo.getReservationNo());

		new ButtonControl(composite, "select Payment", 0,  null);
	}

}
