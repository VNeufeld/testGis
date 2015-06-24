package com.dev.gis.app.taskmanager.SunnyCarsView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.app.taskmanager.loggingView.LogEntryDialog;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.TaskProperties;
import com.dev.gis.connector.api.VehicleHttpService;
import com.dev.gis.connector.sunny.DayAndHour;
import com.dev.gis.connector.sunny.Location;
import com.dev.gis.connector.sunny.OfferInformation;
import com.dev.gis.connector.sunny.TravelInformation;
import com.dev.gis.connector.sunny.VehicleRequest;
import com.dev.gis.connector.sunny.VehicleResponse;
import com.dev.gis.connector.sunny.VehicleSummary;
import com.dev.gis.task.execution.api.IEditableTask;
//import com.dev.gis.db.api.IStationDao;
import com.dev.gis.task.execution.api.ITaskResult;
import com.dev.gis.task.execution.api.SunnyModelProvider;
import com.dev.gis.task.execution.api.SunnyOfferDo;

public class SunnyCarsAppView extends TaskViewAbstract {
	public static final String ID = IEditableTask.ID_TestSunnyCarsView;
	private StringFieldEditor city;
	private StringFieldEditor airport;

	private Text serverUrl;
	private Text operator;

	private Combo languageList;

	Calendar checkInDate = Calendar.getInstance();

	DateTime pickupDate = null;
	DateTime pickupTime = null;

	DateTime dropOffDate = null;
	DateTime dropOffTime = null;

	private IWorkbenchAction exitAction;

	private TableViewer viewer;

	private TableViewer recommendationTable;

	private Text pageInfo = null;
	
	private Text summary = null;

	private Text sessionId = null;

	private Text offerId = null;

	private Text pageSize = null;

	private Text contact = null;

	private Text offerFilterTemlate = null;
	
	private Text requestId = null;

	private Button buttonOfferTest = null;

	private Button buttonGetOffer = null;
	
	private int pageNo = 0;

	private static TravelInformation travelInformation;

