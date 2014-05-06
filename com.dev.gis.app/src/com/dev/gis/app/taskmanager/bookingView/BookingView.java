package com.dev.gis.app.taskmanager.bookingView;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.connector.joi.protocol.BookingResponse;
import com.dev.gis.connector.joi.protocol.Extra;
import com.dev.gis.connector.joi.protocol.Offer;
import com.dev.gis.connector.joi.protocol.PaypalDoCheckoutResponse;
import com.dev.gis.connector.joi.protocol.PaypalSetCheckoutResponse;
import com.dev.gis.task.execution.api.ITaskResult;
import com.dev.gis.task.execution.api.JoiVehicleConnector;

public class BookingView extends TaskViewAbstract {
	
	private static Logger logger = Logger.getLogger(BookingView.class);
	
	private static int instanceNum = 1;

	public static final String ID = "com.dev.gis.app.view.BookingView";
	
	Calendar checkInDate = Calendar.getInstance();
	Calendar dropOffDate = Calendar.getInstance();

	private Text cityText = null;

	private Text priceText = null;

	private Text carDescription = null;
	
	private Text bookingRequestId = null;

	private Text paypalUrl = null;
	private Link paypalUrlLink = null;
	
	private Text paypalToken = null;
	private Text paypalError = null;
	
	private Offer selectedOffer = null;
	private List<Extra> selectedExtras = null;
	

	@Override
	public void createPartControl(Composite parent) {

		
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
		
		new Label(groupStamp, SWT.NONE).setText("RequestId:");
		bookingRequestId = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		bookingRequestId.setLayoutData(new GridData());

		new Label(groupStamp, SWT.NONE).setText("Paypal:");
		paypalUrl = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		paypalUrl.setLayoutData(new GridData());

		new Label(groupStamp, SWT.NONE).setText("PaypalToken:");
		paypalToken = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		paypalToken.setLayoutData(new GridData());

		new Label(groupStamp, SWT.NONE).setText("PaypalError:");
		paypalError = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		paypalError.setLayoutData(new GridData());
		
		new Label(groupStamp, SWT.NONE).setText("PaypalLink:");
		paypalUrlLink = new Link(groupStamp, SWT.BORDER | SWT.SINGLE);
		paypalUrlLink.setSize(140, 40);
		paypalUrlLink.setLayoutData(new GridData());

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
				BookingResponse response = JoiVehicleConnector.verifyOffers(selectedOffer,selectedExtras);
				if ( response.getBookingId() != null)
					carDescription.setText(response.getBookingId());
				bookingRequestId.setText(String.valueOf(response.getRequestId()));
				
				//BookingView.updateView(selectedOffer);
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
				
			}
		});
		

		
		final Button buttonPayPal = new Button(groupButtons, SWT.PUSH | SWT.LEFT);
		buttonPayPal.setText("PayPal ");
		buttonPayPal.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				PaypalSetCheckoutResponse response = JoiVehicleConnector.getPaypalUrl(selectedOffer,bookingRequestId.getText());
				if ( response != null) {
					paypalUrl.setText(response.getPaypalUrl());
					paypalUrlLink.setText(response.getPaypalUrl());
					paypalToken.setText(response.getToken());
					if ( response.getError() != null )
						paypalError.setText(response.getError());
				}
				else
					paypalError.setText(" Unknown PayPal Error" );
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
				
			}
		});

		final Button buttonPayPalResult = new Button(groupButtons, SWT.PUSH | SWT.LEFT);
		buttonPayPalResult.setText("PayPal Result ");
		buttonPayPalResult.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				PaypalDoCheckoutResponse response = JoiVehicleConnector.getPaypalResult(selectedOffer,bookingRequestId.getText(),paypalToken.getText());
				if ( response != null) {
					if ( response.getError() != null )
						paypalError.setText(response.getError());
				}
				else
					paypalError.setText(" Unknown PayPal Error" );
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
				
			}
		});
		
		
		final Button buttonBook = new Button(groupButtons, SWT.PUSH | SWT.LEFT);
		buttonBook.setText("Book");
		buttonBook.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// createBookingRequest - Driver, Customer
				//StartVerify
				BookingResponse response = JoiVehicleConnector.bookOffers(selectedOffer,bookingRequestId.getText(),selectedExtras);
				if ( response.getBookingId() != null)
					carDescription.setText(response.getBookingId());
				bookingRequestId.setText(String.valueOf(response.getRequestId()));
				
				//BookingView.updateView(selectedOffer);
				
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

	public void showOffer(final Offer selectedOffer, List<Extra> extras) {
		this.selectedOffer = selectedOffer;
		this.selectedExtras = extras;
		cityText.setText(selectedOffer.getBookLink().toString());
		priceText.setText(selectedOffer.getPrice().getAmount());
		//carDescription.setText(vehicleResult.getVehicle().getManufacturer() + " Group : "+vehicleResult.getVehicle().getCategoryId());
		
//		vehicleResult.getSupplierId();
//		vehicleResult.getPickUpStationId();
//		vehicleResult.getSupplierId();
//		vehicleResult.getServiceCatalogCode();
		
	}
	
	public static void  updateView(final Offer selectedOffer, final List<Extra> extras) {
		
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
					
					viewPart.showOffer(selectedOffer,extras);
					
					instanceNum++;
					
					logger.info(" add view :"+viewPart.getTitle());
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

		
	}
	

}
