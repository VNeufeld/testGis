package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import org.apache.commons.lang.StringUtils;
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

import com.bpcs.mdcars.json.protocol.ReservationListFilterRequest;
import com.bpcs.mdcars.json.protocol.ReservationListResponse;
import com.bpcs.mdcars.model.Clerk;
import com.bpcs.mdcars.model.DayAndHour;
import com.bpcs.mdcars.model.ReservationDetails;
import com.dev.gis.app.view.elements.BasicControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;

public class ClubMobilReservationListControl extends BasicControl {
	
	private final Composite parent;
	
	private OutputTextControls result = null;
	
	private ReservationNoTextControl reservationNoTextControl;
	
	private OutputTextControls rentalNo = null;

	private ObjectTextControl station = null;
	private ObjectTextControl customer = null;
	private ObjectTextControl adamMember = null;
	private ObjectTextControl resDateFrom = null;
	private ObjectTextControl resDateTo = null;
	private ObjectTextControl checkOutDateFrom = null;
	private ObjectTextControl checkOutDateTo = null;
	private ObjectTextControl rentalStatus = null;
	private ObjectTextControl carLicensePlate = null;
	
	
	private ClubMobilReservationListTable reservationListTable;
	
	public ClubMobilReservationListControl(Composite groupStamp) {
		
		parent = groupStamp;
		
		final Group group = createGroupSpannAllRows(groupStamp, "Reservation",4,4);
		
		Composite cc = createComposite(group, 2, -1, true);
		result = new OutputTextControls(cc, "ReservationInfo", 500, 1 );

		Composite cc2 = createComposite(group, 3, -1, true);
		
		reservationNoTextControl = new ReservationNoTextControl(cc2, 300, false);

		Composite cc3 = createComposite(group, 16, -1, true);
		Composite cc4 = createComposite(group, 16, -1, true);
		
		station = new ObjectTextControl(cc3, 100, false, "StationId");
		customer = new ObjectTextControl(cc3, 100, false, "Customer");
		adamMember = new ObjectTextControl(cc3, 100, false, "Member");
		rentalStatus = new ObjectTextControl(cc3, 100, false, "rentalStatus");
		carLicensePlate = new ObjectTextControl(cc3, 100, false, "carLicensePlate");
		resDateFrom = new ObjectTextControl(cc4, 100, false, "resDateFrom");
		resDateTo = new ObjectTextControl(cc4, 100, false, "resDateTo");
		checkOutDateFrom = new ObjectTextControl(cc4, 100, false, "checkOutDateFrom");
		checkOutDateTo = new ObjectTextControl(cc4, 100, false, "checkOutDateTo");

		com.dev.gis.connector.joi.protocol.Station pickupStation = ClubMobilModelProvider.INSTANCE.pickupStation;
		if ( pickupStation != null && pickupStation.getId() > 0 )
			station.setSelectedValue(""+pickupStation.getId());
		else
			station.setSelectedValue("");
		customer.setSelectedValue("");
		adamMember.setSelectedValue("");
		resDateFrom.setSelectedValue("");
		resDateTo.setSelectedValue("");
		checkOutDateFrom.setSelectedValue("");
		checkOutDateTo.setSelectedValue("");
		rentalStatus.setSelectedValue("");
		carLicensePlate.setSelectedValue("");


		rentalNo = new OutputTextControls(cc, "RentalNo", 500, 1 );
		
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
				
				ReservationListFilterRequest reservationListFilterRequest = createFilter();
				
				ReservationListResponse reservationListResponse = service.getReservationList(reservationListFilterRequest);
				
				ClubMobilModelProvider.INSTANCE.reservationListResponse = reservationListResponse;
				
				updateTable();
				
			}
			catch(Exception err) {
				showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
				
			}

		}



	}

	private ReservationListFilterRequest createFilter() {
		
		ReservationListFilterRequest request = new ReservationListFilterRequest();
		Integer stationId = null;
		Clerk clerk = ClubMobilModelProvider.INSTANCE.clerk;
		if ( StringUtils.isNotEmpty(station.getSelectedValue())) {
			try {	stationId = Integer.parseInt(station.getSelectedValue());	}
			catch(Exception err) {}
		}
		request.setRentalNoPattern(reservationNoTextControl.getSelectedValue());
		request.setStationId(stationId);
		
		request.setCustomerNoPattern(customer.getSelectedValue());
		request.setMemberNo(adamMember.getSelectedValue());

		request.setCarLicensePlatePattern(carLicensePlate.getSelectedValue());

		if ( StringUtils.isNotEmpty(resDateFrom.getSelectedValue())) {
			request.setReservationDateFrom(new DayAndHour(resDateFrom.getSelectedValue(), "00:00"));
		}

		if ( StringUtils.isNotEmpty(resDateTo.getSelectedValue())) {
			request.setReservationDateTo(new DayAndHour(resDateTo.getSelectedValue(), "23:59"));
		}

		if ( StringUtils.isNotEmpty(checkOutDateFrom.getSelectedValue())) {
			request.setCheckOutDateFrom(new DayAndHour(checkOutDateFrom.getSelectedValue(), "00:00"));
		}

		if ( StringUtils.isNotEmpty(checkOutDateTo.getSelectedValue())) {
			request.setCheckOutDateTo(new DayAndHour(checkOutDateTo.getSelectedValue(), "23:59"));
		}
		if ( StringUtils.isNotEmpty(rentalStatus.getSelectedValue())) {
			int status = 1; 
			try {	status = Integer.parseInt(rentalStatus.getSelectedValue());	}
			catch(Exception err) {}
			request.getRentalStatus().add(status);
		}
		
		return request;
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
		ClubMobilSelectReservationListener ss = new ClubMobilSelectReservationListener(result);
		return ss;
	}

	protected IDoubleClickListener getSelectReservationDoubleClickListener() {
		ClubMobilSelectReservationDoubleClickListener ssd = new ClubMobilSelectReservationDoubleClickListener(getShell());
		return ssd;
	}

	public void update() {
		
		ReservationDetails details = ClubMobilModelProvider.INSTANCE.selectedReservation;
		
		result.setValue(details.getReservationNo());
		
		if ( details.getRentalNo() != null)
			rentalNo.setValue(details.getRentalNo());
		
		reservationNoTextControl.setSelectedValue(details.getReservationNo());
		
	}

	public ClubMobilReservationListTable getReservationListTable() {
		return reservationListTable;
	}


}
