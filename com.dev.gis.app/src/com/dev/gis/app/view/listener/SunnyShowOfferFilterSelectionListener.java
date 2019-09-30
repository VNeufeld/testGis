package com.dev.gis.app.view.listener;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.view.dialogs.OfferFilterDialog;
import com.dev.gis.connector.api.SunnyModelProvider;


public class SunnyShowOfferFilterSelectionListener implements SelectionListener {

	private final Shell shell;
	

	public SunnyShowOfferFilterSelectionListener(final Shell shell) {
		super();
		this.shell = shell;
	}
	

	@Override
	public void widgetDefaultSelected(SelectionEvent event) {
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		OfferFilterDialog mpd = new OfferFilterDialog(shell);
		if (mpd.open() == Dialog.OK) {
			SunnyModelProvider.INSTANCE.offerFilter = mpd.getOfferFilter();
		}
	}

}
