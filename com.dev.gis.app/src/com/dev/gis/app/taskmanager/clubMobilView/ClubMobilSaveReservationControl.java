package com.dev.gis.app.taskmanager.clubMobilView;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.view.elements.BasicControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.JoiHttpServiceFactory;

public class ClubMobilSaveReservationControl extends BasicControl {
	
	private final Composite parent;
	
	private OutputTextControls result = null;

	public ClubMobilSaveReservationControl(Composite groupStamp) {
		
		parent = groupStamp;

		Composite ccc = createComposite(groupStamp, 3, -1, false);

		new ButtonControl(ccc, "Save Reservation", 0,  getSavelListener(getShell()));
		

	}
	
	protected Composite getParent() {
		return parent;
	}

	protected Shell getShell() {
		return parent.getShell();
	}

	protected SelectionListener getSavelListener(Shell shell) {
		return new ClubMobilSaveReservationListener(shell);
	}
	
	private class ClubMobilSaveReservationListener extends AbstractListener {
		
		private final Shell shell;

		public ClubMobilSaveReservationListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			try {
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
				service.saveReservation();
			}
			catch(Exception err) {
				ClubMobilUtils.showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
				
			}

		}

	}

	public OutputTextControls getResult() {
		return result;
	}

}
