package com.dev.gis.app.taskmanager.loggingView;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;

import com.dev.gis.app.taskmanager.loggingView.service.LoggingModelProvider;
import com.dev.gis.app.taskmanager.loggingView.service.LoggingSearchFilesSelectionListener;
import com.dev.gis.app.view.elements.BasicControl;
import com.dev.gis.app.view.elements.ButtonControl;

public class SearchCriteriaComposite extends BasicControl {
	
	private final static Logger logger = Logger.getLogger(SearchCriteriaComposite.class);


	public SearchCriteriaComposite() {
		
	}
	
	public void create(Composite groupSearch) {
		
		Composite composite = new Composite(groupSearch, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(false).applyTo(composite);
		
		new FilePatternTextControl(composite);
		
		createDates(composite);
		
		new ButtonControl(composite, "Search Files", 0,  searchFilesListener());
		
	}
	
	private SelectionListener searchFilesListener() {
		return new LoggingSearchFilesSelectionListener();	
	}
	

	private void createDates(Composite parent) {
		
		Composite checkBoxComosite = createComposite(parent, 1, -1, true);
		new UseDatesCheckBox(checkBoxComosite," select dates :");

		Calendar[] cals = LoggingSettings.readTimeProperty();
		
		LoggingModelProvider.INSTANCE.loggingFromDate = cals[0];
		LoggingModelProvider.INSTANCE.loggingToDate = cals[1];
		
		addDateControl("fromDate:",parent,LoggingModelProvider.INSTANCE.loggingFromDate,2);
		
		addDateControl("toDate:",parent,LoggingModelProvider.INSTANCE.loggingToDate,2);

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
				saveInput();
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
				saveInput();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
	}
	
	private void saveInput() {
		LoggingSettings.saveTimeProperty(
				LoggingModelProvider.INSTANCE.loggingFromDate, 
				LoggingModelProvider.INSTANCE.loggingToDate);
	}
}
