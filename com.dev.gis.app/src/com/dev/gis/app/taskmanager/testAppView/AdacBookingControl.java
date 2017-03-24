package com.dev.gis.app.taskmanager.testAppView;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.taskmanager.bookingView.BookingRequestCreator;
import com.dev.gis.app.view.elements.BookingRequestIdControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.EditPartControl;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.AdacVehicleHttpService;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.joi.protocol.BookingRequest;
import com.dev.gis.connector.joi.protocol.BookingResponse;
import com.dev.gis.connector.joi.protocol.Customer;
import com.dev.gis.connector.joi.protocol.Error;

public class AdacBookingControl extends EditPartControl {
	private static Logger logger = Logger.getLogger(AdacBookingControl.class);
	
	private OutputTextControls errorText;
	private OutputTextControls bookingId;
	private OutputTextControls bookingPreis;
	
	private ObjectTextControl bookingRequestId;

	public static AdacBookingControl createBookingControl(Composite parent) {
		AdacBookingControl bc = new AdacBookingControl(parent);
		bc.createGroupControl(parent, "Booking");
		return bc;
		
	}

	public AdacBookingControl(Composite parent) {
		super(parent);
	}
	
	

	@Override
	protected void createElements(Group groupStamp) {
		
		bookingRequestId = new BookingRequestIdControl(groupStamp);
		
		bookingId = new OutputTextControls(groupStamp, "BookingId", -1,1 );

		bookingPreis = new OutputTextControls(groupStamp, "Price", -1,1 );
		
		errorText = new OutputTextControls(groupStamp, "Error / Warning", -1,1 );

	}

	@Override
	protected void createButtons(Group groupStamp) {

		new ButtonControl(groupStamp, "Verify", 0,  getVerifySelectionListener(getShell()));

		new ButtonControl(groupStamp, "Booking", 0,  getBookingSelectionListener(getShell()));
		
	}

	private SelectionListener getBookingSelectionListener(final Shell shell) {
		return new AddBookingCompleteListener(shell);		
	}

	private SelectionListener getVerifySelectionListener(final Shell shell) {
		return new AddVerifyListener(shell);		
	}
	
	protected class AddBookingCompleteListener extends AbstractListener {
		
		private final Shell shell;

		public AddBookingCompleteListener(Shell shell) {
			this.shell = shell;
		}
		

		@Override
		public void widgetSelected(SelectionEvent arg0) {

			clearFields();
			
			try {

				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				AdacVehicleHttpService service = serviceFactory
						.getAdacVehicleJoiService();
				
				int paymentType = 8;  // paypal
				
				Customer customer = AdacVehicleHttpService.createCustomer(AdacModelProvider.INSTANCE.memberNo);
				
				if ( "1".equals(AdacModelProvider.INSTANCE.paymentType))
					paymentType = 1;   // KK
				else
					paymentType = 8;


				BookingResponse response = service.bookOffers(
						AdacModelProvider.INSTANCE.getSelectedOffer(), bookingRequestId.getSelectedValue(),
						AdacModelProvider.INSTANCE.getSelectedExtras(),paymentType, customer , AdacModelProvider.INSTANCE.promotionCode);
				if ( response == null) {
					bookingId.setValue("");
					errorText.setValue(" Booking response is null");
					return;
				}
				
				if (StringUtils.isNotEmpty(response.getBookingId())) {
						bookingId.setValue(response.getBookingId());
						String result = " Status: "; //+response.getStatus();
						result = result + " Preis: "+response.getPrice().toString();
						bookingPreis.setValue(result);
				}
				else {
					showErrors(response);					
				}
				

			} catch (Exception err) {
				logger.error(err.getMessage(),err);
				
				MessageDialog.openError(
						shell,"Error",err.getMessage());
				
				errorText.setValue(err.getMessage());
				
				
			}

		}

	}



	
	protected class AddVerifyListener extends AbstractListener {
		
		private final Shell shell;

		public AddVerifyListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {

			bookingRequestId.setSelectedValue("running....");
			clearFields();
			try {

				BookingRequest request = BookingRequestCreator
						.createBookingRequest(AdacModelProvider.INSTANCE.getSelectedOffer(), 
								AdacModelProvider.INSTANCE.getSelectedExtras(), AdacModelProvider.INSTANCE.memberNo);

				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				AdacVehicleHttpService service = serviceFactory
						.getAdacVehicleJoiService();

				// StartVerify
				BookingResponse response = service.verifyOffers(request,
						AdacModelProvider.INSTANCE.getSelectedOffer() , AdacModelProvider.INSTANCE.promotionCode);
				bookingRequestId
						.setSelectedValue(String.valueOf(response.getRequestId()));

				if (response.getPrice() != null )
					bookingPreis.setValue(response.getPrice().getAmount());
				
				showErrors(response);					
				
				AdacModelProvider.INSTANCE.setBookingRequestId(String.valueOf(response.getRequestId()));
				
			} catch (Exception err) {
				logger.error(err.getMessage(),err);
				
				MessageDialog.openError(
						shell,"Error",err.getMessage());
				
				errorText.setValue(err.getMessage());
				
			}

		}

	}
	
	private void clearFields() {
		errorText.setValue("");
		bookingId.setValue("");
		bookingPreis.setValue("");
	}

	private void showErrors(BookingResponse response) {
		String message = "";
		if ( response.getErrors() != null) {
			for ( Error error : response.getErrors()) {
				message = message + error.getErrorNumber()+ "  "+ error.getErrorText() + " " + error.getErrorType() + " ;";
			}
			
		}
		if ( message.length() > 0 ) {
			errorText.setValue(message);
			errorText.getControl().setBackground(getDisplay().getSystemColor(SWT.COLOR_GRAY));
			errorText.getControl().setForeground(getDisplay().getSystemColor(SWT.COLOR_DARK_RED));
		}
		
	}
	
	public void setInitValues() {
		bookingRequestId.setSelectedValue("");
		bookingPreis.setValue("");
	}

	
}
