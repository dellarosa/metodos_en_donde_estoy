package entities.wk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import metodos.MetodosRequest;
import metodos.RequestTaskAsync;

import com.google.gson.Gson;

import domain.CategoryPoints;
import domain.Gps;
import domain.LocationListener;
import entities.wk.ServiceNear;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
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
import entities.wk.R;



public class ServiceNear extends Activity 
{
	private static final String TAG = "ServiceNear";

	public double dbLat=0;
	public double dbLon=0;
	public double dbAlt=0;
	public float flSpeed=0;
	public String strLevelbat=null;
	String Imei;
	

	boolean TimerState=false; 
	RequestTaskAsync objT;
	boolean flagNewLocation;
	Gps gpsnews=new Gps();
	GetGPSData asyncgps;
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ServiceNear()
	{
		gpsnews=new Gps();
	}
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		Log.i(TAG, "[onCreate] onCreate");		
		 super.onCreate(savedInstanceState);
		 voSetView();
		 this.startUpdateCoordinates();
		 Log.i(TAG, "[onCreate] Pase StartUPdateCoord: ");									//DEBUG
		// currentThread.start();
		 asyncgps=new GetGPSData();
		 try
		 {
			 asyncgps.execute("");
		 }catch(Exception ex)
		 {
			 Log.e(TAG, "[onCreate] Error Exception: "+ex);									//DEBUG
		 }
		 
		 
		 
		 
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
			
	}	
	
	
	private class GetGPSData extends AsyncTask<String, Void, String>	
	{
		@Override
		protected String doInBackground(String... params) 
		{
			
			Log.i(TAG, "[GetGPSData] GetGPSData");									//DEBUG
			
			
			if(gpsnews.getLatitud()==0)
			{}else
			{
				Log.i(TAG, "[GetGPSData] GetGPSData OK");									//DEBUG}
			}
			try
			{
				Thread.sleep(1000);
			}catch(Exception ex)
			{
				Log.i(TAG, "[GetGPSData] Exception Sleep: "+ex);									//DEBUG}
			}
			
			
			return "OK";	
		}		
	}
	
	

	
	public boolean startUpdateCoordinates()
	{		
		 SharedPreferences settings = getSharedPreferences("Timer",MODE_PRIVATE);
		 SharedPreferences.Editor editor= settings.edit();
		 editor.putInt("Timer",1);
		 editor.commit();
		Log.i(TAG, "[startUpdateCoordinates] startUpdateCoordinates");									//DEBUG
		try
		{	
			
			 LocationListener[] mLocationListeners = new LocationListener[] 
			 {  						 
				new LocationListener(LocationManager.GPS_PROVIDER,gpsnews,this),
		        new LocationListener(LocationManager.NETWORK_PROVIDER,gpsnews,this)
			};
			 Log.i(TAG, "[initializeLocationManager] TIEMPO DE SLEEP: "+settings.getInt("Timer",2));				//DEBUG
			 mLocationListeners[0].startLocationListenerNet(settings.getInt("Timer",2),mLocationListeners[0]);	//TIEMPO
			 mLocationListeners[1].startLocationListenerGps(settings.getInt("Timer",2),mLocationListeners[1]);	//TIEMPO
			 
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
		setContentView(R.layout.servicenear);
	}

	///////////////////////////////////////////onRestart///////////////////////////////////////////////////////////
	 @Override
	 protected void onRestart() {
	     super.onRestart();  // Always call the superclass method first
	     
	 	Intent myIntent=null;
    	try
       	{
    		//myIntent = new Intent(cont, HTService.class);
    		
    		 myIntent=new Intent(this.getApplicationContext(),ServiceNear.class);
	    		
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

	
}
	
		 
		 /*
	private Handler threadHandler = new Handler() {
	     @Override
		public void handleMessage(android.os.Message msg) {
        	
        	startUpdateCoordinates();
        		   		
        	
        	
        	///TODO PARA QUE PUEDA ENVIAR EL REQUEST, PRIMERO DEBE HABER SELECCIONADO QUE DESEA BUSCAR (LISTA DE TIPOS)
        	// TODO MOSTRAR CATEGORIAS DISPONIBLES
        	final Button btEnvioURL = (Button) findViewById(R.id.btEnviarURL);
    	    
        	btEnvioURL.setOnClickListener(new OnClickListener()
    	    {   
    			public void onClick(View v) 
    			{				
    				///TODO OBTENER COORDENADAS PARA ENVIAR AL SERVIDOR
    				MetodosRequest metrequest=new MetodosRequest();
    				ArrayList<CategoryPoints> arrcategorypoints=metrequest.obtenerLocacionesCercanas(gpsnews);

    				for(CategoryPoints catp:arrcategorypoints)
    				{
    					Log.e(TAG, "[threadHandler] CategoryPoints: " +catp.getCategoryName());									//DEBUG	
    				}
    				    					
    				
    				//TODO IMPLEMENTAR MAPA
    				/*Intent IntMetodosMap = null;				    				    
				    try
				    {
				    	IntMetodosMap=new Intent(getApplicationContext(),HTService.class);
				    	IntMetodosMap.putExtra("DatosCatLoc", colcatloc);
				    	
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
					}*/
	/*
    			}
    	    });
        	
        }
    };
    */
	
	