package com.dev.gis.app.taskmanager.clubMobilView;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import com.bpcs.mdcars.json.protocol.CredentialResponse;
import com.bpcs.mdcars.json.protocol.GetCustomerResponse;
import com.bpcs.mdcars.model.Clerk;
import com.bpcs.mdcars.model.Credential;
import com.bpcs.mdcars.model.Customer;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.ext.BusinessException;
import com.dev.gis.connector.joi.protocol.Token;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomerDialog extends Dialog {
	
	private static Logger logger = Logger.getLogger(CustomerDialog.class);
	
	private Customer customer;
	
	protected OutputTextControls customerResult = null;

	
	private CustomerNoTextControl customerNoTextControl;
	
	public CustomerDialog(Shell parentShell) {
		super(parentShell);
	    setShellStyle(getShellStyle() | SWT.RESIZE);
		
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
 	    parent.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).hint(500, 400).applyTo(composite);
 	    
		customerNoTextControl = new CustomerNoTextControl(composite, 300, true);

		new ButtonControl(composite, "GetCustomer", 0,  getListener(getShell()));

		customerResult = new OutputTextControls(composite, "customerResult:", 500, 1 );
		
		return composite;
	}
	
	protected SelectionListener getListener(Shell shell) {
		return new ClubMobilCustomerListener(shell);
	}
	
	private class ClubMobilCustomerListener implements SelectionListener {
		
		private final Shell shell;

		public ClubMobilCustomerListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			try {
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
				
				String customerNo = customerNoTextControl.getSelectedValue();
						
				GetCustomerResponse response = service.getCustomer(customerNo);
				if ( response.getCustomer() == null) {
					if ( !response.getErrors().isEmpty())
						showErrors(response.getErrors().get(0).getErrorText());
					else
						showErrors("GetCustomer not successfull");
				}
				else {
					String result = response.getCustomer().getPerson().getName() + " " + response.getCustomer().getPerson().getFirstName();
					customer = response.getCustomer();
					customerResult.setValue(result);
				}
					
			}
			catch(BusinessException err) {
				logger.error(err.getMessage());
				 com.dev.gis.connector.joi.protocol.Error error = null;
				try {
					error = convertJsonStringToObject(err.getMessage(),com.dev.gis.connector.joi.protocol.Error.class);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if ( error != null)
					showErrors(error.getErrorText());
				else
					showErrors(err.getMessage());
				
			}
			catch(Exception err) {
				logger.error(err.getMessage());
				String errName = err.getClass().getName();
				showErrors(new com.dev.gis.connector.sunny.Error(1,1, errName + " : " +err.getMessage()));
				
			}

		}


		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			
		}

	}
	
	
	protected void showErrors(com.dev.gis.connector.sunny.Error error) {
		
		String message = error.getErrorText()+ "\n";
		message = message + error.getErrorNumber()+ "  "+ error.getErrorText() + " " + error.getErrorType() + " ;";
		MessageDialog.openError(
				getShell(),"Error",message);
		
	}

	public void showErrors(String message) {
		MessageDialog.openError(
				getShell(),"Error",message);
		
	}


	protected static <T> T convertJsonStringToObject(String jsonString, Class<T> claszz)  throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		T result = mapper.readValue(jsonString, claszz);
		return result;
	}

	public Customer getCustomer() {
		return customer;
	}

}
