package com.dev.gis.app.taskmanager.testAppView;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPartSite;

import com.dev.gis.app.view.elements.AbstractListTable;
import com.dev.gis.app.view.listener.adac.AdacTooltipListener;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.OfferDo;
import com.dev.gis.connector.joi.protocol.Offer;
import com.dev.gis.connector.joi.protocol.VehicleDescription;

public class AdacOfferListTable extends AbstractListTable {
	
	
	public AdacOfferListTable(IWorkbenchPartSite site,final Composite parent, 
			IDoubleClickListener selectOfferListener, 
			ISelectionChangedListener selectionChangedListener  ) {
		super(site,parent,selectOfferListener,selectionChangedListener);
	}

	@Override
	public void createColumns(Composite parent, TableViewer viewer) {
	    String[] titles = { "Name", "Group", "Supplier", "Station", "Service Catalog",  "Price", "Incl. km.", "Prepaid", "OneWay Fee", };
	    int[] bounds = { 200, 150, 100, 100, 100,  200, 100, 100, 200 };

	    // first column is for the first name
	    TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	OfferDo o = (OfferDo) element;
	    	String name = o.getName();
	    	if ( StringUtils.isEmpty(name)) {
	    		VehicleDescription vd = o.getModel().getVehicle();
	    		name = vd.getBodyStyleText().getText();
	    		
	    	}
	        return name;
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
			String value = String.valueOf(o.getPickUpStationId());
			if ( o.getDropOffStationId() != o.getPickUpStationId()) {
				value = value + " / " + String.valueOf(o.getDropOffStationId());
			}
			return value;
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
			return o.getPrice().toString();
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
			String oneWay = "";
			Offer offer = o.getOffer();
					
			if (offer.getOneWayFee() != null) {
				oneWay = offer.getOneWayFee().getAmount();
				oneWay = oneWay + " " + offer.getOneWayFee().getCurrency();
				if ( offer.isOneWayInclusive() ) 
					oneWay = oneWay + " ( Included ) ";
				else
					oneWay = oneWay + " ( Not Included ) ";
			}
	    	return  oneWay;
	    	
	      }
	    });
	    

	  }



	@Override
	public void update() {
		getViewer().setInput(AdacModelProvider.INSTANCE.getOfferDos());
		getViewer().refresh();
		
	}

	@Override
	protected void addTooltipListener() {
		AdacTooltipListener listener = new AdacTooltipListener(getParent().getShell(), getViewer().getTable());
		
		getViewer().getControl().addListener(SWT.MouseHover, listener.getTableListener());
	}

}
