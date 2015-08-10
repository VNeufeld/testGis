package com.dev.gis.app.view.dialogs;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPartSite;

import com.dev.gis.app.view.elements.AbstractListTable;
import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.sunny.Hit;

public class LocationItemListTable extends AbstractListTable  {

	public LocationItemListTable(IWorkbenchPartSite site, final Composite parent, IDoubleClickListener listener) {
		super(site,parent,listener,null);
	}


	public void update() {
		
		getViewer().setInput(SunnyModelProvider.INSTANCE.getLocationSearchHits());
		getViewer().refresh();
		
	}


	@Override
	public void createColumns(Composite parent, TableViewer viewer) {
	    String[] titles = { "Name", "Id", "apt", "Type", "Country" };
	    int[] bounds = { 200, 150, 150, 100, 200};

	    // first column is for the first name
	    TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	Hit o = (Hit) element;
	        return o.getIdentifier();
	      }
	    });
	    
	    col = createTableViewerColumn(titles[1], bounds[1], 1);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	Hit o = (Hit) element;
	        return String.valueOf(o.getId());
	      }
	    });
	    
	    col = createTableViewerColumn(titles[2], bounds[2], 2);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	Hit o = (Hit) element;
	        return String.valueOf(o.getAptCode());
	      }
	    });


	    col = createTableViewerColumn(titles[3], bounds[3], 3);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	  Hit o = (Hit) element;
	    	return  o.getType().name();
	    	
	      }
	    });

	    col = createTableViewerColumn(titles[4], bounds[4], 4);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	  Hit o = (Hit) element;
	    	return  o.getCountry();
	    	
	      }
	    });
	    

	  }
	

}
