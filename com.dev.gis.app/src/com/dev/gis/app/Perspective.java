package com.dev.gis.app;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.dev.gis.app.taskmanager.TaskTreeView;

public class Perspective implements IPerspectiveFactory {

	/**
	 * The ID of the perspective as specified in the extension.
	 */
	public static final String ID = "com.dev.gis.app.perspective";

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		
		IFolderLayout navFolder = layout.createFolder("projects",
				IPageLayout.LEFT, 0.25f, editorArea);
		navFolder.addView(NavigationView.ID);
		navFolder.addView(TaskTreeView.ID);
		
		
		//layout.addStandaloneView(NavigationView.ID,  false, IPageLayout.LEFT, 0.25f, editorArea);
		IFolderLayout folder = layout.createFolder("messages", IPageLayout.TOP, 0.5f, editorArea);
		folder.addPlaceholder(View.ID + ":*");
		folder.addView(View.ID);
		
		layout.getViewLayout(NavigationView.ID).setCloseable(false);
	}
}
