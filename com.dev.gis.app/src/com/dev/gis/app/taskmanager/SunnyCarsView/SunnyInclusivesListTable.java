package com.dev.gis.app.taskmanager.SunnyCarsView;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPartSite;

import com.dev.gis.app.view.elements.AbstractListTable;
import com.dev.gis.connector.sunny.Inclusive;

public class SunnyInclusivesListTable extends AbstractListTable {


	public SunnyInclusivesListTable(IWorkbenchPartSite site, final Composite parent,
			ISelectionChangedListener selectionChangedListener) {
		super(site, parent, null, selectionChangedListener);

	}

	@Override
	
	public void createColumns(Composite parent, TableViewer viewer) {
		String[] titles = { "id", "Code", "Name", "Class ID", "Class Code","ClassName","ClassFilter", "Priority", "Positive" };
		int[] bounds = {50, 100, 200, 50, 100, 150, 100, 100, 100 };

		// first column is for the first name
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Inclusive o = (Inclusive) element;
				return ""+o.getId();
			}
		});

		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Inclusive o = (Inclusive) element;
				return o.getCode();
			}
		});
		
		// second column is supplierId
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Inclusive o = (Inclusive) element;
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
				Inclusive o = (Inclusive) element;
				return ""+o.getItemClassId();
			}
		});

		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Inclusive o = (Inclusive) element;
				return ""+o.getItemClassCode();
			}
		});

		col = createTableViewerColumn(titles[5], bounds[5], 5);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Inclusive o = (Inclusive) element;
				return ""+o.getItemClassName();
			}
		});

		col = createTableViewerColumn(titles[6], bounds[6], 6);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Inclusive o = (Inclusive) element;
				return String.valueOf(o.isItemClassFilter());
			}
		});
		
		col = createTableViewerColumn(titles[7], bounds[7], 7);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Inclusive o = (Inclusive) element;
				return ""+o.getDisplayPriority();
			}
		});

		col = createTableViewerColumn(titles[8], bounds[8], 8);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Inclusive o = (Inclusive) element;
				if ( o.getPositive() == 1)
					return "true";
				return "false";
			}
		});
		
	}

	public void update(List<Inclusive> inclusives) {
		getViewer().setInput(inclusives);
		getViewer().refresh();
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
