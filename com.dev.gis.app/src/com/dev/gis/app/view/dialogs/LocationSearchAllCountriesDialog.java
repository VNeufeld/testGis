package com.dev.gis.app.view.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.IListTable;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.app.view.elements.SearchStringTextControl;
import com.dev.gis.connector.api.ILocationService;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.sunny.Hit;
import com.dev.gis.connector.sunny.HitType;
import com.dev.gis.connector.sunny.LocationSearchResult;

public class LocationSearchAllCountriesDialog extends Dialog implements IDialogCallBack{
	
	private final long operator;
	
	private CountryListTable countryListTable;

	private Hit  result;

	public LocationSearchAllCountriesDialog(Shell parentShell, final long operator) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		
		this.operator = operator;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.getShell().setText("location search");
		composite.setLayout(new GridLayout(1, false));

		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
		.grab(true, true).applyTo(composite);
		
		Group gGeneral = new Group(composite, SWT.TITLE);
		gGeneral.setText("Allgemein");
		gGeneral.setLayout(new GridLayout(3, false));
		
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
		.grab(true, false).applyTo(gGeneral);
		
		
		final SearchStringTextControl searchStringTextControl = new SearchStringTextControl(gGeneral);
		
		// Create Table
		Group grTable = new Group(composite, SWT.TITLE);
		grTable.setText("Hit Items");
		grTable.setLayout(new GridLayout(1, false));

		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
		.grab(true, true).applyTo(grTable);
		
		SelectItemDoubleClickListener selectItemDoubleClickListener = new SelectItemDoubleClickListener(this);
		countryListTable = new CountryListTable(null, grTable , selectItemDoubleClickListener);
		
		// create Button
		
		new ButtonControl(gGeneral, "Search", 0,  
				new StartCountrySearchListener(operator, searchStringTextControl, countryListTable));
		
		showAllCountries();

		
		composite.pack();
		return composite;		
	}

	private void showAllCountries() {
		JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
		ILocationService service = serviceFactory.getLocationJoiService(operator);
		LocationSearchResult result = service.joiLocationSearch("", HitType.COUNTRY, "");

		SunnyModelProvider.INSTANCE.updateHits(result);
		countryListTable.update();
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		super.okPressed();
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		//super.createButtonsForButtonBar(parent);
	}
	

	public Hit getResult() {
		return result;
	}
	public void closeDialog(Hit hit) {
		result = hit;
		okPressed();
	}
	
	private static class SelectItemDoubleClickListener implements IDoubleClickListener {
		

		private final IDialogCallBack dialogCallBack;

		public SelectItemDoubleClickListener(IDialogCallBack dialogCallBack) {
			super();
			this.dialogCallBack = dialogCallBack;
		}
		
		@Override
		public void doubleClick(DoubleClickEvent event) {
			Hit hit = (Hit) ((IStructuredSelection) event.getSelection()).getFirstElement();
			dialogCallBack.closeDialog(hit);
		}
		
	}
	
	private static class StartCountrySearchListener implements SelectionListener {
		

		private final long operator;
		private final ObjectTextControl searchStringTextControl;
		
		private final IListTable listTable;

		public StartCountrySearchListener(long operator, 
				ObjectTextControl searchStringTextControl, IListTable listTable) {
			super();
			this.operator = operator;
			this.searchStringTextControl = searchStringTextControl;
			this.listTable = listTable;
		}
		
		@Override
		public void widgetSelected(SelectionEvent e) {
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			ILocationService service = serviceFactory.getLocationJoiService(operator);
			LocationSearchResult result = service.joiLocationSearch(searchStringTextControl.getSelectedValue(), HitType.COUNTRY, "");

			SunnyModelProvider.INSTANCE.updateHits(result);
			listTable.update();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			widgetSelected(e);
		}
		
	}

	
}
