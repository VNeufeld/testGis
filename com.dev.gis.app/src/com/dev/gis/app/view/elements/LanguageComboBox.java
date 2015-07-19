package com.dev.gis.app.view.elements;

import org.apache.log4j.Logger;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.dev.gis.connector.api.TaskProperties;

public class LanguageComboBox {
	
	private static Logger logger = Logger.getLogger(LanguageComboBox.class);
	
	private final static String items[] = { "deu ( DE )", "eng ( EN) " };
	
	private final int size; 

	private Combo languageList;
	
	private String selectedLanguage;

	private final Composite parent;
	
	public LanguageComboBox(Composite parent, int size) {
		super();
		this.parent = parent;
		this.size = size;
		create();
	}

	public Combo create() {
		logger.info("create ");
		languageList = createLanguageList(parent);
		initValues();
		languageList.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				selectLanguage();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		return languageList;
	}
	
	private void selectLanguage() {
		selectedLanguage = "EN";
		int index = languageList.getSelectionIndex();
		String st = languageList.getItem(index);
		if ( st.contains("EN"))
			selectedLanguage = "EN";
		else
			selectedLanguage = "DE";
		setProperties(index);
		
		logger.info("select language "+ index + " languageCode = "+ selectedLanguage);

	}

	
	private void initValues() {
		int lang_id = TaskProperties.getTaskProperties().getLanguage();
		languageList.select(lang_id);
	}
	
	private void setProperties(int index) {
		TaskProperties.getTaskProperties().setLanguage(index);
		TaskProperties.getTaskProperties().saveProperty();

	}


	private Combo createLanguageList(Composite groupStamp) {
		
		new Label(groupStamp, SWT.NONE).setText("Language");

		GridLayout gd = (GridLayout)groupStamp.getLayout();
		int col = gd.numColumns;

		final Combo c = new Combo(groupStamp, SWT.READ_ONLY);
		c.setItems(items);
		
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(false, false).span(col-1, 1).hint(size, 16).applyTo(c);

		return c;
	}

	public final String getSelectedLanguage() {
		return selectedLanguage;
	}

}
