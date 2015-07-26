package com.dev.gis.app.view.elements;

import org.apache.log4j.Logger;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.dev.gis.connector.api.TaskProperties;

public abstract class ObjectsComboBox {
	
	private static Logger logger = Logger.getLogger(ObjectsComboBox.class);
	
	private final int size; 

	private Combo comboList;
	
	protected String selectedValue;

	private final Composite parent;
	
	public ObjectsComboBox(Composite parent, int size) {
		super();
		this.parent = parent;
		this.size = size;
		create();
	}

	public void create() {
		logger.info("create ");
		comboList = createList(parent);
		selectSavedValue();
		comboList.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				selectValue();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		selectValue();
	}
	
	private void selectValue() {
		int index = comboList.getSelectionIndex();
		String st = comboList.getItem(index);
		selectedValue = getSelectedValue(index, st);
		
		setProperties(index);
		
		logSelectedValue(index );

	}

	
	protected void logSelectedValue(int index) {
		logger.info("select index "+ index + " value = "+ selectedValue);
		
	}

	protected abstract String getSelectedValue(int index, String st);

	private void selectSavedValue() {
		int index = getSavedSelectedId();
		comboList.select(index);
	}
	
	protected abstract int getSavedSelectedId();

	private void setProperties(int index) {
		saveProperties(index);
		TaskProperties.getTaskProperties().saveProperty();

	}

	protected abstract void saveProperties(int index);

	private Combo createList(Composite groupStamp) {
		
		new Label(groupStamp, SWT.NONE).setText(getLabel());

		GridLayout gd = (GridLayout)groupStamp.getLayout();
		int col = gd.numColumns;

		final Combo c = new Combo(groupStamp, SWT.READ_ONLY);
		c.setItems(getItems());
		
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(false, false).span(col-1, 1).hint(size, 16).applyTo(c);

		return c;
	}

	protected abstract  String getLabel();

	protected abstract String[] getItems();

}
