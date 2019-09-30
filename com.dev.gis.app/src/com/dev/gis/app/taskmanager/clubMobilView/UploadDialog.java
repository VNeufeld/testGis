package com.dev.gis.app.taskmanager.clubMobilView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
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

import com.bpcs.mdcars.json.protocol.CreateDocumentRequest;
import com.bpcs.mdcars.json.protocol.CreateDocumentResponse;
import com.bpcs.mdcars.json.protocol.UploadResponse;
import com.bpcs.mdcars.model.UploadPictureInfo;
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
 	    
		new ButtonControl(composite, "Upload", 0,  getUploadListener(getShell()));
		
		new ButtonControl(composite, "createDocument", 0,  createDocumentListener(getShell()));

		return composite;
	}
	
	protected SelectionListener getUploadListener(Shell shell) {
		return new ClubMobilUploadListener(shell);
	}
	
	protected SelectionListener createDocumentListener(Shell shell) {
		return new ClubMobilCreateDocumentdListener(shell);
	}

	
	
	private class ClubMobilUploadListener implements SelectionListener {
		
		private final Shell shell;

		public ClubMobilUploadListener(Shell shell) {
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
				
				UploadResponse credentialResponse1 = service.uploadReservationImage(bss, rentalId, "bpcs_image.gif");
					
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
	
	private class ClubMobilCreateDocumentdListener implements SelectionListener {
		
		private final Shell shell;

		public ClubMobilCreateDocumentdListener(Shell shell) {
			this.shell = shell;
		}
/*
 *          "id" : "12345678",
         "RUECKGABE_DATUM" : "06.06.2018",
         "RUECKGABE_UHRZEIT" : "18:00",
         "RUECKGABE_TANK" : "Halbvoll",
         "RUECKGABE_KM" : "11111",
         "RUECKGABE_TEXT" : "Hamburg\nHafen",
         "DATUM" : "07.07.2018",
         "VOR_UND_NACHNAME_UNTERZEICHNENDER" : "Maxi Mustermännchen"

 * (non-Javadoc)
 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
 */
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			try {
				
				
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
				
				File file_bpcs_image = new File("C:/Temp/welcome.png");
				byte[] imagebin = FileUtils.readFileToByteArray(file_bpcs_image);
				
				//String bss = org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(imagebin);
				String bss = org.apache.commons.codec.binary.Base64.encodeBase64String(imagebin);
				logger.info("encoded : "+ bss);
				
				int rentalId = 0;
				if ( ClubMobilModelProvider.INSTANCE.selectedReservation  != null) {
					rentalId = ClubMobilModelProvider.INSTANCE.selectedReservation.getRentalId();
				}
				
				CreateDocumentRequest createDocumentRequest = new CreateDocumentRequest();
				createDocumentRequest.setDocumentName("Fahrzeug-Rueckgabe");
				createDocumentRequest.setRentalId(rentalId);
				createDocumentRequest.setType(500);
				createDocumentRequest.setVersion("0.1.0");
				Metadata md = new Metadata();
				md.id = "12345678";
				md.RUECKGABE_DATUM = "06.06.2018";
				md.RUECKGABE_UHRZEIT = "18:00";
				md.RUECKGABE_TANK = "Halbvoll";
				md.RUECKGABE_KM = "11111";
				md.RUECKGABE_TEXT = "Hamburg\nHafen";
				md.DATUM="07.07.2018";
				md.VOR_UND_NACHNAME_UNTERZEICHNENDER="Maxi Mustermaennchen";
				createDocumentRequest.setData(md);
				
				UploadPictureInfo sig = new UploadPictureInfo();
				List<UploadPictureInfo> signatures = new ArrayList<UploadPictureInfo>();
				signatures.add(sig);
				sig.setBase64Image(bss);
				sig.setName("UNTERSCHRIFT_MIETER_BEI_RUECKGABE");
				sig.setType("png");

				createDocumentRequest.setSignatures(signatures);
				
				CreateDocumentResponse createDocumentResponse = service.createDomecument(createDocumentRequest);
				MessageDialog.openInformation(
						getShell(),"Info","Document created " +createDocumentResponse.getDocumentName());

				
					
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
	
	private static class Metadata {
		public String id ;
		public String RUECKGABE_DATUM ;
		public String RUECKGABE_UHRZEIT ;
		public String RUECKGABE_TANK ;
		public String RUECKGABE_KM ;
		public String RUECKGABE_TEXT ;
		public String DATUM ;
		public String VOR_UND_NACHNAME_UNTERZEICHNENDER ;
	}

}
