package com.dev.gis.app.taskmanager.clubMobilView.defects;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
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

import com.bpcs.mdcars.json.protocol.DefectDetailRequest;
import com.bpcs.mdcars.json.protocol.DefectResponse;
import com.bpcs.mdcars.model.DayAndHour;
import com.bpcs.mdcars.model.DefectDescription;
import com.bpcs.mdcars.model.DefectLocation;
import com.bpcs.mdcars.model.DefectLocationClass;
import com.bpcs.mdcars.model.DefectPart;
import com.bpcs.mdcars.model.DefectSize;
import com.bpcs.mdcars.model.DefectType;
import com.bpcs.mdcars.model.MoneyAmount;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilUtils;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.JoiHttpServiceFactory;

public class SelectCarDialog extends Dialog {
	private static Logger logger = Logger.getLogger(SelectCarDialog.class);

	final DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

	private ObjectTextControl carId;

	private ObjectTextControl defectDate;

	private ObjectTextControl receiptDate;
	
	private SelectDefectClassComboBox  defectClassComboBox;

	private SelectDefectLocationComboBox  defectLocationComboBox;

	private SelectDefectPartsComboBox  defectPartsComboBox;
	
	private SelectDefectSizeComboBox  defectSizeComboBox;

	private SelectDefectTypeComboBox  defectTypeComboBox;

	private ObjectTextControl totalAmount;

	
	public SelectCarDialog(Shell parentShell) {
		super(parentShell);
	    setShellStyle(getShellStyle() | SWT.RESIZE);
		
	}
	


	@Override
	protected Control createDialogArea(Composite parent) {
		
		
		
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.getShell().setText("Add Defects. CarId : " );
		
		composite.setLayout(new GridLayout(1, false));

		LocalDate scheduleF = new LocalDate();
		LocalDate schedulet = new LocalDate();
		schedulet = schedulet.plusDays(40);
		
		carId = new ObjectTextControl(composite, 300, false, "CarId ");
		carId.setSelectedValue("223");
		
		defectDate = new ObjectTextControl(composite, 300, false, "defect Date ");
		
		defectDate.setSelectedValue(scheduleF.toString(timeFormatter));
		
		receiptDate = new ObjectTextControl(composite, 300, false, "receipt Date ");
		receiptDate.setSelectedValue(schedulet.toString(timeFormatter));
		
		Composite ccc = createComposite(composite, 3, -1, false);
		
		defectClassComboBox = new SelectDefectClassComboBox(ccc, 80);
		defectLocationComboBox = new SelectDefectLocationComboBox(ccc, 80);
		defectPartsComboBox = new SelectDefectPartsComboBox(ccc, 80);
		defectSizeComboBox = new SelectDefectSizeComboBox(ccc, 80);
		defectTypeComboBox = new SelectDefectTypeComboBox(ccc, 80);
		
		totalAmount = new ObjectTextControl(composite, 300, false, "Total Amount");
		
		new ButtonControl(composite, "Add", addDefectListener());
		
		return composite;
	}
	
	protected SelectionListener addDefectListener() {
		return new ClubMobilAddDefectListener();
	}
	
	private class ClubMobilAddDefectListener implements SelectionListener {
		

		public ClubMobilAddDefectListener() {
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			try {
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
				
				
				DefectDetailRequest request = new DefectDetailRequest();
				DefectDescription info = new DefectDescription();
				request.setDefectDescription(info);
				
				int defectClassId = defectClassComboBox.getSelectedValue();
				if ( defectClassId > 0 ) {
					DefectLocationClass defectLocationClass = new DefectLocationClass();
					defectLocationClass.setId(defectClassId);
					info.setDefectLocationClass(defectLocationClass);
				}

				int defectLocId = defectLocationComboBox.getSelectedValue();
				if ( defectLocId > 0 ) {
					DefectLocation defectLocation = new DefectLocation();
					defectLocation.setId(defectLocId);
					info.setDefectLocation(defectLocation);
				}

				int defectPartId = defectPartsComboBox.getSelectedValue();
				if ( defectPartId > 0 ) {
					DefectPart defectPart = new DefectPart();
					defectPart.setId(defectPartId);
					info.setDefectPart(defectPart);
				}

				int defectSizeId = defectSizeComboBox.getSelectedValue();
				if ( defectSizeId > 0 ) {
					DefectSize defectSize = new DefectSize();
					defectSize.setId(defectSizeId);
					info.setDefectSize(defectSize);
				}

				int defectTypeId = defectTypeComboBox.getSelectedValue();
				if ( defectTypeId > 0 ) {
					DefectType defectType = new DefectType();
					defectType.setId(defectTypeId);
					info.setDefectType(defectType);
				}
				
				LocalDate scheduleF = LocalDate.parse(defectDate.getSelectedValue(), timeFormatter);
				logger.info("defectDate = " + scheduleF.toString(timeFormatter));
				info.setDefectDate(new DayAndHour(scheduleF.toDate()));
				

				LocalDate scheduleT = LocalDate.parse(receiptDate.getSelectedValue(), timeFormatter);
				logger.info("receiptDate = " + scheduleT.toString(timeFormatter));
				info.setReceiptDate(new DayAndHour(scheduleT.toDate()));

				info.setCarId(Integer.valueOf(carId.getSelectedValue()));
				
				String sTotalAmount = totalAmount.getSelectedValue();
				MoneyAmount ma = new MoneyAmount(sTotalAmount, "EUR");
				info.setTotalAmount(ma);
				
				DefectResponse defectResponse = service.addDefect(request);
				
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

	protected Composite createComposite(final Composite parent,int columns, int span, boolean grab) {
		
		GridLayout gd = (GridLayout)parent.getLayout();
		int col = gd.numColumns;
		
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(columns).equalWidth(false).applyTo(composite);

		if ( span < 0)
			GridDataFactory.fillDefaults().span(col, 1)
					.align(SWT.FILL, SWT.BEGINNING).grab(grab, false)
					.applyTo(composite);
		else
			GridDataFactory.fillDefaults().span(span, 1)
			.align(SWT.FILL, SWT.BEGINNING).grab(grab, false)
			.applyTo(composite);


		return composite;
	}
	
}
