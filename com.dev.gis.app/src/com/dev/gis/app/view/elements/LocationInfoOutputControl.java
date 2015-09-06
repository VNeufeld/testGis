package com.dev.gis.app.view.elements;

import org.eclipse.swt.widgets.Composite;

public abstract class LocationInfoOutputControl extends BasicControl implements IVehicleInfoControl {

	protected OutputTextControls pickUpCity;

	protected OutputTextControls dropOffCity;

	protected OutputTextControls dropOffStation;

	
	private final Composite parent;
	
	public LocationInfoOutputControl(Composite parent) {
		super();
		this.parent = parent;

		pickUpCity = new OutputTextControls(this.parent, "PickupCity", 500, 1 );

		dropOffCity = new OutputTextControls(this.parent, "DropOff City", 500, 1 );
		
		dropOffStation = new OutputTextControls(this.parent, "DropOffStation", 500, 1 );
		
	}


}
