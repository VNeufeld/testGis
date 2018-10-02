package com.dev.gis.app.taskmanager.clubMobilView.dispo;

import java.util.Date;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.bpcs.mdcars.json.protocol.DispoPoolRequest;
import com.bpcs.mdcars.json.protocol.DispoPoolResponse;
import com.bpcs.mdcars.json.protocol.DispositionDetailRequest;
import com.bpcs.mdcars.json.protocol.DispositionResponse;
import com.bpcs.mdcars.model.DayAndHour;
import com.bpcs.mdcars.model.DispoPoolCar;
import com.bpcs.mdcars.model.DispositionInfo;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilUtils;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;

public class AddDispoCarDialog extends Dialog {
	private static Logger logger = Logger.getLogger(AddDispoCarDialog.class);

	final DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

	private OutputTextControls storageGenId;
	
	private OutputTextControls carId;

	private OutputTextControls carChargeId;
	
	private ObjectTextControl confirmFrom;

	private ObjectTextControl confirmTo;
	
	private ObjectTextControl licensePlate;

	private final DispositionInfo dispoInfo;

	public AddDispoCarDialog(Shell parentShell, DispositionInfo dispoInfo) {
		super(parentShell);
	    setShellStyle(getShellStyle() | SWT.RESIZE);
	    
	    this.dispoInfo = dispoInfo;
		
	}
	


	@Override
	protected Control createDialogArea(Composite parent) {
		
		
		Long stationId = 0l;
		if ( ClubMobilModelProvider.INSTANCE.dispoStation != null ) {
			stationId =  ClubMobilModelProvider.INSTANCE.dispoStation.getId();
		}
		
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.getShell().setText("Add Car to Dispo. Stations : " + stationId);
		
		composite.setLayout(new GridLayout(5, false));

		LocalDate scheduleF = new LocalDate();
		LocalDate schedulet = new LocalDate();
		schedulet = schedulet.plusDays(40);

		storageGenId = new OutputTextControls(composite, "storageGenId", 300);
		storageGenId.setValue(""+dispoInfo.getId());
		
		carChargeId = new OutputTextControls(composite, "carChargeId", 300);
		if ( dispoInfo.getCarRentalInfo() != null)
			carChargeId.setValue(""+dispoInfo.getCarRentalInfo().getCarChargeId());
		
		if ( dispoInfo.getCarId() != null) {
			carId = new OutputTextControls(composite, "CarId", 300);
			carId.setValue(""+dispoInfo.getCarId());
		}
		
		licensePlate = new ObjectTextControl(composite, 300, false, "licensePlate ");
		if ( dispoInfo.getLicensePlate() != null)
			licensePlate.setSelectedValue(""+dispoInfo.getLicensePlate());
		
		Composite buttonAreas = (Composite) super.createDialogArea(parent);
		buttonAreas.setLayout(new GridLayout(4, false));
		
		new ButtonControl(buttonAreas, "Assign Car ( Einsteurung ) ", assignDispoListener(6));

		new ButtonControl(buttonAreas, "Sonderreiningung ( Einsteurung ) ", assignDispoListener(5));

		new ButtonControl(buttonAreas, "Direkte Versetung ( Einsteurung ) ", assignDispoListener(7));
		
		new ButtonControl(buttonAreas, "Add to DispoPool", addToDispoPool());

		new ButtonControl(buttonAreas, "Bereit zur Aussteuerung", removeDispoListener(4));

		new ButtonControl(buttonAreas, "Aussteuerung", removeDispoListener(1));

		new ButtonControl(buttonAreas, "Werkstatt CO", removeDispoListener(2));

		new ButtonControl(buttonAreas, "Direkte Versetzung", removeDispoListener(3));

		
		return composite;
	}
	
	protected SelectionListener assignDispoListener(int code) {
		return new ClubMobilAssignDispoListener(code);
	}
	protected SelectionListener addToDispoPool() {
		return new ClubMobilAddDispoPoolListener();
	}

	protected SelectionListener removeDispoListener(int code) {
		return new ClubMobilRemoveDispoListener(code);
	}
	
	private class ClubMobilAssignDispoListener implements SelectionListener {

		int actionCode;

		public ClubMobilAssignDispoListener(int code) {
			this.actionCode = code;
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
				
				DispositionDetailRequest request = new DispositionDetailRequest();
				request.setActionCode(actionCode);
				
				if ( stationId != null) {
					request.setCheckInStationId(stationId.intValue());
					request.setCheckOutStationId(stationId.intValue());
				}
				
				request.setLicensePlate(licensePlate.getSelectedValue());
				
				
				DispositionResponse dispositionResponse = service.assignDisposition(request);
				if ( dispositionResponse.getErrors() != null && dispositionResponse.getErrors().size() > 0) {
					ClubMobilUtils.showErrors(dispositionResponse.getErrors().get(0));
				}
				
				okPressed();
				
			}
			catch(Exception err) {
				ClubMobilUtils.showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
				
			}

		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			
		}


	}

	private class ClubMobilRemoveDispoListener implements SelectionListener {
		private int code;

		public ClubMobilRemoveDispoListener(int code) {
			this.code = code;
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
				
				// for "direkte versetzung"
				Long targetStationId = stationId;
				
				DispositionDetailRequest request = new DispositionDetailRequest();
				request.setActionCode(code);

				if ( targetStationId != null)
					request.setCheckInStationId(targetStationId.intValue());

				if ( stationId != null)
					request.setCheckOutStationId(stationId.intValue());

				request.setCarId(dispoInfo.getCarId());
				request.setLicensePlate(licensePlate.getSelectedValue());
				
				DayAndHour today = new DayAndHour(new Date());
				request.setCheckOutDate(today);
				
				DispositionInfo info = new DispositionInfo();
				request.setDispositionInfo(info);
				if ( stationId != null)
					info.setStationId(stationId.intValue());
				
				
				info.setCarId(dispoInfo.getCarId());
				info.setId(dispoInfo.getId());
				
				
				DispositionResponse dispositionResponse = service.removeDisposition(request);
				if ( dispositionResponse.getErrors() != null && dispositionResponse.getErrors().size() > 0) {
					ClubMobilUtils.showErrors(dispositionResponse.getErrors().get(0));
				}
				
				okPressed();
				
			}
			catch(Exception err) {
				ClubMobilUtils.showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
				
			}

		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			
		}


	}
	
	private class ClubMobilAddDispoPoolListener implements SelectionListener {
		

		public ClubMobilAddDispoPoolListener() {
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
				dispoPoolCar.setCarId(dispoInfo.getCarId());
				
				request.getDispoPoolCars().add(dispoPoolCar);

				DispoPoolResponse dispoPoolResponse = service.addDispoToPool(request);
				
				if ( dispoPoolResponse.getErrors() != null && dispoPoolResponse.getErrors().size() > 0) {
					ClubMobilUtils.showErrors(dispoPoolResponse.getErrors().get(0));
				}
				okPressed();
				
				
			}
			catch(Exception err) {
				ClubMobilUtils.showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
				
			}

		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			
		}

	}

	
	@Override
	protected void okPressed() {
		super.okPressed();
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
	}
	
	void closeDialog() {
		okPressed();
	}

}
