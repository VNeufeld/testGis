package com.dev.gis.app.taskmanager.SunnyCarsView;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.sunny.Extra;

public class SunnyExtraListTable extends AbstractListTable {

	public SunnyExtraListTable(IWorkbenchPartSite site, final Composite parent,
			ISelectionChangedListener selectionChangedListener) {
		super(site, parent, getDoubleClickListener(), selectionChangedListener);
		

	}
	
	public List<Extra> getSelectedExtras() {
		List<Extra> extras = SunnyModelProvider.INSTANCE.getExtras();
		List<Extra> selectedExtras = new ArrayList<Extra>();
		for (Extra extra : extras ) {
			if ( extra.isSelected())
				selectedExtras.add(extra);
		}
		
		return selectedExtras;
	}
	

	public List<Extra> getSelectedExtrasOld() {
		List<Extra> extras = getSelectedOjects(Extra.class);
		return extras;
	}

	@Override
	public void createColumns(Composite parent, TableViewer viewer) {
		String[] titles = { "Id", "Code", "Name", "Preis", "TotalPrice", "PayType",  "TotalPriceInSellCurrency", "Mandatory", "Status" };
		int[] bounds = { 50, 70, 300, 100, 100, 150, 150, 100, 100 };
		
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
				String name = o.getName();
				String description = o.getDescription();
				if ( StringUtils.isEmpty(description))
					return name;
				else
					return description;
			}
		});

		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Extra o = (Extra) element;
				if ( o.getPrice() != null)
					return o.getPrice().toString();
				return "";
			}
		});

		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Extra o = (Extra) element;
				if ( o.getTotalPrice() != null)
					return o.getTotalPrice().toString();
				return "";
			}
		});

		col = createTableViewerColumn(titles[5], bounds[5], 5);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Extra o = (Extra) element;
				if ( o.getPayType() != null)
					return o.getPayType().name();
				return "n/a";
			}
		});
		
		col = createTableViewerColumn(titles[6], bounds[6], 6);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Extra o = (Extra) element;
				if ( o.getTotalPriceInSellCurrency() != null)
					return o.getTotalPriceInSellCurrency().toString();
				return "";
			}
		});
		

		col = createTableViewerColumn(titles[7], bounds[7], 7);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Extra o = (Extra) element;
				if ( o.getMandatory())
					return "true";
				return "false";
			}
		});

		col = createTableViewerColumn(titles[8], bounds[8], 8);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Extra o = (Extra) element;
				return o.getStatus();
			}
		});
		
	}

	@Override
	public void update() {
		getViewer().setInput(SunnyModelProvider.INSTANCE.getExtras());
		getViewer().refresh();
	}

	private static IDoubleClickListener getDoubleClickListener() {
		return new SelectExtraDoubleClickListener();
	}
	
	private static class SelectExtraDoubleClickListener implements IDoubleClickListener {
		
		private static Logger logger = Logger.getLogger(SelectExtraDoubleClickListener.class);

		public SelectExtraDoubleClickListener() {
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
