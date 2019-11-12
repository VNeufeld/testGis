package com.dev.gis.app.view.elements;

import org.eclipse.swt.widgets.Composite;

public abstract class VehicleInfoOutputControl extends BasicControl implements IVehicleInfoControl {

	protected OutputTextControls vehicleInfo;
	
	private final Composite parent;
	
	public VehicleInfoOutputControl(Composite parent) {
		super();
		this.parent = parent;

		vehicleInfo = new OutputTextControls(this.parent, "Vehicle Info", -1, 1 );
		
	}


}
