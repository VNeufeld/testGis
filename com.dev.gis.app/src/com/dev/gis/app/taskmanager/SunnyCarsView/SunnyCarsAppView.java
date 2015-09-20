package com.dev.gis.app.taskmanager.SunnyCarsView;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.dev.gis.app.taskmanager.rentcars.RentCarsAppView;
import com.dev.gis.app.view.elements.AgencyNoTextControl;
import com.dev.gis.app.view.elements.AirportLocationSearch;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.CheckBox;
import com.dev.gis.app.view.elements.CityLocationSearch;
import com.dev.gis.app.view.elements.LanguageComboBox;
import com.dev.gis.app.view.elements.OperatorComboBox;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.app.view.elements.ServerTextControl;
import com.dev.gis.app.view.listener.SelectChangedOfferClickListener;
import com.dev.gis.app.view.listener.SunnyGetNextPageSelectionListener;
import com.dev.gis.app.view.listener.SunnyGetOffersSelectionListener;
import com.dev.gis.app.view.listener.SunnySelectOfferDoubleClickListener;
import com.dev.gis.app.view.listener.SunnyShowOfferFilterSelectionListener;
import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.sunny.VehicleResponse;
import com.dev.gis.task.execution.api.IEditableTask;

public class SunnyCarsAppView extends RentCarsAppView {
	public static final String ID = IEditableTask.ID_TestSunnyCarsView;

	private SunnyOfferListTable offerListTable;

	private ResultRecommendationTable recommendationTable;
	
	private OutputTextControls countVehicles = null;

	//private OutputTextControls sessionId = null;

	private OutputTextControls requestId = null;

	private OutputTextControls offerId = null;
	
	private OutputTextControls pageNo = null;

	private OutputTextControls pageCount = null;

	@Override
	protected void createBasicControls(Group groupStamp) {
		super.createBasicControls(groupStamp);
		
		new ServerTextControl(groupStamp);
		
		new AgencyNoTextControl(groupStamp);
		
		new LanguageComboBox(groupStamp, 80);

		new OperatorComboBox(groupStamp, 80);
		
		SunnyCityLocationSearch.createCityLocationSearch(groupStamp); 

		SunnyAirportLocationSearch.createAirportLocationSearch(groupStamp);
		
	}
	
	
	@Override
	protected void createResultFields(Group groupResult) {
		

		Composite cc = createComposite(groupResult, 4, -1, false);

		requestId = new OutputTextControls(cc, "Request ID", 150, 1 );

		countVehicles = new OutputTextControls(cc, "Count of Vehicles", 100,1 );
		
		//sessionId = new OutputTextControls(cc, "Session ID", 300 );
		
		offerId = new OutputTextControls(groupResult, "OfferId", 300 );
		
		{ // buttons
			Composite buttonComposite = createComposite(groupResult, 2, -1, false);
	
			new ButtonControl(buttonComposite, "show filterTemplate", 0,  showOfferFilterListener());
			new ButtonControl(buttonComposite, "show text summary", 0,  showOfferFilterListener());
			
		}

		createNextPage(groupResult);
	}
	
	
	public void showVehicleResponse(VehicleResponse response) {
		
		
		//pageInfo.setText(response.getPageInfo());

		// sessionId.setText(String.valueOf(response.getRequestId()));

		requestId.setValue(String.valueOf(response.getRequestId()));
		
		countVehicles.setValue(String.valueOf(response.getSummary().getTotalQuantityOffers()));

		//setSummary(response.getSummary());

		//contact.setText(" get Contact from response");
		

		// Table
		SunnyModelProvider.INSTANCE.updateOffers(response);
		
		offerListTable.update();
		
		if ( recommendationTable != null) {
			recommendationTable.update();
		}
		
		pageNo.setValue(response.getPageNo());
		pageCount.setValue(response.getPageCount());

		
	}


	

	@Override
	protected SelectionListener getOffersSelectionListener() {
		SunnyGetOffersSelectionListener listener = new SunnyGetOffersSelectionListener(parent.getShell()); 
		return listener;
	}

	@Override
	protected ISelectionChangedListener getSelectChangedOfferClickListener() {
		SelectChangedOfferClickListener ss = new SelectChangedOfferClickListener(offerId.getControl());
		return ss;
	}

	@Override
	protected IDoubleClickListener getSelectOfferDoubleClickListener() {
		SunnySelectOfferDoubleClickListener ssd = new SunnySelectOfferDoubleClickListener();
		return ssd;
	}


	@Override
	protected void updateParent(VehicleResponse response) {
	}

	private SelectionListener getNextPageSelectionListener(final OutputTextControls pageNo, final CheckBox useFilter) {
		SunnyGetNextPageSelectionListener sgn = new SunnyGetNextPageSelectionListener(parent.getShell(), pageNo,useFilter );
		return sgn;
	}


	@Override
	protected SelectionListener showOfferFilterListener() {
		SunnyShowOfferFilterSelectionListener sgn = new SunnyShowOfferFilterSelectionListener(parent.getShell());
		return sgn;
	}


	@Override
	protected void createExtFilter(Group groupStamp) {
		final Group groupFilter = new Group(groupStamp, SWT.TITLE);
		groupFilter.setText("Filter :");
		groupFilter.setLayout(new GridLayout(6, false));
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING).span(4,1)
				.grab(true, true).applyTo(groupFilter);
		
		new SunnyExtSupplierFilterTextControl(groupFilter);
		new SunnyExtServcatFilterTextControl(groupFilter);
		new SunnyExtStationFilterTextControl(groupFilter);
	}

	@Override
	protected void createNextPage(Composite groupResult) {
		// http://localhost:8080/web-joi/joi/vehicleRequest/673406724?pageNo=1
		
		Composite cc = createComposite(groupResult, 4, 2, false);
		
		pageNo = new OutputTextControls(cc, "PageNo", 100,1 );
		pageNo.setValue("0");

		pageCount = new OutputTextControls(cc, "PageCount", 100,1 );
		
		CheckBox useFilter = new CheckBox(groupResult, "use filter");
		new ButtonControl(groupResult, "Show Page", 0,  getNextPageSelectionListener(pageNo, useFilter));

	}


	@Override
	protected void createResultTables(Composite composite) {
		super.createResultTables(composite);
		
		createResultOfferListTable(composite);
		createRecommendationTable(composite);
		
	}

	private  void createResultOfferListTable(Composite composite) {
		
		final Group groupOffers = new Group(composite, SWT.TITLE);
		groupOffers.setText("Ofers:");
		
		groupOffers.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, true).applyTo(groupOffers);
		
		offerListTable = new SunnyOfferListTable(getSite(),
				groupOffers, getSelectOfferDoubleClickListener(), getSelectChangedOfferClickListener() );
		
		
	}

	private void createRecommendationTable(Composite composite) {
		
		final Group groupRecomm = new Group(composite, SWT.TITLE);
		groupRecomm.setText("Recommendations:");
		groupRecomm.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.END).minSize(500, 100)
				.grab(true, false).applyTo(groupRecomm);
		
		recommendationTable = new ResultRecommendationTable(getSite(),groupRecomm);
	}


	public void clearView() {
		SunnyModelProvider.INSTANCE.updateOffers(null);
		offerListTable.getViewer().setInput(SunnyModelProvider.INSTANCE.getOfferDos());
		offerListTable.getViewer().refresh();
	}



}
