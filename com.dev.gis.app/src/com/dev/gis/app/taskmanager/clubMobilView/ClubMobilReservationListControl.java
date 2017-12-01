package com.dev.gis.app.taskmanager.clubMobilView;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.bpcs.mdcars.json.protocol.ReservationListResponse;
import com.bpcs.mdcars.model.ReservationDetails;
import com.dev.gis.app.view.elements.BasicControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.app.view.listener.adac.AdacSelectChangedOfferClickListener;
import com.dev.gis.app.view.listener.adac.AdacSelectOfferDoubleClickListener;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;

public class ClubMobilReservationListControl extends BasicControl {
	
	private final Composite parent;
	
	private OutputTextControls result = null;
	
	private ReservationNoTextControl reservationNoTextControl;
	
	private ClubMobilReservationListTable reservationListTable;
	
	public ClubMobilReservationListControl(Composite groupStamp) {
		
		parent = groupStamp;
		
		final Group group = createGroupSpannAllRows(groupStamp, "Reservation",4,4);
		
		Composite cc = createComposite(group, 2, -1, true);
		result = new OutputTextControls(cc, "ReservationInfo", 500, 1 );

		Composite cc2 = createComposite(group, 3, -1, true);
		
		reservationNoTextControl = new ReservationNoTextControl(cc2, 300, false);

		new ButtonControl(cc2, "GetReservationList", 0,  getReservationListener(getShell(), false));

		createReservationListTable(groupStamp);

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
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
				
				ReservationListResponse reservationListResponse = service.getReservationList(reservationNoTextControl.getSelectedValue());
				
				ClubMobilModelProvider.INSTANCE.reservationListResponse = reservationListResponse;
				
				updateTable();
				
			}
			catch(Exception err) {
				showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
				
			}

		}


	}
	
	private void updateTable() {
		reservationListTable.update();
	}
	
	private void createReservationListTable(Composite composite) {
		final Group groupOffers = new Group(composite, SWT.TITLE);
		groupOffers.setText("Reservations:");
		
		groupOffers.setLayout(new GridLayout(1, false));
//		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
//				.grab(true, true).applyTo(groupOffers);
		
		GridDataFactory.fillDefaults().span(1, 4)
		.align(SWT.FILL, SWT.FILL).grab(true, true)
		.applyTo(groupOffers);
		
		
		this.reservationListTable = new ClubMobilReservationListTable(null,
				groupOffers, getSelectReservationDoubleClickListener(), getSelectChangedReservationClickListener());
		
	}
	
	protected void showErrors(com.dev.gis.connector.sunny.Error error) {
		
		String message = "";
		message = message + error.getErrorNumber()+ "  "+ error.getErrorText() + " " + error.getErrorType() + " ;";
		MessageDialog.openError(
				getShell(),"Error",message);
		
	}
	
	protected ISelectionChangedListener getSelectChangedReservationClickListener() {
//		AdacSelectChangedOfferClickListener ss = new AdacSelectChangedOfferClickListener(offerId);
//		return ss;
		return null;
	}

	protected IDoubleClickListener getSelectReservationDoubleClickListener() {
		ClubMobilSelectReservationDoubleClickListener ssd = new ClubMobilSelectReservationDoubleClickListener();
		return ssd;
	}

	public void update() {
		
		ReservationDetails details = ClubMobilModelProvider.INSTANCE.selectedReservation;
		
		result.setValue(details.getReservationNo());
		
	}


}
