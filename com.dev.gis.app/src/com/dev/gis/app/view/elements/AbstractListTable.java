package com.dev.gis.app.view.elements;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchPartSite;


public abstract class AbstractListTable implements IListTable {

	private static Logger logger = Logger.getLogger(AbstractListTable.class);

	private TableViewer viewer;

	private final IWorkbenchPartSite site;

	private IDoubleClickListener doubleClickListener;

	private ISelectionChangedListener selectionChangedListener;

	private final Composite parent;

	public AbstractListTable(IWorkbenchPartSite site, final Composite parent,
			IDoubleClickListener doubleClickListener,
			ISelectionChangedListener selectionChangedListener) {
		
		this.site = site;
		this.parent = parent;
		this.doubleClickListener = doubleClickListener;
		this.selectionChangedListener = selectionChangedListener;
		
		createViewer();
	}

	private void createViewer() {
		
		GridLayout gd = (GridLayout)parent.getLayout();
		int col = gd.numColumns;

		this.viewer = createViewerAndLaylout(parent,col);
		
		createColumns(parent, this.viewer);

		if (doubleClickListener != null )
			viewer.addDoubleClickListener(doubleClickListener);

		if ( selectionChangedListener != null)
			viewer.addSelectionChangedListener(this.selectionChangedListener);

		hookContextMenu();
		
		addTooltipListener();

	}
	
	protected TableViewer createViewerAndLaylout(Composite parent, int columns) {
		TableViewer viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		
		
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		

		viewer.setContentProvider(new ArrayContentProvider());
		// get the content for the viewer, setInput will call getElements in the
		// contentProvider
		// viewer.setInput(ModelProvider.INSTANCE.getOffers());
		// make the selection available to other views
		if ( site != null)
			site.setSelectionProvider(viewer);
		// set the sorter for the table

		// define layout for the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = columns;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);
		
		return viewer;
		
		
	}

	protected void addTooltipListener() {
	}

	protected TableViewerColumn createTableViewerColumn(String title,
			int bound, final int colNumber) {
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
		if ( site != null)
			site.registerContextMenu(menuMgr, viewer);

	}

	public <T> List<T> getSelectedOjects(Class<T> obbk) {
		logger.info(" getSelectedExtras "+obbk.getName());

		List<T> selectedOjects = new ArrayList<T>();
		TableItem[] selection = getViewer().getTable().getSelection();
		if (selection != null && selection.length > 0) {
			for (TableItem item : selection) {
				@SuppressWarnings("unchecked")
				T e = (T) item.getData();
				selectedOjects.add(e);
				logger.info(" selected object " + e.toString());

			}
		}

		return selectedOjects;
	}

	protected Composite getParent() {
		return parent;
	}
	
}
