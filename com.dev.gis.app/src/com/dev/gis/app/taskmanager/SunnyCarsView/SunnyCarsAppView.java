package com.dev.gis.app.taskmanager.SunnyCarsView;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.dev.gis.app.taskmanager.rentcars.RentCarsAppView;
import com.dev.gis.app.view.elements.ResultRecommendationTable;
import com.dev.gis.app.view.listener.SelectChangedOfferClickListener;
import com.dev.gis.app.view.listener.SunnyGetNextFilterPageSelectionListener;
import com.dev.gis.app.view.listener.SunnyGetNextPageSelectionListener;
import com.dev.gis.app.view.listener.SunnyGetOffersSelectionListener;
import com.dev.gis.app.view.listener.SunnySelectOfferDoubleClickListener;
import com.dev.gis.app.view.listener.SunnyShowOfferFilterSelectionListener;
import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.sunny.VehicleResponse;
import com.dev.gis.task.execution.api.IEditableTask;

public class SunnyCarsAppView extends RentCarsAppView {
	public static final String ID = IEditableTask.ID_TestSunnyCarsView;

	private ResultRecommendationTable recommendationTable;


	@Override
	protected void createRecommendationTable(Composite composite) {
		
		final Group groupRecomm = new Group(composite, SWT.TITLE);
		groupRecomm.setText("Recommendations:");
		groupRecomm.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, true).applyTo(groupRecomm);
		
		recommendationTable = new ResultRecommendationTable(getSite(),
				groupRecomm);
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

		return buttonTruck;
	}
	

	@Override
	protected SelectionListener getOffersSelectionListener() {
		SunnyGetOffersSelectionListener listener = new SunnyGetOffersSelectionListener(parent.getShell()); 
		return listener;
	}

	@Override
	protected ISelectionChangedListener getSelectChangedOfferClickListener() {
		SelectChangedOfferClickListener ss = new SelectChangedOfferClickListener(offerId);
		return ss;
	}

	@Override
	protected IDoubleClickListener getSelectOfferDoubleClickListener() {
		SunnySelectOfferDoubleClickListener ssd = new SunnySelectOfferDoubleClickListener();
		return ssd;
	}


	@Override
	protected void updateParent(VehicleResponse response) {
		if ( recommendationTable != null) {
			SunnyModelProvider.INSTANCE.updateRecmmendations(response);
			recommendationTable.getViewer().setInput(SunnyModelProvider.INSTANCE.getRecommendations());
			recommendationTable.getViewer().refresh();
		}
	}


	@Override
	protected SelectionListener getNextPageSelectionListener() {
		SunnyGetNextPageSelectionListener sgn = new SunnyGetNextPageSelectionListener(parent.getShell());
		return sgn;
	}


	@Override
	protected SelectionListener getNextFilterPageSelectionListener() {
		SunnyGetNextFilterPageSelectionListener sgn = new SunnyGetNextFilterPageSelectionListener(browseFilter);
		return sgn;
	}

	@Override
	protected SelectionListener showOfferFilterListener() {
		SunnyShowOfferFilterSelectionListener sgn = new SunnyShowOfferFilterSelectionListener(parent.getShell());
		return sgn;
	}


}
