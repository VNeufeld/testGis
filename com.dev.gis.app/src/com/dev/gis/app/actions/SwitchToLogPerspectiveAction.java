package com.dev.gis.app.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.dev.gis.app.Activator;
import com.dev.gis.app.ICommandIds;
import com.dev.gis.app.Perspective;
import com.dev.gis.app.taskmanager.loggingView.LogViewUpdater;

public class SwitchToLogPerspectiveAction extends Action {

	private final IWorkbenchWindow window;

	public SwitchToLogPerspectiveAction(String text, IWorkbenchWindow window) {
		super(text);
		this.window = window;
		// The id is used to refer to the action in a menu or toolbar
		setId(ICommandIds.CMD_SWITCH_PERSPECTIVE);
		// Associate the action with a pre-defined command, to allow key
		// bindings.
		setActionDefinitionId(ICommandIds.CMD_SWITCH_PERSPECTIVE);
		setImageDescriptor(com.dev.gis.app.Activator
				.getImageDescriptor("/icons/editconfig.gif"));
	}

	public void run() {

		if (PlatformUI.getWorkbench() != null) {

			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().resetPerspective();

			IPerspectiveDescriptor descriptor = window.getWorkbench()
					.getPerspectiveRegistry()
					.findPerspectiveWithId(Perspective.ID);

			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().setPerspective(descriptor);

			LogViewUpdater.updateView("");

		}

	}
}
