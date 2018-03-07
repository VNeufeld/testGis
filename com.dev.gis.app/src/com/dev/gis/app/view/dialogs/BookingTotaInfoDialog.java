package com.dev.gis.app.view.dialogs;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.joi.protocol.BookingTotalInfo;

public class BookingTotaInfoDialog extends Dialog {
	
	private static Logger logger = Logger.getLogger(BookingTotaInfoDialog.class);
	
	protected OutputTextControls carPrice = null;

	protected OutputTextControls expectedTotalPriceinSellCurrency = null;

	
	public BookingTotaInfoDialog(Shell parentShell) {
		super(parentShell);
	    setShellStyle(getShellStyle() | SWT.RESIZE);
		
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setLayout(new GridLayout(2, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).hint(500, 400).applyTo(composite);
		BookingTotalInfo totalInfo = null;
		if ( AdacModelProvider.INSTANCE.bookingResponse != null) {
			totalInfo = AdacModelProvider.INSTANCE.bookingResponse.getBookingTotalInfo();
		}

		carPrice = new OutputTextControls(composite, "carPrice:", 200, 1 );

		OutputTextControls payType = new OutputTextControls(composite, "payType:", 200, 1 );

		OutputTextControls oneWayFee = new OutputTextControls(composite, "oneWayFee:", 200, 1 );
		OutputTextControls oneWayFeeInSellCurrency = new OutputTextControls(composite, "oneWayFeeInSellCurrency:", 200, 1 );
		
		OutputTextControls totalExtraPoaPrice = new OutputTextControls(composite, "totalExtraPoaPrice:", 200, 1 );
		OutputTextControls totalExtraPrepaidPrice = new OutputTextControls(composite, "totalExtraPrepaidPrice:", 200, 1 );
		OutputTextControls totalExtraPriceInSell = new OutputTextControls(composite, "totalExtraPriceInSellCurrency:", 200, 1 );

		OutputTextControls totalPoaPrice = new OutputTextControls(composite, "totalPoaPrice:", 200, 1 );
		OutputTextControls totalPoaPriceInSell = new OutputTextControls(composite, "totalPoaPriceInSellCurrency:", 200, 1 );
		OutputTextControls totalPrepaidPrice = new OutputTextControls(composite, "totalPrepaidPrice:", 200, 1 );
		
		
		expectedTotalPriceinSellCurrency = new OutputTextControls(composite, "expectedTotalPriceinSellCurrency:", 200, 1 );
		
		if ( totalInfo != null) {
			carPrice.setValue(totalInfo.getCarPrice().toString());
			payType.setValue(totalInfo.getOfferedPayment().name());
			

			if ( totalInfo.getOneWayFee() != null)
				oneWayFee.setValue(totalInfo.getOneWayFee().toString());
			
			if ( totalInfo.getOneWayFeeInSellCurrency() != null)
				oneWayFeeInSellCurrency.setValue(totalInfo.getOneWayFeeInSellCurrency().toString());

			if ( totalInfo.getTotalExtraPoaPrice() != null)
				totalExtraPoaPrice.setValue(totalInfo.getTotalExtraPoaPrice().toString());
			
			if ( totalInfo.getTotalExtraPrepaidPrice() != null)
				totalExtraPrepaidPrice.setValue(totalInfo.getTotalExtraPrepaidPrice().toString());
			
			if ( totalInfo.getTotalExtraPriceInSellCurrency() != null)
				totalExtraPriceInSell.setValue(totalInfo.getTotalExtraPriceInSellCurrency().toString());
			
			if ( totalInfo.getTotalPoaPrice() != null)
				totalPoaPrice.setValue(totalInfo.getTotalPoaPrice().toString());
			
			if ( totalInfo.getTotalPoaPriceInSellCurrency() != null)
				totalPoaPriceInSell.setValue(totalInfo.getTotalPoaPriceInSellCurrency().toString());

			if ( totalInfo.getTotalPrepaidPrice() != null)
				totalPrepaidPrice.setValue(totalInfo.getTotalPrepaidPrice().toString());

			expectedTotalPriceinSellCurrency.setValue(totalInfo.getExpectedTotalPriceinSellCurrency().toString());
			
		}
		
		
		return composite;
	}

}
