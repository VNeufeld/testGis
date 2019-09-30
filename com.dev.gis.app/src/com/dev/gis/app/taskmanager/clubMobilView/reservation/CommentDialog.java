package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import java.math.BigDecimal;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.joda.time.DateTime;

import com.bpcs.mdcars.json.protocol.CheckInRequest;
import com.bpcs.mdcars.json.protocol.CommentListResponse;
import com.bpcs.mdcars.json.protocol.CommentMessageRequest;
import com.bpcs.mdcars.json.protocol.ReservationResponse;
import com.bpcs.mdcars.model.CarRentalInfo;
import com.bpcs.mdcars.model.CommentDescription;
import com.bpcs.mdcars.model.DayAndHour;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilCustomerControl;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilEquipmentsControl;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilUtils;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.joi.protocol.Extra;

public class CommentDialog extends AbstractReservationDialog {
	
	private ObjectTextControl reservationNo;

	private ObjectTextControl carInfoText;

	private Text  text;
	private final Shell shell;
	
	private CommentListResponse commentListResponse;
	

	
	public CommentDialog(Shell parentShell, CommentListResponse response) {
		super(parentShell);
		this.shell = parentShell;
		this.commentListResponse = response;
	}

	protected void fillDialogArea(Composite composite) {
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).hint(900, 500).applyTo(composite);
		
		logger.info("fillDialogArea");

		
		reservationNo = new ObjectTextControl(composite, 300, true, "ReservationNo");

		Composite ccc = createComposite(composite, 3, -1, true);
		new ClubMobilCustomerControl(ccc);

		Label lb = new Label(ccc, SWT.NONE);
		lb.setText("CommentList");

		text = new Text(ccc, SWT.BORDER | SWT.MULTI);
		text.setSize(600,  600);
		
		carInfoText = new ObjectTextControl(composite, 300, true, "Neu Comment");


		
		String value = "";
		for ( CommentDescription commentDescription : commentListResponse.getCommentDescriptions() ) {
			String text = commentDescription.getText();
			value = value + text + ";\n";
		}
		text.setText(value);
		
		try {
		
			if ( ClubMobilModelProvider.INSTANCE.selectedReservation  != null) {
				reservationNo.setSelectedValue(ClubMobilModelProvider.INSTANCE.selectedReservation.getReservationNo());
				
			}
		}
		catch(Exception err) {
			logger.error(err.getMessage(),err);
		}
		
		logger.info("fillDialogArea end");


		//PaymentControlControl.createControl(composite);
		
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		
		createButton(parent, IDialogConstants.OK_ID, "Add Comment",	false);

		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, true);

	}

	protected void okPressed() {
		
		if ( addComment())
			setReturnCode(OK);
		else
			setReturnCode(CANCEL);
		close();
	}
	
	protected boolean addComment() {
		try {
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
			
			int rentalId = ClubMobilModelProvider.INSTANCE.selectedReservation.getRentalId();
			
			CommentMessageRequest request = new CommentMessageRequest();
			request.setRentalId(rentalId);
			request.setText(carInfoText.getSelectedValue());
			
			CommentListResponse reservationResponse = service.addComment(request);
			
			MessageDialog.openInformation(null,"Info"," addComment successfull");
			return true;
			
		}
		catch(Exception err) {
			ClubMobilUtils.showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
			
		}
		return false;

	}

	
}
