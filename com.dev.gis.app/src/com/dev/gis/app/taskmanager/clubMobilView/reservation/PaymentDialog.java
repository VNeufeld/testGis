package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.bpcs.mdcars.model.ReservationDetails;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilUtils;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.ext.BusinessException;

public class PaymentDialog extends Dialog {
	
	private static Logger logger = Logger.getLogger(PaymentDialog.class);
	
	protected OutputTextControls paymentResult = null;
	
	private ObjectTextControl creditCardNoCtrl;
	
	public PaymentDialog(Shell parentShell) {
		super(parentShell);
	    setShellStyle(getShellStyle() | SWT.RESIZE);
		
	}
	
	private void closeOk() {
		okPressed();
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
 	    parent.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).hint(500, 400).applyTo(composite);
		
		creditCardNoCtrl = new ObjectTextControl(composite, 300, false, "CreditCard ");

		new ButtonControl(composite, "Put Payment", 0,  getListener(getShell()));

		paymentResult = new OutputTextControls(composite, "paymentResult:", 500, 1 );
		
		return composite;
	}
	
	protected SelectionListener getListener(Shell shell) {
		return new ClubMobilCustomerListener(shell);
	}
	
	private class ClubMobilCustomerListener implements SelectionListener {

		public ClubMobilCustomerListener(Shell shell) {
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			try {
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
				
				String creditCardNo = creditCardNoCtrl.getSelectedValue();
				ReservationDetails details = ClubMobilModelProvider.INSTANCE.selectedReservation;
				
				details.getPayment().getCard().setCardNumber(creditCardNo);
				
						
				service.putPayment(details.getPayment());
				
				closeOk();
					
			}
			catch(BusinessException err) {
				logger.error(err.getMessage());
				 com.dev.gis.connector.joi.protocol.Error error = null;
				try {
					error = ClubMobilUtils.convertJsonStringToObject(err.getMessage(),com.dev.gis.connector.joi.protocol.Error.class);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if ( error != null)
					ClubMobilUtils.showErrors(error.getErrorText());
				else
					ClubMobilUtils.showErrors(err.getMessage());
				
			}
			catch(Exception err) {
				logger.error(err.getMessage());
				String errName = err.getClass().getName();
				ClubMobilUtils.showErrors(new com.dev.gis.connector.sunny.Error(1,1, errName + " : " +err.getMessage()));
				
			}

		}


		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			
		}

	}
	


}
