package com.dev.gis.app.view.dialogs;

import java.net.URI;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class PayPalDialog extends Dialog {

	private Text text;
	
	private final String url;

    private Browser browser;

	public PayPalDialog(Shell parentShell, String url) {
		super(parentShell);
	    setShellStyle(getShellStyle() | SWT.RESIZE);
	    
	    this.url = url;
		
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
 	    parent.setLayout(new GridLayout(1, false));

 	    text = new Text(parent, SWT.BORDER);
 	    text.setMessage("Enter City");
 	    text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));


 	    browser = new Browser(parent, SWT.NONE);
 	    browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        browser.setUrl(url);
        
        browser.addLocationListener(new LocationListener() {
			
			@Override
			public void changing(LocationEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println(" changing "+arg0.location);
				
			}
			
			@Override
			public void changed(LocationEvent arg0) {
				System.out.println(" changed "+arg0.location);
				
			}
		});
		
		return composite;
	}


}
