package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilEquipmentsControl;
import com.dev.gis.app.taskmanager.clubMobilView.CustomerNoTextControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.ClubMobilModelProvider;

public class CheckInDialog extends AbstractReservationDialog {
	
	private CustomerNoTextControl customerNoTextControl;
	
	private ObjectTextControl reservationNo;

	private ObjectTextControl carInfo;
	
	private final Shell shell;
	
	private ClubMobilReservationListControl clubMobilResControl;
	
	public CheckInDialog(Shell parentShell, ClubMobilReservationListControl clubMobilResControl) {
		super(parentShell);
		this.shell = parentShell;
		this.clubMobilResControl =  clubMobilResControl;
	}

	protected void fillDialogArea(Composite composite) {
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).hint(500, 500).applyTo(composite);
		
		reservationNo = new ObjectTextControl(composite, 300, true, "ReservationNo");

		Composite ccc = createComposite(composite, 3, -1, true);
		customerNoTextControl = new CustomerNoTextControl(ccc, 300, false);
		new ButtonControl(ccc, "edit Customer", 0,  null);

		carInfo = new ObjectTextControl(ccc, 300, false, "Car Info");
		new ButtonControl(ccc, "select Car   ", 0,  null);

		new ClubMobilEquipmentsControl(composite);
		
		if ( ClubMobilModelProvider.INSTANCE.selectedReservationInfo  != null)
			reservationNo.setSelectedValue(ClubMobilModelProvider.INSTANCE.selectedReservationInfo.getReservationNo());

		new ButtonControl(composite, "select Payment", 0,  new PaymentListener(shell));
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		
		createButton(parent, IDialogConstants.OK_ID, "Check In",
				false);

		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, true);

	}

	protected void okPressed() {
		setReturnCode(OK);
		close();
	}
	
}
