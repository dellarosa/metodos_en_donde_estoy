package wlocation.wk;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import metodos.RequestTask;

import com.google.gson.Gson;

import domain.CategoryLocation;
import domain.Gps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.content.BroadcastReceiver;
import wlocation.wk.HTService;
import wlocation.wk.R;



public class HTService extends Activity  implements Runnable
{
	private static final String TAG = "HTService";
	private LocationManager mLocationManager = null;
	private TelephonyManager mTelephoneManager = null;
	private static final int LOCATION_INTERVAL = 10000;
	//private static final float LOCATION_DISTANCE = 5.0f;
	private static final float LOCATION_DISTANCE = 0;
	////////////////// UPDATE TELA /////////////////
	//GPSData objGPSDataClass=new GPSData();
	//GPSData objGPSDataClass;
	public double dbLat=0;
	public double dbLon=0;
	public double dbAlt=0;
	public float flSpeed=0;
	public String strLevelbat=null;
	String Imei;

	public Thread currentThread = new Thread(this);
	//private Handler handler = new Handler();
	//RequestTask objRQTsk=new RequestTask();
	boolean TimerState=false; 
	RequestTask objT;
	boolean flagNewLocation;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public HTService()
	{
		
	}
		
	public void run() {
		// TODO Auto-generated method stub
		
		Log.i(TAG, "[run] Run");									//DEBUG
		try
		{
			//Thread currentThread = new Thread(this);
		   // currentThread.start();
			//threadHandler.postDelayed(this,1000);	//Para hacer el handler constante
			//startUpdateCoordinates();
			//Thread.sleep(1000);			
			//Looper.prepare();
			//Looper.loop();
			threadHandler.sendEmptyMessage(0);
						
		}catch(Exception Ex)
		{
			Log.e(TAG, "[run] Error: " +Ex.getMessage());									//DEBUG
		}
		    
	}
	
