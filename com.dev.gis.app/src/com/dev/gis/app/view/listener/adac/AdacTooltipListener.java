package com.dev.gis.app.view.listener.adac;


import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.dev.gis.connector.api.OfferDo;
import com.dev.gis.connector.joi.protocol.VehicleDescription;

public class AdacTooltipListener {

	private final Shell shell;
	private final Table table;

	public AdacTooltipListener(Shell shell, Table table) {
		super();
		this.shell = shell;
		this.table = table;
	}
	
	public Listener getTableListener() {
		
		final Listener labelListener = new Listener () {
			@Override
			public void handleEvent (Event event) {
				Label label = (Label)event.widget;
				Shell shell = label.getShell ();
				switch (event.type) {
					case SWT.MouseDown:
						Event e = new Event ();
						// Assuming table is single select, set the selection as if
						// the mouse down event went through to the table
						shell.dispose ();
						break;
					case SWT.MouseExit:
						shell.dispose ();
						break;
				}
			}
		};

		
		Listener tableListener = new Listener () {
			Shell tip = null;
			Label label = null;
			@Override
			public void handleEvent (Event event) {
				switch (event.type) {
					case SWT.Dispose:
					case SWT.KeyDown:
					case SWT.MouseMove: {
						if (tip == null) break;
						tip.dispose ();
						tip = null;
						label = null;
						break;
					}
					case SWT.MouseHover: {
							TableItem item = table.getItem (new Point (event.x, event.y));
							if (item != null) {
								Object data = item.getData();
								StringBuffer sb = new StringBuffer();
								if ( data instanceof OfferDo) {
									OfferDo offer = (OfferDo) data;
									VehicleDescription vd = offer.getModel().getVehicle();
									sb.append("Veh: "+vd.getManufacturer());
									sb.append("\n");
									sb.append("Model: "+vd.getVehicleModel());
								}
								Display display = shell.getDisplay();
								if (tip != null  && !tip.isDisposed ()) tip.dispose ();
								tip = new Shell (shell, SWT.ON_TOP | SWT.NO_FOCUS | SWT.TOOL);
								tip.setBackground (display.getSystemColor (SWT.COLOR_INFO_BACKGROUND));
								FillLayout layout = new FillLayout ();
								layout.marginWidth = 2;
								tip.setLayout (layout);
								label = new Label (tip, SWT.NONE);
								label.setForeground (display.getSystemColor (SWT.COLOR_INFO_FOREGROUND));
								label.setBackground (display.getSystemColor (SWT.COLOR_INFO_BACKGROUND));
								//label.setText ("text");
								label.setData ("_TABLEITEM", item);
								label.setText (sb.toString());
								label.addListener (SWT.MouseExit, labelListener);
								label.addListener (SWT.MouseDown, labelListener);
								org.eclipse.swt.graphics.Point size = tip.computeSize (SWT.DEFAULT, SWT.DEFAULT);
								Rectangle rect = item.getBounds (0);
								Point pt = table.toDisplay (rect.x, rect.y);
								tip.setBounds (pt.x, pt.y, 300, 300);
								//tip.setBounds (100, 200, 400, 500);
								tip.setVisible (true);
							}
						}
				}
			}
		};
		return tableListener;
	}

}
