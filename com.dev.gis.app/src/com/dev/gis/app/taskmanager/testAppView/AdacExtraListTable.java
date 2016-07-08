package com.dev.gis.app.taskmanager.testAppView;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPartSite;

import com.dev.gis.app.view.elements.AbstractListTable;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.joi.protocol.Extra;
import com.dev.gis.connector.joi.protocol.ExtraResponse;

public class AdacExtraListTable extends AbstractListTable {
	private static Logger logger = Logger.getLogger(AdacExtraListTable.class);

	public AdacExtraListTable(IWorkbenchPartSite site, final Composite parent,
			ISelectionChangedListener selectionChangedListener) {
		super(site, parent, getDoubleClickListener(), selectionChangedListener);

	}

	public List<Extra> getSelectedExtras() {
		List<Extra> extras = AdacModelProvider.INSTANCE.getExtras();
		List<Extra> selectedExtras = new ArrayList<Extra>();
		for (Extra extra : extras ) {
			if ( extra.isSelected())
				selectedExtras.add(extra);
		}
		
		return selectedExtras;
	}

	public void refresh(ExtraResponse response) {
		AdacModelProvider.INSTANCE.updateExtras(response);
		getViewer().setInput(AdacModelProvider.INSTANCE.getExtras());
		getViewer().refresh();
		
	}

	public void refresh() {
		getViewer().setInput(AdacModelProvider.INSTANCE.getExtras());
		getViewer().refresh();
	}
	
	@Override
	public void createColumns(Composite parent, TableViewer viewer) {
		String[] titles = { "Id", "Code", "Name", "Preis", "TotalPrice", "PayType","Mandatory", "Bookable" };
		int[] bounds = { 50, 70, 300, 100, 100, 150, 100, 100 };

		TableViewerColumn colx = createTableViewerColumn("Sel", 30, 0);
		colx.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Extra o = (Extra) element;
				
				return o.isSelected() ? "1" : "0";
			}
		});
		
		// first column is for the first name
		
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Extra o = (Extra) element;
				return ""+o.getId();
			}
		});
		
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Extra o = (Extra) element;
				return ""+o.getCode();
			}
		});

		// second column is supplierId
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Extra o = (Extra) element;
				return o.getName();
			}
		});

		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Extra o = (Extra) element;
				if ( o.getPrice() != null)
					return o.getPrice().getAmount().toString();
				return "";
			}
		});

		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Extra o = (Extra) element;
				if ( o.getTotalPrice() != null)
					return o.getTotalPrice().getAmount().toString();
				return "";
			}
		});

		col = createTableViewerColumn(titles[5], bounds[5], 5);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Extra o = (Extra) element;
				return o.getPayType();
			}
		});

		col = createTableViewerColumn(titles[6], bounds[6], 6);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Extra o = (Extra) element;
				return String.valueOf(o.isMandatory());
			}
		});

		col = createTableViewerColumn(titles[7], bounds[7], 7);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Extra o = (Extra) element;
				return String.valueOf(o.isBookable());
			}
		});
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	private static IDoubleClickListener getDoubleClickListener() {
		return new AdacSelectExtraDoubleClickListener();
	}
	
	private static class AdacSelectExtraDoubleClickListener implements IDoubleClickListener {
		
		private static Logger logger = Logger.getLogger(AdacSelectExtraDoubleClickListener.class);

		public AdacSelectExtraDoubleClickListener() {
			super();
			logger.info("create AdacSelectExtraDoubleClickListener. ");

		}


		@Override
		public void doubleClick(DoubleClickEvent event) {

			TableViewer viewer = (TableViewer) event.getViewer();
			IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
			
			Object selectedNode = thisSelection.getFirstElement();

			Extra extra = (Extra) selectedNode;
			
			if (extra != null ) {
				if ( extra.isSelected())
					extra.setSelected(false);
				else
					extra.setSelected(true);
			}
			viewer.refresh();

			
		}


	}


}
