package com.dev.gis.app.taskmanager.SunnyCarsView;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.SunnyModelProvider;

public class SunnyExtServcatFilterTextControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(SunnyExtServcatFilterTextControl.class);

	public SunnyExtServcatFilterTextControl(final Composite parent) {
		super(parent, 100, false);
		
		getText().setToolTipText("Add ServiceCatalog filter : serviceCatalogID");
		
		getText().addVerifyListener(new VerifyListener() {
			
			@Override
			public void verifyText(VerifyEvent event) {
				char myChar = event.character;
				String text = ((Text) event.widget).getText();
				
				boolean err = true;
				
		        // Allow 0-9
		        if (Character.isDigit(myChar)) {
			          event.doit = true;
			          err = false;
		        }
		        
		        if (myChar == '\b' || myChar == ' ') {
		            event.doit = true;		        
			          err = false;
		        }

				
				if ( err) {
					MessageDialog.openError(parent.getShell(), "Verify Error"," please enter only Service Catalog ID"+ text);
			        event.doit = false;
					
				}
				
				
			}
		});

	}

	@Override
	protected String getLabel() {
		return "Servcat";
	}


	@Override
	public void saveValue(String value) {
		SunnyModelProvider.INSTANCE.servcatFilter = value;
		logger.info("servcatFilter: "+value);
	
	}

	@Override
	protected String getDefaultValue() {
		return "";
	}

}
