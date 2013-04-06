package com.dev.gis.app.task.model;

public class TaskProjectModelFactory {
	
	public static TaskProjectModel createExampleModel() {
		
		TaskProjectModel model = TaskProjectModel.getInstance();
		
		model.setName("TaskProjectModel");
		
		model.getGroups().getFirst().getTasks().
			add(TaskItem.createTask("joiGetVehicle", "folder_edit.png", "test json http request GetVehicle"));
		model.getGroups().getFirst().getTasks().
			add(TaskItem.createTask("joiGetVehicleRecalculate", "folder_edit.png", "test json http request Recalculate"));
		
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
