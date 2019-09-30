package com.dev.gis.app;

import org.apache.log4j.Logger;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.dev.gis.app.taskmanager.TaskTreeView;
import com.dev.gis.app.taskmanager.SunnyCarsView.SunnyCarsAppView;

public class SunnyAppPerspective implements IPerspectiveFactory {
	
	private static Logger logger = Logger.getLogger(SunnyAppPerspective.class);


	public static final String ID = "com.dev.gis.app.sunnyapp.perspective";

	public void createInitialLayout(IPageLayout layout) {

		logger.info("createInitialLayout for sunny perspective " + ID);
		
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		
		IFolderLayout navFolder = layout.createFolder("Projects",IPageLayout.LEFT, 0.1f, editorArea);
		navFolder.addView(TaskTreeView.ID);
		
		IFolderLayout folder = layout.createFolder("BPCS Sunny APP Test Tools", IPageLayout.TOP, 0.5f, editorArea);
		folder.addPlaceholder(SunnyCarsAppView.ID + ":*");

	}
}
