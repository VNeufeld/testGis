package com.dev.gis.app.taskmanager.clubMobilView;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.bpcs.mdcars.json.protocol.ReservationResponse;
import com.dev.gis.app.view.elements.BasicControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.joi.protocol.Extra;
import com.dev.gis.connector.joi.protocol.ExtraResponse;

public class ClubMobilReservationExtrasControl extends BasicControl {
	
	private static Logger logger = Logger.getLogger(ClubMobilReservationExtrasControl.class);
	
	private final Composite parent;
	
	protected ClubMobilExtraListTable extraListTable;

	public ClubMobilReservationExtrasControl(Composite groupStamp) {
		
		parent = groupStamp;

		addExtraGroup(groupStamp);
		
		logger.info("create ClubMobilReservationExtrasControl ");

	}
	
	protected Composite getParent() {
		return parent;
	}

	protected Shell getShell() {
		return parent.getShell();
	}
	
	protected  void addExtraGroup(Composite composite) {
		final Group group = new Group(composite, SWT.TITLE);
		group.setText("CM Extras:");
		group.setLayout(new GridLayout(2, true));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
		.grab(true, true).applyTo(group);

		Composite ccd = createComposite(group, 3, -1, true);

		new ButtonControl(ccd, "Get Extras", 0,  new AddGetExtrasListener());
		new ButtonControl(ccd, "Get Equipments", 0,  new AddGetEquipmentsListener());
		new ButtonControl(ccd, "Get Additionals", 0,  new AddGetAdditionalsListener());
		
		extraListTable = new ClubMobilExtraListTable(null, group, null);

		Composite cc = createComposite(group, 3, -1, true);

		new ButtonControl(cc, "Select Extras", 0,  new AddSelectExtrasListener());
		new ButtonControl(cc, "Select Fehl-Equipments", 0,  new AddSelectEquipmentsListener());
		

	}
	
	protected class AddGetExtrasListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			ClubMobilHttpService service = serviceFactory
					.getClubMobilleJoiService();
			
			
			try {
				
				
				ExtraResponse response  = service.getReservationExtras();
				
				if ( response != null && response.getExtras() != null)
					logger.info("ClubMobilExtrasControl found extras : " +response.getExtras().size());
				
				
				ClubMobilUtils.showErrors(response);
				
				extraListTable.refresh(response);
			}
			catch(Exception err) {
				logger.error(err.getMessage(),err);
				ClubMobilUtils.showErrors(err.getClass().getSimpleName() + " " +err.getMessage());
			}
		}
	}

	protected class AddSelectExtrasListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			ClubMobilHttpService service = serviceFactory
					.getClubMobilleJoiService();
			try {
				
				ExtraResponse response = service.putExtras(getSelectedExtras());
				
				ClubMobilUtils.showErrors(response);
			}
			catch(Exception err) {
				logger.error(err.getMessage(),err);
				ClubMobilUtils.showErrors(err.getClass().getSimpleName() + " " +err.getMessage());
			}
		}

	}

	protected class AddSelectEquipmentsListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			ClubMobilHttpService service = serviceFactory
					.getClubMobilleJoiService();
			try {
				
				ReservationResponse response = service.putEquipments(getSelectedExtras());
				
				//ClubMobilUtils.showErrors(response);
			}
			catch(Exception err) {
				logger.error(err.getMessage(),err);
				ClubMobilUtils.showErrors(err.getClass().getSimpleName() + " " +err.getMessage());
			}
		}

	}
	
	protected class AddGetEquipmentsListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			ClubMobilHttpService service = serviceFactory
					.getClubMobilleJoiService();
			
			
			try {
				
				
				ExtraResponse response  = service.getEquipments();
				
				if ( response != null && response.getExtras() != null)
					logger.info("ClubMobilExtrasControl found equipments : " +response.getExtras().size());
				
				
				ClubMobilUtils.showErrors(response);
				
				extraListTable.refresh(response);
			}
			catch(Exception err) {
				logger.error(err.getMessage(),err);
				ClubMobilUtils.showErrors(err.getClass().getSimpleName() + " " +err.getMessage());
			}
		}
	}

	protected class AddGetAdditionalsListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			ClubMobilHttpService service = serviceFactory
					.getClubMobilleJoiService();
			
			
			try {
				
				
				ExtraResponse response  = service.getAdditionals();
				
				if ( response != null && response.getExtras() != null)
					logger.info("ClubMobilExtrasControl found additionals : " +response.getExtras().size());
				
				
				ClubMobilUtils.showErrors(response);
				
				extraListTable.refresh(response);
			}
			catch(Exception err) {
				logger.error(err.getMessage(),err);
				ClubMobilUtils.showErrors(err.getClass().getSimpleName() + " " +err.getMessage());
			}
		}
	}

	
	public void refresh(ExtraResponse response) {
		extraListTable.refresh(response);
	}
	
	public List<Extra> getSelectedExtras() {
		return extraListTable.getSelectedExtras();
	}
	
}
