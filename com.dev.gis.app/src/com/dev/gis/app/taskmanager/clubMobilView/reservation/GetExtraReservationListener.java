package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.connector.api.ClubMobilHttpService;


public class GetExtraReservationListener extends AbstractReservationListener{
	private final Shell shell;
	private ClubMobilReservationListControl clubMobilResControl;

	public GetExtraReservationListener(Shell shell, ClubMobilReservationListControl clubMobilResControl) {
		this.shell = shell;
		this.clubMobilResControl = clubMobilResControl;
	}
	
	protected void callService(ClubMobilHttpService service) {
		service.checkOutReservation();
	}
	
	protected void executeEvent(SelectionEvent e) {
		CheckOutDialog mpd = new CheckOutDialog(shell, clubMobilResControl);
		if (mpd.open() == Dialog.OK) {
			executeRestService();
		}

	}


}
