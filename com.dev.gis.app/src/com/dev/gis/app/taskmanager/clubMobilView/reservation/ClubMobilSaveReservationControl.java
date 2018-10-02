package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.bpcs.mdcars.json.protocol.CommentListResponse;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilUtils;
import com.dev.gis.app.taskmanager.clubMobilView.UploadDialog;
import com.dev.gis.app.view.elements.BasicControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;

public class ClubMobilSaveReservationControl extends BasicControl {
	
	private final Composite parent;
	
	private OutputTextControls result = null;

	public ClubMobilSaveReservationControl(Composite groupStamp, ClubMobilReservationListControl clubMobilResControl) {
		
		parent = groupStamp;

		Composite ccc = createComposite(groupStamp, 9, -1, false);

		new ButtonControl(ccc, "Show Reservation", 0,  getSavelListener(getShell()));

		new ButtonControl(ccc, "Change Reservation", 0,  getSavelListener(getShell()));
		
		//new ButtonControl(ccc, "CheckOut", 0,  new CheckOutReservationListener(getShell()));

		//new ButtonControl(ccc, "CheckIn", 0,  new CheckInReservationListener(getShell(), clubMobilResControl));

		new ButtonControl(ccc, "Payment", 0,  new PaymentListener(getShell()));
		
		new ButtonControl(ccc, "Cancel", 0,  getSavelListener(getShell()));

		new ButtonControl(ccc, "Upload", 0,  getUploadListener(getShell()));

		new ButtonControl(ccc, "GetComments", 0,  getCommentsListener(getShell(), 1));

		new ButtonControl(ccc, "AddComments", 0,  getCommentsListener(getShell(), 2));
		

	}
	
	private SelectionListener getUploadListener(Shell shell) {
		return new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				UploadDialog dlg = new UploadDialog(null);
				dlg.open();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	private SelectionListener getCommentsListener(Shell shell, int actionCode) {
		return new ClubMobilCommentListener(shell, actionCode);
	}
	
	protected Composite getParent() {
		return parent;
	}

	protected Shell getShell() {
		return parent.getShell();
	}

	protected SelectionListener getSavelListener(Shell shell) {
		return new CheckOutReservationListener(shell);
	}
	
	public OutputTextControls getResult() {
		return result;
	}

	private class ClubMobilCommentListener extends AbstractListener {
		
		private final Shell shell;
		private int actionCode;

		public ClubMobilCommentListener(Shell shell, int actionCode) {
			this.shell = shell;
			this.actionCode = actionCode;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			try {
				int rentalId = 0;
				if ( ClubMobilModelProvider.INSTANCE.selectedReservation  != null) {
					rentalId = ClubMobilModelProvider.INSTANCE.selectedReservation.getRentalId();
					
				}
				
				if ( actionCode == 1) {
				
					JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
					ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
					CommentListResponse response = service.getComments(rentalId);
	
					CommentDialog dialog = new CommentDialog(shell, response);
					if (dialog.open() == Dialog.OK) {
						
					}
				}
			}
			catch(Exception err) {
				ClubMobilUtils.showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
				
			}

		}

	}

}
