package com.dev.gis.app;

import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/**
 * This workbench advisor creates the window advisor, and specifies
 * the perspective id for the initial window.
 */
public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {
	
    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        return new ApplicationWorkbenchWindowAdvisor(configurer);
    }

	public String getInitialWindowPerspectiveId() {
		return SunnyAppPerspective.ID;
	}

	@Override
	public void postStartup() {
		// TODO Auto-generated method stub
	     PreferenceManager pm = PlatformUI.getWorkbench().getPreferenceManager();
//       See what we have
      for (IPreferenceNode node : pm.getRootSubNodes()) {
         System.out.println(node.getId());
      }
	  pm.remove("org.eclipse.m2e.core.preferences.Maven2PreferencePage");
	  pm.remove("org.eclipse.ui.preferencePages.Workbench");
	  super.postStartup();
	} 
	
}
