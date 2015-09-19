package com.dev.gis.app.view.listener.adac;


import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.dev.gis.app.view.listener.TooltipListener;
import com.dev.gis.connector.api.OfferDo;
import com.dev.gis.connector.joi.protocol.VehicleDescription;

public class AdacTooltipListener extends TooltipListener {


	public AdacTooltipListener(Shell shell, Table table) {
		super(shell, table);
	}

	@Override
	protected String getTooltipText(TableItem item, int pos) {
		Object data = item.getData();
		StringBuffer sb = new StringBuffer();
		if ( data instanceof OfferDo) {
			OfferDo offer = (OfferDo) data;
			VehicleDescription vd = offer.getModel().getVehicle();
			sb.append("Vehicle1: "+vd.getManufacturer());
			sb.append("\n");
			sb.append("Model: "+vd.getVehicleModel());
		}
		return sb.toString();
	}

	@Override
	protected TableItem getTableItem(int x, int y) {
		if ( x < 10)
			return super.getTableItem(x, y);
		return null;
	}

}
