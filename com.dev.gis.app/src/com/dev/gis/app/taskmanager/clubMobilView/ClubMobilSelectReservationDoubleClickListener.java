package com.dev.gis.app.taskmanager.clubMobilView;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

import com.bpcs.mdcars.model.ReservationInfo;


public class ClubMobilSelectReservationDoubleClickListener implements IDoubleClickListener {
	
	private static Logger logger = Logger.getLogger(ClubMobilSelectReservationDoubleClickListener.class);

	public ClubMobilSelectReservationDoubleClickListener() {
		super();
		logger.info("create ClubMobilSelectReservationDoubleClickListener. ");

	}


	@Override
	public void doubleClick(DoubleClickEvent event) {

		TableViewer viewer = (TableViewer) event.getViewer();
		IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
		
		Object selectedNode = thisSelection.getFirstElement();

		ReservationInfo reservation = (ReservationInfo) selectedNode;

		logger.info("selectedNode " + reservation.getReservationNo());
		
	}


}
