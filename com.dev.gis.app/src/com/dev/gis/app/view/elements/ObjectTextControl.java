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
	
	public ObjectTextControl(Composite parent, int size, boolean span) {
		super();
		this.parent = parent;
		this.size = size;
		
		create(span);
		
		text.setText(getDefaultValue());
		
		selectedValue =  text.getText();
		if ( !StringUtils.isEmpty(selectedValue))
			saveValue(selectedValue);
		
		text.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent arg0) {
				if (arg0.character == '\r') {
					
					selectedValue = text.getText();
					saveValue(selectedValue);
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
				selectedValue = text.getText();
				saveValue(selectedValue);
			}
		});

		
	}
	protected String getDefaultValue() {
		return "5";
	}
	public void saveValue(String value) {
		
	}
	
	private void create(boolean span) {
		
		String label = getLabel();

		Label lb = new Label(parent, SWT.NONE);
		lb.setText(label);
		
		int lbSize = getLabelSize();
		if ( lbSize > 0 ) {
			GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
			.grab(false, false).hint(lbSize, 16).applyTo(lb);
		}
		
		GridLayout gd = (GridLayout)parent.getLayout();
		int col = gd.numColumns;
		
		text = new Text(parent, SWT.BORDER | SWT.SINGLE);
		if ( span )
			GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
					.grab(false, false).span(col-1, 1).hint(size, 16).applyTo(text);
		else
			GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
			.grab(false, false).hint(size, 16).applyTo(text);

	}

	protected int getLabelSize() {
		return 0;
	}
	protected String getLabel() {
		return " XXX";
	}
	public String getSelectedValue() {
		return selectedValue;
	}
	
	public void setSelectedValue(String value) {
		selectedValue = value;
		this.text.setText(value);
		saveValue(value);
	}
	protected Text getText() {
		return text;
	}


}
