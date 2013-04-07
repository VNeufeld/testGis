package com.dev.gis.app.taskmanager;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.databinding.viewers.ObservableListTreeContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import com.dev.gis.app.task.execution.TaskRunning;
import com.dev.gis.app.task.model.Model;
import com.dev.gis.app.task.model.TaskExecutonResultModel;
import com.dev.gis.app.task.model.TaskProjectModel;


public class TaskTreeView extends ViewPart {
	public static final String ID = "com.dev.gis.app.taskTreeView";
	private TreeViewer treeViewer;
	private int instanceNum = 1;
	
	private ModelUpdater modelUpdater = null;
	
	public static Map<String,IViewPart> executionTaskViews = new HashMap<String,IViewPart>();

	@Override
	public void createPartControl(Composite parent) {
		
		parent.setLayout(new FillLayout());
		treeViewer = new TreeViewer(parent);

		ObservableListTreeContentProvider contentProvider = new TaskContentProvider();
		treeViewer.setContentProvider(contentProvider);

		treeViewer.setLabelProvider(new TaskLabelProvider(contentProvider
				.getKnownElements()));

		treeViewer.setInput(TaskProjectModel.getInstance());
		
		
		Object defaultSelection = Model.getInstance().getDefaultSelection();
		if (defaultSelection != null) {
			treeViewer.setSelection(new StructuredSelection(defaultSelection));
		}

		getSite().setSelectionProvider(treeViewer);
		
		hookContextMenu();
		hookDoubleClickAction();
		
//		startChangeModelThread();
		//startRefreshViewsThread();
		
	}




	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager();

		menuMgr.setRemoveAllWhenShown(true);

		Menu menu = menuMgr.createContextMenu(treeViewer.getControl());
		treeViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, treeViewer);
	}

	private void hookDoubleClickAction() {

		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection sel = (IStructuredSelection) treeViewer.getSelection();
				
				System.out.println(" dblkl :"+event);
				
				// Start task
				
				TaskExecutonResultModel.getInstance().addItem("Item 1 ");
				new Thread(new TaskRunning()).start();
				
				try {
					// Show protocol, show results
					IViewPart viewPart = getSite().getWorkbenchWindow().getActivePage(). /*showView(TaskExecutionView.ID); */
					showView(TaskExecutionView.ID, Integer.toString(instanceNum), IWorkbenchPage.VIEW_ACTIVATE);
					
					executionTaskViews.put("task "+instanceNum , viewPart);
					instanceNum++;
					
					System.out.println(" add view :"+viewPart.getTitle());
					
					
					
				} catch (PartInitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
		}

		);
		
	}

	// it is important to implement setFocus()!
	public void setFocus() {
		treeViewer.getTree().setFocus();
	}

	private void startRefreshViewsThread() {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while ( modelUpdater != null &&  modelUpdater.isRunning()) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					for (IViewPart viewPart : executionTaskViews.values() ) {
						System.out.println("refresh "+viewPart.getTitle());
						((TaskExecutionView)viewPart).refresh();
						
					}
				}
			}
		}).start();
		
	}
	
	private void startChangeModelThread() {
		modelUpdater = new ModelUpdater();
		new Thread(modelUpdater).start();
	}
	
	private static class ModelUpdater implements Runnable {
		
		private boolean running = true;

		public boolean isRunning() {
			return running;
		}

		public void stop() {
			this.running = false;
		}

		@Override
		public void run() {
			while ( isRunning()) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				TaskExecutonResultModel.getInstance().addItem(Thread.currentThread().getId()+ " ");
				System.out.println("updateModel  "+Thread.currentThread().getName());
				
			}
			
		}
		
	}

}
