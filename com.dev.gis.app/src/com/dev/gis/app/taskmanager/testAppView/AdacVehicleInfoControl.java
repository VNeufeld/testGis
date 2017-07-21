package com.dev.gis.app.taskmanager.testAppView;

import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.VehicleInfoOutputControl;
import com.dev.gis.connector.joi.protocol.VehicleDescription;

public class AdacVehicleInfoControl extends VehicleInfoOutputControl {

	public AdacVehicleInfoControl(Composite parent) {
		super(parent);
	}

	@Override
	public void update(Object vehicleParam) {
		if ( vehicleParam instanceof VehicleDescription) {
			VehicleDescription vehicle = (VehicleDescription) vehicleParam;
			vehicleInfo.setValue(vehicle.getManufacturer()
					+ " Category : " + vehicle.getCategoryId()
					+ " Group : " + vehicle.getVehicleGroup().getId()+ ":"+vehicle.getVehicleGroup().getName()
					+ " BodyStyle : " + vehicle.getBodyStyle()
					+ " Licence : " + vehicle.getLicenseMinTime() + " " + vehicle.getLicenseDimension()
					
					);
		}
		
	}
	
}
