package com.dev.gis.app.view.elements;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.model.GetStationService;
import com.dev.gis.app.model.IStationService;
import com.dev.gis.app.model.StationModel;
import com.dev.gis.app.taskmanager.SunnyCarsView.SelectDropoffStationDialog;
import com.dev.gis.connector.api.SunnyModelProvider;

public class GetDropoffStationControl extends BasicControl{
	
	protected OutputTextControls doTextControls = null;
	
	public Composite create(final Composite parent) {
		
		Composite composite = createControlComposite(parent);
		
		doTextControls = new OutputTextControls(composite, "DropoffStation",-1,1);
		
		new ButtonControl(composite, " Get DropoffStations ",getDropoffStationListener(composite));
		
		showDetailsButton(composite);
		
		return composite;
	}
	
	protected Composite createControlComposite(Composite parent) {
		return createComposite(parent,3,1, true);
	}
	protected void showDetailsButton(Composite composite) {
		
	}
	protected AbstractListener getDropoffStationListener(Composite composite) {
		return new AddDropoffStationsListener(composite.getShell());
	}

	protected class AddDropoffStationsListener extends AbstractListener{
		private final Shell shell;
	
		public AddDropoffStationsListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			String offerId = SunnyModelProvider.INSTANCE.getSelectedOfferId();	
				
			IStationService stationService = new GetStationService();

			SelectDropoffStationDialog mpd = new SelectDropoffStationDialog(shell, offerId,stationService);
			if (mpd.open() == Dialog.OK) {
				StationModel st = mpd.getSelectedStation();
				if ( st != null)
					doTextControls.setValue(st.getId()+ " "+st.getName());
			}
		}

	}

	public OutputTextControls getDoTextControls() {
		return doTextControls;
	}
	
}
