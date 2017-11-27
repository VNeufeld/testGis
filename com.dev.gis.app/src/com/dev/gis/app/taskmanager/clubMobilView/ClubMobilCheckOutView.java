package com.dev.gis.app.taskmanager.clubMobilView;

import org.apache.log4j.Logger;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.dev.gis.app.taskmanager.rentcars.RentCarsAppView;
import com.dev.gis.app.view.elements.CheckBox;
import com.dev.gis.app.view.elements.LanguageComboBox;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.app.view.listener.adac.AdacGetNextFilterPageSelectionListener;
import com.dev.gis.app.view.listener.adac.AdacShowOfferFilterSelectionListener;
import com.dev.gis.task.execution.api.IEditableTask;

public class ClubMobilCheckOutView extends RentCarsAppView {
	
	private static Logger logger = Logger.getLogger(ClubMobilCheckOutView.class);

	public static final String ID = IEditableTask.ID_ClubMobilCheckOutView;
	
	private ClubMobilReservationListTable reservationListTable;

	
	@Override
	public void createPartControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		
		final Group groupStamp = new Group(composite, SWT.TITLE);
		groupStamp.setText("Inputs:");
		groupStamp.setLayout(new GridLayout(4, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(false, false).applyTo(groupStamp);
		
		createBasicControls(groupStamp);
		
		new ClubMobilReservationListControl(composite);
		
	}

	
	@Override
	protected void createBasicControls(final Group groupStamp) {
		
		Composite cc = createComposite(groupStamp, 3, -1, true);
		new ClubMobilServerTextControl(cc);
		new ClubMobilAuthorizationCheckBox(cc,"Authorization");

		new ClubMobilLoginControl(groupStamp);
		
		Composite ccl = createComposite(groupStamp, 6, -1, true);
		new ClubMobilOperatorComboBox(ccl, 80);
		new LanguageComboBox(ccl, 80);

		new ClubMobilCustomerControl(groupStamp);
		
		
	}

	private Composite addReservationGroup(Composite composite) {
		final Group group = new Group(composite, SWT.TITLE);
		group.setText("Reservation List:");
		group.setLayout(new GridLayout(1, true));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		this.reservationListTable = new ClubMobilReservationListTable(getSite(),
				group, null, null);

		
		return group;
	}
	

	@Override
	protected void createResultFields(Group groupResult) {
		
//		new ClubMobilReservationListControl(groupResult);
		
		
//		Composite cc = createComposite(groupResult, 6, -1, false);
//
//		requestId = new OutputTextControls(cc, "Request ID", 150, 1 );
//		sessionId = new OutputTextControls(cc, "Session ID", 300,1 );
//		countVehicles = new OutputTextControls(cc, "Count of Vehicles", 100,1 );
//
//		Composite ccAll = createComposite(groupResult, 2, -1, true);
//
//		Composite ccLeft = createComposite(ccAll, 6, 1, false);
//
//		Composite ccRight = createComposite(ccAll, 2, 1, true);
//		
//		Group grRight = createGroupSpannAll(ccRight, "VehicleProperries",2);
//		
//		offerId = new OutputTextControls(ccLeft, "OfferId", 300 );
//		
//		{ // buttons
//			Composite buttonComposite = createComposite(ccLeft, 2, -1, false);
//			new ButtonControl(buttonComposite, "show filterTemplate", 0,  showOfferFilterTemplate());
//		}
//		createNextPage(ccLeft);
//		
//		OutputTextControls xxx = new OutputTextControls(grRight, "XXX", 300 );
//		OutputTextControls yyy = new OutputTextControls(grRight, "YYY", 300 );
		
	}

	private SelectionListener showOfferFilterTemplate() {
		return new AdacShowOfferFilterSelectionListener(parent.getShell());
	}

	protected SelectionListener selectNextPageSelectionListener(final OutputTextControls pageNo, final CheckBox useFilter) {
		AdacGetNextFilterPageSelectionListener listener = new AdacGetNextFilterPageSelectionListener(parent.getShell(),pageNo,useFilter);
		return listener;
	}

//	@Override
//	protected void createNextPage(Composite groupResult) {
//		// http://localhost:8080/web-joi/joi/vehicleRequest/673406724?pageNo=1
//		pageNo = new OutputTextControls(groupResult, "PageNo", 100,1 );
//		pageNo.setValue("0");
//		CheckBox useFilter = new CheckBox(groupResult, "use filter");
//		new ButtonControl(groupResult, "Show Page", 0,  selectNextPageSelectionListener(pageNo, useFilter));
//
//	}
	

	
}
