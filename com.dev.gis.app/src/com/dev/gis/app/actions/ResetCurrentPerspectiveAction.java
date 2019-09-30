package com.dev.gis.app.actions;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.dev.gis.app.ICommandIds;
import com.dev.gis.app.SunnyAppPerspective;


public class ResetCurrentPerspectiveAction extends Action {
	
	private static Logger logger = Logger.getLogger(ResetCurrentPerspectiveAction.class);


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
    	
    	IWorkbenchPage p = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    	IPerspectiveDescriptor pp = p.getPerspective();
    	
    	
		PlatformUI.getWorkbench().getActiveWorkbenchWindow() 
		.getActivePage().resetPerspective(); 
		
		//LogViewUpdater.updateView("");
    	
    }
}