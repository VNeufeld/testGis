package com.dev.gis.app.view.elements;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.task.execution.api.ModelProvider;

public class PageSizeControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(PageSizeControl.class);

	public static void createCityLocationSearch(Composite parent) {
		new PageSizeControl(parent);
	}

	public PageSizeControl(Composite parent) {
		super(parent, 100);
	}

	@Override
	protected String getLabel() {
		return "Page size";
	}


	@Override
	public void saveValue(String value) {
		if ( StringUtils.isEmpty(value))
			value = "5";
		ModelProvider.INSTANCE.pageSize = Long.valueOf(value);
		logger.info("page size : "+value);
//		TaskProperties.getTaskProperties().setServerProperty(
//				serverUrl.getText());
//		TaskProperties.getTaskProperties().saveProperty();
		
	}

}
