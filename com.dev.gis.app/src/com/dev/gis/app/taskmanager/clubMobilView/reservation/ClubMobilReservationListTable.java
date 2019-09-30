package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPartSite;

import com.bpcs.mdcars.model.ReservationInfo;
import com.dev.gis.app.view.elements.AbstractListTable;
import com.dev.gis.connector.api.ClubMobilModelProvider;

public class ClubMobilReservationListTable extends AbstractListTable {
	
	
	public ClubMobilReservationListTable(IWorkbenchPartSite site,final Composite parent, 
			IDoubleClickListener selectOfferListener, 
			ISelectionChangedListener selectionChangedListener  ) {
		super(site,parent,selectOfferListener,selectionChangedListener);
	}
	

	@Override
	public void createColumns(Composite parent, TableViewer viewer) {
	    String[] titles = { "ResNo", "Pos", "ResId", "Name / MemberNo/ CustomerId", "reservationDate", "checkOutDate", "checkInDate", "Car",  "Price" };
	    int[] bounds = { 100, 50, 100, 300, 300, 300, 300, 200, 200};

	    // first column is for the first name
	    TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	ReservationInfo o = (ReservationInfo) element;
	        return o.getReservationNo();
	      }
	    });
	    
	    col = createTableViewerColumn(titles[1], bounds[1], 1);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	ReservationInfo o = (ReservationInfo) element;
    		return ""+o.getReservationPosNo();
	      }
	    });
	    

	    // second column is supplierId
	    col = createTableViewerColumn(titles[2], bounds[2], 2);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	ReservationInfo o = (ReservationInfo) element;
    		return ""+o.getRentalId();
	      }
	    });

	    col = createTableViewerColumn(titles[3], bounds[3], 3);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	ReservationInfo o = (ReservationInfo) element;
	    	String value = "";
	    	if ( o.getCustomer() != null) {
	    		if ( o.getCustomer().getPerson() != null)
	    			value = o.getCustomer().getPerson().getName() + " "+ o.getCustomer().getPerson().getFirstName() ;
	    		if ( o.getCustomer().getCommonCustomerInfo() != null) {
	    			value = value + " / " + o.getCustomer().getCommonCustomerInfo().getMemberNo();
	    			value = value + " / " + o.getCustomer().getCommonCustomerInfo().getCustomerNo();
	    		}
	    	}
    		return value;
	      }
	    });

	    col = createTableViewerColumn(titles[4], bounds[4], 4);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	ReservationInfo o = (ReservationInfo) element;
	    	
	    	if ( o.getReservationDate() != null)
	    		return o.getReservationDate().getDate() + " "+ o.getReservationDate().getTime();
	    	return "";
	      }
	    });

	    col = createTableViewerColumn(titles[5], bounds[5], 5);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	ReservationInfo o = (ReservationInfo) element;
	    	
	    	if ( o.getCheckOutDate() != null)
	    		return o.getCheckOutDate().getDate() + " "+ o.getCheckOutDate().getTime();
	    	return "";
	      }
	    });

	    col = createTableViewerColumn(titles[6], bounds[6], 6);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	ReservationInfo o = (ReservationInfo) element;
	    	
	    	if ( o.getCheckInDate() != null)
	    		return o.getCheckInDate().getDate() + " "+ o.getCheckInDate().getTime();
	    	return "";
	      }
	    });
	    
	    col = createTableViewerColumn(titles[7], bounds[7], 7);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	ReservationInfo o = (ReservationInfo) element;
	    	
	    	if ( o.getCarId() != null)
	    		return o.getCarId().toString();
	    	return "";
	      }
	    });
	    
	    
//	    col = createTableViewerColumn(titles[5], bounds[5], 5);
//	    col.setLabelProvider(new ColumnLabelProvider() {
//	      @Override
//	      public String getText(Object element) {
//	    	ReservationInfo o = (ReservationInfo) element;
//	    	if ( o.getPrice() != null)
//	    		return o.getPrice().toString();
//    		return "";
//	      }
//
//	    });

	  }



	@Override
	public void update() {
		if ( ClubMobilModelProvider.INSTANCE.reservationListResponse != null)
			getViewer().setInput(ClubMobilModelProvider.INSTANCE.reservationListResponse.getReservationInfoList());
		else
			getViewer().setInput(null);
		getViewer().refresh();
		
	}

}
