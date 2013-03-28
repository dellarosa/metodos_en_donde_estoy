package entities;

import domain.Gps;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

public class LocationListener implements android.location.LocationListener
{
	
   // Location mLastLocation;
	private String TAG="LocationListener";
	private Gps gps;
	public LocationManager mLocationManager = null;
	private TelephonyManager mTelephoneManager = null;
	private Context mcontext;
	private String strLevelbat;
	
	public TelephonyManager getTelMgr()
	{
		return this.mTelephoneManager;
	}
	public LocationManager getLocMgr()
	{
		return this.mLocationManager;
	}
     public LocationListener(String provider,Gps gps,Context mcontext)
    {
    	 this.mcontext=mcontext;
    	 this.gps=gps;
        Log.e(TAG, "[LocationListener] LocationListener Provider " + provider);				//DEBUG
      //  mLastLocation = new Location(provider);
        initializeLocationManager();	//Pruebo de llamarlo desde aca.
    }
		     
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		public void initializeLocationManager() 
		{
		Log.e(TAG, "[initializeLocationManager] initializeLocationManager");				//DEBUG
		
		if (mLocationManager == null) {
			Log.e(TAG, "[initializeLocationManager] mLocationManager == NULL");				//DEBUG
			mLocationManager = (LocationManager)mcontext.getSystemService(Context.LOCATION_SERVICE);
		}
		// mLocationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
		
		if (mTelephoneManager == null)
		{
			Log.e(TAG, "[initializeLocationManager] mTelephoneManager==NULL");				//DEBUG
			mTelephoneManager = (TelephonyManager) mcontext.getSystemService(Context.TELEPHONY_SERVICE);
		}
		// mTelephoneManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		}
	    /////////////////////////////////////////////////////////////// ONLOCATION CHANGED ///////////////////////////
	    
		@SuppressWarnings("unused")
		public void onLocationChanged(Location location )
	    {			 
			 SharedPreferences settings = mcontext.getSharedPreferences("HT",Context.MODE_PRIVATE);
			
			 //flagNewLocation=true;
							   		  	   		
				   		Log.i(TAG,"[onLocationChanged] SOCKET ADDRESS Remote: ");			//DEBUG
				   						   		
				   		/* dbLat=location.getLatitude();
				   		 dbLon=location.getLongitude();
				   		 dbAlt=location.getAltitude();
				   		 flSpeed=location.getSpeed();				   		 
				   		 */
				   		gps.setLat(location.getLatitude());
				   		 gps.setLong(location.getLongitude());
				   		 gps.setAltit(location.getAltitude());
				   		 gps.setVeloc(location.getSpeed());
				   		
				   		
	    }
	    public void onProviderDisabled(String provider)
	    {
	    	/*if(provider.equals("gps"))
	    	{
	            //Toast.makeText(getApplicationContext(), "GPS is off", Toast.LENGTH_LONG).show();
	            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	        }*/		    	
	        Log.e(TAG, "[onProviderDisabled] onProviderDisabled: " + provider);					//DEBUG            
	    }
	    
	    public void onProviderEnabled(String provider)
	    {
	        Log.e(TAG, "[onProviderEnabled] onProviderEnabled: " + provider);					//DEBUG
	    }
	    public void onStatusChanged(String provider, int status, Bundle extras)
	    {
	        Log.e(TAG, "[onStatusChanged] onStatusChanged: " + provider);						//DEBUG
	    }
	    
	    protected void onDestroy() {
	        stopListening();
	        onDestroy();
	    }

	    protected void onPause() {
	        stopListening();
	        onPause();
	    }

	    protected void onResume() {
	        startListening();
	        onResume();
	    }
	    /**********************************************************************
	     * helpers for starting/stopping monitoring of GPS changes below 
	     **********************************************************************/
	    private void startListening() {
	    	mLocationManager.requestLocationUpdates(
	            LocationManager.GPS_PROVIDER, 
	            0, 
	            0, 
	            this
	        );
	    }

	    private void stopListening() {
	        if (mLocationManager != null)
	        	mLocationManager.removeUpdates(this);
	    }
		///////////////////////////////////////////////////////////////////////////////////////////////////
		
		public void startLocationListenerNet(int inTiempoEnvio,LocationListener mLocationListeners)
		{
			SharedPreferences settings = mcontext.getSharedPreferences("HT",Context.MODE_PRIVATE);
			final int LOCATION_INTERVAL = 10000;
			//private static final float LOCATION_DISTANCE = 5.0f;
			final float LOCATION_DISTANCE = 0;
			
			    try {
			    	
			    	if((inTiempoEnvio!=0))
			    	{
			    		Log.i(TAG, "[startUpdateCoordinates] inTiempoEnvio Net: "+inTiempoEnvio);		//DEBUG
			    		
						
						 
			    		mLocationListeners.getLocMgr().requestLocationUpdates(LocationManager.NETWORK_PROVIDER, inTiempoEnvio, LOCATION_DISTANCE,
			    				mLocationListeners);
			    				//locationlistener);
			    	}else
			    	{
			    		mLocationListeners.getLocMgr().requestLocationUpdates(LocationManager.NETWORK_PROVIDER,2, LOCATION_DISTANCE,
			    				//locationlistener);
				    			mLocationListeners);
			    	}
			    	Log.d(TAG, "[startUpdateCoordinates] Pase REQUEST_LOCATION_UPDATES");		//DEBUG    	
			        
			    	gps.setBattery(strbatteryLevel());        
			        //gpsnews = mLocationListeners[1].getTelMgr().getDeviceId();
			        
			        
			    } catch (java.lang.SecurityException ex) {
			        Log.i(TAG, "[startUpdateCoordinates] fail to request location update, ignore", ex);		//DEBUG
			    } catch (IllegalArgumentException ex) {
			        Log.d(TAG, "[startUpdateCoordinates] network provider does not exist, " + ex.getMessage());		//DEBUG        
			    }
			    
			    ///////////////////////////// TIMER //////////////////////////
			       try
					{
			        	Log.i("HTService","[startUpdateCoordinates] Durmiendo el tiempo configurado TIME: "+settings.getString("Timer",null));
						Thread.sleep(Integer.valueOf(settings.getString("Timer",null))*1000,0);
					}
					catch (InterruptedException e)
					{
						Log.i("HTService","SLEEP ERROR: "+e);
					}
		}
		
		public void startLocationListenerGps(int inTiempoEnvio,LocationListener mLocationListeners)
		{
			SharedPreferences settings = mcontext.getSharedPreferences("HT",Context.MODE_PRIVATE);
			final int LOCATION_INTERVAL = 10000;
			//private static final float LOCATION_DISTANCE = 5.0f;
			final float LOCATION_DISTANCE = 0;	
			
			  /////////////////////////////////////////////////////////////////////////
			    try {
			        
			    	if((inTiempoEnvio!=0))
			    	{
			    		Log.i(TAG, "[startUpdateCoordinates] inTiempoEnvio GPS: "+inTiempoEnvio);		//DEBUG
			    		mLocationListeners.getLocMgr().requestLocationUpdates(LocationManager.GPS_PROVIDER, inTiempoEnvio, LOCATION_DISTANCE,mLocationListeners);
			    	}else
			    	{
			    		mLocationListeners.getLocMgr().requestLocationUpdates(LocationManager.GPS_PROVIDER,2, LOCATION_DISTANCE,mLocationListeners);
			    	}
			    	Log.d(TAG, "[startUpdateCoordinates] Pase REQUEST_LOCATION_UPDATES 2 DOS");		//DEBUG
			    	
			        
			    	//Imei = mLocationListeners[1].getTelMgr().getDeviceId();      
			        gps.setBattery(strbatteryLevel());
			        
			    } catch (java.lang.SecurityException ex) {
			        Log.i(TAG, "[startUpdateCoordinates] fail to request location update, ignore", ex);			//DEBUG
			    } catch (IllegalArgumentException ex) {
			        Log.d(TAG, "[startUpdateCoordinates] gps provider does not exist " + ex.getMessage());			//DEBUG
			    }
			    
			    ///////////////////////////// TIMER //////////////////////////
			       try
					{
			        	Log.i("HTService","[startUpdateCoordinates] Durmiendo el tiempo configurado TIME: "+settings.getString("Timer",null));
						Thread.sleep(Integer.valueOf(settings.getString("Timer",null))*1000,0);
					}
					catch (InterruptedException e)
					{
						Log.i("HTService","SLEEP ERROR: "+e);
					}
		}
		
////////////////////////////////////////////////BATTERY &////////////////////////////////////////
	public String strbatteryLevel() {
	
		
		BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
		
			context.unregisterReceiver(this);
			
			int rawlevel = intent.getIntExtra("level", -1);
			
			int scale = intent.getIntExtra("scale", -1);
			
			int levelbat = -1;
			
			if (rawlevel >= 0 && scale > 0) {
			
			levelbat = (rawlevel * 100) / scale;
			
			}
			//Log.i(TAG, "[strbatteryLevel] BATTERY int: " + levelbat);
			strLevelbat=String.valueOf(levelbat);
			Log.i(TAG, "[strbatteryLevel] BATTERY str: " + strLevelbat);	
			}
			
		};
		
		IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);    	
		mcontext.registerReceiver(batteryLevelReceiver, batteryLevelFilter);
		Log.i(TAG, "[strbatteryLevel] BATTERY str final: " + strLevelbat);	
		
		return strLevelbat;
	}	

} 