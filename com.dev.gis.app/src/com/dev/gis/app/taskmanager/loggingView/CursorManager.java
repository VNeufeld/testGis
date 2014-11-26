package com.dev.gis.app.taskmanager.loggingView;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Composite;

public class CursorManager {
	final Composite parent;
	
	private Cursor cursor = null;
	
	public CursorManager (final Composite parent) {
		this.parent = parent;
	}
	
	public void setWaitCursor() {
	   if(cursor != null)
           cursor.dispose();
       cursor = new Cursor(parent.getDisplay(), SWT.CURSOR_WAIT);
       parent.getShell().setCursor(cursor);

	}

	public void resetCursor() {
		   if(cursor != null)
	           cursor.dispose();
	       cursor = new Cursor(parent.getDisplay(), SWT.CURSOR_ARROW);
	       parent.getShell().setCursor(cursor);

		}
	
	
}
