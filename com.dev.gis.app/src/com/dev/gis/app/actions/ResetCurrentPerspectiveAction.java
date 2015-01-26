package com.dev.gis.app.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.dev.gis.app.ICommandIds;


public class ResetCurrentPerspectiveAction extends Action {

    private final IWorkbenchWindow window;

    public ResetCurrentPerspectiveAction(String text, IWorkbenchWindow window) {
        super(text);
        this.window = window;
        // The id is used to refer to the action in a menu or toolbar
        setId(ICommandIds.CMD_RESET_PERSPECTIVE);
        // Associate the action with a pre-defined command, to allow key bindings.
        setActionDefinitionId(ICommandIds.CMD_RESET_PERSPECTIVE);
        setImageDescriptor(com.dev.gis.app.Activator.getImageDescriptor("/icons/forward.gif"));
    }

    public void run() {
    	
		PlatformUI.getWorkbench().getActiveWorkbenchWindow() 
		.getActivePage().resetPerspective(); 
		
		//LogViewUpdater.updateView("");
    	
    }
}