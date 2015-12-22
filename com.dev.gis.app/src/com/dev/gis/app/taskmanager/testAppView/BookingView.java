package com.dev.gis.app.taskmanager.testAppView;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.dev.gis.app.model.service.adac.PaypalService;
import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.app.view.elements.CreditCardControl;
import com.dev.gis.app.view.elements.MemberNoTextControl;
import com.dev.gis.app.view.elements.PayPalControl;
import com.dev.gis.app.view.elements.PaymentTypeControl;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.joi.protocol.Extra;

public class BookingView extends TaskViewAbstract {

	private static Logger logger = Logger.getLogger(BookingView.class);

	private static int instanceNum = 1;

	public static final String ID = "com.dev.gis.app.view.BookingView";
	
	private AdacOfferInfoControl offerInfoControl = null;
	
	private AdacBookingControl bookingControl = null;

	@Override
	protected void createWindow(Composite composite) {
		createControls(composite);
	}

	private void createControls(Composite composite) {
		
		offerInfoControl = AdacOfferInfoControl.createOfferInfoControl(composite);
		
		Composite paymentComposite = createGroupSpannAll(composite,"Payment",2);

		AdacCreditCardControl.createControl(paymentComposite);
		
		PayPalControl.createControl(paymentComposite, new PaypalService());

		Composite customerComposite = createGroupSpannAll(composite,"Customer",4);
		
		new MemberNoTextControl(customerComposite);

		new PaymentTypeControl(customerComposite);
		
		bookingControl = AdacBookingControl.createBookingControl(composite);
		
	}


	private void showOffer( List<Extra> extras) {

		AdacModelProvider.INSTANCE.getSelectedExtras().clear();
		if( extras != null)
			AdacModelProvider.INSTANCE.getSelectedExtras().addAll(extras);
		
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
					BookingView viewPart = (BookingView) wp.showView(
							BookingView.ID, Integer.toString(instanceNum),
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
