package com.dev.gis.app.taskmanager;

import org.apache.log4j.Logger;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.dev.gis.task.execution.api.ITaskResult;

public class TaskExecutionViewUpdater implements IResultView {
	private Logger logger = Logger.getLogger(TaskExecutionViewUpdater.class);
	private int instanceNum = 1;

	@Override
	public void showResult(final ITaskResult result) {
		logger.info(" showResult : " + result);
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					logger.info(" showResult : run instanceNum = "+instanceNum );
					// Show protocol, show results
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					TaskExecutionView viewPart = (TaskExecutionView)  wp.showView(
								TaskExecutionView.ID, 
								Integer.toString(instanceNum), 
								IWorkbenchPage.VIEW_ACTIVATE);
					
					viewPart.refresh(result);
					
					//executionTaskViews.put("task "+instanceNum , viewPart);
					instanceNum++;
					
					logger.info(" add view :"+viewPart.getTitle());
					
					
					
				} catch (PartInitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

}
