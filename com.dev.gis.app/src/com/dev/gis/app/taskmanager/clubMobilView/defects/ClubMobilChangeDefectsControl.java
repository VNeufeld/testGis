package com.dev.gis.app.taskmanager.clubMobilView.defects;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.bpcs.mdcars.json.protocol.DefectListFilterRequest;
import com.bpcs.mdcars.json.protocol.DefectListResponse;
import com.bpcs.mdcars.json.protocol.DispositionListResponse;
import com.bpcs.mdcars.json.protocol.GetDispoListRequest;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilUtils;
import com.dev.gis.app.view.elements.BasicControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;

public class ClubMobilChangeDefectsControl extends BasicControl {
	
	private final Composite parent;
	
	private OutputTextControls result = null;
	
	private final ClubMobilDefectsListTable dispoListTable;

	public ClubMobilChangeDefectsControl(Composite groupStamp, ClubMobilDefectsListTable dispoListTable) {
		
		this.dispoListTable = dispoListTable;
		
		parent = groupStamp;

		Composite ccc = createComposite(groupStamp, 3, -1, false);

		new ButtonControl(ccc, "Get DefectList", 0,  getDefectListListener(getShell()));

		new ButtonControl(ccc, "Add Defect", 0,  getAddCarListener(getShell()));
		

	}
	
	protected Composite getParent() {
		return parent;
	}

	protected Shell getShell() {
		return parent.getShell();
	}

	protected SelectionListener getAddCarListener(Shell shell) {
		return new ClubMobilAddDispoListener(shell);
	}

	protected SelectionListener getDefectListListener(Shell shell) {
		return new ClubMobilGetDefectsListener(shell);
	}
	
	private class ClubMobilAddDispoListener extends AbstractListener {
		
		private final Shell shell;

		public ClubMobilAddDispoListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			try {
				AddDefectDialog dialog = new AddDefectDialog(shell);
				if (dialog.open() == Dialog.OK) {
					
				}
			}
			catch(Exception err) {
				err.fillInStackTrace();
				ClubMobilUtils.showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
				
			}

		}

	}

	public OutputTextControls getResult() {
		return result;
	}
	
	private class ClubMobilGetDefectsListener extends AbstractListener {
		
		private final Shell shell;

		public ClubMobilGetDefectsListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			try {
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
				
				Long stationId = null;
				Integer carId = null;
				if ( ClubMobilModelProvider.INSTANCE.dispoStation != null ) {
					stationId =  ClubMobilModelProvider.INSTANCE.dispoStation.getId();
				}
				
				if ( ClubMobilModelProvider.INSTANCE.selectedDispoCar != null ) {
					if ( ClubMobilModelProvider.INSTANCE.selectedDispoCar.getCarId() != null ) {
						carId = ClubMobilModelProvider.INSTANCE.selectedDispoCar.getCarId();
					}
				}
				
				
				DefectListFilterRequest request = new DefectListFilterRequest();
				if (stationId != null)
					request.setStationId(stationId.intValue());
				if ( carId != null) {
					request.setCarId(carId.intValue());
				}

				DefectListResponse defectListResponse = service.getDefectsList(request);
				
				ClubMobilModelProvider.INSTANCE.defectListResponse = defectListResponse;
				
				updateTable();
				
			}
			catch(Exception err) {
				showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
				
			}

		}


	}
	
	private void updateTable() {
		dispoListTable.update();
	}


}
