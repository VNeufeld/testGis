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
import com.bpcs.mdcars.model.Payment;
import com.bpcs.mdcars.model.PaymentTransaction;
import com.bpcs.mdcars.model.PaymentType;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.EditPartControl;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.JoiHttpServiceFactory;

public class PaymentControlControl extends EditPartControl {
	private static Logger logger = Logger.getLogger(PaymentControlControl.class);

	protected OutputTextControls errorText;

	private ObjectTextControl rentalNo;

	private ObjectTextControl paymentRef;

	private ObjectTextControl payer;

	private ObjectTextControl issueText;

	private ObjectTextControl paymentId;
	
	protected OutputTextControls paypalInfo = null;

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

		rentalNo = new ObjectTextControl(groupStamp, 300, false, "rentalNo ");

		paymentRef = new ObjectTextControl(groupStamp, 300, false, "paymentRef ");

		payer = new ObjectTextControl(groupStamp, 300, false, "payer ");

		issueText = new ObjectTextControl(groupStamp, 300, false, "issueText ");

		paymentId = new ObjectTextControl(groupStamp, 300, false, "paymentId");
		
		paypalInfo = new OutputTextControls(groupStamp, "Info", -1, 1);

		errorText = new OutputTextControls(groupStamp, "Error / Warning", -1, 1);

	}

	@Override
	protected void createButtons(Group groupStamp) {
		Composite cp = createComposite(groupStamp, 1, -1, true);

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
				
				String sRentalNo = rentalNo.getSelectedValue();
				
				GetCMPaymentInfoRequest request = new GetCMPaymentInfoRequest();
				request.setRentalNo(sRentalNo);

				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
				
				GetCMPaymentInfoResponse response = service.getPaymentInfo(request);
				paypalInfo.setValue(response.getOpenAmount().toString());
				PaymentTransaction pt = null;
				if ( !response.getPaymentTransactions().isEmpty())
					pt = response.getPaymentTransactions().get(0);
				if ( pt != null) {
					paymentRef.setSelectedValue(pt.getExtPaymentReference());
					paymentId.setSelectedValue(pt.getPaymentId());
					issueText.setSelectedValue(pt.getIssueText());
					payer.setSelectedValue(pt.getPayer());
				}

			} catch (Exception err) {
				paypalInfo.setValue("");
				showErrors(new com.dev.gis.connector.sunny.Error(1, 1,
						err.getMessage()));

			}

		}

		private void clearFields() {
			paypalInfo.setValue("");
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
				
				String sRentalNo = rentalNo.getSelectedValue();
				String sPaymentRef = paymentRef.getSelectedValue();
				
				SetCMPaymentTransactionRequest request = new SetCMPaymentTransactionRequest();
				request.setRentalNo(sRentalNo);
				
				PaymentTransaction payment = new PaymentTransaction() ;
				payment.setPaymentType(PaymentType.CASH_PAYMENT);
				payment.setPaidAmount(new MoneyAmount("225,00", "EUR"));
				payment.setExtPaymentReference(sPaymentRef);
				payment.setIssueText(issueText.getSelectedValue());
				payment.setPayer(payer.getSelectedValue());
				payment.setPaymentId(paymentId.getSelectedValue());
				
				request.setPaymentTransaction(payment);
				

				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
				
				GetCMPaymentInfoResponse response = service.addPaymentInfo(request);
				paypalInfo.setValue(response.getOpenAmount().toString());

			} catch (Exception err) {
				paypalInfo.setValue("");
				showErrors(new com.dev.gis.connector.sunny.Error(1, 1,
						err.getMessage()));

			}

		}

		private void clearFields() {
			paypalInfo.setValue("");
			errorText.setValue("");
			
		}

	}


}
