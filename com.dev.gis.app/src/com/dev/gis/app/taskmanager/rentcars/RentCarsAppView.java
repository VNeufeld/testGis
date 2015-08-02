package com.dev.gis.app.taskmanager.rentcars;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.app.taskmanager.SunnyCarsView.SunnyOfferListTable;
import com.dev.gis.app.view.elements.AgencyNoTextControl;
import com.dev.gis.app.view.elements.AirportLocationSearch;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.CityLocationSearch;
import com.dev.gis.app.view.elements.DropOffDateControl;
import com.dev.gis.app.view.elements.LanguageComboBox;
import com.dev.gis.app.view.elements.OperatorComboBox;
import com.dev.gis.app.view.elements.PageSizeControl;
import com.dev.gis.app.view.elements.PickupDateControl;
import com.dev.gis.app.view.elements.ServerTextControl;
import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.sunny.VehicleResponse;
import com.dev.gis.connector.sunny.VehicleSummary;
import com.dev.gis.task.execution.api.IEditableTask;

public class RentCarsAppView extends TaskViewAbstract {
	public static final String ID = IEditableTask.ID_TestRentCarsView;

	private SunnyOfferListTable offerListTable;

	protected Text pageInfo = null;
	
	protected Text summary = null;

	protected Text sessionId = null;

	protected Text offerId = null;
	
	protected Text browseFilter = null;

	protected Text contact = null;

	protected Text offerFilterTemlate = null;
	
	protected Text requestId = null;
	
	
	protected Composite parent;

