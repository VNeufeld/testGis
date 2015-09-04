package com.bpcs.eml.app;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	private static final String PERSPECTIVE_ID = "com.bpcs.eml.app.perspective";

	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		return new ApplicationWorkbenchWindowAdvisor(configurer);
	}

	public String getInitialWindowPerspectiveId() {
		return PERSPECTIVE_ID;
	}

	@Override
	public void postStartup() {
		// TODO Auto-generated method stub
		super.postStartup();
//		final Shell shell = 
//				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
//		shell.setVisible(true);
		//PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setMinimized(false); 
	}

	@Override
	public IStatus restoreState(IMemento memento) {
		// TODO Auto-generated method stub
		return super.restoreState(memento);
	}

	@Override
	public void createWindowContents(IWorkbenchWindowConfigurer configurer,
			Shell shell) {
		// TODO Auto-generated method stub
		super.createWindowContents(configurer, shell);
	}

	@Override
	public void initialize(IWorkbenchConfigurer configurer) {
		// TODO Auto-generated method stub
		super.initialize(configurer);
		configurer.restoreState();
		
	}

	@Override
	public boolean openWindows() {
		// TODO Auto-generated method stub
		return super.openWindows();
	}

	@Override
	public void preStartup() {
		// TODO Auto-generated method stub
		super.preStartup();
	}

	@Override
	public void preWindowOpen(IWorkbenchWindowConfigurer configurer) {
		// TODO Auto-generated method stub
		super.preWindowOpen(configurer);
	}

}
