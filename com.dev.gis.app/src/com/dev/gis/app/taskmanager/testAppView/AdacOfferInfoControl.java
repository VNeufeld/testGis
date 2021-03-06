package com.dev.gis.app.taskmanager.testAppView;

import java.util.List;

import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.OfferInfoControl;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.OfferDo;
import com.dev.gis.connector.joi.protocol.Extra;

public class AdacOfferInfoControl extends OfferInfoControl {

	protected AdacOfferInfoControl(Composite parent) {
		super(parent);
	}

	public static AdacOfferInfoControl createOfferInfoControl(Composite parent) {
		AdacOfferInfoControl bc = new AdacOfferInfoControl(parent);
		bc.createGroupControl(parent, "Booking Offer");
		return bc;
		
	}
	
	public void setInitValues() {
		
		OfferDo selectedOffer = AdacModelProvider.INSTANCE.getSelectedOffer();

		List<Extra> extras = AdacModelProvider.INSTANCE.getSelectedExtras();
		
		bookingLink.setValue(selectedOffer.getBookLink().toString());

		String text = selectedOffer.getGroup()+ ". Price = " + selectedOffer.getPrice().toString() + " Servcat : "+selectedOffer.getServiceCatalogCode() + " / "+selectedOffer.getServiceCatalogId();

		bookingCar.setValue(text);
		
		if ( extras != null && extras.size() > 0) {
			StringBuilder sbx = new StringBuilder();
			for ( Extra extra : extras) {
				sbx.append(" "+ extra.getId()+ " "+extra.getCode()+" :  " + extra.getName());
				if ( extra.getPrice() != null)
					sbx.append(". Price :"+extra.getPrice().toString()+ " ;  ");
			}
			selectedExtras.setValue(sbx.toString());
		}
		else
			selectedExtras.setValue("");
		
		
	
	}

	
}
