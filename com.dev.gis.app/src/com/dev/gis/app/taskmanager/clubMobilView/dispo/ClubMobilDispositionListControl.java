package com.dev.gis.app.taskmanager.clubMobilView.dispo;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.view.elements.BasicControl;
import com.dev.gis.app.view.elements.OutputTextControls;

public class ClubMobilDispositionListControl extends BasicControl {
	
	private final Composite parent;
	
	private OutputTextControls result = null;
	
	private ClubMobilDispostionListTable dispoListTable;
	
	public ClubMobilDispositionListControl(Composite groupStamp) {
		
		parent = groupStamp;
		
		final Group group = createGroupSpannAllRows(groupStamp, "Disposition",4,4);
		
		Composite cc = createComposite(group, 2, -1, true);
		result = new OutputTextControls(cc, "DispoInfo", 500, 1 );

		Composite cc2 = createComposite(group, 3, -1, true);
		
		ClubMobilDispoStationSearch.createDispoStationSearch(cc2); 
		
		createDispositionListTable(groupStamp);
		
		new ClubMobilChangeDispositionControl(groupStamp, dispoListTable);	


	}
	
	protected Composite getParent() {
		return parent;
	}

	protected Shell getShell() {
		return parent.getShell();
	}

	
	
	private void createDispositionListTable(Composite composite) {
		final Group groupOffers = new Group(composite, SWT.TITLE);
		groupOffers.setText("Car List:");
		
		groupOffers.setLayout(new GridLayout(1, false));
//		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
//				.grab(true, true).applyTo(groupOffers);
		
		GridDataFactory.fillDefaults().span(1, 4)
		.align(SWT.FILL, SWT.FILL).grab(true, true)
		.applyTo(groupOffers);
		
		
		this.dispoListTable = new ClubMobilDispostionListTable(null,
				groupOffers, getSelectReservationDoubleClickListener(), getSelectChangedReservationClickListener());
		
	}
	
	
	protected ISelectionChangedListener getSelectChangedReservationClickListener() {
		return null;
	}

	protected IDoubleClickListener getSelectReservationDoubleClickListener() {
		return null;
	}

	public void update() {
		dispoListTable.update();
	}


}
