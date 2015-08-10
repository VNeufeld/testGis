package com.dev.gis.app.taskmanager.SunnyCarsView;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.SunnyModelProvider;

public class SunnyExtStationFilterTextControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(SunnyExtStationFilterTextControl.class);

	public SunnyExtStationFilterTextControl(final Composite parent) {
		super(parent, 100, false);
		
		getText().setToolTipText("Add Station IDS ( pickupStationID, DropOffStationID");
		
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
				
		        if (myChar == ',' && text.length() > 0 && !text.contains(",")) {
		          event.doit = true;
		          err = false;
		        }
				
				if ( err) {
					MessageDialog.openError(parent.getShell(), "Verify Error"," please enter only pickupStationID, dropoffstationID");
			        event.doit = false;
					
				}
				
				
			}
		});
		
	}

	@Override
	protected String getLabel() {
		return "Station ";
	}


	@Override
	public void saveValue(String value) {
		SunnyModelProvider.INSTANCE.stationFilter = value;
		logger.info("Station Filter: "+value);
	
	}

	@Override
	protected String getDefaultValue() {
		return "";
	}

}
