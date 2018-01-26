package com.dev.gis.app.taskmanager.clubMobilView.defects;

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

import com.bpcs.mdcars.model.DefectDescription;
import com.dev.gis.app.view.elements.AbstractListTable;
import com.dev.gis.connector.api.ClubMobilModelProvider;

public class ClubMobilDefectsListTable extends AbstractListTable {
	
	final DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

	public ClubMobilDefectsListTable(IWorkbenchPartSite site,final Composite parent, 
			IDoubleClickListener selectOfferListener, 
			ISelectionChangedListener selectionChangedListener  ) {
		super(site,parent,selectOfferListener,selectionChangedListener);
	}

	@Override
	public void createColumns(Composite parent, TableViewer viewer) {
	    String[] titles = { "DefectNo", "CarId", "??", "DefectDate", "ReceiptDate",  "" };
	    int[] bounds = { 100, 250, 250, 300, 300,  10};

	    // first column is for the first name
	    TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	DefectDescription o = (DefectDescription) element;
	        return ""+o.getId();
	      }
	    });
	    
	    col = createTableViewerColumn(titles[1], bounds[1], 1);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
		    	DefectDescription o = (DefectDescription) element;
		    	return ""+o.getCarId();
	      }
	    });
	    

	    // second column is supplierId
	    col = createTableViewerColumn(titles[2], bounds[2], 2);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
		    	DefectDescription o = (DefectDescription) element;
	    		return "0";
	      }
	    });

	    col = createTableViewerColumn(titles[3], bounds[3], 3);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
		    	DefectDescription o = (DefectDescription) element;
	    	if ( o.getDefectDate() != null) {
		    	DateTime dt = new DateTime(o.getDefectDate().toDate());
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
	    	DefectDescription o = (DefectDescription) element;
	    	if ( o.getReceiptDate() != null) {
		    	DateTime dt = new DateTime(o.getReceiptDate().toDate());
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
		if ( ClubMobilModelProvider.INSTANCE.defectListResponse != null)
			getViewer().setInput(ClubMobilModelProvider.INSTANCE.defectListResponse.getDefectList());
		else
			getViewer().setInput(null);
		getViewer().refresh();
		
	}

}
