package com.dev.gis.app;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.dev.gis.app.taskmanager.TaskTreeView;
import com.dev.gis.app.taskmanager.loggingView.LoggingAppView;
import com.dev.gis.app.taskmanager.loggingView.LoggingTableView;

public class Perspective implements IPerspectiveFactory {

	/**
	 * The ID of the perspective as specified in the extension.
	 */
	public static final String ID = "com.dev.gis.app.perspective";

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		
		IFolderLayout navFolder = layout.createFolder("projects",
				IPageLayout.LEFT, 0.1f, editorArea);
		//navFolder.addView(NavigationView.ID);
		navFolder.addView(TaskTreeView.ID);
		
		
		//layout.addStandaloneView(NavigationView.ID,  false, IPageLayout.LEFT, 0.25f, editorArea);
		IFolderLayout folder = layout.createFolder("BPCS Tools", IPageLayout.TOP, 0.5f, editorArea);
		folder.addPlaceholder(LoggingAppView.ID + ":*");
		folder.addView(LoggingAppView.ID);
		
		//layout.getViewLayout(NavigationView.ID).setCloseable(false);
		layout.getViewLayout(TaskTreeView.ID).setCloseable(false);
		
		IFolderLayout tabFolder = layout.createFolder("tables",
				IPageLayout.BOTTOM, 0.3f, editorArea);
		tabFolder.addView(LoggingTableView.ID);

	}
}