	private Handler threadHandler = new Handler() {
	
		
        @Override
		public void handleMessage(android.os.Message msg) {
            // whenever the Thread notifies this handler we have
            // only this behavior
          //  threadModifiedText.setText("my text changed by the thread");
        	
	   		        	 
        	startUpdateCoordinates();
        	/*while(!currentThread.isAlive())
        	{
        		startUpdateCoordinates();
        		try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	*/
        		   		
        	Button btMinimizes = (Button) findViewById(R.id.btMinimizar);
    	    
        	btMinimizes.setOnClickListener(new OnClickListener()
    	    {     
    			public void onClick(View v) 
    			{				
    				Intent startMain = new Intent(Intent.ACTION_MAIN);
    	        	startMain.addCategory(Intent.CATEGORY_HOME);
    	        	startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	        	startActivity(startMain);
    				
    			}
    	    });
        	
        	
        	
        	
        	final Button btEnvioURL = (Button) findViewById(R.id.btEnviarURL);
    	    
        	btEnvioURL.setOnClickListener(new OnClickListener()
    	    {   
    			public void onClick(View v) 
    			{				
    				try
    		   		{    	
    					dbLat= -31.445;																	//DEBUG
    					dbLon= -58.4454;																//DEBUG
    					if((dbLat!=0)||(dbLon!=0))
    					{
    						Log.i(TAG, "[Handler] HAY LATITUD Y/O LONGITUD");							//DEBUG
    						
    						SharedPreferences settings = getSharedPreferences("HT",MODE_PRIVATE);
	    			   		
    						btEnvioURL.setSelected(true); 
	    			   			    			   	 
	    			   		final Gson gson = new Gson();	    			   		
	    			   		Gps objGps=new Gps("AndCYS",String.valueOf(dbLat),String.valueOf(dbLon),String.valueOf(dbAlt),String.valueOf(flSpeed),Imei,settings.getString("CodeActiv",null),strLevelbat);
	    			   		
	    			   		final String json = gson.toJson(objGps); 
	    			   		
	    			   		//String urlCatLoc = new String("http://192.168.252.129:3333/location_points/near_location_points.json?lat=-34.593968&lng=-58.413883");
	    			   		//String urlCatLoc = new String("http://192.168.252.129:3333/location_points/near_location_points.json?"+json);
	    			   		//String urlCatLoc = new String("http://sharedpc.dnsalias.com:3001/location_points/near_location_points.json?"+json);
	    			   		String urlCatLoc = new String("http://sharedpc.dnsalias.com:3001/location_points/near_location_points.json?alt=0.0&battery=50&code=CYS172827&id=AndCYS&imei=000000000000003&lat=-34.593968&lng=-58.413882&vel=0.0");
	    			   		
	    			   		Log.i(TAG, "[Handler] ENVIAR URL: "+urlCatLoc );		//DEBUG
	    			        try
	    			        {
	    			        	objT = (RequestTask) new RequestTask().execute(urlCatLoc);
	    			        }catch(Exception ex)
	    			        {
	    			        	Log.i(TAG, "[Handler] REQUEST EXCEPTION: "+ex );		//DEBUG
	    			        	if(btEnvioURL.isSelected())
	    			        	{
	    			        		btEnvioURL.setSelected(false);
	    			        	}
	    			        	return;
	    			        }
	    			        /////////////////////////////////////////////////////////////////////////////
	    			      
	    			        try
	    			        {	
		    			       TimerState=false;	    			       
					    	   new CountDownTimer(5000, 1000) {
					    		     public void onTick(long millisUntilFinished) {			    		         
					    		         Log.i(TAG, "[Handler] ONTICK: "+millisUntilFinished/1000);		//DEBUG
					    		     }
					    		     public void onFinish() {
					    		    	 Log.i(TAG, "[Handler] TIMER DONE");		//DEBUG
					    		    	 TimerState=true;    
					    		    	 if(objT.getResponse()==null)
					    		    	 {
					    		    		 Log.i(TAG, "[Handler] RESPONSE NULL");		//DEBUG
					    		    	 }else		//////////////OBTUVE RESPUESTA DE ENVIO... CATEGORIAS ...
					    		    	 {
					    		    		 
					    		    		 Log.i(TAG, "[Handler] RESPONSE : "+objT.getResponse());		//DEBUG
					    		    		 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					    		    		 ///TODO: CATEGORYLocation deberian ser dos clases, pero todavia no probe el gson para dos objetos uno dentro de otro FD v15.3.13
				    		    			CategoryLocation[] arrobjCatLoc = gson.fromJson(objT.getResponse(), CategoryLocation[].class);
				    		    			
				    		    			if(arrobjCatLoc.length>0)
				    		    			{
					    		    			Intent IntMetodosMap = null;				    				    
					    					    try
					    					    {
					    					    	IntMetodosMap=new Intent(getApplicationContext(),HTService.class);
					    					    	IntMetodosMap.putExtra("DatosCatLoc", arrobjCatLoc);
					    					    	
					    					    }catch(Exception Ex)
					    					    {
					    					    	Log.e(TAG,"[Handler] Exception MetodosMapas Intent: "+Ex);	
					    					    }
					    					    try
					    						{
					    					    	startActivity(IntMetodosMap);
					    						}catch(Exception ex)
					    						{
					    							Log.i(TAG,"[Handler] Exception MetodosMapas Start: "+ex);	
					    						}					
				    		    			}else
				    		    			{
				    		    				Log.i(TAG,"[Handler] NO HAY CATEGORIAS CERCA");
				    		    			}			    		    			
				    		    			
					    		    	 }
					    		    	 btEnvioURL.setSelected(false);
					    		     }
					    		  }.start();
				
		    			       /// USO UN THREAD ASINCRONICO PERO NO QUIERO QUE REALICE OTRA COSA SI NOT ENGO LA RESPUESTA,
		    			       /// DE ESTE MODO MANEJO LA RESPUESTA 
	    			        }catch(Exception ex)
	    			        {
	    			        	Log.i(TAG, "[Handler] Exception: "+ex);		//DEBUG
	    			        	if(btEnvioURL.isSelected())
	    			        	{
	    			        		btEnvioURL.setSelected(false);
	    			        	}
	    			        	return;
	    			        }
    					}else
    					{
    						Log.i(TAG, "[Handler] NO HAY COORDENADADS PARA ENVIAR ");		//DEBUG
    					}
    		   		}catch(Exception ex)
    		   		{
    		   			Log.e(TAG, "[Handler] Exploto todo: "+ex );		//DEBUG
    		   			
    		   		}

    				
    			}
    	    });
        	
        }
    };
 
    
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		Log.e(TAG, "[onCreate] onCreate");		
		 super.onCreate(savedInstanceState);	
		// setContentView(R.layout.viewsender);
			// appContext = this.getApplicationContext();
		// Thread currentThread = new Thread(this);
		 currentThread.start();		 		
	}
	
	
	public boolean startUpdateCoordinates()
	{		
		 SharedPreferences settings = getSharedPreferences("HT",MODE_PRIVATE);
	
   	
		Log.e(TAG, "[startUpdateCoordinates] startUpdateCoordinates");									//DEBUG
		try
		{	
			voSetView();
			
			 int inTiempoEnvio=0;
					 
			       
			    try
			    {
				    initializeLocationManager();
				    
				     inTiempoEnvio=Integer.valueOf(settings.getString("Timer",null))*1000;
				     //inTiempoEnvio=1;
			    }catch(Exception ex)
			    {
			    	Log.e(TAG, "[startUpdateCoordinates] ERROR: "+ex);		
			    	//settings.edit().clear();
			    }
			    try {
			        
			    	if((inTiempoEnvio!=0))
			    	{
			    		Log.i(TAG, "[startUpdateCoordinates] inTiempoEnvio Net: "+inTiempoEnvio);		//DEBUG
			    		
			    		mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, inTiempoEnvio, LOCATION_DISTANCE,
			    				mLocationListeners[1]);
			    				//locationlistener);
			    	}else
			    	{
			    		mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,2, LOCATION_DISTANCE,
			    				//locationlistener);
				    			mLocationListeners[1]);
			    	}
			    	Log.d(TAG, "[startUpdateCoordinates] Pase REQUEST_LOCATION_UPDATES");		//DEBUG    	
			        
			    	strLevelbat=strbatteryLevel();        
			        Imei = mTelephoneManager.getDeviceId();
			        
			        
			    } catch (java.lang.SecurityException ex) {
			        Log.i(TAG, "[startUpdateCoordinates] fail to request location update, ignore", ex);		//DEBUG
			    } catch (IllegalArgumentException ex) {
			        Log.d(TAG, "[startUpdateCoordinates] network provider does not exist, " + ex.getMessage());		//DEBUG        
			    }
			  /////////////////////////////////////////////////////////////////////////
			    try {
			        
			    	if((inTiempoEnvio!=0))
			    	{
			    		Log.i(TAG, "[startUpdateCoordinates] inTiempoEnvio GPS: "+inTiempoEnvio);		//DEBUG
			    		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, inTiempoEnvio, LOCATION_DISTANCE,
			    			//locationlistener);
			    			mLocationListeners[0]);
			    	}else
			    	{
			    		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2, LOCATION_DISTANCE,
			    				//locationlistener);
			    				mLocationListeners[0]);
			    	}
			    	Log.d(TAG, "[startUpdateCoordinates] Pase REQUEST_LOCATION_UPDATES 2 DOS");		//DEBUG
			    	
			        Imei = mTelephoneManager.getDeviceId();      
			        strLevelbat=strbatteryLevel();
			        
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
			     //objGPSDataClass.updateCoordinates(dbLat,dbLon,dbAlt,flSpeed,Imei,strLevelbat);
		}catch(Exception Ex)
		{
			Log.e(TAG, "[startUpdateCoordinates] ERROR: "+Ex);									//DEBUG
			return false;	
		}
		 return true;
	}
	
	///////////////////////////////////////////////////// Set View //////////////////////////////
	public void voSetView()
	{
		 SharedPreferences settings ;
     	 settings = getSharedPreferences("HT",MODE_PRIVATE);
     	 
		setContentView(R.layout.viewsender);
		
		TextView txtImei = (TextView) findViewById(R.id.TextViewNuevoUsuario2);
	    TextView txtCode = (TextView) findViewById(R.id.TextViewCode2);
	    txtImei.setText(settings.getString("ImeiID",null));
	    txtCode.setText(txtCode.getText()+settings.getString("CodeActiv",null));
	}
	////////////////////////////////////////////////////////LOCATION LISTENER /////////////////////////
	
	public class LocationListener implements android.location.LocationListener
	{
	   // Location mLastLocation;

	     public LocationListener(String provider)
	    {
	    	
	        Log.e(TAG, "[LocationListener] LocationListener Provider " + provider);				//DEBUG
	      //  mLastLocation = new Location(provider);       
	    }
		    /////////////////////////////////////////////////////////////// ONLOCATION CHANGED ///////////////////////////
		    
			@SuppressWarnings("unused")
			public void onLocationChanged(Location location )
		    {
				 SharedPreferences settings = getSharedPreferences("HT",MODE_PRIVATE);
				
				 flagNewLocation=true;
								   		  	   		
					   		Log.i(TAG,"[onLocationChanged] SOCKET ADDRESS Remote: ");			//DEBUG
					   		
					   		 dbLat=location.getLatitude();
					   		 dbLon=location.getLongitude();
					   		 dbAlt=location.getAltitude();
					   		 flSpeed=location.getSpeed();
					   		
					   		 /*	v_D.00.01
					   		  String test = "{"
					   				  		+ "\"Id\":\""+"AndCYS"+"\","
					   				  		+ "\"Lat\":\""+String.valueOf(dbLat)+"\","
					   				  		+ "\"Lng\":\""+String.valueOf(dbLon)+"\","
					   				  		+ "\"Alt\":\""+String.valueOf(dbAlt)+"\","
					   				  		+ "\"Spe\":\""+String.valueOf(flSpeed)+"\","						
					   				  		+ "\"IMEI\":\""+Imei+"\","
					   				  		+ "\"CodAct\":\""+settings.getString("CodeActiv",null)+"\","
					   				  		+ "\"Batt\":\""+ strLevelbat+"\""
					   		  				+ "}";
					   		*/
					   		 /*
					   		//String urlCatLoc = new String("http://localhost:3000/location_points/near_location_points");
					   		String urlCatLoc = new String("http://192.168.252.129:3000/location_points/near_location_points");
					   		
					   		Gps objGps=new Gps("AndCYS",String.valueOf(dbLat),String.valueOf(dbLon),String.valueOf(dbAlt),String.valueOf(flSpeed),Imei,settings.getString("CodeActiv",null),strLevelbat);  
					   
					   	// Create a new RestTemplate instance
					   		RestTemplate restTemplate = new RestTemplate();

					   		Log.i(TAG, "[onLocationChanged] PASE TEMPLATE REST" );		//DEBUG
					   		// Add the Jackson message converter
					   		//restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
					   		//restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
					   		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
					   		//restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
					   		
					   		// Make the HTTP GET request, marshaling the response from JSON to an array of Events
					   		CategoryLocation[] objCatLoc = restTemplate.getForObject(urlCatLoc, CategoryLocation[].class);
					   		int x=0;
					   		while(x<objCatLoc.length)
					   		{
					   			Log.i(TAG, "[onLocationChanged] Category Id: "+objCatLoc[x].getIdCategory() + " - Category Name: "+objCatLoc[x].getCategoryName()+" - Local Name: "+objCatLoc[x].getLocalName() );		//DEBUG
					   		}
					   		  */
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
	    
	    
	} 
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	 LocationListener[] mLocationListeners = new LocationListener[] 
	 {  
	 
			new LocationListener(LocationManager.GPS_PROVIDER),
	        new LocationListener(LocationManager.NETWORK_PROVIDER)
	};
	

	///////////////////////////////////////////onRestart///////////////////////////////////////////////////////////
	 @Override
	 protected void onRestart() {
	     super.onRestart();  // Always call the superclass method first
	     
	 	Intent myIntent=null;
    	try
       	{
    		//myIntent = new Intent(cont, HTService.class);
    		
    		 myIntent=new Intent(this.getApplicationContext(),HTService.class);
	    		
	       	}catch(Exception ex)
	         {
	        	 Log.e("PositionCYSActivity","[onCreate] ERROR I EX: "+ex);		        
	         }	    	
	       	try
	       	{
	       		startActivity(myIntent);
	         }catch(Exception ex)
	         {
	        	 Log.e("PositionCYSActivity","[onCreate] ERROR A EX: "+ex);
	         }
	     // Activity being restarted from stopped state    
	 }
	 
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	public void initializeLocationManager() 
	{
	   Log.e(TAG, "[initializeLocationManager] initializeLocationManager");				//DEBUG
	   
	    if (mLocationManager == null) {
	    	Log.e(TAG, "[initializeLocationManager] mLocationManager == NULL");				//DEBUG
	    	mLocationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
	    }
	   // mLocationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
	    
	    if (mTelephoneManager == null)
	    {
	    	Log.e(TAG, "[initializeLocationManager] mTelephoneManager==NULL");				//DEBUG
	    	mTelephoneManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
	    }
	   // mTelephoneManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
	}
	
	//////////////////////////////////////////////// BATTERY &////////////////////////////////////////
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
		registerReceiver(batteryLevelReceiver, batteryLevelFilter);
		Log.i(TAG, "[strbatteryLevel] BATTERY str final: " + strLevelbat);	
		
		return strLevelbat;
	}	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public IBinder onBind(Intent arg0)
	{
	    return null;
	}
	/////////////////////////////////////// PARA QUE NPO PUEDA VOLVER A LA PANTALLA ANTERIOR /////////////
	@Override
	public void onBackPressed() {
		Log.i(TAG, "[onBackPressed] onBackPressed");
		Intent startMain = new Intent(Intent.ACTION_MAIN);
    	startMain.addCategory(Intent.CATEGORY_HOME);
    	startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	startActivity(startMain);
    	
	    // do nothing.
	}
	/*
	public int onStartCommand(Intent intent, int flags, int startId)
	{    
		Log.e(TAG, "[onStartCommand] onStartCommand");							//DEBUG
	    super.onStartCommand(intent, flags, startId);       
	    return START_STICKY;
	}
	*/
	
	/*			////EL ONDESTROY ES PARA SERVICIOS
	@Override
	public void onDestroy()
	{
	     Log.e(TAG, "[onDestroy] onDestroy");								//DEBUG
	    super.onDestroy();
	    if (mLocationManager != null) {
	        for (int i = 0; i < mLocationListeners.length; i++) {
	            try {
	                mLocationManager.removeUpdates(mLocationListeners[i]);
	            } catch (Exception ex) {
	                Log.i(TAG, "[onDestroy] fail to remove location listners, ignore", ex);			//DEBUG
	            }
	        }
	    }
	} 
	*/
	
}
