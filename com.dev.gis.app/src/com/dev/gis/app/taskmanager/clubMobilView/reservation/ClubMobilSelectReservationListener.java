package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;

import com.bpcs.mdcars.json.protocol.ReservationResponse;
import com.bpcs.mdcars.model.ReservationInfo;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilReservationView;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.OfferDo;


public class ClubMobilSelectReservationListener implements ISelectionChangedListener {
	
	private static Logger logger = Logger.getLogger(ClubMobilSelectReservationListener.class);

	
	private final OutputTextControls reservationInfo;

	public ClubMobilSelectReservationListener(final OutputTextControls reservationInfo) {
		super();
		this.reservationInfo = reservationInfo;
		
		logger.info("create SelectChangedOfferClickListener. reservationInfo = " + reservationInfo );
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		IStructuredSelection thisSelection = (IStructuredSelection) event
				.getSelection();
		Object selectedNode = thisSelection.getFirstElement();

		if ( selectedNode != null ) {

			ReservationInfo reservation = (ReservationInfo) selectedNode;
			
			if ( reservation != null ) {
				logger.info("selectedNode " + reservation.getReservationNo());
				reservationInfo.setValue(reservation.getReservationNo().toString());
				
				ClubMobilModelProvider.INSTANCE.selectedReservationInfo = reservation;
				
			}
		}
	
	}


}
