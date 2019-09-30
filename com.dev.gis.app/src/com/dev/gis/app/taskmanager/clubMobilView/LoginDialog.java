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
import org.joda.time.LocalDateTime;

import com.bpcs.mdcars.json.protocol.CredentialResponse;
import com.bpcs.mdcars.model.Clerk;
import com.bpcs.mdcars.model.Credential;
import com.bpcs.mdcars.model.Token;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.ext.BusinessException;
import com.dev.gis.connector.joi.protocol.CredentialLocal;
import com.dev.gis.connector.joi.protocol.CredentialResponseLocal;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginDialog extends Dialog {
	
	private static Logger logger = Logger.getLogger(LoginDialog.class);


	protected OutputTextControls tokenControl = null;

	protected OutputTextControls loginResult = null;
	
	private  ChangePasswordTextControl changePasswordCtl = null;
	
	private final boolean change_pwd;
	
	private Clerk clerk;
	
	private Token token;
	
	public LoginDialog(Shell parentShell) {
		super(parentShell);
		this.change_pwd = false; 
	    setShellStyle(getShellStyle() | SWT.RESIZE);
		
	}

	
	public LoginDialog(Shell parentShell, boolean change_pwd) {
		super(parentShell);
		this.change_pwd = change_pwd; 
	    setShellStyle(getShellStyle() | SWT.RESIZE);
		
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
 	    parent.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).hint(500, 400).applyTo(composite);
 	    
 	    new UserTextControl(composite, 300, true);

 	    new PasswordTextControl(composite, 300, true);
 	    if ( change_pwd)
 	    	changePasswordCtl = new  ChangePasswordTextControl(composite, 300, true);
		
		new ButtonControl(composite, "Login", 0,  getLoginListener(getShell()));
		if ( change_pwd)
			new ButtonControl(composite, "Change Password", 0,  getChangePwdListener(getShell()));
		
		tokenControl = new OutputTextControls(composite, "token:", 500, 1 );

		loginResult = new OutputTextControls(composite, "loginResult:", 500, 1 );
		

		return composite;
	}
	
	protected SelectionListener getLoginListener(Shell shell) {
		return new ClubMobilLoginListener(shell);
	}

	protected SelectionListener getChangePwdListener(Shell shell) {
		return new ClubMobilChangePwdListener(shell);
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
				
				String user = ClubMobilModelProvider.INSTANCE.loginUser;
				String pwd = ClubMobilModelProvider.INSTANCE.loginPassword;
				
				//pwd = encrypt(pwd);

				CredentialLocal credential = new CredentialLocal(); 
				credential.setUser(user);
				credential.setPwd(pwd);
				
						
				CredentialResponseLocal credentialResponse =service.login(credential);
				if ( credentialResponse.getClerk() == null) {
					if ( !credentialResponse.getErrors().isEmpty())
						showErrors(credentialResponse.getErrors().get(0).getErrorText());
					else
						showErrors("Login not successfull");
				}
				else if ( credentialResponse.isSmsCheckNeeded()){
					String smdCode = credentialResponse.getSmsSecurityCode();
					credentialResponse = service.loginTwoFactor(smdCode);
					clerk = credentialResponse.getClerk();
					token = credentialResponse.getToken();
					tokenControl.setValue(token.getToken());
					String result = credentialResponse.getClerk().getName() + " " + credentialResponse.getClerk().getFirstName();
					loginResult.setValue(result);
					
				}
				else if (credentialResponse.getCredential().isReset()) {
					String result = credentialResponse.getClerk().getName() + " " + credentialResponse.getClerk().getFirstName();

					Date pwdValidTo = credentialResponse.getCredential().getPwdValidTo();
					String expiredTIme = "";
					if ( pwdValidTo != null) {
						org.joda.time.LocalDateTime ld = new LocalDateTime(pwdValidTo);;
						expiredTIme = expiredTIme + " pwd valid to " + ld.toString("yyyy-MM-dd");
					}
					result = result + " pwd change needed";
					String message = " Password is nicht mehr gültig und muss geändert werden.  Ablaufzeit " + expiredTIme;
					MessageDialog.openInformation(getShell(), "Info", message);
						
				}
				else {
					String result = credentialResponse.getClerk().getName() + " " + credentialResponse.getClerk().getFirstName();
					clerk = credentialResponse.getClerk();
					
					token = credentialResponse.getToken();
					
					tokenControl.setValue(token.getToken());
					
					loginResult.setValue(result);
					
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
	
	private class ClubMobilChangePwdListener implements SelectionListener {
		
		private final Shell shell;

		public ClubMobilChangePwdListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			try {
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
				
				String user = ClubMobilModelProvider.INSTANCE.loginUser;
				String pwd = ClubMobilModelProvider.INSTANCE.loginPassword;
				
				String newPwd = ClubMobilModelProvider.INSTANCE.changePasword;
				
//				pwd = encrypt(pwd);
//				newPwd = encrypt(newPwd);

				logger.info("newPwd = " + newPwd);
				
				Credential credential = new Credential(); 
				credential.setUser(user);
				credential.setPwd(pwd);
				credential.setNewpwd(newPwd);
						
				CredentialResponse credentialResponse = service.changePwd(credential);
				if ( credentialResponse.getClerk() == null) {
					if ( !credentialResponse.getErrors().isEmpty())
						showErrors(credentialResponse.getErrors().get(0).getErrorText());
					else
						showErrors("Login not successfull");
				}
				else {
					if ( credentialResponse.getStatus() == 0) {
						MessageDialog.openInformation(getShell(), "Info", "Password erfolgreich geändert. Bitte melden Sie sich mit dem neuen Passwort an");
					}
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

		private String encrypt(String pwd) {
			String shaHex = DigestUtils.shaHex(pwd);
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

	public Clerk getClerk() {
		return clerk;
	}


	public Token getToken() {
		return token;
	}


	public void setToken(Token token) {
		this.token = token;
	}

}
