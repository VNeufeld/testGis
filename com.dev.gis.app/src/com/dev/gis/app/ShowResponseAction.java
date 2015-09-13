package com.dev.gis.app;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;

import com.dev.gis.app.view.dialogs.ShowJsonResponseDialog;


public class ShowResponseAction extends Action {

    private final IWorkbenchWindow window;

    ShowResponseAction(String text, IWorkbenchWindow window) {
        super(text);
        this.window = window;
        // The id is used to refer to the action in a menu or toolbar
        setId(ICommandIds.CMD_SHOW_RESPONSE);
        // Associate the action with a pre-defined command, to allow key bindings.
        setActionDefinitionId(ICommandIds.CMD_SHOW_RESPONSE);
        setImageDescriptor(com.dev.gis.app.Activator.getImageDescriptor("/icons/folder_go.png"));
    }

    public void run() {
    	
    	new ShowJsonResponseDialog(window.getShell(),"").open();
    	
    }
}