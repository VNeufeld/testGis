package com.dev.gis.app.taskmanager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class TestAppView extends ViewPart {
	public static final String ID = "com.dev.gis.app.task.TestAppView";

	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout());
		
		Label label = new Label(container, SWT.BORDER);
		label.setText("This is a label:");
		label.setToolTipText("This is the tooltip of this label");

		Text text = new Text(container, SWT.NONE);
		text.setText("This is the text in the text widget");
		text.setBackground(container.getDisplay().getSystemColor(SWT.COLOR_BLACK));
		text.setForeground(container.getDisplay().getSystemColor(SWT.COLOR_WHITE));
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
