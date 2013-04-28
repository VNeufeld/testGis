package com.dev.gis.app.taskmanager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.xmlutils.XmlUtils;
import com.dev.gis.task.dialogs.TaskDialogAbstract;
import com.dev.gis.task.dialogs.TaskDialogFactory;
import com.dev.gis.task.dialogs.TaskLocationSearchDialog;
import com.dev.gis.task.execution.api.ITaskDataProvider;

public class TaskPropertyDialog extends Dialog {

//	private TaskItem currentItem = new TaskItem();
//	private TaskItem tempItem = new TaskItem();
	private String  requestPath = null;
	private ITaskDataProvider dataProvider;
	private String taskName = "x";

	public TaskPropertyDialog(Shell parentShell) {
		super(parentShell);
	    setShellStyle(getShellStyle() | SWT.RESIZE);

		// setShellStyle(SWT.CLOSE | SWT.MODELESS | SWT.BORDER | SWT.TITLE);
		//
		// setBlockOnOpen(false);
	}

	public void setData(ITaskDataProvider dataProvider) {
		this.dataProvider = dataProvider;
		
		//requestPath = this.dataProvider.
		
		checkResource();
		
	}

	private void checkResource() {
		String file = "/resources/GetCarsRequest/GetCarsRequest.xml";
		//InputStream stream = Class.class.getClassLoader().getResourceAsStream("/GetCarsRequest/GetCarsRequest.xml");
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(file);
		
		StringWriter writer = new StringWriter();

		 try {
			IOUtils.copy(is, writer, "utf-8");
			String result = writer.toString();
			System.out.println("result = "+result);
			
			new XmlUtils().loadXml(file);
			
		} catch (IOException e) {
			e.printStackTrace();
		}


		
		
	}

	@Override
	protected void okPressed() {
		super.okPressed();
//		currentItem.setName(taskName);
//		if ( !StringUtils.isEmpty(requestPath)) {
//			currentItem.setRequestPath(requestPath);
//		}
//		if (!tempItem.getRequestPath().equals(currentItem.getRequestPath()))
//			currentItem.setRequestPath(tempItem.getRequestPath());

	}

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
		lName.setText("Name");
		final Text tName = new Text(gGeneral, SWT.BORDER | SWT.SINGLE);
		GridData gdName = new GridData();
		gdName.horizontalSpan = 2;
		gdName.horizontalAlignment = SWT.FILL;
		gdName.grabExcessHorizontalSpace = true;

		tName.setLayoutData(gdName);
		tName.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				taskName = tName.getText();
			}
		});
		tName.setText(taskName);

		Button bPath = new Button(gGeneral, SWT.PUSH);
		bPath.setText("Pfad ändern");

		final Text tPath = new Text(gGeneral, SWT.BORDER | SWT.SINGLE);
		tPath.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				requestPath = tPath.getText();
			}
		});
//		if (currentItem.getRequestPath()!= null) {
//			tPath.setText(currentItem.getRequestPath().toString());
//		}
		GridData gdPath = new GridData();
		gdPath.horizontalSpan = 1;
		gdPath.horizontalAlignment = SWT.FILL;
		gdPath.grabExcessHorizontalSpace = true;
		gdPath.minimumWidth = 120;
		tPath.setLayoutData(gdPath);
		Button bRelativ = new Button(gGeneral, SWT.CHECK);
		bRelativ.setText("Relativ");
		
		bPath.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(getShell(), SWT.OPEN);
				if (!StringUtils.isEmpty(requestPath))
					fd.setFileName(requestPath);
				String newPath = fd.open();
				if (newPath != null) {
					tPath.setText(newPath);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		Button bDetailDialog = new Button(gGeneral, SWT.PUSH);
		bDetailDialog.setText("Detailsdialog");
		bDetailDialog.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				TaskDialogAbstract dialog = TaskDialogFactory.createTaskDialog(dataProvider,getShell());
				if ( dialog != null) {
					dialog.setData(dataProvider);
					dialog.open();
				}
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

		});
		



		composite.pack();
		return composite;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
	}


}
