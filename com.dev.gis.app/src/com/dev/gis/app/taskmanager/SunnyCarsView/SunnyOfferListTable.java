package com.dev.gis.app.taskmanager.SunnyCarsView;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbenchPartSite;

import com.dev.gis.connector.api.SunnyOfferDo;

public class SunnyOfferListTable {
	
	private TableViewer viewer;
	
	private final IWorkbenchPartSite site;
	
	private IDoubleClickListener selectOfferListener;

	private ISelectionChangedListener selectionChangedListener;
	
	private final Composite parent;
	
	
	public SunnyOfferListTable(IWorkbenchPartSite site,final Composite parent, 
			IDoubleClickListener selectOfferListener, 
			ISelectionChangedListener selectionChangedListener  ) {
		this.site = site;
		this.parent = parent;
		this.selectOfferListener = selectOfferListener;
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
		// get the content for the viewer, setInput will call getElements in the
		// contentProvider
		// viewer.setInput(ModelProvider.INSTANCE.getOffers());
		// make the selection available to other views
		site.setSelectionProvider(viewer);
		// set the sorter for the table

		// define layout for the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);

		viewer.addDoubleClickListener(selectOfferListener);

		viewer.addSelectionChangedListener(this.selectionChangedListener);

		hookContextMenu();
	}

	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Name", "Group", "Supplier", "Station",
				"Service Catalog", "Price", "Rating", "Incl. km." };
		int[] bounds = { 250, 100, 80, 80, 50, 120, 50, 100 };

		// first column is for the first name
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0, viewer);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				return o.getCarDescription();
			}
		});

		col = createTableViewerColumn(titles[1], bounds[1], 1, viewer);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				return o.getGroup();
			}
		});

		// second column is supplierId
		col = createTableViewerColumn(titles[2], bounds[2], 2, viewer);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				return String.valueOf(o.getSupplierId());
			}
		});

		col = createTableViewerColumn(titles[3], bounds[3], 3, viewer);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				return String.valueOf(o.getPickUpStationId());
			}
		});

		col = createTableViewerColumn(titles[4], bounds[4], 4, viewer);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				return String.valueOf(o.getServiceCatalogId());
			}
		});

		col = createTableViewerColumn(titles[5], bounds[5], 5, viewer);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				return o.getPrice().getAmount();
			}

		});

		col = createTableViewerColumn(titles[6], bounds[6], 6, viewer);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				if (o.getRating() == null)
					return "null";
				return o.getRating().toString();
			}

		});

		col = createTableViewerColumn(titles[7], bounds[7], 7, viewer);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				String incl = o.getInclusiveKm();
				return incl;
			}
		});

	}

	private TableViewerColumn createTableViewerColumn(String title, int bound,
			final int colNumber, final TableViewer viewer) {
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

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager();

		menuMgr.setRemoveAllWhenShown(true);

		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		site.registerContextMenu(menuMgr, viewer);

	}

}
