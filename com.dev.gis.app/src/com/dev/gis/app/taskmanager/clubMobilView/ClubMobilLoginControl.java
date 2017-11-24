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

import com.bpcs.mdcars.model.Clerk;
import com.dev.gis.app.view.dialogs.BSCreditCardDialog;
import com.dev.gis.app.view.elements.BasicControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.AdacVehicleHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.joi.protocol.CreditCard;
import com.dev.gis.connector.joi.protocol.VerifyCreditCardPaymentResponse;

public class ClubMobilLoginControl extends BasicControl {
	
	private final Composite parent;
	
	private OutputTextControls result = null;
	private OutputTextControls clerkPartner = null;

	public ClubMobilLoginControl(Composite groupStamp) {
		
		parent = groupStamp;

		final Group group = createGroupSpannAll(groupStamp, "Login",4);

		Composite ccc = createComposite(group, 2, -1, true);
		
		result = new OutputTextControls(ccc, "ClerkInfo", 500, 1 );
		clerkPartner = new OutputTextControls(ccc, "clerkPartner", 500, 1 );

		new ButtonControl(group, "Login", 0,  getLoginListener(getShell(), false));

		new ButtonControl(group, "Change PWD", 0,  getLoginListener(getShell(), true));

		
		

	}
	
	protected Composite getParent() {
		return parent;
	}

	protected Shell getShell() {
		return parent.getShell();
	}

	protected SelectionListener getLoginListener(Shell shell, boolean change_pwd) {
		return new ClubMobilLoginListener(shell, change_pwd);
	}
	
	private class ClubMobilLoginListener extends AbstractListener {
		
		private final Shell shell;
		private final boolean change_pwd;

		public ClubMobilLoginListener(Shell shell, boolean change_pwd) {
			this.shell = shell;
			this.change_pwd = change_pwd;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			try {
				LoginDialog mpd = new LoginDialog(shell,change_pwd);
				if (mpd.open() == Dialog.OK) {
					Clerk clerk = mpd.getClerk();
					if ( clerk != null) {
						ClubMobilModelProvider.INSTANCE.clerk = clerk;
						result.setValue("Clerk : " + clerk.getName() + "," + clerk.getFirstName() + ";  type=" + clerk.getClerkType() + "; typeId="+clerk.getClerkTypeId());
						clerkPartner.setValue(" PartnerNr : " + clerk.getPartnerNr() + "; PartnerArt = " + clerk.getPartnerArt());
						
					}
					else {
						if (!change_pwd) {
							result.setValue("Clerk not found");
						}
						else 
							result.setValue("");
						clerkPartner.setValue("");
						ClubMobilModelProvider.INSTANCE.clerk = null;
					}
						
				}
				else {
					Clerk clerk = ClubMobilModelProvider.INSTANCE.clerk;
					if ( clerk != null) {
						result.setValue("Clerk : " + clerk.getName() + "," + clerk.getFirstName() + ";  type=" + clerk.getClerkType() + "; typeId="+clerk.getClerkTypeId());
						clerkPartner.setValue(" PartnerNr : " + clerk.getPartnerNr() + "; PartnerArt = " + clerk.getPartnerArt());
					}
					else {
						result.setValue("");
						clerkPartner.setValue("");
					}
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
