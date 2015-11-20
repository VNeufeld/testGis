package com.dev.gis.app.view.elements;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.model.GetStationService;
import com.dev.gis.app.model.IStationService;
import com.dev.gis.app.model.StationModel;
import com.dev.gis.app.taskmanager.SunnyCarsView.SelectPickupStationDialog;
import com.dev.gis.connector.api.SunnyModelProvider;

public class GetPickupStationControl extends BasicControl{
	
	private OutputTextControls puTextControls = null;
	
	public Composite create(final Composite parent) {
		
		Composite composite = createComposite(parent,3,1, true);
		
		puTextControls = new OutputTextControls(composite, "PickupStation",-1,1);
		
		AddPickupStationsListener ap = new AddPickupStationsListener(composite.getShell());
		
		new ButtonControl(composite, " Get PickupStations ",ap);
		
		return composite;
	}

	protected class AddPickupStationsListener extends AbstractListener{
		private final Shell shell;
	
		public AddPickupStationsListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {

			String offerId = SunnyModelProvider.INSTANCE.getSelectedOfferId();
			
			IStationService stationService = new GetStationService();
			
			SelectPickupStationDialog mpd = new SelectPickupStationDialog(shell, offerId, stationService);
			if (mpd.open() == Dialog.OK) {
				StationModel st = mpd.getSelectedStation();
				if ( st != null) {
					puTextControls.setValue(st.getId()+ " "+st.getName());
					if ( st.getId() != null)
						SunnyModelProvider.INSTANCE.selectedPickupStationId = st.getId().longValue();
				}
			}
		}
	}

	public OutputTextControls getPuTextControls() {
		return puTextControls;
	}

	
}
