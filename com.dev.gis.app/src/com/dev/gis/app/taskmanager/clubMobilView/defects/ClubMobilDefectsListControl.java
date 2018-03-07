package com.dev.gis.app.taskmanager.clubMobilView.defects;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilCarListControl;
import com.dev.gis.app.taskmanager.clubMobilView.dispo.ClubMobilDispoStationSearch;
import com.dev.gis.app.view.elements.BasicControl;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.OutputTextControls;

public class ClubMobilDefectsListControl extends BasicControl {
	
	private final Composite parent;
	
	private ClubMobilDefectsListTable defectListTable;
	
	private ClubMobilCarListControl carListControl;
	
	public ClubMobilDefectsListControl(Composite groupStamp) {
		
		parent = groupStamp;

		carListControl = new ClubMobilCarListControl(groupStamp);
		
		createDispositionListTable(groupStamp);
		
		new ClubMobilChangeDefectsControl(groupStamp, defectListTable);	


	}

	protected Composite getParent() {
		return parent;
	}

	protected Shell getShell() {
		return parent.getShell();
	}

	
	
	private void createDispositionListTable(Composite composite) {
		final Group groupOffers = new Group(composite, SWT.TITLE);
		groupOffers.setText("Defect List:");
		
		groupOffers.setLayout(new GridLayout(1, false));
		
		GridDataFactory.fillDefaults().span(1, 4)
		.align(SWT.FILL, SWT.FILL).grab(true, true)
		.applyTo(groupOffers);
		
		
		this.defectListTable = new ClubMobilDefectsListTable(null,
				groupOffers, getSelectReservationDoubleClickListener(), getSelectChangedReservationClickListener());
		
	}
	
	
	protected ISelectionChangedListener getSelectChangedReservationClickListener() {
		return null;
	}

	protected IDoubleClickListener getSelectReservationDoubleClickListener() {
		return new ClubMobilSelectDefectDoubleClickListener(getShell());
	}

	public void update() {
		defectListTable.update();
	}


}
