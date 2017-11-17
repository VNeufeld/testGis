package com.dev.gis.app.taskmanager.clubMobilView;

import java.net.URI;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.view.dialogs.BSCreditCardDialog;
import com.dev.gis.app.view.elements.BasicControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.AdacVehicleHttpService;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.joi.protocol.CreditCard;
import com.dev.gis.connector.joi.protocol.VerifyCreditCardPaymentResponse;

public class ClubMobilLoginControl extends BasicControl {
	
	private final Composite parent;

	public ClubMobilLoginControl(Composite groupStamp) {
		
		parent = groupStamp;

		Composite cc = createComposite(groupStamp, 3, -1, true);
		
		new ButtonControl(cc, "Login", 0,  getLoginListener(getShell()));

		
		new OutputTextControls(cc, "login", 500, 1 );


	}
	
	protected Composite getParent() {
		return parent;
	}

	protected Shell getShell() {
		return parent.getShell();
	}

	protected SelectionListener getLoginListener(Shell shell) {
		return new ClubMobilLoginListener(shell);
	}
	
	private class ClubMobilLoginListener extends AbstractListener {
		
		private final Shell shell;

		public ClubMobilLoginListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			try {
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				AdacVehicleHttpService service = serviceFactory.getAdacVehicleJoiService();
				LoginDialog mpd = new LoginDialog(shell);
				if (mpd.open() == Dialog.OK) {
					showErrors(new com.dev.gis.connector.sunny.Error(1,1," Error in PayPage Verify "));
				}
					
			}
			catch(Exception err) {
				showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
				
			}

		}

	}
	protected void showErrors(com.dev.gis.connector.sunny.Error error) {
		
		String message = "";
		message = message + error.getErrorNumber()+ "  "+ error.getErrorText() + " " + error.getErrorType() + " ;";
		MessageDialog.openError(
				getShell(),"Error",message);
		
	}

}
