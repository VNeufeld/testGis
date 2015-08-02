package com.dev.gis.app.view.elements;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class CheckBox extends BasicControl {
	
	private Button button;

	private final Composite parent;
	
	public CheckBox(Composite parent, String name) {
		
		super();
		this.parent = parent;
		
		button = new Button(this.parent, SWT.CHECK);
		button.setText(name);
	}
	
	public boolean isSelected() {
		return button.getSelection();
	}
	
}
