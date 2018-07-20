package com.dev.gis.app.taskmanager.clubMobilView;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.bpcs.mdcars.model.Customer;
import com.bpcs.mdcars.model.Person;
import com.dev.gis.app.view.elements.BasicControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.ClubMobilModelProvider;

public class ClubMobilCustomerControl extends BasicControl {
	
	private final Composite parent;
	
	private OutputTextControls result = null;

	public ClubMobilCustomerControl(Composite groupStamp) {
		
		parent = groupStamp;

		final Group group = createGroupSpannAll(groupStamp, "Customer",4);
		
		result = new OutputTextControls(group, "CustomerInfo", 500, 1 );

		new ButtonControl(group, "GetCustomer", 0,  getCustomerListener(getShell(), false));
		
		
		if ( ClubMobilModelProvider.INSTANCE.selectedReservation  != null) {
			
			if ( ClubMobilModelProvider.INSTANCE.selectedReservation.getCustomer() != null ) {
				Person person = ClubMobilModelProvider.INSTANCE.selectedReservation.getCustomer().getPerson();
				String customerNo = "";
				if ( ClubMobilModelProvider.INSTANCE.selectedReservation.getCustomer().getCommonCustomerInfo() != null)
					customerNo = ClubMobilModelProvider.INSTANCE.selectedReservation.getCustomer().getCommonCustomerInfo().getMemberNo();

				if ( person != null)
					result.setValue("MemberNo : " +customerNo+ " Person : "+ person.getName() + "," + person.getFirstName() );
				else
					result.setValue("MemberNo : " +customerNo);
			}
			
		}

		

	}
	
	protected Composite getParent() {
		return parent;
	}

	protected Shell getShell() {
		return parent.getShell();
	}

	protected SelectionListener getCustomerListener(Shell shell, boolean change_pwd) {
		return new ClubMobilCustomerListener(shell);
	}
	
	private class ClubMobilCustomerListener extends AbstractListener {
		
		private final Shell shell;

		public ClubMobilCustomerListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			try {
				CustomerDialog mpd = new CustomerDialog(shell);
				if (mpd.open() == Dialog.OK) {
					Customer customer = mpd.getCustomer();
					if ( customer != null) {
						ClubMobilModelProvider.INSTANCE.customer = customer;
						Person person = customer.getPerson();
						result.setValue("Customer : MemberNo : " + customer.getCommonCustomerInfo().getMemberNo()+ " Person : "+ person.getName() + "," + person.getFirstName() );
						
						if ( ClubMobilModelProvider.INSTANCE.selectedReservation  != null) {
							ClubMobilModelProvider.INSTANCE.selectedReservation.setCustomer(customer);
						}
						
					}
					else {
						ClubMobilModelProvider.INSTANCE.customer = null;
						result.setValue("" );
						
					}
				}
				else {
				}
			}
			catch(Exception err) {
				showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
				
			}

		}

	}
	protected void showErrors(com.dev.gis.connector.sunny.Error error) {
		
		String message = "";
		message = message + error.getErrorNumber()+ "  "+ error.getErrorText() + " " + error.getErrorType() + " ;";
		MessageDialog.openError(
				getShell(),"Error",message);
		
	}

	public OutputTextControls getResult() {
		return result;
	}

}
