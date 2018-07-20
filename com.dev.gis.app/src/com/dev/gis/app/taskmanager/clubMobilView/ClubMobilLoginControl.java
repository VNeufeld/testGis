package com.dev.gis.app.taskmanager.clubMobilView;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.bpcs.mdcars.model.Clerk;
import com.dev.gis.app.view.elements.BasicControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.ClubMobilModelProvider;

public class ClubMobilLoginControl extends BasicControl {
	
	private final Composite parent;
	
	private OutputTextControls result = null;
	//private OutputTextControls clerkPartner = null;

	public ClubMobilLoginControl(Composite groupStamp) {
		
		parent = groupStamp;

		final Group group = createGroupSpannAll(groupStamp, "Login",4);

		

		Composite ccc = createComposite(group, 3, -1, false);
		result = new OutputTextControls(ccc, "ClerkInfo", 500, 1 );
		new ButtonControl(ccc, "Login", 0,  getLoginListener(getShell(), false));
		//clerkPartner = new OutputTextControls(ccc, "clerkPartner", 500, 1 );


		//new ButtonControl(group, "Change PWD", 0,  getLoginListener(getShell(), true));

		
		

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
			String clerkResult = "";
			try {
				LoginDialog mpd = new LoginDialog(shell);
				if (mpd.open() == Dialog.OK) {
					Clerk clerk = mpd.getClerk();
					if ( clerk != null) {
						clerkResult = "Clerk : " + clerk.getName() + "," + clerk.getFirstName() + ";  type=" + clerk.getClerkType() + "; typeId="+clerk.getClerkTypeId();
						clerkResult = clerkResult + " " + " PartnerNr : " + clerk.getPartnerNr() + "; PartnerArt = " + clerk.getPartnerArt();
						ClubMobilModelProvider.INSTANCE.clerk = clerk;
						result.setValue(clerkResult);
						
						ClubMobilModelProvider.INSTANCE.sessionToken = mpd.getToken();
						
					}
					else {
						result.setValue("Clerk not found");
						ClubMobilModelProvider.INSTANCE.clerk = null;
					}
						
				}
				else {
					Clerk clerk = ClubMobilModelProvider.INSTANCE.clerk;
					if ( clerk != null) {
						clerkResult = "Clerk : " + clerk.getName() + "," + clerk.getFirstName() + ";  type=" + clerk.getClerkType() + "; typeId="+clerk.getClerkTypeId();
						clerkResult = clerkResult + " " + " PartnerNr : " + clerk.getPartnerNr() + "; PartnerArt = " + clerk.getPartnerArt();
						result.setValue(clerkResult);
					}
					else {
						result.setValue("");
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
