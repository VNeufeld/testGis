package com.dev.gis.app.taskmanager.clubMobilView.dispo;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.bpcs.mdcars.model.DispoPoolCar;
import com.bpcs.mdcars.model.DispositionInfo;
import com.dev.gis.app.taskmanager.clubMobilView.defects.ClubMobilSelectDefectDoubleClickListener;
import com.dev.gis.app.view.elements.BasicControl;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.ClubMobilModelProvider;

public class ClubMobilDispositionListControl extends BasicControl {
	
	private final Composite parent;
	
	private OutputTextControls result = null;
	
	private ClubMobilDispostionListTable availCarListTable;

	private ClubMobilDispoPoolListTable dispoPoolListTable;
	
	public ClubMobilDispositionListControl(Composite groupStamp) {
		
		parent = groupStamp;
		
		final Group group = createGroupSpannAllRows(groupStamp, "Disposition",4,4);
		
		Composite cc = createComposite(group, 2, -1, true);
		result = new OutputTextControls(cc, "DispoInfo", 500, 1 );

		Composite cc2 = createComposite(group, 3, -1, true);
		
		ClubMobilDispoStationSearch.createDispoStationSearch(cc2); 
		
		createAvailCarListTable(groupStamp);
		
		new ClubMobilChangeDispositionControl(groupStamp, availCarListTable);	
		
		createDispoPoolListTable(groupStamp);		

		new ClubMobilChangeDispoPoolControl(groupStamp, dispoPoolListTable);	

	}
	
	protected Composite getParent() {
		return parent;
	}

	protected Shell getShell() {
		return parent.getShell();
	}

	
	
	private void createAvailCarListTable(Composite composite) {
		final Group groupOffers = new Group(composite, SWT.TITLE);
		groupOffers.setText("Car List:");
		
		groupOffers.setLayout(new GridLayout(1, false));
//		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
//				.grab(true, true).applyTo(groupOffers);
		
		GridDataFactory.fillDefaults().span(1, 4)
		.align(SWT.FILL, SWT.FILL).grab(true, true)
		.applyTo(groupOffers);
		
		
		this.availCarListTable = new ClubMobilDispostionListTable(null,
				groupOffers, getSelectDispolistDoubleClickListener(), getSelectChangedReservationClickListener());
		
	}
	
	private void createDispoPoolListTable(Composite composite) {
		final Group groupOffers = new Group(composite, SWT.TITLE);
		groupOffers.setText("DispoPoolCar List:");
		
		groupOffers.setLayout(new GridLayout(1, false));
		
		GridDataFactory.fillDefaults().span(1, 4)
		.align(SWT.FILL, SWT.FILL).grab(true, true)
		.applyTo(groupOffers);
		
		
		this.dispoPoolListTable = new ClubMobilDispoPoolListTable(null,
				groupOffers, getSelectDispoPoolDoubleClickListener(), getSelectChangedReservationClickListener());
		
	}

	protected IDoubleClickListener getSelectDispolistDoubleClickListener() {
		return new ClubMobilSelectDispolistDoubleClickListener(getShell());
	}
	
	protected ISelectionChangedListener getSelectChangedReservationClickListener() {
		return null;
	}

	protected IDoubleClickListener getSelectDispoPoolDoubleClickListener() {
		return new ClubMobilSelectDispoPoolDoubleClickListener(getShell());
	}

	public void update() {
		availCarListTable.update();
	}

	private static class ClubMobilSelectDispoPoolDoubleClickListener implements IDoubleClickListener {
		
		private static Logger logger = Logger.getLogger(ClubMobilSelectDefectDoubleClickListener.class);
		
		private final Shell shell;
		public ClubMobilSelectDispoPoolDoubleClickListener(Shell shell) {
			super();
			logger.info("create ClubMobilSelectDispoPoolDoubleClickListener. ");
			this.shell = shell;

		}


		@Override
		public void doubleClick(DoubleClickEvent event) {

			TableViewer viewer = (TableViewer) event.getViewer();
			IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
			
			Object selectedNode = thisSelection.getFirstElement();

			DispoPoolCar dispoPoolCar = (DispoPoolCar) selectedNode;

			logger.info("selectedNode " + dispoPoolCar.getId());
			
			ClubMobilModelProvider.INSTANCE.selectedDispoPoolId = dispoPoolCar.getId();
			
		}
	}

	private static class ClubMobilSelectDispolistDoubleClickListener implements IDoubleClickListener {
		
		private static Logger logger = Logger.getLogger(ClubMobilSelectDefectDoubleClickListener.class);
		
		private final Shell shell;
		public ClubMobilSelectDispolistDoubleClickListener(Shell shell) {
			super();
			logger.info("create ClubMobilSelectDispoPoolDoubleClickListener. ");
			this.shell = shell;

		}


		@Override
		public void doubleClick(DoubleClickEvent event) {

			TableViewer viewer = (TableViewer) event.getViewer();
			IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
			
			Object selectedNode = thisSelection.getFirstElement();

			DispositionInfo dispoInfo = (DispositionInfo) selectedNode;

			logger.info("selectedNode " + dispoInfo.getId());
			
			try {
				AddDispoCarDialog dialog = new AddDispoCarDialog(shell, dispoInfo);
				if (dialog.open() == Dialog.OK) {
					
				}			

			}
			catch(Exception err) {
				logger.error(err.getMessage(),err);
				showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
			}			
			
			
		}
		protected void showErrors(com.dev.gis.connector.sunny.Error error) {
			
			String message = "";
			message = message + error.getErrorNumber()+ "  "+ error.getErrorText() + " " + error.getErrorType() + " ;";
			MessageDialog.openError(
					null,"Error",message);
			
		}
	}


}
