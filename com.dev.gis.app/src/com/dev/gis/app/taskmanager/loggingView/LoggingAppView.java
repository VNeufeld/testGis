package com.dev.gis.app.taskmanager.loggingView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.app.taskmanager.bookingView.BookingView;
import com.dev.gis.app.taskmanager.offerDetailView.OfferViewUpdater;
import com.dev.gis.connector.joi.protocol.Extra;
import com.dev.gis.connector.joi.protocol.Offer;
import com.dev.gis.connector.joi.protocol.VehicleResponse;
import com.dev.gis.task.execution.api.ITaskResult;
import com.dev.gis.task.execution.api.ModelProvider;
import com.dev.gis.task.execution.api.OfferDo;

public class LoggingAppView extends TaskViewAbstract {
	public static final String ID = "com.dev.gis.app.task.LoggingAppView";
	
	private final static Logger logger = Logger.getLogger(LoggingAppView.class);
	
	Calendar loggingFromDate = Calendar.getInstance();
	Calendar loggingToDate = Calendar.getInstance();

	private IWorkbenchAction exitAction;
	
	private static int instanceNum = 1;

	
	private Text logFileText;
	private Text logDirText;
	private Text outputDirText;
	private Text sessionIdText;
	private Text bookingIdText;
	private Text maxFileSizeText;
	private Button buttonSession;
	private ProgressBar pb;

	private Text currentFileName;
	
	private TableViewer viewer;
	
	@Override
	public void createPartControl(final Composite parent) {
		
		final Group group = new Group(parent, SWT.TITLE);
		group.setText("Check Logging:");
		group.setLayout(new GridLayout(1, false));

		createGroupFiles(group);

		createGroupSearch(group);

		createButtons(group);

		createOutputText(group);
		
	}
	

	private void createOutputText(Group group) {
		GridData gdDateSession = new GridData();
		gdDateSession.grabExcessHorizontalSpace = false;
		gdDateSession.horizontalAlignment = SWT.NONE;
		gdDateSession.horizontalSpan = 3;
		gdDateSession.widthHint = 800;

		
		currentFileName = new Text(group, SWT.BORDER | SWT.SINGLE);
		currentFileName.setLayoutData(gdDateSession);
		
		pb = new  ProgressBar(group, SWT.NULL);
		pb.setSelection(0);
		pb.setLayoutData(gdDateSession);

		
	}


	private void createGroupFiles(Group group) {
		final Group groupFiles = new Group(group, SWT.TITLE);
		groupFiles.setText("Files and Dirs:");
		groupFiles.setLayout(new GridLayout(1, false));
		
		
		Composite composite = new Composite(groupFiles, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(false).applyTo(composite);

		createSelectDirComposite(composite);

		createSelectFilesComposite(composite);
		createMaxFileSizeComposite(composite);

		createOutputDirComposite(composite);

		
	}




	private void createGroupSearch(Group group) {
		final Group groupSearch = new Group(group, SWT.TITLE);
		groupSearch.setText("Search criteria:");
		groupSearch.setLayout(new GridLayout(1, false));

		Composite composite = new Composite(groupSearch, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(false).applyTo(composite);

		createSessionComposite(composite);

		createBookingComposite(composite);
		
		createDates(composite);
		
	}
	
	private void createButtons(Composite parent) {
		
		GridData gdButton = new GridData();
		gdButton.grabExcessHorizontalSpace = false;
		gdButton.horizontalAlignment = SWT.NONE;
		gdButton.widthHint = 150;


		GridData gdDate = new GridData();
		gdDate.grabExcessHorizontalSpace = false;
		gdDate.horizontalAlignment = SWT.NONE;
		gdDate.horizontalSpan = 3;

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(gdDate);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(composite);


		buttonSession = new Button(composite, SWT.PUSH | SWT.CENTER);
		buttonSession.setText("Split to session");
		buttonSession.setLayoutData(gdButton);
		
		final Button buttonSplit = new Button(composite, SWT.PUSH | SWT.CENTER | SWT.COLOR_BLUE);
		buttonSplit.setText("Split to Files");
		buttonSplit.setLayoutData(gdButton);


		buttonSession.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				saveInput();
				
				buttonSession.setEnabled(false);
				buttonSession.setText(" Wait ....");
				
				SplittFileToSession splittFileToSession = new SplittFileToSession(logFileText.getText(),
						logDirText.getText(),outputDirText.getText(), 
						sessionIdText.getText(),bookingIdText.getText(),
						maxFileSizeText.getText(),
						loggingFromDate,loggingToDate);
				
				ExecutorService executor = Executors.newSingleThreadExecutor();
				executor.submit(splittFileToSession);
				
				logger.info(" executer started ");
	
			}
			

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		
		buttonSplit.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				
				saveInput();

				File file = new File ( logFileText.getText());
				logger.info("  size = "+FileUtils.sizeOf(file));

				int countLines = SplitFile.splitFileOld(file);
				
				logger.info(" lines  = "+ countLines );
				
			}


			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
	}
	
