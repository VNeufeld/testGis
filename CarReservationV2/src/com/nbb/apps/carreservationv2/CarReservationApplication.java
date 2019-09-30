/**
 * 
 */
package com.nbb.apps.carreservationv2;

import java.util.Calendar;

import com.nbb.apps.carreservationv2.base.Hit;
import com.nbb.apps.carreservationv2.base.Offer;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

/**
 * @author Valeri
 *
 */
public class CarReservationApplication extends Application {
    
	private final String TAG = "CarReservationApplication";

	private static CarReservationApplication singleton;
	
	private Hit selectedHit;
	
	private Calendar puDate;

	private Offer selectedOffer;
	
	
	public CarReservationApplication getInstance(){
		return singleton;
	}
	@Override
	public void onCreate() {
		Log.i(TAG," onCreate :: ");
		super.onCreate();
		singleton = this;
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	public Hit getSelectedHit() {
		return selectedHit;
	}

	public void setSelectedHit(Hit selectedHit) {
		this.selectedHit = selectedHit;
	}
	public Calendar getPuDate() {
		return puDate;
	}
	public void setPuDate(Calendar puDate) {
		this.puDate = puDate;
	}
	public void setSelectedOffer(Offer offer) {
		this.selectedOffer = offer;
		
	}
	public Offer getSelectedOffer() {
		return selectedOffer;
	}

}
