package com.dev.gis.app.view.elements;

import org.apache.log4j.Logger;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

public abstract class EditPartControl extends BasicControl{
	private static Logger logger = Logger.getLogger(EditPartControl.class);
	
	private final Composite parent;
	
	public EditPartControl(Composite parent) {
		super();
		this.parent = parent;
	}

	protected void createGroupControl(Composite parent, String label) {
		final Group groupStamp = new Group(parent, SWT.TITLE);
		groupStamp.setText(label);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(groupStamp);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(groupStamp);

		
		createElements(groupStamp);
		
		createButtons(groupStamp);
		

		
	}

	protected abstract void createButtons(Group groupStamp);

	protected abstract void createElements(Group groupStamp);

	protected Composite getParent() {
		return parent;
	}

	protected Display getDisplay() {
		return parent.getDisplay();
	}

	protected Shell getShell() {
		return parent.getShell();
	}
	
}
