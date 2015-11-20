package com.dev.gis.app.taskmanager.SunnyCarsView;

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

import com.dev.gis.app.model.IStationService;
import com.dev.gis.app.model.StationModel;
import com.dev.gis.app.view.dialogs.StationListTable;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.connector.api.SunnyModelProvider;

public class SelectPickupStationDialog extends Dialog {

	private final String offerId;
	
	private StationModel selectedStation;
	
	private StationListTable stationListTable;
	
	private final IStationService stationService;


	public SelectPickupStationDialog(Shell parentShell, String offerId,IStationService stationService ) {
		super(parentShell);
		this.stationService = stationService;
		this.offerId = offerId;
	    setShellStyle(getShellStyle() | SWT.RESIZE);
		
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.getShell().setText("Get PickupStation : Select City Or Airport");
		
		composite.setLayout(new GridLayout(1, false));

		final Group groupStamp = new Group(composite, SWT.TITLE);
		groupStamp.setText("Inputs:");
		groupStamp.setLayout(new GridLayout(3, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(false, false).applyTo(groupStamp);
		
		
		final Combo c = new Combo(groupStamp, SWT.READ_ONLY);
		String items[] = { "Airport", " City Id " };
		c.setItems(items);
		c.select(0);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(false, false).span(1, 1).hint(100, 16).applyTo(c);
		
		new Label(groupStamp, SWT.NONE).setText("Location ");
		
		final Text location = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		
		location.setText("PMI");
		
		if ( SunnyModelProvider.INSTANCE.cityId > 0) {
			location.setText(String.valueOf(SunnyModelProvider.INSTANCE.cityId));
			c.select(1);
		}
		else {
			location.setText(SunnyModelProvider.INSTANCE.airport);
			c.select(0);
		}
		
		
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(false, false).span(1, 1).hint(100, 16).applyTo(location);
		
		
		PickupStationsServiceListener pp = new PickupStationsServiceListener( c, location,this.offerId, stationService);
		
		new ButtonControl(groupStamp, "Start getickupStations", pp);

		final Group groupStations = new Group(composite, SWT.TITLE);
		groupStations.setText("Stations:");
		groupStations.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, true).applyTo(groupStations);
		
		DoubleClickListener dbl = new DoubleClickListener(this);
		stationListTable = new StationListTable(null, groupStations, dbl);
		
		return composite;
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
		SelectPickupStationDialog parent = null;

		public DoubleClickListener(
				SelectPickupStationDialog selectPickupStationDialog) {
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
	

	protected class PickupStationsServiceListener implements SelectionListener{
		private Combo c;
		private String offerId;
		private Text location;
		private IStationService stationService;
		
	
		public PickupStationsServiceListener(Combo c,  Text location, String offerId, IStationService stationService) {
			this.c = c;
			this.location = location;
			this.offerId = offerId;
			this.stationService = stationService;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			int type = c.getSelectionIndex();
			String search = location.getText();
			
			List<StationModel> stations = stationService.executeService(type, search, offerId);
			
			stationListTable.update(stations);
			
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			
		}


	}

}
