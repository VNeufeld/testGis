package com.dev.gis.app.taskmanager.clubMobilView;

import java.net.URI;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.taskmanager.testAppView.AdacCreditCardControl;
import com.dev.gis.app.taskmanager.testAppView.PayOneDialog;
import com.dev.gis.app.view.dialogs.BSCreditCardDialog;
import com.dev.gis.app.view.elements.CreditCardControl;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.joi.protocol.CreditCard;
import com.dev.gis.connector.joi.protocol.VerifyCreditCardPaymentResponse;


public class ClubMobilCreditCardControl extends AdacCreditCardControl {

	public ClubMobilCreditCardControl(Composite parent) {
		super(parent);
	}
	
	public static CreditCardControl createControl(Composite parent) {
		ClubMobilCreditCardControl bc = new ClubMobilCreditCardControl(parent);
		bc.createGroupControl(parent, "CM Select Credit Card");
		return bc;
		
	}

	@Override
	protected SelectionListener getCreditCardListener(Shell shell) {
		return new ClubMobilBsPaymentListener(shell);
	}

	private class ClubMobilBsPaymentListener extends AbstractListener {
		
		private final Shell shell;

		public ClubMobilBsPaymentListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			PayOneDialog pod = new PayOneDialog(shell);
		/*	try {
				bsUrl.setValue("running adac");
				
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				ClubMobilHttpService service = serviceFactory
						.getClubMobilleJoiService();
				

				String bookingRequestId = ClubMobilModelProvider.INSTANCE.getBookingRequestId();

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
				
			}*/

		}

	}

}
