package libreria;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
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
	private boolean modorastreo;
	public TelephonyManager getTelMgr()
	{
		return this.mTelephoneManager;
	}
	public LocationManager getLocMgr()
	{
		return this.mLocationManager;
	}
	public LocationListener()
    {
		Log.i(TAG, "[LocationListener] LocationListener ONLY");				//DEBUG
		//this.setGpsNew();
    }
	public LocationListener(Context mcontext)
    {
		Log.i(TAG, "[LocationListener] LocationListener ONLY CONTEXT");				//DEBUG 
    	 this.mcontext=mcontext;
    	 this.setBatteryLevel(null);
    	// this.setGpsNew();        
      //  mLastLocation = new Location(provider);
    	 //coordenadagps=false;
    	 initializeLocationManager();	//Pruebo de llamarlo desde aca.
        
    }
	public LocationListener(String provider,Context mcontext,Gps gpst)
    {
		Log.i(TAG, "[LocationListener] LocationListener Provider " + provider);				//DEBUG
    	 this.mcontext=mcontext;
    	 this.setBatteryLevel(null);
    	 this.setGpsNew();
    	 this.setGps(gpst);
        
      //  mLastLocation = new Location(provider);
        initializeLocationManager();	//Pruebo de llamarlo desde aca.
    }
	public LocationListener(String provider,Context mcontext)
    {
		Log.i(TAG, "[LocationListener] LocationListener Provider " + provider);				//DEBUG
    	 this.mcontext=mcontext;
    	 this.setBatteryLevel(null);
    	 this.setGpsNew();
    	 //this.setGps(gpst);
        
      //  mLastLocation = new Location(provider);
        initializeLocationManager();	//Pruebo de llamarlo desde aca.
    }
	private void setModoRastreo(boolean rastreo)
	{
		this.modorastreo=true;
	}
	private boolean getModoRastreo()
	{
		return this.modorastreo;
	}
	public void setGpsNew()
	{
		this.gps=new Gps();
	}
	public void setGps(Gps gps)
	{
		this.gps=gps;
	}	
	public Gps getGps()
	{
		return this.gps;
	}
	public void setBatteryLevel(String strBattery)
	{
		this.strLevelbat=strBattery;
	}
	public String getBatteryLevel()
	{
		return this.strLevelbat;
	}
	public void setContext(Context context)
	{
		this.mcontext=context;
	}
	
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		public void initializeLocationManager() 
		{
			Log.i(TAG, "[initializeLocationManager] initializeLocationManager");				//DEBUG
			
			if (mLocationManager == null) {
				Log.i(TAG, "[initializeLocationManager] mLocationManager == NULL");				//DEBUG
				mLocationManager = (LocationManager)mcontext.getSystemService(Context.LOCATION_SERVICE);
			}
			// mLocationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
			
			if (mTelephoneManager == null)
			{
				Log.i(TAG, "[initializeLocationManager] mTelephoneManager==NULL");				//DEBUG
				mTelephoneManager = (TelephonyManager) mcontext.getSystemService(Context.TELEPHONY_SERVICE);
			}
			// mTelephoneManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
			Log.i(TAG, "[initializeLocationManager] TERMINE initializeLocationManager");				//DEBUG
		}
	    /////////////////////////////////////////////////////////////// ONLOCATION CHANGED ///////////////////////////
	    
		@SuppressWarnings("unused")
		public void onLocationChanged(Location location )
	    {			
			try
			{	
				try
				{
					this.getGps().setLong(location.getLongitude());
					this.getGps().setLat(location.getLatitude());			
					this.getGps().setAltit(location.getAltitude());
					this.getGps().setVeloc(location.getSpeed());
				}catch(Exception ex)
				{
					Log.e(TAG,"[onLocationChanged] Exception Alt speed: "+ex);			//DEBUG
				}
		   		Log.i(TAG,"[onLocationChanged] Lat: "+this.getGps().getLatitud()+" Long: "+this.getGps().getLongitud());			//DEBUG
		   		
			}catch(Exception ex)
			{
				Log.e(TAG,"[onLocationChanged] Exception grl: "+ex);			//DEBUG
			}
			
			try
			{
				if(this.getBatteryLevel()!=null)					
				{
					this.getGps().setBattery(this.getBatteryLevel());
				}
			}catch(Exception e)
			{
				Log.e(TAG,"[onLocationChanged] Exception getbatterylevel: "+e);			//DEBUG
			}
			
			if(this.getModoRastreo()==true)
			{				
					//SEND DATA
		    }else		//MODO RASTREO
		    {
		    	
		    }
	    }
	    public void onProviderDisabled(String provider)
	    {
	    	/*if(provider.equals("gps"))
	    	{
	            //Toast.makeText(getApplicationContext(), "GPS is off", Toast.LENGTH_LONG).show();
	            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	        }*/		    	
	        Log.i(TAG, "[onProviderDisabled] onProviderDisabled: " + provider);					//DEBUG            
	    }
	    
	    public void onProviderEnabled(String provider)
	    {
	        Log.i(TAG, "[onProviderEnabled] onProviderEnabled: " + provider);					//DEBUG
	    }
	    public void onStatusChanged(String provider, int status, Bundle extras)
	    {
	        Log.i(TAG, "[onStatusChanged] onStatusChanged: " + provider);						//DEBUG
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
	    	SharedPreferences settings = mcontext.getSharedPreferences("Timer",Context.MODE_PRIVATE);
	    	
	    	mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,settings.getInt("Timer",2),0,this);
	    	//mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
	    }

	    private void stopListening() {
	        if (mLocationManager != null)
	        	mLocationManager.removeUpdates(this);
	    }

	    ///////////////////////////////////////////////////////////////////////////////////////////////////
