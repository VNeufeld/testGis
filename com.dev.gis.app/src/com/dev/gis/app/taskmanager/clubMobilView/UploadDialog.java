package com.dev.gis.app.taskmanager.clubMobilView;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.internal.preferences.Base64;
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
import org.joda.time.LocalDateTime;

import com.bpcs.mdcars.json.protocol.CredentialResponse;
import com.bpcs.mdcars.json.protocol.UploadResponse;
import com.bpcs.mdcars.model.Credential;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.ext.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UploadDialog extends Dialog {
	
	private static Logger logger = Logger.getLogger(UploadDialog.class);

	protected OutputTextControls loginResult = null;

	
	public UploadDialog(Shell parentShell) {
		super(parentShell);
	    setShellStyle(getShellStyle() | SWT.RESIZE);
		
	}


	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
 	    parent.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).hint(500, 400).applyTo(composite);
 	    
		new ButtonControl(composite, "Upload", 0,  getLoginListener(getShell()));
		

		return composite;
	}
	
	protected SelectionListener getLoginListener(Shell shell) {
		return new ClubMobilLoginListener(shell);
	}

	
	private class ClubMobilLoginListener implements SelectionListener {
		
		private final Shell shell;

		public ClubMobilLoginListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			try {
				
				
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
				
				File file_bpcs_image = new File("C:/Temp/bpcs_image.gif");
				byte[] imagebin = FileUtils.readFileToByteArray(file_bpcs_image);
				
				//String bss = org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(imagebin);
				String bss = org.apache.commons.codec.binary.Base64.encodeBase64String(imagebin);
				logger.info("encoded : "+ bss);
				
//				byte[] img = org.apache.commons.codec.binary.Base64.decodeBase64(bss.getBytes());
//				File file_bpcs_image2 = new File("C:/Temp/bpcs_image2.gif");
//				FileUtils.writeByteArrayToFile(file_bpcs_image2, img);
				
				int rentalId = 0;
				if ( ClubMobilModelProvider.INSTANCE.selectedReservation  != null) {
					rentalId = ClubMobilModelProvider.INSTANCE.selectedReservation.getRentalId();
					
				}
				
				UploadResponse credentialResponse1 = service.upload2(bss, rentalId);
						
				//CredentialResponse credentialResponse = service.upload(imagebin, bss);
					
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

		private String encrypt(String pwd) {
			String shaHex = DigestUtils.shaHex(pwd);
			System.out.println("shaHex = " + shaHex);
			return shaHex;
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

}
