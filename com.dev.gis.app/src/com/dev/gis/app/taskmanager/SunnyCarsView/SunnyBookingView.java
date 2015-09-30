package com.dev.gis.app.taskmanager.SunnyCarsView;

import java.net.URI;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.SunnyOfferDo;
import com.dev.gis.connector.api.VehicleHttpService;
import com.dev.gis.connector.joi.protocol.PaypalDoCheckoutResponse;
import com.dev.gis.connector.joi.protocol.PaypalSetCheckoutResponse;
import com.dev.gis.connector.sunny.BookingRequest;
import com.dev.gis.connector.sunny.BookingResponse;
import com.dev.gis.connector.sunny.CreditCard;
import com.dev.gis.connector.sunny.Extra;
import com.dev.gis.connector.sunny.VerifyCreditCardPaymentResponse;

public class SunnyBookingView extends TaskViewAbstract {
	
	private static Logger logger = Logger.getLogger(SunnyBookingView.class);
	
	private static int instanceNum = 1;

	public static final String ID = "com.dev.gis.app.view.SunnyBookingView";
	
	Calendar checkInDate = Calendar.getInstance();
	Calendar dropOffDate = Calendar.getInstance();

	private Text bookingText = null;

	private Text priceText = null;

	private Text carDescription = null;
	
	private Text bookingRequestId = null;
	
	private Text bookingId = null;

	private Text selectedExtrasText = null;
	
	private Text textOfferLink;
	
	private Text paypalUrl = null;
	private Link paypalUrlLink = null;
	
	private Text paypalToken = null;
	private Text paypalError = null;
	
	private Text bsToken = null;
	private Text bsCrediCard = null;
	
	private SunnyOfferDo selectedOffer = null;
	private List<Extra> selectedExtras = null;

	protected class AddVerifyListener extends AbstractListener {
		
		private final Shell shell;

		public AddVerifyListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			
			bookingRequestId.setText("running....");

			try {
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				VehicleHttpService service = serviceFactory
						.getVehicleJoiService();
				
				service.putExtras(selectedOffer, selectedExtras);
				
	
				BookingResponse response = service.verifyOffer(selectedOffer);
				String result = "";
				if ( response != null) {
					if ( response.getSupplierBookingNo() != null)
						result = result + " BNR: "+response.getSupplierBookingNo();
					result = result + " Status: "+response.getStatus();
					result = result + " Preis: "+response.getPrice().toString();
					
					bookingId.setText(result);
					
				}
				
							
				
				//bookingRequestId.setText(response);
			}
			catch( Exception err) {
				
				logger.error(err.getMessage(),err);
			
				MessageDialog.openError(
						shell,"Error",err.getMessage());
			}
			

		}

	}

	protected class AddGetBookingInfoListener extends AbstractListener {
		
		private final Shell shell;

		public AddGetBookingInfoListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			try {
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				VehicleHttpService service = serviceFactory
						.getVehicleJoiService();
				
				service.putExtras(selectedOffer, selectedExtras);
				
				BookingRequest response = service.getBookingInfo(selectedOffer);
				String result = "";
				if ( response != null) {
					result = result + " Car Preis: "+response.getBookingTotalInfo().getCarPrice().toString()+ " Totalpreis : "+response.getBookingTotalInfo().getTotalPrice().toString();
					bookingId.setText(result);
					
					if ( response.getPayment() != null) {
						bsToken.setText(response.getPayment().getCreditCardPaymentReference());
						if ( response.getPayment().getCard() != null) {
							bsCrediCard.setText(response.getPayment().getCard().getOwnerName()+ " : " + response.getPayment().getCard().getCardNumber());
						}
						
					}
					
				}
				
			}
			catch( Exception err) {
				
				logger.error(err.getMessage(),err);
			
				MessageDialog.openError(
						shell,"Error",err.getMessage());
			}
			

		}

	}
	
	protected class AddBookingListener extends AbstractListener {
		
		private final Shell shell;

		public AddBookingListener(Shell shell) {
			this.shell = shell;
		}
		

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			bookingId.setText("running....");
			
			try {
				//BookingRequestCreater 
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				VehicleHttpService service = serviceFactory
						.getVehicleJoiService();
	
				service.putExtras(selectedOffer, selectedExtras);
	
				BookingResponse response = service.bookOffer(selectedOffer);
				String result = "";
				if ( response != null) {
					if ( response.getSupplierBookingNo() != null)
						result = result + " BNR: "+response.getSupplierBookingNo();
					result = result + " Status: "+response.getStatus();
					result = result + " Preis: "+response.getPrice().toString();
					
					bookingId.setText(result);
					
				}
			}
			catch( Exception err) {
				
				logger.error(err.getMessage(),err);
			
				MessageDialog.openError(
						shell,"Error",err.getMessage());
			}
			
		}

	}

	
	protected class AddPaypalListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			
			paypalUrl.setText("running....");
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			VehicleHttpService service = serviceFactory
					.getVehicleJoiService();

