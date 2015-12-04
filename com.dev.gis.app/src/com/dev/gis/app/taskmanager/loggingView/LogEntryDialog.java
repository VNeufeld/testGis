package com.dev.gis.app.taskmanager.loggingView;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.taskmanager.loggingView.service.LogEntry;
import com.dev.gis.app.xmlutils.XmlUtils;

public class LogEntryDialog extends Dialog {
	
	private final static Logger logger = Logger.getLogger(LogEntryDialog.class);
	
	private final LogEntry logEntry;

	public LogEntryDialog(Shell parentShell, LogEntry logEntry) {
		super(parentShell);
	    setShellStyle(getShellStyle() | SWT.RESIZE);
	    this.logEntry = logEntry;
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
		
		int limit = tName.getTextLimit();
		
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
		logger.info("create text " + limit );
		String s = createText(logEntry);
		logger.info("show entry s = "+s.length());
		if ( s.length() > 300000)
			s = s.substring(0,300000);
		tName.setText(s);

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


	private String createText(LogEntry o) {
		StringBuilder sb = new StringBuilder();
		for ( String s : o.getEntry()) {
			sb.append(s);
			sb.append('\n');
		}
		return sb.toString();
	}
	
}