	private void saveInput() {
		LoggingSettings.saveProperty(LoggingSettings.PREFERENCE_MAX_FILE_SIZE_PROPERTY,maxFileSizeText.getText());
		
		LoggingSettings.saveTimeProperty(loggingFromDate, loggingFromDate);

		LoggingSettings.saveProperty(LoggingSettings.PREFERENCE_INPUT_DIR_PROPERTY,logDirText.getText());

		LoggingSettings.saveProperty(LoggingSettings.PREFERENCE_SESSION_PROPERTY,sessionIdText.getText());
		
		
	}

	
	private void createBookingComposite(Composite composite) {
		GridData gdDateSession = new GridData();
		gdDateSession.grabExcessHorizontalSpace = false;
		gdDateSession.horizontalAlignment = SWT.NONE;
		gdDateSession.horizontalSpan = 2;
		gdDateSession.widthHint = 300;

		new Label(composite, SWT.NONE).setText("BookingId");
		
		bookingIdText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		bookingIdText.setLayoutData(gdDateSession);
		
	}


	private void createSessionComposite(Composite composite) {
		
		String saved = LoggingSettings.readProperty(LoggingSettings.PREFERENCE_SESSION_PROPERTY,"0");

		GridData gdDateSession = new GridData();
		gdDateSession.grabExcessHorizontalSpace = false;
		gdDateSession.horizontalAlignment = SWT.NONE;
		gdDateSession.horizontalSpan = 2;
		gdDateSession.widthHint = 300;

		new Label(composite, SWT.NONE).setText("SessionId");
		
		sessionIdText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		sessionIdText.setLayoutData(gdDateSession);
		sessionIdText.setText(saved);
		
	}
	
	private void createMaxFileSizeComposite(Composite parent) {

		String maxFileSizeSaved = LoggingSettings.readProperty(LoggingSettings.PREFERENCE_MAX_FILE_SIZE_PROPERTY,"100");


		new Label(parent, SWT.NONE).setText("maxFileSize");
		
		GridData gdTextComposite = new GridData();
		gdTextComposite.grabExcessHorizontalSpace = false;
		gdTextComposite.horizontalAlignment = SWT.NONE;
		gdTextComposite.horizontalSpan = 2;
		
		Composite textComposite = new Composite(parent, SWT.NONE);
		textComposite.setLayoutData(gdTextComposite);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(textComposite);

		GridData gdMaxFileSize = new GridData();
		gdMaxFileSize.grabExcessHorizontalSpace = false;
		gdMaxFileSize.horizontalAlignment = SWT.NONE;
		gdMaxFileSize.widthHint = 150;
		
		maxFileSizeText = new Text(textComposite, SWT.BORDER | SWT.SINGLE);
		maxFileSizeText.setLayoutData(gdMaxFileSize);
		
		maxFileSizeText.setText(maxFileSizeSaved);

		new Label(textComposite, SWT.NONE).setText("( mB )");
		
	}


	private void createDates(Composite parent) {

		Calendar[] cals = LoggingSettings.readTimeProperty();
		
		//initDates();
		loggingFromDate = cals[0];
		loggingToDate = cals[1];
		
		addDateControl("fromDate:",parent,loggingFromDate);

		addDateControl("toDate:",parent,loggingToDate);

	}

	private void createSelectFilesComposite(final Composite parent) {
		
		
		Label label = new Label(parent, SWT.NONE);
		label.setText("LogFile");
		logFileText = new Text(parent, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).hint(600, 16).grab(false, false).applyTo(logFileText);

		final Button fileDialog = new Button(parent, SWT.PUSH | SWT.LEFT);
		fileDialog.setText("Select File");

		fileDialog.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				 FileDialog fileDialog = new FileDialog(parent.getShell());
				    // Set the text
				 fileDialog.setText("Select Log. File");				
				 logFileText.setText(fileDialog.open());

			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
	}

	private void createSelectDirComposite(final Composite composite) {

		String savedDir = LoggingSettings.readProperty(LoggingSettings.PREFERENCE_INPUT_DIR_PROPERTY,"");

		Label logDirLabel = new Label(composite, SWT.NONE);
		logDirLabel.setText("LogDir");
		
		logDirText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).hint(400, 16).grab(false, false).applyTo(logDirText);

		final Button dirDialogBtn = new Button(composite, SWT.PUSH | SWT.LEFT);
		dirDialogBtn.setText("Select DIR");
		
		logDirText.setText(savedDir);

		
		dirDialogBtn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dirDialog = new DirectoryDialog(composite.getShell());
				    // Set the text
				dirDialog.setText("Select logging dir");		


