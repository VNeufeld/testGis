package com.dev.gis.app.taskmanager;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.dev.gis.app.Activator;
import com.dev.gis.app.task.model.TaskItemBase;
import com.dev.gis.app.task.model.TaskProjectModel;

public class TaskLabelProvider extends ObservableMapLabelProvider {

	private LocalResourceManager resourceManager = new LocalResourceManager(
			JFaceResources.getResources());
	
	private ImageDescriptor serverImage;
	
	Map<String,ImageDescriptor> images = new HashMap<String,ImageDescriptor>();
	
	public TaskLabelProvider(IObservableSet knownElements) {
		// We pass an array of properties so that appropriate listeners
		// are added automatically to each element displayed in the viewer.
		super(Properties.observeEach(knownElements, BeanProperties
				.values(new String[] { "name", "hostname", "messages" })));
		
		initializeImageDescriptors();
	}

	private void initializeImageDescriptors() {
		serverImage = getDescriptor("server.png");
		images.put("server.png", getDescriptor("server.png"));
		images.put("folder.png", getDescriptor("folder.png"));
		images.put("folder_edit.png", getDescriptor("folder_edit.png"));
		images.put("folder_go.png", getDescriptor("folder_go.png"));
		images.put("email.png", getDescriptor("email.png"));
		images.put("folder_delete.png", getDescriptor("folder_delete.png"));
	}

	@Override
	public void dispose() {
		super.dispose();
		resourceManager.dispose();		
	}

	@Override
	public Image getImage(Object element) {
		if ( element instanceof TaskProjectModel)
			return (Image) resourceManager.get(serverImage);
		String iconName = ((TaskItemBase) element).getIconName();
		if ( iconName != null ) {
			return (Image) resourceManager.get(images.get(iconName));
		}
		return (Image) resourceManager.get(images.get("folder_edit.png"));
	}

	@Override
	public String getText(Object element) {
		if (element instanceof TaskItemBase) {
			return ((TaskItemBase) element).getName();
		}
		return null;

	}

	private ImageDescriptor getDescriptor(String fileName) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
				"icons/" + fileName);
	}
	

	
}
