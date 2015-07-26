package com.dev.gis.app.view.elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;

public abstract class ObjectDateControl extends BasicControl{
	
	private static Logger logger = Logger.getLogger(ObjectDateControl.class);
	private final static String DATE_TIME_FORMAT = "yyyy-MM.dd HH:mm";
	
	
	private DateTime time;
	
	private DateTime date;

	private final Composite parent;
	
	protected Calendar selectedValue;
	
	public ObjectDateControl(Composite parent) {
		super();
		this.parent = parent;
		create();
	}

	public void create() {
		logger.info("create ");
		
		selectedValue = getSavedValue();
		
		addDateControl();
		
		getValue();
		
	}

	private void addDateControl() {

		GridLayout gd = (GridLayout)parent.getLayout();
		int col = gd.numColumns;

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(3, true));
		GridDataFactory.fillDefaults().span(col, 1)
				.align(SWT.FILL, SWT.BEGINNING).grab(true, false)
				.applyTo(composite);

		String label = getLabel();
		new Label(composite, SWT.NONE).setText(label);

		date = createDateControl(composite);
		time = createTimeControl(composite);
		
		
		if ( selectedValue != null) {
			date.setYear(selectedValue.get(Calendar.YEAR));
			date.setMonth(selectedValue.get(Calendar.MONTH));
			date.setDay(selectedValue.get(Calendar.DAY_OF_MONTH));
			
			time.setHours(selectedValue.get(Calendar.HOUR_OF_DAY));
			time.setMinutes(selectedValue.get(Calendar.MINUTE));
		}

	}
	private DateTime createTimeControl(Composite composite) {

		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;

		DateTime time = new DateTime(composite, SWT.TIME);

		time.setHours(10);
		time.setMinutes(0);
		time.setSeconds(0);

		time.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				getValue();
				logger.info(" selected date :" +selectedValue.toString());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		return time;

	}

	private DateTime createDateControl(Composite composite) {

		DateTime date = new DateTime(composite, SWT.DATE);

		GridData gdDate = new GridData();
		gdDate.grabExcessHorizontalSpace = true;
		gdDate.horizontalAlignment = SWT.FILL;

		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER)
				.grab(false, false).applyTo(date);

		date.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				getValue();
				logger.info(" selected date :" +convertToPropertyFromCalendar(selectedValue));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		return date;

	}
	
	public void getValue() {

		this.selectedValue = createDate(date, time);
		String property = convertToPropertyFromCalendar(selectedValue);
		saveProperty(getPropertyName(),property);
		saveDate();
		
	}
	
	protected abstract void saveDate();

	private static Calendar createDate(DateTime pickupDate,	DateTime pickupTime) {
		Calendar t = Calendar.getInstance();
		t.set(pickupDate.getYear(),pickupDate.getMonth(),pickupDate.getDay(),pickupTime.getHours(),pickupTime.getMinutes(),0);
		return t;
	}
	
	protected Calendar getSavedValue() {
		String value = readProperty(getPropertyName());
		Calendar c =  convertPropertyToCalender(value);
		if ( c == null) {
			c = Calendar.getInstance();
		}
		return c;
	}
	
	private static Calendar convertPropertyToCalender(String value) {
		
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
		Date d;
		try {
			if ( StringUtils.isNotEmpty(value)) {
				d = sdf.parse(value);
				Calendar cal = Calendar.getInstance();
				cal.setTime(d);
				return cal;
			}
				
		} catch (ParseException e) {
			logger.info(e.getMessage(), e);
		}
		return null;
		
	}


	protected abstract String getPropertyName();

	protected abstract  String getLabel();
	
	private static String convertToPropertyFromCalendar(Calendar cal) {
		String sday = String.format("%4d-%02d-%02d",
				cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
				cal.get(Calendar.DAY_OF_MONTH));
		String sTime = String.format("%02d:%02d",
				cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE));
		
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
		String s = sdf.format(cal.getTime());
		return s;
		
		
		
	}


}