///############################# SET LOCATION LISTENER NET #############################################################
		public void startLocationListenerNet(int inTiempoEnvio,LocationListener mLocationListeners)
		{
			SharedPreferences settings = mcontext.getSharedPreferences("Timer",Context.MODE_PRIVATE);
			final int LOCATION_INTERVAL = 10000;
			//private static final float LOCATION_DISTANCE = 5.0f;
			final float LOCATION_DISTANCE = 0;
			
			    try {
			    	
			    	if((inTiempoEnvio!=0))
			    	{
			    		Log.i(TAG, "[startLocationListenerNet] inTiempoEnvio Net: "+inTiempoEnvio);		//DEBUG			    		
			    		mLocationListeners.getLocMgr().requestLocationUpdates(LocationManager.NETWORK_PROVIDER, inTiempoEnvio, LOCATION_DISTANCE,mLocationListeners);
			    	}else
			    	{
			    		mLocationListeners.getLocMgr().requestLocationUpdates(LocationManager.NETWORK_PROVIDER,2, LOCATION_DISTANCE,mLocationListeners);
			    	}
			    	Log.i(TAG, "[startLocationListenerNet] Pase REQUEST_LOCATION_UPDATES");		//DEBUG
			        //gpsnews = mLocationListeners[1].getTelMgr().getDeviceId();
			    } catch (java.lang.SecurityException ex) {
			        Log.e(TAG, "[startLocationListenerNet] fail to request location update, ignore", ex);		//DEBUG
			    } catch (IllegalArgumentException ex) {
			        Log.e(TAG, "[startLocationListenerNet] network provider does not exist, " + ex.getMessage());		//DEBUG        
			    }
			    //////////////////////////////// BATTERY LEVEL /////////////////////////////
			   try
			   {
				    if(getBatteryLevel()==null)
			        {
			        	gps.setBattery(batteryLevel());
			        }
				    Log.i(TAG, "[startLocationListenerNet] BATTERY str: " + this.getBatteryLevel());			//DEBUG
			   } catch(Exception e)
			    {
			    	Log.i(TAG,"[startLocationListenerGps] Erro Battery Exception: "+e);
			    }
			    
			    ///////////////////////////// TIMER //////////////////////////
			       try
					{
			        	Log.i(TAG,"[startLocationListenerNet] Durmiendo el tiempo configurado TIME: "+settings.getInt("Timer",2));
						Thread.sleep(settings.getInt("Timer",2)*1000,0);
					}
					catch (InterruptedException e)
					{
						Log.e(TAG,"[startLocationListenerNet]SLEEP ERROR: "+e);
					}
		}
		
