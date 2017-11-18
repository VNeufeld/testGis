package com.dev.gis.app.taskmanager.clubMobilView;

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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;

import com.dev.gis.app.taskmanager.rentcars.RentCarsAppView;
import com.dev.gis.app.taskmanager.testAppView.AdacAirportLocationSearch;
import com.dev.gis.app.taskmanager.testAppView.AdacAuthorizationCheckBox;
import com.dev.gis.app.taskmanager.testAppView.AdacCityLocationSearch;
import com.dev.gis.app.taskmanager.testAppView.AdacExtServcatFilterTextControl;
import com.dev.gis.app.taskmanager.testAppView.AdacExtStationFilterTextControl;
import com.dev.gis.app.taskmanager.testAppView.AdacExtSupplierFilterTextControl;
import com.dev.gis.app.taskmanager.testAppView.AdacOfferListTable;
import com.dev.gis.app.taskmanager.testAppView.AdacOperatorComboBox;
import com.dev.gis.app.taskmanager.testAppView.AdacServerTextControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.CheckBox;
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

public class ClubMobilView extends RentCarsAppView {
	
	private static Logger logger = Logger.getLogger(ClubMobilView.class);

	public static final String ID = IEditableTask.ID_ClubMobilAppView;
	
	private OutputTextControls countVehicles = null;

	private OutputTextControls sessionId = null;

	private OutputTextControls requestId = null;
	
	private AdacOfferListTable offerListTable;
	
	private OutputTextControls offerId = null;

	private OutputTextControls pageNo = null;
	
	private ClubMobilLoginControl loginControl = null;
	
	
	@Override
	protected void createBasicControls(final Group groupStamp) {
		
		Composite cc = createComposite(groupStamp, 3, -1, true);
		new ClubMobilServerTextControl(cc);
		new ClubMobilAuthorizationCheckBox(cc,"Authorization");

		loginControl = new ClubMobilLoginControl(groupStamp);
		
		Composite ccl = createComposite(groupStamp, 6, -1, true);
		new ClubMobilOperatorComboBox(ccl, 80);
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

		createLocationRadioButton(groupLocation);

		AdacCityLocationSearch.createPickupCityLocationSearch(groupLocation); 

		AdacAirportLocationSearch.createPickupAirportLocationSearch(groupLocation);

		
		AdacCityLocationSearch.createDropoffCityLocationSearch(groupLocation); 

		AdacAirportLocationSearch.createDropoffAirportLocationSearch(groupLocation);
		
		final Group stationGroup = createGroupSpannAll(groupLocation,"Stations",2);

		ClubMobilStationSearch.createPickupCityLocationSearch(stationGroup); 
		ClubMobilStationSearch.createDropoffCityLocationSearch(stationGroup); 
		
		
	}

	private void createLocationRadioButton(final Group groupLocation) {
		Composite rbComposite = new Composite(groupLocation, SWT.NONE);
		rbComposite.setLayout(new GridLayout(3, true));
		GridDataFactory.fillDefaults().span(4, 1)
				.align(SWT.FILL, SWT.BEGINNING).grab(true, false)
				.applyTo(rbComposite);
		
		
		final Button buttonStation = new Button(rbComposite, SWT.RADIO);
		buttonStation.setSelection(true);
		buttonStation.setText("Stations");

		final Button buttonCity = new Button(rbComposite, SWT.RADIO);
		buttonCity.setSelection(false);
		buttonCity.setText("City");

		final Button buttonApt = new Button(rbComposite, SWT.RADIO);
		buttonApt.setSelection(false);
		buttonApt.setText("Airport");
		
		SelectionListener sl = new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent event) {
				Object obj = event.getSource();
				if (obj instanceof Button) {
					Button b = (Button) obj;
					if ( b.getSelection() ) {
						if ( "Stations".equals(b.getText()))
							AdacModelProvider.INSTANCE.locationType = 1;
						else if ( "City".equals(b.getText()))
							AdacModelProvider.INSTANCE.locationType = 2;
						else if ( "Airport".equals(b.getText()))
							AdacModelProvider.INSTANCE.locationType = 4;
					}
				}
				logger.info("Select LocationType "+AdacModelProvider.INSTANCE.locationType);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		buttonStation.addSelectionListener(sl);
		buttonCity.addSelectionListener(sl);
		buttonApt.addSelectionListener(sl);
	}

	
	protected void createPageSize(Group groupStamp) {
		Composite cc = createComposite(groupStamp, 4, -1, false);
		cc.setBackground(cc.getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION));
		new PageSizeControl(cc, 50, false);

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
		ClubMobilGetOffersSelectionListener listener = new ClubMobilGetOffersSelectionListener(parent.getShell()); 
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

	}

	
}
