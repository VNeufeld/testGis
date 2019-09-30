package com.dev.gis.app.view.listener.adac;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

import com.dev.gis.app.taskmanager.testAppView.OfferViewUpdater;
import com.dev.gis.connector.api.OfferDo;


public class AdacSelectOfferDoubleClickListener implements IDoubleClickListener {
	
	protected Logger logger = Logger.getLogger(getClass());

	public AdacSelectOfferDoubleClickListener() {
		super();
		logger.info("create AdacSelectOfferDoubleClickListener. ");

	}


	@Override
	public void doubleClick(DoubleClickEvent event) {

		TableViewer viewer = (TableViewer) event.getViewer();
		IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
		
		Object selectedNode = thisSelection.getFirstElement();

		OfferDo offer = (OfferDo) selectedNode;

        new OfferViewUpdater().showOffer(offer);

		logger.info("selectedNode " + offer);
		
	}


}
