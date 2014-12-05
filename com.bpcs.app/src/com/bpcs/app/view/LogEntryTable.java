package com.bpcs.app.view;

import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.Dialog;
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
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbenchPartSite;

import com.dev.http.server.LogEntry;


public class LogEntryTable {
	private TableViewer viewer;
	private final Composite parent;
	private final IWorkbenchPartSite site;

	public LogEntryTable(Composite group, IWorkbenchPartSite site) {
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
		gridData.horizontalSpan = 1;
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
		
		String[] titles = { "Start", "End", "Duration", "Request", "Response" };
		int[] bounds = { 140, 140,100, 600, 600 };

		// first column is for the first name
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				LogEntry o = (LogEntry) element;
				return stf.format(o.getStart());
			}
		});

		// second column is supplierId
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				LogEntry o = (LogEntry) element;
				if ( o.getEnd() != null )
					return stf.format(o.getEnd());
				return "";
			}
		});

		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				LogEntry o = (LogEntry) element;
				return Long.toString(o.getDuration());
			}
		});

		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				LogEntry o = (LogEntry) element;
				String request  = StringUtils.substringBetween(o.getRequest(), "<Mail>",  "</Mail>"); 
				return request;
			}
		});

		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				LogEntry o = (LogEntry) element;
				String response  = StringUtils.substringBetween(o.getResponse(), "<File>",  "</File>"); 
				return response;
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
			
			String text = createText ( o );
	        
			LogEntryDialog mpd = new LogEntryDialog(viewer.getTable().getShell(), text);
			if (mpd.open() == Dialog.OK) {
				
			}
	        

		}

		private String createText(LogEntry o) {
			StringBuilder sb = new StringBuilder();
			sb.append("Request :");
			sb.append('\n');
			sb.append(o.getRequest());
			sb.append('\n');
			sb.append("Response :");
			sb.append('\n');
			sb.append(o.getResponse());
			return sb.toString();
		}

	}

	public void update() {
		LogEntryModel model = LogEntryModel.getInstance();
		viewer.setInput(model.getEntries());
	}

}
