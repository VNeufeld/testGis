package com.dev.gis.app.taskmanager.clubMobilView.dispo;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPartSite;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.bpcs.mdcars.model.DispositionInfo;
import com.bpcs.mdcars.model.ReservationInfo;
import com.dev.gis.app.view.elements.AbstractListTable;
import com.dev.gis.connector.api.ClubMobilModelProvider;

public class ClubMobilDispostionListTable extends AbstractListTable {
	
	final DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

	public ClubMobilDispostionListTable(IWorkbenchPartSite site,final Composite parent, 
			IDoubleClickListener selectOfferListener, 
			ISelectionChangedListener selectionChangedListener  ) {
		super(site,parent,selectOfferListener,selectionChangedListener);
	}

	@Override
	public void createColumns(Composite parent, TableViewer viewer) {
	    String[] titles = { "DispoNo", "CarId", "StationId", "ScheduleFrom", "ScheduleTo",  "" };
	    int[] bounds = { 100, 250, 250, 300, 300,  10};

	    // first column is for the first name
	    TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	DispositionInfo o = (DispositionInfo) element;
	        return ""+o.getId();
	      }
	    });
	    
	    col = createTableViewerColumn(titles[1], bounds[1], 1);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	DispositionInfo o = (DispositionInfo) element;
	    	if ( o.getCarId() != null)
	    		return o.getCarId().toString();
	    	else
	    		return "-1";
	      }
	    });
	    

	    // second column is supplierId
	    col = createTableViewerColumn(titles[2], bounds[2], 2);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	DispositionInfo o = (DispositionInfo) element;
	    	if ( o.getStationId() != null)
	    		return o.getStationId().toString();
	    	else
	    		return "0";
	      }
	    });

	    col = createTableViewerColumn(titles[3], bounds[3], 3);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	DispositionInfo o = (DispositionInfo) element;
	    	if ( o.getScheduleFrom() != null) {
		    	DateTime dt = new DateTime(o.getScheduleFrom().toDate());
	    		return dt.toString(timeFormatter);
	    	}
	    	else
	    		return "";
	      }
	    });

	    col = createTableViewerColumn(titles[4], bounds[4], 4);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	DispositionInfo o = (DispositionInfo) element;
	    	if ( o.getScheduleTo() != null) {
		    	DateTime dt = new DateTime(o.getScheduleTo().toDate());
	    		return dt.toString(timeFormatter);
	    	}
	    	else
	    		return "";
	      }
	    });
	    
	    
	    col = createTableViewerColumn(titles[5], bounds[5], 5);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
    		return "";
	      }

	    });

	  }



	@Override
	public void update() {
		if ( ClubMobilModelProvider.INSTANCE.dispositionListResponse != null)
			getViewer().setInput(ClubMobilModelProvider.INSTANCE.dispositionListResponse.getDispoList());
		else
			getViewer().setInput(null);
		getViewer().refresh();
		
	}

}
