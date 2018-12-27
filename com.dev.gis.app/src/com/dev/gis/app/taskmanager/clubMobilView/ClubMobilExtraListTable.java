package com.dev.gis.app.taskmanager.clubMobilView;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPartSite;

import com.dev.gis.app.taskmanager.testAppView.AdacExtraListTable;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.joi.protocol.Extra;
import com.dev.gis.connector.joi.protocol.ExtraResponse;
import com.dev.gis.connector.joi.protocol.ExtraResponseCM;

public class ClubMobilExtraListTable extends AdacExtraListTable {
	private static Logger logger = Logger.getLogger(ClubMobilExtraListTable.class);

	public ClubMobilExtraListTable(IWorkbenchPartSite site, final Composite parent,
			ISelectionChangedListener selectionChangedListener) {
		super(site, parent, selectionChangedListener);

	}

	public List<Extra> getSelectedExtras() {
		List<Extra> extras = ClubMobilModelProvider.INSTANCE.getExtras();
		List<Extra> selectedExtras = new ArrayList<Extra>();
		for (Extra extra : extras ) {
			if ( extra.isSelected())
				selectedExtras.add(extra);
		}
		
		return selectedExtras;
	}

	public void refresh(ExtraResponse response) {
		ClubMobilModelProvider.INSTANCE.updateExtras(response);
		getViewer().setInput(ClubMobilModelProvider.INSTANCE.getExtras());
		getViewer().refresh();
		logger.info("refresh CM Extras" + ClubMobilModelProvider.INSTANCE.getExtras().size());
		
	}

	public void refresh() {
		getViewer().setInput(ClubMobilModelProvider.INSTANCE.getExtras());
		getViewer().refresh();
	}


}
