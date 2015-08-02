package com.dev.gis.app.taskmanager.offerDetailView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.app.taskmanager.bookingView.BookingView;
import com.dev.gis.connector.api.ModelProvider;
import com.dev.gis.connector.api.OfferDo;
import com.dev.gis.connector.joi.protocol.Extra;
import com.dev.gis.connector.joi.protocol.ExtraResponse;
import com.dev.gis.connector.joi.protocol.Offer;
import com.dev.gis.connector.joi.protocol.TravelInformation;
import com.dev.gis.connector.joi.protocol.VehicleResult;
import com.dev.gis.task.execution.api.ITaskResult;
import com.dev.gis.task.execution.api.JoiVehicleConnector;

public class OfferDetailView extends TaskViewAbstract {
	public static final String ID = "com.dev.gis.app.view.OfferDetailView";

	Calendar checkInDate = Calendar.getInstance();
	Calendar dropOffDate = Calendar.getInstance();

	private Text textOfferLink = null;

	private Text priceText = null;

	private Text carDescription = null;

	private TableViewer tableExtras;

	private Offer selectedOffer = null;

	private TravelInformation travelInformation;

	private Text pickUpCityId;

	private Text dropOffCityId;

	private Text dropOffStationId;

	private Text pickupStationsResponse;

	private Text recalculateResponse;

	@Override
	public void createPartControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		// Offer - Group
		Composite offerGroup = createOfferGroup(composite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(offerGroup);


		Composite rbComp = createRequestButtons(composite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(rbComp);

		// Extras
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(addExtraGroup(composite));
		

		// Booking
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(addBookingButton(composite));

	}
	
	private Composite createOfferGroup(Composite composite) {

		final Group groupStamp = new Group(composite, SWT.TITLE);
		groupStamp.setText("Offer:");
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(groupStamp);

		Label cityLabel = new Label(groupStamp, SWT.NONE);
		cityLabel.setText("Offer");
		textOfferLink = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(textOfferLink);

		Label priceLabel = new Label(groupStamp, SWT.NONE);
		priceLabel.setText("Preis:");
		priceText = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);

		Label carDescriptionLabel = new Label(groupStamp, SWT.NONE);
		carDescriptionLabel.setText("Fahrzeug:");
		carDescription = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.FILL).hint(300, 16).grab(false, false).applyTo(carDescription);

