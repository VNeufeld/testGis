package com.dev.gis.app.view.elements;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class OutputTextControls extends BasicControl {

	private Text text;

	private final Composite parent;

	public OutputTextControls(Composite parent, String label, int size) {
		super();
		this.parent = parent;

		new Label(parent, SWT.NONE).setText(label);

		GridLayout gd = (GridLayout) parent.getLayout();
		int col = gd.numColumns;

		text = new Text(this.parent, SWT.BORDER | SWT.SINGLE);

		if (size < 0) {
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
					.grab(true, false).span(col-1, 1).applyTo(text);

		} else
			GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
					.grab(false, false).span(col - 1, 1).hint(size, 16)
					.applyTo(text);

	}
	
	public OutputTextControls(Composite parent, String label, int size, int span) {
		super();
		this.parent = parent;

		new Label(parent, SWT.NONE).setText(label);

		text = new Text(this.parent, SWT.BORDER | SWT.SINGLE);

		if (size < 0) {
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
					.grab(true, false).span(span, 1).applyTo(text);

		} else
			GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
					.grab(false, false).span(span, 1).hint(size, 16)
					.applyTo(text);

	}
	

	protected String getDefaultValue() {
		return "5";
	}

	public void setValue(String value) {
		text.setText(value);
	}

	public String getValue() {
		return text.getText();
	}

	public Text getControl() {
		return text;
	}
	
}
