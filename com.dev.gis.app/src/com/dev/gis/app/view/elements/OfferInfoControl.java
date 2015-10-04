package com.dev.gis.app.view.elements;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.view.listener.SearchCitySelectionListener;
import com.dev.gis.connector.api.ModelProvider;
import com.dev.gis.connector.api.SunnyOfferDo;
import com.dev.gis.connector.api.TaskProperties;
import com.dev.gis.connector.sunny.Extra;

public class OfferInfoControl extends EditPartControl {
	private static Logger logger = Logger.getLogger(OfferInfoControl.class);
	
	private OutputTextControls bookingLink;
	private OutputTextControls carPrice;
	private OutputTextControls selectedExtras;
	private OutputTextControls bookingCar;

	public static OfferInfoControl createOfferInfoControl(Composite parent) {
		OfferInfoControl bc = new OfferInfoControl(parent);
		bc.createGroupControl(parent, "Booking Offer");
		return bc;
		
	}

	private OfferInfoControl(Composite parent) {
		super(parent);
	}

	@Override
	protected void createElements(Group groupStamp) {

		bookingLink = new OutputTextControls(groupStamp, "Booking Link", -1,1 );

		bookingCar = new OutputTextControls(groupStamp, "Car ", -1,1 );
		
		//carPrice = new OutputTextControls(groupStamp, "Price", -1,1 );

		selectedExtras = new OutputTextControls(groupStamp, "Extras ", -1,1 );

	}

	@Override
	protected void createButtons(Group groupStamp) {
	}
	
	public void setInitValues(SunnyOfferDo selectedOffer, List<Extra> extras) {
		//carPrice.setValue(selectedOffer.getPrice().toString());
		bookingLink.setValue(selectedOffer.getBookLink().toString());

		bookingCar.setValue(selectedOffer.getGroup()+ " : " + selectedOffer.getVehicle().getACRISS()
				+ " Model : " + selectedOffer.getVehicle().getVehicleModel() + ". Price = "+selectedOffer.getPrice().toString());
		
		if ( extras != null && extras.size() > 0) {
			StringBuilder sbx = new StringBuilder();
			for ( Extra extra : extras) {
				sbx.append(""+ extra.getId()+ " "+extra.getCode()+" :  " + extra.getName());
				if ( extra.getPrice() != null)
					sbx.append(". Price :"+extra.getPrice().toString());
			}
			selectedExtras.setValue(sbx.toString());
		}
		
		
		
	
	}

}
