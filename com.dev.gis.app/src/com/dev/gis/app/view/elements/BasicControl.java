package com.dev.gis.app.view.elements;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.osgi.service.prefs.BackingStoreException;

public class BasicControl {
	
	private static String PREFERENCE_PATH = "CONTROL_PREFERENCE";

	protected String readProperty(String property) {
		final IEclipsePreferences preferences = ConfigurationScope.INSTANCE
				.getNode(PREFERENCE_PATH);
		String value = preferences.get(property,"");
		return value;

	}
	
	protected void saveProperty(String property, String value) {
		final IEclipsePreferences preferences = ConfigurationScope.INSTANCE
				.getNode(PREFERENCE_PATH);
		preferences.put(property, value);

		try {
			preferences.flush();
		} catch (BackingStoreException e1) {
		}

	}

	protected class AbstractListener implements SelectionListener {

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			widgetSelected(arg0);

		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			// TODO Auto-generated method stub
			
		}

	}

	protected Composite createComposite(final Composite parent,int columns, int span, boolean grab) {
		
		GridLayout gd = (GridLayout)parent.getLayout();
		int col = gd.numColumns;
		
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(columns).equalWidth(false).applyTo(composite);

		if ( span < 0)
			GridDataFactory.fillDefaults().span(col, 1)
					.align(SWT.FILL, SWT.BEGINNING).grab(grab, false)
					.applyTo(composite);
		else
			GridDataFactory.fillDefaults().span(span, 1)
			.align(SWT.FILL, SWT.BEGINNING).grab(grab, false)
			.applyTo(composite);


		return composite;
	}
	
	protected Group createGroupSpannAll(final Composite parent,String title, int columns) {
		
		GridLayout gd = (GridLayout)parent.getLayout();
		int col = gd.numColumns;
		
		final Group groupResult = new Group(parent, SWT.TITLE);
		groupResult.setText(title);		
		GridLayoutFactory.fillDefaults().numColumns(columns).equalWidth(false).applyTo(groupResult);

		GridDataFactory.fillDefaults().span(col, 1)
					.align(SWT.FILL, SWT.BEGINNING).grab(true, false)
					.applyTo(groupResult);

		return groupResult;
	}

	protected Group createGroupSpannAllRows(final Composite parent,String title, int columns, int rows) {
		
		GridLayout gd = (GridLayout)parent.getLayout();
		int col = gd.numColumns;
		
		final Group groupResult = new Group(parent, SWT.TITLE);
		groupResult.setText(title);		
		GridLayoutFactory.fillDefaults().numColumns(columns).equalWidth(false).applyTo(groupResult);

		GridDataFactory.fillDefaults().span(col, rows)
					.align(SWT.FILL, SWT.BEGINNING).grab(true, false)
					.applyTo(groupResult);

		return groupResult;
	}

	protected void showErrors(com.dev.gis.connector.sunny.Error error) {
		
		String message = "";
		message = message + error.getErrorNumber()+ "  "+ error.getErrorText() + " " + error.getErrorType() + " ;";
		MessageDialog.openError(
				null,"Error",message);
		
	}
	
}
