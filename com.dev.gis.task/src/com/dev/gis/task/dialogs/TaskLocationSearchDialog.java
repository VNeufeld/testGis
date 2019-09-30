package com.dev.gis.task.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.task.execution.api.ITaskDataProvider;
import com.dev.gis.task.execution.api.LocationSearchTask;
import com.dev.gis.task.execution.impl.LocationSearchTaskDataProvider;

public class TaskLocationSearchDialog extends TaskDialogAbstract {
	
	private LocationSearchTask locationSearchTask;
	
	private String searchString;
	

	public TaskLocationSearchDialog(Shell parentShell) {
		super(parentShell);
		 setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.getShell().setText("edit task properties");
		GridLayout glMain = new GridLayout(1, false);
		composite.setLayout(glMain);
		Group gGeneral = new Group(composite, SWT.TITLE);
		gGeneral.setText("Allgemein");
		GridLayout glGeneral = new GridLayout(3, false);
		gGeneral.setLayout(glGeneral);
		GridData gdGeneral = new GridData();
		gdGeneral.horizontalAlignment = SWT.FILL;
		gdGeneral.grabExcessHorizontalSpace = true;
		gGeneral.setLayoutData(gdGeneral);
		Label lName = new Label(gGeneral, SWT.NONE);
		lName.setText("search");
		final Text tName = new Text(gGeneral, SWT.BORDER | SWT.SINGLE);
		GridData gdName = new GridData();
		gdName.horizontalSpan = 2;
		gdName.horizontalAlignment = SWT.FILL;
		gdName.grabExcessHorizontalSpace = true;

		tName.setText(searchString);
		tName.setLayoutData(gdName);
		tName.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				searchString = tName.getText();
			}
		});
		
		composite.pack();
		return composite;		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		super.okPressed();
		if (  this.locationSearchTask != null )
			this.locationSearchTask.setLocation(searchString);
	}

	@Override
	public void setData(ITaskDataProvider dataProvider) {
		super.setData(dataProvider);
		//LocationSearchResult result = new LocationSearchResult();

		
		if ( this.dataProvider instanceof LocationSearchTaskDataProvider )
			this.locationSearchTask = (LocationSearchTask) ((LocationSearchTaskDataProvider) dataProvider).getTask();
		
		if ( this.locationSearchTask != null )
			this.searchString = this.locationSearchTask.getLocation();
		
		
	}

}