		Label pickUpCityIdLabel = new Label(groupStamp, SWT.NONE);
		pickUpCityIdLabel.setText("PickUpCityId:");
		pickUpCityId = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);

		Label dropOffCityIdLabel = new Label(groupStamp, SWT.NONE);
		dropOffCityIdLabel.setText("DropOff CityId:");
		dropOffCityId = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);

		Label dropOffStation = new Label(groupStamp, SWT.NONE);
		dropOffStation.setText("DropOff StationId:");
		dropOffStationId = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);

		return groupStamp;
	}
	

	private Composite addExtraGroup(Composite composite) {
		final Group group = new Group(composite, SWT.TITLE);
		group.setText("Extras:");
		group.setLayout(new GridLayout(1, true));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		final Button buttonExtras = new Button(group, SWT.PUSH	| SWT.LEFT);
		buttonExtras.setText("Get Extras");

		buttonExtras.addSelectionListener(new AddGetExtrasListener());

		createTableExtras(group);
		
		return group;
	}
	
	private Composite addBookingButton(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(composite);
		
		final Button buttonBooking = new Button(composite, SWT.PUSH	| SWT.LEFT);
		buttonBooking.setText("Booking");

		buttonBooking.addSelectionListener(new AddBookingListener() );
		
		return composite;
	}
	

	
	protected class AddGetExtrasListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			//ExtraResponse response = JoiVehicleConnector.getExtras(selectedOffer);
			ExtraResponse response = JoiVehicleConnector.getExtrasDummy();
			changeModel(response);
			tableExtras.refresh();

		}

	}

	protected class AddBookingListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			List<Extra> extras = getSelectedExtras();
			BookingView.updateView(selectedOffer, extras);

		}

	}


	protected class AddRecalculateListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			recalculateResponse.setText("running....");
			
			if ( StringUtils.isNotEmpty(dropOffStationId.getText()) && StringUtils.isNumeric(dropOffStationId.getText()))
					travelInformation.getDropOffLocation().setStationId(Long.valueOf(dropOffStationId.getText()));
			//travelInformation.getDropOffLocation().setStationId(167956);420222

			String response = JoiVehicleConnector.recalculate(selectedOffer, travelInformation);
			recalculateResponse.setText(response);

		}

	}

	protected class AddPickupStationsListener extends AbstractListener{

	
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			pickupStationsResponse.setText("running....");
			String response = JoiVehicleConnector.getPickupStations(selectedOffer);
			pickupStationsResponse.setText(response);
		}

	}
	
	private Composite createRequestButtons(final Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(composite);
		
		final Button buttonRecalculate = new Button(composite, SWT.PUSH | SWT.LEFT);
		buttonRecalculate.setText("POST Recalculate");
		
		buttonRecalculate.addSelectionListener(new AddRecalculateListener());
		
		recalculateResponse = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(recalculateResponse);
		
		final Button buttonGetPickupStations = new Button(composite, SWT.PUSH | SWT.LEFT);
		buttonGetPickupStations.setText("Get PickupStations");
		buttonGetPickupStations.addSelectionListener(new AddPickupStationsListener());
				
		pickupStationsResponse = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(pickupStationsResponse);
		
		return composite;

		
	}


	private List<Extra> getSelectedExtras() {
		List<Extra> extras = new ArrayList<Extra>();
		TableItem[] selection = tableExtras.getTable().getSelection();
		System.out.println(" getSelectedExtras ");
		if (selection != null && selection.length > 0) {
			for (TableItem item : selection) {
				Extra e = (Extra) item.getData();
				extras.add(e);

				System.out.println(" selected extra " + e.getName());

			}
		}

		return extras;
	}

	protected void changeModel(ExtraResponse response) {
		ModelProvider.INSTANCE.updateExtras(response);
		tableExtras.setInput(ModelProvider.INSTANCE.getExtraDos());

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(ITaskResult result) {
		// TODO Auto-generated method stub

	}

	public void showOffer(VehicleResult vehicleResult) {
		textOfferLink.setText(vehicleResult.getOfferList().get(0).getBookLink()
				.toString());
		priceText.setText(vehicleResult.getOfferList().get(0).getPrice()
				.getAmount());
		carDescription.setText(vehicleResult.getVehicle().getManufacturer()
				+ " Group : " + vehicleResult.getVehicle().getCategoryId());

		this.selectedOffer = vehicleResult.getOfferList().get(0);

		// vehicleResult.getSupplierId();
		// vehicleResult.getPickUpStationId();
		// vehicleResult.getSupplierId();
		// vehicleResult.getServiceCatalogCode();

	}

	public void showOffer(OfferDo offerDo) {
		VehicleResult vehicleResult = offerDo.getModel();
		textOfferLink.setText(vehicleResult.getOfferList().get(0).getBookLink()
				.toString());
		priceText.setText(vehicleResult.getOfferList().get(0).getPrice()
				.getAmount());
		carDescription.setText(vehicleResult.getVehicle().getManufacturer()
				+ " Group : " + vehicleResult.getVehicle().getCategoryId());

		this.selectedOffer = vehicleResult.getOfferList().get(0);

		this.travelInformation = offerDo.getTravelInformation();

		this.travelInformation.setPickUpLocation(vehicleResult
				.getPickUpLocation());
		this.travelInformation.setDropOffLocation(vehicleResult
				.getDropOffLocation());

		pickUpCityId.setText(String.valueOf(this.travelInformation
				.getPickUpLocation().getCityId()));
		dropOffCityId.setText(String.valueOf(this.travelInformation
				.getDropOffLocation().getCityId()));

	}

	private void createTableExtras(Composite parent) {
		tableExtras = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL // | SWT.CHECK
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, tableExtras);
		final Table table = tableExtras.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		tableExtras.setContentProvider(new ArrayContentProvider());
		// get the content for the viewer, setInput will call getElements in the
		// contentProvider
		// viewer.setInput(ModelProvider.INSTANCE.getOffers());
		// make the selection available to other views
		getSite().setSelectionProvider(tableExtras);
		// set the sorter for the table

		// define layout for the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		tableExtras.getControl().setLayoutData(gridData);

	}

	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Name", "Code", "Preis" };
		int[] bounds = { 200, 100, 100 };

		// first column is for the first name
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Extra o = (Extra) element;
				return o.getName() + "(" + o.getCode() + ")";
			}
		});

		// second column is supplierId
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Extra o = (Extra) element;
				return String.valueOf(o.getId());
			}
		});

		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Extra o = (Extra) element;
				return o.getPrice().getAmount();
			}
		});

	}

	private TableViewerColumn createTableViewerColumn(String title, int bound,
			final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(
				tableExtras, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}
	
	

}
