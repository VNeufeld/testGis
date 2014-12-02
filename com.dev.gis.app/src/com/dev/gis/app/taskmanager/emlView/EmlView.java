package com.dev.gis.app.taskmanager.emlView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.app.taskmanager.offerDetailView.OfferViewUpdater;
import com.dev.gis.task.execution.api.ITaskResult;
import com.dev.gis.task.execution.api.OfferDo;

public class EmlView extends TaskViewAbstract {
	public static final String ID = "com.dev.gis.app.task.EmlView";
	
	private final static Logger logger = Logger.getLogger(EmlView.class);
	
	
	private static int instanceNum = 1;
	
	private Text logFileText;
	private Text outputDirText;
	private Text maxFileSizeText;
	
	private Button buttonSplit;
	private Button buttonStop;
	
	private ProgressBar pb;

	private Text currentFileName;
	
	private TableViewer viewer;
	
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	
	EmlService sevice = null;

	
	@Override
	public void createPartControl(final Composite parent) {
		
		final Group group = new Group(parent, SWT.TITLE);
		group.setText("Logging manager");
		group.setLayout(new GridLayout(1, false));

		createGroupFiles(group);

		//createGroupSearch(group);

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

		//createSelectDirComposite(composite);

		createSelectFilesComposite(composite);
		createMaxFileSizeComposite(composite);

		createOutputDirComposite(composite);
		
	}

	
	private void createButtons(Composite parent) {

		GridData gdDate = new GridData();
		gdDate.grabExcessHorizontalSpace = false;
		gdDate.horizontalAlignment = SWT.NONE;
		gdDate.horizontalSpan = 3;

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(gdDate);
		GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(false).applyTo(composite);

		//addButtonSession(composite);
		
		addButtonSplitt(composite);

		
	}






	private void addButtonSplitt(final Composite composite) {

		GridData gdButton = new GridData();
		gdButton.grabExcessHorizontalSpace = false;
		gdButton.horizontalAlignment = SWT.NONE;
		gdButton.widthHint = 150;

		buttonSplit = new Button(composite, SWT.PUSH | SWT.CENTER | SWT.COLOR_BLUE);
		buttonSplit.setText("create EML");
		buttonSplit.setLayoutData(gdButton);
		
		buttonSplit.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if ( !checkInputs(logFileText,outputDirText,maxFileSizeText))
					return;
				
				saveInput();
			    File[] attachments = new File[] { new File("H:\\_TRANSFER\\neu\\pom.xml"), new File("H:\\_TRANSFER\\neu\\Avis_fleet280414.xls") };

				   String to = "abcd@gmail.com";

				      // Sender's email ID needs to be mentioned
				      String from = "web@gmail.com";
					
					
					 try {
						createMessage(to, from, "test", " body", attachments);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
//				sevice = new EmlService(logFileText.getText(),outputDirText.getText(), 
//						maxFileSizeText.getText());
//				
//				executor.submit(sevice);
				
				
				logger.info(" executer started ");
				

				
			}
			
			// http://blog.smartbear.com/how-to/how-to-send-email-with-embedded-images-using-java/
			private void createMessage(String to, String from, String subject, String body, File[] attachments) throws Exception{
		    	  // Get system properties
		        Properties properties = System.getProperties();

		        
		        // Setup mail server
		        //properties.setProperty("mail.smtp.host", host);

		        // Get the default Session object.
		        Session session = Session.getDefaultInstance(properties,null);	    	
		    	
		        MimeMessage message = new MimeMessage(Session.getInstance(System.getProperties(),null));
//		        message.setFrom(new InternetAddress(from));
//		        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		        message.setSubject(subject);
		        // create the message part 
		        MimeBodyPart content = new MimeBodyPart();
		        // fill message
		        content.setText(body);
		        MimeMultipart multipart = new MimeMultipart();
		        multipart.addBodyPart(content);
		        
//		        MimeBodyPart imagePart = new MimeBodyPart();
//
//		        imagePart.attachFile("resources/teapot.jpg");
		        
//		        multipart.addBodyPart(imagePart);
		        
//		        // add attachments
		        for(File file : attachments) {
		            MimeBodyPart attachment = new MimeBodyPart();
		            attachment.attachFile(file);
//		            FileDataSource source = new FileDataSource(file);
//		            attachment.setDataHandler(new DataHandler(source));
//		            attachment.setFileName(file.getName());
		            multipart.addBodyPart(attachment);
		        }
//		        // integration
		        message.setContent(multipart);
		        // store file
		        message.writeTo(new FileOutputStream(new File("c:/temp/mail.eml")));
		        
		}



			private boolean checkInputs(Text logFileText, Text outputDirText,
					Text maxFileSizeText) {
				File f = new File(outputDirText.getText());
				if ( !f.exists() || !f.isDirectory()) {
					   MessageBox messageBox = new MessageBox(logFileText.getShell(), SWT.ICON_ERROR | SWT.OK);
			           messageBox.setText("Error");
				       messageBox.setMessage("Directory "+ outputDirText.getText() + " not exists!");
				       messageBox.open();					
					return false;
				}
				f = new File(logFileText.getText());
				if ( f.exists() && f.canRead() && f.isFile())
					return true;
				
				else {
				   MessageBox messageBox = new MessageBox(logFileText.getShell(), SWT.ICON_ERROR | SWT.OK);
		           messageBox.setText("Error");
			       messageBox.setMessage("File "+ logFileText.getText() + " not exists!");
			       messageBox.open();					
				}
				return false;
			}


			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		
	}



	
	private void saveInput() {
		LoggingSettings.saveProperty(LoggingSettings.PREFERENCE_MAX_FILE_SIZE_PROPERTY,maxFileSizeText.getText());
		
		LoggingSettings.saveProperty(LoggingSettings.PREFERENCE_INPUT_FILE_PROPERTY,logFileText.getText());
		
		
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

	private void createSelectFilesComposite(final Composite parent) {
		String saved = LoggingSettings.readProperty(LoggingSettings.PREFERENCE_INPUT_FILE_PROPERTY,"");

		
		Label label = new Label(parent, SWT.NONE);
		label.setText("LogFile");
		logFileText = new Text(parent, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).hint(600, 16).grab(false, false).applyTo(logFileText);

		final Button fileDialog = new Button(parent, SWT.PUSH | SWT.LEFT);
		fileDialog.setText("Select File");
		
		logFileText.setText(saved);


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
					EmlView viewPart =  (EmlView)wp.showView(
							EmlView.ID, 
							Integer.toString(instanceNum), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					viewPart.outputText(text);
					
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

		
	}
	
	public static void  updateProgressBar(final long size, final long maxSize ) {
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					// Show protocol, show results
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					EmlView viewPart =  (EmlView)wp.showView(
							EmlView.ID, 
							Integer.toString(instanceNum), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					viewPart.updateProgressb(size, maxSize);
					
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

		
	}

	protected void updateProgressb(final long size, final long maxSize) {
		int imax = (int) ( maxSize / 1000 );
		int isize = (int) ( size / 1000 );
		pb.setMaximum(imax);
		pb.setSelection(isize);

	}


	protected void outputText(String text) {
		if ( "exit".equals(text) || "canceled".equals(text)) {
			
			buttonSplit.setText("Split to Files");
			buttonSplit.setEnabled(true);
			
			if ("exit".equals(text) )
				currentFileName.setText(" completed ");
			else
				currentFileName.setText(" canceled");
			
			
			buttonStop.setEnabled(false);

			
		}
		else
			currentFileName.setText(text);
	}



}
