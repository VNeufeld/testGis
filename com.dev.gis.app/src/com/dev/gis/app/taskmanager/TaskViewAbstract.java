package com.dev.gis.app.taskmanager;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.part.ViewPart;

import com.dev.gis.app.taskmanager.SunnyCarsView.SunnyOfferViewUpdater;
import com.dev.gis.task.execution.api.ITaskResult;

public abstract class TaskViewAbstract extends ViewPart {
	
	protected Logger logger = Logger.getLogger(getClass());


	protected Composite parent;


	protected class AbstractListener implements SelectionListener {

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			widgetSelected(arg0);

		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			// TODO Auto-generated method stub
			
		}

	}
	
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	public void refresh(ITaskResult result) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void createPartControl(Composite parent) {
		
		this.parent = parent;

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		
		createWindow(composite);

	}

	
	protected void createWindow(Composite composite) {
		// TODO Auto-generated method stub
		
	}

	protected Composite createComposite(final Composite parent,int columns, int span, boolean grab) {
		
		GridLayout gd = (GridLayout)parent.getLayout();
		int col = gd.numColumns;
		
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(columns).equalWidth(false).applyTo(composite);

		if ( span < 0)
			GridDataFactory.fillDefaults().span(col, 1)
					.align(SWT.FILL, SWT.BEGINNING).grab(grab, false)
					.applyTo(composite);
		else
			GridDataFactory.fillDefaults().span(span, 1)
			.align(SWT.FILL, SWT.BEGINNING).grab(grab, false)
			.applyTo(composite);


		return composite;
	}

	protected Group createGroupSpannAll(final Composite parent,String title, int columns) {
		
		GridLayout gd = (GridLayout)parent.getLayout();
		int col = gd.numColumns;
		
		final Group groupResult = new Group(parent, SWT.TITLE);
		groupResult.setText(title);		
		GridLayoutFactory.fillDefaults().numColumns(columns).equalWidth(false).applyTo(groupResult);

		GridDataFactory.fillDefaults().span(col, 1)
					.align(SWT.FILL, SWT.BEGINNING).grab(true, false)
					.applyTo(groupResult);

		return groupResult;
	}
	

	protected void showError(String message) {
		MessageDialog.openError(
			parent.getShell(),
			"Error",
			message);
	}

}
