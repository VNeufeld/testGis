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

public class AddTaskHandler extends AbstractHandler implements IHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection sel = HandlerUtil.getCurrentSelection(event);
		if (sel instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) sel;
			Object element = selection.getFirstElement();
			if (element instanceof TaskGroup) {
				TaskGroup taskGroup = (TaskGroup) element;
				taskGroup.addTask(TaskItem.createTask("neu","email.png", "default"));
			}else if (element instanceof TaskItem) {
				MessageDialog.openWarning(null, "Warn", "Please select a group");
			}
			else if ( element == null) {
				TaskGroup taskGroup = new TaskGroup("group neu");
				taskGroup.setIconName("folder_delete.png");
				TaskProjectModel.getInstance().addGroup(taskGroup);
			}
		}
		return null;
			
	}

}
