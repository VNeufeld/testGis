package com.dev.gis.app.view.dialogs;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.Dialog;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.connector.api.ILocationService;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.sunny.Hit;
import com.dev.gis.connector.sunny.HitType;
import com.dev.gis.connector.sunny.LocationSearchResult;

public class LocationSearchAllCountriesDialog extends Dialog {
	
	private String searchString="";
	private Text tName;
	private TableViewer viewer;
	private final long operator;

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
		
		GridLayout glMain = new GridLayout(1, false);
		composite.setLayout(glMain);
		
		Group gGeneral = new Group(composite, SWT.TITLE);
		gGeneral.setText("Allgemein");
		GridLayout glGeneral = new GridLayout(2, false);
		gGeneral.setLayout(glGeneral);
		
		GridData gdGeneral = new GridData();
		gdGeneral.horizontalAlignment = SWT.FILL;
		gdGeneral.grabExcessHorizontalSpace = true;
		gGeneral.setLayoutData(gdGeneral);
		
		
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
		
				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				ILocationService service = serviceFactory.getLocationJoiService(operator);
				LocationSearchResult result = service.joiLocationSearch(searchString, HitType.COUNTRY, "");
				
				SunnyModelProvider.INSTANCE.updateHits(result);
				
				viewer.refresh();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
		ILocationService service = serviceFactory.getLocationJoiService(operator);
		LocationSearchResult result = service.joiLocationSearch("", HitType.COUNTRY, "");
		
		SunnyModelProvider.INSTANCE.updateHits(result);
		
		viewer.refresh();
		
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
	    String[] titles = { "Name", "Id" };
	    int[] bounds = { 200, 150};

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
