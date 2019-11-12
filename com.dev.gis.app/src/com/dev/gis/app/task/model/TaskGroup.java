package com.dev.gis.app.task.model;

import java.util.LinkedList;

public class TaskGroup extends TaskItemBase{
	private LinkedList<TaskItem> tasks = new LinkedList<TaskItem>();
	

	public TaskGroup() {
		super();
	}

	public TaskGroup(String name) {
		super(name);
	}

	/**
	 * @return the tasks
	 */
	public LinkedList<TaskItem> getTasks() {
		return tasks;
	}

	public void addTask(TaskItem task) {
		task.setGroup(this);
		tasks.add(task);
		firePropertyChange("tasks", null, null);
		
	}

	public void removeTask(TaskItem taskItem) {
		tasks.remove(taskItem);
		firePropertyChange("tasks", null, null);
		
	}

}
