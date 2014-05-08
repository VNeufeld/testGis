package com.dev.gis.app.taskmanager.offerDetailView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.app.taskmanager.bookingView.BookingView;
import com.dev.gis.connector.joi.protocol.Extra;
import com.dev.gis.connector.joi.protocol.ExtraResponse;
import com.dev.gis.connector.joi.protocol.Offer;
import com.dev.gis.connector.joi.protocol.VehicleResponse;
import com.dev.gis.connector.joi.protocol.VehicleResult;
import com.dev.gis.task.execution.api.ITaskResult;
import com.dev.gis.task.execution.api.JoiVehicleConnector;
import com.dev.gis.task.execution.api.ModelProvider;
import com.dev.gis.task.execution.api.OfferDo;

public class OfferDetailView extends TaskViewAbstract {
	public static final String ID = "com.dev.gis.app.view.OfferDetailView";
	private StringFieldEditor city;
	private StringFieldEditor airport;
	
	Calendar checkInDate = Calendar.getInstance();
	Calendar dropOffDate = Calendar.getInstance();

	private Text textOfferLink = null;

	private Text priceText = null;

	private Text carDescription = null;
	
	private TableViewer tableExtras;
	
	private Offer selectedOffer = null;
	

	@Override
	public void createPartControl(Composite parent) {
		
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		
		Composite offerGroup = createOfferGroup(composite);
		
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(offerGroup);


		final Group groupButtons = new Group(composite, SWT.TITLE);
		groupButtons.setText("Offer:");
		groupButtons.setLayout(new GridLayout(4, true));
		groupButtons.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		final Button buttonGetOffer = new Button(groupButtons, SWT.PUSH | SWT.LEFT);
		buttonGetOffer.setText("Get PickupStations");

		final Button buttonBooking = new Button(groupButtons, SWT.PUSH | SWT.LEFT);
		buttonBooking.setText("Booking");
		
		buttonBooking.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				List<Extra> extras = getSelectedExtras();
				
				BookingView.updateView(selectedOffer,extras);
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
				
			}
		});

		final Button buttonExtras = new Button(groupButtons, SWT.PUSH | SWT.LEFT);
		buttonExtras.setText("Get Extras");
		
		buttonExtras.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				
				
				
				ExtraResponse response = JoiVehicleConnector.getExtras(selectedOffer);
				changeModel(response);
				tableExtras.refresh();
				
				
			}


			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
				
			}
		});
		
		createTableExtras(composite);

	}

	private Composite createOfferGroup(Composite composite) {
		
		final Group groupStamp = new Group(composite, SWT.TITLE);
		groupStamp.setText("Offer:");
		//groupStamp.setLayout(new GridLayout(2, true));
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(true).applyTo(groupStamp);

		GridData gdFirm = new GridData();
		gdFirm.grabExcessHorizontalSpace = true;
		gdFirm.horizontalAlignment = SWT.FILL;
		gdFirm.horizontalSpan = 2;

		Label cityLabel = new Label(groupStamp, SWT.NONE);
		cityLabel.setText("Offer");
		textOfferLink = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);

		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(false, false).applyTo(textOfferLink);
		
		//cityText.setLayoutData(gdFirm);
		
		Label priceLabel = new Label(groupStamp, SWT.NONE);
		priceLabel.setText("Preis:");
		priceText = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		//priceText.setLayoutData(gdFirm);
		
		Label carDescriptionLabel = new Label(groupStamp, SWT.NONE);
		carDescriptionLabel.setText("Fahrzeug:");
		carDescription = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		//carDescription.setLayoutData(new GridData());
		
		return groupStamp;
	}
	
	private List<Extra> getSelectedExtras() {
		List<Extra> extras = new ArrayList<Extra>();
		TableItem[] selection = tableExtras.getTable().getSelection();
		if ( selection != null && selection.length > 0) {
			for ( TableItem item : selection) {
				extras.add((Extra)item.getData());
				
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
		textOfferLink.setText(vehicleResult.getOfferList().get(0).getBookLink().toString());
		priceText.setText(vehicleResult.getOfferList().get(0).getPrice().getAmount());
		carDescription.setText(vehicleResult.getVehicle().getManufacturer() + " Group : "+vehicleResult.getVehicle().getCategoryId());
		
		this.selectedOffer = vehicleResult.getOfferList().get(0);
		
//		vehicleResult.getSupplierId();
//		vehicleResult.getPickUpStationId();
//		vehicleResult.getSupplierId();
//		vehicleResult.getServiceCatalogCode();
		
	}
	
	private void createTableExtras(Composite parent) {
		tableExtras = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
	        | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
	    createColumns(parent, tableExtras);
	    final Table table = tableExtras.getTable();
	    table.setHeaderVisible(true);
	    table.setLinesVisible(true);

	    tableExtras.setContentProvider(new ArrayContentProvider());
	    // get the content for the viewer, setInput will call getElements in the
	    // contentProvider
	    //viewer.setInput(ModelProvider.INSTANCE.getOffers());
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
	        return o.getName() + "("+o.getCode()+")";
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

	  private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		    final TableViewerColumn viewerColumn = new TableViewerColumn(tableExtras,
		        SWT.NONE);
		    final TableColumn column = viewerColumn.getColumn();
		    column.setText(title);
		    column.setWidth(bound);
		    column.setResizable(true);
		    column.setMoveable(true);
		    return viewerColumn;
		  }

}
