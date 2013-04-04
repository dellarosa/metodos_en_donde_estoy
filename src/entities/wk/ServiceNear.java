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
import domain.LocationListener.GetGPSDataState;
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
	//Gps gpsnews=new Gps();
	Gps gpsnews;
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ServiceNear()
	{
		this.gpsnews=new Gps();
	}
	public Gps getGpsnews()
	{
		return this.gpsnews;
	}
	public void setGpsnews(Gps gpsn)
	{
		this.gpsnews=gpsn;
	}
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		Log.i(TAG, "[onCreate] onCreate");		
		 super.onCreate(savedInstanceState);
		 voSetView();
		 LocationListener listenerloc= new LocationListener(this);
		 
		 //LocationListener.GetGPSDataState gpsstate=(GetGPSDataState) new LocationListener.GetGPSDataState().execute("");
		 //gpsstate.isCancelled();
		 
		 try{
			 listenerloc.setContext(getApplicationContext());		//Rvisar contexto			 
			 gpsnews=listenerloc.startUpdateCoordinates(this.getGpsnews());			 
			 Log.i(TAG, "[onCreate] Pase StartUPdateCoord: GPS DATA "+gpsnews.getAltitud());									//DEBUG	 
		 }catch(Exception e)
		 {
			 Log.e(TAG, "[onCreate] Exception: "+e);									//DEBUG
		 }
		 
		// currentThread.start();
		
		 
		 
		 
		 
		 Button btMinimizes = (Button) findViewById(R.id.btMinimizarNear);    
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
	
	