package com.dev.gis.app.view.listener.adac;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

import com.dev.gis.app.taskmanager.SunnyCarsView.SunnyOfferViewUpdater;
import com.dev.gis.app.taskmanager.offerDetailView.OfferViewUpdater;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.OfferDo;
import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.api.SunnyOfferDo;
import com.dev.gis.connector.api.VehicleHttpService;
import com.dev.gis.connector.sunny.OfferInformation;


public class AdacSelectOfferDoubleClickListener implements IDoubleClickListener {
	
	private static Logger logger = Logger.getLogger(AdacSelectOfferDoubleClickListener.class);

	public AdacSelectOfferDoubleClickListener() {
		super();
		logger.info("create SunnySelectOfferDoubleClickListener. ");

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
