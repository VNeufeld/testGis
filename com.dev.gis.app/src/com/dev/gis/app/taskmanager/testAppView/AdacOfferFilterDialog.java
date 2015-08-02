package com.dev.gis.app.taskmanager.testAppView;

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

import com.dev.gis.connector.api.ModelProvider;
import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.joi.protocol.FilterObject;
import com.dev.gis.connector.joi.protocol.ObjectValuePair;
import com.dev.gis.connector.joi.protocol.OfferFilterTemplate;
import com.dev.gis.connector.joi.protocol.VehicleRequestFilter;
import com.dev.gis.connector.joi.protocol.VehicleResponse;

public class AdacOfferFilterDialog extends Dialog {

	private final VehicleRequestFilter vehicleRequestFilter = new VehicleRequestFilter();

	private Button selectMinMax;
	private Button aircondition;
	private Button automatic;
	
	Map<Long,Button> bodyStylesButtons = new HashMap<Long,Button>();
	Map<String,Button> inclusiveButtons = new HashMap<String,Button>();

	private Text min, max;

	public AdacOfferFilterDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);

	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.getShell().setText("Offer Filter");

		OfferFilterTemplate offerFilterTemplate = null;
		VehicleResponse vehicleResponse = ModelProvider.INSTANCE.getVehicleResponse();
		if (vehicleResponse != null
				&& vehicleResponse.getOfferFilterTemplate() != null) {
			offerFilterTemplate = vehicleResponse.getOfferFilterTemplate();
		}

		if (offerFilterTemplate != null) {
			setSummary(composite, offerFilterTemplate);
			setBodyStyles(composite, offerFilterTemplate);
			setInclusives(composite, offerFilterTemplate);
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
				l.setText(bb);
				GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(true, false).span(4, 1).applyTo(l);
				Button select = new Button(groupResult, SWT.CHECK);
				select.setText("Select");
				String[] parts = b.getName().split(",");
				if ( parts.length > 0) {
					//int id = Integer.valueOf(parts[0]);
					inclusiveButtons.put(parts[0], select);
				}
				
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
//		for ( Long id : bodyStylesButtons.keySet() ) {
//			Button select = bodyStylesButtons.get(id);
//			if ( select.getSelection()) {
//				bodyStyles.add(id);
//			}
//		}
//		Long[] ll = bodyStyles.toArray(new Long[0]);
//		vehicleRequestFilter.setBodyStyles(ll);
//		
//		vehicleRequestFilter.setAircondition(aircondition.getSelection());
//
//		vehicleRequestFilter.setAutomatic(automatic.getSelection());
		
		List<Long> incl = new ArrayList<Long>();
		
//		for ( Long id : inclusiveButtons.keySet() ) {
//			Button select = inclusiveButtons.get(id);
//			if ( select.getSelection()) {
//				incl.add(id);
//			}
//		}
//		Long[] inll = incl.toArray(new Long[0]);
//		vehicleRequestFilter.setInclusives(inll);
		
		
		super.okPressed();
	}

	public VehicleRequestFilter getVehicleRequestFilter() {
		return vehicleRequestFilter;
	}

}