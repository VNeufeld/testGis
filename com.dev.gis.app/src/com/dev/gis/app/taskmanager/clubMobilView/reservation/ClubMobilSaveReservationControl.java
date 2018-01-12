package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.view.elements.BasicControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.OutputTextControls;

public class ClubMobilSaveReservationControl extends BasicControl {
	
	private final Composite parent;
	
	private OutputTextControls result = null;

	public ClubMobilSaveReservationControl(Composite groupStamp, ClubMobilReservationListControl clubMobilResControl) {
		
		parent = groupStamp;

		Composite ccc = createComposite(groupStamp, 5, -1, false);

		new ButtonControl(ccc, "Show Reservation", 0,  getSavelListener(getShell()));

		new ButtonControl(ccc, "Change Reservation", 0,  getSavelListener(getShell()));
		
		new ButtonControl(ccc, "CheckOut", 0,  new CheckOutReservationListener(getShell(), clubMobilResControl));

		new ButtonControl(ccc, "CheckIn", 0,  new CheckInReservationListener(getShell(), clubMobilResControl));

		new ButtonControl(ccc, "Cancel", 0,  getSavelListener(getShell()));
		

	}
	
	protected Composite getParent() {
		return parent;
	}

	protected Shell getShell() {
		return parent.getShell();
	}

	protected SelectionListener getSavelListener(Shell shell) {
		return new CheckOutReservationListener(shell, null);
	}
	
	public OutputTextControls getResult() {
		return result;
	}

}
