package com.dev.gis.app.view.elements;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ObjectTextControl extends BasicControl {
	
	private final int size; 
	
	private Text text;
	
	protected String selectedValue;

	private final Composite parent;
	
	public ObjectTextControl(Composite parent, int size) {
		super();
		this.parent = parent;
		this.size = size;
		
		create();
		
		text.setText(getDefaultValue());
		
		selectedValue =  text.getText();
		if ( !StringUtils.isEmpty(selectedValue))
			saveValue(selectedValue);
		
		text.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent arg0) {
				if (arg0.character == '\r') {
					
					String value = text.getText();
					saveValue(value);
				}
			}


			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		text.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void focusLost(FocusEvent arg0) {
				String value = text.getText();
				saveValue(value);
			}
		});

		
	}
	protected String getDefaultValue() {
		return "5";
	}
	public void saveValue(String value) {
		
	}
	
	private void create() {
		
		String label = getLabel();

		new Label(parent, SWT.NONE).setText(label);
		
		GridLayout gd = (GridLayout)parent.getLayout();
		int col = gd.numColumns;
		
		text = new Text(parent, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(false, false).span(col-1, 1).hint(size, 16).applyTo(text);

	}

	protected String getLabel() {
		return " XXX";
	}

}
