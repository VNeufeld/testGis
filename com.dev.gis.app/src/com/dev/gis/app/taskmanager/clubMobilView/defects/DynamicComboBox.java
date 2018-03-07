package com.dev.gis.app.taskmanager.clubMobilView.defects;

import org.apache.log4j.Logger;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.dev.gis.app.view.elements.BasicControl;

public class DynamicComboBox extends BasicControl{
	
	private static Logger logger = Logger.getLogger(DynamicComboBox.class);
	
	private Combo comboList;

	private String dynItems[] ;
	
	private final int size; 
	
	protected int selectedValue;

	protected int[] dynValues = new int[0] ;
	
	protected final Composite parent;
	
	public DynamicComboBox(Composite parent, int size, boolean span) {
		super();
		this.parent = parent;
		this.size = size;
		create(span);
	}

	
	protected void create(boolean span) {
		logger.info("create dymanic combo");
		dynItems = createDynamicItems();
		comboList = createDynamicList(parent, span);
		if (comboList != null ) {
			comboList.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					selectDynValue();
				}
				

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
				}
			});
		}
		
	}
	protected String[] createDynamicItems() {
		dynItems = new String[1];
		dynItems[0] = " Not Defined";
		return dynItems;
	}


	private void selectDynValue() {
		try {
			int index = comboList.getSelectionIndex();
			String st = comboList.getItem(index);
			selectedValue = index;
			logSelectedValue(index, st );
		}
		catch(Exception err) {
			
		}
		
	}



	private Combo createDynamicList(Composite composite, boolean span) {
		new Label(composite, SWT.NONE).setText(getLabel());

		GridLayout gd = (GridLayout)composite.getLayout();
		int col = gd.numColumns;
		try {
			final Combo c = new Combo(composite, SWT.READ_ONLY);
			c.setItems(dynItems);
			if ( span )
				GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
						.grab(false, false).span(col-1, 1).hint(size, 16).applyTo(c);
			else
				GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(false, false).hint(size, 16).applyTo(c);
	
			return c;
		}
		catch(Exception err) {
			err.printStackTrace();
		}
		return null;
	}

	protected String getLabel() {
		return "Dynamic Combo";
	}
	protected String getPropertyName() {
		return "NO";
	}

	public void selectValue(int id) {
		int index = -1;
		for ( int i = 0; i < dynValues.length; i++) {
			if ( dynValues[i] == id) {
				index = i;
				break;
			}
		}
		if ( index > -1) {
			comboList.select(index);
		}
	
	}
	public int getSelectedValue() {
		return selectedValue;
	}

	protected void logSelectedValue(int index, String st) {
		logger.info("select index "+ index + " value = "+ selectedValue + " str : "+st);
		
	}
	
}
