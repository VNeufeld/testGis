package com.dev.gis.app.view.elements;

import org.apache.commons.lang.StringUtils;
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

public abstract class ObjectsComboBox extends BasicControl{
	
	private static Logger logger = Logger.getLogger(ObjectsComboBox.class);
	
	private final int size; 

	private Combo comboList;
	
	protected String selectedValue;

	protected final Composite parent;
	
	public ObjectsComboBox(Composite parent, int size, boolean span) {
		super();
		this.parent = parent;
		this.size = size;
		create(span);
	}

	public void create(boolean span) {
		logger.info("create ");
		comboList = createList(parent, span);
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
		try {
			int index = comboList.getSelectionIndex();
			String st = comboList.getItem(index);
			saveSelected(index,st);
			
			logSelectedValue(index );
		}
		catch(Exception err) {
			
		}

	}

	
	protected abstract  void saveSelected(int index, String st);

	protected void logSelectedValue(int index) {
		logger.info("select index "+ index + " value = "+ selectedValue);
		
	}

	private void selectSavedValue() {
		int index = getSavedSelectedId();
		comboList.select(index);
	}
	
	protected abstract String getPropertyName() ; 
	
	protected int getSavedSelectedId() {
		String value = readProperty(getPropertyName());
		if ( StringUtils.isNotEmpty(value)) 
			return Integer.valueOf(value);
		return 0;
	}


	private Combo createList(Composite groupStamp, boolean span) {
		
		new Label(groupStamp, SWT.NONE).setText(getLabel());

		GridLayout gd = (GridLayout)groupStamp.getLayout();
		int col = gd.numColumns;

		final Combo c = new Combo(groupStamp, SWT.READ_ONLY);
		c.setItems(getItems());
		if ( span )
			GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
					.grab(false, false).span(col-1, 1).hint(size, 16).applyTo(c);
		else
			GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
			.grab(false, false).hint(size, 16).applyTo(c);

		return c;
	}

	protected abstract  String getLabel();

	protected abstract String[] getItems();

}
