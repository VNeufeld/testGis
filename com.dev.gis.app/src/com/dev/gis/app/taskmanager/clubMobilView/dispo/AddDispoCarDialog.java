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
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;

public class AddDispoCarDialog extends Dialog {
	private static Logger logger = Logger.getLogger(AddDispoCarDialog.class);

	final DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

	private ObjectTextControl carId;

	private ObjectTextControl scheduleFrom;

	private ObjectTextControl scheduleTo;

	public AddDispoCarDialog(Shell parentShell) {
		super(parentShell);
	    setShellStyle(getShellStyle() | SWT.RESIZE);
		
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
		
		carId = new ObjectTextControl(composite, 300, false, "CarId ");
		carId.setSelectedValue("207");
		
		scheduleFrom = new ObjectTextControl(composite, 300, false, "scheduleFrom ");
		
		scheduleFrom.setSelectedValue(scheduleF.toString(timeFormatter));
		
		scheduleTo = new ObjectTextControl(composite, 300, false, "scheduleTo ");
		scheduleTo.setSelectedValue(schedulet.toString(timeFormatter));
		
		new ButtonControl(composite, "Add", addDispoListener());
		
		return composite;
	}
	
	protected SelectionListener addDispoListener() {
		return new ClubMobilAddDispoListener();
	}
	
	private class ClubMobilAddDispoListener implements SelectionListener {
		

		public ClubMobilAddDispoListener() {
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
				
				LocalDate scheduleF = LocalDate.parse(scheduleFrom.getSelectedValue(), timeFormatter);
				logger.info("scheduleF = " + scheduleF.toString(timeFormatter));
				info.setScheduleFrom(new DayAndHour(scheduleF.toDate()));
				

				LocalDate scheduleT = LocalDate.parse(scheduleTo.getSelectedValue(), timeFormatter);
				logger.info("scheduleT = " + scheduleT.toString(timeFormatter));
				info.setScheduleTo(new DayAndHour(scheduleT.toDate()));

				info.setCarId(Integer.valueOf(carId.getSelectedValue()));
				
				DispositionResponse dispositionListResponse = service.addDisposition(request);
				
				okPressed();
//				
//				ClubMobilModelProvider.INSTANCE.dispositionListResponse = dispositionListResponse;
				
				//updateTable();
				
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
