package com.dev.gis.app.taskmanager.clubMobilView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.ModelProvider;
import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.joi.protocol.FilterObject;
import com.dev.gis.connector.joi.protocol.ObjectValuePair;
import com.dev.gis.connector.joi.protocol.OfferFilterTemplate;
import com.dev.gis.connector.joi.protocol.VehicleRequestFilter;
import com.dev.gis.connector.joi.protocol.VehicleResponse;

public class ClubMobilOfferFilterDialog extends Dialog {

	private final VehicleRequestFilter vehicleRequestFilter = new VehicleRequestFilter();

	private Button selectMinMax;
	private Button aircondition;
	private Button automatic;
	
	Map<Long,Button> serviceCatalogButtons = new HashMap<Long,Button>();
	Map<Long,Button> bodyStylesButtons = new HashMap<Long,Button>();
	Map<Long,Button> carTypesButtons = new HashMap<Long,Button>();
	Map<Long,Button> inclusiveButtons = new HashMap<Long,Button>();
	Map<Long,Button> supplierButtons = new HashMap<Long,Button>();
	Map<Long,Button> paymentTypesButtons = new HashMap<Long,Button>();
	Map<Long,Button> autCarTypesButtons = new HashMap<Long,Button>();

	private Text min, max;

	public ClubMobilOfferFilterDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);

	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.getShell().setText("CM_Offer Filter");

		OfferFilterTemplate offerFilterTemplate = null;
		VehicleResponse vehicleResponse = ClubMobilModelProvider.INSTANCE.getVehicleResponse();
		if (vehicleResponse != null
				&& vehicleResponse.getOfferFilterTemplate() != null) {
			offerFilterTemplate = vehicleResponse.getOfferFilterTemplate();
		}

		if (offerFilterTemplate != null) {
			setSummary(composite, offerFilterTemplate);
			setSuppliers(composite, offerFilterTemplate);
			//setStations(composite, offerFilterTemplate);
			setServiceCatalogs(composite, offerFilterTemplate);
			setBodyStyles(composite, offerFilterTemplate);
			setInclusives(composite, offerFilterTemplate);
			setCarTypes(composite, offerFilterTemplate);
			setPaymentTypes(composite, offerFilterTemplate);
			setAutCarTypes(composite, offerFilterTemplate);
		}

		composite.pack();
		return composite;
	}


	private void setAutCarTypes(Composite composite, OfferFilterTemplate offerFilterTemplate) {
		final Group groupResult = new Group(composite, SWT.TITLE);
		groupResult.setText("Autom types:");
		groupResult.setLayout(new GridLayout(5, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(true, true).applyTo(groupResult);
		
		List<FilterObject>  carTypes =   offerFilterTemplate.getCarProperties();
		
		defineAutFilter(groupResult, carTypes);
		
	}

	private void setSuppliers(Composite composite,
			OfferFilterTemplate offerFilterTemplate) {
		final Group groupResult = new Group(composite, SWT.TITLE);
		groupResult.setText("Suppliers:");
		groupResult.setLayout(new GridLayout(5, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(true, true).applyTo(groupResult);
		
		List<FilterObject>  filter =   offerFilterTemplate.getSuppliers();
		
		defineSupplierFilter(groupResult, filter);
		
	}

	private void setServiceCatalogs(Composite composite,
			OfferFilterTemplate offerFilterTemplate) {
		final Group groupResult = new Group(composite, SWT.TITLE);
		groupResult.setText("Service catalogs:");
		groupResult.setLayout(new GridLayout(5, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(true, true).applyTo(groupResult);
		
		List<FilterObject>  filter =   offerFilterTemplate.getServiceCatalogs();
		
		defineServiceCatalogFilter(groupResult, filter);
		
	}
	
	private void setPaymentTypes(Composite composite, OfferFilterTemplate offerFilterTemplate) {
		final Group groupResult = new Group(composite, SWT.TITLE);
		groupResult.setText("Payment types:");
		groupResult.setLayout(new GridLayout(5, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(true, true).applyTo(groupResult);
		
		List<FilterObject>  filter =   offerFilterTemplate.getPaymentTypes();
		
		definePaymentTypesFilter(groupResult, filter);
		
	}


	private void definePaymentTypesFilter(Group groupResult, List<FilterObject> filter) {
		for (FilterObject b : filter ) {

			String bb = b.getName()+ "("+b.getCount()+")";
			Label l = new Label(groupResult, SWT.NONE);
			l.setText(bb);
			
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
					.grab(true, false).span(4, 1).applyTo(l);

			Button select = new Button(groupResult, SWT.CHECK);
			select.setText("Select");
			paymentTypesButtons.put(b.getId(), select);
			

		}
		
	}

	private void setStations(Composite composite,
			OfferFilterTemplate offerFilterTemplate) {
		final Group groupResult = new Group(composite, SWT.TITLE);
		groupResult.setText("Stations:");
		groupResult.setLayout(new GridLayout(5, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(true, true).applyTo(groupResult);
		
		List<FilterObject>  filter =   offerFilterTemplate.getStations();
		
		defineFilter(groupResult, filter);
		
	}
	
	private void defineFilter(final Group groupResult, List<FilterObject> filter) {
		for (FilterObject b : filter ) {

			String bb = b.getName()+ "("+b.getCount()+")";
			Label l = new Label(groupResult, SWT.NONE);
			l.setText(bb);
			
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
					.grab(true, false).span(4, 1).applyTo(l);

			Button select = new Button(groupResult, SWT.CHECK);
			select.setText("Select");
			carTypesButtons.put(b.getId(), select);
			

		}
	}

	private void defineAutFilter(final Group groupResult, List<FilterObject> filter) {
		for (FilterObject b : filter ) {

			String bb = b.getName()+ "("+b.getCount()+")";
			Label l = new Label(groupResult, SWT.NONE);
			l.setText(bb);
			
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
					.grab(true, false).span(4, 1).applyTo(l);

			Button select = new Button(groupResult, SWT.CHECK);
			select.setText("Select");
			autCarTypesButtons.put(b.getId(), select);
			

		}
	}
	
	private void defineServiceCatalogFilter(final Group groupResult, List<FilterObject> filter) {
		for (FilterObject b : filter ) {

			String bb = b.getName()+ "("+b.getCount()+")";
			Label l = new Label(groupResult, SWT.NONE);
			l.setText(bb);
			
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
					.grab(true, false).span(4, 1).applyTo(l);

			Button select = new Button(groupResult, SWT.CHECK);
			select.setText("Select");
			serviceCatalogButtons.put(b.getId(), select);
			

		}
	}
	
	private void defineSupplierFilter(final Group groupResult, List<FilterObject> filter) {
		for (FilterObject b : filter ) {

			String bb = b.getName()+ "("+b.getCount()+")";
			Label l = new Label(groupResult, SWT.NONE);
			l.setText(bb);
			
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
					.grab(true, false).span(4, 1).applyTo(l);

			Button select = new Button(groupResult, SWT.CHECK);
			select.setText("Select");
			supplierButtons.put(b.getId(), select);
			

		}
	}
	
	private void setCarTypes(Composite composite,
			OfferFilterTemplate offerFilterTemplate) {
		
		final Group groupResult = new Group(composite, SWT.TITLE);
		groupResult.setText("Car types:");
		groupResult.setLayout(new GridLayout(5, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(true, true).applyTo(groupResult);
		
		List<FilterObject>  carTypes =   offerFilterTemplate.getCarTypes();
		
		defineFilter(groupResult, carTypes);
		
	}

	private void setSummary(Composite composite,
			OfferFilterTemplate offerFilterTemplate) {

		final Group groupResult = new Group(composite, SWT.TITLE);
		groupResult.setText("Summary:");
		groupResult.setLayout(new GridLayout(5, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(true, true).applyTo(groupResult);

		if (offerFilterTemplate != null) {

			// row1
			if ( offerFilterTemplate.getMinPrice() != null) {
			Label l1 = new Label(groupResult, SWT.NONE);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
					.grab(true, false).span(2, 1).applyTo(l1);
			l1.setText("Min. Price :"
					+ offerFilterTemplate.getMinPrice().toString());

			Label l2 = new Label(groupResult, SWT.NONE);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
					.grab(true, false).span(2, 1).applyTo(l2);
			l2.setText("Max. Price :"
					+ offerFilterTemplate.getMaxPrice().toString());

			Label l3 = new Label(groupResult, SWT.NONE);

			// row2
			Label lf = new Label(groupResult, SWT.NONE);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
					.grab(true, false).span(5, 1).applyTo(lf);
			lf.setText("Filter :");

			// row3
			new Label(groupResult, SWT.NONE).setText("Min. Price");
			Text value = new Text(groupResult, SWT.BORDER | SWT.SINGLE);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
					.grab(true, false).span(1, 1).applyTo(value);
			value.setText(offerFilterTemplate.getMinPrice().getDecimalAmount()
					.toString());
			min = value;

			new Label(groupResult, SWT.NONE).setText("Max. Price");
			value = new Text(groupResult, SWT.BORDER | SWT.SINGLE);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
					.grab(true, false).span(1, 1).applyTo(value);
			value.setText(offerFilterTemplate.getMaxPrice().getDecimalAmount()
					.toString());
			max = value;

			selectMinMax = new Button(groupResult, SWT.CHECK);
			selectMinMax.setText("Select");
			
			FilterObject  air = offerFilterTemplate.getAircondition();
			aircondition = new Button(groupResult, SWT.CHECK);
			aircondition.setText(air.getName()+ "("+air.getCount()+ ")");
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
			.grab(true, false).span(5, 1).applyTo(aircondition);
			
			
			FilterObject  aut = offerFilterTemplate.getAutomatic();
			automatic  = new Button(groupResult, SWT.CHECK);
			automatic.setText(aut.getName()+ "("+aut.getCount()+ ")");
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
			.grab(true, false).span(5, 1).applyTo(automatic);
			}

		}

	}

	private void setBodyStyles(Composite composite,
			OfferFilterTemplate offerFilterTemplate) {

		final Group groupResult = new Group(composite, SWT.TITLE);
		groupResult.setText("Body Styles:");
		groupResult.setLayout(new GridLayout(5, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(true, true).applyTo(groupResult);
		
		List<FilterObject>  bodyStyles =   offerFilterTemplate.getBodyStyles();
		
		for (FilterObject b : bodyStyles ) {

			String bb = b.getName()+ "("+b.getCount()+")";
			Label l = new Label(groupResult, SWT.NONE);
			l.setText(bb);
			
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
					.grab(true, false).span(4, 1).applyTo(l);

			Button select = new Button(groupResult, SWT.CHECK);
			select.setText("Select");
			bodyStylesButtons.put(b.getId(), select);
			

		}

	}
	
	private void setInclusives(Composite composite,
			OfferFilterTemplate offerFilterTemplate) {

		final Group groupResult = new Group(composite, SWT.TITLE);
		groupResult.setText("Inclusives:");
		groupResult.setLayout(new GridLayout(5, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(true, true).applyTo(groupResult);
		
		Map<String, List<FilterObject>>  inclusives =   offerFilterTemplate.getInclusivesFilter();
		
		for ( String key : inclusives.keySet()) {
			
			List<FilterObject> items = inclusives.get(key);
			for (FilterObject b : items ) {
				String bb = key+ " : "+b.getName()+ "("+b.getCount()+")";
				Label l = new Label(groupResult, SWT.NONE);
				long id = b.getId();
				l.setText(bb);
				GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(true, false).span(4, 1).applyTo(l);
				Button select = new Button(groupResult, SWT.CHECK);
				select.setText("Select");
				String[] parts = b.getName().split(",");
				inclusiveButtons.put(id, select);
			}
			
		}

	}
	

	@Override
	protected void okPressed() {
//		if (selectMinMax.getSelection()) {
//			vehicleRequestFilter.setMinPrice(BigDecimal.valueOf(Double.valueOf(min.getText())));
//			vehicleRequestFilter.setMaxPrice(BigDecimal.valueOf(Double.valueOf(max
//					.getText())));
//		}
//		
//		List<Long> bodyStyles = new ArrayList<Long>();
//		
		for ( Long id : bodyStylesButtons.keySet() ) {
			Button select = bodyStylesButtons.get(id);
			if ( select.getSelection()) {
				vehicleRequestFilter.getBodyStyles().add(id);
			}
		}
		
		for ( Long id : serviceCatalogButtons.keySet() ) {
			Button select = serviceCatalogButtons.get(id);
			if ( select.getSelection()) {
				vehicleRequestFilter.getServiceCatalogs().add(id);
			}
		}
		
		
//		Long[] ll = bodyStyles.toArray(new Long[0]);
//		vehicleRequestFilter.setBodyStyles(ll);
//		
//		vehicleRequestFilter.setAircondition(aircondition.getSelection());
//
//		vehicleRequestFilter.setAutomatic(automatic.getSelection());
		
		List<Long> incl = new ArrayList<Long>();
		
		for ( Long id : inclusiveButtons.keySet() ) {
			Button select = inclusiveButtons.get(id);
			if ( select.getSelection()) {
				incl.add(id);
			}
		}
		vehicleRequestFilter.getInclusives().addAll(incl);


		for ( Long id : carTypesButtons.keySet() ) {
			Button select = carTypesButtons.get(id);
			if ( select.getSelection()) {
				vehicleRequestFilter.getCarTypes().add(id);
			}
		}

		for ( Long id : supplierButtons.keySet() ) {
			Button select = supplierButtons.get(id);
			if ( select.getSelection()) {
				vehicleRequestFilter.getSuppliers().add(id);
			}
		}
		
		for ( Long id : paymentTypesButtons.keySet() ) {
			Button select = paymentTypesButtons.get(id);
			if ( select.getSelection()) {
				vehicleRequestFilter.getPaymentTypes().add(id);
			}
		}

		for ( Long id : autCarTypesButtons.keySet() ) {
			Button select = autCarTypesButtons.get(id);
			if ( select.getSelection()) {
				vehicleRequestFilter.getCarProperties().add(id);
			}
		}
		
		super.okPressed();
	}

	public VehicleRequestFilter getVehicleRequestFilter() {
		return vehicleRequestFilter;
	}

}
