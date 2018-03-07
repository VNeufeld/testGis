package com.dev.gis.app.taskmanager.clubMobilView.defects;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Shell;

import com.bpcs.mdcars.json.protocol.DefectListResponse;
import com.bpcs.mdcars.json.protocol.ReservationResponse;
import com.bpcs.mdcars.model.DefectDescription;
import com.bpcs.mdcars.model.ReservationInfo;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilReservationView;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;


public class ClubMobilSelectDefectDoubleClickListener implements IDoubleClickListener {
	
	private static Logger logger = Logger.getLogger(ClubMobilSelectDefectDoubleClickListener.class);
	
	private final Shell shell;
	public ClubMobilSelectDefectDoubleClickListener(Shell shell) {
		super();
		logger.info("create ClubMobilSelectDefectDoubleClickListener. ");
		this.shell = shell;

	}


	@Override
	public void doubleClick(DoubleClickEvent event) {

		TableViewer viewer = (TableViewer) event.getViewer();
		IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
		
		Object selectedNode = thisSelection.getFirstElement();

    	DefectDescription defect = (DefectDescription) selectedNode;

		logger.info("selectedNode " + defect.getId());
		
		JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
		ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();

		try {
			//DefectListResponse reservationResponse = service.getDefectsList(null);
//			logger.info("select reservation " + reservationResponse.getReservationDetails().getRentalId());
//			ClubMobilModelProvider.INSTANCE.selectedReservation = reservationResponse.getReservationDetails();
//			ClubMobilReservationView.updateCustomerControl(" selected reservation ");
			
			EditDefectDialog editDefectDialog = new EditDefectDialog(shell,defect);
			if (editDefectDialog.open() == Dialog.OK) {
				
			}			

		}
		catch(Exception err) {
			logger.error(err.getMessage(),err);
			showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
		}			
		
	}

	protected void showErrors(com.dev.gis.connector.sunny.Error error) {
		
		String message = "";
		message = message + error.getErrorNumber()+ "  "+ error.getErrorText() + " " + error.getErrorType() + " ;";
		MessageDialog.openError(
				null,"Error",message);
		
	}

}
