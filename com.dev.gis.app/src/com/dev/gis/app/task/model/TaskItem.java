/**
 * 
 */
package com.dev.gis.app.task.model;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Valeri
 *
 */
public class TaskItem extends TaskItemBase {
	private TaskGroup group;
	
	private String requestPath;
	private Map<String,String> customValues = new HashMap<String,String>();
	
	public TaskItem() {
		super();
	}
	public TaskItem(String name, String description, String icon) {
		super(name, description, icon);
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
		
		return new TaskItem(name,decription,icon);
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
	
}
