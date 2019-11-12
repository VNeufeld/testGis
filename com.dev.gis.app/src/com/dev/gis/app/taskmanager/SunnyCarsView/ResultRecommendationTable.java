package com.dev.gis.app.taskmanager.SunnyCarsView;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IWorkbenchPartSite;

import com.dev.gis.connector.api.SunnyModelProvider;

public class ResultRecommendationTable extends SunnyOfferListTable {
	
	
	public ResultRecommendationTable(IWorkbenchPartSite site,final Composite parent) {
		super(site,parent, null, null);
	}
	
	protected TableViewer createViewerAndLaylout(Composite parent,int columns) {
		
		TableViewer viewer = new TableViewer(parent, SWT.SINGLE | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(new ArrayContentProvider());

		// define layout for the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = columns;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.minimumHeight = 100;
		
		viewer.getControl().setLayoutData(gridData);
		
		return viewer;
		
	}


	@Override
	public void update() {
		getViewer().setInput(SunnyModelProvider.INSTANCE.getRecommendations());
		getViewer().refresh();
	}
	
}
