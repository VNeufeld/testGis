package com.dev.gis.app.taskmanager.clubMobilView.defects;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.bpcs.mdcars.model.DefectLocation;
import com.bpcs.mdcars.model.DefectLocationClass;
import com.bpcs.mdcars.model.DefectPart;
import com.dev.gis.connector.api.ClubMobilModelProvider;

public class SelectDefectPartsComboBox extends DynamicComboBox{
	
	private static Logger logger = Logger.getLogger(SelectDefectPartsComboBox.class);
	
	private int[] dynValues ;
	
	public SelectDefectPartsComboBox(Composite parent, int size) {
		super(parent, size, true);
		
	}
	@Override
	protected String[] createDynamicItems() {
		String[] dynItems = new String[ 1];
		dynItems[0] = "nothing";
		dynValues = new int[ 1];
		dynValues[0] = 0;

		try {
			if ( ClubMobilModelProvider.INSTANCE.defectListResponse != null) {
				List<DefectPart>  locClasses = ClubMobilModelProvider.INSTANCE.defectListResponse.getDefectParts();
				int lSize = locClasses.size();
				logger.info("createDynamicItems = " + lSize);
				
				dynItems = new String[lSize];
				dynValues = new int[lSize];
				for ( int i = 0; i < lSize; i++) {
					if (locClasses.get(i).getDescription() != null ) { 
						dynItems[i] = locClasses.get(i).getDescription();
						dynValues[i] = locClasses.get(i).getId();
					}
					else
						dynItems[i] = " NOP";
				}
			}
	
		}
		catch(Exception err) {
			logger.error(err.getMessage(),err);
		}
		return dynItems;
	}

	@Override
	public int getSelectedValue() {
		int selectedValueIndex = super.getSelectedValue();
		if ( selectedValueIndex < dynValues.length)
			return dynValues[selectedValueIndex];
		return 0;
	}


	@Override
	protected String getLabel() {
		return "Defect Parts :";
	}
	protected String getPropertyName() {
		return "NO";
	}

}
