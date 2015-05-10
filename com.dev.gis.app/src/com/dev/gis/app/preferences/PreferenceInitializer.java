package com.dev.gis.app.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.dev.gis.app.Activator;
import com.dev.gis.connector.api.TaskProperties;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_BOOLEAN, true);
		store.setDefault(PreferenceConstants.P_CHOICE, "choice2");
		store.setDefault(PreferenceConstants.P_STRING,"Default value");
		
		store.setDefault(PreferenceConstants.P_URL,"http://127.0.0.1:8080/sunny-joi/joi");
		store.setDefault(PreferenceConstants.P_USE_DUMMY, false);
	}

}
