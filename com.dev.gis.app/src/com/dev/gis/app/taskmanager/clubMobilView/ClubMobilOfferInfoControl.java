package com.dev.gis.app.taskmanager.clubMobilView;

import java.util.List;

import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.OfferInfoControl;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.OfferDo;
import com.dev.gis.connector.joi.protocol.Extra;

public class ClubMobilOfferInfoControl extends OfferInfoControl {

	protected ClubMobilOfferInfoControl(Composite parent) {
		super(parent);
	}

	public static ClubMobilOfferInfoControl createOfferInfoControl(Composite parent) {
		ClubMobilOfferInfoControl bc = new ClubMobilOfferInfoControl(parent);
		bc.createGroupControl(parent, "CM Booking Offer");
		return bc;
		
	}
	
	public void setInitValues() {
		
		OfferDo selectedOffer = ClubMobilModelProvider.INSTANCE.getSelectedOffer();

		List<Extra> extras = ClubMobilModelProvider.INSTANCE.getSelectedExtras();
		if ( selectedOffer != null) {
			bookingLink.setValue(selectedOffer.getBookLink().toString());
	
			String text = selectedOffer.getGroup()+ ". Price = " + selectedOffer.getPrice().toString() + " Servcat : "+selectedOffer.getServiceCatalogCode() + " / "+selectedOffer.getServiceCatalogId();
	
			bookingCar.setValue(text);
		}
		
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
