package com.dev.gis.app.taskmanager.SunnyCarsView;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.SunnyModelProvider;

public class SunnyExtSupplierFilterTextControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(SunnyExtSupplierFilterTextControl.class);

	public SunnyExtSupplierFilterTextControl(Composite parent) {
		super(parent, 100, false);
	}

	@Override
	protected String getLabel() {
		return "Supplier Filter";
	}


	@Override
	public void saveValue(String value) {
		SunnyModelProvider.INSTANCE.supplierFilter = value;
		logger.info("supplierFilter: "+value);
	
	}

	@Override
	protected String getDefaultValue() {
		return "";
	}

}
