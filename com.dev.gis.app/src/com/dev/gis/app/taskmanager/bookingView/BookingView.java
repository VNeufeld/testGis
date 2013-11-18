package com.dev.gis.app.taskmanager.bookingView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import com.bpcs.mdcars.protocol.Offer;
import com.dev.gis.app.Components;
import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.app.taskmanager.offerDetailView.OfferDetailView;
import com.dev.gis.app.taskmanager.offerDetailView.OfferViewUpdater;
import com.dev.gis.connector.joi.protocol.DayAndHour;
import com.dev.gis.connector.joi.protocol.Location;
import com.dev.gis.connector.joi.protocol.Module;
import com.dev.gis.connector.joi.protocol.TravelInformation;
import com.dev.gis.connector.joi.protocol.VehicleRequest;
import com.dev.gis.connector.joi.protocol.VehicleResponse;
import com.dev.gis.connector.joi.protocol.VehicleResult;
import com.dev.gis.db.api.IStationDao;
import com.dev.gis.task.execution.api.ITaskResult;
import com.dev.gis.task.execution.api.JoiVehicleConnector;
import com.dev.gis.task.execution.api.ModelProvider;

public class BookingView extends TaskViewAbstract {
	
	private static Logger logger = Logger.getLogger(BookingView.class);
	
	private static int instanceNum = 1;

	public static final String ID = "com.dev.gis.app.view.BookingView";
	private StringFieldEditor city;
	private StringFieldEditor airport;
	
	Calendar checkInDate = Calendar.getInstance();
	Calendar dropOffDate = Calendar.getInstance();

	private Text cityText = null;

	private Text priceText = null;

	private Text carDescription = null;
	
	private IWorkbenchAction exitAction;

	@Override
	public void createPartControl(Composite parent) {
		
//		 exitAction = ActionFactory.QUIT.create(this);
//	        register(exitAction);
//	        
	        
		//exitAction = new IWorkbenchAction(viewer, parent.getShell());

		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		
		final Group groupDriver = new Group(composite, SWT.TITLE);
		groupDriver.setText("Driver:");
		groupDriver.setLayout(new GridLayout(4, true));
		groupDriver.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		

		final Group groupStamp = new Group(composite, SWT.TITLE);
		groupStamp.setText("Customer:");
		groupStamp.setLayout(new GridLayout(4, true));
		groupStamp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		GridData gdFirm = new GridData();
		gdFirm.grabExcessHorizontalSpace = true;
		gdFirm.horizontalAlignment = SWT.FILL;
		gdFirm.horizontalSpan = 3;

		Label cityLabel = new Label(groupStamp, SWT.NONE);
		cityLabel.setText("Booking");
		cityText = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		cityText.setLayoutData(gdFirm);
		
		Label priceLabel = new Label(groupStamp, SWT.NONE);
		priceLabel.setText("Preis:");
		priceText = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		priceText.setLayoutData(gdFirm);
		
		Label carDescriptionLabel = new Label(groupStamp, SWT.NONE);
		carDescriptionLabel.setText("Fahrzeug:");
		carDescription = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		carDescription.setLayoutData(new GridData());

		final Group groupButtons = new Group(composite, SWT.TITLE);
		groupButtons.setText("Offer:");
		groupButtons.setLayout(new GridLayout(4, true));
		groupButtons.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Button buttonVerify = new Button(groupButtons, SWT.PUSH | SWT.LEFT);
		buttonVerify.setText("Verify");
		buttonVerify.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// createBookingRequest - Driver, Customer
				//StartVerify
				BookingView.updateView();
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
				
			}
		});
	}
	
	private void initDates() {
		
		checkInDate.set(Calendar.HOUR_OF_DAY, 11);
		checkInDate.set(Calendar.MINUTE, 30);
		checkInDate.set(Calendar.SECOND, 0);

		dropOffDate.set(Calendar.HOUR_OF_DAY, 18);
		dropOffDate.set(Calendar.MINUTE, 0);
		dropOffDate.set(Calendar.SECOND, 0);	
	}

	private void addDateControl(String label,Composite composite,final Calendar resultDate ) {
		GridData gdDate = new GridData();
		gdDate.grabExcessHorizontalSpace = true;
		gdDate.horizontalAlignment = SWT.FILL;

		new Label(composite, SWT.NONE).setText(label);

		final DateTime dateTime = new DateTime(composite, SWT.DATE);
		dateTime.setLayoutData(gdDate);
		
		
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;

		final DateTime dateTime2 = new DateTime(composite, SWT.TIME);
		dateTime2.setLayoutData(gridData);
		
		
		dateTime2.setHours(resultDate.get(Calendar.HOUR_OF_DAY));
		dateTime2.setMinutes(resultDate.get(Calendar.MINUTE));
		dateTime2.setSeconds(resultDate.get(Calendar.SECOND));

		dateTime.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				resultDate.set(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		
		dateTime2.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				resultDate.set(Calendar.HOUR_OF_DAY, dateTime2.getHours());
				resultDate.set(Calendar.MINUTE, dateTime2.getMinutes());
				resultDate.set(Calendar.SECOND, dateTime2.getSeconds());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		
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
		cityText.setText(vehicleResult.getOfferList().get(0).getBookLink().toString());
		priceText.setText(vehicleResult.getOfferList().get(0).getPrice().getAmount());
		carDescription.setText(vehicleResult.getVehicle().getManufacturer() + " Group : "+vehicleResult.getVehicle().getCategoryId());
		
//		vehicleResult.getSupplierId();
//		vehicleResult.getPickUpStationId();
//		vehicleResult.getSupplierId();
//		vehicleResult.getServiceCatalogCode();
		
	}
	
	public static void  updateView() {
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					logger.info(" showResult : run instanceNum = "+instanceNum );
					// Show protocol, show results
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					BookingView viewPart =  (BookingView)wp.showView(
							BookingView.ID, 
							Integer.toString(instanceNum), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					//viewPart.showOffer(offer.getModel());
					
					instanceNum++;
					
					logger.info(" add view :"+viewPart.getTitle());
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

		
	}
	

}
