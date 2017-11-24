package com.dev.gis.app.taskmanager.clubMobilView;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.bpcs.mdcars.model.Customer;
import com.bpcs.mdcars.model.Person;
import com.dev.gis.app.view.elements.BasicControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.ClubMobilModelProvider;

public class ClubMobilReservationListControl extends BasicControl {
	
	private final Composite parent;
	
	private OutputTextControls result = null;
	
	
	private ReservationNoTextControl reservationNoTextControl;
	
	private ClubMobilOfferListTable reservationListTable;


	public ClubMobilReservationListControl(Composite groupStamp) {
		
		parent = groupStamp;

		final Group group = createGroupSpannAll(groupStamp, "Reservation",4);
		
		Composite cc = createComposite(group, 2, -1, true);
		result = new OutputTextControls(cc, "ReservationInfo", 500, 1 );

		Composite cc2 = createComposite(group, 3, -1, true);
		
		reservationNoTextControl = new ReservationNoTextControl(cc2, 300, false);

		new ButtonControl(cc2, "GetReservation", 0,  getReservationListener(getShell(), false));

		createReservationListTable(group);

	}
	
	protected Composite getParent() {
		return parent;
	}

	protected Shell getShell() {
		return parent.getShell();
	}

	protected SelectionListener getReservationListener(Shell shell, boolean change_pwd) {
		return new ClubMobilReservationListener(shell);
	}
	
	private class ClubMobilReservationListener extends AbstractListener {
		
		private final Shell shell;

		public ClubMobilReservationListener(Shell shell) {
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
						result.setValue("Customer : MemberNo : " + customer.getCustomerNo()+ " Person : "+ person.getName() + "," + person.getFirstName() );
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
	
	private void createReservationListTable(Composite composite) {
		final Group groupOffers = new Group(composite, SWT.TITLE);
		groupOffers.setText("Reservations:");
		
		groupOffers.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, true).applyTo(groupOffers);
		
		this.reservationListTable = new ClubMobilOfferListTable(null,
				groupOffers, null, null);
	}
	
	protected void showErrors(com.dev.gis.connector.sunny.Error error) {
		
		String message = "";
		message = message + error.getErrorNumber()+ "  "+ error.getErrorText() + " " + error.getErrorType() + " ;";
		MessageDialog.openError(
				getShell(),"Error",message);
		
	}

}
