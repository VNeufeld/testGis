package com.dev.gis.app.taskmanager.clubMobilView;

import org.apache.log4j.Logger;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.bpcs.mdcars.json.protocol.DispositionListResponse;
import com.bpcs.mdcars.json.protocol.GetDispoListRequest;
import com.dev.gis.app.taskmanager.clubMobilView.defects.ClubMobilSelectCarDoubleClickListener;
import com.dev.gis.app.taskmanager.clubMobilView.dispo.ClubMobilDispoStationSearch;
import com.dev.gis.app.taskmanager.clubMobilView.dispo.ClubMobilDispostionListTable;
import com.dev.gis.app.view.elements.BasicControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;

public class ClubMobilCarListControl extends BasicControl {
	
	private static Logger logger = Logger.getLogger(ClubMobilCarListControl.class);
	
	private final Composite parent;
	
	private ObjectTextControl selectedCar;
	
	protected ClubMobilDispostionListTable carListTable;

	public ClubMobilCarListControl(Composite groupStamp) {
		
		parent = groupStamp;

		
		addCarsGroup(groupStamp);
		
		logger.info("create ClubMobilCarListControl ");

	}
	
	protected Composite getParent() {
		return parent;
	}

	protected Shell getShell() {
		return parent.getShell();
	}
	
	protected  void addCarsGroup(Composite composite) {
		final Group group = new Group(composite, SWT.TITLE);
		group.setText("CM Cars:");
		group.setLayout(new GridLayout(3, true));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
		.grab(true, true).applyTo(group);
		
		Composite cc2 = createComposite(group, 4, -1, true);
		ClubMobilDispoStationSearch.createDispoStationSearch(cc2); 
		
		ClubMobilSelectCarDoubleClickListener ssd = new ClubMobilSelectCarDoubleClickListener(getShell(),null);
		
		carListTable = new ClubMobilDispostionListTable(null, group, ssd, null);

		Composite cc3 = createComposite(group, 6, -1, true);

		new ButtonControl(cc3, "Get Cars", 0,  new AddGetAvailCarsListener());
		//new ButtonControl(cc3, "Select Car", 0,  new AddSelectExtrasListener());
		selectedCar = new ObjectTextControl(cc3, 100, false, "selected Car");
		ssd.setSelectedCar(selectedCar);
		

	}

	
	protected class AddGetAvailCarsListener extends AbstractListener {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			ClubMobilHttpService service = serviceFactory
					.getClubMobilleJoiService();
			
			
			try {
				Long stationId = null;
				if ( ClubMobilModelProvider.INSTANCE.dispoStation != null ) {
					stationId =  ClubMobilModelProvider.INSTANCE.dispoStation.getId();
				}

				GetDispoListRequest request = new GetDispoListRequest();
				if (stationId != null)
					request.setStationId(stationId.intValue());

				DispositionListResponse dispositionListResponse = service.getDispositionList(request);
				
				ClubMobilUtils.showErrors(dispositionListResponse);
				
				ClubMobilModelProvider.INSTANCE.dispositionListResponse = dispositionListResponse;
				
				carListTable.update();
			}
			catch(Exception err) {
				logger.error(err.getMessage(),err);
				ClubMobilUtils.showErrors(err.getClass().getSimpleName() + " " +err.getMessage());
			}
		}
	}
	
	public void update() {
		carListTable.update();
	}
	
	
}
