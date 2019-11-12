/**
 * 
 */
package com.dev.gis.task.persistance.impl;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import com.dev.gis.task.execution.api.ITaskPersistanceProvider;
import com.dev.gis.task.execution.api.LocationSearchTask;
import com.dev.gis.task.execution.locationSearch.JoiTask;

/**
 * @author Valeri
 *
 */
public class LocationSearchPersistanceXmlProvider extends AbstractPersistanceXmlProvider implements
		ITaskPersistanceProvider {
	
	private Logger logger = Logger.getLogger(LocationSearchPersistanceXmlProvider.class);


	/* (non-Javadoc)
	 * @see com.dev.gis.task.execution.api.ITaskPersistanceProvider#load(com.dev.gis.task.execution.api.JoiTask)
	 */
	@Override
	public void load(JoiTask task) {
		
		

	}

	/* (non-Javadoc)
	 * @see com.dev.gis.task.execution.api.ITaskPersistanceProvider#save(com.dev.gis.task.execution.api.JoiTask)
	 */
	@Override
	public void save(JoiTask task) throws JAXBException, IOException {
		saveTask(task);

	}


	/* (non-Javadoc)
	 * @see com.dev.gis.task.execution.api.ITaskPersistanceProvider#create(com.dev.gis.task.execution.api.JoiTask)
	 */
	@Override
	public void create(JoiTask task) {

	}

	@Override
	public LocationSearchTask load(String name, Class<LocationSearchTask> class1) throws JAXBException {
		logger.info("load task " + name);
		LocationSearchTask task = loadTask(name,class1);
		if ( task == null )
			task = LocationSearchTask.getDefaultTask();
		logger.info("task : " + task);
		return task;
	}


}
