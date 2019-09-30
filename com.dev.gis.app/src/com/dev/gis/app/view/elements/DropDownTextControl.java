package com.dev.gis.app.view.elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public abstract class DropDownTextControl extends BasicControl {

	private Composite parent;
	private int size;

	Combo combo;
	private int index=0;
	String selectedText;
	protected List<String> elements;
	private String label = "Server URL";

	public DropDownTextControl(Composite parent, int size, boolean span) {
		init(parent, size, span);
		//elements = new ArrayList<String>();
	}

	private void init(Composite parent, int size, boolean span) {
		// TODO Auto-generated method stub
		this.parent = parent;
		this.size = size;
		this.elements = new ArrayList<String>();
		create(span);

		getDefaultValue();
		
		index = getSavedSelectedId();
		combo.select(index);
		selectedText = combo.getText();

		if (!StringUtils.isEmpty(selectedText))
			saveValue(selectedText);
		combo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				selectedText = combo.getText();
				//combo.setText(selectedText);
				saveValue(selectedText);
				if(elements.contains(selectedText)) {
					index = elements.indexOf(selectedText);
					saveIndex();
					combo.select(index);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		combo.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent arg0) {
				if (arg0.character == '\r') {

					selectedText = combo.getText();
					saveValue(selectedText);
					if(elements.contains(selectedText)) {
						index = elements.indexOf(selectedText);
						saveIndex();
						combo.select(index);
					}
					
				}
			}


			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		combo.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void focusLost(FocusEvent arg0) {
				selectedText = combo.getText();
				//combo.setText(selectedText);
				saveValue(selectedText);
				if(elements.contains(selectedText)) {
					index = elements.indexOf(selectedText);
					saveIndex();
					combo.select(index);
				}else {
					
				}
			}
		});

	}

	protected abstract void saveValue(String selectedText2);

	protected abstract String getPropertyName();

	protected int getSavedSelectedId() {
		String value = readProperty(getPropertyName());
		if (StringUtils.isNotEmpty(value))
			return Integer.valueOf(value);
		return 0;
	}

	private void saveIndex() {
		// TODO Auto-generated method stub
		saveProperty(getPropertyName(), ""+index);
	}


	protected int getLabelSize() {
		return 0;
	}

	protected String getLabel() {
		return label;
	}

	private void create(boolean span) {
		// TODO Auto-generated method stub

		String label = getLabel();

		Label lb = new Label(parent, SWT.NONE);
		lb.setText(label);

		int lbSize = getLabelSize();
		if (lbSize > 0) {
			GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING).grab(false, false).hint(lbSize, 16)
					.applyTo(lb);
		}

		combo = new Combo(parent, SWT.DROP_DOWN);
		GridLayout gd = (GridLayout) parent.getLayout();
		int col = gd.numColumns;

		if (span)
			GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING).grab(false, false).span(col - 1, 1)
					.hint(size, 16).applyTo(combo);
		else
			GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING).grab(false, false).hint(size, 16)
					.applyTo(combo);

	}

	protected String getDefaultValue() {

		return "5";

	}

	public String getSelectedValue() {
		return selectedText;
	}

	public void updateView(List<String> elements) {
		if (elements == null) {
			return;
		}
		String[] s = elements.toArray(new String[elements.size()]);
		;

		this.combo.setItems(s);
	}

	public void setSelectedValue(String value) {
		selectedText = value;

		saveValue(value);
	}

	protected Combo getText() {
		return combo;
	}

}
