package com.dev.gis.app.taskmanager.clubMobilView;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.model.AdacGetStationService;
import com.dev.gis.app.model.IStationService;
import com.dev.gis.app.model.StationModel;
import com.dev.gis.app.view.dialogs.StationListTable;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.joi.protocol.Station;
import com.dev.gis.connector.joi.protocol.StationResponse;

public class SelectStationDialog extends Dialog {

	private StationModel selectedStation;
	
	private StationListTable stationListTable;
	

	public SelectStationDialog(Shell parentShell) {
		super(parentShell);
	    setShellStyle(getShellStyle() | SWT.RESIZE);
		
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.getShell().setText("Get CM Stations : ");
		
		composite.setLayout(new GridLayout(1, false));

		final Group groupStamp = new Group(composite, SWT.TITLE);
		groupStamp.setText("Inputs:");
		groupStamp.setLayout(new GridLayout(3, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(false, false).applyTo(groupStamp);
		
		
		final Combo c = new Combo(groupStamp, SWT.READ_ONLY);
		String items[] = { "Airport", " City Id " };
		c.setItems(items);
		c.select(1);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(false, false).span(1, 1).hint(100, 16).applyTo(c);
		
		new Label(groupStamp, SWT.NONE).setText("Location ");
		
		final Text location = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		
		location.setText("");
		
		long cityId = getModelCityId();
		String airport = getModelAirport();
		if ( cityId > 0) {
			location.setText(String.valueOf(cityId));
			c.select(1);
		}
		else if ( airport != null){
			location.setText(airport);
			c.select(0);
		}

		new Label(groupStamp, SWT.NONE).setText("Search ");
		final Text searchText = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);

		
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(false, false).span(1, 1).hint(100, 16).applyTo(location);
		
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
		.grab(false, false).span(1, 1).hint(100, 16).applyTo(searchText);
		
		StationsServiceListener pp = new StationsServiceListener(location,c, searchText);
		
		new ButtonControl(groupStamp, "Start get Stations", pp);

		final Group groupStations = new Group(composite, SWT.TITLE);
		groupStations.setText("Stations:");
		groupStations.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, true).applyTo(groupStations);
		
		DoubleClickListener dbl = new DoubleClickListener(this);
		stationListTable = new StationListTable(null, groupStations, dbl);
		
		return composite;
	}
	
	protected String getModelAirport() {
		return AdacModelProvider.INSTANCE.airport;
	}

	protected long getModelCityId() {
		return AdacModelProvider.INSTANCE.cityId;
	}
	
	@Override
	protected void okPressed() {
		super.okPressed();
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
	}
	
	void closeDialog(StationModel st) {
		this.selectedStation = st;
		okPressed();
	}
	
	private static class DoubleClickListener implements IDoubleClickListener {
		SelectStationDialog parent = null;

		public DoubleClickListener(
				SelectStationDialog selectPickupStationDialog) {
			parent = selectPickupStationDialog;
		}

		@Override
		public void doubleClick(DoubleClickEvent event) {

			TableViewer viewer = (TableViewer) event.getViewer();
			IStructuredSelection thisSelection = (IStructuredSelection) event
					.getSelection();
			Object selectedNode = thisSelection.getFirstElement();
			StationModel st = (StationModel) selectedNode;
			parent.closeDialog(st);

		}
	}
	public StationModel getSelectedStation() {
		return selectedStation;
	}
	

	protected class StationsServiceListener implements SelectionListener{
		private Text location;
		private final Combo locationTypeCombo;
		private final Text searchTextCtl;
	
		public StationsServiceListener(Text location, final Combo c, final Text searchText) {
			this.location = location;
			this.locationTypeCombo = c;
			this.searchTextCtl = searchText;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			final long operator = ClubMobilModelProvider.INSTANCE.operatorId;
			
			int type = locationTypeCombo.getSelectionIndex();
			String locationId = location.getText();
			if ( type == 0)
				type = 6;
			else
				type = 2;

			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
			

			String searchText = searchTextCtl.getText();
			StationResponse response  =service.getStations(operator,type,locationId, searchText);
			List<StationModel> stations = new ArrayList<StationModel>();
			
			for ( Station station : response.getStations()) {
				StationModel stationModel = new StationModel(station.getId(), station.getStationName());
				
				stationModel.setSupplier(station.getSupplierId() + ":"+ station.getSupplierGroupId());
				stationModel.setSupplierId(station.getSupplierId());
				stationModel.setStation(station);
				stations.add(stationModel);
			}

			stationListTable.update(stations);
			
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			
		}


	}

}
