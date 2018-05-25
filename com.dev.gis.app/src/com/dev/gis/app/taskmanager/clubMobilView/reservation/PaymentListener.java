package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;

import com.bpcs.mdcars.model.CreditCard;
import com.bpcs.mdcars.model.ReservationDetails;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilUtils;
import com.dev.gis.connector.api.ClubMobilModelProvider;


public class PaymentListener implements SelectionListener {
	
	private final Shell shell;

	public PaymentListener(Shell shell) {
		this.shell = shell;
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		
		try {
			PaymentDialog mpd = new PaymentDialog(shell);
			mpd.open();

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
