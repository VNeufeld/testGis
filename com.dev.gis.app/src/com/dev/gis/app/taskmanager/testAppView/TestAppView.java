package com.dev.gis.app.taskmanager.testAppView;

import java.awt.Color;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;

import com.dev.gis.app.taskmanager.SunnyCarsView.SunnyAirportLocationSearch;
import com.dev.gis.app.taskmanager.SunnyCarsView.SunnyCityLocationSearch;
import com.dev.gis.app.taskmanager.SunnyCarsView.SunnyExtServcatFilterTextControl;
import com.dev.gis.app.taskmanager.SunnyCarsView.SunnyExtStationFilterTextControl;
import com.dev.gis.app.taskmanager.SunnyCarsView.SunnyExtSupplierFilterTextControl;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilOperatorComboBox;
import com.dev.gis.app.taskmanager.rentcars.RentCarsAppView;
import com.dev.gis.app.view.elements.AirportLocationSearch;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.CheckBox;
import com.dev.gis.app.view.elements.CityLocationSearch;
import com.dev.gis.app.view.elements.LanguageComboBox;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.app.view.elements.PageSizeControl;
import com.dev.gis.app.view.listener.adac.AdacGetNextFilterPageSelectionListener;
import com.dev.gis.app.view.listener.adac.AdacJoiGetOffersSelectionListener;
import com.dev.gis.app.view.listener.adac.AdacSelectChangedOfferClickListener;
import com.dev.gis.app.view.listener.adac.AdacSelectOfferDoubleClickListener;
import com.dev.gis.app.view.listener.adac.AdacShowOfferFilterSelectionListener;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.joi.protocol.VehicleResponse;
import com.dev.gis.task.execution.api.IEditableTask;

public class TestAppView extends RentCarsAppView {
	
	private static Logger logger = Logger.getLogger(TestAppView.class);

	public static final String ID = IEditableTask.ID_TestAppView;
	
	private CrossOfferListTable crossOfferListTable;
	
	private OutputTextControls countVehicles = null;

	private OutputTextControls sessionId = null;

	private OutputTextControls requestId = null;
	
	private AdacOfferListTable offerListTable;
	
	private OutputTextControls offerId = null;

	private OutputTextControls pageNo = null;
	

	
	@Override
	protected void createBasicControls(final Group groupStamp) {
		
		Composite cc = createComposite(groupStamp, 3, -1, true);
		
		//new AdacServerTextControl(cc);
		new AdacDropDownTextControl(cc);
		new AdacAuthorizationCheckBox(cc,"Authorization");
		
		Composite ccl = createComposite(groupStamp, 6, -1, true);
		new AdacOperatorComboBox(groupStamp, 80);
		new LanguageComboBox(ccl, 80);
		
		createLocationGroup(groupStamp);
		
	}
	
	@Override
	protected void createExtFilter(Group groupStamp) {
		final Group groupFilter = new Group(groupStamp, SWT.TITLE);
		groupFilter.setText("Filter :");
		groupFilter.setLayout(new GridLayout(6, false));
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING).span(4,1)
				.grab(true, true).applyTo(groupFilter);
		
