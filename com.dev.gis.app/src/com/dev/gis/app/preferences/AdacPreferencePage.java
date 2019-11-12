package com.dev.gis.app.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

import com.dev.gis.app.Activator;
import com.dev.gis.app.taskmanager.testAppView.AdacOperatorComboBox;
import com.dev.gis.connector.api.TaskProperties;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class AdacPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public AdacPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("JOI preference page");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(new DirectoryFieldEditor(PreferenceConstants.P_PATH, 
				"&Directory preference:", getFieldEditorParent()));
		addField(
			new BooleanFieldEditor(
				PreferenceConstants.P_BOOLEAN,
				"&An example of a boolean preference",
				getFieldEditorParent()));

		addField(new RadioGroupFieldEditor(
				PreferenceConstants.P_CHOICE,
			"An example of a multiple-choice preference",
			1,
			new String[][] { { "&Choice 1", "choice1" }, {
				"C&hoice 2", "choice2" }
		}, getFieldEditorParent()));
		
		addField(
			new StringFieldEditor(PreferenceConstants.P_STRING, "A &text preference:", getFieldEditorParent()));

		addField(
				new StringFieldEditor(PreferenceConstants.P_STRING, "A &text preference:", getFieldEditorParent()));
		
		addField(new StringFieldEditor(PreferenceConstants.ADAC_URL, "ADAC_URL:", getFieldEditorParent()));

		addField(new StringFieldEditor(PreferenceConstants.ADAC_OPERATORS, "ADAC_Operators:", getFieldEditorParent()));
		
		addField(new BooleanFieldEditor(PreferenceConstants.P_USE_DUMMY,"&Use Dummy",getFieldEditorParent()));
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	@Override
	public boolean performOk() {
		
		if ( super.performOk() ) {
			setValues();
			
			return true;
		}
		return false;
		
	}

	private void setValues() {
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		
	    String url = preferenceStore.getString(PreferenceConstants.ADAC_URL);		
		TaskProperties.getTaskProperties().setServerProperty(url);

		TaskProperties.getTaskProperties().setUseDummy(preferenceStore.getBoolean(PreferenceConstants.P_USE_DUMMY));

		TaskProperties.getTaskProperties().setAdacOperators(preferenceStore.getString(PreferenceConstants.ADAC_OPERATORS));
		
		AdacOperatorComboBox.createOperator();
		
		TaskProperties.getTaskProperties().saveProperty();
	}

	@Override
	protected void performApply() {
		super.performApply();
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
	}
	
}