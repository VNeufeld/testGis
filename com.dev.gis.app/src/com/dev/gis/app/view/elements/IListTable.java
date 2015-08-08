package com.dev.gis.app.view.elements;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;

public interface IListTable {
	void createColumns(Composite parent, TableViewer viewer);
	void update();
}
