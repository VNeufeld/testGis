package com.dev.gis.app;

import org.apache.log4j.Logger;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.dev.gis.app.taskmanager.TaskTreeView;
import com.dev.gis.app.taskmanager.loggingView.LoggingAppView;
import com.dev.gis.app.taskmanager.testAppView.TestAppView;

public class TestAppPerspective implements IPerspectiveFactory {
	
	private static Logger logger = Logger.getLogger(TestAppPerspective.class);

	
	public static final String ID = "com.dev.gis.app.testapp.perspective";

	public void createInitialLayout(IPageLayout layout) {
		
		logger.info("createInitialLayout for sunny perspective " + ID);
		
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		
		IFolderLayout navFolder = layout.createFolder("Projects",IPageLayout.LEFT, 0.1f, editorArea);
		navFolder.addView(TaskTreeView.ID);
		
		IFolderLayout folder = layout.createFolder("BPCS APP Test Tools", IPageLayout.TOP, 0.5f, editorArea);
		folder.addPlaceholder(TestAppView.ID + ":*");

	}
}
