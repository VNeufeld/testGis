package com.dev.gis.app.view.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.CountryTextControl;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.app.view.elements.SearchStringTextControl;
import com.dev.gis.app.view.elements.SelectHitTypeComboBox;
import com.dev.gis.connector.api.ILocationService;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.sunny.Hit;
import com.dev.gis.connector.sunny.HitType;
import com.dev.gis.connector.sunny.LocationSearchResult;

public class LocationSearchDialog extends Dialog implements IDialogCallBack {
	
	private Shell parentShell;
	
	private LocationItemListTable locationItemListTable;
	
	private Hit  result;

	public LocationSearchDialog(Shell parentShell) {
		super(parentShell);
		this.parentShell =parentShell;
		setShellStyle(getShellStyle() | SWT.RESIZE);
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
		gGeneral.setLayout(new GridLayout(8, false));
		
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
		.grab(true, false).applyTo(gGeneral);
		
		final SearchStringTextControl searchStringTextControl = new SearchStringTextControl(gGeneral);

		final CountryTextControl countryTextControl = new CountryTextControl(gGeneral);

		final long operator = SunnyModelProvider.INSTANCE.operatorId;

		new ButtonControl(gGeneral, "select country", 0,  new LocationSearchCountryListener(parentShell, operator,countryTextControl));
		
		final SelectHitTypeComboBox hitTypeComboBox = new SelectHitTypeComboBox(gGeneral, 100);

		Group grTable = new Group(composite, SWT.TITLE);
		grTable.setText("Hit Items");
		grTable.setLayout(new GridLayout(1, false));

		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
		.grab(true, true).applyTo(grTable);

		SelectItemDoubleClickListener selectItemDoubleClickListener = new SelectItemDoubleClickListener(this);
		locationItemListTable = new LocationItemListTable(null, grTable, selectItemDoubleClickListener);

		new ButtonControl(gGeneral, "Search", 0,  
				new StartLocationSearchListener(operator,countryTextControl, searchStringTextControl, hitTypeComboBox, locationItemListTable));
		
		composite.pack();
		return composite;		
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
	
	private static class LocationSearchCountryListener implements SelectionListener {
		

		private final Shell parentShell;
		private final long operator;
		private final ObjectTextControl country;

		public LocationSearchCountryListener(Shell parentShell, long operator,
				ObjectTextControl country) {
			super();
			this.parentShell = parentShell;
			this.operator = operator;
			this.country = country;
		}
		
		@Override
		public void widgetSelected(SelectionEvent e) {
			LocationSearchAllCountriesDialog mpd = new LocationSearchAllCountriesDialog(parentShell, operator);
			
			if (mpd.open() == Dialog.OK) {
				if ( mpd.getResult() != null ) {
					String id = String.valueOf(mpd.getResult().getId());
					country.setSelectedValue(id);
				}
					
			}
			
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			widgetSelected(e);
		}
		
	}
	
	private static class StartLocationSearchListener implements SelectionListener {
		

		private final long operator;
		private final ObjectTextControl country;
		private final ObjectTextControl searchStringTextControl;
		private final SelectHitTypeComboBox hitTypeComboBox;
		
		private final LocationItemListTable locationItemListTable;

		public StartLocationSearchListener(long operator, ObjectTextControl country, 
				ObjectTextControl searchStringTextControl, SelectHitTypeComboBox hitTypeComboBox, LocationItemListTable locationItemListTable) {
			super();
			this.operator = operator;
			this.country = country;
			this.searchStringTextControl = searchStringTextControl;
			this.hitTypeComboBox = hitTypeComboBox;
			this.locationItemListTable = locationItemListTable;
		}
		
		@Override
		public void widgetSelected(SelectionEvent e) {
			HitType type = hitTypeComboBox.getHitType();
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			ILocationService service = serviceFactory.getLocationJoiService(operator);
			LocationSearchResult result = service.joiLocationSearch(searchStringTextControl.getSelectedValue(), type, country.getSelectedValue());
			SunnyModelProvider.INSTANCE.updateHits(result);
			
			locationItemListTable.update();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			widgetSelected(e);
		}
		
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
	
	
	
}
