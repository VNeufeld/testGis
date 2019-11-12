package com.dev.gis.app.task.model;




public class Task  extends ModelObject {
	private String name;
	private String description;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	Object getDefaultSelection() {
		return null;
	}
	public Model getModel() {
		return Model.getInstance();
	}

}
