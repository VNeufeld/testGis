package com.dev.gis.app.taskmanager;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.masterdetail.IObservableFactory;
import org.eclipse.jface.databinding.viewers.ObservableListTreeContentProvider;
import org.eclipse.jface.databinding.viewers.TreeStructureAdvisor;

import com.dev.gis.app.task.model.TaskGroup;
import com.dev.gis.app.task.model.TaskItem;
import com.dev.gis.app.task.model.TaskProjectModel;


public class TaskContentProvider extends ObservableListTreeContentProvider {

	public TaskContentProvider() {
		super(getObservableListFactory(), getTreeStructureAdvisor());
	}

	// This factory returns an observable list of children for the given parent.
	private static IObservableFactory getObservableListFactory() {
		return new IObservableFactory() {
			public IObservable createObservable(Object parent) {
				if (parent instanceof TaskProjectModel) {
					return BeanProperties.list("groups").observe(parent);
				}
				if (parent instanceof TaskGroup) {
					return BeanProperties.list("tasks").observe(parent);
				}
//				if (parent instanceof TaskItem) {
//					return BeanProperties.value("name").observe(parent);}
				return null;
			}
		};
	}

	// The following is optional, you can pass null as the advisor, but then
	// setSelection() will not find elements that have not been expanded.
	private static TreeStructureAdvisor getTreeStructureAdvisor() {
		return new TreeStructureAdvisor() {
			public Object getParent(Object element) {
				if (element instanceof TaskItem) {
					return ((TaskItem) element).getModel();
				}
				return super.getParent(element);
			}
		};
	}

}
