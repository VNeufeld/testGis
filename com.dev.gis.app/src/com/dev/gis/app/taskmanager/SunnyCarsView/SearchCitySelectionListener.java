package com.dev.gis.app.taskmanager.SunnyCarsView;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBException;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.connector.api.TaskProperties;
import com.dev.gis.task.execution.api.ITaskDataProvider;
import com.dev.gis.task.execution.api.TaskDataProviderFactory;


public class SearchCitySelectionListener implements SelectionListener {

	private final Text serverUrl;
	private final Text operator;
	private final Shell shell;
	private final Text cityText;
	
	private String  id;

	public SearchCitySelectionListener(Text serverUrl, Text operator, final Shell shell, Text cityText) {
		super();
		this.serverUrl = serverUrl;
		this.operator = operator;
		this.shell = shell;
		this.cityText = cityText;
		
	}
	

	@Override
	public void widgetDefaultSelected(SelectionEvent event) {
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		System.out.println("Checkindate = "+new SimpleDateFormat("dd.MM.yyyy hh:mm:ss").format(new Date()));
		
		ITaskDataProvider dp = null;
		try {
			dp = TaskDataProviderFactory.createTaskDataProvider("locationSearch");
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
//		IStationDao stationDao = Components.getInstance().getDaoFactory().getStationDao();
//		
//		System.out.println(" Stationname = "+stationDao.getStationName());
		
		TaskProperties.getTaskProperties().setServerProperty(serverUrl.getText());
		TaskProperties.getTaskProperties().setOperator(Long.valueOf(operator.getText()));
		
		TaskProperties.getTaskProperties().saveProperty();
		
		LocationSearchDialog mpd = new LocationSearchDialog(shell);
		
		if (mpd.open() == Dialog.OK) {
			if ( mpd.getResult() != null ) {
				id = String.valueOf(mpd.getResult().getId());
				cityText.setText(id);
			}
				
		}
		
	}

}
