package com.dev.gis.app.view.dialogs;

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

import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.sunny.ObjectValuePair;
import com.dev.gis.connector.sunny.OfferFilter;
import com.dev.gis.connector.sunny.OfferFilterTemplate;
import com.dev.gis.connector.sunny.VehicleResponse;

public class OfferFilterDialog extends Dialog {

	private final OfferFilter offerFilter = new OfferFilter();

	private Button selectMinMax;
	private Button aircondition;
	private Button automatic;
	
	Map<Long,Button> bodyStylesButtons = new HashMap<Long,Button>();
	Map<Long,Button> inclusiveButtons = new HashMap<Long,Button>();
	Map<String,Button> stationLocTypesButtons = new HashMap<String,Button>();

	Map<Long,Button> transferCatgoriesButtons = new HashMap<Long,Button>();

	Map<Long,Button> carTypesButtons = new HashMap<Long,Button>();

	private Text min, max;

	public OfferFilterDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);

	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.getShell().setText("Offer Filter");

		OfferFilterTemplate offerFilterTemplate = null;
		VehicleResponse vehicleResponse = SunnyModelProvider.INSTANCE.currentResponse;
		if (vehicleResponse != null
				&& vehicleResponse.getOfferFilterTemplate() != null) {
			offerFilterTemplate = vehicleResponse.getOfferFilterTemplate();
		}

		if (offerFilterTemplate != null) {
			setSummary(composite, offerFilterTemplate);
			setBodyStyles(composite, offerFilterTemplate);
			setCarTypes(composite, offerFilterTemplate);
			setInclusives(composite, offerFilterTemplate);
			setStationLocType(composite, offerFilterTemplate);
			setTransferCategories(composite, offerFilterTemplate);
		}

		composite.pack();
		return composite;
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
			
			ObjectValuePair  air = offerFilterTemplate.getAircondition();
			aircondition = new Button(groupResult, SWT.CHECK);
			aircondition.setText(air.getName()+ "("+air.getCount()+ ")");
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
			.grab(true, false).span(5, 1).applyTo(aircondition);
			
			
			ObjectValuePair  aut = offerFilterTemplate.getAutomatic();
			automatic  = new Button(groupResult, SWT.CHECK);
			automatic.setText(aut.getName()+ "("+aut.getCount()+ ")");
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
			.grab(true, false).span(5, 1).applyTo(automatic);

		}

	}

	private void setBodyStyles(Composite composite,
			OfferFilterTemplate offerFilterTemplate) {

		final Group groupResult = new Group(composite, SWT.TITLE);
		groupResult.setText("Body Styles:");
		groupResult.setLayout(new GridLayout(5, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(true, true).applyTo(groupResult);
		
		List<ObjectValuePair>  bodyStyles =   offerFilterTemplate.getBodyStyles();
		
		for (ObjectValuePair b : bodyStyles ) {

			String bb = b.getName()+ "("+b.getCount()+")";
			Label l = new Label(groupResult, SWT.NONE);
			l.setText(bb);
			
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
					.grab(true, false).span(4, 1).applyTo(l);

			Button select = new Button(groupResult, SWT.CHECK);
			select.setText("Select");
			
			String[] parts = b.getName().split(",");
			if ( parts.length > 0) {
				int id = Integer.valueOf(parts[0]);
				bodyStylesButtons.put((long)id, select);
			}
			

		}

	}
	
	private void setCarTypes(Composite composite, OfferFilterTemplate offerFilterTemplate) {
		final Group groupResult = new Group(composite, SWT.TITLE);
		groupResult.setText("CarTypes:");
		groupResult.setLayout(new GridLayout(5, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(true, true).applyTo(groupResult);
		
		List<ObjectValuePair>  carTypes =   offerFilterTemplate.getCarTypes();
		
		for (ObjectValuePair b : carTypes ) {

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

	private void setTransferCategories(Composite composite, OfferFilterTemplate offerFilterTemplate) {
		final Group groupResult = new Group(composite, SWT.TITLE);
		groupResult.setText("Transfers:");
		groupResult.setLayout(new GridLayout(5, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(true, true).applyTo(groupResult);
		
		List<ObjectValuePair>  stationTransfers =   offerFilterTemplate.getStationTransfers();
		
		for (ObjectValuePair b : stationTransfers ) {

			String bb = b.getName()+ "("+b.getCount()+")";
			Label l = new Label(groupResult, SWT.NONE);
			l.setText(bb);
			
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
					.grab(true, false).span(4, 1).applyTo(l);

			Button select = new Button(groupResult, SWT.CHECK);
			select.setText("Select");
			
			String[] parts = b.getName().split(",");
			if ( parts.length > 0) {
				int id = Integer.valueOf(parts[0]);
				transferCatgoriesButtons.put((long)id, select);
			}
			

		}
		
	}

	private void setStationLocType(Composite composite,
			OfferFilterTemplate offerFilterTemplate) {

		final Group groupResult = new Group(composite, SWT.TITLE);
		groupResult.setText("Station Location Types:");
		groupResult.setLayout(new GridLayout(5, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(true, true).applyTo(groupResult);
		
		List<ObjectValuePair>  stationlocTypes =   offerFilterTemplate.getStationLocTypes();
		
		for (ObjectValuePair b : stationlocTypes ) {

			String bb = b.getName()+ "("+b.getCount()+")";
			Label l = new Label(groupResult, SWT.NONE);
			l.setText(bb);
			
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
					.grab(true, false).span(4, 1).applyTo(l);

			Button select = new Button(groupResult, SWT.CHECK);
			select.setText("Select");
			
			String[] parts = b.getName().split(",");
			if ( parts.length > 0) {
				stationLocTypesButtons.put(parts[0], select);
			}
			

		}

	}
	
	
	private void setInclusives(Composite composite,
			OfferFilterTemplate offerFilterTemplate) {

		final Group groupResult = new Group(composite, SWT.TITLE);
		groupResult.setText("Inclusives:");
		groupResult.setLayout(new GridLayout(5, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(true, true).applyTo(groupResult);
		
		Map<String, List<ObjectValuePair>>  inclusives =   offerFilterTemplate.getInclusives();
		
		for ( String key : inclusives.keySet()) {
			
			List<ObjectValuePair> items = inclusives.get(key);
			for (ObjectValuePair b : items ) {
				String bb = key+ " : "+b.getName()+ "("+b.getCount()+")";
				long id = b.getId();
				Label l = new Label(groupResult, SWT.NONE);
				l.setText(bb);
				GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(true, false).span(4, 1).applyTo(l);
				Button select = new Button(groupResult, SWT.CHECK);
				select.setText("Select");
				inclusiveButtons.put(id, select);
				
			}
			
		}

	}
	

	@Override
	protected void okPressed() {
		if (selectMinMax.getSelection()) {
			offerFilter.setMinPrice(BigDecimal.valueOf(Double.valueOf(min
					.getText())));
			offerFilter.setMaxPrice(BigDecimal.valueOf(Double.valueOf(max
					.getText())));
		}
		
		List<Long> bodyStyles = new ArrayList<Long>();
		
		for ( Long id : bodyStylesButtons.keySet() ) {
			Button select = bodyStylesButtons.get(id);
			if ( select.getSelection()) {
				bodyStyles.add(id);
			}
		}
		Long[] ll = bodyStyles.toArray(new Long[0]);
		offerFilter.setBodyStyles(ll);
		
		if ( aircondition.getSelection())
			offerFilter.setAircondition(true);
		else
			offerFilter.setAircondition(null);

		if ( automatic.getSelection())
			offerFilter.setAutomatic(true);
		else
			offerFilter.setAutomatic(null);
		
		for ( Long id : inclusiveButtons.keySet() ) {
			Button select = inclusiveButtons.get(id);
			if ( select.getSelection()) {
				offerFilter.getInclusives().add(id);
			}
		}

		List<String> stationLocTypes = new ArrayList<String>();
		for ( String code : stationLocTypesButtons.keySet() ) {
			Button select = stationLocTypesButtons.get(code);
			if ( select.getSelection()) {
				stationLocTypes.add(code);
			}
		}
		String[] llst = stationLocTypes.toArray(new String[0]);
		offerFilter.setStationLocTypeCodes(llst);
		
		List<Long> transferCategories = new ArrayList<Long>();
		
		for ( Long id : transferCatgoriesButtons.keySet() ) {
			Button select = transferCatgoriesButtons.get(id);
			if ( select.getSelection()) {
				transferCategories.add(id);
			}
		}
		Long[] trr = transferCategories.toArray(new Long[0]);
		offerFilter.setTransferCategories(trr);

		List<Long> carTypes = new ArrayList<Long>();

		for ( Long id : carTypesButtons.keySet() ) {
			Button select = carTypesButtons.get(id);
			if ( select.getSelection()) {
				carTypes.add(id);
			}
		}
		Long[] lll = carTypes.toArray(new Long[0]);
		offerFilter.setCarTypes(lll);

		super.okPressed();
	}

	public OfferFilter getOfferFilter() {
		return offerFilter;
	}

}
