package com.dev.gis.app.taskmanager;

import org.apache.log4j.Logger;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.dev.gis.task.execution.api.IResultView;
import com.dev.gis.task.execution.api.ITaskResult;

public class TaskViewUpdater  implements IResultView {
	private Logger logger = Logger.getLogger(TaskViewUpdater.class);
	private int instanceNum = 1;
	
	private String viewID = null;

	public TaskViewUpdater(String viewID) {
		this.viewID = viewID;
	}

	@Override
	public void showResult(final ITaskResult result) {
		logger.info(" showResult ID = "+viewID + " result : " + result);
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					logger.info(" showResult : run instanceNum = "+instanceNum );
					// Show protocol, show results
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					TaskViewAbstract viewPart =  (TaskViewAbstract)wp.showView(
							viewID, 
							Integer.toString(instanceNum), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					viewPart.refresh(result);
					
					instanceNum++;
					
					logger.info(" add view :"+viewPart.getTitle());
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

	}

}
