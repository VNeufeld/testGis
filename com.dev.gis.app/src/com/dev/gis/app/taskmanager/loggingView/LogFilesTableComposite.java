package com.dev.gis.app.taskmanager.loggingView;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPartSite;

import com.dev.gis.app.task.model.FileNameEntryModel;
import com.dev.gis.app.view.elements.BasicControl;

public class LogFilesTableComposite extends BasicControl{

	private Text filesCount;
	private LogFilesTable logFilesTable;
	private final IWorkbenchPartSite partSite;

	public LogFilesTableComposite(IWorkbenchPartSite iWorkbenchPartSite) {
		this.partSite = iWorkbenchPartSite;
	}

	public void create(Composite parent) {
		GridData gdComposite2 = new GridData();
		gdComposite2.grabExcessHorizontalSpace = true;
		gdComposite2.grabExcessVerticalSpace = false;
		gdComposite2.horizontalAlignment = SWT.FILL;
		gdComposite2.verticalAlignment = SWT.FILL;


		Composite composite = new Composite(parent, SWT.RIGHT);
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
		gridDataGroupTable.grabExcessHorizontalSpace = true;
		gridDataGroupTable.grabExcessVerticalSpace = true;

		groupTable.setText("File list:");
		groupTable.setLayoutData(gridDataGroupTable);
		

		// Composite for row with button and text 

		Composite compositeFileSearch = new Composite(groupTable, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(compositeFileSearch);

		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.horizontalSpan = 2;
		compositeFileSearch.setLayoutData(gridData2);
		
		filesCount = new Text(compositeFileSearch, SWT.BORDER | SWT.SINGLE);
		filesCount.setEnabled(false);
		filesCount.setText("files :        0");

		Composite compositeTable = new Composite(groupTable, SWT.RIGHT);
		GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(false).applyTo(compositeTable);
		
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.heightHint = 320;
		//gridData.widthHint  = 900;
		//gridData.horizontalSpan = 1;
		gridData.grabExcessHorizontalSpace = true;
		//gridData.grabExcessVerticalSpace = true;
		compositeTable.setLayoutData(gridData);

		logFilesTable = new LogFilesTable(compositeTable, partSite);
	}

	public void update() {
		logFilesTable.update();
		filesCount.setText("files : "+ FileNameEntryModel.getInstance().getEntries().size()+ "   ");
	}

}
