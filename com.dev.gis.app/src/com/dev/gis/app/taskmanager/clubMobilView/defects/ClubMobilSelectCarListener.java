package com.dev.gis.app.taskmanager.clubMobilView.defects;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class ClubMobilSelectCarListener  implements SelectionListener  {

	protected Text stationText;

	private final Shell shell; 

	public ClubMobilSelectCarListener(final Shell shell) {
		this.shell = shell;
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {

		SelectCarDialog mpd = new SelectCarDialog(shell);

		if (mpd.open() == Dialog.OK) {
				
		}
		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
