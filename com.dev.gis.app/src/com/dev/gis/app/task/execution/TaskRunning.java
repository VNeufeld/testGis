package com.dev.gis.app.task.execution;

import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;

import com.bpcs.mdcars.protocol.LocationSearchResult;
import com.dev.gis.app.task.model.TaskExecutonResultModel;
import com.dev.gis.app.taskmanager.IResultView;
import com.dev.gis.app.taskmanager.TaskTreeView;
import com.dev.gis.connector.api.HttpConnectorFactory;
import com.dev.gis.connector.api.IHttpConnector;
import com.dev.gis.task.execution.api.ITaskResult;
import com.dev.gis.task.execution.api.JoiTask;
import com.dev.gis.task.execution.api.LocationSearchTask;
import com.dev.gis.task.execution.api.TaskResultFactory;

public class TaskRunning implements Runnable {
	Random random = new Random();
	private Logger logger = Logger.getLogger(TaskRunning.class);
	
	JoiTask task;
	IResultView resultView;
	public TaskRunning(JoiTask task, IResultView resultView) {
		this.task = task;
		this.resultView = resultView;
	}

	@Override
	public void run() {
		TaskExecutonResultModel.getInstance().addItem("Item "+ Thread.currentThread().getName());
		
		ITaskResult result = executeTask(task);
		
		showResult(result);
	}

	private void showResult(ITaskResult result) {
		logger.info("showResult result :" +result);
		resultView.showResult(result);
	}

	private ITaskResult executeTask(JoiTask task) {
		Date startDate = new Date();
		logger.info("execute task :" +task);		
		try {
			int ms = random.nextInt(10)*100;
			logger.info("execute task sleep : " +ms);		
			Thread.sleep(ms);
			
			LocationSearchTask locationSearchTask = (LocationSearchTask)task;
			IHttpConnector httpConnector = HttpConnectorFactory.createLocationSearchConnector( locationSearchTask);
			LocationSearchResult result = httpConnector.joiLocationSearch(locationSearchTask.getLocation());
			return TaskResultFactory.createLocationResult(result,startDate);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("execute task finisched!");		
		return null;
	}

}
