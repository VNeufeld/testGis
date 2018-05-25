package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import com.bpcs.mdcars.json.protocol.CheckOutRequest;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilUtils;
import com.dev.gis.app.taskmanager.clubMobilView.CustomerDialog;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.joi.protocol.VehicleDescription;

public class AbstractReservationListener implements SelectionListener {

	protected Logger logger = Logger.getLogger(this.getClass());

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
			
		}
		catch(Exception err) {
			ClubMobilUtils.showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
			
		}

	}

	protected void callService(ClubMobilHttpService service) {
		
	}

	

}
