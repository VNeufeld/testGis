package com.dev.gis.app.taskmanager.SunnyCarsView;

import java.net.URI;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.app.view.elements.BookingControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.OfferInfoControl;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.SunnyOfferDo;
import com.dev.gis.connector.api.VehicleHttpService;
import com.dev.gis.connector.sunny.BookingRequest;
import com.dev.gis.connector.sunny.CreditCard;
import com.dev.gis.connector.sunny.Extra;
import com.dev.gis.connector.sunny.VerifyCreditCardPaymentResponse;

public class SunnyBookingView extends TaskViewAbstract {
	
	private static Logger logger = Logger.getLogger(SunnyBookingView.class);
	
	private static int instanceNum = 1;

	public static final String ID = "com.dev.gis.app.view.SunnyBookingView";
	private Text bookingId = null;
	
	private Text bsToken = null;
	private Text bsCrediCard = null;
	
	private SunnyOfferDo selectedOffer = null;
	private List<Extra> selectedExtras = null;
	
	private OfferInfoControl offerInfoControl = null;
	
	private BookingControl bookingControl = null;

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

		}

	}
	

	@Override
	public void createPartControl(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		
		offerInfoControl = OfferInfoControl.createOfferInfoControl(composite);

		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(createCreditCardGroup(composite));

		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(createDriverGroup(composite));

		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(createCustomerGroup(composite));
		
		new ButtonControl(composite, "GetBookingInfo", 0,  new AddGetBookingInfoListener(parent.getShell()));

		
		bookingControl = BookingControl.createBookingControl(composite);
		
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
		}
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


	public void showOffer(final SunnyOfferDo selectedOffer, List<Extra> extras) {
		if ( selectedOffer == null)
			return;
		this.selectedOffer = selectedOffer;
		this.selectedExtras = extras;
		
		offerInfoControl.setInitValues(selectedOffer,extras);
		
		bookingControl.setInitValues(selectedOffer,extras);

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
