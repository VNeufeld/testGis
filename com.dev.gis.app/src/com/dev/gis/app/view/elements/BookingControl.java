package com.dev.gis.app.view.elements;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.SunnyOfferDo;
import com.dev.gis.connector.api.VehicleHttpService;
import com.dev.gis.connector.sunny.BookingResponse;
import com.dev.gis.connector.sunny.Extra;

public class BookingControl extends EditPartControl {
	private static Logger logger = Logger.getLogger(BookingControl.class);
	
	private OutputTextControls errorText;
	private OutputTextControls bookingId;
	private OutputTextControls bookingPreis;
	
	private SunnyOfferDo selectedOffer;
	private List<Extra> extras;

	public static BookingControl createBookingControl(Composite parent) {
		BookingControl bc = new BookingControl(parent);
		bc.createGroupControl(parent, "Booking");
		return bc;
		
	}

	public BookingControl(Composite parent) {
		super(parent);
	}
	
	

	@Override
	protected void createElements(Group groupStamp) {
		
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
		return new AddBookingListener(shell);		
	}

	private SelectionListener getVerifySelectionListener(final Shell shell) {
		return new AddVerifyListener(shell);		
	}


	protected class AddBookingListener extends AbstractListener {
		
		private final Shell shell;

		public AddBookingListener(Shell shell) {
			this.shell = shell;
		}
		

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			try {
				//BookingRequestCreater 
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				VehicleHttpService service = serviceFactory
						.getVehicleJoiService();
	
				service.putExtras(selectedOffer, extras);
	
				BookingResponse response = service.bookOffer(selectedOffer);
				String result = "";
				if ( response != null) {
					if ( response.getSupplierBookingNo() != null)
						result = result + " BNR: "+response.getSupplierBookingNo();

					bookingId.setValue(result);
					
					result = " Status: "+response.getStatus();
					result = result + " Preis: "+response.getPrice().toString();
					
					bookingPreis.setValue(result);
					
					showErrors( response );

					
				}
			}
			catch( Exception err) {
				
				logger.error(err.getMessage(),err);
			
				MessageDialog.openError(
						shell,"Error",err.getMessage());
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

			try {
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				VehicleHttpService service = serviceFactory
						.getVehicleJoiService();
				
				service.putExtras(selectedOffer, extras);
	
				BookingResponse response = service.verifyOffer(selectedOffer);
				String result = "";
				if ( response != null) {
					if ( response.getSupplierBookingNo() != null)
						result = result + " BNR: "+response.getSupplierBookingNo();
					bookingId.setValue(result);
					
					result = " Status: "+response.getStatus();
					result = result + " Preis: "+response.getPrice().toString();
					
					bookingPreis.setValue(result);
					showErrors( response );
				}
			}
			catch( Exception err) {
				
				logger.error(err.getMessage(),err);
			
				MessageDialog.openError(
						shell,"Error",err.getMessage());
			}
			

		}


	}
	private void showErrors(BookingResponse response) {
		String message = "";
		if ( response.getErrors() != null) {
			for ( com.dev.gis.connector.sunny.Error error : response.getErrors()) {
				message = message + error.getErrorNumber()+ "  "+ error.getErrorText() + " " + error.getErrorType() + " ;";
			}
			
		}
		errorText.setValue(message);
		errorText.getControl().setBackground(getDisplay().getSystemColor(SWT.COLOR_GRAY));
		errorText.getControl().setForeground(getDisplay().getSystemColor(SWT.COLOR_DARK_RED));
		
	}
	
	public void setInitValues(SunnyOfferDo selectedOffer, List<Extra> extras) {
		this.selectedOffer = selectedOffer;
		this.extras = extras;
	
	}

	
}
