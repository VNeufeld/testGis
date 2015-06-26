package com.dev.gis.app.taskmanager.SunnyCarsView;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.VehicleHttpService;
import com.dev.gis.connector.sunny.Hit;
import com.dev.gis.connector.sunny.Station;
import com.dev.gis.connector.sunny.StationResponse;
import com.dev.gis.task.execution.api.SunnyModelProvider;

public class SelectDropoffStationDialog extends Dialog {

	private TableViewer tableStations;
	private final String offerId;
	
	private Station selectedStation;


	protected SelectDropoffStationDialog(Shell parentShell, String offerId) {
		super(parentShell);
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
		location.setText("PMI");
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(false, false).span(1, 1).hint(100, 16).applyTo(location);
		
		
		final Button buttonGetPickup = new Button(groupStamp, SWT.PUSH | SWT.LEFT);
		buttonGetPickup.setText("Start getDropoffStations");
		buttonGetPickup.addSelectionListener(new DropoffStationsServiceListener(parent, c, location, puStation));

		final Group groupStations = new Group(composite, SWT.TITLE);
		groupStations.setText("Stations:");
		groupStations.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, true).applyTo(groupStations);
		
		createTableStaions(groupStations);
		
		return composite;
	}
	
	private void createTableStaions(Composite parent) {
		tableStations = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL // | SWT.CHECK
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, tableStations);
		final Table table = tableStations.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		tableStations.setContentProvider(new ArrayContentProvider());
		

		// define layout for the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 1;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		tableStations.getControl().setLayoutData(gridData);
		
		tableStations.addDoubleClickListener(new DoubleClickListener(this));

		
	}
	
	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Name", "Id", "Class" };
		int[] bounds = { 200, 100, 100 };

		// first column is for the first name
		TableViewerColumn col = createTableViewerColumn(viewer,titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Station o = (Station) element;
				return o.getIdentifier() ;
			}
		});

		// second column is supplierId
		col = createTableViewerColumn(viewer,titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Station o = (Station) element;
				return ""+o.getId();
			}
		});

		col = createTableViewerColumn(viewer,titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Station o = (Station) element;
				return o.getSupplierId() + ":"+ o.getSupplierGroupId();
			}
		});
		
	}
	private TableViewerColumn createTableViewerColumn(TableViewer table, String title, int bound,
			final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(
				table, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}
	
	protected class DropoffStationsServiceListener implements SelectionListener{
		private final Composite parent;
		private final Combo type;
		private final Text location;
		private final Text puStation;
	
		public DropoffStationsServiceListener(Composite parent, Combo c, Text location, Text puStation) {
			this.parent = parent;
			this.type = c;
			this.location = location;
			this.puStation = puStation;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			VehicleHttpService service = serviceFactory
					.getVehicleJoiService();

			StationResponse response = service.getDropOffStations(type.getSelectionIndex(),location.getText(), offerId, puStation.getText());
			
			SunnyModelProvider.INSTANCE.updateDropoffStations(response);
			tableStations.setInput(SunnyModelProvider.INSTANCE.getDropoffStations());
			tableStations.refresh();
			
			
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
	
	void closeDialog(Station st) {
		this.selectedStation = st;
		okPressed();
	}
	
	private static class DoubleClickListener implements IDoubleClickListener {
		SelectDropoffStationDialog parent = null;

		public DoubleClickListener(
				SelectDropoffStationDialog selectPickupStationDialog) {
			// TODO Auto-generated constructor stub
			parent = selectPickupStationDialog;
		}

		@Override
		public void doubleClick(DoubleClickEvent event) {

			TableViewer viewer = (TableViewer) event.getViewer();
			IStructuredSelection thisSelection = (IStructuredSelection) event
					.getSelection();
			Object selectedNode = thisSelection.getFirstElement();
			
			Station st = (Station) selectedNode;
			
			System.out.println("selectedNode " + st);
			
			parent.closeDialog(st);


	        

		}


	}
	public Station getSelectedStation() {
		return selectedStation;
	}
	


}
