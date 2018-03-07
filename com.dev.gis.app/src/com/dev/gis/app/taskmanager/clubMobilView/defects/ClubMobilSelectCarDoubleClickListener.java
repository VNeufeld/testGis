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
import com.bpcs.mdcars.model.DispositionInfo;
import com.bpcs.mdcars.model.ReservationInfo;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilReservationView;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;


public class ClubMobilSelectCarDoubleClickListener implements IDoubleClickListener {
	
	private static Logger logger = Logger.getLogger(ClubMobilSelectCarDoubleClickListener.class);
	
	private final Shell shell;
	private ObjectTextControl selectedCar;
	
	public ClubMobilSelectCarDoubleClickListener(Shell shell, ObjectTextControl selectedCar) {
		super();
		logger.info("create ClubMobilSelectCarDoubleClickListener. ");
		this.shell = shell;
		this.selectedCar = selectedCar;

	}


	@Override
	public void doubleClick(DoubleClickEvent event) {

		TableViewer viewer = (TableViewer) event.getViewer();
		IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
		
		Object selectedNode = thisSelection.getFirstElement();

    	DispositionInfo dispoCar = (DispositionInfo) selectedNode;
    	
		logger.info("selectedNode " + dispoCar.getId());

		try {
			ClubMobilModelProvider.INSTANCE.selectedDispoCar = dispoCar;
			selectedCar.setSelectedValue(dispoCar.getCarId().toString());

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


	public void setSelectedCar(ObjectTextControl selectedCar) {
		this.selectedCar = selectedCar;
	}

}
