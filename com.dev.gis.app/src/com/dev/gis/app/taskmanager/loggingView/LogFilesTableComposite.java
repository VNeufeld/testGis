package com.dev.gis.app.taskmanager.loggingView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPartSite;

import com.dev.gis.app.task.model.FileNameEntryModel;
import com.dev.gis.app.taskmanager.loggingView.service.FindFilesService;

public class LogFilesTableComposite {

	private final static Logger logger = Logger.getLogger(LogFilesTableComposite.class);

	private Text filesCount;
	private LogFilesTable logFilesTable;
	private final IWorkbenchPartSite partSite;
	private final Text logDirText;
	
	private final SearchCriteriaComposite searchCriteriaComposite;

	private ExecutorService executor = Executors.newSingleThreadExecutor();


	public LogFilesTableComposite(Text logDirText,
			final SearchCriteriaComposite searchCriteriaComposite,
			IWorkbenchPartSite iWorkbenchPartSite) {
		this.searchCriteriaComposite = searchCriteriaComposite;
		this.partSite = iWorkbenchPartSite;
		this.logDirText = logDirText;
		
	}


	public void create(Composite parent) {

		GridData gdComposite2 = new GridData();
		gdComposite2.grabExcessHorizontalSpace = true;
		gdComposite2.grabExcessVerticalSpace = false;
		gdComposite2.horizontalAlignment = SWT.FILL;
		gdComposite2.verticalAlignment = SWT.FILL;
		//gdComposite2.widthHint = 800;


		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(composite);
		composite.setLayoutData(gdComposite2);
		
		
		addFilesTable(composite);
	}
	
	
	private void addFilesTable(Composite compositeSearch) {
		final Group groupTable = new Group(compositeSearch, SWT.TITLE);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(groupTable);
		
		GridData gridDataGroupTable = new GridData();
		gridDataGroupTable.verticalAlignment = GridData.FILL;
		gridDataGroupTable.horizontalAlignment = GridData.FILL;
//		gridData.heightHint = 200;
		gridDataGroupTable.widthHint  = 900;
		gridDataGroupTable.horizontalSpan = 1;
		gridDataGroupTable.grabExcessHorizontalSpace = false;
		gridDataGroupTable.grabExcessVerticalSpace = true;

//		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(false, true).applyTo(groupTable);
		groupTable.setText("File list:");
		groupTable.setLayoutData(gridDataGroupTable);
		

		// Composite for row with button and text 

		Composite compositeFileSearch = new Composite(groupTable, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(compositeFileSearch);

		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.horizontalSpan = 2;
		compositeFileSearch.setLayoutData(gridData2);

		addSearchFilesButton(compositeFileSearch);
		
		filesCount = new Text(compositeFileSearch, SWT.BORDER | SWT.SINGLE);
		filesCount.setEnabled(false);
		filesCount.setText("files : 0");

		Composite compositeTable = new Composite(groupTable, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(false).applyTo(compositeTable);
		
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.heightHint = 220;
		gridData.widthHint  = 900;
		gridData.horizontalSpan = 1;
		gridData.grabExcessHorizontalSpace = true;
		//gridData.grabExcessVerticalSpace = true;
		compositeTable.setLayoutData(gridData);

		logFilesTable = new LogFilesTable(compositeTable, partSite);
	}

	private void addSearchFilesButton(Composite parent) {
		GridData gdButton = new GridData();
		gdButton.grabExcessHorizontalSpace = false;
		gdButton.horizontalAlignment = SWT.NONE;
		gdButton.widthHint = 150;
		
		final Button dirDialogBtn = new Button(parent, SWT.PUSH | SWT.CENTER);
		dirDialogBtn.setText("Show files");
		dirDialogBtn.setLayoutData(gdButton);
		
		dirDialogBtn.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				FileNameEntryModel.getInstance().getEntries().clear();
				logFilesTable.update();
				
				FindFilesService ff = new FindFilesService(logDirText.getText(),searchCriteriaComposite.filePattern.getText(),
						searchCriteriaComposite.loggingFromDate,searchCriteriaComposite.loggingToDate);
				
				executor.submit(ff);
				
				logger.info(" executer started ");
				
	
			}
			

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		
	}


	public void update() {
		logFilesTable.update();
		filesCount.setText("files :"+ FileNameEntryModel.getInstance().getEntries().size());
	}

}
