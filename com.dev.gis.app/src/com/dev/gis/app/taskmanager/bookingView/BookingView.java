package com.dev.gis.app.taskmanager.bookingView;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.app.taskmanager.SunnyCarsView.BSCreditCardDialog;
import com.dev.gis.app.view.dialogs.PayPalDialog;
import com.dev.gis.connector.api.AdacVehicleHttpService;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.joi.protocol.BookingRequest;
import com.dev.gis.connector.joi.protocol.BookingResponse;
import com.dev.gis.connector.joi.protocol.Extra;
import com.dev.gis.connector.joi.protocol.Offer;
import com.dev.gis.connector.joi.protocol.PaypalDoCheckoutResponse;
import com.dev.gis.connector.joi.protocol.PaypalSetCheckoutResponse;
import com.dev.gis.connector.sunny.VerifyCreditCardPaymentResponse;
import com.dev.gis.task.execution.api.JoiVehicleConnector;

public class BookingView extends TaskViewAbstract {
	
	private static Logger logger = Logger.getLogger(BookingView.class);
	
	private static int instanceNum = 1;

	public static final String ID = "com.dev.gis.app.view.BookingView";
	
	Calendar checkInDate = Calendar.getInstance();
	Calendar dropOffDate = Calendar.getInstance();

	private Text bookingText = null;

	private Text priceText = null;

	private Text carDescription = null;
	
	private Text bookingRequestId = null;
	
	private Text bookingId = null;
	

	private Text paypalUrl = null;
	private Link paypalUrlLink = null;
	
	private Text paypalToken = null;
	private Text paypalError = null;
	
	private Offer selectedOffer = null;
	private List<Extra> selectedExtras = null;

	protected class AddVerifyListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			
			bookingRequestId.setText("running....");
			
			BookingRequest  request =  BookingRequestCreator.createBookingRequest(selectedOffer, selectedExtras);
			
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			AdacVehicleHttpService service = serviceFactory.getAdacVehicleJoiService();
			
