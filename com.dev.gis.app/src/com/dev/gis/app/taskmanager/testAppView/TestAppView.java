package com.dev.gis.app.taskmanager.testAppView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import com.bpcs.mdcars.protocol.Offer;
import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.connector.joi.protocol.VehicleResponse;
import com.dev.gis.db.api.DaoFactory;
import com.dev.gis.db.api.IStationDao;
import com.dev.gis.task.execution.api.ITaskResult;
import com.dev.gis.task.execution.api.JoiVehicleConnector;
import com.dev.gis.task.execution.api.ModelProvider;

public class TestAppView extends TaskViewAbstract {
	public static final String ID = "com.dev.gis.app.task.TestAppView";
	private StringFieldEditor city;
	private StringFieldEditor airport;
	
	Calendar checkInDate = Calendar.getInstance();
	Calendar dropOffDate = Calendar.getInstance();

	private IWorkbenchAction exitAction;

	 private TableViewer viewer;
	
	@Override
	public void createPartControl(Composite parent) {
		
//		 exitAction = ActionFactory.QUIT.create(this);
//	        register(exitAction);
//	        
	        
		//exitAction = new IWorkbenchAction(viewer, parent.getShell());

		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		
		final Group groupStamp = new Group(composite, SWT.TITLE);
		groupStamp.setText("Inputs:");
		groupStamp.setLayout(new GridLayout(4, true));
		groupStamp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		GridData gdFirm = new GridData();
		gdFirm.grabExcessHorizontalSpace = true;
		gdFirm.horizontalAlignment = SWT.FILL;

		Label cityLabel = new Label(groupStamp, SWT.NONE);
		cityLabel.setText("Stadt");
		final Text cityText = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		cityText.setLayoutData(gdFirm);

		Label aptLabel = new Label(groupStamp, SWT.NONE);
		aptLabel.setText("Airport");
		final Text aptText = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		aptText.setLayoutData(gdFirm);
		
		
//		final Button buttonPrintDate = new Button(groupStamp, SWT.CHECK | SWT.LEFT);
//		buttonPrintDate.setText("Pickup-Datum");
//		buttonPrintDate.setSelection(true);
		
		initDates();
		
		addDateControl("pickupDate:",groupStamp,checkInDate);

		addDateControl("dropOffDate:",groupStamp,dropOffDate);
		

		
		final Button buttonGetOffer = new Button(groupStamp, SWT.PUSH | SWT.LEFT);
		buttonGetOffer.setText("Get Offers");

		
		buttonGetOffer.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				System.out.println("Checkindate = "+new SimpleDateFormat("dd.MM.yyyy hh:mm:ss").format(checkInDate.getTime()));
				
				IStationDao stationDao = new DaoFactory().getStationDao();
				System.out.println(" Stationname = "+stationDao.getStationName());
				
				//VehicleResponse response = JoiVehicleConnector.getOffers();
				
				//changeModel(response);
				viewer.refresh();
			}


			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		createViewer(composite);
		
		
//	    final Table table = viewer.getTable();
//
//		  Menu menu = new Menu(table);
//	        MenuItem menuItem = new MenuItem(menu, SWT.NONE);
//	        menuItem.setText("Print Element");
//	        menuItem.addSelectionListener(new SelectionAdapter() {
//	            @Override
//	            public void widgetSelected(SelectionEvent event) {
//	                System.out.println(table.getSelection()[0].getText());
//	            }
//	        });
//	        viewer.getControl().setMenu(menu);


		
	}
	
	protected void changeModel(VehicleResponse response) {
		ModelProvider.INSTANCE.update();
	    //viewer.setInput(response.getModelProvider.INSTANCE.getOffers());
	    viewer.setInput(ModelProvider.INSTANCE.getOffers());
	
	}

	private void initDates() {
		
		checkInDate.set(Calendar.HOUR_OF_DAY, 11);
		checkInDate.set(Calendar.MINUTE, 30);
		checkInDate.set(Calendar.SECOND, 0);

		dropOffDate.set(Calendar.HOUR_OF_DAY, 18);
		dropOffDate.set(Calendar.MINUTE, 0);
		dropOffDate.set(Calendar.SECOND, 0);	
	}

	private void addDateControl(String label,Composite composite,final Calendar resultDate ) {
		GridData gdDate = new GridData();
		gdDate.grabExcessHorizontalSpace = true;
		gdDate.horizontalAlignment = SWT.FILL;

		new Label(composite, SWT.NONE).setText(label);

		final DateTime dateTime = new DateTime(composite, SWT.DATE);
		dateTime.setLayoutData(gdDate);
		
		
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;

		final DateTime dateTime2 = new DateTime(composite, SWT.TIME);
		dateTime2.setLayoutData(gridData);
		
		
		dateTime2.setHours(resultDate.get(Calendar.HOUR_OF_DAY));
		dateTime2.setMinutes(resultDate.get(Calendar.MINUTE));
		dateTime2.setSeconds(resultDate.get(Calendar.SECOND));

		dateTime.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				resultDate.set(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		
		dateTime2.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				resultDate.set(Calendar.HOUR_OF_DAY, dateTime2.getHours());
				resultDate.set(Calendar.MINUTE, dateTime2.getMinutes());
				resultDate.set(Calendar.SECOND, dateTime2.getSeconds());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		
	}
	
	private void createViewer(Composite parent) {
	    viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
	        | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
	    createColumns(parent, viewer);
	    final Table table = viewer.getTable();
	    table.setHeaderVisible(true);
	    table.setLinesVisible(true);

	    viewer.setContentProvider(new ArrayContentProvider());
	    // get the content for the viewer, setInput will call getElements in the
	    // contentProvider
	    //viewer.setInput(ModelProvider.INSTANCE.getOffers());
	    // make the selection available to other views
	    getSite().setSelectionProvider(viewer);
	    // set the sorter for the table

	    // define layout for the viewer
	    GridData gridData = new GridData();
	    gridData.verticalAlignment = GridData.FILL;
	    gridData.horizontalSpan = 2;
	    gridData.grabExcessHorizontalSpace = true;
	    gridData.grabExcessVerticalSpace = true;
	    gridData.horizontalAlignment = GridData.FILL;
	    viewer.getControl().setLayoutData(gridData);
	    
	    hookContextMenu();
	  }
	
	private void createColumns(final Composite parent, final TableViewer viewer) {
	    String[] titles = { "Name", "Supplier", "Station", "Price" };
	    int[] bounds = { 100, 100, 100, 100 };

	    // first column is for the first name
	    TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	        Offer o = (Offer) element;
	        return o.getName();
	      }
	    });

	    // second column is for the last name
	    col = createTableViewerColumn(titles[1], bounds[1], 1);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	        Offer o = (Offer) element;
	        return String.valueOf(o.getSupplierId());
	      }
	    });

	    // now the gender
	    col = createTableViewerColumn(titles[2], bounds[2], 2);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	        Offer o = (Offer) element;
	        return String.valueOf(o.getPickUpStationId());
	      }
	    });

	    // now the status married
	    col = createTableViewerColumn(titles[3], bounds[3], 3);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	        Offer o = (Offer) element;
	        
	        return o.getPrice().getAmount();
	      }

//	      @Override
//	      public Image getImage(Object element) {
//	        if (((Person) element).isMarried()) {
//	          return CHECKED;
//	        } else {
//	          return UNCHECKED;
//	        }
//	      }
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


	  public TableViewer getViewer() {
	    return viewer;
	  }

	
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(ITaskResult result) {
		// TODO Auto-generated method stub
		
	}
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager();

		menuMgr.setRemoveAllWhenShown(true);

		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
		
		
		
	}


}