//			PaypalSetCheckoutResponse response = service.getPaypalUrl(selectedOffer,bookingRequestId.getText());
//			if ( response != null) {
//				paypalUrl.setText(response.getPaypalUrl());
//				paypalUrlLink.setText(response.getPaypalUrl());
//				paypalToken.setText(response.getToken());
//				if ( response.getError() != null )
//					paypalError.setText(response.getError());
//			}
//			else
//				paypalError.setText(" Unknown PayPal Error" );

		}

	}
	
	protected class AddBsGetUrlListener extends AbstractListener {
		
		private final  Text bsUrl;
		private final  Text bsToken;
		private final  Text bsError;
		private final Composite parent;
		

		public AddBsGetUrlListener(Composite parent,Text bsUrl, Text bsToken, Text bsError) {
			this.bsUrl = bsUrl;
			this.bsToken = bsToken;
			this.bsError = bsError;
			this.parent = parent;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			try {
				bsToken.setText("running....");
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				VehicleHttpService service = serviceFactory
						.getVehicleJoiService();
	
				URI bsuri = service.getPypageUrl();
				if ( bsuri != null) {
					bsUrl.setText(bsuri.toString());
					
					BSCreditCardDialog mpd = new BSCreditCardDialog(parent.getShell(), bsuri);
					if (mpd.open() == Dialog.OK) {
						VerifyCreditCardPaymentResponse response = service.getPayPageResult();
						if ( response != null && response.getCard() != null) {
							CreditCard cc = response.getCard();
							String token = cc.getCardAliasNo() + " ( "+cc.getCardTresorNo() + " )";
							bsToken.setText(token);
							
							String card = cc.getCardNumber() + " "+cc.getOwnerName();
							bsError.setText(card);
							
						}
						else
							bsError.setText(" Unknown PayPage Error" );
					}
					
				}
				else
					bsError.setText(" Unknown PayPage Error" );
			}
			catch(Exception err) {
				bsToken.setText("");
				bsError.setText(err.getMessage());
				
			}

		}

	}

	protected class AddBSGetResultListener extends AbstractListener {
		
		private final  Text bsToken;
		private final  Text bsError;

		public AddBSGetResultListener(Text bsToken, Text bsError) {
			this.bsToken = bsToken;
			this.bsError = bsError;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			paypalUrl.setText("running....");
			
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			VehicleHttpService service = serviceFactory
					.getVehicleJoiService();

			
			VerifyCreditCardPaymentResponse response = service.getPayPageResult();
			if ( response != null && response.getCard() != null) {
				CreditCard cc = response.getCard();
				String token = cc.getCardAliasNo() + " ( "+cc.getCardTresorNo() + " )";
				bsToken.setText(token);
				
				String card = cc.getCardNumber() + " "+cc.getOwnerName();
				bsError.setText(card);
				
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
			VehicleHttpService service = serviceFactory
					.getVehicleJoiService();

			
//			PaypalDoCheckoutResponse response = service.getPaypalResult(selectedOffer,bookingRequestId.getText(),paypalToken.getText());
//			if ( response != null) {
//				if ( response.getError() != null )
//					paypalError.setText(response.getError());
//			}
//			else
//				paypalError.setText(" Unknown PayPal Error" );

		}

	}


	

	@Override
	public void createPartControl(Composite parent) {

		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		
		Composite bookingGroup = createBookingGroup(composite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(bookingGroup);
		
		//GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(createRequestId(composite));
		

		//GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(createPaypalGroup(composite));

		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(createCreditCardGroup(composite));

		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(createDriverGroup(composite));

		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(createCustomerGroup(composite));
		
		final Group groupButtons = new Group(composite, SWT.TITLE);
		groupButtons.setText("Offer:");
		groupButtons.setLayout(new GridLayout(4, true));
		groupButtons.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Button buttonVerify = new Button(groupButtons, SWT.PUSH | SWT.LEFT);
		buttonVerify.setText("Verify");
		buttonVerify.addSelectionListener(new AddVerifyListener(parent.getShell()));
		
		
		final Button buttonBook = new Button(groupButtons, SWT.PUSH | SWT.LEFT);
		buttonBook.setText("Book");
		buttonBook.addSelectionListener(new AddBookingListener(parent.getShell()) );
		
		final Button buttonGetBookingInfo = new Button(groupButtons, SWT.PUSH | SWT.LEFT);
		buttonGetBookingInfo.setText("GetBookingInfo");
		buttonGetBookingInfo.addSelectionListener(new AddGetBookingInfoListener(parent.getShell()) );
		
	}
	

	private Control createDriverGroup(Composite parent) {
		final Group groupStamp = new Group(parent, SWT.TITLE);
		groupStamp.setText("Driver:");
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(groupStamp);
		
		// TODO Auto-generated method stub
		new Label(groupStamp, SWT.NONE).setText("Driver:");
		final Text driver = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(driver);
		
		final Button buttonPutDriver = new Button(groupStamp, SWT.PUSH | SWT.LEFT);
		buttonPutDriver.setText("PutDriver ");
		buttonPutDriver.addSelectionListener(new AddPutDriverListener(driver));
		
		return groupStamp;

	}

	protected class AddPutDriverListener extends AbstractListener {
		
		private final Text  driver;

		public AddPutDriverListener(Text driver) {
			this.driver = driver;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			VehicleHttpService service = serviceFactory
					.getVehicleJoiService();
			
			String response = service.putDriver(selectedOffer,this.driver);
			if ( response != null) {
				driver.setText(driver.getText()+ " "+ response);
			}
			else
				paypalError.setText(" Unknown PayPal Error" );

		}

	}

	private Control createCustomerGroup(Composite parent) {
		final Group groupStamp = new Group(parent, SWT.TITLE);
		groupStamp.setText("Customer:");
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(groupStamp);
		
		// TODO Auto-generated method stub
		new Label(groupStamp, SWT.NONE).setText("Customer:");
		final Text customer = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(customer);
		
		final Button buttonPutCustomer = new Button(groupStamp, SWT.PUSH | SWT.LEFT);
		buttonPutCustomer.setText("PutCustomer ");
		buttonPutCustomer.addSelectionListener(new AddPutCustomerListener(customer));
		
		return groupStamp;

	}

	protected class AddPutCustomerListener extends AbstractListener {
		
		private final Text  customer;

		public AddPutCustomerListener(Text customer) {
			this.customer = customer;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			VehicleHttpService service = serviceFactory
					.getVehicleJoiService();
			
			String response = service.putCustomer(selectedOffer,this.customer);
			if ( response != null) {
				customer.setText(customer.getText()+ " "+ response);
			}
			else
				paypalError.setText(" Unknown PayPal Error" );

		}

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
		
		final Button buttonPayPal = new Button(groupStamp, SWT.PUSH | SWT.LEFT);
		buttonPayPal.setText("PayPal ");
		buttonPayPal.addSelectionListener(new AddPaypalListener());

		final Button buttonPayPalResult = new Button(groupStamp, SWT.PUSH | SWT.LEFT);
		buttonPayPalResult.setText("PayPal Result ");
		buttonPayPalResult.addSelectionListener(new AddPaypalResultListener());

		
		return groupStamp;
	}
	
	private Control createCreditCardGroup(Composite parent) {
		
		final Group groupStamp = new Group(parent, SWT.TITLE);
		groupStamp.setText("Credit Card:");
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(groupStamp);
		
		// TODO Auto-generated method stub
		new Label(groupStamp, SWT.NONE).setText("B & S URL:");
		final Text bsUrl = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(bsUrl);
		

		new Label(groupStamp, SWT.NONE).setText("B & S Token:");
		bsToken = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.FILL).
		grab(true, false).hint(300, 16).applyTo(bsToken);

		new Label(groupStamp, SWT.NONE).setText("B & S Kreditkarte:");
		bsCrediCard = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(bsCrediCard);
		
		
		final Button buttonPayPal = new Button(groupStamp, SWT.PUSH | SWT.LEFT);
		buttonPayPal.setText("Get PayPage URL ");
		buttonPayPal.addSelectionListener(new AddBsGetUrlListener(parent,bsUrl, bsToken, bsCrediCard));

//		final Button buttonPayPalResult = new Button(groupStamp, SWT.PUSH | SWT.LEFT);
//		buttonPayPalResult.setText("Get PayPage Result ");
//		buttonPayPalResult.addSelectionListener(new AddBSGetResultListener(bsToken, bsError));

		
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
		textOfferLink = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
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
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.FILL).hint(600, 16).grab(false, false).applyTo(bookingRequestId);
		
		new Label(groupStamp, SWT.NONE).setText("bookingId:");
		this.bookingId = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(bookingId);

		new Label(groupStamp, SWT.NONE).setText("selected extras:");
		this.selectedExtrasText = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(selectedExtrasText);
		

		return groupStamp;
	}





	public void showOffer(final SunnyOfferDo selectedOffer, List<Extra> extras) {
		this.selectedOffer = selectedOffer;
		this.selectedExtras = extras;
		
		priceText.setText(selectedOffer.getPrice().getAmount());
		textOfferLink.setText(selectedOffer.getBookLink().toString());
		
		if ( extras != null && extras.size() > 0) {
			StringBuilder sbx = new StringBuilder();
			for ( Extra extra : extras) {
				sbx.append(""+ extra.getId()+ " "+extra.getCode()+ extra.getName()+ " "+extra.getPrice());
			}
			selectedExtrasText.setText(sbx.toString());
		}

	}
	
	public static void  updateView(final SunnyOfferDo selectedOffer, final List<Extra> extras) {
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					logger.info(" showResult : run instanceNum = "+instanceNum );
					// Show protocol, show results
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					SunnyBookingView viewPart =  (SunnyBookingView)wp.showView(
							SunnyBookingView.ID, 
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
