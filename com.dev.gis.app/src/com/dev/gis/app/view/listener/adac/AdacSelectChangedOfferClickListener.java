package com.dev.gis.app.view.listener.adac;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;

import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.OfferDo;


public class AdacSelectChangedOfferClickListener implements ISelectionChangedListener {
	
	private static Logger logger = Logger.getLogger(AdacSelectChangedOfferClickListener.class);

	
	private final OutputTextControls offerId;

	public AdacSelectChangedOfferClickListener(final OutputTextControls offerId) {
		super();
		this.offerId = offerId;
		
		logger.info("create SelectChangedOfferClickListener. offerId = " + offerId );
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		IStructuredSelection thisSelection = (IStructuredSelection) event
				.getSelection();
		Object selectedNode = thisSelection.getFirstElement();

		if ( selectedNode != null ) {
			OfferDo offer = (OfferDo) selectedNode;
			if ( offer != null ) {
				offerId.setValue(offer.getId().toString());
				logger.info(" select offer "+offer.getId().toString());				
			}
		}
	
	}


}
