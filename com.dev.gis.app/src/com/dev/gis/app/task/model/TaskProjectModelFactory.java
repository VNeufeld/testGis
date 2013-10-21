package com.dev.gis.app.task.model;

public class TaskProjectModelFactory {
	
	public static TaskProjectModel createExampleModel() {
		
		TaskProjectModel model = TaskProjectModel.getInstance();
		
		model.setName("TaskProjectModel");
		
		TaskItem taskItem = TaskItem.createTask("locationSearch", "folder_edit.png", "test json http request locationSearch");
		if ( taskItem != null)
			model.getGroups().getFirst().getTasks().add(taskItem);
		model.getGroups().getFirst().getTasks().
			add(TaskItem.createTask("joiGetVehicle", "folder_edit.png", "test json http request GetVehicle"));
		model.getGroups().getFirst().getTasks().
			add(TaskItem.createTask("joiGetVehicleRecalculate", "folder_edit.png", "test json http request Recalculate"));

		model.getGroups().getFirst().getTasks().
		add(TaskItem.createTask("testADAC_App", "folder_edit.png", "test ADAC APP"));
		
		TaskGroup taskGroup = new TaskGroup("HSGW");
		model.getGroups().add(taskGroup);
		taskGroup.getTasks().add(TaskItem.createTask("HSGW GetCars", "folder.png", "test hsgw http request GetCars"));
		
		for ( TaskGroup group: model.getGroups()) {
			for ( TaskItem item : group.getTasks())
				item.setGroup(group);
		}

		
		return model;
	}

}
