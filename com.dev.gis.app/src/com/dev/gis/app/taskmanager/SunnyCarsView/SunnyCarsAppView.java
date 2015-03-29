package com.dev.gis.app.taskmanager.SunnyCarsView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.app.taskmanager.offerDetailView.OfferViewUpdater;
import com.dev.gis.connector.joi.protocol.DayAndHour;
import com.dev.gis.connector.joi.protocol.Location;
import com.dev.gis.connector.joi.protocol.TravelInformation;
import com.dev.gis.connector.joi.protocol.VehicleRequest;
import com.dev.gis.connector.joi.protocol.VehicleResponse;
import com.dev.gis.task.execution.api.IEditableTask;
//import com.dev.gis.db.api.IStationDao;
import com.dev.gis.task.execution.api.ITaskResult;
import com.dev.gis.task.execution.api.JoiVehicleConnector;
import com.dev.gis.task.execution.api.ModelProvider;
import com.dev.gis.task.execution.api.OfferDo;
import com.dev.gis.task.execution.api.TaskProperties;

public class SunnyCarsAppView extends TaskViewAbstract {
	public static final String ID = IEditableTask.ID_TestSunnyCarsView;
	private StringFieldEditor city;
	private StringFieldEditor airport;
	
	private Text serverUrl;
	private Text operator;
	
	Calendar checkInDate = Calendar.getInstance();
	
	DateTime pickupDate = null;
	DateTime pickupTime = null;

	DateTime dropOffDate = null;
	DateTime dropOffTime = null;
	
	private IWorkbenchAction exitAction;

	private TableViewer viewer;
	
	private Text countVehicles = null;

	private Text sessionId = null;

	private Text requestId = null;
	
	private Button buttonOfferTest = null;
	
	private Button buttonGetOffer = null;	
	
	private static TravelInformation travelInformation;
	
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
		groupStamp.setLayout(new GridLayout(4, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(false, false).applyTo(groupStamp);
		
		serverUrl = createServer(groupStamp);

		operator = createOperator(groupStamp);
		
		final Text cityText = createCityText(groupStamp);

		final Text aptText = createAptText(groupStamp);
		
		final Button buttonTruck = createRadioButtonsCarAndTruck(groupStamp);
		
		initDates();
		
		addPickupDateControl("pickupDate:",groupStamp);

		addDropOffDateControl("dropOffDate:",groupStamp);
		
		// GetOffer - Button
		createGetOffersButton(composite);
		
		// Result - Group
		Composite groupResult = createResultComposite(composite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(false, false).span(4, 1).applyTo(groupResult);
		
		createViewer(composite);
		
		buttonOfferTest.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
		        new OfferViewUpdater().showOffer(null);
			}
		});

		
		
		buttonGetOffer.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				System.out.println("Checkindate = "+new SimpleDateFormat("dd.MM.yyyy hh:mm:ss").format(checkInDate.getTime()));
//				
//				IStationDao stationDao = Components.getInstance().getDaoFactory().getStationDao();
//				
//				System.out.println(" Stationname = "+stationDao.getStationName());
				
				TaskProperties.getTaskProperties().setServerProperty(serverUrl.getText());
				TaskProperties.getTaskProperties().setOperator(Long.valueOf(operator.getText()));
				TaskProperties.getTaskProperties().saveProperty();
				
				TableItem[] selection = viewer.getTable().getSelection();
				if ( selection != null && selection.length > 0) {
					for ( TableItem item : selection) {
						System.out.println(" item = "+item.getData());
						
					}
				}
				
				VehicleRequest request = new VehicleRequest();
				TravelInformation ti = new TravelInformation();
				Location pickUpLocation = new Location() ;
				pickUpLocation.setAirport(aptText.getText());
				if ( StringUtils.isNotEmpty(cityText.getText()))
					pickUpLocation.setCityId(Long.valueOf(cityText.getText()));
				else
					pickUpLocation.setAirport(aptText.getText());

				Location dropOffLocation = new Location() ;
				if ( StringUtils.isNotEmpty(cityText.getText()))
					dropOffLocation.setCityId(Long.valueOf(cityText.getText()));
				else
					dropOffLocation.setAirport(aptText.getText());
				
				ti.setPickUpLocation(pickUpLocation);
				ti.setDropOffLocation(dropOffLocation);
				
