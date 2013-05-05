/**
 * 
 */
package com.dev.gis.app.task.model;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import com.dev.gis.task.execution.api.ITaskDataProvider;
import com.dev.gis.task.execution.api.TaskDataProviderException;
import com.dev.gis.task.execution.api.TaskDataProviderFactory;

/**
 * @author Valeri
 *
 */
public class TaskItem extends TaskItemBase {
	private static Logger taskItemlogger = Logger.getLogger(TaskItem.class.getName());

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
		try {
			ITaskDataProvider dataProvider = TaskDataProviderFactory.createTaskDataProvider(name);
			return new TaskItem(name,decription,icon,dataProvider);
		}
		catch ( TaskDataProviderException | JAXBException tdpe) {
			taskItemlogger.error("error in create task ",tdpe);
		}
		return null;
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
