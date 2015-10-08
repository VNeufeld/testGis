package com.dev.gis.app.taskmanager.loggingView;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.task.execution.api.ITaskResult;

public class LoggingAppView extends TaskViewAbstract {
	public static final String ID = "com.dev.gis.app.task.LoggingAppView";
	
	private final static Logger logger = Logger.getLogger(LoggingAppView.class);
	
	
	private Text logDirText;
	private Text outputDirText;
	private Text maxThreadsText;
	
	private Text currentFileName;
	
	LogFilesTableComposite logFilesTableComposite ;
	
	ProgressBarElement progressBarElement;
	
	SearchCriteriaComposite searchCriteriaComposite;
	
	
	@Override
	public void createPartControl(final Composite parent) {
		
		final Composite group = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(group);
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).grab(true, true).applyTo(group);
		
		Composite composite1 = createCompositeRowLeft(group);

		createGroupFiles(composite1);

		createGroupSearch(composite1);
		
		logFilesTableComposite = new LogFilesTableComposite(
				logDirText,
				searchCriteriaComposite,
				getSite());
		logFilesTableComposite.create(group);

		progressBarElement = new ProgressBarElement();
		progressBarElement.create(group);

		createOutputText(group);

		
		LogViewUpdater.updateTempView();
		
	}



	private Composite createCompositeRowLeft(Composite group) {
		GridData gdComposite1 = new GridData();
		gdComposite1.grabExcessHorizontalSpace = false;
		gdComposite1.grabExcessVerticalSpace = false;
		gdComposite1.horizontalAlignment = SWT.LEFT;
		gdComposite1.verticalAlignment = SWT.FILL;
		//gdComposite1.widthHint = 550;
		
		Composite composite = new Composite(group, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(composite);
		composite.setLayoutData(gdComposite1);
		//GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(composite1);
		
		return composite;
	}




	private void createGroupFiles(Composite group) {
		final Group groupFiles = new Group(group, SWT.TITLE);
		groupFiles.setText("Directory:");
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(groupFiles);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(groupFiles);

		
		Composite composite = new Composite(groupFiles, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(false).applyTo(composite);

		createSelectDirComposite(composite);

		createMaxThreadComposite(composite);

		createOutputDirComposite(composite);

		
	}

	private void createGroupSearch(Composite group) {
		
		final Group groupSearch = new Group(group, SWT.TITLE);
		groupSearch.setText("Search criteria:");
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(groupSearch);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(groupSearch);
		
		searchCriteriaComposite = new SearchCriteriaComposite(logDirText, maxThreadsText, outputDirText);
		searchCriteriaComposite.create(groupSearch);
		
	}


	private void createOutputText(Composite group) {

		Composite composite = new Composite(group, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(false).applyTo(composite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).span(2, 1).applyTo(composite);

		GridData gdMultiText = new GridData();
		gdMultiText.grabExcessHorizontalSpace = true;
		gdMultiText.grabExcessVerticalSpace = false;
		gdMultiText.horizontalAlignment = SWT.FILL;
		gdMultiText.verticalAlignment = SWT.FILL;
		gdMultiText.horizontalSpan = 1;
		//gdMultiText.widthHint = 800;
		gdMultiText.heightHint = 50;

		currentFileName = new Text(composite, SWT.BORDER | SWT.MULTI | /* SWT.WRAP |*/ SWT.V_SCROLL);
		currentFileName.setLayoutData(gdMultiText);
		
	}

	
	
	private void createMaxThreadComposite(Composite parent) {

		String maxThreadsSaved = LoggingSettings.readProperty(LoggingSettings.PREFERENCE_MAX_THREADS_PROPERTY,"1");


		new Label(parent, SWT.NONE).setText("maxThreadPoolSize");
		
		GridData gdTextComposite = new GridData();
		gdTextComposite.grabExcessHorizontalSpace = false;
		gdTextComposite.horizontalAlignment = SWT.NONE;
		gdTextComposite.horizontalSpan = 2;
		
		Composite textComposite = new Composite(parent, SWT.NONE);
		textComposite.setLayoutData(gdTextComposite);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(textComposite);

		GridData gdMaxFileSize = new GridData();
		gdMaxFileSize.grabExcessHorizontalSpace = false;
		gdMaxFileSize.horizontalAlignment = SWT.NONE;
		//gdMaxFileSize.widthHint = 150;
		
		maxThreadsText = new Text(textComposite, SWT.BORDER | SWT.SINGLE);
		maxThreadsText.setLayoutData(gdMaxFileSize);
		
		maxThreadsText.setText(maxThreadsSaved);

		new Label(textComposite, SWT.NONE).setText("( 1 : 10 )");
		
	}


	private void createSelectDirComposite(final Composite composite) {

		String savedDir = LoggingSettings.readProperty(LoggingSettings.PREFERENCE_INPUT_DIR_PROPERTY,"");

		new Label(composite, SWT.NONE).setText("LogDir");
		
		logDirText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		//GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(logDirText);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).hint(300, 16).grab(false, false).applyTo(logDirText);
		logDirText.setText(savedDir);

		final Button dirDialogBtn = addButtonSelectDir(composite);
		
		
		dirDialogBtn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dirDialog = new DirectoryDialog(composite.getShell());
				    // Set the text
				dirDialog.setText("Select logging dir");		


				// Set the text
				if ( StringUtils.isNotBlank(logDirText.getText()))
					dirDialog.setFilterPath(logDirText.getText());

				String dir = dirDialog.open();
				
				if (StringUtils.isNotEmpty(dir) )
					logDirText.setText(dir);
				
				LoggingSettings.saveProperty(LoggingSettings.PREFERENCE_INPUT_DIR_PROPERTY,logDirText.getText());
				

			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
				
	}
	
	private void createOutputDirComposite(final Composite composite) {
		
		String savedDir = LoggingSettings.readProperty(LoggingSettings.PREFERENCE_OUTPUT_DIR_PROPERTY,"");
		
		new Label(composite, SWT.NONE).setText("OutputDir");
		
		outputDirText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		//GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).hint(400, 16).grab(false, false).applyTo(outputDirText);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(outputDirText);
		outputDirText.setText(savedDir);

		final Button dirDialogBtn = addButtonSelectDir(composite);
		
		
		
		dirDialogBtn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dirDialog = new DirectoryDialog(composite.getShell());
				
				// Set the text
				if ( StringUtils.isNotBlank(outputDirText.getText()))
					dirDialog.setFilterPath(outputDirText.getText());
				
				dirDialog.setText("Select output dir");				

				String dir = dirDialog.open();
				
				if (StringUtils.isNotEmpty(dir) )
					outputDirText.setText(dir);
				
				LoggingSettings.saveProperty(LoggingSettings.PREFERENCE_OUTPUT_DIR_PROPERTY,outputDirText.getText());

			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
	}


	private Button addButtonSelectDir(final Composite composite) {
		GridData gdButton = new GridData(GridData.HORIZONTAL_ALIGN_END);
		gdButton.grabExcessHorizontalSpace = false;
		gdButton.horizontalAlignment = SWT.FILL;
		//gdButton.widthHint = 150;
		
		final Button dirDialogBtn = new Button(composite, SWT.PUSH | SWT.CENTER);
		dirDialogBtn.setText("Select Dir");
		dirDialogBtn.setLayoutData(gdButton);
		return dirDialogBtn;
	}

	



	
	@Override
	public void setFocus() {

	}

	@Override
	public void refresh() {
		
	}

	@Override
	public void refresh(ITaskResult result) {
		
	}


	void refreshCriteria(String bookingId, String sessionId) {
		searchCriteriaComposite.update(bookingId,sessionId);
	}


	void outputText(String text) {
		if ( text.startsWith("error") || text.startsWith("session")) {

			resetButtons();

			if ( text.startsWith("error"))
				currentFileName.setText(text);

		}
		
		if ( "exit".equals(text) || "canceled".equals(text)) {
			resetButtons();
			
			if ("exit".equals(text) )
				currentFileName.setText(" completed ");
			else
				currentFileName.setText(" canceled");
			
			
		}
		else
			currentFileName.setText(text);
	}



	private void resetButtons() {
		
		searchCriteriaComposite.resetButtons();
		
		progressBarElement.reset();
		
	}





}
