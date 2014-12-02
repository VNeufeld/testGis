package com.dev.gis.app.task.model;

import com.dev.gis.task.execution.api.ITaskDataProvider;

public class TaskProjectModelFactory {
	
	public static TaskProjectModel createExampleModel() {
		
		TaskProjectModel model = TaskProjectModel.getInstance();
		
		model.setName("TaskProjectModel");
/*		
		TaskItem taskItem = TaskItem.createTask("locationSearch", "folder_edit.png", "test json http request locationSearch");
		if ( taskItem != null)
			model.getGroups().getFirst().getTasks().add(taskItem);
		model.getGroups().getFirst().getTasks().
			add(TaskItem.createTask("joiGetVehicle", "folder_edit.png", "test json http request GetVehicle"));
		model.getGroups().getFirst().getTasks().
			add(TaskItem.createTask("joiGetVehicleRecalculate", "folder_edit.png", "test json http request Recalculate"));

		model.getGroups().getFirst().getTasks().
			add(TaskItem.createTask(ITaskDataProvider.TASK_APP_PAYMENT, "folder_edit.png", "Payments"));

		model.getGroups().getFirst().getTasks().
		add(TaskItem.createTask(ITaskDataProvider.TASK_APP_BOOKING, "folder_edit.png", "test ADAC APP"));
		
		model.getGroups().getFirst().getTasks().
		add(TaskItem.createTask("googleMap", "folder_edit.png", "test ADAC APP"));
		
		TaskGroup taskGroup = new TaskGroup("HSGW");
		model.getGroups().add(taskGroup);
		taskGroup.getTasks().add(TaskItem.createTask("HSGW GetCars", "folder.png", "test hsgw http request GetCars"));
*/			
		TaskGroup taskGroupEml = new TaskGroup("EML");
		model.getGroups().add(taskGroupEml);
		taskGroupEml.getTasks().add(TaskItem.createTask("EML_Creator", "folder.png", "test create eml"));
		
		TaskGroup taskGroup = new TaskGroup("Logging");
		model.getGroups().add(taskGroup);
		taskGroup.getTasks().add(TaskItem.createTask("Splitt", "folder_edit.png", "Splitt Logging"));
		taskGroup.getTasks().add(TaskItem.createTask("SearchSession", "folder_edit.png", "Search session"));
			
		
		for ( TaskGroup group: model.getGroups()) {
			for ( TaskItem item : group.getTasks())
				item.setGroup(group);
		}

		
		return model;
	}

}
