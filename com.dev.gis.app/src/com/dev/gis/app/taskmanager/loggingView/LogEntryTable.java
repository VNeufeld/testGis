package com.dev.gis.app.taskmanager.loggingView;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
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
import com.dev.gis.app.taskmanager.TaskPropertyDialog;
import com.dev.gis.app.taskmanager.loggingView.service.FileNameEntry;
import com.dev.gis.app.taskmanager.loggingView.service.LogEntry;

public class LogEntryTable {
	private TableViewer viewer;
	private final Composite parent;
	private final IWorkbenchPartSite site;
	
	private final static Logger logger = Logger.getLogger(LogEntryTable.class);
	

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
		
		String[] titles = { "Date", "Request", "LogEntry" };
		int[] bounds = { 140, 200, 1800 };
		
		TableViewerColumn columnKey2=new TableViewerColumn(viewer,SWT.NONE,0);
		final TableColumn column2 = columnKey2.getColumn();
		column2.setText("file");
		column2.setWidth(100);
		column2.setResizable(true);
		columnKey2.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				LogEntry o = (LogEntry) element;
				return ""+o.getFileIndex();
			}
		});


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
				return o.getDemandedObject();
			}
		});

		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				LogEntry o = (LogEntry) element;
				String s = o.getEntry().get(0);
				if ( s.length() > 300)
					s = s.substring(0, 300);
				return s;
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
			
			logger.info(" open dialog :" + o.getEntry().size());
	        
			LogEntryDialog mpd = new LogEntryDialog(viewer.getTable().getShell(), o);
			if (mpd.open() == Dialog.OK) {
				
			}
	        

		}


	}

	public void update() {
		LogEntryModel model = LogEntryModel.getInstance();
		viewer.setInput(model.getLoggingEntries());
	}

	public void update(List<LogEntry> entries) {
		viewer.setInput(entries);
	}

	
}
