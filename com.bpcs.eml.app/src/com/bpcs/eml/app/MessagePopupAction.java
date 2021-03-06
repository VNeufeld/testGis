package com.bpcs.eml.app;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;



public class MessagePopupAction extends Action {

    private final IWorkbenchWindow window;

    MessagePopupAction(String text, IWorkbenchWindow window) {
        super(text);
        this.window = window;
        // The id is used to refer to the action in a menu or toolbar
        setId(ICommandIds.CMD_OPEN_MESSAGE);
        // Associate the action with a pre-defined command, to allow key bindings.
        setActionDefinitionId(ICommandIds.CMD_OPEN_MESSAGE);
        setImageDescriptor(Activator.getImageDescriptor("/icons/sample3.gif"));
    }

    public void run() {
    	
		
		PlatformUI.getWorkbench().getActiveWorkbenchWindow() 
		.getActivePage().resetPerspective(); 
		
    	
        MessageDialog.openInformation(window.getShell(), "Open", "Open Message Dialog!");
    }
}