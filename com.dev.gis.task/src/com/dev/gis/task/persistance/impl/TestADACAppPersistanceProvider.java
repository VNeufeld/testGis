package com.dev.gis.task.persistance.impl;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.dev.gis.task.execution.api.ITaskPersistanceProvider;
import com.dev.gis.task.execution.locationSearch.JoiTask;
import com.dev.gis.task.execution.locationSearch.impl.LocationSearchTask;

public class TestADACAppPersistanceProvider extends
		AbstractPersistanceXmlProvider implements	ITaskPersistanceProvider {

	@Override
	public void load(JoiTask task) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(JoiTask task) throws JAXBException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(JoiTask task) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LocationSearchTask load(String name, Class<LocationSearchTask> class1)
			throws JAXBException {
		// TODO Auto-generated method stub
		return null;
	}

}
