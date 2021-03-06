package com.bpcs.app;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.bpcs.app.view.LogEntryTable;
import com.bpcs.app.view.LoggingSettings;
import com.bpcs.app.view.ServerCallback;
import com.dev.http.server.HttpServer;

public class View extends ViewPart {
	public static final String ID = "com.bpcs.app.view";
	
	private final static Logger logger = Logger.getLogger(View.class);
	
	private Text logFileText;
	private Text outputDirText;
	private Text serverStatus;
	
	private Button buttonSplit;
	private Button buttonStop;
	
	private ProgressBar pb;

	private Text currentFileName;
	
	private HttpServer server;
	
	private LogEntryTable  logEntryTable;
	


	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		final Group group = new Group(parent, SWT.TITLE);
		group.setText("EML Creator");
		group.setLayout(new GridLayout(1, false));

		createGroupFiles(group);
		
		createServerStatusComposite(group);

		createOutputText(group);
		
		Composite compositeLogTable = createCompositeLogTable(group);

		logEntryTable = new LogEntryTable(compositeLogTable,getSite());
		
		startServer();

	}
	
	
	private Composite createCompositeLogTable(final Composite group) {
		Composite compositeLogTable = new Composite(group, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(compositeLogTable);

		GridData gdLogTable = new GridData();
		gdLogTable.grabExcessHorizontalSpace = true;
		gdLogTable.grabExcessVerticalSpace = true;
		gdLogTable.horizontalAlignment = SWT.FILL;
		gdLogTable.verticalAlignment = SWT.FILL;
		//gdLogTable.heightHint = 800;
		gdLogTable.horizontalSpan = 1;

		compositeLogTable.setLayoutData(gdLogTable);
		return compositeLogTable;
	}


	private void startServer() {
		try {
			ServerCallback callback  =new ServerCallback();
			server  = new HttpServer();
			
			server.setOutputDir(outputDirText.getText());
			
			server.startup(callback);
			logger.info("Server started " );
			
			final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";
			final SimpleDateFormat stf = new SimpleDateFormat(DATE_TIME_FORMAT);
			
			serverStatus.setText("Server started at " + stf.format(new Date()));
		}
		catch(Exception err)
		{
			logger.error("Error " + err.getMessage(), err);
			serverStatus.setText("Error Server " + err.getMessage());
		}
		
	}


	private void createOutputText(Group group) {
		GridData gdDateSession = new GridData();
		gdDateSession.grabExcessHorizontalSpace = false;
		gdDateSession.horizontalAlignment = SWT.NONE;
		gdDateSession.horizontalSpan = 1;
		gdDateSession.widthHint = 800;
		gdDateSession.heightHint = 100;

		
		currentFileName = new Text(group, SWT.BORDER | SWT.MULTI);
		currentFileName.setLayoutData(gdDateSession);

		GridData gdPb = new GridData();
		gdPb.grabExcessHorizontalSpace = true;
		//gdPb.grabExcessVerticalSpace = true;
		gdPb.horizontalAlignment = SWT.FILL;
		//gdPb.horizontalSpan = 1;
		//gdPb.widthHint = 800;
		
		pb = new  ProgressBar(group, SWT.NULL);
		pb.setSelection(0);
		pb.setLayoutData(gdPb);

		
	}


	private void createGroupFiles(Group group) {
		final Group groupFiles = new Group(group, SWT.TITLE);
		groupFiles.setText("Files and Dirs:");
		groupFiles.setLayout(new GridLayout(1, false));
		
		
		Composite composite = new Composite(groupFiles, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(false).applyTo(composite);

		//createSelectDirComposite(composite);

		createSelectFilesComposite(composite);

		createOutputDirComposite(composite);
		
	}

	
	private void saveInput() {
		LoggingSettings.saveProperty(LoggingSettings.PREFERENCE_INPUT_FILE_PROPERTY,logFileText.getText());
		
	}

	
	private void createServerStatusComposite(Composite parent) {
		
		Composite textComposite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(textComposite);

		GridData gdMaxFileSize = new GridData();
		gdMaxFileSize.grabExcessHorizontalSpace = false;
		gdMaxFileSize.horizontalAlignment = SWT.NONE;
		gdMaxFileSize.horizontalSpan = 2;
		gdMaxFileSize.widthHint = 600;
		
		this.serverStatus = new Text(textComposite, SWT.BORDER | SWT.SINGLE);
		this.serverStatus.setLayoutData(gdMaxFileSize);
		this.serverStatus.setEditable(false);
		Color color = new Color(parent.getDisplay(), new RGB(0,255,25));
		this.serverStatus.setForeground(color);

		
	}

	private void createSelectFilesComposite(final Composite parent) {
		String saved = LoggingSettings.readProperty(LoggingSettings.PREFERENCE_INPUT_FILE_PROPERTY,"");

		
		Label label = new Label(parent, SWT.NONE);
		label.setText("LogFile");
		logFileText = new Text(parent, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).hint(600, 16).grab(false, false).applyTo(logFileText);

		final Button fileDialog = new Button(parent, SWT.PUSH | SWT.LEFT);
		fileDialog.setText("Select File");
		
		logFileText.setText(saved);


		fileDialog.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				 FileDialog fileDialog = new FileDialog(parent.getShell());
				    // Set the text
				 fileDialog.setText("Select Log. File");				
				 logFileText.setText(fileDialog.open());

			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
	}

	
	private void createOutputDirComposite(final Composite composite) {
		
		String savedDir = LoggingSettings.readProperty(LoggingSettings.PREFERENCE_OUTPUT_DIR_PROPERTY,"");
		
		Label logDirLabel = new Label(composite, SWT.NONE);
		logDirLabel.setText("OutputDir");
		
		outputDirText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).hint(400, 16).grab(false, false).applyTo(outputDirText);

		final Button dirDialogBtn = new Button(composite, SWT.PUSH | SWT.LEFT);
		dirDialogBtn.setText("Select DIR");
		
		outputDirText.setText(savedDir);
		
		dirDialogBtn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dirDialog = new DirectoryDialog(composite.getShell());
				
				// Set the text
				if ( StringUtils.isNotBlank(outputDirText.getText()))
					dirDialog.setFilterPath(outputDirText.getText());
				
				dirDialog.setText("Select output dir");				

				String dir = dirDialog.open();
				
				if (StringUtils.isNotEmpty(dir) ) {
					outputDirText.setText(dir);
					if ( server != null)
						server.setOutputDir(outputDirText.getText());
				}

				
				LoggingSettings.saveProperty(LoggingSettings.PREFERENCE_OUTPUT_DIR_PROPERTY,outputDirText.getText());

			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
	}
	

	protected void updateProgressb(final long size, final long maxSize) {
		int imax = (int) ( maxSize / 1000 );
		int isize = (int) ( size / 1000 );
		pb.setMaximum(imax);
		pb.setSelection(isize);

	}


	public void outputText(String text) {
		if ( "exit".equals(text) || "canceled".equals(text)) {
			
			buttonSplit.setText("Split to Files");
			buttonSplit.setEnabled(true);
			
			if ("exit".equals(text) )
				currentFileName.setText(" completed ");
			else
				currentFileName.setText(" canceled");
			
			
			buttonStop.setEnabled(false);

			
		}
		else
			currentFileName.setText(text);
	}


	@Override
	public void dispose() {
		if( server != null)
			server.shutdown();
		// TODO Auto-generated method stub
		super.dispose();
	}


	public void updateTable() {
		logEntryTable.update();
		
	}
	

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
	}

}