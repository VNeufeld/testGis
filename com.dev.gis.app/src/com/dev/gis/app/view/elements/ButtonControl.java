package com.dev.gis.app.view.elements;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class ButtonControl extends BasicControl {
	private int size; 
	
	private Button button;

	private final Composite parent;
	
	public ButtonControl(Composite parent, String name, int size, SelectionListener selectionListener) {
		
		super();
		this.parent = parent;
		this.size = size;
		
		button = new Button(parent, SWT.PUSH | SWT.LEFT);
		button.setText(name);
		if ( selectionListener != null)
			button.addSelectionListener(selectionListener);
	}

	public ButtonControl(Composite parent, String name, SelectionListener selectionListener) {
		
		super();
		this.parent = parent;
		
		button = new Button(parent, SWT.PUSH | SWT.LEFT);
		button.setText(name);
		if ( selectionListener != null)
			button.addSelectionListener(selectionListener);
	}
	
}
