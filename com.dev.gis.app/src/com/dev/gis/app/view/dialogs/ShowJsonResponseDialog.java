package com.dev.gis.app.view.dialogs;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.xmlutils.XmlUtils;
import com.dev.gis.connector.api.ModelProvider;

public class ShowJsonResponseDialog extends Dialog {

//	private TaskItem currentItem = new TaskItem();
//	private TaskItem tempItem = new TaskItem();
	private String  requestPath = null;
	//private ITaskDataProvider dataProvider;
	private String logText ;

	public ShowJsonResponseDialog(Shell parentShell, final String logText) {
		super(parentShell);
	    setShellStyle(getShellStyle() | SWT.RESIZE);
	    
	    this.logText = ModelProvider.INSTANCE.lastResponse;
		if ( this.logText == null) {
			this.logText = " Kein Response vorhanden .";
		}
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
		tName.setText(logText);

		composite.pack();
		return composite;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
	}


}
