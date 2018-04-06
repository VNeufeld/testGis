package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilUtils;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.JoiHttpServiceFactory;

public class AbstractReservationListener implements SelectionListener {

	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		executeEvent(e);
		
	}

	protected void executeEvent(SelectionEvent e) {
		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	protected void executeRestService() {
		try {
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
			callService(service);
			MessageDialog.openInformation(null,"Info"," CheckOut successfull");
			
		}
		catch(Exception err) {
			ClubMobilUtils.showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
			
		}

	}

	protected void callService(ClubMobilHttpService service) {
		
	}

}