		new AdacExtSupplierFilterTextControl(groupFilter);
		new AdacExtServcatFilterTextControl(groupFilter);
		new AdacExtStationFilterTextControl(groupFilter);
	}

	
	private void createLocationGroup(Group groupStamp) {
		final Group groupLocation = createGroupSpannAll(groupStamp, "Location",4);

		AdacCityLocationSearch.createPickupCityLocationSearch(groupLocation); 

		AdacAirportLocationSearch.createPickupAirportLocationSearch(groupLocation);
		
		AdacCityLocationSearch.createDropoffCityLocationSearch(groupLocation); 

		AdacAirportLocationSearch.createDropoffAirportLocationSearch(groupLocation);
		
	}

	
	protected void createPageSize(Group groupStamp) {
		Composite cc = createComposite(groupStamp, 4, -1, false);
		cc.setBackground(cc.getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION));

		new PageSizeControl(cc, 50, false);
		CrossOfferCheckBox crossOffer = new CrossOfferCheckBox(cc, "use crossOffer");

		ButtonControl bb = new ButtonControl(cc, "GetOffer", 0, getOffersSelectionListener());
		bb.getButton().setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));

	}
	protected void createExecutionPanel(Group groupStamp) {
		createPageSize(groupStamp);

	}

	
	protected Button createRadioButtonsCarAndTruck(final Group groupStamp) {
		Composite rbComposite = new Composite(groupStamp, SWT.NONE);
		rbComposite.setLayout(new GridLayout(2, true));
		GridDataFactory.fillDefaults().span(4, 1)
				.align(SWT.FILL, SWT.BEGINNING).grab(true, false)
				.applyTo(rbComposite);

		final Button buttonCar = new Button(rbComposite, SWT.RADIO);
		buttonCar.setSelection(true);
		buttonCar.setText("Car");

		final Button buttonTruck = new Button(rbComposite, SWT.RADIO);
		buttonTruck.setSelection(false);
		buttonTruck.setText("Truck");
		
		SelectionListener sl = new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent event) {
				Object obj = event.getSource();
				if (obj instanceof Button) {
					Button b = (Button) obj;
					if ( b.getSelection() ) {
						AdacModelProvider.INSTANCE.module = 2;
					}
					else
						AdacModelProvider.INSTANCE.module = 1;
				}
				logger.info("Select Modle "+AdacModelProvider.INSTANCE.module);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		buttonTruck.addSelectionListener(sl);

		return buttonTruck;
	}
	


	
	
	@Override
	protected ISelectionChangedListener getSelectChangedOfferClickListener() {
		AdacSelectChangedOfferClickListener ss = new AdacSelectChangedOfferClickListener(offerId);
		return ss;
	}

	@Override
	protected IDoubleClickListener getSelectOfferDoubleClickListener() {
		AdacSelectOfferDoubleClickListener ssd = new AdacSelectOfferDoubleClickListener();
		return ssd;
	}

	@Override
	protected SelectionListener getOffersSelectionListener() {
		AdacJoiGetOffersSelectionListener listener = new AdacJoiGetOffersSelectionListener(parent.getShell()); 
		return listener;
	}
	
	public void showVehicleResponse(VehicleResponse response) {
		
		countVehicles.setValue(String.valueOf(response.getResultList().size()));
		
		if ( response.getSessionId() != null)
			sessionId.setValue(response.getSessionId());
		requestId.setValue(String.valueOf(response.getRequestId()));	
		if ( StringUtils.isEmpty(pageNo.getValue()))
				pageNo.setValue("0");
		
		AdacModelProvider.INSTANCE.updateResponse(response);
		
		offerListTable.update();
		
		crossOfferListTable.update();
		
	}

	public void clearView() {
		AdacModelProvider.INSTANCE.clearView();
		offerListTable.getViewer().setInput(AdacModelProvider.INSTANCE.getOfferDos());
		offerListTable.getViewer().refresh();

	}

	@Override
	protected void createResultFields(Group groupResult) {
		
		Composite cc = createComposite(groupResult, 6, -1, false);

		requestId = new OutputTextControls(cc, "Request ID", 150, 1 );
		sessionId = new OutputTextControls(cc, "Session ID", 300,1 );
		countVehicles = new OutputTextControls(cc, "Count of Vehicles", 100,1 );

		Composite ccAll = createComposite(groupResult, 2, -1, true);

		Composite ccLeft = createComposite(ccAll, 6, 1, false);

		Composite ccRight = createComposite(ccAll, 2, 1, true);
		
		Group grRight = createGroupSpannAll(ccRight, "VehicleProperries",2);
		
		offerId = new OutputTextControls(ccLeft, "OfferId", 300 );
		
		{ // buttons
			Composite buttonComposite = createComposite(ccLeft, 2, -1, false);
			new ButtonControl(buttonComposite, "show filterTemplate", 0,  showOfferFilterTemplate());
		}
		createNextPage(ccLeft);
		
		OutputTextControls xxx = new OutputTextControls(grRight, "XXX", 300 );
		OutputTextControls yyy = new OutputTextControls(grRight, "YYY", 300 );
		
	}

	private SelectionListener showOfferFilterTemplate() {
		return new AdacShowOfferFilterSelectionListener(parent.getShell());
	}

	protected SelectionListener selectNextPageSelectionListener(final OutputTextControls pageNo, final CheckBox useFilter) {
		AdacGetNextFilterPageSelectionListener listener = new AdacGetNextFilterPageSelectionListener(parent.getShell(),pageNo,useFilter);
		return listener;
	}

	@Override
	protected void createNextPage(Composite groupResult) {
		// http://localhost:8080/web-joi/joi/vehicleRequest/673406724?pageNo=1
		pageNo = new OutputTextControls(groupResult, "PageNo", 100,1 );
		pageNo.setValue("0");
		CheckBox useFilter = new CheckBox(groupResult, "use filter");
		new ButtonControl(groupResult, "Show Page", 0,  selectNextPageSelectionListener(pageNo, useFilter));

	}
	
	protected static class CrossOfferOperatorControl extends ObjectTextControl {
		private static final String PREFERENCE_PROPERTY = "ADAC_CROSSOFFER";

		public CrossOfferOperatorControl(Composite parent) {
			super(parent, 50, false);
		}

		@Override
		protected String getLabel() {
			return "Cross Offer Operator";
		}


		@Override
		public void saveValue(String value) {
			if ( StringUtils.isEmpty(value))
				value = "1";
			AdacModelProvider.INSTANCE.crossOfferOperator = value;
			logger.info("Cross Offer Operator : "+value);
			
			saveProperty(PREFERENCE_PROPERTY,value);
		}

		@Override
		protected String getDefaultValue() {
			String value = readProperty(PREFERENCE_PROPERTY);
			return value;
		}

	}

	
	private void createCrossOfferTable(Composite composite) {
		
		final Group groupRecomm = new Group(composite, SWT.TITLE);
		groupRecomm.setText("CrossOfferList:");
		groupRecomm.setLayout(new GridLayout(1, false));

		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).minSize(500, 300)
		.grab(true, false).applyTo(groupRecomm);
		
		
		crossOfferListTable = new CrossOfferListTable(getSite(),groupRecomm, null);
	}
	
	private void createResultOfferListTable(Composite composite) {
		final Group groupOffers = new Group(composite, SWT.TITLE);
		groupOffers.setText("Ofers:");
		
		groupOffers.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, true).applyTo(groupOffers);
		
		this.offerListTable = new AdacOfferListTable(getSite(),
				groupOffers, getSelectOfferDoubleClickListener(), getSelectChangedOfferClickListener() );
	}


	@Override
	protected void createResultTables(Composite composite) {

		createResultOfferListTable(composite);

		createCrossOfferTable(composite);

	}

	
}
