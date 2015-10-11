package com.dev.gis.app.view.elements;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.taskmanager.SunnyCarsView.SelectDropoffStationDialog;
import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.sunny.Station;

public class GetDropoffStationControl extends BasicControl{
	
	OutputTextControls doTextControls = null;
	
	public Composite create(final Composite parent) {
		
		Composite composite = createComposite(parent,3,1, true);
		
		doTextControls = new OutputTextControls(composite, "DropoffStation",-1,1);
		
		AddDropoffStationsListener ap = new AddDropoffStationsListener(composite.getShell());
		
		new ButtonControl(composite, " Get DropoffStations ",ap);
		
		return composite;
	}
	
	protected class AddDropoffStationsListener extends AbstractListener{
		private final Shell shell;
	
		public AddDropoffStationsListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			String offerId = SunnyModelProvider.INSTANCE.getSelectedOfferId();	
				
			SelectDropoffStationDialog mpd = new SelectDropoffStationDialog(shell, offerId);
			if (mpd.open() == Dialog.OK) {
				Station st = mpd.getSelectedStation();
				if ( st != null)
					doTextControls.setValue(st.getId()+ " "+st.getIdentifier());
			}
		}

	}
	
}
