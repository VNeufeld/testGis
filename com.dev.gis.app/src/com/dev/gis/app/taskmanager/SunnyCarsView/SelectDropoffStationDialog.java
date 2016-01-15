package com.dev.gis.app.taskmanager.SunnyCarsView;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.model.IStationService;
import com.dev.gis.app.model.StationModel;
import com.dev.gis.app.view.dialogs.StationListTable;
import com.dev.gis.connector.api.SunnyModelProvider;

public class SelectDropoffStationDialog extends Dialog {

	private final String offerId;
	
	private StationModel selectedStation;

	private StationListTable stationListTable;
	private final IStationService stationService;


	public SelectDropoffStationDialog(Shell parentShell, String offerId,IStationService stationService) {
		super(parentShell);
		this.offerId = offerId;
	    setShellStyle(getShellStyle() | SWT.RESIZE);
	    
	    this.stationService = stationService;
		
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.getShell().setText("Get Dropoff Station : Select City Or Airport");
		
		composite.setLayout(new GridLayout(1, false));

		final Group groupStamp = new Group(composite, SWT.TITLE);
		groupStamp.setText("Inputs:");
		groupStamp.setLayout(new GridLayout(3, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(false, false).applyTo(groupStamp);

		new Label(groupStamp, SWT.NONE).setText("PickupStation ");
		
		final Text puStation = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		puStation.setText("");
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(false, false).span(2, 1).hint(100, 16).applyTo(puStation);
		
		
		final Combo c = new Combo(groupStamp, SWT.READ_ONLY);
		String items[] = { "Airport", " City Id " };
		c.setItems(items);
		c.select(0);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(false, false).span(1, 1).hint(100, 16).applyTo(c);
		
		new Label(groupStamp, SWT.NONE).setText("Location ");
		
		final Text location = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		
		long cityId = getModelCityId();
		String airport = getModelAirport();
		long pickupStationId = getPickupStationId();
		
		if ( cityId > 0) {
			location.setText(String.valueOf(cityId));
			c.select(1);
		}
		else if ( airport != null){
			location.setText(airport);
			c.select(0);
		}
		
		if ( pickupStationId > 0 )
			puStation.setText(String.valueOf(pickupStationId)); 
		
		
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(false, false).span(1, 1).hint(100, 16).applyTo(location);
		
		
		final Button buttonGetPickup = new Button(groupStamp, SWT.PUSH | SWT.LEFT);
		buttonGetPickup.setText("Start getDropoffStations");
		buttonGetPickup.addSelectionListener(new DropoffStationsServiceListener(parent, c, location, puStation , stationService));

		final Group groupStations = new Group(composite, SWT.TITLE);
		groupStations.setText("Stations:");
		groupStations.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, true).applyTo(groupStations);
		
		//createTableStaions(groupStations);

		DoubleClickListener dbl = new DoubleClickListener(this);
		stationListTable = new StationListTable(null, groupStations, dbl);

		return composite;
	}
	
	protected String getModelAirport() {
		return SunnyModelProvider.INSTANCE.dropoffAirport;
	}

	protected long getModelCityId() {
		return SunnyModelProvider.INSTANCE.dropoffCityId;
	}
	
	protected long getPickupStationId() {
		return SunnyModelProvider.INSTANCE.selectedPickupStationId;
	}
	
	
	
	
	protected class DropoffStationsServiceListener implements SelectionListener{
		private final Composite parent;
		private final Combo type;
		private final Text location;
		private final Text puStation;
		
		private IStationService stationService;

		
	
		public DropoffStationsServiceListener(Composite parent, Combo c, Text location, Text puStation, IStationService stationService) {
			this.parent = parent;
			this.type = c;
			this.location = location;
			this.puStation = puStation;
			this.stationService = stationService;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
//			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
//			VehicleHttpService service = serviceFactory
//					.getVehicleJoiService();
//
//			StationResponse response = service.getDropOffStations(type.getSelectionIndex(),location.getText(), offerId, puStation.getText());
			
			List<StationModel> stations = stationService.getDropoffStationService(type.getSelectionIndex(),location.getText(), offerId, puStation.getText());
			
			
//			SunnyModelProvider.INSTANCE.updateDropoffStations(response);
//			tableStations.setInput(SunnyModelProvider.INSTANCE.getDropoffStations());
//			tableStations.refresh();
			
			stationListTable.update(stations);

		}

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			// TODO Auto-generated method stub
			
		}

	}
	@Override
	protected void okPressed() {
		super.okPressed();
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		//super.createButtonsForButtonBar(parent);
	}
	
	void closeDialog(StationModel st) {
		this.selectedStation = st;
		okPressed();
	}
	private static class DoubleClickListener implements IDoubleClickListener {
		SelectDropoffStationDialog parent = null;

		public DoubleClickListener(
				SelectDropoffStationDialog selectPickupStationDialog) {
			parent = selectPickupStationDialog;
		}

		@Override
		public void doubleClick(DoubleClickEvent event) {

			IStructuredSelection thisSelection = (IStructuredSelection) event
					.getSelection();
			Object selectedNode = thisSelection.getFirstElement();
			

			StationModel st = (StationModel) selectedNode;
			System.out.println("selectedNode " + st);
			parent.closeDialog(st);

		}


	}
	public StationModel getSelectedStation() {
		return selectedStation;
	}
	


}