	@Override
	public void createPartControl(Composite parent) {

		// exitAction = ActionFactory.QUIT.create(this);
		// register(exitAction);
		//

		// exitAction = new IWorkbenchAction(viewer, parent.getShell());

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		final Group groupStamp = new Group(composite, SWT.TITLE);
		groupStamp.setText("Inputs:");
		groupStamp.setLayout(new GridLayout(4, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(false, false).applyTo(groupStamp);

		serverUrl = createServer(groupStamp);

		operator = createOperator(groupStamp);

		languageList = createLanguageList(groupStamp);

		final Text cityText = createCityText(groupStamp);

		final Text aptText = createAptText(groupStamp);

		final Button buttonTruck = createRadioButtonsCarAndTruck(groupStamp);

		initDates();

		addPickupDateControl("pickupDate:", groupStamp);

		addDropOffDateControl("dropOffDate:", groupStamp);
		
		new Label(groupStamp, SWT.NONE).setText("Page size");
		pageSize = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		pageSize.setText("3");
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(false, false).span(2, 1).hint(30, 16).applyTo(pageSize);
		new Label(groupStamp, SWT.NONE).setText("( Anzahl Gruppen auf der Seite. )");


		// GetOffer - Button
		createGetOffersButton(composite);

		// Result - Group
		Composite groupResult = createResultComposite(composite);
		

		final Group groupOffers = new Group(composite, SWT.TITLE);
		groupOffers.setText("Ofers:");
		
		groupOffers.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, true).applyTo(groupOffers);
		
		createViewer(groupOffers);

		final Group groupRecomm = new Group(composite, SWT.TITLE);
		groupRecomm.setText("Recommendations:");
		groupRecomm.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, true).applyTo(groupRecomm);
		
		createRecommendations(groupRecomm);

		buttonOfferTest.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new SunnyOfferViewUpdater().showOffer(null);
			}
		});

		buttonGetOffer.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				System.out.println("Checkindate = "
						+ new SimpleDateFormat("dd.MM.yyyy hh:mm:ss")
								.format(checkInDate.getTime()));

				TaskProperties.getTaskProperties().setServerProperty(
						serverUrl.getText());
				TaskProperties.getTaskProperties().setOperator(
						Long.valueOf(operator.getText()));

				TaskProperties.getTaskProperties().setAptCode(aptText.getText());
				
				savePropertyDates();

				TaskProperties.getTaskProperties().saveProperty();
				
				

				VehicleRequest request = createVehicleRequest();

				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
				VehicleHttpService service = serviceFactory
						.getVehicleJoiService();
				
				
				int pageSizeInt = 3;
				if ( pageSize.getText() != null )
					pageSizeInt = Integer.valueOf(pageSize.getText());
				

				VehicleResponse response = service.getOffers(request, false, pageSizeInt);

				if (response != null) {

					showVehicleResponse(response);
					
					pageNo = 0;
					

				}
			}


			private VehicleRequest createVehicleRequest() {

				VehicleRequest request = new VehicleRequest();
				TravelInformation ti = new TravelInformation();

				Location pickUpLocation = new Location();
				if (StringUtils.isNotEmpty(cityText.getText()))
					pickUpLocation.setCityId(Long.valueOf(cityText.getText()));
				else
					pickUpLocation.setAirport(aptText.getText());

				Location dropOffLocation = new Location();
				if (StringUtils.isNotEmpty(cityText.getText()))
					dropOffLocation.setCityId(Long.valueOf(cityText.getText()));
				else
					dropOffLocation.setAirport(aptText.getText());

				ti.setPickUpLocation(pickUpLocation);
				ti.setDropOffLocation(dropOffLocation);

				DayAndHour dh = createDate(pickupDate, pickupTime);
				ti.setPickUpTime(dh);

				dh = createDate(dropOffDate, dropOffTime);
				ti.setDropOffTime(dh);

				request.setTravel(ti);

				// if ( buttonTruck.getSelection())
				// request.setModule(2);
				// else
				// request.setModule(1);
				// request.setPayment(PayType.PREPAID);

				return request;

			}

			private DayAndHour createDate(DateTime pickupDate,
					DateTime pickupTime) {
				String sday = String.format("%4d-%02d-%02d",
						pickupDate.getYear(), pickupDate.getMonth() + 1,
						pickupDate.getDay());
				String sTime = String.format("%02d:%02d",
						pickupTime.getHours(), pickupTime.getMinutes());

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

		// final Table table = viewer.getTable();
		//
		// Menu menu = new Menu(table);
		// MenuItem menuItem = new MenuItem(menu, SWT.NONE);
		// menuItem.setText("Print Element");
		// menuItem.addSelectionListener(new SelectionAdapter() {
		// @Override
		// public void widgetSelected(SelectionEvent event) {
		// System.out.println(table.getSelection()[0].getText());
		// }
		// });
		// viewer.getControl().setMenu(menu);

	}

	protected void savePropertyDates() {
		
		String sday = String.format("%4d-%02d-%02d",
				pickupDate.getYear(), pickupDate.getMonth() + 1,
				pickupDate.getDay());
		String sTime = String.format("%02d:%02d",
				pickupTime.getHours(), pickupTime.getMinutes());

		
		DayAndHour dh = new DayAndHour();
		dh.setDate(sday);
		dh.setTime(sTime);
		
		TaskProperties.getTaskProperties().setPickupDate(dh);
		

		sday = String.format("%4d-%02d-%02d",
				dropOffDate.getYear(), dropOffDate.getMonth() + 1,
				dropOffDate.getDay());
		sTime = String.format("%02d:%02d",
				dropOffTime.getHours(), dropOffTime.getMinutes());

		
		dh = new DayAndHour();
		dh.setDate(sday);
		dh.setTime(sTime);
		
		TaskProperties.getTaskProperties().setDropoffDate(dh);
		
		
		
		
	}

	private void createRecommendations(Composite parent) {

		recommendationTable = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		createColumns(parent, recommendationTable);
		final Table table = recommendationTable.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		recommendationTable.setContentProvider(new ArrayContentProvider());

		getSite().setSelectionProvider(recommendationTable);
		// set the sorter for the table

		// define layout for the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		recommendationTable.getControl().setLayoutData(gridData);

	}

	private Button createRadioButtonsCarAndTruck(final Group groupStamp) {
		Composite rbComposite = new Composite(groupStamp, SWT.NONE);
		rbComposite.setLayout(new GridLayout(2, true));
		GridDataFactory.fillDefaults().span(4, 1)
				.align(SWT.FILL, SWT.BEGINNING).grab(true, false)
				.applyTo(rbComposite);

		final Button buttonCar = new Button(rbComposite, SWT.RADIO);
		buttonCar.setSelection(true);
		buttonCar.setText("Car");

		final Button buttonTruck = new Button(rbComposite, SWT.RADIO);
		buttonTruck.setSelection(false);
		buttonTruck.setText("Truck");

		return buttonTruck;
	}

	private Composite createGetOffersButton(Composite composite) {
		Composite bComposite = new Composite(composite, SWT.NONE);
		bComposite.setLayout(new GridLayout(2, true));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(true, false).applyTo(bComposite);

		buttonGetOffer = new Button(bComposite, SWT.PUSH | SWT.LEFT);
		buttonGetOffer.setText("Get Offers");

		buttonOfferTest = new Button(bComposite, SWT.PUSH | SWT.LEFT);
		buttonOfferTest.setText("OfferTest");

		return bComposite;
	}
	
	private void createNextPage(final Composite groupResult) {
		
		new Label(groupResult, SWT.NONE).setText("Page");
		pageInfo =  new Text(groupResult, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.hint(800, 16).grab(false, false).span(2, 1).applyTo(pageInfo);
		
		Button nextPage = new Button(groupResult, SWT.PUSH | SWT.LEFT);
		nextPage.setText("next page");
		
		nextPage.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				 getNextPage();
			}

			private void getNextPage() {

				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();

				VehicleHttpService service = serviceFactory
						.getVehicleJoiService();
				
				pageNo++;
				VehicleResponse response = service.getPage(pageNo);
				
				showVehicleResponse(response);

				
				
			}
		});

	}
	
	private void createBrowseFilterPage(final Composite groupResult) {
		
		new Label(groupResult, SWT.NONE).setText("BrowseFilter");
		final Text browseFilter =  new Text(groupResult, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.hint(800, 16).grab(false, false).span(2, 1).applyTo(browseFilter);
		
		Button nextPage = new Button(groupResult, SWT.PUSH | SWT.LEFT);
		nextPage.setText("browse filter page");
		
		
		nextPage.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				 getNextPage();
			}

			private void getNextPage() {

				JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();

				VehicleHttpService service = serviceFactory
						.getVehicleJoiService();
				
				int pageSizeInt = 3;
				if ( pageSize.getText() != null )
					pageSizeInt = Integer.valueOf(pageSize.getText());
				
				VehicleResponse response = service.getBrowsePage(browseFilter.getText(),pageNo,pageSizeInt);
				
				showVehicleResponse(response);
				
			}
		});

	}
	

	private Composite createResultComposite(final Composite composite) {

		GridData gdFirm = new GridData();

		final Group groupResult = new Group(composite, SWT.TITLE);
		groupResult.setText("Result:");
		groupResult.setLayout(new GridLayout(4, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(false, false).span(4, 1).applyTo(groupResult);

		createNextPage(groupResult);
		
		createBrowseFilterPage(groupResult);

		new Label(groupResult, SWT.NONE).setText("Summary");

		summary = new Text(groupResult, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(true, false).span(3, 1).applyTo(summary);



		new Label(groupResult, SWT.NONE).setText("Request ID");
		requestId = new Text(groupResult, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER)
				.hint(200, 16).grab(false, false).span(3, 1).applyTo(requestId);

		new Label(groupResult, SWT.NONE).setText("Session ID");
		sessionId = new Text(groupResult, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER)
				.hint(300, 16).grab(false, false).span(3, 1).applyTo(sessionId);

		new Label(groupResult, SWT.NONE).setText("OfferId");
		offerId = new Text(groupResult, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER)
				.hint(300, 16).grab(false, false).span(3, 1).applyTo(offerId);

		new Label(groupResult, SWT.NONE).setText("Contact");
		contact = new Text(groupResult, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(true, false).span(3, 1).applyTo(contact);

		new Label(groupResult, SWT.NONE).setText("offerFilterTemplate");
		offerFilterTemlate = new Text(groupResult, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.hint(800, 16).grab(false, false).span(2, 1).applyTo(offerFilterTemlate);
		
		Button showFilter = new Button(groupResult, SWT.PUSH | SWT.LEFT);
		showFilter.setText("show Filter");
		showFilter.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				OfferFilterDialog mpd = new OfferFilterDialog(composite.getShell());
				if (mpd.open() == Dialog.OK) {
					
				}
				
			}
		});


		return groupResult;
	}

	protected void changeModel(VehicleResponse response) {
		SunnyModelProvider.INSTANCE.updateOffers(response);
		viewer.setInput(SunnyModelProvider.INSTANCE.getOfferDos());

	}

	private void initDates() {

		checkInDate.set(Calendar.HOUR_OF_DAY, 11);
		checkInDate.set(Calendar.MINUTE, 30);
		checkInDate.set(Calendar.SECOND, 0);

	}

	private void addPickupDateControl(String label, Composite groupStamp) {

		Composite composite = new Composite(groupStamp, SWT.NONE);
		composite.setLayout(new GridLayout(3, true));
		GridDataFactory.fillDefaults().span(4, 1)
				.align(SWT.FILL, SWT.BEGINNING).grab(true, false)
				.applyTo(composite);

		new Label(composite, SWT.NONE).setText(label);

		pickupDate = createDateControl(composite);
		pickupTime = createTimeControl(composite);
		
		DayAndHour dh = TaskProperties.getTaskProperties().getPickupDate();
		if ( dh != null) {
			Calendar cal = dh.toCalendar();
			pickupDate.setYear(cal.get(Calendar.YEAR));
			pickupDate.setMonth(cal.get(Calendar.MONTH));
			pickupDate.setDay(cal.get(Calendar.DAY_OF_MONTH));
			
			pickupTime.setHours(cal.get(Calendar.HOUR_OF_DAY));
			pickupTime.setMinutes(cal.get(Calendar.MINUTE));
		}

	}

	private void addDropOffDateControl(String label, Composite groupStamp) {
		Composite composite = new Composite(groupStamp, SWT.NONE);
		composite.setLayout(new GridLayout(3, true));
		GridDataFactory.fillDefaults().span(4, 1)
				.align(SWT.FILL, SWT.BEGINNING).grab(true, false)
				.applyTo(composite);

		new Label(composite, SWT.NONE).setText(label);

		dropOffDate = createDateControl(composite);

		dropOffTime = createTimeControl(composite);
		
		DayAndHour dh = TaskProperties.getTaskProperties().getDropoffDate();
		if ( dh != null) {
			Calendar cal = dh.toCalendar();
			dropOffDate.setYear(cal.get(Calendar.YEAR));
			dropOffDate.setMonth(cal.get(Calendar.MONTH));
			dropOffDate.setDay(cal.get(Calendar.DAY_OF_MONTH));
			
			dropOffTime.setHours(cal.get(Calendar.HOUR_OF_DAY));
			dropOffTime.setMinutes(cal.get(Calendar.MINUTE));
		}
		

	}

	private DateTime createTimeControl(Composite composite) {

		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;

		DateTime time = new DateTime(composite, SWT.TIME);
		// time.setLayoutData(gridData);

		time.setHours(10);
		time.setMinutes(0);
		time.setSeconds(0);

		time.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				// resultDate.set(Calendar.HOUR_OF_DAY, pickupTime.getHours());
				// resultDate.set(Calendar.MINUTE, pickupTime.getMinutes());
				// resultDate.set(Calendar.SECOND, pickupTime.getSeconds());
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

		// date.setLayoutData(gdDate);

		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER)
				.grab(false, false).applyTo(date);

		date.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// resultDate.set(pickupDate.getYear(), pickupDate.getMonth(),
				// pickupDate.getDay());
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
		// viewer.setInput(ModelProvider.INSTANCE.getOffers());
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

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection thisSelection = (IStructuredSelection) event
						.getSelection();
				Object selectedNode = thisSelection.getFirstElement();

				if ( selectedNode != null ) {
					SunnyOfferDo offer = (SunnyOfferDo) selectedNode;
					if ( offer != null )
						offerId.setText(offer.getId().toString());
				}

			}
		});

		hookContextMenu();
	}

	private static class DoubleClickListener implements IDoubleClickListener {

		@Override
		public void doubleClick(DoubleClickEvent event) {

			TableViewer viewer = (TableViewer) event.getViewer();
			IStructuredSelection thisSelection = (IStructuredSelection) event
					.getSelection();
			Object selectedNode = thisSelection.getFirstElement();

			SunnyOfferDo offer = (SunnyOfferDo) selectedNode;

			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			VehicleHttpService service = serviceFactory.getVehicleJoiService();

			OfferInformation offerInformation = service.selectOffer(offer
					.getLink());
			if (offerInformation != null)
				offer.addOfferInformation(offerInformation);

			new SunnyOfferViewUpdater().showOffer(offer);
			System.out.println("selectedNode " + offer);

		}

	}

	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Name", "Group", "Supplier", "Station",
				"Service Catalog", "Price", "Rating", "Incl. km." };
		int[] bounds = { 250, 100, 80, 80, 50, 120, 50, 100 };

		// first column is for the first name
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0, viewer);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				return o.getCarDescription();
			}
		});

		col = createTableViewerColumn(titles[1], bounds[1], 1, viewer);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				return o.getGroup();
			}
		});

		// second column is supplierId
		col = createTableViewerColumn(titles[2], bounds[2], 2, viewer);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				return String.valueOf(o.getSupplierId());
			}
		});

		col = createTableViewerColumn(titles[3], bounds[3], 3, viewer);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				return String.valueOf(o.getPickUpStationId());
			}
		});

		col = createTableViewerColumn(titles[4], bounds[4], 4, viewer);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				return String.valueOf(o.getServiceCatalogId());
			}
		});

		col = createTableViewerColumn(titles[5], bounds[5], 5, viewer);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				return o.getPrice().getAmount();
			}

		});

		col = createTableViewerColumn(titles[6], bounds[6], 6, viewer);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				if (o.getRating() == null)
					return "null";
				return o.getRating().toString();
			}

		});

		col = createTableViewerColumn(titles[7], bounds[7], 7, viewer);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				String incl = o.getInclusiveKm();
				return incl;
			}
		});

	}

	private TableViewerColumn createTableViewerColumn(String title, int bound,
			final int colNumber, final TableViewer viewer) {
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

	private Text createServer(Group groupStamp) {

		new Label(groupStamp, SWT.NONE).setText("Server URL");

		GridData gdComposite1 = new GridData();
		gdComposite1.grabExcessHorizontalSpace = true;
		gdComposite1.grabExcessVerticalSpace = false;
		gdComposite1.horizontalAlignment = SWT.FILL;
		gdComposite1.verticalAlignment = SWT.FILL;
		gdComposite1.widthHint = 550;
		gdComposite1.horizontalSpan = 3;

		Composite composite = new Composite(groupStamp, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(composite);
		composite.setLayoutData(gdComposite1);

		final Text serverUrl = new Text(composite, SWT.BORDER | SWT.SINGLE);
		serverUrl.setText(TaskProperties.getTaskProperties()
				.getServerProperty());
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(false, false).span(1, 1).hint(400, 16).applyTo(serverUrl);

		serverUrl.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent arg0) {
				if (arg0.character == '\r') {
					TaskProperties.getTaskProperties().setServerProperty(
							serverUrl.getText());
					TaskProperties.getTaskProperties().saveProperty();
				}
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		serverUrl.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void focusLost(FocusEvent arg0) {
				TaskProperties.getTaskProperties().setServerProperty(
						serverUrl.getText());
				TaskProperties.getTaskProperties().saveProperty();
			}
		});

		return serverUrl;

	}

	private Combo createLanguageList(Group groupStamp) {
		// TODO Auto-generated method stub
		GridData gdComposite1 = new GridData();
		gdComposite1.grabExcessHorizontalSpace = true;
		gdComposite1.grabExcessVerticalSpace = false;
		gdComposite1.horizontalAlignment = SWT.FILL;
		gdComposite1.verticalAlignment = SWT.FILL;
		gdComposite1.widthHint = 550;
		gdComposite1.horizontalSpan = 3;

		new Label(groupStamp, SWT.NONE).setText("Language");

		Composite composite = new Composite(groupStamp, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(composite);
		composite.setLayoutData(gdComposite1);

		final Combo c = new Combo(composite, SWT.READ_ONLY);
		String items[] = { "deu ( de-DE )", "eng ( uk-UK) " };
		c.setItems(items);
		int lang_id = TaskProperties.getTaskProperties().getLanguage() - 1;
		c.select(lang_id);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(false, false).span(1, 1).hint(100, 16).applyTo(c);

		return c;
	}

	private Text createOperator(Group groupStamp) {
		GridData gdComposite1 = new GridData();
		gdComposite1.grabExcessHorizontalSpace = true;
		gdComposite1.grabExcessVerticalSpace = false;
		gdComposite1.horizontalAlignment = SWT.FILL;
		gdComposite1.verticalAlignment = SWT.FILL;
		gdComposite1.widthHint = 550;
		gdComposite1.horizontalSpan = 3;

		new Label(groupStamp, SWT.NONE).setText("Operator");

		Composite composite = new Composite(groupStamp, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(composite);
		composite.setLayoutData(gdComposite1);

		final Text operator = new Text(composite, SWT.BORDER | SWT.SINGLE);
		operator.setText(String.valueOf(TaskProperties.getTaskProperties()
				.getOperator()));
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(false, false).span(1, 1).hint(100, 16).applyTo(operator);

		return operator;
	}

	private Text createCityText(final Group groupStamp) {

		GridData gdComposite1 = new GridData();
		gdComposite1.grabExcessHorizontalSpace = true;
		gdComposite1.grabExcessVerticalSpace = false;
		gdComposite1.horizontalAlignment = SWT.FILL;
		gdComposite1.verticalAlignment = SWT.FILL;
		gdComposite1.widthHint = 550;
		gdComposite1.horizontalSpan = 3;

		new Label(groupStamp, SWT.NONE).setText("City ID");

		Composite composite = new Composite(groupStamp, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(composite);
		composite.setLayoutData(gdComposite1);
		// GridDataFactory.fillDefaults().span(3, 1).align(SWT.FILL,
		// SWT.BEGINNING).grab(true, false).hint(550, 1).applyTo(composite);

		final Text cityText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(false, false).hint(100, 16).applyTo(cityText);

		Button buttonSearch = new Button(composite, SWT.PUSH | SWT.LEFT);
		buttonSearch.setText("Search city");
		buttonSearch.addSelectionListener(new SearchCitySelectionListener(
				this.serverUrl, this.operator, this.languageList, composite
						.getShell(), cityText));

		// new Label(composite, SWT.NONE).setText("( 3586 for SIXT Truck ) ");

		return cityText;
	}

	private Text createAptText(Group groupStamp) {

		GridData gdComposite1 = new GridData();
		gdComposite1.grabExcessHorizontalSpace = true;
		gdComposite1.grabExcessVerticalSpace = false;
		gdComposite1.horizontalAlignment = SWT.FILL;
		gdComposite1.verticalAlignment = SWT.FILL;
		gdComposite1.widthHint = 550;
		gdComposite1.horizontalSpan = 3;

		new Label(groupStamp, SWT.NONE).setText("APT CODE");

		Composite composite = new Composite(groupStamp, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(composite);
		composite.setLayoutData(gdComposite1);

		final Text aptText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(false, false).hint(100, 16).applyTo(aptText);

		Button buttonSearch = new Button(composite, SWT.PUSH | SWT.LEFT);
		buttonSearch.setText("Search airport");
		buttonSearch.addSelectionListener(new SearchCitySelectionListener(
				this.serverUrl, this.operator, this.languageList, composite
						.getShell(), aptText));

		aptText.setText(TaskProperties.getTaskProperties().getAptCode());
		return aptText;
	}
	
	private void showVehicleResponse(VehicleResponse response) {
		pageInfo.setText(response.getPageInfo());

		// sessionId.setText(String.valueOf(response.getRequestId()));

		requestId.setText(String.valueOf(response.getRequestId()));

		setSummary(response.getSummary());

		contact.setText(" get Contact from response");
		
		if (response.getOfferFilterTemplate() != null) {
			String offerFilter = response.getOfferFilterTemplate().toString();
			
			offerFilterTemlate.setText(offerFilter);
		}

		// Table
		SunnyModelProvider.INSTANCE.updateOffers(response);
		viewer.setInput(SunnyModelProvider.INSTANCE.getOfferDos());
		viewer.refresh();
		
		SunnyModelProvider.INSTANCE.updateRecmmendations(response);
		recommendationTable.setInput(SunnyModelProvider.INSTANCE.getRecommendations());
		recommendationTable.refresh();
	}

	private void setSummary(VehicleSummary vehicleSummary) {
		StringBuilder sb = new StringBuilder();
		sb.append("Offers on the page : "+vehicleSummary.getQuantityOffers());
		sb.append("/ Offers total : "+vehicleSummary.getTotalQuantityOffers());
		
		summary.setText(sb.toString());

	}
	

}
