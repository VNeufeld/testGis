package com.dev.gis.app.view.listener;


import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.dev.gis.connector.api.SunnyOfferDo;
import com.dev.gis.connector.sunny.Address;
import com.dev.gis.connector.sunny.Station;
import com.dev.gis.connector.sunny.Supplier;
import com.dev.gis.connector.sunny.Vehicle;

public class SunnyTooltipListener extends TooltipListener {


	public SunnyTooltipListener(Shell shell, Table table) {
		super(shell, table);
	}

	@Override
	protected String getTooltipText(TableItem item, int pos) {
		Object data = item.getData();
		if ( data instanceof SunnyOfferDo) {
			SunnyOfferDo offer = (SunnyOfferDo) data;
			if ( pos < 20)
				return createVehicleInfo(offer);
			if ( pos > 500 && pos < 700)
				return createStationInfo(offer);
			
		}
		return "";
	}

	private String createStationInfo(SunnyOfferDo offer) {
		
		StringBuffer sb = new StringBuffer();
		Station station = offer.getPickupStation();
		Supplier supplier = station.getSupplier();
		Address address = station.getAddres();
		
		sb.append("Station "+station.getIdentifier()+ " " + station.getId());
		sb.append("\n");
		if ( supplier != null)
			sb.append("Supplier "+supplier.getId()+ " / "+supplier.getName()+ " sgr: " + supplier.getSupplierGroupId());
		else
			sb.append("Supplier "+station.getSupplierId()+ " sgr: " + station.getSupplierGroupId());
		sb.append("\n");
		sb.append("link : "+station.getLink());
		sb.append("\n");
		if ( address != null) {
			sb.append("address : "+address.getCity());
		}
		
		return sb.toString();
	}

	private String createVehicleInfo(SunnyOfferDo offer) {
		Vehicle vd = offer.getVehicle();
		StringBuffer sb = new StringBuffer();
		sb.append("Vehicle "+vd.getManufacturer());
		sb.append("\n");
		sb.append("Model: "+vd.getVehicleModel());
		sb.append("\n");
		sb.append("Acriss: "+vd.getACRISS());
		sb.append("\n");
		sb.append("Category: "+offer.getCarCategoryId());
		sb.append("\n");
		sb.append("Doors: "+vd.getNrDoors()+ " Seats :"+vd.getNrSeats());
		sb.append("\n");
		return sb.toString();
	}

	@Override
	protected TableItem getTableItem(int x, int y) {
		return super.getTableItem(x, y);
	}

}
