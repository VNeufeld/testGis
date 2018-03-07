package com.dev.gis.app.taskmanager.clubMobilView;

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
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.OfferDo;
import com.dev.gis.connector.joi.protocol.Offer;
import com.dev.gis.connector.joi.protocol.VehicleDescription;

public class ClubMobilOfferListTable extends AbstractListTable {
	
	
	public ClubMobilOfferListTable(IWorkbenchPartSite site,final Composite parent, 
			IDoubleClickListener selectOfferListener, 
			ISelectionChangedListener selectionChangedListener  ) {
		super(site,parent,selectOfferListener,selectionChangedListener);
	}

	@Override
	public void createColumns(Composite parent, TableViewer viewer) {
	    String[] titles = { "Name", "Car", "Supplier", "Station", "Service Catalog",  "Price", "Incl. km.", "Prepaid", "OneWay Fee", "BusinessSegment", "defectDesc."};
	    int[] bounds = { 300, 250, 100, 100, 100,  200, 100, 100, 200 , 200, 200};

	    // first column is for the first name
	    TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	OfferDo o = (OfferDo) element;
	    	String name = o.getName();
	    	
    		VehicleDescription vd = o.getModel().getVehicle();
	    	if ( StringUtils.isEmpty(name)) {
	    		name = vd.getBodyStyleText().getText();
	    	}
	    	String result = "";
    		String carId = vd.getCarId();
    		String licensePlate = vd.getLicensePlate();
    		
    		if (StringUtils.isNotEmpty(licensePlate) )
    			result = licensePlate;
    		if (StringUtils.isNotEmpty(carId) )
    			result = result + " id:"+carId;
    		
			result = result + " "+name;
	        return result;
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
	    
	    col = createTableViewerColumn(titles[9], bounds[9], 9);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	OfferDo o = (OfferDo) element;
			Offer offer = o.getOffer();
			
			String businessSegment = offer.getBusinessSegmentCode();
			if ( businessSegment == null)
				businessSegment = " n/a";
					
	    	return  businessSegment;
	    	
	      }
	    });
	    
	    col = createTableViewerColumn(titles[10], bounds[10], 10);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	OfferDo o = (OfferDo) element;
			VehicleDescription vd = o.getModel().getVehicle();
			String result = vd.getDefectDescription();
			if ( result == null)
				result = "";
	    	return  result;
	    	
	      }
	    });


	  }



	@Override
	public void update() {
		getViewer().setInput(ClubMobilModelProvider.INSTANCE.getOfferDos());
		getViewer().refresh();
		
	}

	@Override
	protected void addTooltipListener() {
		AdacTooltipListener listener = new AdacTooltipListener(getParent().getShell(), getViewer().getTable());
		
		getViewer().getControl().addListener(SWT.MouseHover, listener.getTableListener());
	}

}
