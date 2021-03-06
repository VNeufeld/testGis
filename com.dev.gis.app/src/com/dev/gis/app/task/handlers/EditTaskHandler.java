package com.dev.gis.app.task.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import com.dev.gis.app.task.model.TaskGroup;
import com.dev.gis.app.task.model.TaskItem;
import com.dev.gis.app.taskmanager.TaskPropertyDialog;

public class EditTaskHandler extends AbstractHandler implements IHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection sel = HandlerUtil.getCurrentSelection(event);
		Shell shell = HandlerUtil.getActiveShell(event);
		if (sel instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) sel;
			Object element = selection.getFirstElement();
			if (element instanceof TaskGroup) {
				TaskGroup taskGroup = (TaskGroup) element;
				System.out.println(" Edit : "+taskGroup);
			}
			else if (element instanceof TaskItem) {
				TaskItem taskItem = (TaskItem) element;
				System.out.println(" Edit : "+taskItem);
				
				TaskPropertyDialog mpd = new TaskPropertyDialog(shell);
				mpd.setData(taskItem.getDataProvider());
				if (mpd.open() == Dialog.OK) {
					
				}
				
				
			}
		}
		return null;
			
	}

}
