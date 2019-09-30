package com.nbb.apps.carreservationv2;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.codehaus.jackson.JsonProcessingException;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nbb.apps.carreservationv2.base.Offer;
import com.nbb.apps.carreservationv2.conections.GetOfferService;

public class GetOfferActivity extends Activity {
	private final String TAG = "CreateReservationActivity";
	
	private SharedPreferences preferences;
	private URI uri = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_offer);
		
		
	    preferences = PreferenceManager.getDefaultSharedPreferences(this);
	    
	    Offer offer = retrieveOffer();
	    
		final TextView tv = (TextView) findViewById(R.id.getOffer);
		tv.setText(offer.toString());

		
	}

	private Offer retrieveOffer() {
		Offer response = null;;
		try {
			GetOfferService service = new GetOfferService(preferences);
			
			response = service.getOffer();
			
		} catch (JsonProcessingException e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
			showError(uri, e.getMessage());
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
		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
			showError(uri, e.getMessage());
		}
		return response;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.get_offer, menu);
		return true;
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


}
