package cn.com.easytaxi.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LocationBroadcastReceiver extends BroadcastReceiver {

	private static int currentLat;
	private static int currentLng;
	private static float radius;
	private static float derect;
	private static String address;
	private static String city;

	@Override
	public void onReceive(Context context, Intent intent) {
		currentLat = intent.getIntExtra("latitude", 0);
		currentLng = intent.getIntExtra("longitude", 0);
		radius = intent.getFloatExtra("radius", 0.0f);
		derect = intent.getFloatExtra("derect", 0.0f);
		address = intent.getStringExtra("address");

		city = intent.getStringExtra("city");

		if (mlistener != null) {
			mlistener.onLocReveive(currentLat, currentLng, city);
		}
		// derect = 0.0f;

		// AppLog.LogD( " currentLng = "+ currentLng + " , " + " currentlat = "+
		// currentLat);
	}

	public static int getCurrentLat() {
		return currentLat;
	}

	public static int getCurrentLng() {
		return currentLng;
	}

	public static float getRadius() {
		return radius;
	}

	public static float getDerect() {
		return derect;
	}

	public static String getAddress() {
		return address;
	}
	public static String getcity(){
		return city;
	}
	public static LocationReceive mlistener;

	public static void setReceiveListener(LocationReceive listener) {
		mlistener = listener;
	}

	public interface LocationReceive {
		void onLocReveive(int lat, int lng, String city);
	}

}
