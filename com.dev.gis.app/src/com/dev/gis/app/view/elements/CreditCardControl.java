package com.dev.gis.app.view.elements;

import java.net.URI;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.view.dialogs.BSCreditCardDialog;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.VehicleHttpService;
import com.dev.gis.connector.sunny.BookingResponse;
import com.dev.gis.connector.sunny.CreditCard;
import com.dev.gis.connector.sunny.VerifyCreditCardPaymentResponse;

public class CreditCardControl extends EditPartControl {
	private static Logger logger = Logger.getLogger(CreditCardControl.class);
	
	private OutputTextControls errorText;
	private OutputTextControls bsUrl = null;
	private OutputTextControls bsToken = null;
	private OutputTextControls bsCrediCard = null;

	public static CreditCardControl createControl(Composite parent) {
		CreditCardControl bc = new CreditCardControl(parent);
		bc.createGroupControl(parent, "Edit Credit Card");
		return bc;
		
	}

	public CreditCardControl(Composite parent) {
		super(parent);
	}
	
	

	@Override
	protected void createElements(Group groupStamp) {
		
		bsUrl = new OutputTextControls(groupStamp, "Url", -1,1 );
		bsToken = new OutputTextControls(groupStamp, "Token", -1,1 );

		bsCrediCard = new OutputTextControls(groupStamp, "Credit Card", -1,1 );
		
		errorText = new OutputTextControls(groupStamp, "Error / Warning", -1,1 );

	}

	@Override
	protected void createButtons(Group groupStamp) {

		new ButtonControl(groupStamp, "CreditCard Payment With B&S", 0,  getCreditCardListener(getShell()));
		
	}

	private SelectionListener getCreditCardListener(Shell shell) {
		return new AddBsPaymentListener(shell);
	}

	private void showErrors(com.dev.gis.connector.sunny.Error error) {
		
		String message = "";
		message = message + error.getErrorNumber()+ "  "+ error.getErrorText() + " " + error.getErrorType() + " ;";
		errorText.setValue(message);
		
		errorText.getControl().setBackground(getDisplay().getSystemColor(SWT.COLOR_GRAY));
		errorText.getControl().setForeground(getDisplay().getSystemColor(SWT.COLOR_DARK_RED));
		
	}
	
	private class AddBsPaymentListener extends AbstractListener {
		
		private final Shell shell;

		public AddBsPaymentListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			try {
				bsUrl.setValue("running");
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				VehicleHttpService service = serviceFactory
						.getVehicleJoiService();
	
				URI bsuri = service.getPypageUrl();
				if ( bsuri != null) {
					bsUrl.setValue(bsuri.toString());
					
					BSCreditCardDialog mpd = new BSCreditCardDialog(shell, bsuri);
					if (mpd.open() == Dialog.OK) {
						VerifyCreditCardPaymentResponse response = service.getPayPageResult();
						if ( response != null && response.getCard() != null) {
							CreditCard cc = response.getCard();
							String token = cc.getCardAliasNo() + " ( "+cc.getCardTresorNo() + " )";
							bsToken.setValue(token);
							
							String card = cc.getCardNumber() + " "+cc.getOwnerName();
							bsCrediCard.setValue(card);
							
						}
						else
							showErrors(new com.dev.gis.connector.sunny.Error(1,1," Error in PayPage Verify "));
					}
					
				}
				else
					showErrors(new com.dev.gis.connector.sunny.Error(1,1," Error in PayPage GetUrl "));
			}
			catch(Exception err) {
				bsToken.setValue("");
				bsCrediCard.setValue("");
				showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
				
			}

		}

	}

	@Deprecated
	private class AddBSGetResultListener extends AbstractListener {
		
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


	
}
