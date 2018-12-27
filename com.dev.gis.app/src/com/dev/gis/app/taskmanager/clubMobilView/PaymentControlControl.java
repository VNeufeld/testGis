package com.dev.gis.app.taskmanager.clubMobilView;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.bpcs.mdcars.json.protocol.GetCMPaymentInfoRequest;
import com.bpcs.mdcars.json.protocol.GetCMPaymentInfoResponse;
import com.bpcs.mdcars.json.protocol.SetCMPaymentTransactionRequest;
import com.bpcs.mdcars.model.MoneyAmount;
import com.bpcs.mdcars.model.PaymentMethod;
import com.bpcs.mdcars.model.PaymentTransaction;
import com.bpcs.mdcars.model.ReservationDetails;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.EditPartControl;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;

public class PaymentControlControl extends EditPartControl {
	private static Logger logger = Logger.getLogger(PaymentControlControl.class);

	protected OutputTextControls errorText;

	private ObjectTextControl rentalId;

	private ObjectTextControl paymentRef;

	private OutputTextControls payer;

	private ObjectTextControl paymentId;

	private ObjectTextControl payedAmount;
	
	protected OutputTextControls openAmount = null;

	protected OutputTextControls totalAmount = null;

	
	public static PaymentControlControl createControl(Composite parent) {
		PaymentControlControl bc = new PaymentControlControl(parent);
		bc.createGroupControl(parent, "Payment");
		return bc;

	}

	public PaymentControlControl(Composite parent) {
		super(parent);
	}

	@Override
	protected void createElements(Group groupStamp) {

		rentalId = new ObjectTextControl(groupStamp, 300, false, "rentalId ");

		paymentRef = new ObjectTextControl(groupStamp, 300, false, "paymentRef ");

		payer = new OutputTextControls(groupStamp, "Payer", -1, 1);

		paymentId = new ObjectTextControl(groupStamp, 300, false, "paymentId");

		totalAmount = new OutputTextControls(groupStamp, "TotalAmount", -1, 1);
		
		openAmount = new OutputTextControls(groupStamp, "OpenAmount", -1, 1);
		
		payedAmount = new ObjectTextControl(groupStamp, 300, false, "PayedAmount");

		errorText = new OutputTextControls(groupStamp, "Error / Warning", -1, 1);
		
		ReservationDetails details = ClubMobilModelProvider.INSTANCE.selectedReservation;
		if ( details != null) {
			rentalId.setSelectedValue(""+details.getRentalId());
		}
		paymentRef.setSelectedValue("");
		totalAmount.setValue("");
		paymentId.setSelectedValue("");
		openAmount.setValue("");
		errorText.setValue("");
		payedAmount.saveValue("");

	}

	@Override
	protected void createButtons(Group groupStamp) {
		Composite cp = createComposite(groupStamp, 2, -1, true);

		new ButtonControl(cp, "Get Payment", 250, getPaymentListener(getShell()));

		new ButtonControl(cp, "Add Payment", 250, addPaymentListener(getShell()));
		
	}

	protected SelectionListener getPaymentListener(Shell shell) {
		return new GetPaymentListener(shell);
	}

	protected SelectionListener addPaymentListener(Shell shell) {
		return new AddPaymentListener(shell);
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

	private class GetPaymentListener extends AbstractListener {

		private final Shell shell;

		public GetPaymentListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {

			try {
				clearFields();
				int rentalId = 0;
				ReservationDetails details = ClubMobilModelProvider.INSTANCE.selectedReservation;
				if ( details != null)
					rentalId = details.getRentalId();
				
				GetCMPaymentInfoRequest request = new GetCMPaymentInfoRequest();
				request.setRentalId(rentalId);
				request.setPayerId(details.getDriver().getCommonCustomerInfo().getCustomerId());

				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
				
				GetCMPaymentInfoResponse response = service.getPaymentInfo(request);
				if ( response.getPaymentInfo() != null) {
					openAmount.setValue(response.getPaymentInfo().getOpenAmount().toString());
					totalAmount.setValue(response.getPaymentInfo().getTotalAmount().toString());
					payer.setValue(response.getPaymentInfo().getPayerName());
					payedAmount.setSelectedValue(response.getPaymentInfo().getPaidAmount().toString());
				}
				else
					showErrors(new com.dev.gis.connector.sunny.Error(1, 1,
							"PaymentInfo is null"));

			} catch (Exception err) {
				logger.error(err.getMessage(),err);
				openAmount.setValue("");
				showErrors(new com.dev.gis.connector.sunny.Error(1, 1,
						err.getMessage()));

			}

		}

		private void clearFields() {
			openAmount.setValue("");
			errorText.setValue("");
			
		}

	}
	
	private class AddPaymentListener extends AbstractListener {

		private final Shell shell;

		public AddPaymentListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {

			try {
				clearFields();
				
				int rentalId = 0;
				ReservationDetails details = ClubMobilModelProvider.INSTANCE.selectedReservation;
				if ( details != null)
					rentalId = details.getRentalId();

				String sPaymentRef = paymentRef.getSelectedValue();
				
				SetCMPaymentTransactionRequest request = new SetCMPaymentTransactionRequest();
				request.setRentalId(rentalId);
				
				PaymentTransaction payment = new PaymentTransaction() ;
				if ( paymentId.getSelectedValue().equals("13"))
					payment.setPaymentMethod(PaymentMethod.CASH_PAYMENT);
				else
					payment.setPaymentMethod(PaymentMethod.REMITTANCE);
				payment.setPaidAmount(new MoneyAmount(payedAmount.getSelectedValue(), "EUR"));
				payment.setExtPaymentReference(sPaymentRef);
				
				request.setPaymentTransaction(payment);
				

				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
				
				GetCMPaymentInfoResponse response = service.addPaymentInfo(request);
				if ( response.getPaymentInfo() != null) {
					openAmount.setValue(response.getPaymentInfo().getOpenAmount().toString());
					totalAmount.setValue(response.getPaymentInfo().getTotalAmount().toString());
					payer.setValue(response.getPaymentInfo().getPayerName());
					payedAmount.setSelectedValue(response.getPaymentInfo().getPaidAmount().toString());
				}
				else
					showErrors(new com.dev.gis.connector.sunny.Error(1, 1,
							"PaymentInfo is null"));


			} catch (Exception err) {
				openAmount.setValue("");
				showErrors(new com.dev.gis.connector.sunny.Error(1, 1,
						err.getMessage()));

			}

		}

		private void clearFields() {
			openAmount.setValue("");
			errorText.setValue("");
			
		}

	}


}
