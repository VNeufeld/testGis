package com.dev.gis.app.taskmanager.SunnyCarsView;

import org.eclipse.swt.widgets.Shell;

public class WaitWorker implements Runnable{
	boolean close = false;
	private Shell parentShell;
	public WaitWorker(Shell parentShell) {
		this.parentShell = parentShell;
	}

	public void close() {
		close = true;
	}
	@Override
	public void run() {
		int ind = 0;
		WaitDialog wd;
		wd = new WaitDialog(parentShell);
		wd.open();
		while (!close ) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		wd.close();
	}

}
