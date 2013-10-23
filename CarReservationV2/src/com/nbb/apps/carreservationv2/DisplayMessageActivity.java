package com.nbb.apps.carreservationv2;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.nbb.apps.carreservationv2.base.Hit;
import com.nbb.apps.carreservationv2.base.LocationSearchResult;
import com.nbb.apps.carreservationv2.conections.LocationSearchService;

public class DisplayMessageActivity extends Activity {
	private final String TAG = "DisplayMessageActivity";
	private SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_display_message);
		// Show the Up button in the action bar.
		setupActionBar();

		Intent intent = getIntent();
		String searchPattern = intent
				.getStringExtra(MainActivity.EXTRA_MESSAGE);

		Log.i(TAG, "onCreate : searchPattern =  " + searchPattern);

		preferences = PreferenceManager.getDefaultSharedPreferences(this);

		LocationSearchResult result = null;

		// isNetworkAvailable();
		
		URI uri = null;
		try {
			LocationSearchService locationSearchService = new LocationSearchService(
					preferences, searchPattern);
			uri = locationSearchService.getUri();
			result = locationSearchService.getLocation();

		} catch (URISyntaxException e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
			showError(uri, e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
			showError(uri, e.getMessage());
		} catch (InterruptedException e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
			showError(uri, e.getMessage());
		} catch (ExecutionException e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
			showError(uri, e.getMessage());
		} catch (TimeoutException e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
			showError(uri, e.getMessage());
		}

		if (result != null) {
			List<Hit> hits = result.getAllLocations();

			fillList(hits);
		}

		showError(uri, " OK! ");

		// Set the text view as the activity layout
		// setContentView(textView);

	}

	private void showError(URI uri, String errorText) {
		Context context = getApplicationContext();

		CharSequence text = errorText;
		if (uri != null)
			text = text + " when call " + uri.toString();
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

	private void fillList(List<Hit> hits) {
		final ListView listview = (ListView) findViewById(R.id.listview);

		final ArrayAdapter<Hit> adapter = new ArrayAdapter<Hit>(this,
				android.R.layout.simple_list_item_1, hits);

		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final Hit item = (Hit) parent.getItemAtPosition(position);
				finish(item);

			}
		});

	}

	public void finish(Hit hitItem) {
		Log.i(TAG, "finish : hitItem =  " + hitItem);
		setResult(RESULT_OK);
		CarReservationApplication carReservationApplication = new CarReservationApplication()
				.getInstance();
		carReservationApplication.setSelectedHit(hitItem);
		super.finish();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.display_message, menu);
	// return true;
	// }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("unused")
	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}
}
