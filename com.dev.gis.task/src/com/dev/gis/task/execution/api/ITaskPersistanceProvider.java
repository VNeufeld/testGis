package com.dev.gis.task.execution.api;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.dev.gis.task.execution.locationSearch.JoiTask;

public interface ITaskPersistanceProvider {
	
	void load ( JoiTask task );

	void save ( JoiTask task ) throws JAXBException, IOException;

	void create ( JoiTask task );

	LocationSearchTask load(String name, Class<LocationSearchTask> class1)  throws JAXBException;
	
}
