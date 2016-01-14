package com.dev.gis.app.view.elements;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.connector.api.ModelProvider;

public class PageSizeControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(PageSizeControl.class);

	public PageSizeControl(Composite parent) {
		super(parent, 50, false);
	}

	public PageSizeControl(Composite parent, int size,  boolean span) {
		super(parent, size, span);
	}

	@Override
	protected String getLabel() {
		return "Page size";
	}


	@Override
	public void saveValue(String value) {
		if ( StringUtils.isEmpty(value))
			value = "10";
		ModelProvider.INSTANCE.pageSize = Long.valueOf(value);
		logger.info("page size : "+value);
	}
	
	protected String getDefaultValue() {
		return "10";
	}


}
