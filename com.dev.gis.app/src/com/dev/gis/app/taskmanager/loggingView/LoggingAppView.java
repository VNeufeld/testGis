package com.dev.gis.app.taskmanager.loggingView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.LineIterator;
import org.apache.log4j.Logger;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.preference.StringFieldEditor;
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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.app.taskmanager.offerDetailView.OfferViewUpdater;
import com.dev.gis.connector.joi.protocol.VehicleResponse;
import com.dev.gis.task.execution.api.ITaskResult;
import com.dev.gis.task.execution.api.ModelProvider;
import com.dev.gis.task.execution.api.OfferDo;

public class LoggingAppView extends TaskViewAbstract {
	public static final String ID = "com.dev.gis.app.task.LoggingAppView";
	
	private Logger logger = Logger.getLogger(LoggingAppView.class);
	
	
	private StringFieldEditor city;
	private StringFieldEditor airport;
	
	Calendar checkInDate = Calendar.getInstance();
	Calendar dropOffDate = Calendar.getInstance();

	private IWorkbenchAction exitAction;
	
	private Text logFileText;
	private Text sessionIdText;

	 private TableViewer viewer;
	
	@Override
	public void createPartControl(final Composite parent) {
		
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
		//gdFirm.minimumWidth = 500;

		Label cityLabel = new Label(groupStamp, SWT.NONE);
		cityLabel.setText("LogFile");
		logFileText = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		logFileText.setLayoutData(gdFirm);
		
		final Button fileDialog = new Button(groupStamp, SWT.PUSH | SWT.LEFT);
		fileDialog.setText("File");
		
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


		
		new Label(groupStamp, SWT.NONE).setText("test");
		
		initDates();
		
		addDateControl("pickupDate:",groupStamp,checkInDate);

		addDateControl("dropOffDate:",groupStamp,dropOffDate);
		

		new Label(groupStamp, SWT.NONE).setText("SessionId");
		sessionIdText = new Text(groupStamp, SWT.BORDER | SWT.SINGLE);
		sessionIdText.setLayoutData(gdFirm);
		new Label(groupStamp, SWT.NONE).setText("1");
		new Label(groupStamp, SWT.NONE).setText("2");
		
		
		final Button buttonSession = new Button(groupStamp, SWT.PUSH | SWT.LEFT);
		buttonSession.setText("Split To session");
		
		buttonSession.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				File file = new File ( logFileText.getText());
				logger.info("  size = "+FileUtils.sizeOf(file));
				
				splitToSession(file,sessionIdText.getText());
	
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		

		
		final Button buttonSplit = new Button(groupStamp, SWT.PUSH | SWT.LEFT);
		buttonSplit.setText("Split");
		
		buttonSplit.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				
				
				
				File file = new File ( logFileText.getText());
				logger.info("  size = "+FileUtils.sizeOf(file));
				
				int count = 0;
				int size = 0;
				int counter = 1;
				int maxSize = 10000000;
				LineIterator li;
				List<String> rows = new ArrayList<String>();
				try {
					li = FileUtils.lineIterator(file);
					while ( li.hasNext()) {
						String s  = li.nextLine();
						rows.add(s);
						count++;
						size = size + s.length();
						if ( size > maxSize ) {
							writeFile(file,counter,rows);
							rows.clear();
							size = 0;
							counter++;
						}
						
					}
					
					writeFile(file,counter,rows);
					
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				logger.info(" lines  = "+ count );
				
				
//				InputStream fis = null;
//				 try {
//				   fis = new FileInputStream(file);
//				   InputStreamReader inR = new InputStreamReader(fis);
//				   BufferedReader buf = new BufferedReader( inR );
//				   String line;
//				   while ( ( line = buf.readLine() ) != null ) {
//				     System.out.println( line );
//				   }
//				 } catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} finally {
//					 try {
//						fis.close();
//					} catch (IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//				 }

				
//				changeModel(null);
//				viewer.refresh();
			}


			private void writeFile(File original, int counter,
					List<String> rows) throws IOException {
				
				String ext = FilenameUtils.getExtension(original.getAbsolutePath());
				String path = FilenameUtils.getFullPath(original.getAbsolutePath());
				String name = FilenameUtils.getBaseName(original.getAbsolutePath())+ "_" + counter + ".plog";
				
				String newFile = FilenameUtils.concat(path,name);

				File of = new File(newFile);
				
				FileUtils.writeLines(of, rows);
				
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
	
	protected void splitToSession(File file, String sessionId) {

		int count = 0;
		int size = 0;
		int counter = 1;
		int maxSize = 10000000;
		LineIterator li;
		List<String> rows = new ArrayList<String>();
		try {
			li = FileUtils.lineIterator(file);
			while ( li.hasNext()) {
				String s  = li.nextLine();
				if (s.contains(sessionId) )
				{
					rows.add(s);
					count++;
					size = size + s.length();
				}
				
			}
			
			writeFile(file,sessionId,rows);
			
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		logger.info(" lines  = "+ count );
		
	}

	private void writeFile(File original, String sessionId, List<String> rows) throws IOException {
		String ext = FilenameUtils.getExtension(original.getAbsolutePath());
		String path = FilenameUtils.getFullPath(original.getAbsolutePath());
		String name = FilenameUtils.getBaseName(original.getAbsolutePath())+ "_" + sessionId + ".plog";
		
		String newFile = FilenameUtils.concat(path,name);

		File of = new File(newFile);
		
		FileUtils.writeLines(of, rows);
		
	}

	protected void changeModel(VehicleResponse response) {
		ModelProvider.INSTANCE.updateOffers(response);
	    //viewer.setInput(response.getResultList());
	    viewer.setInput(ModelProvider.INSTANCE.getOfferDos());
	
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


}
