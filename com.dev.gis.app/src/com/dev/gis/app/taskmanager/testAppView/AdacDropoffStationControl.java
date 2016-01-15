package com.dev.gis.app.taskmanager.testAppView;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.model.AdacGetStationService;
import com.dev.gis.app.model.IStationService;
import com.dev.gis.app.model.StationModel;
import com.dev.gis.app.taskmanager.SunnyCarsView.SelectDropoffStationDialog;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.GetDropoffStationControl;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.OfferDo;
import com.dev.gis.connector.sunny.Station;

public class AdacDropoffStationControl extends GetDropoffStationControl {
	
	
	@Override
	protected Composite createControlComposite(Composite parent) {
		return createComposite(parent,4,1, true);
	}
	
	@Override
	protected AbstractListener getDropoffStationListener(Composite composite) {
		return new AdacDropoffStationsListener(composite.getShell());
	}

	
	protected class AdacDropoffStationsListener extends AbstractListener{
		private final Shell shell;
	
		public AdacDropoffStationsListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {

			OfferDo offer = AdacModelProvider.INSTANCE.getSelectedOffer();
			String offerId = "";
			if ( offer != null)
				offerId = offer.getId().toString();

			IStationService stationService = new AdacGetStationService(offer);

			SelectDropoffStationDialog mpd = new AdacSelectDropoffStationDialog(shell, offerId, stationService);
			if (mpd.open() == Dialog.OK) {
				StationModel st = mpd.getSelectedStation();
				if ( st != null) {
					doTextControls.setValue(st.getId()+ " "+st.getName());
					AdacModelProvider.INSTANCE.selectedDropoffStationId = st.getId();
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
