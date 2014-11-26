package com.dev.gis.app.taskmanager.loggingView;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.dev.gis.app.task.model.FileNameEntryModel;
import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.task.execution.api.ITaskResult;

public class LoggingAppView extends TaskViewAbstract {
	public static final String ID = "com.dev.gis.app.task.LoggingAppView";
	
	private final static Logger logger = Logger.getLogger(LoggingAppView.class);
	
	Calendar loggingFromDate = Calendar.getInstance();
	Calendar loggingToDate = Calendar.getInstance();

	
	private static int instanceNum = 1;

	
	private Text logDirText;
	private Text outputDirText;
	private Text sessionIdText;
	private Text bookingIdText;
	private Text maxThreadsText;
	
	private Button buttonSession;
	private Button buttonStop;
	private Button buttonBooking;
	
	private Text pbText;
	private ProgressBar pb;

	private Text currentFileName;
	
	//private TableViewer viewer;
	
	private LogTable  logTable;

	private LogFilesTable  logFilesTable;

	private Text filesCount;
	
	private CursorManager  cursorManager;
	
	private SplittFileToSession splittFileToSession = null;
	
	private FindBooking findBooking = null;
	
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	
	
	@Override
	public void createPartControl(final Composite parent) {
		
		cursorManager = new CursorManager(parent);
		
		final Group group = new Group(parent, SWT.TITLE);
		group.setText("Session Logging:");
		group.setLayout(new GridLayout(1, false));

		createGroupFiles(group);

		createGroupSearch(group);

		//createButtons(group);

		createOutputText(group);
		
		logTable = new LogTable(group,getSite());
		
		
	}
	
	
	

