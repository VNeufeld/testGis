package com.dev.gis.app.view.elements;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class LocationSearchText extends BasicControl {
	
	private final int size;
	
	protected final String searchLabel;
	
	private Text text;
	
	protected String selectedValue;

	private final Composite parent;
	
	private boolean spannAll = false;

	private final boolean addSearch ;
	
	public LocationSearchText(Composite parent, int size, boolean addSearch, String label) {
		super();
		this.parent = parent;
		this.size = size;
		this.searchLabel = label;
		this.addSearch = addSearch;
		

		
	}
	protected String getDefaultValue() {
		return "";
	}
	public void saveValue(String value) {
		
	}
	
	protected void create() {
		
		String label = searchLabel; //getLabel();

		new Label(parent, SWT.NONE).setText(label);
		
		GridLayout gd = (GridLayout)parent.getLayout();
		int col = gd.numColumns;
		
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(composite);
		
		if ( spannAll)
			GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
			.grab(false, false).span(col-1, 1).hint(size, 28).applyTo(composite);
		else
			GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
			.grab(false, false).hint(size, 24).applyTo(composite);

		text = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(false, false).hint(200, 24).applyTo(text);

		if ( addSearch) {
			Button buttonSearch = new Button(composite, SWT.PUSH | SWT.LEFT);
			buttonSearch.setText("Location Search");
			buttonSearch.addSelectionListener(getSelectionListener(parent.getShell(), text));
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
			.grab(false, true).applyTo(buttonSearch);
		}


		text.setText(getDefaultValue());
		
		saveValue(text.getText());

		
		text.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent arg0) {
				if (arg0.character == '\r') {
					
					String value = text.getText();
					saveValue(value);
				}
			}


			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		text.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void focusLost(FocusEvent arg0) {
				String value = text.getText();
				saveValue(value);
			}
		});
		
	}

	protected String getLabel() {
		return " Location Search";
	}

	protected SelectionListener getSelectionListener(Shell shell, Text text) {
		return new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				   MessageBox messageBox = new MessageBox(parent.getShell(), SWT.ICON_ERROR | SWT.OK);
		           messageBox.setText("Info");
			       messageBox.setMessage("No Listener implemented");
			       messageBox.open();					
				
			}
		}; 
	}

	protected SelectionListener getSelectionListener() {
		// TODO Auto-generated method stub
		return null;
	}
	public Composite getParent() {
		return parent;
	}
	
	 

}
