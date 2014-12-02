package com.dev.gis.app.taskmanager.loggingView;

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

public class LogEntryDialog extends Dialog {

//	private TaskItem currentItem = new TaskItem();
//	private TaskItem tempItem = new TaskItem();
	private String  requestPath = null;
	//private ITaskDataProvider dataProvider;
	private final String logText ;

	public LogEntryDialog(Shell parentShell, final String logText) {
		super(parentShell);
	    setShellStyle(getShellStyle() | SWT.RESIZE);
	    
	    this.logText = logText;

		// setShellStyle(SWT.CLOSE | SWT.MODELESS | SWT.BORDER | SWT.TITLE);
		//
		// setBlockOnOpen(false);
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
		gGeneral.setText("LogEntry");
		
		GridLayout glGeneral = new GridLayout(1, false);
		gGeneral.setLayout(glGeneral);
		
		GridData gdGeneral = new GridData();
		gdGeneral.horizontalAlignment = SWT.FILL;
		gdGeneral.grabExcessHorizontalSpace = true;
		gGeneral.setLayoutData(gdGeneral);
		
		final Text tName = new Text(gGeneral, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL);
		
		GridData gdName = new GridData();
		gdName.horizontalAlignment = SWT.FILL;
		gdName.grabExcessHorizontalSpace = true;
		gdName.heightHint = 600;
		gdName.widthHint = 800;

		tName.setLayoutData(gdName);
//		tName.addModifyListener(new ModifyListener() {
//
//			@Override
//			public void modifyText(ModifyEvent e) {
//				taskName = tName.getText();
//			}
//		});
		tName.setText(logText);

//		Button bDetailDialog = new Button(gGeneral, SWT.PUSH);
//		bDetailDialog.setText("Detailsdialog");
//		bDetailDialog.addSelectionListener(new SelectionListener() {
//
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				
//			}
//
//			@Override
//			public void widgetDefaultSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//
//		});
		



		composite.pack();
		return composite;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
	}


}
