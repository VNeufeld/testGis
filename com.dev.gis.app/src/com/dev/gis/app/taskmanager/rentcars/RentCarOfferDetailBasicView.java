package com.dev.gis.app.taskmanager.rentcars;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.app.view.elements.OutputTextControls;

public abstract class RentCarOfferDetailBasicView extends TaskViewAbstract {

	protected OutputTextControls textOfferLink = null;
	
	protected OutputTextControls priceText = null;

	protected Text pickupStationsResponse;

	protected Text recalculateResponse;
	
	
	
	@Override
	protected void createWindow(Composite composite) {
		// Offer - Group
		Composite offerGroup = createOfferGroup(composite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, false).applyTo(offerGroup);

		Composite rbComp = createRequestButtons(composite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, false).applyTo(rbComp);

		// Inclusives
		addInclusivesGroup(composite);
		
		// Extras
		addExtraGroup(composite);

		// Booking
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, false).applyTo(addBookingButton(composite));
	}

	
	protected void addExtraGroup(Composite composite) {
	}

	protected void addInclusivesGroup(Composite composite) {
	}


	protected Composite createOfferGroup(Composite composite) {

		final Group groupStamp = new Group(composite, SWT.TITLE);
		groupStamp.setText("Offer:");
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false)
				.applyTo(groupStamp);

		textOfferLink = new OutputTextControls(groupStamp, "Offer", -1, 1 );
		
		createPrice(groupStamp);

		createVehicleInfoOutput(groupStamp);

		createServcatSupplierOutput(groupStamp);
		
		createLocationGroup(groupStamp);

		return groupStamp;
	}

	protected void createServcatSupplierOutput(Group groupStamp) {
		// TODO Auto-generated method stub
		
	}


	protected void createPrice(Group groupStamp) {
		priceText = new OutputTextControls(groupStamp, "Preis:", 200, 1 );
	}


	protected abstract void createLocationGroup(Group groupStamp);


	protected abstract void createVehicleInfoOutput(Group groupStamp);


	

	private Composite addBookingButton(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false)
				.applyTo(composite);

		final Button buttonBooking = new Button(composite, SWT.PUSH | SWT.LEFT);
		buttonBooking.setText("Booking");

		buttonBooking.addSelectionListener(getBookingViewSelectionListener());

		return composite;
	}



	protected Composite createRequestButtons(final Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false)
				.applyTo(composite);

		final Button buttonRecalculate = new Button(composite, SWT.PUSH
				| SWT.LEFT);
		buttonRecalculate.setText("POST Recalculate");

		buttonRecalculate.addSelectionListener(getRecalculateSelectionListener());

		recalculateResponse = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, false).applyTo(recalculateResponse);

		final Button buttonGetPickupStations = new Button(composite, SWT.PUSH
				| SWT.LEFT);
		buttonGetPickupStations.setText("Get PickupStations");
		buttonGetPickupStations
				.addSelectionListener(getPickupStationSelectionListener());

		pickupStationsResponse = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, false).applyTo(pickupStationsResponse);

		return composite;

	}

	protected SelectionListener getRecalculateSelectionListener() {
		return null;
	}

	protected SelectionListener getPickupStationSelectionListener() {
		return null;
	}
	
	protected SelectionListener getBookingViewSelectionListener() {
		return null;
	}
	
}
