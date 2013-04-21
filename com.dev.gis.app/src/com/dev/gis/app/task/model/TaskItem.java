/**
 * 
 */
package com.dev.gis.app.task.model;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.dev.gis.task.execution.api.ITaskDataProvider;
import com.dev.gis.task.execution.api.TaskDataProviderFactory;

/**
 * @author Valeri
 *
 */
public class TaskItem extends TaskItemBase {
	private TaskGroup group;
	
	private ITaskDataProvider dataProvider;
	private String requestPath;
	private Map<String,String> customValues = new HashMap<String,String>();
	
	public TaskItem() {
		super();
	}
	public TaskItem(String name, String description, String icon) {
		super(name, description, icon);
	}
	private TaskItem(String name, String description, String icon, ITaskDataProvider dataProvider) {
		super(name, description, icon);
		this.dataProvider = dataProvider;
	}

	public TaskItem(String name) {
		super(name);
	}
	
	public String getRequestPath() {
		return requestPath;
	}
	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}
	public Map<String, String> getCustomValues() {
		return customValues;
	}
	public void setCustomValues(Map<String, String> customValues) {
		this.customValues = customValues;
	}

	
	public static TaskItem createTask(String name, String icon, String decription) {
		ITaskDataProvider dataProvider = TaskDataProviderFactory.createDefaultTaskDataProvider();
		return new TaskItem(name,decription,icon,dataProvider);
	}
	/**
	 * @return the group
	 */
	public TaskGroup getGroup() {
		return group;
	}
	/**
	 * @param group the group to set
	 */
	public void setGroup(TaskGroup group) {
		this.group = group;
	}
	/**
	 * @return the dataProvider
	 */
	public ITaskDataProvider getDataProvider() {
		return dataProvider;
	}
	/**
	 * @param dataProvider the dataProvider to set
	 */
	public void setDataProvider(ITaskDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}
	
}
