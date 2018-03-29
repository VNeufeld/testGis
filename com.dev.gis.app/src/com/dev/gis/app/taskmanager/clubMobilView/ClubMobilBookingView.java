package com.dev.gis.app.taskmanager.clubMobilView;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.bpcs.mdcars.model.Address;
import com.bpcs.mdcars.model.CommonCustomerInfo;
import com.bpcs.mdcars.model.Customer;
import com.bpcs.mdcars.model.Person;
import com.bpcs.mdcars.model.PhoneNumber;
import com.dev.gis.app.model.service.adac.PaypalService;
import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.app.taskmanager.testAppView.PromotionCodeTextControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.MemberNoTextControl;
import com.dev.gis.app.view.elements.PayPalControl;
import com.dev.gis.app.view.elements.PaymentTypeControl;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.joi.protocol.Extra;

public class ClubMobilBookingView extends TaskViewAbstract {

	private static Logger logger = Logger.getLogger(ClubMobilBookingView.class);

	private static int instanceNum = 1;

	public static final String ID = "com.dev.gis.app.view.ClubMobilBookingView";
	
	private ClubMobilOfferInfoControl offerInfoControl = null;
	
	private ClubMobilBookingControl bookingControl = null;

	@Override
	protected void createWindow(Composite composite) {
		createControls(composite);
	}

	private void createControls(Composite composite) {
		
		offerInfoControl = ClubMobilOfferInfoControl.createOfferInfoControl(composite);
		
		Composite paymentComposite = createGroupSpannAll(composite,"CM Payment",3);

		PaymentControlControl.createControl(paymentComposite);

		ClubMobilCreditCardControl.createControl(paymentComposite);
		
		PayPalControl.createControl(paymentComposite, new PaypalService());

		Composite customerComposite = createGroupSpannAll(composite,"Customer",4);
		
		new MemberNoTextControl(customerComposite);

		new PaymentTypeControl(customerComposite);
		
		PromotionCodeTextControl.createPromotionCodeControl(customerComposite);
		
		new ButtonControl(customerComposite, "Put Customer", 0,  new ClubMobilCustomerListener(parent.getShell()));
		
		bookingControl = ClubMobilBookingControl.createBookingControl(composite);
		
	}

	protected class ClubMobilCustomerListener extends AbstractListener {
		
		private final Shell shell;

		public ClubMobilCustomerListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {

			try {

				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				ClubMobilHttpService service = serviceFactory
						.getClubMobilleJoiService();
				
				com.bpcs.mdcars.model.Customer customer = new Customer();
				CommonCustomerInfo commonCustomerInfo = new CommonCustomerInfo();
				commonCustomerInfo.setCustomerNo(AdacModelProvider.INSTANCE.memberNo);
				customer.setCommonCustomerInfo(commonCustomerInfo);
				
				Person person = new  Person();
				person.setName("Miller");
				person.setFirstName("Iwan");
				customer.setPerson(person);

				Address addres = new Address();
				addres.setCity("München");
				addres.setStreet("Schönstr");
				addres.setZip("81543");
				customer.setAddress(addres);
				
				customer.setEMail("bgt@adac.de");
				
				PhoneNumber phone = new PhoneNumber();
				phone.setExtension("08963456543");
				customer.setPhone(phone);
				
				// StartVerify
				service.putCustomer(customer);

				person = new  Person();
				person.setName("Meier");
				person.setFirstName("John");
				customer.setPerson(person);
				
				phone = new PhoneNumber();
				phone.setExtension("0172/63456543");
				customer.setPhone(phone);
				
				service.putDriver(customer);
				
			} catch (Exception err) {
				logger.error(err.getMessage(),err);
				
				MessageDialog.openError(
						shell,"Error",err.getMessage());
				
			}

		}

	}

	private void showOffer( List<Extra> extras) {

		ClubMobilModelProvider.INSTANCE.getSelectedExtras().clear();
		if( extras != null)
			ClubMobilModelProvider.INSTANCE.getSelectedExtras().addAll(extras);
		
		offerInfoControl.setInitValues();
		bookingControl.setInitValues();
		
	}

	public static void updateView(final List<Extra> extras) {

		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				try {
					logger.info(" showResult : run instanceNum = "
							+ instanceNum);
					// Show protocol, show results
					IWorkbenchPage wp = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage();
					ClubMobilBookingView viewPart = (ClubMobilBookingView) wp.showView(
							ClubMobilBookingView.ID, Integer.toString(instanceNum),
							IWorkbenchPage.VIEW_ACTIVATE);

					viewPart.showOffer(extras);

					logger.info(" add view :" + viewPart.getTitle());

				} catch (PartInitException e) {
					logger.error(e.getMessage(), e);
				}
			}
		});

	}


}
