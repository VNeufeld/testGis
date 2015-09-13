package com.dev.gis.app.taskmanager.testAppView;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPartSite;

import com.dev.gis.app.view.elements.AbstractListTable;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.OfferDo;

public class CrossOfferListTable extends AbstractListTable {
	
	
	
	public CrossOfferListTable(IWorkbenchPartSite site, final Composite parent,
			ISelectionChangedListener selectionChangedListener) {
		super(site, parent, null, selectionChangedListener);

	}

	


	@Override
	public void createColumns(Composite parent, TableViewer viewer) {
		createColumnsP(parent,viewer);
	}


	@Override
	public void update() {
		getViewer().setInput(AdacModelProvider.INSTANCE.getCrossOffers());
		getViewer().refresh();
	}
	
	private void createColumnsP(final Composite parent, final TableViewer viewer) {
	    String[] titles = { "Name", "Group", "Supplier", "Station", "Service Catalog",  "Price", "Incl. km.", "Prepaid", "CrossOfferLink" };
	    int[] bounds = { 200, 150, 100, 100, 100,  200, 100, 100, 400 };

	    // first column is for the first name
	    TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	OfferDo o = (OfferDo) element;
	        return o.getName();
	      }
	    });
	    
	    col = createTableViewerColumn(titles[1], bounds[1], 1);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	OfferDo o = (OfferDo) element;
	        return o.getGroup();
	      }
	    });
	    

	    // second column is supplierId
	    col = createTableViewerColumn(titles[2], bounds[2], 2);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	OfferDo o = (OfferDo) element;
	        return String.valueOf(o.getSupplierId());
	      }
	    });

	    col = createTableViewerColumn(titles[3], bounds[3], 3);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	OfferDo o = (OfferDo) element;
			return String.valueOf(o.getPickUpStationId());
	      }
	    });

	    col = createTableViewerColumn(titles[4], bounds[4], 4);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	OfferDo o = (OfferDo) element;
			return o.getServiceCatalogCode() + " : "+ String.valueOf(o.getServiceCatalogId());
	      }
	    });
	    
	    
	    col = createTableViewerColumn(titles[5], bounds[5], 5);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	OfferDo o = (OfferDo) element;
			return o.getPrice().getAmount();
	      }

	    });
	    
	    col = createTableViewerColumn(titles[6], bounds[6], 6);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	OfferDo o = (OfferDo) element;
	    	String incl = o.getInclusiveKm();
			return incl;
	      }
	    });

	    col = createTableViewerColumn(titles[7], bounds[7], 7);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	OfferDo o = (OfferDo) element;
	    	return  o.getPrepaid();
	    	
	      }
	    });
	    
	    col = createTableViewerColumn(titles[8], bounds[8], 8);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	OfferDo o = (OfferDo) element;
	    	String link = "-";
	    	if ( o.getModel() != null)
	    		link = o.getModel().getCrossOfferLink();
	    	return  link;
	    	
	      }
	    });

	  }

}
