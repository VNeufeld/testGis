package com.dev.gis.app.taskmanager.clubMobilView;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

import com.dev.gis.app.view.listener.adac.AdacSelectOfferDoubleClickListener;
import com.dev.gis.connector.api.OfferDo;


public class SelectOfferClubMobilDoubleClickListener extends AdacSelectOfferDoubleClickListener {
	
	@Override
	public void doubleClick(DoubleClickEvent event) {

		TableViewer viewer = (TableViewer) event.getViewer();
		IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
		
		Object selectedNode = thisSelection.getFirstElement();

		OfferDo offer = (OfferDo) selectedNode;

        new ClubMobilOfferViewUpdater().showOffer(offer);

		logger.info("selectedNode " + offer);
		
	}


}