///################################ START LOCATION LISTENER GPS NEAR  ########################################################################
		public void startLocationListenerGps(int inTiempoEnvio,LocationListener mLocationListeners)
		{
			final int LOCATION_INTERVAL = 10000;
			final float LOCATION_DISTANCE = 0;
			SharedPreferences settings = mcontext.getSharedPreferences("Timer",Context.MODE_PRIVATE);
						
			  /////////////////////////////////////////////////////////////////////////
			    try {
			        
			    	if((inTiempoEnvio!=0))
			    	{
			    		Log.i(TAG, "[startLocationListenerGps] inTiempoEnvio GPS: "+inTiempoEnvio);		//DEBUG
			    		mLocationListeners.getLocMgr().requestLocationUpdates(LocationManager.GPS_PROVIDER, inTiempoEnvio, LOCATION_DISTANCE,mLocationListeners);
			    	}else
			    	{
			    		mLocationListeners.getLocMgr().requestLocationUpdates(LocationManager.GPS_PROVIDER,2, LOCATION_DISTANCE,mLocationListeners);
			    	}
			    	Log.d(TAG, "[startLocationListenerGps] Pase REQUEST_LOCATION_UPDATES GPS");		//DEBUG
			    	
			        
			    	//Imei = mLocationListeners[1].getTelMgr().getDeviceId();      
			        
			    } catch (java.lang.SecurityException ex) {
			        Log.i(TAG, "[startLocationListenerGps] fail to request location update, ignore", ex);			//DEBUG
			    } catch (IllegalArgumentException ex) {
			        Log.d(TAG, "[startLocationListenerGps] gps provider does not exist " + ex.getMessage());			//DEBUG
			    }
			    ///////////////////////////////// Battery level ///////////////////////////
			   try
			   {
				    if(this.getBatteryLevel()==null)
			        {
			        	gps.setBattery(batteryLevel());
			        }
				    Log.i(TAG, "[startLocationListenerGps] BATTERY str: " + this.getBatteryLevel());			//DEBUG
			   }catch(Exception e)
			    {
			    	Log.i(TAG,"[startLocationListenerGps] Erro Battery Exception: "+e);
			    }
			    ///////////////////////////// TIMER //////////////////////////
			       try
					{
			        	Log.i(TAG,"[startLocationListenerGps] Durmiendo el tiempo configurado TIME: "+settings.getInt("Timer",2));
						Thread.sleep(settings.getInt("Timer",2)*1000,0);
					}
					catch (InterruptedException e)
					{
						Log.i(TAG,"[startLocationListenerGps]SLEEP ERROR: "+e);
					}
		}
		
		
		/////////////////////////////////////////////////////////////////////////////////////////
///########################### OBTENER COORDENADAS - PARA NEAR ##############################################################
		public Gps obtenerCoordenadas(Gps gpstofill)		//No sería necesario recibir el objeto
		{		
			// setGps(gpstofill);
			 SharedPreferences settings = this.mcontext.getSharedPreferences("Timer",Context.MODE_PRIVATE);
			 SharedPreferences.Editor editor= settings.edit();
			 editor.putInt("Timer",1);
			 editor.commit();
			// Log.d(TAG, "[startUpdateCoordinates] REFERENCIA gps fill: "+gpstofill);									//DEBUG}
			Log.i(TAG, "[obtenerCoordenadas] startUpdateCoordinates");									//DEBUG
			try
			{						
				 LocationListener[] mLocationListeners = new LocationListener[] 
				 {  						 
						 new LocationListener(LocationManager.NETWORK_PROVIDER,this.mcontext,gpstofill),			        
					     new LocationListener(LocationManager.GPS_PROVIDER,this.mcontext,gpstofill)
				};
				 Log.i(TAG, "[obtenerCoordenadas] TIEMPO DE SLEEP: "+settings.getInt("Timer",2));				//DEBUG
				 //mLocationListeners[0].startLocationListenerNet(settings.getInt("Timer",2),mLocationListeners[0]);	// POR AHROA SOLO GPS
				 Log.i(TAG, "[obtenerCoordenadas] PASE LOCATION NET");				//DEBUG
				 mLocationListeners[1].startLocationListenerGps(settings.getInt("Timer",2),mLocationListeners[1]);	//TIEMPO
				 Log.i(TAG, "[obtenerCoordenadas] PASE LOCATION GPS");				//DEBUG
				 
				 this.setGps(gpstofill);
				 
			}catch(Exception Ex)
			{
				Log.e(TAG, "[obtenerCoordenadas] ERROR: "+Ex);									//DEBUG
				Log.e(TAG, "[obtenerCoordenadas] ERROR 1: "+Ex.getCause());									//DEBUG
				return null;	
			}
			//gpstofill=this.getGps();	//Verificar si lo devuelvo en el return o copio aca la referencia.
			
			 return this.getGps();
		}
		
		
