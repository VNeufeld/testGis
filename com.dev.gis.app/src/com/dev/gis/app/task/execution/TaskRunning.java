package com.dev.gis.app.task.execution;

import com.dev.gis.app.task.model.TaskExecutonResultModel;

public class TaskRunning implements Runnable {

	@Override
	public void run() {
		TaskExecutonResultModel.getInstance().addItem("Item "+ Thread.currentThread().getName());

	}

}
