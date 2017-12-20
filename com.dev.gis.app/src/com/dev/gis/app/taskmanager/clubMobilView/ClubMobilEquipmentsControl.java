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

import com.dev.gis.app.view.elements.BasicControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.joi.protocol.Extra;
import com.dev.gis.connector.joi.protocol.ExtraResponse;
import com.dev.gis.connector.joi.protocol.Offer;

public class ClubMobilEquipmentsControl extends BasicControl {
	
	private static Logger logger = Logger.getLogger(ClubMobilEquipmentsControl.class);
	
	private final Composite parent;
	
	protected ClubMobilExtraListTable extraListTable;

	public ClubMobilEquipmentsControl(Composite groupStamp) {
		
		parent = groupStamp;

		addExtraGroup(groupStamp);
		
		logger.info("create ClubMobilEquipmentsControl ");

	}
	
	protected Composite getParent() {
		return parent;
	}

	protected Shell getShell() {
		return parent.getShell();
	}
	
	protected  void addExtraGroup(Composite composite) {
		final Group group = new Group(composite, SWT.TITLE);
		group.setText("CM Equipments:");
		group.setLayout(new GridLayout(1, true));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
		.grab(true, true).applyTo(group);
		
		new ButtonControl(group, "Get Equipments", 0,  new AddGetEquipmentsListener());
		
		extraListTable = new ClubMobilExtraListTable(null, group, null);
		

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
	
	public void refresh(ExtraResponse response) {
		extraListTable.refresh(response);
	}
	
	public List<Extra> getSelectedExtras() {
		return extraListTable.getSelectedExtras();
	}

}
