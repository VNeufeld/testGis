package com.dev.gis.app.taskmanager.loggingView;

import java.text.SimpleDateFormat;
import java.util.List;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPartSite;

import com.dev.gis.app.task.model.LogEntryModel;
import com.dev.gis.app.taskmanager.loggingView.service.LogEntry;

public class LogTable {
	private TableViewer viewer;
	private final Composite parent;
	private final IWorkbenchPartSite site;

	public LogTable(Composite group, IWorkbenchPartSite site) {
		this.parent = group;
		this.site = site;
		createViewer(parent);
	}

	private void createViewer(Composite parent) {
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

		viewer.addDoubleClickListener(new DoubleClickListener());

		hookContextMenu();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager();

		menuMgr.setRemoveAllWhenShown(true);

		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		site.registerContextMenu(menuMgr, viewer);
	}

	private void createColumns(final Composite parent, final TableViewer viewer) {
		final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss,SSS";
		final SimpleDateFormat stf = new SimpleDateFormat(DATE_TIME_FORMAT);
		
		String[] titles = { "Date", "BookingId", "Session", "Price", "LogEntry" };
		int[] bounds = { 140, 100, 280, 80, 800 };

		// first column is for the first name
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				LogEntry o = (LogEntry) element;
				return stf.format(o.getDate());
			}
		});

		// second column is supplierId
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				LogEntry o = (LogEntry) element;
				return o.getBookingId();
			}
		});

		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				LogEntry o = (LogEntry) element;
				return o.getSessionId();
			}
		});

		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				LogEntry o = (LogEntry) element;
				return o.getPrice();
			}

			// @Override
			// public Image getImage(Object element) {
			// if (((Person) element).isMarried()) {
			// return CHECKED;
			// } else {
			// return UNCHECKED;
			// }
			// }
		});

		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				LogEntry o = (LogEntry) element;
				return o.getEntry().get(0);
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
	
	private static class DoubleClickListener implements IDoubleClickListener {

		@Override
		public void doubleClick(DoubleClickEvent event) {

			TableViewer viewer = (TableViewer) event.getViewer();
			IStructuredSelection thisSelection = (IStructuredSelection) event
					.getSelection();
			Object selectedNode = thisSelection.getFirstElement();
			
			LogEntry o = (LogEntry) selectedNode;
			
			LogViewUpdater.updateView(o);

		}

	}

	public void update() {
		updateModel();
	}
	
	public void updateTemp() {
		LogEntryModel model = LogEntryModel.getInstance();
		viewer.setInput(model.getTempEntries());
	}

	
	private void updateModel() {
		LogEntryModel model = LogEntryModel.getInstance();
		List<LogEntry> entries = model.getEntries();
		for ( LogEntry entry : entries) {
		}
		viewer.setInput(entries);
		
	}


}