	private void createOutputText(Group group) {

		Composite composite = new Composite(group, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(true).applyTo(composite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(composite);

		GridData gdText = new GridData();
		gdText.grabExcessHorizontalSpace = true;
		gdText.grabExcessVerticalSpace = false;
		gdText.horizontalAlignment = SWT.FILL;
		gdText.verticalAlignment = SWT.FILL;
		gdText.widthHint = 400;
		
		pbText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		pbText.setEnabled(false);
		pbText.setText(".");
		pbText.setLayoutData(gdText);

		pb = new  ProgressBar(composite, SWT.NULL);
		pb.setSelection(0);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(pb);

		GridData gdMultiText = new GridData();
		gdMultiText.grabExcessHorizontalSpace = true;
		gdMultiText.grabExcessVerticalSpace = false;
		gdMultiText.horizontalAlignment = SWT.FILL;
		gdMultiText.verticalAlignment = SWT.FILL;
		gdMultiText.horizontalSpan = 2;
		//gdMultiText.widthHint = 800;
		gdMultiText.heightHint = 50;

		currentFileName = new Text(composite, SWT.BORDER | SWT.MULTI | /* SWT.WRAP |*/ SWT.V_SCROLL);
		currentFileName.setLayoutData(gdMultiText);
		
		//GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(false, true).applyTo(currentFileName);

		
	}


	private void createGroupFiles(Group group) {
		final Group groupFiles = new Group(group, SWT.TITLE);
		groupFiles.setText("Directory:");
		groupFiles.setLayout(new GridLayout(1, false));
		
		
		Composite composite = new Composite(groupFiles, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(false).applyTo(composite);

		createSelectDirComposite(composite);

		createMaxThreadComposite(composite);

		createOutputDirComposite(composite);
		
	}


	private void createGroupSearch(Group group) {

		Composite compositeSearch = new Composite(group, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(compositeSearch);

		
		final Group groupSearch = new Group(compositeSearch, SWT.TITLE);
		groupSearch.setText("Search criteria:");
		groupSearch.setLayout(new GridLayout(1, false));

		Composite composite = new Composite(groupSearch, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(false).applyTo(composite);

		createDates(composite);

		createSessionComposite(composite);

		createBookingComposite(composite);
		
		
		addFilesTable(compositeSearch);
		
		
	}




	private void addFilesTable(Composite compositeSearch) {
		final Group groupTable = new Group(compositeSearch, SWT.TITLE);
		groupTable.setText("File list:");
		
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalAlignment = GridData.FILL;
//		gridData.heightHint = 200;
//		gridData.widthHint  = 800;
		gridData.horizontalSpan = 1;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;

		groupTable.setLayoutData(gridData);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(groupTable);

		GridData gridData2 = new GridData();
		//gridData2.verticalAlignment = GridData.FILL;
		gridData.horizontalAlignment = GridData.FILL;
//		gridData.heightHint = 200;
//		gridData.widthHint  = 800;
		gridData2.horizontalSpan = 1;
		//gridData.grabExcessHorizontalSpace = true;
		//gridData2.grabExcessVerticalSpace = true;

		Composite composite = new Composite(groupTable, SWT.NONE);
		composite.setLayoutData(gridData2);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(composite);


		addSearchFilesButton(composite);
		
		filesCount = new Text(composite, SWT.BORDER | SWT.SINGLE);
		filesCount.setEnabled(false);
		filesCount.setText("files : 0");

		logFilesTable = new LogFilesTable(groupTable, getSite());
	}
	
	private void createButtons(Composite parent) {

		GridData gdDate = new GridData();
		gdDate.grabExcessHorizontalSpace = false;
		gdDate.horizontalAlignment = SWT.NONE;
		gdDate.horizontalSpan = 3;

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(gdDate);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(composite);

		//addButtonSession(composite);
		
		addButtonStopJob(composite);
		
	}


	private void addButtonStopJob(Composite composite) {
		GridData gdButton = new GridData();
		gdButton.grabExcessHorizontalSpace = false;
		gdButton.horizontalAlignment = SWT.NONE;
		gdButton.widthHint = 150;

		buttonStop = new Button(composite, SWT.PUSH | SWT.CENTER | SWT.COLOR_BLUE);
		buttonStop.setText("Stop job");
		buttonStop.setLayoutData(gdButton);
		
		buttonStop.setEnabled(false);

		
		buttonStop.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if ( splittFileToSession != null)
					splittFileToSession.setCanceled(true);
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
	}


	private void addButtonSession(final Composite composite) {
		
		GridData gdButton = new GridData();
		gdButton.grabExcessHorizontalSpace = false;
		gdButton.horizontalAlignment = SWT.NONE;
		gdButton.widthHint = 150;

		
		buttonSession = new Button(composite, SWT.PUSH | SWT.CENTER);
		buttonSession.setText("Split to session");
		buttonSession.setLayoutData(gdButton);

		
		buttonSession.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				saveInput();
				
				buttonSession.setEnabled(false);
				buttonSession.setText(" Wait ....");
				
				buttonStop.setEnabled(true);
				
				splittFileToSession = new SplittFileToSession(logDirText.getText(),outputDirText.getText(), 
						sessionIdText.getText(),bookingIdText.getText(),
						maxThreadsText.getText(),
						loggingFromDate,loggingToDate);
				
				executor.submit(splittFileToSession);
				
				logger.info(" executer started ");
				
	
			}
			

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}
	

	
	private void saveInput() {
		LoggingSettings.saveProperty(LoggingSettings.PREFERENCE_MAX_THREADS_PROPERTY,maxThreadsText.getText());
		
		LoggingSettings.saveTimeProperty(loggingFromDate, loggingToDate);

		LoggingSettings.saveProperty(LoggingSettings.PREFERENCE_INPUT_DIR_PROPERTY,logDirText.getText());

		LoggingSettings.saveProperty(LoggingSettings.PREFERENCE_SESSION_PROPERTY,sessionIdText.getText());

		
	}

	
	private void createBookingComposite(Composite composite) {
		GridData gdDateSession = new GridData();
		gdDateSession.grabExcessHorizontalSpace = false;
		gdDateSession.horizontalAlignment = SWT.NONE;
		gdDateSession.horizontalSpan = 1;
		gdDateSession.widthHint = 300;

		new Label(composite, SWT.NONE).setText("BookingId");
		
		bookingIdText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		bookingIdText.setLayoutData(gdDateSession);
		

		GridData gdButton = new GridData();
		gdButton.grabExcessHorizontalSpace = false;
		gdButton.horizontalAlignment = SWT.NONE;
		gdButton.widthHint = 150;

		buttonBooking = new Button(composite, SWT.PUSH | SWT.CENTER);
		buttonBooking.setText("Search booking");
		buttonBooking.setLayoutData(gdButton);
		
		
		buttonBooking.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				saveInput();
				
				buttonBooking.setEnabled(false);
				buttonBooking.setText(" Wait ....");
				
				FileNameEntryModel.getInstance().getEntries().clear();
				
				//buttonStop.setEnabled(true);
				
				findBooking = new FindBooking(logDirText.getText(),outputDirText.getText(), 
						bookingIdText.getText(),
						maxThreadsText.getText(),
						loggingFromDate,loggingToDate);
				
				executor.submit(findBooking);
				
				logger.info(" executer started ");
				
	
			}
			

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
	}


	private void createSessionComposite(Composite composite) {
		
		String saved = LoggingSettings.readProperty(LoggingSettings.PREFERENCE_SESSION_PROPERTY,"0");

		GridData gdDateSession = new GridData();
		gdDateSession.grabExcessHorizontalSpace = false;
		gdDateSession.horizontalAlignment = SWT.NONE;
		gdDateSession.horizontalSpan = 1;
		gdDateSession.widthHint = 300;

		new Label(composite, SWT.NONE).setText("SessionId");
		
		sessionIdText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		sessionIdText.setLayoutData(gdDateSession);
		sessionIdText.setText(saved);
		
		addButtonSession(composite);
		
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
		gdMaxFileSize.widthHint = 150;
		
		maxThreadsText = new Text(textComposite, SWT.BORDER | SWT.SINGLE);
		maxThreadsText.setLayoutData(gdMaxFileSize);
		
		maxThreadsText.setText(maxThreadsSaved);

		new Label(textComposite, SWT.NONE).setText("( 1 : 10 )");
		
	}


	private void createDates(Composite parent) {

		Calendar[] cals = LoggingSettings.readTimeProperty();
		
		//initDates();
		loggingFromDate = cals[0];
		loggingToDate = cals[1];
		
		addDateControl("fromDate:",parent,loggingFromDate,2);
		
		addDateControl("toDate:",parent,loggingToDate,2);

	}


	private void addSearchFilesButton(Composite parent) {
		GridData gdButton = new GridData();
		gdButton.grabExcessHorizontalSpace = false;
		gdButton.horizontalAlignment = SWT.NONE;
		gdButton.widthHint = 150;
		
		final Button dirDialogBtn = new Button(parent, SWT.PUSH | SWT.CENTER);
		dirDialogBtn.setText("Show files");
		dirDialogBtn.setLayoutData(gdButton);
		
		dirDialogBtn.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				logFilesTable.update();
				FileNameEntryModel.getInstance().getEntries().clear();
				
				FindFilesToSession ff = new FindFilesToSession(logDirText.getText(),
						loggingFromDate,loggingToDate);
				
				executor.submit(ff);
				
				logger.info(" executer started ");
				
	
			}
			

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		
	}




	private void createSelectDirComposite(final Composite composite) {

		String savedDir = LoggingSettings.readProperty(LoggingSettings.PREFERENCE_INPUT_DIR_PROPERTY,"");

		Label logDirLabel = new Label(composite, SWT.NONE);
		logDirLabel.setText("LogDir");
		
		logDirText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).hint(400, 16).grab(false, false).applyTo(logDirText);

		final Button dirDialogBtn = new Button(composite, SWT.PUSH | SWT.LEFT);
		dirDialogBtn.setText("Select DIR");
		
		logDirText.setText(savedDir);

		
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

	
	private void addDateControl(String label,Composite parent,final Calendar resultDate, int span ) {
		

		GridData gdDate = new GridData();
		gdDate.grabExcessHorizontalSpace = false;
		gdDate.horizontalAlignment = SWT.NONE;
		gdDate.widthHint = 100;

		new Label(parent, SWT.NONE).setText(label);
		
		GridData gdTextComposite = new GridData();
		gdTextComposite.grabExcessHorizontalSpace = true;
		gdTextComposite.horizontalAlignment = SWT.NONE;
		gdTextComposite.horizontalSpan = span;
		
		Composite dateComposite = new Composite(parent, SWT.NONE);
		dateComposite.setLayoutData(gdTextComposite);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(dateComposite);


		final DateTime dateTime = new DateTime(dateComposite, SWT.DATE);
		dateTime.setLayoutData(gdDate);
		
		final DateTime dateTime2 = new DateTime(dateComposite, SWT.TIME);
		dateTime2.setLayoutData(gdDate);
		
		dateTime.setYear(resultDate.get(Calendar.YEAR));
		dateTime.setMonth(resultDate.get(Calendar.MONTH));
		dateTime.setDay(resultDate.get(Calendar.DAY_OF_MONTH));
		
		dateTime2.setHours(resultDate.get(Calendar.HOUR_OF_DAY));
		dateTime2.setMinutes(resultDate.get(Calendar.MINUTE));
		dateTime2.setSeconds(resultDate.get(Calendar.SECOND));

		dateTime.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				resultDate.set(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		
		dateTime2.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				resultDate.set(Calendar.HOUR_OF_DAY, dateTime2.getHours());
				resultDate.set(Calendar.MINUTE, dateTime2.getMinutes());
				resultDate.set(Calendar.SECOND, dateTime2.getSeconds());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		
	}


	private void initDates() {
		
		loggingFromDate.set(Calendar.HOUR_OF_DAY, 11);
		loggingFromDate.set(Calendar.MINUTE, 30);
		loggingFromDate.set(Calendar.SECOND, 0);

		loggingToDate.set(Calendar.HOUR_OF_DAY, 18);
		loggingToDate.set(Calendar.MINUTE, 0);
		loggingToDate.set(Calendar.SECOND, 0);	
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
	
	public static void  updateView(final String text ) {
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					// Show protocol, show results
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					LoggingAppView viewPart =  (LoggingAppView)wp.showView(
							LoggingAppView.ID, 
							Integer.toString(instanceNum), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					viewPart.outputText(text);
					
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});
	}
	
	public static void  updateView(final LogEntry entry ) {
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					// Show protocol, show results
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					LoggingAppView viewPart =  (LoggingAppView)wp.showView(
							LoggingAppView.ID, 
							Integer.toString(instanceNum), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					viewPart.outputText(entry.getEntry().get(0));

					viewPart.refreshCriteria(entry.getBookingId(), entry.getSessionId());
					
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

		
	}
	
	public static void  updateFileModel() {
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					// Show protocol, show results
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					LoggingAppView viewPart =  (LoggingAppView)wp.showView(
							LoggingAppView.ID, 
							Integer.toString(instanceNum), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					viewPart.updateFileTable();
					
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

		
	}

	
	
	protected void updateFileTable() {
		logFilesTable.update();
		
		filesCount.setText("files :"+ FileNameEntryModel.getInstance().getEntries().size());

		
	}




	protected void refreshCriteria(String bookingId, String sessionId) {
		sessionIdText.setText(sessionId);
		bookingIdText.setText(bookingId);
		
	}




	public static void  updateProgressBar(final long size, final long maxSize ) {
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					// Show protocol, show results
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					LoggingAppView viewPart =  (LoggingAppView)wp.showView(
							LoggingAppView.ID, 
							Integer.toString(instanceNum), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					viewPart.updateProgressb(size, maxSize);
					
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

		
	}

	protected void updateProgressb(final long size, final long maxSize) {
		int imax = (int) ( maxSize / 1000 );
		int isize = (int) ( size / 1000 );
		pb.setMaximum(imax);
		pb.setSelection(isize);

	}


	protected void outputText(String text) {
		if ( text.startsWith("error") || text.startsWith("session")) {

			resetButtons();

			if ( text.startsWith("error"))
				currentFileName.setText(text);
			else
				logTable.update();

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
		buttonSession.setEnabled(true);
		buttonSession.setText("Split to session");
		
		splittFileToSession = null;
		
		findBooking = null;
		
		//buttonStop.setEnabled(false);

		buttonBooking.setEnabled(true);
		
		buttonBooking.setText("Search booking");
		
		pb.setSelection(0);
	}




	public static void updateFileName(final String text) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					// Show protocol, show results
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					LoggingAppView viewPart =  (LoggingAppView)wp.showView(
							LoggingAppView.ID, 
							Integer.toString(instanceNum), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					viewPart.updateProgressText(text);
					
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});
		
	}

	protected void updateProgressText(String text) {
		// TODO Auto-generated method stub
		pbText.setText(text);
		
	}

}