				// Set the text
				if ( StringUtils.isNotBlank(logDirText.getText()))
					dirDialog.setFilterPath(logDirText.getText());

				String dir = dirDialog.open();
				
				if (StringUtils.isNotEmpty(dir) )
					logDirText.setText(dir);
				
				LoggingSettings.saveProperty(LoggingSettings.PREFERENCE_INPUT_DIR_PROPERTY,logDirText.getText());
				

			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
				
	}
	
	private void createOutputDirComposite(final Composite composite) {
		
		String savedDir = LoggingSettings.readProperty(LoggingSettings.PREFERENCE_OUTPUT_DIR_PROPERTY,"");
		
		Label logDirLabel = new Label(composite, SWT.NONE);
		logDirLabel.setText("OutputDir");
		
		outputDirText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).hint(400, 16).grab(false, false).applyTo(outputDirText);

		final Button dirDialogBtn = new Button(composite, SWT.PUSH | SWT.LEFT);
		dirDialogBtn.setText("Select DIR");
		
		outputDirText.setText(savedDir);
		
		dirDialogBtn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dirDialog = new DirectoryDialog(composite.getShell());
				
				// Set the text
				if ( StringUtils.isNotBlank(outputDirText.getText()))
					dirDialog.setFilterPath(outputDirText.getText());
				
				dirDialog.setText("Select output dir");				

				String dir = dirDialog.open();
				
				if (StringUtils.isNotEmpty(dir) )
					outputDirText.setText(dir);
				
				LoggingSettings.saveProperty(LoggingSettings.PREFERENCE_OUTPUT_DIR_PROPERTY,outputDirText.getText());

			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
	}

	
	private void addDateControl(String label,Composite parent,final Calendar resultDate ) {
		

		GridData gdDate = new GridData();
		gdDate.grabExcessHorizontalSpace = false;
		gdDate.horizontalAlignment = SWT.NONE;
		gdDate.widthHint = 100;

		new Label(parent, SWT.NONE).setText(label);

		final DateTime dateTime = new DateTime(parent, SWT.DATE);
		dateTime.setLayoutData(gdDate);
		
		final DateTime dateTime2 = new DateTime(parent, SWT.TIME);
		dateTime2.setLayoutData(gdDate);
		
		dateTime.setYear(resultDate.get(Calendar.YEAR));
		dateTime.setMonth(resultDate.get(Calendar.MONTH));
		dateTime.setDay(resultDate.get(Calendar.DAY_OF_MONTH));
		
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




	protected void changeModel(VehicleResponse response) {
		ModelProvider.INSTANCE.updateOffers(response);
	    //viewer.setInput(response.getResultList());
	    viewer.setInput(ModelProvider.INSTANCE.getOfferDos());
	
	}

	private void initDates() {
		
		loggingFromDate.set(Calendar.HOUR_OF_DAY, 11);
		loggingFromDate.set(Calendar.MINUTE, 30);
		loggingFromDate.set(Calendar.SECOND, 0);

		loggingToDate.set(Calendar.HOUR_OF_DAY, 18);
		loggingToDate.set(Calendar.MINUTE, 0);
		loggingToDate.set(Calendar.SECOND, 0);	
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
 
	        new OfferViewUpdater().showOffer(offer);
	        System.out.println("selectedNode "+offer);
	        
		}
		
	}
	
	private void createColumns(final Composite parent, final TableViewer viewer) {
	    String[] titles = { "Name", "Supplier", "Station", "Price" };
	    int[] bounds = { 100, 100, 100, 100 };

	    // first column is for the first name
	    TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	OfferDo o = (OfferDo) element;
	        return o.getName();
	      }
	    });

	    // second column is supplierId
	    col = createTableViewerColumn(titles[1], bounds[1], 1);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	OfferDo o = (OfferDo) element;
	        return String.valueOf(o.getSupplierId());
	      }
	    });

	    col = createTableViewerColumn(titles[2], bounds[2], 2);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	OfferDo o = (OfferDo) element;
			return String.valueOf(o.getPickUpStationId());
	      }
	    });

	    col = createTableViewerColumn(titles[3], bounds[3], 3);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	OfferDo o = (OfferDo) element;
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
	
	
	public static void  updateView(final String text ) {
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					// Show protocol, show results
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					LoggingAppView viewPart =  (LoggingAppView)wp.showView(
							LoggingAppView.ID, 
							Integer.toString(instanceNum), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					viewPart.outputText(text);
					
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

		
	}


	protected void outputText(String text) {
		if ( "exit".equals(text)) {
			buttonSession.setEnabled(true);
			buttonSession.setText("Split to session");
			currentFileName.setText(" completed ");
		}
		else
			currentFileName.setText(text);
	}



}
