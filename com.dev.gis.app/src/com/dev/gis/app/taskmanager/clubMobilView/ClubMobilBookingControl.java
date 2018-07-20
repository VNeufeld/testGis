package com.dev.gis.app.taskmanager.clubMobilView;

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
import com.dev.gis.app.taskmanager.testAppView.AdacBookingControl;
import com.dev.gis.app.view.elements.BookingRequestIdControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.AdacVehicleHttpService;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.joi.protocol.BookingResponse;
import com.dev.gis.connector.joi.protocol.CMVerifyRequest;
import com.dev.gis.connector.joi.protocol.Customer;
import com.dev.gis.connector.joi.protocol.Error;

public class ClubMobilBookingControl extends AdacBookingControl {
	private static Logger logger = Logger.getLogger(ClubMobilBookingControl.class);
	
	private OutputTextControls errorText;
	private OutputTextControls bookingId;
	private OutputTextControls bookingPreis;
	
	private ObjectTextControl bookingRequestId;

	public static ClubMobilBookingControl createBookingControl(Composite parent) {
		ClubMobilBookingControl bc = new ClubMobilBookingControl(parent);
		bc.createGroupControl(parent, "CM Booking");
		return bc;
		
	}

	public ClubMobilBookingControl(Composite parent) {
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

		new ButtonControl(groupStamp, "CM Verify", 0,  getVerifySelectionListener(getShell()));

		new ButtonControl(groupStamp, "Booking", 0,  getBookingSelectionListener(getShell()));
		
	}

	private SelectionListener getBookingSelectionListener(final Shell shell) {
		return new ClubMobilBookingCompleteListener(shell);		
	}

	private SelectionListener getVerifySelectionListener(final Shell shell) {
		return new ClubMobilVerifyListener(shell);		
	}
	
	protected class ClubMobilBookingCompleteListener extends AbstractListener {
		
		private final Shell shell;

		public ClubMobilBookingCompleteListener(Shell shell) {
			this.shell = shell;
		}
		

		@Override
		public void widgetSelected(SelectionEvent arg0) {

			clearFields();
			
			try {

				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				ClubMobilHttpService service = serviceFactory
						.getClubMobilleJoiService();
				
				int paymentType = 8;  // paypal
				
				Customer customer = AdacVehicleHttpService.createCustomer(AdacModelProvider.INSTANCE.memberNo);
				
				if ( "1".equals(AdacModelProvider.INSTANCE.paymentType))
					paymentType = 1;   // KK
				else if ( "0".equals(AdacModelProvider.INSTANCE.paymentType)) {
					paymentType = 0;
				}
				else 
					paymentType = 8;


				BookingResponse response = service.bookOffers(
						ClubMobilModelProvider.INSTANCE.getSelectedOffer(), bookingRequestId.getSelectedValue(),
						ClubMobilModelProvider.INSTANCE.getSelectedExtras(),paymentType, customer , AdacModelProvider.INSTANCE.promotionCode);
				if ( response == null) {
					bookingId.setValue("");
					errorText.setValue(" Booking response is null");
					return;
				}
				
				if (StringUtils.isNotEmpty(response.getReservationId())) {
						bookingId.setValue(response.getReservationId());
						String result = " Status: "; //+response.getStatus();
						if ( response.getPrice() != null)
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



	
	protected class ClubMobilVerifyListener extends AbstractListener {
		
		private final Shell shell;

		public ClubMobilVerifyListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {

			bookingRequestId.setSelectedValue("running....");
			clearFields();
			try {

				CMVerifyRequest request = BookingRequestCreator
						.createVerifyRequest(ClubMobilModelProvider.INSTANCE.getSelectedOffer(), 
								ClubMobilModelProvider.INSTANCE.getSelectedExtras(), AdacModelProvider.INSTANCE.memberNo);

				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				ClubMobilHttpService service = serviceFactory
						.getClubMobilleJoiService();
				

				// StartVerify
				BookingResponse response = service.verifyOffers(request,
						ClubMobilModelProvider.INSTANCE.getSelectedOffer() , AdacModelProvider.INSTANCE.promotionCode);
				
				bookingRequestId
						.setSelectedValue(String.valueOf(response.getRequestId()));

				if (response.getPrice() != null )
					bookingPreis.setValue(response.getPrice().toString());
				
				showErrors(response);					
				
				ClubMobilModelProvider.INSTANCE.setBookingRequestId(String.valueOf(response.getRequestId()));
				
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
		String message = "Booking Error : ";
		boolean isError = false;
		if ( response.getErrors() != null) {
			for ( Error error : response.getErrors()) {
				message = message + error.getErrorNumber()+ "  "+ error.getErrorText() + " " + error.getErrorType() + " ;";
				isError = true;
			}
			
		}
		if (isError ) {
			errorText.setValue(message);
			errorText.getControl().setBackground(getDisplay().getSystemColor(SWT.COLOR_GRAY));
			errorText.getControl().setForeground(getDisplay().getSystemColor(SWT.COLOR_DARK_RED));
			
			MessageDialog.openError(
					null,"Error",message);
			
		}
		
	}
	
	public void setInitValues() {
		bookingRequestId.setSelectedValue("");
		bookingPreis.setValue("");
	}

	
}
