package com.dev.gis.app.view.elements;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.model.service.IPaypalService;
import com.dev.gis.app.view.dialogs.PayPalDialog;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.VehicleServiceException;
import com.dev.gis.connector.joi.protocol.PaypalDoCheckoutResponse;
import com.dev.gis.connector.joi.protocol.PaypalSetCheckoutResponse;

public class PayPalControl extends EditPartControl {
	private static Logger logger = Logger.getLogger(PayPalControl.class);

	protected OutputTextControls errorText;
	protected OutputTextControls paypalUrl = null;
	protected OutputTextControls paypalToken = null;
	protected OutputTextControls paypalInfo = null;

	private final IPaypalService paypalService;

	public static PayPalControl createControl(Composite parent,
			IPaypalService paypalService) {
		PayPalControl bc = new PayPalControl(parent, paypalService);
		bc.createGroupControl(parent, "Edit Paypal");
		return bc;

	}

	public PayPalControl(Composite parent, IPaypalService paypalService) {
		super(parent);
		this.paypalService = paypalService;
	}

	@Override
	protected void createElements(Group groupStamp) {

		paypalUrl = new OutputTextControls(groupStamp, "Paypal Url", -1, 1);
		paypalToken = new OutputTextControls(groupStamp, "Token", -1, 1);

		paypalInfo = new OutputTextControls(groupStamp, "PayPal Info", -1, 1);

		errorText = new OutputTextControls(groupStamp, "Error / Warning", -1, 1);

	}

	@Override
	protected void createButtons(Group groupStamp) {
		Composite cp = createComposite(groupStamp, 1, -1, true);

		new ButtonControl(cp, "Call Paypal", 0, getPaypalListener(getShell()));

	}

	protected SelectionListener getPaypalListener(Shell shell) {
		return new AddBsPaymentListener(shell);
	}

	protected void showErrors(com.dev.gis.connector.sunny.Error error) {

		String message = "";
		message = message + error.getErrorNumber() + "  "
				+ error.getErrorText() + " " + error.getErrorType() + " ;";
		errorText.setValue(message);

		errorText.getControl().setBackground(
				getDisplay().getSystemColor(SWT.COLOR_GRAY));
		errorText.getControl().setForeground(
				getDisplay().getSystemColor(SWT.COLOR_DARK_RED));

	}

	private class AddBsPaymentListener extends AbstractListener {

		private final Shell shell;

		public AddBsPaymentListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {

			try {
				paypalUrl.setValue("running");
				
				String bookingRequestId = AdacModelProvider.INSTANCE.getBookingRequestId();

				PaypalSetCheckoutResponse response = paypalService.retriveUri(bookingRequestId);
				paypalUrl.setValue(response.getPaypalUrl());
				paypalToken.setValue(response.getToken());
				if (response.getError() != null)
					throw new VehicleServiceException(response.getError());
				else {
					PayPalDialog mpd = new PayPalDialog(shell,
							response.getPaypalUrl());
					if (mpd.open() == Dialog.OK) {
						PaypalDoCheckoutResponse response2 = 
								paypalService.getPaypalResult(bookingRequestId,paypalToken.getValue());
						if (response2 != null) {
							if (response2.getError() != null)
								throw new VehicleServiceException(
										response2.getError());
							else {
								paypalInfo.setValue(response2.getPayerId() +" transactionId: "+ response2.getTransactionId());
							}
						} else
							errorText.setValue(" Unknown PayPal Error");
					}
				}

			} catch (Exception err) {
				paypalToken.setValue("");
				paypalInfo.setValue("");
				showErrors(new com.dev.gis.connector.sunny.Error(1, 1,
						err.getMessage()));

			}

		}

	}

}
