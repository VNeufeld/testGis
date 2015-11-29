package com.dev.gis.app.view.listener.adac;


import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.dev.gis.app.view.listener.TooltipListener;
import com.dev.gis.connector.api.OfferDo;
import com.dev.gis.connector.joi.protocol.Address;
import com.dev.gis.connector.joi.protocol.Station;
import com.dev.gis.connector.joi.protocol.VehicleDescription;
import com.dev.gis.connector.sunny.Supplier;

public class AdacTooltipListener extends TooltipListener {


	public AdacTooltipListener(Shell shell, Table table) {
		super(shell, table);
	}

	@Override
	protected String getTooltipText(TableItem item, int pos) {
		Object data = item.getData();
		int xx = item.getBounds().x;
		int ww = item.getBounds().width;
		
		StringBuffer sb = new StringBuffer();
		if ( data instanceof OfferDo) {
			OfferDo offer = (OfferDo) data;
			
			if ( pos < 100)
				return createVehicleInfo(offer);
			if ( pos > 300 && pos < 600)
				return createStationInfo(offer);
		}
		return sb.toString();
	}
	private String createStationInfo(OfferDo offer) {
		
		StringBuffer sb = new StringBuffer();
		Station station = offer.getPickupStation();
		com.dev.gis.connector.joi.protocol.Supplier supplier = offer.getSupplier();
		String supplierTxt = "";
		if (supplier != null ) {
			supplierTxt = supplier.getId() + " " + supplier.getName() + "  sgr:" + station.getSupplierGroupId();
		}
		else
			supplierTxt = station.getSupplierId() +  "  sgr:" + station.getSupplierGroupId();
		
		Address address = station.getAddress();
		
		sb.append("Station "+station.getStationName()+ " " + station.getId());
		sb.append("\n");
		sb.append("Supplier "+supplierTxt);
		sb.append("\n");
		sb.append("link : "+station.getLink());
		sb.append("\n");
		if ( address != null) {
			sb.append("address : "+address.getCity() + " " + address.getStreet());
		}
		
		return sb.toString();
	}

	private String createVehicleInfo(OfferDo offer) {
		StringBuffer sb = new StringBuffer();
		
		VehicleDescription vd = offer.getModel().getVehicle();
		sb.append("Manufacturer: "+vd.getManufacturer());
		sb.append("\n");
		sb.append("BodyStyle : "+vd.getBodyStyleText().getText());
		sb.append(" Acriss: "+vd.getVehicleGroup().getName());
		sb.append("\n");
		sb.append("Category: "+vd.getCategoryId());
		sb.append("\n");
		sb.append("Doors: "+vd.getNrDoors()+ " Seats :"+vd.getNrSeats()+ " Adults :"+vd.getNrAdults() + " Children :"+vd.getNrChildren());
		sb.append("\n");
		sb.append("Large image: "+vd.getVehicleLargeImage());
		sb.append("\n");
		sb.append("Small image: "+vd.getVehicleSmallImage());
		sb.append("\n");
		sb.append("Cargo height : "+vd.getCargoHeight() + " width : "+vd.getCargoWidth()+ " lenght : "+vd.getCargoLength()+ " volume : "+vd.getCargoVolume());
		sb.append("Weight Total : "+vd.getWeightTotal() + "Weight Use : "+vd.getWeightUse());
		sb.append("\n");
		sb.append("Luggage large : "+vd.getNrLargeLuggage() + " small : "+vd.getNrSmallLuggage());
		sb.append("\n");
		sb.append("Driver license : "+vd.getDriverLicense() + " min age : "+vd.getDriverMinAge());
			
		
		sb.append("\n");
		return sb.toString();
	}

	@Override
	protected TableItem getTableItem(int x, int y) {
		return super.getTableItem(x, y);
	}

}
