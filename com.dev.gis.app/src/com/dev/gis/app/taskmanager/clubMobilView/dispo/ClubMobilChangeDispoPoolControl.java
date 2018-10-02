package com.dev.gis.app.taskmanager.clubMobilView.dispo;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.bpcs.mdcars.json.protocol.DispoPoolListResponse;
import com.bpcs.mdcars.json.protocol.DispoPoolRequest;
import com.bpcs.mdcars.json.protocol.DispoPoolResponse;
import com.bpcs.mdcars.json.protocol.DispositionListResponse;
import com.bpcs.mdcars.json.protocol.GetDispoListRequest;
import com.bpcs.mdcars.model.DispoPoolCar;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilUtils;
import com.dev.gis.app.view.elements.BasicControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;

public class ClubMobilChangeDispoPoolControl extends BasicControl {
	
	private final Composite parent;
	
	private OutputTextControls result = null;
	
	private final ClubMobilDispoPoolListTable dispoPoolListTable;

	public ClubMobilChangeDispoPoolControl(Composite groupStamp, ClubMobilDispoPoolListTable dispoListTable) {
		
		this.dispoPoolListTable = dispoListTable;
		
		parent = groupStamp;

		Composite ccc = createComposite(groupStamp, 3, -1, false);

		new ButtonControl(ccc, "Get DispoPool", 0,  getDispoPoolListener(getShell()));
		
		//new ButtonControl(ccc, "Add DispoPool", 0,  addDispoPoolListener(getShell()));

		//new ButtonControl(ccc, "Remove DispoPool", 0,  updateDispoPoolListener(getShell()));
		
	}
	
	protected Composite getParent() {
		return parent;
	}

	protected Shell getShell() {
		return parent.getShell();
	}

	protected SelectionListener getDispoPoolListener(Shell shell) {
		return new ClubMobilGetDispoPoolListener(shell);
	}
	
//	protected SelectionListener addDispoPoolListener(Shell shell) {
//		return new ClubMobilAddDispoPoolListener(shell);
//	}

//	protected SelectionListener updateDispoPoolListener(Shell shell) {
//		return new ClubMobilUpdateDispoPoolListener(shell);
//	}
	
	public OutputTextControls getResult() {
		return result;
	}
	
	
	private class ClubMobilGetDispoPoolListener extends AbstractListener {
		
		private final Shell shell;

		public ClubMobilGetDispoPoolListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			try {
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
				
				Long stationId = null;
				if ( ClubMobilModelProvider.INSTANCE.dispoStation != null ) {
					stationId =  ClubMobilModelProvider.INSTANCE.dispoStation.getId();
				}
				
				GetDispoListRequest request = new GetDispoListRequest();
				if (stationId != null)
					request.setStationId(stationId.intValue());

				DispoPoolListResponse dispoPoolListResponse = service.getDispoPoolList(request);
				
				ClubMobilModelProvider.INSTANCE.dispoPoolListResponse = dispoPoolListResponse;
				
				updateTable();
				
			}
			catch(Exception err) {
				showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
				
			}

		}


	}

	private class ClubMobilAddDispoPoolListener extends AbstractListener {
		
		private final Shell shell;

		public ClubMobilAddDispoPoolListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			try {
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
				
				Long stationId = null;
				if ( ClubMobilModelProvider.INSTANCE.dispoStation != null ) {
					stationId =  ClubMobilModelProvider.INSTANCE.dispoStation.getId();
				}
				
				DispoPoolRequest request = new DispoPoolRequest();
				if (stationId != null)
					request.setStationId(stationId.intValue());
				request.setCreatedBy(ClubMobilModelProvider.INSTANCE.loginUser);
				
				DispoPoolCar dispoPoolCar = new DispoPoolCar();
				dispoPoolCar.setCarId(-1);
				request.getDispoPoolCars().add(dispoPoolCar);

				DispoPoolResponse dispoPoolResponse = service.addDispoToPool(request);
				
			}
			catch(Exception err) {
				showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
				
			}

		}


	}

	private class ClubMobilUpdateDispoPoolListener extends AbstractListener {
		
		private final Shell shell;

		public ClubMobilUpdateDispoPoolListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			try {
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
				
				Long stationId = null;
				if ( ClubMobilModelProvider.INSTANCE.dispoStation != null ) {
					stationId =  ClubMobilModelProvider.INSTANCE.dispoStation.getId();
				}
				
				DispoPoolRequest request = new DispoPoolRequest();
				if (stationId != null)
					request.setStationId(stationId.intValue());

				request.setCreatedBy(ClubMobilModelProvider.INSTANCE.loginUser);
				DispoPoolCar dispoPoolCar = new DispoPoolCar();
				if ( ClubMobilModelProvider.INSTANCE.selectedDispoPoolId != null ) {
					dispoPoolCar.setId(ClubMobilModelProvider.INSTANCE.selectedDispoPoolId);
					dispoPoolCar.setResultBy(ClubMobilModelProvider.INSTANCE.loginUser);
					request.getDispoPoolCars().add(dispoPoolCar);
					DispoPoolResponse dispoPoolResponse = service.updateDispoToPool(request);
				}
				else {
					MessageDialog.openError(
							null,"Error","please select a dispoPoolCar with double click");					
				}
				
			}
			catch(Exception err) {
				showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
				
			}

		}


	}
	
	
	private void updateTable() {
		dispoPoolListTable.update();
	}


}
