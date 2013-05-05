package com.dev.gis.app.task.model;

import java.util.Random;

import org.apache.log4j.Logger;

public abstract class TaskItemBase extends ModelObject {
	
	protected Logger logger = Logger.getLogger(getClass().getName());
	
	private long id;
	private String name;
	private String description;
	private String iconName;
	private static Random random = new Random(System.currentTimeMillis());

	public TaskItemBase() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TaskItemBase(String name) {
		super();
		this.name = name;
		this.id = generateId();
	}

	public TaskItemBase(String name,String description, String icon) {
		super();
		this.name = name;
		this.id = generateId();
		this.description = description;
		this.iconName = icon;
	}

	private long generateId() {
		return  Math.abs(random.nextInt());
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		firePropertyChange("name", null, null);
		
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIconName() {
		return iconName;
	}
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public TaskProjectModel getModel() {
		return TaskProjectModel.getInstance();
	}
	Object getDefaultSelection() {
		return null;
	}

}
