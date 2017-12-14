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
import com.dev.gis.app.taskmanager.clubMobilView.dispo.ClubMobilDispositionListControl;
import com.dev.gis.app.taskmanager.rentcars.RentCarsAppView;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.task.execution.api.IEditableTask;

public class ClubMobilDispositionView extends RentCarsAppView {
	
	private static Logger logger = Logger.getLogger(ClubMobilDispositionView.class);

	public static final String ID = IEditableTask.ID_ClubMobilDispositionView;
	
	private ClubMobilDispositionListControl clubMobilDispoControl;

	
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
		
		clubMobilDispoControl = new ClubMobilDispositionListControl(composite);
		
	}

	
	@Override
	protected void createBasicControls(final Group groupStamp) {
		
		Composite cc = createComposite(groupStamp, 3, -1, true);
		new ClubMobilServerTextControl(cc);
		new ClubMobilAuthorizationCheckBox(cc,"Authorization");

		new ClubMobilLoginControl(groupStamp);

		
		
	}
	
	public static void updateCustomerControl(final String text) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					logger.info(" showResult : run instanceNum = "+1 );
					// Show protocol, show results
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					ClubMobilDispositionView viewPart =  (ClubMobilDispositionView)wp.showView(
							ID, 
							Integer.toString(1), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					
					ReservationDetails details = ClubMobilModelProvider.INSTANCE.selectedReservation;
					if ( details != null) {
						
						viewPart.getClubMobilDispoControl().update();
					}
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

	}



	public ClubMobilDispositionListControl getClubMobilDispoControl() {
		return clubMobilDispoControl;
	}





	
}
