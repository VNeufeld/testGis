package com.dev.gis.app.taskmanager.clubMobilView.dispo;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.bpcs.mdcars.json.protocol.DispoPoolListResponse;
import com.bpcs.mdcars.json.protocol.DispositionListResponse;
import com.bpcs.mdcars.json.protocol.GetDispoListRequest;
import com.bpcs.mdcars.model.DispositionInfo;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilUtils;
import com.dev.gis.app.view.elements.BasicControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;

public class ClubMobilChangeDispositionControl extends BasicControl {
	
	private final Composite parent;
	
	private OutputTextControls result = null;
	
	private final ClubMobilDispostionListTable availCarListTable;

	public ClubMobilChangeDispositionControl(Composite groupStamp, ClubMobilDispostionListTable availCarListTable) {
		
		this.availCarListTable = availCarListTable;
		
		parent = groupStamp;

		Composite ccc = createComposite(groupStamp, 3, -1, false);

		new ButtonControl(ccc, "Get Avail Cars", 0,  getAvailCarListListener(getShell()));


	}
	
	protected Composite getParent() {
		return parent;
	}

	protected Shell getShell() {
		return parent.getShell();
	}

//	protected SelectionListener getAddCarListener(Shell shell) {
//		return new ClubMobilAddDispoListener(shell);
//	}

	protected SelectionListener getAvailCarListListener(Shell shell) {
		return new ClubMobilGetAvailCarListListener(shell);
	}
	
	private class ClubMobilAddDispoListener extends AbstractListener {
		
		private final Shell shell;

		public ClubMobilAddDispoListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			try {
				Long stationId = null;
				if ( ClubMobilModelProvider.INSTANCE.dispoStation != null ) {
					stationId =  ClubMobilModelProvider.INSTANCE.dispoStation.getId();
				}
				if ( stationId != null) {
					DispositionInfo dispositionInfo = new DispositionInfo();
					dispositionInfo.setStationId(stationId.intValue());
					AddDispoCarDialog dialog = new AddDispoCarDialog(shell,dispositionInfo);
					if (dialog.open() == Dialog.OK) {
						
					}
					
				}
			}
			catch(Exception err) {
				ClubMobilUtils.showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
				
			}

		}

	}

	public OutputTextControls getResult() {
		return result;
	}
	
	private class ClubMobilGetAvailCarListListener extends AbstractListener {
		
		private final Shell shell;

		public ClubMobilGetAvailCarListListener(Shell shell) {
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

				DispositionListResponse dispositionListResponse = service.getAvailCarList(request);
				
				ClubMobilModelProvider.INSTANCE.dispositionListResponse = dispositionListResponse;
				
				updateTable();
				
			}
			catch(Exception err) {
				showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
				
			}

		}


	}
	
	
	
	private void updateTable() {
		availCarListTable.update();
	}


}
