package com.dev.gis.app.view.sunny.requestUtils;

import org.eclipse.core.runtime.IProgressMonitor;

public class ShowTimer implements Runnable {
	final IProgressMonitor monitor;
	private boolean work = true;
	
	public ShowTimer(IProgressMonitor monitor) {
		this.monitor = monitor;
	}

	@Override
	public void run() {
		while ( work ) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		    monitor.worked(300);
		}
	}

	public void setWork(boolean work) {
		this.work = work;
	}
	
}

