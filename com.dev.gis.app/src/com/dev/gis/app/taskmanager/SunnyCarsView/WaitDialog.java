package com.dev.gis.app.taskmanager.SunnyCarsView;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class WaitDialog extends Dialog {

	private Text text;
	
	private int close = 0;
	

	protected WaitDialog(Shell parentShell) {
		super(parentShell);
	    setShellStyle(getShellStyle() | SWT.RESIZE | SWT.CLOSE | SWT.MODELESS | SWT.BORDER | SWT.TITLE);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
 	    parent.setLayout(new GridLayout(1, false));

 	    text = new Text(parent, SWT.BORDER);
 	    text.setMessage("Wait ...");
 	    text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		return composite;
	}


	public void setClose(int close) {
		this.close = close;
	}


}
