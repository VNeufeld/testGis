package com.dev.gis.app;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import com.dev.gis.app.actions.ResetCurrentPerspectiveAction;
import com.dev.gis.app.actions.SwitchToLogPerspectiveAction;
import com.dev.gis.app.actions.SwitchToAppPerspectiveAction;
import com.dev.gis.app.actions.SwitchToSunnyAppPerspectiveAction;
import com.dev.gis.connector.api.TaskProperties;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of the
 * actions added to a workbench window. Each window will be populated with
 * new actions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

    // Actions - important to allocate these only in makeActions, and then use them
    // in the fill methods.  This ensures that the actions aren't recreated
    // when fillActionBars is called with FILL_PROXY.
    private IWorkbenchAction exitAction;
    private IWorkbenchAction aboutAction;
    private Action stopProcessAction;
    private Action switchPerspectiveAction;
    private Action switchToAppPerspectiveAction;
    private Action switchToSunnyAppPerspectiveAction;
    private Action resetCurrentPerspectiveAction;
    private Action lastResponseAction;
    private IWorkbenchAction preferencesAction;

    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }
    
    protected void makeActions(final IWorkbenchWindow window) {
        // Creates the actions and registers them.
        // Registering is needed to ensure that key bindings work.
        // The corresponding commands keybindings are defined in the plugin.xml file.
        // Registering also provides automatic disposal of the actions when
        // the window is closed.
    	
		boolean onlyLogging = TaskProperties.getTaskProperties().isOnlyLogging();


        exitAction = ActionFactory.QUIT.create(window);
        register(exitAction);
        
        aboutAction = ActionFactory.ABOUT.create(window);
        register(aboutAction);
        
//        newWindowAction = ActionFactory.OPEN_NEW_WINDOW.create(window);
//        register(newWindowAction);
//        
//        openViewAction = new OpenViewAction(window, "Open Another Message View", View.ID);
//        register(openViewAction);

//        messagePopupAction = new MessagePopupAction("Open Message", window);
//        register(messagePopupAction);

        resetCurrentPerspectiveAction = new ResetCurrentPerspectiveAction("Reset Perspective", window);
        register(resetCurrentPerspectiveAction);
        
        stopProcessAction = new StopProcessAction("Stop Process", window);
        register(stopProcessAction);

        switchPerspectiveAction = new SwitchToLogPerspectiveAction("Switch to Log Perspective", window);
        register(switchPerspectiveAction);

        switchToAppPerspectiveAction = new SwitchToAppPerspectiveAction("Switch To App Perspective", window);
        register(switchToAppPerspectiveAction);
        
        switchToSunnyAppPerspectiveAction = new SwitchToSunnyAppPerspectiveAction("Switch To Sunny App Perspective", window);
        register(switchToAppPerspectiveAction);

        preferencesAction = ActionFactory.PREFERENCES.create(window);
        register(preferencesAction);
        
        lastResponseAction = new ShowResponseAction("Show last response", window);
        register(lastResponseAction);        
        
    }
    
    protected void fillMenuBar(IMenuManager menuBar) {
        MenuManager fileMenu = new MenuManager("&File", IWorkbenchActionConstants.M_FILE);
        MenuManager perspectiveMenu = new MenuManager("&Perspective", "Perspective");
        MenuManager helpMenu = new MenuManager("&Help", IWorkbenchActionConstants.M_HELP);
        
        menuBar.add(fileMenu);
        menuBar.add(perspectiveMenu);
        // Add a group marker indicating where action set menus will appear.
        menuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
        menuBar.add(helpMenu);
        
        // File
        //fileMenu.add(newWindowAction);
        //fileMenu.add(new Separator());
        //fileMenu.add(messagePopupAction);
        //fileMenu.add(openViewAction);
        //fileMenu.add(new Separator());
		//boolean onlyLogging = TaskProperties.getTaskProperties().isOnlyLogging();
		

        perspectiveMenu.add(switchPerspectiveAction);
		if ( !Application.ONLY_LOGGING){
	        perspectiveMenu.add(switchToAppPerspectiveAction);
	        perspectiveMenu.add(switchToSunnyAppPerspectiveAction);
		}
        perspectiveMenu.add(new Separator());
        perspectiveMenu.add(resetCurrentPerspectiveAction);
        fileMenu.add(exitAction);
        
        // Help
        helpMenu.add(aboutAction);
        helpMenu.add(preferencesAction);
    }
    
    protected void fillCoolBar(ICoolBarManager coolBar) {
    	
        IToolBarManager toolbar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
        coolBar.add(new ToolBarContributionItem(toolbar, "main"));   
        //toolbar.add(openViewAction);
        toolbar.add(switchPerspectiveAction);
		if ( !Application.ONLY_LOGGING){
	        //toolbar.add(switchToAppPerspectiveAction);
	        toolbar.add(resetCurrentPerspectiveAction);
	        toolbar.add(lastResponseAction);
		}
		else
			toolbar.add(stopProcessAction);
    }
}
