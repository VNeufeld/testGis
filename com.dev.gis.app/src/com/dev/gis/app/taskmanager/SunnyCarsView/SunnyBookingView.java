package com.dev.gis.app.taskmanager.SunnyCarsView;

import java.util.List;

import org.apache.log4j.Logger;
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
import com.dev.gis.app.view.elements.CreditCardControl;
import com.dev.gis.app.view.elements.OfferInfoControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.SunnyOfferDo;
import com.dev.gis.connector.api.VehicleHttpService;
import com.dev.gis.connector.sunny.BookingRequest;
import com.dev.gis.connector.sunny.Extra;

public class SunnyBookingView extends TaskViewAbstract {
	
	private static Logger logger = Logger.getLogger(SunnyBookingView.class);
	
	private static int instanceNum = 1;

	public static final String ID = "com.dev.gis.app.view.SunnyBookingView";
	
	private SunnyOfferDo selectedOffer = null;
	private List<Extra> selectedExtras = null;
	
	private OfferInfoControl offerInfoControl = null;
	
	private OutputTextControls driver;
	
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
				
				BookingRequest response = service.getBookingInfo(selectedOffer);
				if ( response != null) {
					
//					if ( response.getPayment() != null) {
//						bsToken.setText(response.getPayment().getCreditCardPaymentReference());
//						if ( response.getPayment().getCard() != null) {
//							bsCrediCard.setText(response.getPayment().getCard().getOwnerName()+ " : " + response.getPayment().getCard().getCardNumber());
//						}
//						
//					}
					
				}
				
			}
			catch( Exception err) {
				logger.error(err.getMessage(),err);
				MessageDialog.openError(
						shell,"Error",err.getMessage());
			}
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		
		offerInfoControl = OfferInfoControl.createOfferInfoControl(composite);
		
		CreditCardControl.createControl(composite);
		
		createDriverGroup(composite);

		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(createCustomerGroup(composite));
		
		new ButtonControl(composite, "GetBookingInfo", 0,  new AddGetBookingInfoListener(parent.getShell()));
		
		bookingControl = BookingControl.createBookingControl(composite);
		
	}
	

	private Control createDriverGroup(Composite parent) {
		
		driver = new OutputTextControls(parent, "Driver ",-1);
		
		ButtonControl b  = new ButtonControl(parent, "PutDriver", 0,  new AddPutDriverListener(driver.getControl()));
		
		return null;

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

	public void showOffer(final SunnyOfferDo selectedOffer, List<Extra> extras) {
		if ( selectedOffer == null)
			return;
		this.selectedOffer = selectedOffer;
		this.selectedExtras = extras;
		
		JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
		VehicleHttpService service = serviceFactory
				.getVehicleJoiService();
		
		BookingRequest response = service.getBookingInfo(selectedOffer);
		
		if ( response != null) {
			if ( response.getDriver() != null)
				driver.setValue(response.getDriver().getName());
			else
				driver.setValue("");
			
//			if ( response.getPayment() != null) {
//				bsToken.setText(response.getPayment().getCreditCardPaymentReference());
//				if ( response.getPayment().getCard() != null) {
//					bsCrediCard.setText(response.getPayment().getCard().getOwnerName()+ " : " + response.getPayment().getCard().getCardNumber());
//				}
//				
//			}
			
		}
		
		
		
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
					
					logger.info(" add view :"+viewPart.getTitle() + " offer "+selectedOffer.getId().toString() + " extras : "+ ( extras != null ? extras.size(): 0) );
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

		
	}
	

}
