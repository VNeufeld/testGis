package com.dev.gis.app.taskmanager.testAppView;

import java.util.Calendar;

import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.task.execution.api.ITaskResult;

public class TestAppView extends TaskViewAbstract {
	public static final String ID = "com.dev.gis.app.task.TestAppView";
	private StringFieldEditor city;
	private StringFieldEditor airport;
	@Override
	public void createPartControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		final Group groupStamp = new Group(composite, SWT.TITLE);
		groupStamp.setText("Inputs:");
		groupStamp.setLayout(new GridLayout(4, true));
		groupStamp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		GridData gdFirm = new GridData();
		gdFirm.grabExcessHorizontalSpace = true;
		gdFirm.horizontalAlignment = SWT.FILL;

		Label cityLabel = new Label(groupStamp, SWT.NONE);
		cityLabel.setText("Stadt");
		final Text cityText = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		cityText.setLayoutData(gdFirm);

		Label aptLabel = new Label(groupStamp, SWT.NONE);
		aptLabel.setText("Airport");
		final Text aptText = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		aptText.setLayoutData(gdFirm);

		
		//city.getTextControl(container).getText();
		
		
		final Button buttonPrintDate = new Button(groupStamp, SWT.CHECK | SWT.LEFT);
		buttonPrintDate.setText("Datum drucken");
		buttonPrintDate.setSelection(true);

		final DateTime dateTime = new DateTime(groupStamp, SWT.CALENDAR);
		dateTime.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (buttonPrintDate.getSelection()) {
					Calendar date = Calendar.getInstance();
					date.set(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay());
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		GridData gdDate = new GridData();
		gdDate.grabExcessHorizontalSpace = true;
		gdDate.horizontalAlignment = SWT.FILL;
		dateTime.setLayoutData(gdDate);
		buttonPrintDate.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (buttonPrintDate.getSelection()) {
					Calendar date = Calendar.getInstance();
					date.set(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay());
				} 
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});


		
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(ITaskResult result) {
		// TODO Auto-generated method stub
		
	}

}
