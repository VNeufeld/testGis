package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.taskmanager.clubMobilView.PaymentControlControl;

public class PaymentDialog extends Dialog {
	
	private static Logger logger = Logger.getLogger(PaymentDialog.class);
	
	public PaymentDialog(Shell parentShell) {
		super(parentShell);
	    setShellStyle(getShellStyle() | SWT.RESIZE);
		
	}
	
//	private void closeOk() {
//		okPressed();
//	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
 	    parent.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).hint(500, 400).applyTo(composite);
		
		PaymentControlControl.createControl(composite);
		
		return composite;
	}
	
//	protected SelectionListener getListener(Shell shell) {
//		return new ClubMobilCustomerListener(shell);
//	}
//	
//	private class ClubMobilCustomerListener implements SelectionListener {
//
//		public ClubMobilCustomerListener(Shell shell) {
//		}
//
//		@Override
//		public void widgetSelected(SelectionEvent arg0) {
//			
//			try {
//				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
//				ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
//				
//				String creditCardNo = "cc";
//				ReservationDetails details = ClubMobilModelProvider.INSTANCE.selectedReservation;
//				if ( details != null) {
//					if ( details.getPaymentTransactions().isEmpty()){
//						com.bpcs.mdcars.model.Payment payment = new com.bpcs.mdcars.model.Payment();
//						details.getPaymentTransactions().add(payment);
//					}
//					
//					if ( !details.getPaymentTransactions().isEmpty()){
//						com.bpcs.mdcars.model.Payment payment = details.getPaymentTransactions().get(0);
//					
//						if ( payment.getCard() == null)
//							payment.setCard(new CreditCard());
//						payment.getCard().setCardNumber(creditCardNo);
//							
//						service.putPayment(payment);
//					}
//				}
//				
//				closeOk();
//					
//			}
//			catch(BusinessException err) {
//				logger.error(err.getMessage());
//				 com.dev.gis.connector.joi.protocol.Error error = null;
//				try {
//					error = ClubMobilUtils.convertJsonStringToObject(err.getMessage(),com.dev.gis.connector.joi.protocol.Error.class);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				if ( error != null)
//					ClubMobilUtils.showErrors(error.getErrorText());
//				else
//					ClubMobilUtils.showErrors(err.getMessage());
//				
//			}
//			catch(Exception err) {
//				logger.error(err.getMessage());
//				String errName = err.getClass().getName();
//				ClubMobilUtils.showErrors(new com.dev.gis.connector.sunny.Error(1,1, errName + " : " +err.getMessage()));
//				
//			}
//
//		}
//
//
//		@Override
//		public void widgetDefaultSelected(SelectionEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//	}
	


}
