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
	    String[] titles = { "ResNo", "Pos", "ResId", "Name", "Car",  "Price" };
	    int[] bounds = { 100, 50, 100, 300, 300,  200};

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
    		return o.getCustomer().getPerson().getName();
	      }
	    });

	    col = createTableViewerColumn(titles[4], bounds[4], 4);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	ReservationInfo o = (ReservationInfo) element;
    		return o.getCarType() + "/" + o.getCarCategoryId();
	      }
	    });
	    
	    
	    col = createTableViewerColumn(titles[5], bounds[5], 5);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	ReservationInfo o = (ReservationInfo) element;
    		return o.getPrice().toString();
	      }

	    });

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
