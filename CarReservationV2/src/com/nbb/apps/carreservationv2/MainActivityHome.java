package com.nbb.apps.carreservationv2;

import com.nbb.apps.carreservationv2.base.Hit;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivityHome extends Activity {
	private final String TAG = "MainActivityHome";
	
	SharedPreferences preferences;

	private static final int REQUEST_CODE_PU = 10;
	private static final int REQUEST_CODE_DO = 20;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Log.i(TAG," onCreate :: ");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity_home);
		
		
		// Initialize preferences
	    preferences = PreferenceManager.getDefaultSharedPreferences(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity_home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			 // Launch Preference activity
		    Intent i = new Intent(this, SettingsActivity.class);
		    startActivity(i);
			return true;
		case R.id.startReservation:
			 // Launch Preference activity
		    Intent iStart = new Intent(this, CreateReservationActivity.class);
		    startActivity(iStart);
			return true;
		default:
			return false;
		}
	}

	public void selectPuLocation(View view) {

		Log.i(TAG," selectPuLocation ");
		
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		EditText editText = (EditText) findViewById(R.id.puLocationEdit);
		String message = editText.getText().toString();

		intent.putExtra(MainActivity.EXTRA_MESSAGE, message);

		startActivityForResult(intent, REQUEST_CODE_PU);
	}

	public void selectDoLocation(View view) {
		
		Log.i(TAG," selectDoLocation ");

		Intent intent = new Intent(this, DisplayMessageActivity.class);
		EditText editText = (EditText) findViewById(R.id.doLocationEdit);
		String message = editText.getText().toString();

		intent.putExtra(MainActivity.EXTRA_MESSAGE, message);
		startActivityForResult(intent, REQUEST_CODE_DO);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(TAG," onActivityResult resultCode : "+resultCode);
		if (resultCode == RESULT_OK ) {
			
			CarReservationApplication carReservationApplication = new CarReservationApplication().getInstance();
			Hit selectedHit = carReservationApplication.getSelectedHit();
			Log.i(TAG," selectedHit = " + selectedHit);

			if ( requestCode == REQUEST_CODE_PU) {
				EditText editText = (EditText) findViewById(R.id.puLocationEdit);
				editText.setText(selectedHit.getIdentifier() + " _ " + selectedHit.getId());
			}
			else if ( requestCode == REQUEST_CODE_DO) {
				EditText editText = (EditText) findViewById(R.id.doLocationEdit);
				editText.setText(selectedHit.getIdentifier() + " _ " + selectedHit.getId());
			}
		}

	}
}