/////////////////////////////////////////////////////////////////////////////////////////
///########################### START LOCATION UPDATE - PARA RASTREO ##############################################################
public boolean startLocationUpdate()		
{		
	//setGps(gpstofill);
	SharedPreferences settings = this.mcontext.getSharedPreferences("Timer",Context.MODE_PRIVATE);
	SharedPreferences.Editor editor= settings.edit();
	//editor.putInt("Timer",1);
	editor.commit();
	
	this.setModoRastreo(true);		//ACTIVO MODO RASTREO
	
	// Log.d(TAG, "[startUpdateCoordinates] REFERENCIA gps fill: "+gpstofill);									//DEBUG}
	Log.i(TAG, "[obtenerCoordenadas] startUpdateCoordinates");									//DEBUG
	try
	{						
		LocationListener[] mLocationListeners = new LocationListener[] 
	{  						 
				new LocationListener(LocationManager.NETWORK_PROVIDER,this.mcontext),			        
				new LocationListener(LocationManager.GPS_PROVIDER,this.mcontext)
	};
	Log.i(TAG, "[obtenerCoordenadas] TIEMPO DE SLEEP: "+settings.getInt("Timer",2));				//DEBUG
	//mLocationListeners[0].startLocationListenerNet(settings.getInt("Timer",2),mLocationListeners[0]);	// POR AHROA SOLO GPS
	Log.i(TAG, "[obtenerCoordenadas] PASE LOCATION NET");				//DEBUG
	mLocationListeners[1].startLocationListenerGps(settings.getInt("Timer",2),mLocationListeners[1]);	//TIEMPO
	Log.i(TAG, "[obtenerCoordenadas] PASE LOCATION GPS");				//DEBUG
	
	//	 this.setGps(gpstofill);
	
	}catch(Exception Ex)
	{
		Log.e(TAG, "[obtenerCoordenadas] ERROR: "+Ex);									//DEBUG
		Log.e(TAG, "[obtenerCoordenadas] ERROR 1: "+Ex.getCause());									//DEBUG
		return false;	
	}
	//gpstofill=this.getGps();	//Verificar si lo devuelvo en el return o copio aca la referencia.
	
	return true;
}

////////////////////////////////////////////////////////////////////////
////####################### STOP LOCATION UPDATE ##############################################
public boolean stopLocationUpdate()
{
	this.onDestroy();		//PROBAR
	//this.onPause();
	//TODO
	return true;
}
		

		
////////////////////////////////////////////////BATTERY &////////////////////////////////////////
	public String batteryLevel() {
	
		try
		{
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
				setBatteryLevel(String.valueOf(levelbat));
				//Log.i(TAG, "[strbatteryLevel] BATTERY str: " + strLevelbat);			//DEBUG	
				}
				
			};
			IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);    	
			mcontext.registerReceiver(batteryLevelReceiver, batteryLevelFilter);
			Log.i(TAG, "[strbatteryLevel] BATTERY str final: " + getBatteryLevel());	
			
		}catch(Exception e)
		{
			Log.i(TAG, "[strbatteryLevel] BATTERY EXCEPTION: "+e);
		}
		
		return getBatteryLevel();
	}	

} 