package com.dev.gis.app.taskmanager.rentcars;

import java.awt.Color;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.DropOffDateControl;
import com.dev.gis.app.view.elements.PageSizeControl;
import com.dev.gis.app.view.elements.PickupDateControl;
import com.dev.gis.app.view.elements.SessionIdControl;
import com.dev.gis.connector.sunny.VehicleResponse;
import com.dev.gis.connector.sunny.VehicleSummary;
import com.dev.gis.task.execution.api.IEditableTask;

public class RentCarsAppView extends TaskViewAbstract {
	public static final String ID = IEditableTask.ID_TestRentCarsView;


	protected Text pageInfo = null;
	
	protected Text summary = null;

	protected Text sessionId = null;

	protected Text offerId = null;
	
	protected Text browseFilter = null;

	protected Text contact = null;

	protected Text offerFilterTemlate = null;
	
	protected Text requestId = null;
	
	

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
		
		createDatesControl(groupStamp);
		
		createRadioButtonsCarAndTruck(groupStamp);

		createExtFilter(groupStamp);
		
		createExecutionPanel(groupStamp);

		// Result - Group
		createResultComposite(composite);
		
		createResultTables(composite);
		
		composite.pack();
	
	}

	protected void createExecutionPanel(Group groupStamp) {
		final Composite groupExecution = createComposite(groupStamp, 6, -1,true);
		new SessionIdControl(groupExecution);
		createPageSize(groupExecution);
		ButtonControl  bc =new ButtonControl(groupExecution, "GetOffer", 0, getOffersSelectionListener());
		bc.getButton().setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));

	}

	protected void createDatesControl(Group groupStamp) {
		
		final Group groupDates = createGroupSpannAll(groupStamp, "Dates",6);
		
		new PickupDateControl(groupDates);
		new DropOffDateControl(groupDates);
		
	}

	protected void createResultTables(Composite composite) {
		
	}

	protected void createPageSize(Composite groupStamp) {
		new PageSizeControl(groupStamp);
		
	}

	protected void createExtFilter(Group groupStamp) {
		
	}


	protected void createBasicControls(final Group groupStamp) {
		
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



	private void setSummary(VehicleSummary vehicleSummary) {
		StringBuilder sb = new StringBuilder();
		sb.append("Offers on the page : "+vehicleSummary.getQuantityOffers());
		sb.append("/ Offers total : "+vehicleSummary.getTotalQuantityOffers());
		
		summary.setText(sb.toString());

	}

	
	protected ISelectionChangedListener getSelectChangedOfferClickListener() {
		// TODO Auto-generated method stub
		return null;
	}


	protected IDoubleClickListener getSelectOfferDoubleClickListener() {
		// TODO Auto-generated method stub
		return null;
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
