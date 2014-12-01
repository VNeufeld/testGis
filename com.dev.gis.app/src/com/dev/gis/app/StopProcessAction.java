package com.dev.gis.app;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.dev.gis.app.task.model.LogEntryModel;
import com.dev.gis.app.taskmanager.TaskTreeView;
import com.dev.gis.app.taskmanager.executionView.TaskExecutionView;


public class StopProcessAction extends Action {

    private final IWorkbenchWindow window;

    StopProcessAction(String text, IWorkbenchWindow window) {
        super(text);
        this.window = window;
        // The id is used to refer to the action in a menu or toolbar
        setId(ICommandIds.CMD_STOP_PROCESS);
        // Associate the action with a pre-defined command, to allow key bindings.
        //setActionDefinitionId(ICommandIds.CMD_OPEN_MESSAGE);
        setImageDescriptor(com.dev.gis.app.Activator.getImageDescriptor("/icons/sample.gif"));
    }

    public void run() {
    	
        MessageDialog.openInformation(window.getShell(), "Open", "Open Message Dialog!");
        
    	LogEntryModel.getInstance().stopProcess();
    }
}