package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.bpcs.mdcars.model.CreditCard;
import com.bpcs.mdcars.model.PaymentTransaction;
import com.bpcs.mdcars.model.ReservationDetails;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilUtils;
import com.dev.gis.app.view.elements.BasicControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.ClubMobilModelProvider;

public class ClubMobilPaymentControl extends BasicControl {
	
	private final Composite parent;
	
	private OutputTextControls result = null;

	public ClubMobilPaymentControl(Composite groupStamp) {
		
		parent = groupStamp;

		final Group group = createGroupSpannAll(groupStamp, "Payment",4);
		
		result = new OutputTextControls(group, "PaymentInfo", 500, 1 );

		new ButtonControl(group, "ChangePayment", 0,  getPaymentListener(getShell()));
		

	}
	
	protected Composite getParent() {
		return parent;
	}

	protected Shell getShell() {
		return parent.getShell();
	}

	protected SelectionListener getPaymentListener(Shell shell) {
		return new ClubMobilPaymentListener(shell);
	}
	
	private class ClubMobilPaymentListener extends AbstractListener {
		
		private final Shell shell;

		public ClubMobilPaymentListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			try {
				PaymentDialog mpd = new PaymentDialog(shell);
				mpd.open();
				
				ReservationDetails details = ClubMobilModelProvider.INSTANCE.selectedReservation;

			}
			catch(Exception err) {
				ClubMobilUtils.showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
				
			}

		}

	}

	public OutputTextControls getResult() {
		return result;
	}

}
