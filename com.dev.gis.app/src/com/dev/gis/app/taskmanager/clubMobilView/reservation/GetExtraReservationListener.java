package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;

// not used more
public class GetExtraReservationListener extends AbstractReservationListener{
	private final Shell shell;

	public GetExtraReservationListener(Shell shell) {
		this.shell = shell;
	}
	
	protected void callService(ClubMobilHttpService service) {
		service.checkOutReservation(null);
	}
	
	protected void executeEvent(SelectionEvent e) {
		CheckOutDialog mpd = new CheckOutDialog(shell);
		if (mpd.open() == Dialog.OK) {
			executeRestService();
		}

	}


}