			//StartVerify
			BookingResponse response = service.verifyOffers(request,selectedOffer);
			bookingRequestId.setText(String.valueOf(response.getRequestId()));
			priceText.setText(response.getPrice().getAmount());

		}

	}
	
	protected class AddPaypalListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			
			paypalUrl.setText("running....");
			
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			AdacVehicleHttpService service = serviceFactory.getAdacVehicleJoiService();
			
			
			PaypalSetCheckoutResponse response = service.getPaypalUrl(selectedOffer,bookingRequestId.getText());
			if ( response != null) {
				paypalUrl.setText(response.getPaypalUrl());
				paypalUrlLink.setText(response.getPaypalUrl());
				paypalToken.setText(response.getToken());
				if ( response.getError() != null )
					paypalError.setText(response.getError());
				else {
					PayPalDialog mpd = new PayPalDialog(parent.getShell(), response.getPaypalUrl());
					if (mpd.open() == Dialog.OK) {
						
					}
				}
				
			}
			else
				paypalError.setText(" Unknown PayPal Error" );

		}

	}

	protected class AddPaypalResultListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			//paypalUrl.setText("running....");
			
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			AdacVehicleHttpService service = serviceFactory.getAdacVehicleJoiService();
			
			PaypalDoCheckoutResponse response = service.getPaypalResult(selectedOffer,bookingRequestId.getText(),paypalToken.getText());
			if ( response != null) {
				if ( response.getError() != null )
					paypalError.setText(response.getError());
			}
			else
				paypalError.setText(" Unknown PayPal Error" );

		}

	}

	protected class AddBookingCompleteListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			bookingId.setText("running....");
			
			//BookingRequestCreater 
			
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			AdacVehicleHttpService service = serviceFactory.getAdacVehicleJoiService();

			
			BookingResponse response = JoiVehicleConnector.bookOffers(selectedOffer,bookingRequestId.getText(),selectedExtras);
			if ( response.getBookingId() != null)
				bookingId.setText(response.getBookingId());
			bookingRequestId.setText(String.valueOf(response.getRequestId()));

		}

	}

	

	@Override
	public void createPartControl(Composite parent) {

		this.parent = parent;
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		
		Composite bookingGroup = createBookingGroup(composite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(bookingGroup);
		
		//GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(createRequestId(composite));
		

		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(createPaypalGroup(composite));

		final Group groupButtons = new Group(composite, SWT.TITLE);
		groupButtons.setText("Offer:");
		groupButtons.setLayout(new GridLayout(4, true));
		groupButtons.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Button buttonVerify = new Button(groupButtons, SWT.PUSH | SWT.LEFT);
		buttonVerify.setText("Verify");
		buttonVerify.addSelectionListener(new AddVerifyListener());

		
		final Button buttonPayPal = new Button(groupButtons, SWT.PUSH | SWT.LEFT);
		buttonPayPal.setText("PayPal ");
		buttonPayPal.addSelectionListener(new AddPaypalListener());

		final Button buttonPayPalResult = new Button(groupButtons, SWT.PUSH | SWT.LEFT);
		buttonPayPalResult.setText("PayPal Result ");
		buttonPayPalResult.addSelectionListener(new AddPaypalResultListener());
		
		final Button buttonBook = new Button(groupButtons, SWT.PUSH | SWT.LEFT);
		buttonBook.setText("Book");
		buttonBook.addSelectionListener(new AddBookingCompleteListener() );
	}
	


	

	private Control createPaypalGroup(Composite parent) {
		
		final Group groupStamp = new Group(parent, SWT.TITLE);
		groupStamp.setText("Paypal:");
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(groupStamp);
		
		// TODO Auto-generated method stub
		new Label(groupStamp, SWT.NONE).setText("Paypal Url:");
		paypalUrl = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(paypalUrl);
		

		new Label(groupStamp, SWT.NONE).setText("PaypalToken:");
		paypalToken = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.FILL).grab(true, false).
		hint(300, 16).
		applyTo(paypalToken);

		new Label(groupStamp, SWT.NONE).setText("PaypalError:");
		paypalError = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(paypalError);
		
		new Label(groupStamp, SWT.NONE).setText("PaypalLink:");
		paypalUrlLink = new Link(groupStamp, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(paypalUrlLink);
		
		return groupStamp;
	}





	private Control createRequestId(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(composite);
		
		new Label(composite, SWT.NONE).setText("RequestId:");
		bookingRequestId = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.FILL).hint(300, 16).grab(false, false).applyTo(bookingRequestId);

		return composite;
	}





	private Composite createBookingGroup(Composite parent) {
		final Group groupStamp = new Group(parent, SWT.TITLE);
		groupStamp.setText("Booking:");
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(groupStamp);

		Label label = new Label(groupStamp, SWT.NONE);
		label.setText("Booking link");
		Text textOfferLink = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(textOfferLink);

		label = new Label(groupStamp, SWT.NONE);
		label.setText("Preis:");
		priceText = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.FILL).grab(true, false).
		hint(300, 16).
		applyTo(priceText);

		label = new Label(groupStamp, SWT.NONE);
		label.setText("Car:");
		carDescription = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.FILL).grab(true, false).
			hint(300, 16).
			applyTo(carDescription);

		if (selectedOffer != null ) {
			textOfferLink.setText(selectedOffer.getBookLink().toString());
			priceText.setText(selectedOffer.getPrice().getAmount());
		}

		//carDescription.setText(vehicleResult.getVehicle().getManufacturer() + " Group : "+vehicleResult.getVehicle().getCategoryId());
		
		new Label(groupStamp, SWT.NONE).setText("RequestId:");
		this.bookingRequestId = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.FILL).hint(300, 16).grab(false, false).applyTo(bookingRequestId);
		
		new Label(groupStamp, SWT.NONE).setText("bookingId:");
		this.bookingId = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.FILL).hint(300, 16).grab(false, false).applyTo(bookingId);
		

		return groupStamp;
	}





	public void showOffer(final Offer selectedOffer, List<Extra> extras) {
		this.selectedOffer = selectedOffer;
		this.selectedExtras = extras;
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
					
					//instanceNum++;
					
					logger.info(" add view :"+viewPart.getTitle());
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

		
	}
	

}
