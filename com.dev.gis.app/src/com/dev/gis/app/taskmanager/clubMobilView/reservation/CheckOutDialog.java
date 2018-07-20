package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilCustomerControl;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilReservationExtrasControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.ClubMobilModelProvider;

public class CheckOutDialog extends AbstractReservationDialog {
	
	private static Logger logger = Logger.getLogger(CheckOutDialog.class);
	
	private ObjectTextControl reservationNo;

	private OutputTextControls carInfo;

	private final Shell shell;
	
	public CheckOutDialog(Shell parentShell) {
		super(parentShell);
		this.shell = parentShell;
	}

	protected void fillDialogArea(Composite composite) {
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).hint(900, 500).applyTo(composite);
		
		try {
		
			logger.info("fillDialogArea");
			
			reservationNo = new ObjectTextControl(composite, 300, true, "ReservationNo");
	
			new ClubMobilCustomerControl(composite);
			
			Composite ccc = createComposite(composite, 4, -1, true);
			
			carInfo = new OutputTextControls(ccc, "Car Info",500, 1);
			new ButtonControl(ccc, "show Car", 0,  new ClubMobilCarInfoListener(getShell()));
			
			new ClubMobilReservationExtrasControl(composite);
			
			if ( ClubMobilModelProvider.INSTANCE.selectedReservation  != null) {
				logger.info("reservationNo");
				
				reservationNo.setSelectedValue(ClubMobilModelProvider.INSTANCE.selectedReservation.getReservationNo());
				
				String carInfoText = "booked car id = ";
	//			carInfoText = carInfoText + ClubMobilModelProvider.INSTANCE.selectedReservation.getCarId() + 
	//					" Type :" + ClubMobilModelProvider.INSTANCE.selectedReservation.getReservationCar().getAcrissCode();
				//carInfoText = carInfoText + ClubMobilModelProvider.INSTANCE.selectedReservation.getCarId();
				
				logger.info("reservationNo2");
				
				carInfo.setValue(carInfoText);
			}
			logger.info("fillDialogArea end");
		}
		catch(Exception ex) {
			logger.error(ex.getMessage(),ex);
		}

	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		
		createButton(parent, IDialogConstants.OK_ID, "Check Out",
				false);

		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, true);

	}

	protected void okPressed() {
		setReturnCode(OK);
		close();
	}

	private class ClubMobilCarInfoListener implements SelectionListener {
		
		private final Shell shell;

		public ClubMobilCarInfoListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			CarInfoDialog mpd = new CarInfoDialog(shell);
			mpd.open();

		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			
		}

	}
	
}
