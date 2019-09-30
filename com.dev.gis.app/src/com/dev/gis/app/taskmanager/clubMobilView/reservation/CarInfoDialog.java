package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilCreateVehicleRequestUtils;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilOfferListTable;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilOfferViewUpdater;
import com.dev.gis.app.taskmanager.clubMobilView.SelectOfferClubMobilDoubleClickListener;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.app.view.listener.adac.AdacSelectChangedOfferClickListener;
import com.dev.gis.app.view.listener.adac.AdacSelectOfferDoubleClickListener;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.OfferDo;
import com.dev.gis.connector.joi.protocol.VehicleRequest;
import com.dev.gis.connector.joi.protocol.VehicleResponse;

public class CarInfoDialog extends AbstractReservationDialog {
	
	private ObjectTextControl reservationNo;

	private ObjectTextControl carInfo;
	
	private OutputTextControls carId;

	private OutputTextControls licensePlateCtl;
	
	private ClubMobilOfferListTable offerListTable;

	

	private final Shell shell;
	
	public CarInfoDialog(Shell parentShell) {
		super(parentShell);
		this.shell = parentShell;
	}

	protected void fillDialogArea(Composite composite) {
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).hint(900, 500).applyTo(composite);
		
		reservationNo = new ObjectTextControl(composite, 300, true, "ReservationNo");

		Composite ccc = createComposite(composite, 3, -1, true);
		
		carId = new OutputTextControls(ccc, "carId", 300);
		
		carInfo = new ObjectTextControl(ccc, 300, true, "Car Info");
		
		licensePlateCtl = new OutputTextControls(ccc, "licensePlate", 300,1);
		
		if ( ClubMobilModelProvider.INSTANCE.selectedReservation  != null) {
			reservationNo.setSelectedValue(ClubMobilModelProvider.INSTANCE.selectedReservation.getReservationNo());
			
			carId.setValue(""+ClubMobilModelProvider.INSTANCE.selectedReservation.getCarId());
			
			if ( ClubMobilModelProvider.INSTANCE.selectedReservation.getReservationCar() != null) {
				if ( ClubMobilModelProvider.INSTANCE.selectedReservation.getReservationCar().getAcrissCode()  != null)
					carInfo.setSelectedValue(ClubMobilModelProvider.INSTANCE.selectedReservation.getReservationCar().getAcrissCode());
				
				String licensePlate = ClubMobilModelProvider.INSTANCE.selectedReservation.getReservationCar().getLicensePlate();
				if ( licensePlate != null)
					licensePlateCtl.setValue(licensePlate);
			}
			
		}
		
		this.offerListTable = new ClubMobilOfferListTable(null,
				composite, 	getSelectOfferDoubleClickListener(), getSelectChangedOfferClickListener() );


		
		new ButtonControl(ccc, "get Cars", 0,  new ClubMobilCarInfoListener(getShell()));


	}

	protected ISelectionChangedListener getSelectChangedOfferClickListener() {
		AdacSelectChangedOfferClickListener ss = new AdacSelectChangedOfferClickListener(carId);
		return ss;
	}

	protected IDoubleClickListener getSelectOfferDoubleClickListener() {
		SelectCarClubMobilDoubleClickListener ssd = new SelectCarClubMobilDoubleClickListener();
		return ssd;
	}

	protected void okPressed() {

		setReturnCode(OK);
		close();
	}
	private class ClubMobilCarInfoListener implements SelectionListener {
		
		private final Shell shell;

		public ClubMobilCarInfoListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			if ( ClubMobilModelProvider.INSTANCE.selectedReservation != null) {
				ClubMobilModelProvider.INSTANCE.reservationNo = ""+ClubMobilModelProvider.INSTANCE.selectedReservation.getRentalId();
			}
			
			
			VehicleRequest request = ClubMobilCreateVehicleRequestUtils.createVehicleRequest();
			
			if ( request == null) {
				MessageDialog.openError(
						null,"Error","Can'tT create vehicle request - check station and date ");
	
			}
			else {
			
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				ClubMobilHttpService service = serviceFactory
						.getClubMobilleJoiService();
	
				VehicleResponse response = service.getOffers(request, false, 100, false );
				
				ClubMobilModelProvider.INSTANCE.updateResponse(response);
				
				offerListTable.update();
			}


		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			
		}

	}
	private class SelectCarClubMobilDoubleClickListener extends AdacSelectOfferDoubleClickListener {
		
		@Override
		public void doubleClick(DoubleClickEvent event) {

			TableViewer viewer = (TableViewer) event.getViewer();
			IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
			
			Object selectedNode = thisSelection.getFirstElement();

			OfferDo offer = (OfferDo) selectedNode;

			logger.info("selectedNodeOffer " + offer.getModel().getVehicle().getCarId());
			
			carId.setValue(offer.getModel().getVehicle().getCarId());
			
			carInfo.setSelectedValue(offer.getModel().getVehicle().getManufacturer());
			
			licensePlateCtl.setValue(offer.getModel().getVehicle().getLicensePlate());
			
			ClubMobilModelProvider.INSTANCE.setSelectedOffer(offer);
			
			
		}


	}	
	
}
