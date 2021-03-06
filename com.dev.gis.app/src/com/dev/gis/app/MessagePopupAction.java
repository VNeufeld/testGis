package com.dev.gis.app;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.dev.gis.app.taskmanager.TaskTreeView;
import com.dev.gis.app.taskmanager.executionView.TaskExecutionView;
import com.dev.gis.app.taskmanager.loggingView.LogViewUpdater;


public class MessagePopupAction extends Action {

    private final IWorkbenchWindow window;

    MessagePopupAction(String text, IWorkbenchWindow window) {
        super(text);
        this.window = window;
        // The id is used to refer to the action in a menu or toolbar
        setId(ICommandIds.CMD_OPEN_MESSAGE);
        // Associate the action with a pre-defined command, to allow key bindings.
        setActionDefinitionId(ICommandIds.CMD_OPEN_MESSAGE);
        setImageDescriptor(com.dev.gis.app.Activator.getImageDescriptor("/icons/sample3.gif"));
    }

    public void run() {
    	
//		for (IViewPart viewPart : TaskTreeView.executionTaskViews.values() ) {
//			System.out.println("refresh "+viewPart.getTitle()+ " thread : "+Thread.currentThread().getName());
//			((TaskExecutionView)viewPart).refresh();
//			
//		}
		
		PlatformUI.getWorkbench().getActiveWorkbenchWindow() 
		.getActivePage().resetPerspective(); 
		
		LogViewUpdater.updateView("");
    	
        MessageDialog.openInformation(window.getShell(), "Open", "Open Message Dialog!");
    }
}