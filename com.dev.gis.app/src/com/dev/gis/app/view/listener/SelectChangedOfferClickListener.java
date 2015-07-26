package com.dev.gis.app.view.listener;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.connector.api.SunnyOfferDo;


public class SelectChangedOfferClickListener implements ISelectionChangedListener {
	
	private static Logger logger = Logger.getLogger(SelectChangedOfferClickListener.class);

	
	private final Text offerId;

	public SelectChangedOfferClickListener(final Text offerId) {
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
			SunnyOfferDo offer = (SunnyOfferDo) selectedNode;
			if ( offer != null ) {
				offerId.setText(offer.getId().toString());
				logger.info(" select offer "+offer.getId().toString());				
			}
		}
	
	}


}
