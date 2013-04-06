package com.dev.gis.app.task.model;

import java.util.LinkedList;

public class TaskProjectModel extends TaskItemBase {
	private static TaskProjectModel model;

	private LinkedList<TaskGroup> groups = new LinkedList<TaskGroup>();

	public TaskProjectModel() {
		super();
		groups.add(new TaskGroup("Default"));
	}

	/**
	 * @return the taskGroups
	 */
	public LinkedList<TaskGroup> getGroups() {
		return groups;
	}
	public Object getDefaultSelection() {
		return groups.size() > 0 ? groups.get(0).getDefaultSelection() : null;
	}

	public static TaskProjectModel getInstance() {
		if (model == null) {
			model = new TaskProjectModel();
		}
		return model;
	}
	
	public void addGroup(TaskGroup taskGroup) {
		groups.add(taskGroup);
		firePropertyChange("groups", null, null);
		
	}

	public void deleteTaskGroup(TaskGroup taskGroup) {
		groups.remove(taskGroup);
		firePropertyChange("groups", null, null);
	}
	public void deleteTask(TaskItem taskItem) {
		if ( taskItem.getGroup() != null )
			taskItem.getGroup().removeTask(taskItem);
	}


}
