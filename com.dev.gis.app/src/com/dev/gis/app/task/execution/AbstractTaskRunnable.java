package com.dev.gis.app.task.execution;

import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;

import com.dev.gis.app.taskmanager.IResultView;
import com.dev.gis.task.execution.api.ITaskResult;
import com.dev.gis.task.execution.api.JoiTask;

public abstract class AbstractTaskRunnable implements Runnable {
	protected Logger logger = Logger.getLogger(getClass());

	private JoiTask task;
	
	private IResultView resultView;

	Random random = new Random();

	private boolean debugFlag = false;
	

	public AbstractTaskRunnable(JoiTask task, IResultView resultView) {
		super();
		this.task = task;
		this.resultView = resultView;
	}


	@Override
	public void run() {
		
		ITaskResult result = executeTask(task);
		
		showResult(result);
	}

	protected abstract ITaskResult executeHttpCall(JoiTask task,Date startDate);

	private void showResult(ITaskResult result) {
		logger.info("showResult result :" +result);
		resultView.showResult(result);
	}
	
	
	private ITaskResult executeTask(JoiTask task) {
		Date startDate = new Date();
		logger.info("execute task :" +task);		
		if ( debugFlag)
			sleep(10);
			
		ITaskResult result = executeHttpCall(task,startDate);
		logger.info("execute task finisched! taskResult = "+result);	
		return result;
	}


	private void sleep(int randomSekunden) {
		try {
			int ms = random.nextInt(randomSekunden)*100;
			logger.info("execute task sleep : " +ms);		
			Thread.sleep(ms);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}



	
}
