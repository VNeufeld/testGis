package com.dev.gis.app;

import org.apache.log4j.Logger;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.dev.gis.app.task.model.TaskProjectModelFactory;
import com.dev.gis.connector.api.TaskProperties;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {
	public static final String PLUGIN_ID = "rcpmail-99";
	private Logger logger = Logger.getLogger(getClass());

	public static final boolean  ONLY_LOGGING = false;

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	public Object start(IApplicationContext context) {
		Display display = PlatformUI.createDisplay();
        logger.info("class path :"+ System.getProperty("java.class.path"));
        logger.info("home :"+ System.getProperty("java.home"));
        logger.info("os :"+ System.getProperty("os.name"));
        logger.info("user dir :"+ System.getProperty("user.dir"));
        logger.info("user home:"+ System.getProperty("user.home"));
        logger.info("user name:"+ System.getProperty("user.name"));

		try {
			TaskProjectModelFactory.createModel();
			
			int returnCode = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor());
			if (returnCode == PlatformUI.RETURN_RESTART) {
				return IApplication.EXIT_RESTART;
			}
			return IApplication.EXIT_OK;
		} finally {
			display.dispose();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		if (!PlatformUI.isWorkbenchRunning())
			return;
		
		TaskProperties.getTaskProperties().readProperty();
		
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable() {
			public void run() {
				if (!display.isDisposed())
					workbench.close();
			}
		});
	}
}
