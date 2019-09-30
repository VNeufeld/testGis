package com.dev.gis.app.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.dev.gis.app.Activator;
import com.dev.gis.app.ICommandIds;
import com.dev.gis.app.SunnyAppPerspective;
import com.dev.gis.app.TestAppPerspective;
import com.dev.gis.app.taskmanager.SunnyCarsView.SunnyCarsAppView;
import com.dev.gis.app.taskmanager.SunnyCarsView.SunnyOfferViewUpdater;
import com.dev.gis.app.taskmanager.testAppView.OfferDetailView;
import com.dev.gis.app.taskmanager.testAppView.TestAppView;

public class SwitchToSunnyAppPerspectiveAction extends Action {

	private final IWorkbenchWindow window;

	public SwitchToSunnyAppPerspectiveAction(String text, IWorkbenchWindow window) {
		super(text);
		this.window = window;
		// The id is used to refer to the action in a menu or toolbar
		setId(ICommandIds.CMD_SWITCH_TO_SUNNY_APP_PERSPECTIVE);
		// Associate the action with a pre-defined command, to allow key
		// bindings.
		setActionDefinitionId(ICommandIds.CMD_SWITCH_TO_SUNNY_APP_PERSPECTIVE);
		setImageDescriptor(com.dev.gis.app.Activator
				.getImageDescriptor("/icons/sample2.gif"));
	}

	public void run() {

		if (PlatformUI.getWorkbench() != null) {

			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().resetPerspective();

			IPerspectiveDescriptor descriptor = window.getWorkbench()
					.getPerspectiveRegistry()
					.findPerspectiveWithId(SunnyAppPerspective.ID);

			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().setPerspective(descriptor);
			
			new SunnyOfferViewUpdater().clearView();

		}

	}
}
