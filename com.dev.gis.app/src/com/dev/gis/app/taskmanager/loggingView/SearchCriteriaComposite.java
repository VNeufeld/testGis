package com.dev.gis.app.taskmanager.loggingView;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.task.model.FileNameEntryModel;
import com.dev.gis.app.taskmanager.loggingView.service.FindBookingService;
import com.dev.gis.app.taskmanager.loggingView.service.FindFilesService;
import com.dev.gis.app.taskmanager.loggingView.service.LoggingModelProvider;
import com.dev.gis.app.taskmanager.loggingView.service.LoggingSearchFilesSelectionListener;
import com.dev.gis.app.taskmanager.loggingView.service.WriteSessionService;
import com.dev.gis.app.view.elements.BasicControl;
import com.dev.gis.app.view.elements.ButtonControl;

public class SearchCriteriaComposite extends BasicControl {
	
	private final static Logger logger = Logger.getLogger(SearchCriteriaComposite.class);
	
	private WriteSessionService splittFileToSession = null;
	
	private FindBookingService findBooking = null;
	
	private ExecutorService executor = Executors.newSingleThreadExecutor();


	private Text sessionIdText;
	private Text bookingIdText;
	
	
	Calendar loggingFromDate ;
	Calendar loggingToDate;

	
	private Button buttonSession;
	private Button buttonBooking;


	public SearchCriteriaComposite() {
		
	}
	
	public void create(Composite groupSearch) {
		
		Composite composite = new Composite(groupSearch, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(false).applyTo(composite);

		Composite innernComposite = createComposite(composite, 2, -1, false);
		
		new FilePatternTextControl(composite);
		
		createDates(composite);
		
		new ButtonControl(composite, "Search Files", 0,  searchFilesListener());
		

//		createSessionComposite(composite);
//
//		createBookingComposite(composite);
		
	}
	
	private SelectionListener searchFilesListener() {
		return new LoggingSearchFilesSelectionListener();	
	}

	
	private void createBookingComposite(Composite composite) {
		GridData gdDateSession = new GridData();
		gdDateSession.grabExcessHorizontalSpace = false;
		gdDateSession.horizontalAlignment = SWT.NONE;
		gdDateSession.horizontalSpan = 1;
		gdDateSession.widthHint = 400;

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
				
				findBooking = new FindBookingService(LoggingModelProvider.INSTANCE.logDirName, 
						bookingIdText.getText(),
						LoggingModelProvider.INSTANCE.filePattern,
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


	private void createDates(Composite parent) {

		Calendar[] cals = LoggingSettings.readTimeProperty();
		
		//initDates();
		loggingFromDate = cals[0];
		loggingToDate = cals[1];
		
		addDateControl("fromDate:",parent,loggingFromDate,2);
		
		addDateControl("toDate:",parent,loggingToDate,2);

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
				
				//buttonStop.setEnabled(true);
				FileNameEntryModel.getInstance().getEntries().clear();
				
				splittFileToSession = new WriteSessionService(LoggingModelProvider.INSTANCE.logDirName,LoggingModelProvider.INSTANCE.outputDirName, 
						sessionIdText.getText(),
						LoggingModelProvider.INSTANCE.filePattern,
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
		
		LoggingSettings.saveTimeProperty(loggingFromDate, loggingToDate);

		LoggingSettings.saveProperty(LoggingSettings.PREFERENCE_SESSION_PROPERTY,sessionIdText.getText());


		
	}

	public void resetButtons() {
		buttonSession.setEnabled(true);
		buttonSession.setText("Split to session");
		
		splittFileToSession = null;
		
		findBooking = null;

		buttonBooking.setEnabled(true);
		
		buttonBooking.setText("Search booking");
		
	}
	
	@SuppressWarnings("unused")
	private void initDates() {
		
		loggingFromDate.set(Calendar.HOUR_OF_DAY, 11);
		loggingFromDate.set(Calendar.MINUTE, 30);
		loggingFromDate.set(Calendar.SECOND, 0);

		loggingToDate.set(Calendar.HOUR_OF_DAY, 18);
		loggingToDate.set(Calendar.MINUTE, 0);
		loggingToDate.set(Calendar.SECOND, 0);	
	}

	public void update(String bookingId, String sessionId) {
		sessionIdText.setText(sessionId);
		bookingIdText.setText(bookingId);
	}

}
