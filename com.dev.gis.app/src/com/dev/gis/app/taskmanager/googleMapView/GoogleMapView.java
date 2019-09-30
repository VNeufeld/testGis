package com.dev.gis.app.taskmanager.googleMapView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.task.execution.api.ITaskResult;

public class GoogleMapView extends TaskViewAbstract {
	public static final String ID = "com.dev.gis.app.view.taskGoogleMap";
	private Text text;
    private Browser browser;

	@Override
	public void createPartControl(Composite parent) {
		
	    parent.setLayout(new GridLayout(2, false));

	    text = new Text(parent, SWT.BORDER);
	    text.setMessage("Enter City");
	    text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

	    Button button = new Button(parent, SWT.PUSH);
	    button.setText("Search");
	    button.addSelectionListener(new SelectionAdapter() {
	      @Override
	      public void widgetSelected(SelectionEvent e) {
	        String city = text.getText();
	        if (city.isEmpty()) {
	          return;
	        }
	        try {
	          browser.setUrl("http://maps.google.com/maps?q="
	              + URLEncoder.encode(city, "UTF-8")
	              + "&output=embed");
	        } catch (UnsupportedEncodingException e1) {
	          e1.printStackTrace();
	        }
	      }
	    });

	    browser = new Browser(parent, SWT.NONE);
	    browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
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

}
