package com.dev.gis.app.taskmanager.executionView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import com.dev.gis.app.task.model.Task;
import com.dev.gis.app.task.model.TaskExecutonResultModel;
import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.task.execution.api.ITaskResult;
import com.dev.gis.task.execution.api.TaskResultItem;

public class TaskExecutionView extends TaskViewAbstract {
	public static final String ID = "com.dev.gis.app.view.taskExecution";
	private Table table;
	private TableViewer tableViewer;
	private final WritableValue selectedFolder = new WritableValue();
	private LocalResourceManager resourceManager = new LocalResourceManager(
			JFaceResources.getResources());
	private Color gray;
	private Font italics;
	final SimpleDateFormat timeFormater = new SimpleDateFormat("HH:mm:ss");
	// final SimpleDateFormat timeFormater = new SimpleDateFormat("HH:mm");
	final DecimalFormat distFormatter = new DecimalFormat("#0.000");

	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout());

		createTableGroup(container);
		
		createColumns();

		initializeToolBar();
		initializeMenu();
		initializeContextMenu();

		refresh(null);

	}

	public void refresh() {
		if (tableViewer != null) {
			if (!tableViewer.getTable().isDisposed())

				System.out.println("refresh tas execution view "+this.getTitle()+ " thread : "+Thread.currentThread().getName());
				
				tableViewer.setInput(TaskExecutonResultModel.getInstance()
						.getTaskList());
		}
	}

	private void createTableGroup(Composite composite) {

		tableViewer = new TableViewer(composite, SWT.FULL_SELECTION | SWT.MULTI
				| SWT.BORDER) {
			// The following is a workaround for bug 269264.
			public void remove(Object[] elements) {
				int oldIndex = -1;
				Table table = tableViewer.getTable();
				int[] selectionIndices = table.getSelectionIndices();
				if (selectionIndices.length > 0) {
					oldIndex = selectionIndices[0];
				}
				super.remove(elements);
				if (oldIndex != -1) {
					if (table.getItemCount() > oldIndex) {
						table.setSelection(oldIndex);
					} else if (table.getItemCount() > 0) {
						table.setSelection(table.getItemCount() - 1);
					}
				}
				setSelection(getSelection(), false);
			}
		};
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		

		tableViewer
				.setContentProvider(new ListObjectsStructureObjectProvider());


	}

	private void createColumns() {
		TableViewerColumn columnDate = new TableViewerColumn(tableViewer,
				SWT.NONE);
		columnDate.getColumn().setWidth(85);
		columnDate.getColumn().setText("Date");
		columnDate.getColumn().setResizable(true);
		columnDate.getColumn().setMoveable(true);

		columnDate.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				TaskResultItem item = (TaskResultItem) element;
				return timeFormater.format(item.getDate());
			}
		});

		TableViewerColumn columnFrom = new TableViewerColumn(tableViewer,
				SWT.NONE);
		columnFrom.getColumn().setWidth(95);
		columnFrom.getColumn().setText("From");
		columnFrom.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				TaskResultItem item = (TaskResultItem) element;
				return "me";
			}
		});

		TableViewerColumn columnSubject = new TableViewerColumn(tableViewer,
				SWT.NONE);
		columnSubject.getColumn().setWidth(300);
		columnSubject.getColumn().setText("Subject");
		columnSubject.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				TaskResultItem item = (TaskResultItem) element;
				return item.getText();
			}
		});

	}

	private void initializeToolBar() {
		getViewSite().getActionBars().getToolBarManager();
	}

	private void initializeMenu() {
		getViewSite().getActionBars().getMenuManager();
	}

	// it is important to implement setFocus()!
	public void setFocus() {
		tableViewer.getTable().setFocus();
	}

	private void initializeContextMenu() {
		MenuManager manager = new MenuManager();
		manager.setRemoveAllWhenShown(true);
		manager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				manager.add(new GroupMarker(
						IWorkbenchActionConstants.MB_ADDITIONS));
			}
		});
		Menu contextMenu = manager.createContextMenu(table);
		table.setMenu(contextMenu);
		getSite().registerContextMenu(manager, tableViewer);
	}

	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			Object element = structuredSelection.getFirstElement();
			if (element instanceof Task) {
				Task task = (Task) element;
				selectedFolder.setValue(task);
				// Message[] messages = task.getName();
				// if (messages.length > 0) {
				// tableViewer.setSelection(new
				// StructuredSelection(messages[0]));
				// }
			}
		}
	}

	public void dispose() {
		super.dispose();
		resourceManager.dispose();
	}

	private static class ListObjectsStructureObjectProvider implements
			IStructuredContentProvider {

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// TODO Auto-generated method stub

		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub

		}

		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof List<?>) {
				List<?> input = (List<?>) inputElement;
				return input.toArray();
			}
			return null;
		}
	}

	public void refresh(ITaskResult result) {
		if ( result != null) {
			if (tableViewer != null) {
				if (!tableViewer.getTable().isDisposed())
	
					System.out.println("refresh tas execution view "+this.getTitle()+ " thread : "+Thread.currentThread().getName());
					
					tableViewer.setInput(result.getTaskList());
			}
		}
	}

}