				DayAndHour dh = createDate(pickupDate,pickupTime);
				ti.setPickUpTime(dh);

				dh = createDate(dropOffDate,dropOffTime);
				ti.setDropOffTime(dh);
				
				request.setTravel(ti);
				
				if ( buttonTruck.getSelection())
					request.setModule(2);
				else
					request.setModule(1);
				request.setPayment(1);
				
				travelInformation = ti;
				
				VehicleResponse response = JoiVehicleConnector.getOffers(request);
				//VehicleResponse response = JoiVehicleConnector.getOffersDummy();
				
				countVehicles.setText(String.valueOf(response.getResultList().size()));
				
				sessionId.setText(response.getSessionId());
				
				requestId.setText(String.valueOf(response.getRequestId()));				
				
				changeModel(response);
				viewer.refresh();
			}


			private DayAndHour createDate(DateTime pickupDate, DateTime pickupTime) {
				String sday = String.format("%4d-%02d-%02d", pickupDate.getYear(), pickupDate.getMonth()+1, pickupDate.getDay());
				String sTime = String.format("%02d:%02d", pickupTime.getHours(), pickupTime.getMinutes());
				
				DayAndHour dh = new DayAndHour();
				dh.setDate(sday);
				
				dh.setTime(sTime);
				return dh;
			}


			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		
		
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






	private Button createRadioButtonsCarAndTruck(final Group groupStamp) {
		Composite rbComposite=new Composite(groupStamp, SWT.NONE);
		rbComposite.setLayout(new GridLayout(2, true));
		GridDataFactory.fillDefaults().span(4, 1).align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(rbComposite);

		final Button buttonCar = new Button(rbComposite, SWT.RADIO);
		buttonCar.setSelection(true);
		buttonCar.setText("Car");
		
		final Button buttonTruck = new Button(rbComposite, SWT.RADIO);
		buttonTruck.setSelection(false);
		buttonTruck.setText("Truck");

		return buttonTruck;
	}

	private Composite createGetOffersButton(Composite composite) {
		Composite bComposite=new Composite(composite, SWT.NONE);
		bComposite.setLayout(new GridLayout(2, true));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(bComposite);
		
		buttonGetOffer = new Button(bComposite, SWT.PUSH | SWT.LEFT);
		buttonGetOffer.setText("Get Offers");
		
		buttonOfferTest = new Button(bComposite, SWT.PUSH | SWT.LEFT);
		buttonOfferTest.setText("OfferTest");
		
		return bComposite;
	}

	private Composite createResultComposite(Composite composite) {
		
		GridData gdFirm = new GridData();
//		gdFirm.grabExcessHorizontalSpace = true;
//		gdFirm.horizontalAlignment = SWT.FILL;
		
		final Group groupResult = new Group(composite, SWT.TITLE);
		groupResult.setText("Result:");
		groupResult.setLayout(new GridLayout(4, true));
		
		new Label(groupResult, SWT.NONE).setText("Count of Vehicles");
		countVehicles = new Text(groupResult, SWT.BORDER | SWT.SINGLE);
		countVehicles.setLayoutData(gdFirm);

		new Label(groupResult, SWT.NONE).setText("Request ID");
		requestId = new Text(groupResult, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER).hint(100, 16).grab(false,false).applyTo(requestId);
		
		new Label(groupResult, SWT.NONE).setText("Session ID");
		sessionId = new Text(groupResult, SWT.BORDER | SWT.SINGLE);
		//sessionId.setLayoutData(gdFirm);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER).hint(300, 16).grab(false,false).span(3, 1).applyTo(sessionId);
		
		
		return groupResult;
	}
	
	protected void changeModel(VehicleResponse response) {
		ModelProvider.INSTANCE.updateOffers(response);
	    viewer.setInput(ModelProvider.INSTANCE.getOfferDos());
	
	}

	private void initDates() {
		
		checkInDate.set(Calendar.HOUR_OF_DAY, 11);
		checkInDate.set(Calendar.MINUTE, 30);
		checkInDate.set(Calendar.SECOND, 0);

	}

	private void addPickupDateControl(String label,Composite groupStamp) {
		
		Composite composite=new Composite(groupStamp, SWT.NONE);
		composite.setLayout(new GridLayout(3, true));
		GridDataFactory.fillDefaults().span(4, 1).align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(composite);
		

		new Label(composite, SWT.NONE).setText(label);

		pickupDate = createDateControl(composite);

		pickupTime = createTimeControl(composite);
		
	}
	
	private void addDropOffDateControl(String label,Composite groupStamp) {
		Composite composite=new Composite(groupStamp, SWT.NONE);
		composite.setLayout(new GridLayout(3, true));
		GridDataFactory.fillDefaults().span(4, 1).align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(composite);
		
		new Label(composite, SWT.NONE).setText(label);

		dropOffDate = createDateControl(composite);

		dropOffTime = createTimeControl(composite);

		
	}
	
	
	private DateTime createTimeControl(Composite composite) {
		
		final Calendar resultDate = Calendar.getInstance();
		
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;

		DateTime time = new DateTime(composite, SWT.TIME);
//		time.setLayoutData(gridData);
		
		
		time.setHours(10);
		time.setMinutes(0);
		time.setSeconds(0);

		
		time.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
//				resultDate.set(Calendar.HOUR_OF_DAY, pickupTime.getHours());
//				resultDate.set(Calendar.MINUTE, pickupTime.getMinutes());
//				resultDate.set(Calendar.SECOND, pickupTime.getSeconds());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		return time;
		
	}

	
	private DateTime createDateControl(Composite composite) {

		DateTime date = new DateTime(composite, SWT.DATE);
		
		GridData gdDate = new GridData();
		gdDate.grabExcessHorizontalSpace = true;
		gdDate.horizontalAlignment = SWT.FILL;

		//date.setLayoutData(gdDate);

		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER).grab(false,false).applyTo(date);
		

		date.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//resultDate.set(pickupDate.getYear(), pickupDate.getMonth(), pickupDate.getDay());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		return date;
		
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
	    
	    viewer.addDoubleClickListener(new DoubleClickListener());
	    
	    hookContextMenu();
	}
	
	private static class DoubleClickListener implements IDoubleClickListener 
	{

		@Override
		public void doubleClick(DoubleClickEvent event) {
			
			TableViewer viewer = (TableViewer) event.getViewer();
	        IStructuredSelection thisSelection = (IStructuredSelection) event
	            .getSelection();
	        Object selectedNode = thisSelection.getFirstElement();
	        
	    	OfferDo offer = (OfferDo) selectedNode;
	    	offer.setTravelInformation(travelInformation);
 
	        new OfferViewUpdater().showOffer(offer);
	        System.out.println("selectedNode "+offer);
	        
		}
		
	}
	
	private void createColumns(final Composite parent, final TableViewer viewer) {
	    String[] titles = { "Name", "Group", "Supplier", "Station", "Service Catalog",  "Price", "Incl. km.", "Prepaid" };
	    int[] bounds = { 200, 150, 100, 100, 100,  200, 100, 100 };

	    // first column is for the first name
	    TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	OfferDo o = (OfferDo) element;
	        return o.getName();
	      }
	    });
	    
	    col = createTableViewerColumn(titles[1], bounds[1], 1);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	OfferDo o = (OfferDo) element;
	        return o.getGroup();
	      }
	    });
	    

	    // second column is supplierId
	    col = createTableViewerColumn(titles[2], bounds[2], 2);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	OfferDo o = (OfferDo) element;
	        return String.valueOf(o.getSupplierId());
	      }
	    });

	    col = createTableViewerColumn(titles[3], bounds[3], 3);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	OfferDo o = (OfferDo) element;
			return String.valueOf(o.getPickUpStationId());
	      }
	    });

	    col = createTableViewerColumn(titles[4], bounds[4], 4);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	OfferDo o = (OfferDo) element;
			return o.getServiceCatalogCode() + " : "+ String.valueOf(o.getServiceCatalogId());
	      }
	    });
	    
	    
	    col = createTableViewerColumn(titles[5], bounds[5], 5);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	OfferDo o = (OfferDo) element;
			return o.getPrice().getAmount();
	      }

	    });
	    
	    col = createTableViewerColumn(titles[6], bounds[6], 6);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	OfferDo o = (OfferDo) element;
	    	String incl = o.getInclusiveKm();
			return incl;
	      }
	    });

	    col = createTableViewerColumn(titles[7], bounds[7], 7);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	OfferDo o = (OfferDo) element;
	    	return  o.getPrepaid();
	    	
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

	private void span(Composite parent, int x ) {
		GridDataFactory.fillDefaults().span(x, 1).align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(parent);
	}

	private Text createServer(Group groupStamp) {
		
		new Label(groupStamp, SWT.NONE).setText("Server URL");
		
		GridData gdComposite1 = new GridData();
		gdComposite1.grabExcessHorizontalSpace = true;
		gdComposite1.grabExcessVerticalSpace = false;
		gdComposite1.horizontalAlignment = SWT.FILL;
		gdComposite1.verticalAlignment = SWT.FILL;
		gdComposite1.widthHint = 550;
		gdComposite1.horizontalSpan=3;
		
		Composite composite = new Composite(groupStamp, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(composite);
		composite.setLayoutData(gdComposite1);
		
		

		final Text serverUrl = new Text(composite, SWT.BORDER | SWT.SINGLE);
		serverUrl.setText(TaskProperties.getTaskProperties().getServerProperty());
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING).grab(false, false).span(1, 1).hint(400, 16).applyTo(serverUrl);
		
		serverUrl.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				if ( arg0.character == '\r') {
					TaskProperties.getTaskProperties().setServerProperty(serverUrl.getText());
					TaskProperties.getTaskProperties().saveProperty();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		return serverUrl;
		
	}

	private Text createOperator(Group groupStamp) {
		GridData gdComposite1 = new GridData();
		gdComposite1.grabExcessHorizontalSpace = true;
		gdComposite1.grabExcessVerticalSpace = false;
		gdComposite1.horizontalAlignment = SWT.FILL;
		gdComposite1.verticalAlignment = SWT.FILL;
		gdComposite1.widthHint = 550;
		gdComposite1.horizontalSpan=3;
		
		new Label(groupStamp, SWT.NONE).setText("Operator");
		
		
		Composite composite = new Composite(groupStamp, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(composite);
		composite.setLayoutData(gdComposite1);
		
		final Text operator = new Text(composite, SWT.BORDER | SWT.SINGLE);
		operator.setText(String.valueOf(TaskProperties.getTaskProperties().getOperator()));
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING).grab(false, false).span(1, 1).hint(100, 16).applyTo(operator);
		
		return operator;
	}

	private Text createCityText(final Group groupStamp) {
		
		GridData gdComposite1 = new GridData();
		gdComposite1.grabExcessHorizontalSpace = true;
		gdComposite1.grabExcessVerticalSpace = false;
		gdComposite1.horizontalAlignment = SWT.FILL;
		gdComposite1.verticalAlignment = SWT.FILL;
		gdComposite1.widthHint = 550;
		gdComposite1.horizontalSpan=3;

		new Label(groupStamp, SWT.NONE).setText("City ID");
		
		Composite composite=new Composite(groupStamp, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(composite);
		composite.setLayoutData(gdComposite1);
//		GridDataFactory.fillDefaults().span(3, 1).align(SWT.FILL, SWT.BEGINNING).grab(true, false).hint(550, 1).applyTo(composite);

		
		final Text cityText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING).grab(false, false).hint(100, 16).applyTo(cityText);
		
		Button buttonSearch = new Button(composite, SWT.PUSH | SWT.LEFT);
		buttonSearch.setText("Search city");
		buttonSearch.addSelectionListener (new SearchCitySelectionListener(this.serverUrl.getText(), this.operator.getText(), composite.getShell(), cityText));
		
		//new Label(composite, SWT.NONE).setText("( 3586 for SIXT Truck ) ");

		return cityText;
	}

	
	private Text createAptText(Group groupStamp) {
		
		GridData gdComposite1 = new GridData();
		gdComposite1.grabExcessHorizontalSpace = true;
		gdComposite1.grabExcessVerticalSpace = false;
		gdComposite1.horizontalAlignment = SWT.FILL;
		gdComposite1.verticalAlignment = SWT.FILL;
		gdComposite1.widthHint = 550;
		gdComposite1.horizontalSpan=3;
		
		new Label(groupStamp, SWT.NONE).setText("APT CODE");
		

		Composite composite=new Composite(groupStamp, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(composite);
		composite.setLayoutData(gdComposite1);

		
		final Text aptText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING).grab(false, false).hint(100, 16).applyTo(aptText);
		return aptText;
	}
	
	
}
