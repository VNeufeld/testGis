package com.dev.gis.app.taskmanager.clubMobilView;

import org.apache.log4j.Logger;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.bpcs.mdcars.model.ReservationDetails;
import com.dev.gis.app.taskmanager.clubMobilView.defects.ClubMobilDefectsListControl;
import com.dev.gis.app.taskmanager.clubMobilView.dispo.ClubMobilDispositionListControl;
import com.dev.gis.app.taskmanager.rentcars.RentCarsAppView;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.task.execution.api.IEditableTask;

public class ClubMobilDefectsView extends RentCarsAppView {
	
	private static Logger logger = Logger.getLogger(ClubMobilDefectsView.class);

	public static final String ID = IEditableTask.ID_ClubMobilDefectsView;
	
	private ClubMobilDefectsListControl clubMobilControl;

	
	@Override
	public void createPartControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		
		final Group groupStamp = new Group(composite, SWT.TITLE);
		groupStamp.setText("Login:");
		groupStamp.setLayout(new GridLayout(4, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(false, false).applyTo(groupStamp);
		
		createBasicControls(groupStamp);
		
		clubMobilControl = new ClubMobilDefectsListControl(composite);
		
	}

	
	@Override
	protected void createBasicControls(final Group groupStamp) {
		
		Composite cc = createComposite(groupStamp, 3, -1, true);
		new ClubMobilServerTextControl(cc);
		new ClubMobilAuthorizationCheckBox(cc,"Authorization");

		new ClubMobilLoginControl(groupStamp);

		
		
	}


	public ClubMobilDefectsListControl getClubMobilControl() {
		return clubMobilControl;
	}







	
}
