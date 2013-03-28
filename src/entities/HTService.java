package entities;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import metodos.RequestTask;

import com.google.gson.Gson;

import domain.CategoryLocation;
import domain.Gps;
import entities.HTService;

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
import wlocation.wk.R;



public class HTService extends Activity  implements Runnable
{
	private static final String TAG = "HTService";

	public double dbLat=0;
	public double dbLon=0;
	public double dbAlt=0;
	public float flSpeed=0;
	public String strLevelbat=null;
	String Imei;

	public Thread currentThread = new Thread(this);

	boolean TimerState=false; 
	RequestTask objT;
	boolean flagNewLocation;
	Gps gpsnews=new Gps();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public HTService()
	{
		gpsnews=new Gps();
	}
		
	public void run() {
		// TODO Auto-generated method stub
		
		Log.i(TAG, "[run] Run");									//DEBUG
		try
		{
			threadHandler.sendEmptyMessage(0);
						
		}catch(Exception Ex)
		{
			Log.e(TAG, "[run] Error: " +Ex.getMessage());									//DEBUG
		}
		    
	}
	
	private Handler threadHandler = new Handler() {
	     @Override
		public void handleMessage(android.os.Message msg) {
        	
        	startUpdateCoordinates();
        		   		
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
	    			   		//Gps objGps=new Gps("AndCYS",dbLat,dbLon,dbAlt,flSpeed,Imei,settings.getString("CodeActiv",null),strLevelbat);
	    			   		Gps objGps=gpsnews;
	    			   		Log.i(TAG,"DATOS GPS: "+gpsnews.getLatitud()+" "+gpsnews.getLongitud());
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
		 currentThread.start();		 		
	}
	
	
	public boolean startUpdateCoordinates()
	{		
		 SharedPreferences settings = getSharedPreferences("HT",MODE_PRIVATE);
	
   	
		Log.e(TAG, "[startUpdateCoordinates] startUpdateCoordinates");									//DEBUG
		try
		{	
			voSetView();
			 LocationListener[] mLocationListeners = new LocationListener[] 
			 {  						 
				new LocationListener(LocationManager.GPS_PROVIDER,gpsnews,this),
		        new LocationListener(LocationManager.NETWORK_PROVIDER,gpsnews,this)
			};
			 mLocationListeners[0].startLocationListenerNet(Integer.valueOf(settings.getString("Timer",null)),mLocationListeners[0]);	//TIEMPO
			 mLocationListeners[0].startLocationListenerNet(Integer.valueOf(settings.getString("Timer",null)),mLocationListeners[1]);	//TIEMPO
			 
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
