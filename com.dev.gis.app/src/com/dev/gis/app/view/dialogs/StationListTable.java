package com.dev.gis.app.view.dialogs;

import java.util.List;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPartSite;

import com.dev.gis.app.model.StationModel;
import com.dev.gis.app.view.elements.AbstractListTable;

public class StationListTable extends AbstractListTable {

	public StationListTable(IWorkbenchPartSite site, final Composite parent,
			IDoubleClickListener listener) {
		super(site, parent, listener, null);
	}

	public void update(List<StationModel> stations) {

		getViewer().setInput(stations);
		getViewer().refresh();

	}

	@Override
	public void createColumns(Composite parent, TableViewer viewer) {
		String[] titles = { "Id", "Name", "Supplier " };
		int[] bounds = { 100, 200, 300 };

		// first column is for the first name
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				StationModel o = (StationModel) element;
				return ""+o.getId();
			}
		});

		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				StationModel o = (StationModel) element;
				return o.getName() ;
			}
		});

		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				StationModel o = (StationModel) element;
				String text = "";
				if ( o.getSupplierId() != null)
					text = o.getSupplierId().toString();
				if ( o.getStation() != null) {
					text = text + " ";
					if ( o.getStation().getAddress() != null)
						text = text +o.getStation().getAddress().getCity();
					else
						text = text +o.getStation().getCityId();
				}
				return text;
			}
		});
		
	}

	@Override
	public void update() {
		
	}

}
