package com.dev.gis.app.taskmanager.testAppView;

import java.net.URI;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.view.dialogs.BSCreditCardDialog;
import com.dev.gis.app.view.elements.CreditCardControl;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.AdacVehicleHttpService;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.VehicleHttpService;
import com.dev.gis.connector.sunny.CreditCard;
import com.dev.gis.connector.sunny.VerifyCreditCardPaymentResponse;


public class AdacCreditCardControl extends CreditCardControl {

	public AdacCreditCardControl(Composite parent) {
		super(parent);
		// TODO Auto-generated constructor stub
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
				bsUrl.setValue("running");
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				AdacVehicleHttpService service = serviceFactory.getAdacVehicleJoiService();

				String bookingRequestId = AdacModelProvider.INSTANCE.getBookingRequestId();

				URI bsuri = service.getBSPayPageUrl(bookingRequestId);
				if ( bsuri != null) {
					bsUrl.setValue(bsuri.toString());
					
					BSCreditCardDialog mpd = new BSCreditCardDialog(shell, bsuri);
					if (mpd.open() == Dialog.OK) {
						VerifyCreditCardPaymentResponse response = null; // service.getPayPageResult();
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
