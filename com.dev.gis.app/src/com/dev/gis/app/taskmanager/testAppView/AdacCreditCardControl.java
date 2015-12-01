package com.dev.gis.app.taskmanager.testAppView;

import java.net.URI;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.view.dialogs.BSCreditCardDialog;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.CreditCardControl;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.AdacVehicleHttpService;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.joi.protocol.CreditCard;
import com.dev.gis.connector.joi.protocol.VerifyCreditCardPaymentResponse;


public class AdacCreditCardControl extends CreditCardControl {

	public AdacCreditCardControl(Composite parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}
	
	public static CreditCardControl createControl(Composite parent) {
		AdacCreditCardControl bc = new AdacCreditCardControl(parent);
		bc.createGroupControl(parent, "Edit Credit Card");
		return bc;
		
	}
	
	@Override
	protected void createButtons(Group groupStamp) {
		Composite  cp = createComposite(groupStamp, 1, -1, true);
		
		new ButtonControl(cp, "CreditCard ADAC Payment With B&S", 0,  getCreditCardListener(getShell()));
		
	}

	@Override
	protected SelectionListener getCreditCardListener(Shell shell) {
		return new AdacBsPaymentListener(shell);
	}

	private class AdacBsPaymentListener extends AbstractListener {
		
		private final Shell shell;

		public AdacBsPaymentListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			try {
				bsUrl.setValue("running adac");
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				AdacVehicleHttpService service = serviceFactory.getAdacVehicleJoiService();

				String bookingRequestId = AdacModelProvider.INSTANCE.getBookingRequestId();

				URI bsuri = service.getBSPayPageUrl(bookingRequestId);
				if ( bsuri != null) {
					bsUrl.setValue(bsuri.toString());
					
					BSCreditCardDialog mpd = new BSCreditCardDialog(shell, bsuri);
					if (mpd.open() == Dialog.OK) {
						VerifyCreditCardPaymentResponse response = service.getBSPayPageResult(bookingRequestId);
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

}
