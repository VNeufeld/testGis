package com.dev.gis.app.view.dialogs;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.connector.api.ILocationService;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.ModelProvider;
import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.sunny.Hit;
import com.dev.gis.connector.sunny.HitType;
import com.dev.gis.connector.sunny.LocationSearchResult;

public class LocationSearchDialog extends Dialog {
	
	private String searchString="";
	private Text tName;
	private TableViewer viewer;
	
	private Shell parentShell;
	
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
		
		GridLayout glMain = new GridLayout(1, false);
		composite.setLayout(glMain);
		
		Group gGeneral = new Group(composite, SWT.TITLE);
		gGeneral.setText("Allgemein");
		GridLayout glGeneral = new GridLayout(8, false);
		gGeneral.setLayout(glGeneral);
		
		GridData gdGeneral = new GridData();
		gdGeneral.horizontalAlignment = SWT.FILL;
		gdGeneral.grabExcessHorizontalSpace = true;
		gGeneral.setLayoutData(gdGeneral);
		
		Label lName = new Label(gGeneral, SWT.NONE);
		lName.setText("search");
		
		
		GridData gdName = new GridData();
		gdName.horizontalAlignment = SWT.FILL;
		gdName.grabExcessHorizontalSpace = true;

		tName = new Text(gGeneral, SWT.BORDER | SWT.SINGLE);
		tName.setText(searchString);
		tName.setLayoutData(gdName);
		tName.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				searchString = tName.getText();
			}
		});

		new Label(gGeneral, SWT.NONE).setText("country:");
		
		final Text country = new Text(gGeneral, SWT.BORDER | SWT.SINGLE);
		country.setLayoutData(gdName);
		
		Button countryButton = new Button(gGeneral, SWT.PUSH | SWT.CENTER | SWT.COLOR_BLUE);
		countryButton.setText("select country");
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING).grab(false, false).applyTo(countryButton);
		
		final long operator = ModelProvider.INSTANCE.operatorId;
		countryButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				LocationSearchAllCountriesDialog mpd = new LocationSearchAllCountriesDialog(parentShell, operator);
				
				if (mpd.open() == Dialog.OK) {
					if ( mpd.getResult() != null ) {
						String id = String.valueOf(mpd.getResult().getId());
						country.setText(id);
					}
						
				}
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		
		
		
		new Label(gGeneral, SWT.NONE).setText("filter:");
		final Combo c = new Combo(gGeneral, SWT.READ_ONLY);
		String items[] = { "","All", "City", "Airport","City + Airports", "Country" };
		c.setItems(items);
		c.select(2);
		
		
		createViewer(composite);
		
		GridData gdButton = new GridData();
		gdButton.grabExcessHorizontalSpace = false;
		gdButton.horizontalAlignment = SWT.NONE;
		Button button = new Button(gGeneral, SWT.PUSH | SWT.CENTER | SWT.COLOR_BLUE);
		button.setText("Search");
		button.setLayoutData(gdButton);

		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				int filterIndex = c.getSelectionIndex();
				HitType type = HitType.UNINITIALIZED;
				if ( filterIndex == 2)
					type = HitType.CITY;
				if ( filterIndex == 3)
					type = HitType.AIRPORT;
				if ( filterIndex == 5)
					type = HitType.COUNTRY;

				if ( filterIndex == 4)
					type = HitType.RAILWAY_STATION;
				

				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				ILocationService service = serviceFactory.getLocationJoiService(operator);
				LocationSearchResult result = service.joiLocationSearch(searchString, type, country.getText());
				
				SunnyModelProvider.INSTANCE.updateHits(result);
				
				viewer.refresh();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		
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
	
	private void createViewer(Composite parent) {
		
	    viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
	        | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
	    createColumns(parent, viewer);
	    final Table table = viewer.getTable();
	    table.setHeaderVisible(true);
	    table.setLinesVisible(true);

	    viewer.setContentProvider(new ArrayContentProvider());

	    viewer.setInput(SunnyModelProvider.INSTANCE.getLocationSearchHits());

	    // define layout for the viewer
	    GridData gridData = new GridData();
	    gridData.verticalAlignment = GridData.FILL;
	    gridData.horizontalSpan = 2;
	    gridData.grabExcessHorizontalSpace = true;
	    gridData.grabExcessVerticalSpace = true;
	    gridData.horizontalAlignment = GridData.FILL;
	    viewer.getControl().setLayoutData(gridData);
	    
	    viewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				result = (Hit) ((IStructuredSelection) event.getSelection()).getFirstElement();
				okPressed();
			}
		});
	    
	    hookContextMenu();
	}
	
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager();

		menuMgr.setRemoveAllWhenShown(true);

		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		
		
	}
	
	
	private void createColumns(final Composite parent, final TableViewer viewer) {
	    String[] titles = { "Name", "Id", "apt", "Type", "Country" };
	    int[] bounds = { 200, 150, 150, 100, 200};

	    // first column is for the first name
	    TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	Hit o = (Hit) element;
	        return o.getIdentifier();
	      }
	    });
	    
	    col = createTableViewerColumn(titles[1], bounds[1], 1);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	Hit o = (Hit) element;
	        return String.valueOf(o.getId());
	      }
	    });
	    
	    col = createTableViewerColumn(titles[2], bounds[2], 2);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	Hit o = (Hit) element;
	        return String.valueOf(o.getAptCode());
	      }
	    });


	    col = createTableViewerColumn(titles[3], bounds[3], 3);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	  Hit o = (Hit) element;
	    	return  o.getType().name();
	    	
	      }
	    });

	    col = createTableViewerColumn(titles[4], bounds[4], 4);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	  Hit o = (Hit) element;
	    	return  o.getCountry();
	    	
	      }
	    });
	    

	  }

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
	    final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
	        SWT.NONE);
	    final TableColumn column = viewerColumn.getColumn();
	    column.setText(title);
	    column.setWidth(bound);
	    column.setResizable(true);
	    column.setMoveable(true);
	    return viewerColumn;
		
	}

	public Hit getResult() {
		return result;
	}
	
}
