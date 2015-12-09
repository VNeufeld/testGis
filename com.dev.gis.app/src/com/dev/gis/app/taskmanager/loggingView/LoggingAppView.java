package com.dev.gis.app.taskmanager.loggingView;

import org.apache.log4j.Logger;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.app.taskmanager.loggingView.service.LoggingSelectDirListener;
import com.dev.gis.app.taskmanager.loggingView.service.SearchBookingListener;
import com.dev.gis.app.taskmanager.loggingView.service.SearchSessionListener;
import com.dev.gis.app.taskmanager.loggingView.service.SearchThreadListener;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.task.execution.api.ITaskResult;

public class LoggingAppView extends TaskViewAbstract {
	public static final String ID = "com.dev.gis.app.task.LoggingAppView";
	
	private final static Logger logger = Logger.getLogger(LoggingAppView.class);
	
	private Text currentFileName;
	
	LogFilesTableComposite logFilesTableComposite ;
	
	ProgressBarElement progressBarElement;
	
	SearchCriteriaComposite searchCriteriaComposite;
	
	
	@Override
	public void createPartControl(final Composite parent) {
		
		final Composite group = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(group);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(group);
		
		Composite composite1 = createCompositeRowLeft(group);
		
		createMaxThreadComposite(composite1);

		createSelectDirComposite(composite1);

		createGroupSearchCriteria(composite1);
		
		createGroupSearch(composite1);
		
		
		logFilesTableComposite = new LogFilesTableComposite(getSite());
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
		
		Composite composite = new Composite(group, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(composite);
		composite.setLayoutData(gdComposite1);
		
		return composite;
	}


	private void createGroupSearchCriteria(Composite group) {
		
		final Group groupSearch = new Group(group, SWT.TITLE);
		groupSearch.setText("Search criteria:");
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(groupSearch);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(groupSearch);
		
		SearchCriteriaComposite searchCriteriaComposite = new SearchCriteriaComposite();
		searchCriteriaComposite.create(groupSearch);
		
	}
	
	private void createGroupSearch(Composite group) {
		
		final Group groupSearch = new Group(group, SWT.TITLE);
		groupSearch.setText("Search:");
		GridLayoutFactory.fillDefaults().numColumns(3).applyTo(groupSearch);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(groupSearch);
		
		new SearchSessionIdTextControl(groupSearch);
		
		new ButtonControl(groupSearch, "Search Session", 0,  new SearchSessionListener());

		new SearchBookingIdTextControl(groupSearch);
		
		new ButtonControl(groupSearch, "Search Booking", 0,  new SearchBookingListener());

		new SearchThreadTextControl(groupSearch);
		
		new ButtonControl(groupSearch, "Search Thread", 0,  new SearchThreadListener());
		
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
		
		Composite composite = createComposite(parent, 2, -1, true);
		
		new ThreadCountTextControl(composite);
		
		
	}
	private void createSelectDirComposite(final Composite composite) {
		
		Composite innernComposite = createComposite(composite, 3, -1, false);
		
		LogDirControl logDirControl = new LogDirControl(innernComposite);
		
		new ButtonControl(innernComposite, "Select Dir", 0,  selectDirListener(composite, logDirControl));
		
		
	}

	private SelectionListener selectDirListener(final Composite composite, ObjectTextControl dirControl) {
		
		LoggingSelectDirListener listener = new LoggingSelectDirListener(composite.getShell(), dirControl);
		return listener;
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
		
		progressBarElement.reset();
		
	}





}
