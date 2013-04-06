package com.dev.gis.app.task.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import com.dev.gis.app.task.model.TaskGroup;
import com.dev.gis.app.task.model.TaskItem;
import com.dev.gis.app.task.model.TaskProjectModel;

public class DeleteTaskHandler extends AbstractHandler implements IHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection sel = HandlerUtil.getCurrentSelection(event);
		if (sel instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) sel;
			Object element = selection.getFirstElement();
			if (element instanceof TaskGroup) {
				TaskGroup taskGroup = (TaskGroup) element;
				if (taskGroup.getTasks().size() == 0  ) {
					if (TaskProjectModel.getInstance().getGroups().size() > 1)
						TaskProjectModel.getInstance().deleteTaskGroup(taskGroup);
					else
						MessageDialog.openError(null, "Error", "you can't delete the last group");
				}
				else
					MessageDialog.openError(null, "Error", "Please first delete all task in the group");
					
			}
			else if (element instanceof TaskItem) {
				TaskItem taskItem = (TaskItem) element;
				
				TaskProjectModel.getInstance().deleteTask(taskItem);
				
				
			}
		}
		return null;
			
	}

}
