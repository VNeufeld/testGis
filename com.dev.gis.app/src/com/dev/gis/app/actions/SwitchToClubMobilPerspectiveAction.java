package com.dev.gis.app.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.dev.gis.app.Activator;
import com.dev.gis.app.ClubMobilPerspective;
import com.dev.gis.app.ICommandIds;
import com.dev.gis.app.TestAppPerspective;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilView;
import com.dev.gis.app.taskmanager.testAppView.OfferDetailView;
import com.dev.gis.app.taskmanager.testAppView.TestAppView;
import com.dev.gis.connector.api.TaskProperties;

public class SwitchToClubMobilPerspectiveAction extends Action {

	private final IWorkbenchWindow window;

	public SwitchToClubMobilPerspectiveAction(String text, IWorkbenchWindow window) {
		super(text);
		this.window = window;
		// The id is used to refer to the action in a menu or toolbar
		setId(ICommandIds.CMD_SWITCH_TO_CLUBMOBIL_PERSPECTIVE);
		// Associate the action with a pre-defined command, to allow key
		// bindings.
		setActionDefinitionId(ICommandIds.CMD_SWITCH_TO_CLUBMOBIL_PERSPECTIVE);
		setImageDescriptor(com.dev.gis.app.Activator
				.getImageDescriptor("/icons/sample2.gif"));
	}

	public void run() {
		// MessageDialog.openInformation(window.getShell(), "Switch",
		// "Switch to App Perspektive!");

		if (PlatformUI.getWorkbench() != null) {

			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().resetPerspective();

			IPerspectiveDescriptor descriptor = window.getWorkbench()
					.getPerspectiveRegistry()
					.findPerspectiveWithId(ClubMobilPerspective.ID);

			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().setPerspective(descriptor);

			IWorkbenchPage wp = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			try {
				wp.showView(ClubMobilView.ID, Integer.toString(0),
						IWorkbenchPage.VIEW_ACTIVATE);
			} catch (PartInitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		TaskProperties.getTaskProperties().setOnlyLogging(false);
		TaskProperties.getTaskProperties().saveProperty();
		

		// if(window != null) {
		// try {
		// window.getActivePage().showView(viewId,
		// Integer.toString(instanceNum++), IWorkbenchPage.VIEW_ACTIVATE);
		// } catch (PartInitException e) {
		// MessageDialog.openError(window.getShell(), "Error",
		// "Error opening view:" + e.getMessage());
		// }
		// }
	}
}