	@Override
	public void createPartControl(Composite parent) {

		this.parent = parent;

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		final Group groupStamp = new Group(composite, SWT.TITLE);
		groupStamp.setText("Inputs:");
		groupStamp.setLayout(new GridLayout(4, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(false, false).applyTo(groupStamp);
		
		createBasicControls(groupStamp);
		
		new PickupDateControl(groupStamp);

		new DropOffDateControl(groupStamp);

		createRadioButtonsCarAndTruck(groupStamp);

		new PageSizeControl(groupStamp);

		new ButtonControl(groupStamp, "GetOffer", 0, getOffersSelectionListener());

		// Result - Group
		createResultComposite(composite);

		createResultOfferListTable(composite);
		
		createRecommendationTable(composite);
	
	}


	protected void createBasicControls(final Group groupStamp) {
		
		new ServerTextControl(groupStamp);
		
		new AgencyNoTextControl(groupStamp);
		
		new LanguageComboBox(groupStamp, 80);

		new OperatorComboBox(groupStamp, 80);
		
		CityLocationSearch.createCityLocationSearch(groupStamp); 

		AirportLocationSearch.createAirportLocationSearch(groupStamp);
	}


	protected  void createResultOfferListTable(Composite composite) {
		
		final Group groupOffers = new Group(composite, SWT.TITLE);
		groupOffers.setText("Ofers:");
		
		groupOffers.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, true).applyTo(groupOffers);
		
		offerListTable = new SunnyOfferListTable(getSite(),
				groupOffers, getSelectOfferDoubleClickListener(), getSelectChangedOfferClickListener() );
		
		
	}

	protected void createNextPage(final Composite groupResult) {
		
		new Label(groupResult, SWT.NONE).setText("Page");
		pageInfo =  new Text(groupResult, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.hint(800, 16).grab(false, false).span(2, 1).applyTo(pageInfo);
		
		new ButtonControl(groupResult, "next page", 0,  getNextPageSelectionListener());
	
	}
	
	protected void createBrowseFilterPage(final Composite groupResult) {
		
		new Label(groupResult, SWT.NONE).setText("BrowseFilter");
		browseFilter =  new Text(groupResult, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.hint(800, 16).grab(false, false).span(2, 1).applyTo(browseFilter);

		new ButtonControl(groupResult, "browse filter page", 0,  getNextFilterPageSelectionListener());
	}


	private Composite createResultComposite(final Composite composite) {
	
		final Group groupResult = new Group(composite, SWT.TITLE);
		groupResult.setText("Result:");
		groupResult.setLayout(new GridLayout(4, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(false, false).span(4, 1).applyTo(groupResult);

		createResultFields(groupResult);



		return groupResult;
	}
	
	protected Composite createComposite(final Composite parent,int columns, int span, boolean grab) {
		
		GridLayout gd = (GridLayout)parent.getLayout();
		int col = gd.numColumns;
		
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(columns).equalWidth(false).applyTo(composite);

		if ( span < 0)
			GridDataFactory.fillDefaults().span(col, 1)
					.align(SWT.FILL, SWT.BEGINNING).grab(grab, false)
					.applyTo(composite);
		else
			GridDataFactory.fillDefaults().span(span, 1)
			.align(SWT.FILL, SWT.BEGINNING).grab(grab, false)
			.applyTo(composite);


		return composite;
	}



	protected void createResultFields(final Group groupResult) {
		
		createNextPage(groupResult);
		
		createBrowseFilterPage(groupResult);

		new Label(groupResult, SWT.NONE).setText("Summary");

		summary = new Text(groupResult, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(true, false).span(3, 1).applyTo(summary);



		new Label(groupResult, SWT.NONE).setText("Request ID");
		requestId = new Text(groupResult, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER)
				.hint(200, 16).grab(false, false).span(3, 1).applyTo(requestId);

		new Label(groupResult, SWT.NONE).setText("Session ID");
		sessionId = new Text(groupResult, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER)
				.hint(300, 16).grab(false, false).span(3, 1).applyTo(sessionId);

		new Label(groupResult, SWT.NONE).setText("OfferId");
		offerId = new Text(groupResult, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER)
				.hint(300, 16).grab(false, false).span(3, 1).applyTo(offerId);

		new Label(groupResult, SWT.NONE).setText("Contact");
		contact = new Text(groupResult, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(true, false).span(3, 1).applyTo(contact);
		
		createFilterTemplate(groupResult);
	}




	protected void createFilterTemplate(Group groupResult) {
		new Label(groupResult, SWT.NONE).setText("offerFilterTemplate");
		offerFilterTemlate = new Text(groupResult, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.hint(800, 16).grab(false, false).span(2, 1).applyTo(offerFilterTemlate);
		
		Button showFilter = new Button(groupResult, SWT.PUSH | SWT.LEFT);
		showFilter.setText("show Filter");
		SelectionListener sl = showOfferFilterListener();
		if (sl != null)
			showFilter.addSelectionListener(sl);
	}


	protected SelectionListener showOfferFilterListener() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void showVehicleResponse(VehicleResponse response) {
		
		
		pageInfo.setText(response.getPageInfo());

		// sessionId.setText(String.valueOf(response.getRequestId()));

		requestId.setText(String.valueOf(response.getRequestId()));

		setSummary(response.getSummary());

		contact.setText(" get Contact from response");
		
		if (response.getOfferFilterTemplate() != null) {
			String offerFilter = response.getOfferFilterTemplate().toString();
			offerFilterTemlate.setText(offerFilter);
			SunnyModelProvider.INSTANCE.currentResponse = response;
		}

		// Table
		SunnyModelProvider.INSTANCE.updateOffers(response);
		offerListTable.getViewer().setInput(SunnyModelProvider.INSTANCE.getOfferDos());
		offerListTable.getViewer().refresh();
		
		updateParent(response);
		
	}



	private void setSummary(VehicleSummary vehicleSummary) {
		StringBuilder sb = new StringBuilder();
		sb.append("Offers on the page : "+vehicleSummary.getQuantityOffers());
		sb.append("/ Offers total : "+vehicleSummary.getTotalQuantityOffers());
		
		summary.setText(sb.toString());

	}

	public void clearView() {
		SunnyModelProvider.INSTANCE.updateOffers(null);
		offerListTable.getViewer().setInput(SunnyModelProvider.INSTANCE.getOfferDos());
		offerListTable.getViewer().refresh();
	}
	
	protected ISelectionChangedListener getSelectChangedOfferClickListener() {
		// TODO Auto-generated method stub
		return null;
	}


	protected IDoubleClickListener getSelectOfferDoubleClickListener() {
		// TODO Auto-generated method stub
		return null;
	}

	protected  void createRecommendationTable(Composite composite) {
		
	}


	protected Button createRadioButtonsCarAndTruck(final Group groupStamp) {

		return null;
	}
	
	protected SelectionListener getOffersSelectionListener() {
		return null;
	}

	protected SelectionListener getNextPageSelectionListener() {
		return null;
	}

	protected SelectionListener getNextFilterPageSelectionListener() {
		return null;
	}
	
	protected void updateParent(VehicleResponse response) {
		// TODO Auto-generated method stub
		
	}
	
}
