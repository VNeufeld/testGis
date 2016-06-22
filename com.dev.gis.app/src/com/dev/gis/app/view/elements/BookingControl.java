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
import com.dev.gis.connector.sunny.BookingRequest;
import com.dev.gis.connector.sunny.BookingResponse;
import com.dev.gis.connector.sunny.BookingTotalInfo;
import com.dev.gis.connector.sunny.Extra;

public class BookingControl extends EditPartControl {
	private static Logger logger = Logger.getLogger(BookingControl.class);
	
	private OutputTextControls errorText;
	private OutputTextControls bookingId;
	private OutputTextControls bookingNo;
	private OutputTextControls status;
	private OutputTextControls bookingPreis;
	private OutputTextControls oneWayPrice;
	private OutputTextControls oneWayPriceSell;
	private OutputTextControls extraPoaPrice;
	private OutputTextControls extraPoaPriceSell;
	private OutputTextControls expectedTotalPrice;
	
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
		
		Composite bookingComposite = createComposite(groupStamp,2,-1, true);

		Composite priceComposite = createComposite(groupStamp,4,-1, true);
		
		bookingId = new OutputTextControls(bookingComposite, "BookingId", 300,1 );
		bookingNo = new OutputTextControls(bookingComposite, "BookingNo", 300,1 );
		status = new OutputTextControls(bookingComposite, "Status", 300,1 );
		bookingPreis = new OutputTextControls(bookingComposite, "PrepaidPrice", 300,1 );

		oneWayPriceSell = new OutputTextControls(priceComposite, "OneWayPriceSell", 300,1 );
		oneWayPrice = new OutputTextControls(priceComposite, "OneWayPrice", 300,1 );

		extraPoaPriceSell = new OutputTextControls(priceComposite, "ExtraPOAPriceSell", 300,1 );
		extraPoaPrice = new OutputTextControls(priceComposite, "ExtraPOAPrice", 300,1 );

		expectedTotalPrice = new OutputTextControls(priceComposite, "ExpectedTotalPrice", 300,1 );
		
		errorText = new OutputTextControls(groupStamp, "Error / Warning", -1,1 );

	}

	@Override
	protected void createButtons(Group groupStamp) {

		Composite buttonComposite = createComposite(groupStamp,3,-1, true);

		
		new ButtonControl(buttonComposite, "GetBookingInfo", 0,  new AddGetBookingInfoListener(getShell()));

		new ButtonControl(buttonComposite, "Verify", 0,  getVerifySelectionListener(getShell()));

		new ButtonControl(buttonComposite, "Booking", 0,  getBookingSelectionListener(getShell()));
		
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
				if ( response != null) {
					if ( response.getSupplierBookingNo() != null) {
						bookingNo.setValue(" BNR: "+response.getSupplierBookingNo());
						bookingId.setValue(response.getBookingId());
					}
					status.setValue(""+response.getStatus());
					
					if ( response.getPrice() != null)
						bookingPreis.setValue(response.getPrice().toString());
					
					if ( response.getOneWayFee() != null)
						oneWayPrice.setValue(response.getOneWayFee().toString());
					if ( response.getOneWayFeeInSellCurrency() != null)
						oneWayPriceSell.setValue(response.getOneWayFeeInSellCurrency().toString());

					if ( response.getExtraPrice() != null)
						extraPoaPrice.setValue(response.getExtraPrice().toString());
					if ( response.getExtraPriceInSellCurrency() != null)
						extraPoaPriceSell.setValue(response.getExtraPriceInSellCurrency().toString());

					if ( response.getExpectedPoaTotalPrice() != null)
						expectedTotalPrice.setValue(response.getExpectedPoaTotalPrice().toString());
					
					
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

				if ( response != null) {
					
					status.setValue(""+response.getStatus());
					
					if ( response.getPrice() != null)
						bookingPreis.setValue(response.getPrice().toString());
					
					if ( response.getOneWayFee() != null)
						oneWayPrice.setValue(response.getOneWayFee().toString());
					if ( response.getOneWayFeeInSellCurrency() != null)
						oneWayPriceSell.setValue(response.getOneWayFeeInSellCurrency().toString());

					if ( response.getExtraPrice() != null)
						extraPoaPrice.setValue(response.getExtraPrice().toString());
					if ( response.getExtraPriceInSellCurrency() != null)
						extraPoaPriceSell.setValue(response.getExtraPriceInSellCurrency().toString());

					if ( response.getExpectedPoaTotalPrice() != null)
						expectedTotalPrice.setValue(response.getExpectedPoaTotalPrice().toString());

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
	
				service.putExtras(selectedOffer, extras);
				
				BookingRequest response = service.getBookingInfo(selectedOffer);
				if ( response != null) {
					BookingTotalInfo bookingTotalInfo = response.getBookingTotalInfo();
					if ( bookingTotalInfo.getTotalPrice() != null)
						bookingPreis.setValue(bookingTotalInfo.getTotalPrice().toString());
					else
						bookingPreis.setValue("");
					
					if ( bookingTotalInfo.getOneWayFee() != null)
						oneWayPrice.setValue(bookingTotalInfo.getOneWayFee().toString());
					else
						oneWayPrice.setValue("");
					
					if ( bookingTotalInfo.getOneWayFeeInSellCurrency() != null)
						oneWayPriceSell.setValue(bookingTotalInfo.getOneWayFeeInSellCurrency().toString());
					else
						oneWayPriceSell.setValue("");

					if ( bookingTotalInfo.getTotalExtraPoaPrice() != null)
						extraPoaPrice.setValue(bookingTotalInfo.getTotalExtraPoaPrice().toString());
					else
						extraPoaPrice.setValue("");
					
					if ( bookingTotalInfo.getTotalExtraPriceInSellCurrency() != null)
						extraPoaPriceSell.setValue(bookingTotalInfo.getTotalExtraPriceInSellCurrency().toString());
					else
						extraPoaPriceSell.setValue("");

					if ( bookingTotalInfo.getExpectedTotalPrice() != null)
						expectedTotalPrice.setValue(bookingTotalInfo.getExpectedTotalPrice().toString());

				}
				
			}
			catch( Exception err) {
				logger.error(err.getMessage(),err);
				MessageDialog.openError(
						shell,"Error",err.getMessage());
			}
		}
	}
	
}
