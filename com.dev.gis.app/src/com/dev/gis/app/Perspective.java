package com.dev.gis.app;

import org.apache.log4j.Logger;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.dev.gis.app.taskmanager.TaskTreeView;
import com.dev.gis.app.taskmanager.loggingView.LogBookingEntryView;
import com.dev.gis.app.taskmanager.loggingView.LogEntryTableView;
import com.dev.gis.app.taskmanager.loggingView.LoggingAppView;

public class Perspective implements IPerspectiveFactory {

	private static Logger logger = Logger.getLogger(Perspective.class);

	public static final String ID = "com.dev.gis.app.perspective";

	public void createInitialLayout(IPageLayout layout) {
		logger.info("createInitialLayout for logging ( default ); perspective ");
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		
		IFolderLayout navFolder = layout.createFolder("projects",
				IPageLayout.LEFT, 0.1f, editorArea);
		//navFolder.addView(NavigationView.ID);
		navFolder.addView(TaskTreeView.ID);
		
		
		//layout.addStandaloneView(NavigationView.ID,  false, IPageLayout.LEFT, 0.25f, editorArea);
		IFolderLayout folder = layout.createFolder("BPCS Tools", IPageLayout.TOP, 0.5f, editorArea);
		folder.addPlaceholder(LoggingAppView.ID + ":*");
		//folder.addView(LoggingAppView.ID);
		
		//layout.getViewLayout(NavigationView.ID).setCloseable(false);
		layout.getViewLayout(TaskTreeView.ID).setCloseable(false);
		
		IFolderLayout tabFolder = layout.createFolder("tables",
				IPageLayout.BOTTOM, 0.3f, editorArea);
		tabFolder.addPlaceholder(LogBookingEntryView.ID + ":*");
		tabFolder.addPlaceholder(LogEntryTableView.ID + ":*");
//		tabFolder.addView(LoggingTableView.ID);
//		tabFolder.addView(LogEntryTableView.ID);

	}
}
