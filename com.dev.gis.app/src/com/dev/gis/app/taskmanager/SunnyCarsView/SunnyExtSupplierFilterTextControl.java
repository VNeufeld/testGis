package com.dev.gis.app.taskmanager.SunnyCarsView;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.SunnyModelProvider;

public class SunnyExtSupplierFilterTextControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(SunnyExtSupplierFilterTextControl.class);

	public SunnyExtSupplierFilterTextControl(final Composite parent) {
		super(parent, 100, false);
		getText().setToolTipText("Add Supplier filter : n,n,...");
		
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
				
		        if (myChar == ',' && text.length() > 0) {
		          event.doit = true;
		          err = false;
		        }
		        
		        if (myChar == '\b' || myChar == ' ') {
		            event.doit = true;		        
			        err = false;
		        }

				
				if ( err) {
					MessageDialog.openError(parent.getShell(), "Verify Error"," please enter only comma separates numbers"+ text);
			        event.doit = false;
					
				}
				
				
			}
		});
		
		
//		getText().addModifyListener(new ModifyListener() {
//			
//			@Override
//			public void modifyText(ModifyEvent event) {
//				System.out.println("Source :" +event.getSource());
//				System.out.println("data :" +event.data);
//				String text = getText().getText();
//				if ( !text.matches("^[0-9]([,][0-9])?$")) {
//					MessageDialog.openError(parent.getShell(), "Error"," please enter only comma separates numbers"+ text);
//				}
//				
//				
//			}
//		});

	}

	@Override
	protected String getLabel() {
		return "Supplier";
	}


	@Override
	public void saveValue(String value) {
		SunnyModelProvider.INSTANCE.supplierFilter = value;
		logger.info("supplierFilter: "+value);
	
	}

	@Override
	protected String getDefaultValue() {
		return "";
	}

}
