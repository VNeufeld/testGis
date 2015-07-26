package com.dev.gis.app.view.elements;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.service.prefs.BackingStoreException;

public class BasicControl {
	private static String PREFERENCE_PATH = "CONTROL_PREFERENCE";

	protected String readProperty(String property) {
		final IEclipsePreferences preferences = ConfigurationScope.INSTANCE
				.getNode(PREFERENCE_PATH);
		String value = preferences.get(property,"");
		return value;

	}
	
	protected void saveProperty(String property, String value) {
		final IEclipsePreferences preferences = ConfigurationScope.INSTANCE
				.getNode(PREFERENCE_PATH);
		preferences.put(property, value);

		try {
			preferences.flush();
		} catch (BackingStoreException e1) {
		}

	}
	
}
