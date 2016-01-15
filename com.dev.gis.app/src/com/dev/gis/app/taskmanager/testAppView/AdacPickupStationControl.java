package com.dev.gis.app.taskmanager.testAppView;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.model.AdacGetStationService;
import com.dev.gis.app.model.GetStationService;
import com.dev.gis.app.model.IStationService;
import com.dev.gis.app.model.StationModel;
import com.dev.gis.app.taskmanager.SunnyCarsView.SelectPickupStationDialog;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.GetPickupStationControl;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.OfferDo;

public class AdacPickupStationControl extends GetPickupStationControl {
	
	
	@Override
	protected Composite createControlComposite(Composite parent) {
		return createComposite(parent,4,1, true);
	}
	
	protected AbstractListener getPickupStationListener(Composite composite) {
		return new AdacPickupStationsListener(composite.getShell());
	}
	

	protected class AdacPickupStationsListener extends AbstractListener{
		private final Shell shell;
	
		public AdacPickupStationsListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {

			OfferDo offer = AdacModelProvider.INSTANCE.getSelectedOffer();
			String offerId = "";
			if ( offer != null)
				offerId = offer.getId().toString();
			
			IStationService stationService = new AdacGetStationService(offer);
			
			SelectPickupStationDialog mpd = new AdacSelectPickupStationDialog(shell, offerId, stationService);
			if (mpd.open() == Dialog.OK) {
				StationModel st = mpd.getSelectedStation();
				if ( st != null) {
					puTextControls.setValue(st.getId()+ " "+st.getName());
					if ( st.getId() != null)
						AdacModelProvider.INSTANCE.selectedPickupStationId = st.getId().longValue();
				}
			}
		}
	}
	@Override
	protected void showDetailsButton(Composite composite) {
		ShowStationDeatailsListener ap = new ShowStationDeatailsListener(composite.getShell());
		
		new ButtonControl(composite, " Details ",ap);

	}
	protected class ShowStationDeatailsListener extends AbstractListener{
		private final Shell shell;
	
		public ShowStationDeatailsListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {

		}
	}


}
