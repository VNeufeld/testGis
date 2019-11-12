package com.dev.gis.app.taskmanager.testAppView;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.bpcs.mdcars.protocol.Vehicle;
import com.dev.gis.app.model.AdacGetStationService;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.AdacVehicleHttpService;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.VehicleHttpService;
import com.dev.gis.connector.joi.protocol.PayOnePaymentResponse;
import com.dev.gis.connector.joi.protocol.PaymentPayOne;

public class PayOneDialog extends Dialog{

	private ObjectTextControl pseudoCardPan;
	private ObjectTextControl cardType;
	private ObjectTextControl truncatedCardPan;
	private ObjectTextControl cardExpireDate;
	private ObjectTextControl cardOwner;
	private String errorText;
	private Shell parent;
	private ButtonControl button;
	public PayOneDialog(Shell parent) {
		super(parent);
		this.parent = parent;
		// TODO Auto-generated constructor stub
	    setShellStyle(getShellStyle() | SWT.RESIZE);
	}
	@Override
	protected Control createDialogArea(Composite parent) {

		Composite composite = (Composite) super.createDialogArea(parent);
		parent.setLayout(new GridLayout(2, false));
		 pseudoCardPan = new ObjectTextControl(composite, 200, true, "PseudoCardPan");
		 truncatedCardPan = new ObjectTextControl(composite, 200, true, "truncatedCardPan");
		 cardType = new ObjectTextControl(composite, 200, true, "cardType");
		 cardExpireDate = new ObjectTextControl(composite, 200, true, "cardExpireDate");
		 cardOwner = new ObjectTextControl(composite, 200, true, "cardOwner");
		 pseudoCardPan.setSelectedValue("9550010000204200177");
		 truncatedCardPan.setSelectedValue("4111XXXX177");
		 cardType.setSelectedValue("V");
		 cardExpireDate.setSelectedValue("2304");
		 cardOwner.setSelectedValue("Hans Müller");

		return composite;
		
	}

	@Override
	protected void okPressed() {
		// TODO Auto-generated method stub
		long bookingRequestId = Long.parseLong(AdacModelProvider.INSTANCE.getBookingRequestId());
		String pCardPan = pseudoCardPan.getSelectedValue();
		String tCardPan = truncatedCardPan.getSelectedValue();
		String cardTypeString = cardType.getSelectedValue();
		String cardExpire = cardExpireDate.getSelectedValue();
		String owner = cardOwner.getSelectedValue();
		PaymentPayOne ppo = new PaymentPayOne();
		ppo.setBookingRequestId(bookingRequestId);
		ppo.setpCardPan(pCardPan);
		ppo.settCardPan(tCardPan);
		ppo.setCardTypeString(cardTypeString);
		ppo.setCardExpire(cardExpire);
		ppo.setOwner(owner);
		VehicleHttpService service = new JoiHttpServiceFactory().getVehicleJoiService();
		PayOnePaymentResponse popr = service.getPayOnePaymentResult(ppo);
		errorText = popr.getErrors()[0].getErrorText();
		cardOwner.setSelectedValue(errorText);
		MessageBox mb = new MessageBox(this.parent, SWT.ICON_ERROR | SWT.OK);
		mb.setText("Error");
		mb.setMessage(errorText);
		mb.open();
		super.okPressed();
	}

}
