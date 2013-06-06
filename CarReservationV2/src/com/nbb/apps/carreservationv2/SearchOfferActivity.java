package com.nbb.apps.carreservationv2;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.codehaus.jackson.JsonProcessingException;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.nbb.apps.carreservationv2.base.Hit;
import com.nbb.apps.carreservationv2.base.Offer;
import com.nbb.apps.carreservationv2.base.VehicleResponse;
import com.nbb.apps.carreservationv2.conections.SearchOfferService;

public class SearchOfferActivity extends ListActivity {
	private final String TAG = "SearchOfferActivity";

	private SharedPreferences preferences;
	private URI uri = null;
	private String requestBody = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//setContentView(R.layout.activity_search_offer);
		
	    preferences = PreferenceManager.getDefaultSharedPreferences(this);


		VehicleResponse response = null;;
		try {
			SearchOfferService service = new SearchOfferService(preferences);
			uri = service.getUri();
			requestBody = service.getJsonRequestBody();
			
			response = service.getOffers();
			
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
		
		List<Offer> offers = new ArrayList<Offer>();

		if ( response != null) 
			offers.addAll(response.getAllOffers());
		
		
		final ArrayAdapter<Offer> adapter = new ArrayAdapter<Offer>(this,
				android.R.layout.simple_list_item_1, 
				offers);
		
		setListAdapter(adapter);

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_offer, menu);
		return true;
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		final Offer item = (Offer) l.getItemAtPosition(position);
		finish(item);
		
	}

	public void finish(Offer offer) {
		Log.i(TAG, "finish : offer =  " + offer);
		setResult(RESULT_OK);
		CarReservationApplication carReservationApplication = new CarReservationApplication()
				.getInstance();
		carReservationApplication.setSelectedOffer(offer);
		super.finish();
	}

}
