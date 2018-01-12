package com.dev.gis.app.taskmanager.clubMobilView;

import org.apache.log4j.Logger;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.bpcs.mdcars.model.Payment;
import com.bpcs.mdcars.model.Person;
import com.bpcs.mdcars.model.ReservationDetails;
import com.dev.gis.app.taskmanager.clubMobilView.reservation.ClubMobilPaymentControl;
import com.dev.gis.app.taskmanager.clubMobilView.reservation.ClubMobilReservationListControl;
import com.dev.gis.app.taskmanager.clubMobilView.reservation.ClubMobilSaveReservationControl;
import com.dev.gis.app.taskmanager.rentcars.RentCarsAppView;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.task.execution.api.IEditableTask;

public class ClubMobilReservationView extends RentCarsAppView {
	
	private static Logger logger = Logger.getLogger(ClubMobilReservationView.class);

	public static final String ID = IEditableTask.ID_ClubMobilReservationView;
	
	private ClubMobilCustomerControl clubMobilCustomerControl;
	
	private ClubMobilPaymentControl clubMobilPaymentControl;
	
	private ClubMobilReservationListControl clubMobilResControl;

	
	@Override
	public void createPartControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		
		final Group groupStamp = new Group(composite, SWT.TITLE);
		groupStamp.setText("Inputs:");
		groupStamp.setLayout(new GridLayout(4, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(false, false).applyTo(groupStamp);
		
		createBasicControls(groupStamp);
		
		clubMobilResControl = new ClubMobilReservationListControl(composite);
		
		clubMobilCustomerControl = new ClubMobilCustomerControl(composite);
		
		clubMobilPaymentControl = new ClubMobilPaymentControl(composite);

		new ClubMobilSaveReservationControl(composite, clubMobilResControl);	
	}

	
	@Override
	protected void createBasicControls(final Group groupStamp) {
		
		Composite cc = createComposite(groupStamp, 3, -1, true);
		new ClubMobilServerTextControl(cc);
		new ClubMobilAuthorizationCheckBox(cc,"Authorization");

		new ClubMobilLoginControl(groupStamp);

		
		
	}
	
	public static void updateCustomerControl(final String text) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					logger.info(" showResult : run instanceNum = "+1 );
					// Show protocol, show results
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					ClubMobilReservationView viewPart =  (ClubMobilReservationView)wp.showView(
							ID, 
							Integer.toString(1), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					
					ReservationDetails details = ClubMobilModelProvider.INSTANCE.selectedReservation;
					if ( details != null) {
						if ( details.getCustomer() != null && details.getCustomer().getPerson() != null) {
							Person person = details.getCustomer().getPerson();
							viewPart.getClubMobilCustomerControl().getResult().setValue(person.getName() + " " + person.getFirstName());
						}
						if ( !details.getPaymentTransactions().isEmpty()) {
							Payment payment = details.getPaymentTransactions().get(0); 
							String cardNumber = "-";
							if ( payment.getCard() != null)
								cardNumber = payment.getCard().getCardNumber();
							viewPart.getClubMobilPaymentControl().getResult().setValue(cardNumber);
						}
						
						viewPart.getClubMobilResControl().update();
					}
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

	}


	public ClubMobilCustomerControl getClubMobilCustomerControl() {
		return clubMobilCustomerControl;
	}


	public ClubMobilPaymentControl getClubMobilPaymentControl() {
		return clubMobilPaymentControl;
	}


	public ClubMobilReservationListControl getClubMobilResControl() {
		return clubMobilResControl;
	}

	
}
