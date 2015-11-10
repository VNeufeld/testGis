package com.dev.gis.app.view.dialogs;

import java.net.URI;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
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

public class BSCreditCardDialog extends Dialog {

	private Text text;
	
	private final URI bsUri;

    private Browser browser;

	public BSCreditCardDialog(Shell parentShell, URI uri) {
		super(parentShell);
	    setShellStyle(getShellStyle() | SWT.RESIZE);
	    bsUri = uri;
		
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
 	    parent.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).hint(500, 600).applyTo(composite);

 	    text = new Text(composite, SWT.BORDER);
 	    text.setText(bsUri.toString());
 	    text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));


 	    browser = new Browser(composite, SWT.NONE);
 	    browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        browser.setUrl(bsUri.toString());
        
        browser.addLocationListener(new LocationListener() {
			
			@Override
			public void changing(LocationEvent arg0) {
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
