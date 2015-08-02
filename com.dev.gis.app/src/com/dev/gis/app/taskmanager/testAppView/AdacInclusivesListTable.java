package com.dev.gis.app.taskmanager.testAppView;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbenchPartSite;

import com.dev.gis.connector.api.ModelProvider;
import com.dev.gis.connector.joi.protocol.Inclusive;

public class AdacInclusivesListTable {

	private TableViewer viewer;

	private final IWorkbenchPartSite site;

	private ISelectionChangedListener selectionChangedListener;

	private final Composite parent;

	public AdacInclusivesListTable(IWorkbenchPartSite site, final Composite parent,
			ISelectionChangedListener selectionChangedListener) {
		this.site = site;
		this.parent = parent;
		this.selectionChangedListener = selectionChangedListener;
		createViewer();
	}

	private void createViewer() {

		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(new ArrayContentProvider());
		site.setSelectionProvider(viewer);

		// define layout for the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);

		if (selectionChangedListener != null)
			viewer.addSelectionChangedListener(this.selectionChangedListener);

	}

	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "id", "Name", "Code", "Class", "Priority" };
		int[] bounds = {50, 200, 100, 200, 50 };

		// first column is for the first name
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Inclusive o = (Inclusive) element;
				return ""+o.getId();
			}
		});

		// second column is supplierId
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Inclusive o = (Inclusive) element;
				String name = o.getDescription();
				return name;
			}
		});

		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Inclusive o = (Inclusive) element;
				return o.getCode();
			}
		});

		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Inclusive o = (Inclusive) element;
				return o.getItemClassName();
			}
		});

		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Inclusive o = (Inclusive) element;
				return ""+o.getDisplayPriority();
			}
		});
		
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound,
			final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	public TableViewer getViewer() {
		return viewer;
	}

	public void update(List<Inclusive> inclusives) {
		viewer.setInput(inclusives);
		viewer.refresh();
		
	}

}
