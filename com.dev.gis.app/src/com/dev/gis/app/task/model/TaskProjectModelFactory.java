package com.dev.gis.app.task.model;

import com.dev.gis.app.Application;
import com.dev.gis.connector.api.TaskProperties;
import com.dev.gis.task.execution.api.ITaskDataProvider;

public class TaskProjectModelFactory {
	
	public static TaskProjectModel createModel() {
		
		TaskProjectModel model = TaskProjectModel.getInstance();
		
		model.setName("TaskProjectModel");
		
		if ( !Application.ONLY_LOGGING){
			createDefaultModel();
		}

		createLoggingModel();
		
/*
		model.getGroups().getFirst().getTasks().
			add(TaskItem.createTask(ITaskDataProvider.TASK_APP_PAYMENT, "folder_edit.png", "Payments"));

		
		model.getGroups().getFirst().getTasks().
		add(TaskItem.createTask("googleMap", "folder_edit.png", "test ADAC APP"));
		
		TaskGroup taskGroup = new TaskGroup("HSGW");
		model.getGroups().add(taskGroup);
		taskGroup.getTasks().add(TaskItem.createTask("HSGW GetCars", "folder.png", "test hsgw http request GetCars"));
*/			
//		TaskGroup taskGroupEml = new TaskGroup("EML");
//		model.getGroups().add(taskGroupEml);
//		taskGroupEml.getTasks().add(TaskItem.createTask("EML_Creator", "folder.png", "test create eml"));


//		model.getGroups().getFirst().getTasks().
//		add(TaskItem.createTask(ITaskDataProvider.TASK_APP_BOOKING, "folder_edit.png", "test ADAC APP"));
		
			
		
		for ( TaskGroup group: model.getGroups()) {
			for ( TaskItem item : group.getTasks())
				item.setGroup(group);
		}
		
		return model;
	}

	private static void createLoggingModel() {
		TaskProjectModel model = TaskProjectModel.getInstance();
		TaskGroup taskGroup = new TaskGroup("Logging");
		model.getGroups().add(taskGroup);
		taskGroup.getTasks().add(TaskItem.createTask("Splitt", "folder_edit.png", "Splitt Logging"));
		taskGroup.getTasks().add(TaskItem.createTask("SearchSession", "folder_edit.png", "Search session"));
		
	}

	private static void createDefaultModel() {
		TaskProjectModel model = TaskProjectModel.getInstance();
		TaskGroup defaultGroup = new TaskGroup("Default");
		model.getGroups().add(defaultGroup);
		defaultGroup.getTasks().
		add(TaskItem.createTask("3.SunnyCars Test (Joi)", "folder_edit.png", "test json http request SunnyCars"));
		defaultGroup.getTasks().
		add(TaskItem.createTask(ITaskDataProvider.TASK_APP_BOOKING, "folder_edit.png", "test ADAC APP"));
		defaultGroup.getTasks().
		add(TaskItem.createTask("ClubMobil", "folder_edit.png", "ClubMobil APP"));

		defaultGroup.getTasks().
		add(TaskItem.createTask("CM Reservation", "folder_edit.png", "ClubMobil CheckOut APP"));

		defaultGroup.getTasks().
		add(TaskItem.createTask("CM Disposition", "folder_edit.png", "ClubMobil Disposition APP"));
		
	}

}
