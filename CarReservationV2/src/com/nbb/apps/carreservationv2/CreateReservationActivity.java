package com.nbb.apps.carreservationv2;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.nbb.apps.carreservationv2.base.Hit;
import com.nbb.apps.carreservationv2.base.Offer;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateReservationActivity extends Activity implements	DatePickerDialog.OnDateSetListener{
	private final String TAG = "CreateReservationActivity";
	private final String TAG_P = "datePicker";
	
	private static final int REQUEST_CODE_PU = 10;
	private static final int REQUEST_CODE_VEHICLE = 11;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_reservation);

		EditText editText = (EditText) findViewById(R.id.puDate);
		editText.setFocusable(false);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_reservation, menu);
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
		case R.id.getOffer:
			 // Launch Preference activity
		    Intent iStart = new Intent(this, GetOfferActivity.class);
		    startActivity(iStart);
			return true;
		default:
			return false;
		}
	}

	public void selectLocation(View view) {
		Log.i(TAG," selectLocation ");
		
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		
		EditText editText = (EditText) findViewById(R.id.startCity);
		String message = editText.getText().toString();

		intent.putExtra(MainActivity.EXTRA_MESSAGE, message);

		startActivityForResult(intent, REQUEST_CODE_PU);
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(TAG," onActivityResult resultCode : "+resultCode);
		if (resultCode == RESULT_OK ) {
			
			CarReservationApplication carReservationApplication = new CarReservationApplication().getInstance();
			Hit selectedHit = carReservationApplication.getSelectedHit();
			Log.i(TAG," selectedHit = " + selectedHit);

			if ( requestCode == REQUEST_CODE_PU) {
				EditText editText = (EditText) findViewById(R.id.startCity);
				editText.setText(selectedHit.getIdentifier() + " _ " + selectedHit.getId());
			}
//			else if ( requestCode == REQUEST_CODE_DO) {
//				EditText editText = (EditText) findViewById(R.id.doLocationEdit);
//				editText.setText(selectedHit.getIdentifier() + " _ " + selectedHit.getId());
//			}
			else if ( requestCode == REQUEST_CODE_VEHICLE) {
				Offer offer = carReservationApplication.getSelectedOffer();
				
				TextView tv = (TextView) findViewById(R.id.offerView);
				tv.setText(offer.getCarCategoryId()+ "id ="+offer.getId().toString());
			}
			
		}

	}


	public void searchOffers(View view) {
		Log.i(TAG," searchOffers ");
		Intent intent = new Intent(this, SearchOfferActivity.class);
		startActivityForResult(intent,REQUEST_CODE_VEHICLE);

	}

	
	public void selectDate(View view) {
		Log.i(TAG," selectDate ");
		 DialogFragment datePicker = new DatePickerFragment();
		 FragmentManager fm = getFragmentManager();
		 datePicker.show(fm,TAG_P);
	}

	public static class DatePickerFragment extends DialogFragment  {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), (CreateReservationActivity)getActivity(), year, month, day);
		}

	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		
		EditText editText = (EditText) findViewById(R.id.puDate);
		editText.setFocusable(false);
		Calendar cal = Calendar.getInstance();
		cal.set(year, monthOfYear, dayOfMonth);
		
		CarReservationApplication carReservationApplication = new CarReservationApplication().getInstance();
		carReservationApplication.setPuDate(cal);
		
		//SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");
		DateFormat sdf1 = SimpleDateFormat.getDateInstance();
		String strDate = sdf1.format(cal.getTime());
		editText.setText(strDate);
		
	}
}
