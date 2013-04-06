package com.dev.gis.app.task.model;

import java.util.ArrayList;
import java.util.List;

public class Model extends ModelObject {
	private static Model model;
	private List<Task> tasks = new ArrayList<Task>();

	public Model() {
		Task task = new Task();
		task.setName("test_1");
		addTask(task);
		Task task2 = new Task();
		task2.setName("test_12");
		addTask(task2);
	}
	
	public void addTask(final Task task) {
		tasks.add(task);
		firePropertyChange("tasks", null, null);
	}

	public void removeServer(Task child) {
		tasks.remove(child);
		firePropertyChange("servers", null, null);
	}

	public List<Task> getTasks() {
		return tasks;
	}
	
	public Object getDefaultSelection() {
		return tasks.size() > 0 ? tasks.get(0).getDefaultSelection() : null;
	}

	public static Model getInstance() {
		if (model == null) {
			model = new Model();
		}
		return model;
	}
}
