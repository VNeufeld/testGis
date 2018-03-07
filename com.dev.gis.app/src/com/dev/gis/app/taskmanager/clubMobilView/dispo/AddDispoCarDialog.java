package com.dev.gis.app.taskmanager.clubMobilView.dispo;

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

import com.bpcs.mdcars.json.protocol.DispositionDetailRequest;
import com.bpcs.mdcars.json.protocol.DispositionResponse;
import com.bpcs.mdcars.model.DayAndHour;
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
		
		composite.setLayout(new GridLayout(1, false));

		LocalDate scheduleF = new LocalDate();
		LocalDate schedulet = new LocalDate();
		schedulet = schedulet.plusDays(40);

		storageGenId = new OutputTextControls(composite, "storageGenId", 300);
		storageGenId.setValue(""+dispoInfo.getId());
		
		if ( dispoInfo.getCarId() != null) {
			carId = new OutputTextControls(composite, "CarId", 300);
			carId.setValue(""+dispoInfo.getCarId());
		}
		
		licensePlate = new ObjectTextControl(composite, 300, false, "licensePlate ");
		if ( dispoInfo.getLicensePlate() != null)
			licensePlate.setSelectedValue(""+dispoInfo.getLicensePlate());
		
//		confirmFrom = new ObjectTextControl(composite, 300, false, "confirmFrom ");
//		
//		confirmFrom.setSelectedValue(scheduleF.toString(timeFormatter));
//		
//		confirmTo = new ObjectTextControl(composite, 300, false, "scheduleTo ");
//		confirmTo.setSelectedValue(schedulet.toString(timeFormatter));
		
		new ButtonControl(composite, "Assign Car", assignDispoListener());

		new ButtonControl(composite, "Remove Car", removeDispoListener());
		
		return composite;
	}
	
	protected SelectionListener assignDispoListener() {
		return new ClubMobilAssignDispoListener();
	}

	protected SelectionListener removeDispoListener() {
		return new ClubMobilRemoveDispoListener();
	}
	
	private class ClubMobilAssignDispoListener implements SelectionListener {
		

		public ClubMobilAssignDispoListener() {
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
				DispositionInfo info = new DispositionInfo();
				request.setDispositionInfo(info);
				if ( stationId != null)
					info.setStationId(stationId.intValue());
				
				info.setCarId(dispoInfo.getCarId());
				
				info.setId(dispoInfo.getId());
				
//				LocalDate scheduleF = LocalDate.parse(confirmFrom.getSelectedValue(), timeFormatter);
//				logger.info("confirmFrom = " + scheduleF.toString(timeFormatter));
//				info.setScheduleFrom(new DayAndHour(scheduleF.toDate()));
//				
//
//				LocalDate scheduleT = LocalDate.parse(confirmTo.getSelectedValue(), timeFormatter);
//				logger.info("confirmTo = " + scheduleT.toString(timeFormatter));
//				info.setScheduleTo(new DayAndHour(scheduleT.toDate()));

//				info.setCarId(Integer.valueOf(carId.getSelectedValue()));

				info.setLicensePlate(licensePlate.getSelectedValue());
				
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
		

		public ClubMobilRemoveDispoListener() {
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
