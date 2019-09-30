package com.dev.gis.app.taskmanager.clubMobilView.defects;

import org.apache.commons.lang.StringUtils;
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
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.bpcs.mdcars.json.protocol.DefectDetailRequest;
import com.bpcs.mdcars.json.protocol.DefectResponse;
import com.bpcs.mdcars.model.DayAndHour;
import com.bpcs.mdcars.model.DefectCategory;
import com.bpcs.mdcars.model.DefectDescription;
import com.bpcs.mdcars.model.DefectLocation;
import com.bpcs.mdcars.model.DefectLocationClass;
import com.bpcs.mdcars.model.DefectPart;
import com.bpcs.mdcars.model.DefectSize;
import com.bpcs.mdcars.model.DefectSpecial;
import com.bpcs.mdcars.model.DefectType;
import com.bpcs.mdcars.model.MoneyAmount;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilUtils;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.JoiHttpServiceFactory;

public class EditDefectDialog extends Dialog {
	private static Logger logger = Logger.getLogger(EditDefectDialog.class);

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
	
	private ObjectTextControl repairStatus;
	
	
	private final DefectDescription defect;

	
	public EditDefectDialog(Shell parentShell, DefectDescription defect) {
		super(parentShell);
	    setShellStyle(getShellStyle() | SWT.RESIZE);
	    this.defect = defect;
		
	}
	


	@Override
	protected Control createDialogArea(Composite parent) {
		
		
		
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.getShell().setText("Edit Defects. CarId : " );
		
		composite.setLayout(new GridLayout(1, false));

		DateTime defectTime = defect.getDefectDate().getDateTime();
		DateTime receiptTime = defect.getReceiptDate().getDateTime();
		
		carId = new ObjectTextControl(composite, 300, false, "CarId ");
		carId.setSelectedValue(""+defect.getCarId());
		
		defectDate = new ObjectTextControl(composite, 300, false, "defect Date ");
		
		defectDate.setSelectedValue(defectTime.toString(timeFormatter));
		
		receiptDate = new ObjectTextControl(composite, 300, false, "receipt Date ");
		receiptDate.setSelectedValue(receiptTime.toString(timeFormatter));
		
		Composite ccc = createComposite(composite, 3, -1, false);
		
		defectClassComboBox = new SelectDefectClassComboBox(ccc, 80);
		if ( defect.getDefectLocationClass() != null)
			defectClassComboBox.selectValue(defect.getDefectLocationClass().getId());
		
		defectLocationComboBox = new SelectDefectLocationComboBox(ccc, 80);
		if ( defect.getDefectLocation() != null)
			defectLocationComboBox.selectValue(defect.getDefectLocation().getId());

		defectPartsComboBox = new SelectDefectPartsComboBox(ccc, 80);
		if ( defect.getDefectPart() != null)
			defectPartsComboBox.selectValue(defect.getDefectPart().getId());
		
		defectSizeComboBox = new SelectDefectSizeComboBox(ccc, 80);
		if ( defect.getDefectSize() != null)
			defectSizeComboBox.selectValue(defect.getDefectSize().getId());

		defectTypeComboBox = new SelectDefectTypeComboBox(ccc, 80);
		if ( defect.getDefectType() != null)
			defectTypeComboBox.selectValue(defect.getDefectType().getId());

		repairStatus = new ObjectTextControl(composite, 300, false, "repairStatus ");
		repairStatus.setSelectedValue(""+defect.getRepairStatus());
		
		totalAmount = new ObjectTextControl(composite, 300, false, "Total Amount");
		if ( defect.getTotalAmount() != null)
			totalAmount.setSelectedValue(defect.getTotalAmount().getAmount());
		
		
		new ButtonControl(composite, "Save", addDefectListener());
		
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
				request.setDefectDescription(defect);
				
				int defectClassId = defectClassComboBox.getSelectedValue();
				if ( defectClassId > 0 ) {
					DefectLocationClass defectLocationClass = new DefectLocationClass();
					defectLocationClass.setId(defectClassId);
					defect.setDefectLocationClass(defectLocationClass);
				}

				int defectLocId = defectLocationComboBox.getSelectedValue();
				if ( defectLocId > 0 ) {
					DefectLocation defectLocation = new DefectLocation();
					defectLocation.setId(defectLocId);
					defect.setDefectLocation(defectLocation);
				}

				int defectPartId = defectPartsComboBox.getSelectedValue();
				if ( defectPartId > 0 ) {
					DefectPart defectPart = new DefectPart();
					defectPart.setId(defectPartId);
					defect.setDefectPart(defectPart);
				}

				int defectSizeId = defectSizeComboBox.getSelectedValue();
				if ( defectSizeId > 0 ) {
					DefectSize defectSize = new DefectSize();
					defectSize.setId(defectSizeId);
					defect.setDefectSize(defectSize);
				}

				int defectTypeId = defectTypeComboBox.getSelectedValue();
				if ( defectTypeId > 0 ) {
					DefectType defectType = new DefectType();
					defectType.setId(defectTypeId);
					defect.setDefectType(defectType);
				}
				
				LocalDate scheduleF = LocalDate.parse(defectDate.getSelectedValue(), timeFormatter);
				logger.info("defectDate = " + scheduleF.toString(timeFormatter));
				defect.setDefectDate(new DayAndHour(scheduleF.toDate()));
				

				LocalDate scheduleT = LocalDate.parse(receiptDate.getSelectedValue(), timeFormatter);
				logger.info("receiptDate = " + scheduleT.toString(timeFormatter));
				defect.setReceiptDate(new DayAndHour(scheduleT.toDate()));

				defect.setCarId(Integer.valueOf(carId.getSelectedValue()));
				
				String sTotalAmount = totalAmount.getSelectedValue();
				MoneyAmount ma = new MoneyAmount(sTotalAmount, "EUR");
				defect.setTotalAmount(ma);
				
				String rs = repairStatus.getSelectedValue();
				if ( StringUtils.isNotEmpty(rs)) {
					defect.setRepairStatus(Integer.valueOf(rs));
				}
				
				defect.setDmpDescription("Test DMP Description BPCS Tester ");
				
				DefectCategory defectCategory = new DefectCategory();
				defectCategory.setId(1);
				defect.setDefectCategory(defectCategory);
				
				DefectSpecial defectSpecial = new DefectSpecial();
				defectSpecial.setId(1);
				defect.setDefectSpecial(defectSpecial);
				
				DefectResponse defectResponse = service.saveDefect(request);
				
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
