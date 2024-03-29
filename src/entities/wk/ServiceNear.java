package entities.wk;

import libreria.Gps;
import libreria.LocationListener;

import libreria.RequestTaskAsync;

import entities.wk.ServiceNear;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.IBinder;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
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
	private ProgressBar mProgress;
	private Button btreintentar;
	private TextView txbuscando;
	boolean TimerState=false; 
	RequestTaskAsync objT;
	boolean flagNewLocation;
	
	//GPSAsyncState gpsstate;
	//Gps gpsnews=new Gps();
	Gps gpsnews;
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	///###################################################################################################
	public ServiceNear()
	{
		Log.i(TAG, "**[ServiceNear] Construct servicenear***");
		this.setGpsnews();
	}
	
	public  void setGpsnews()
	{
		this.gpsnews=new Gps();
	}
	
	public void setGps(Gps gpsn)
	{
		this.gpsnews=gpsn;
	}
	public Gps getGps()
	{
		return this.gpsnews;
	}
	///###################################################################################################
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		Log.i(TAG, "[onCreate] onCreate");		
		 super.onCreate(savedInstanceState);
		 voSetView();
		 LocationListener listenerloc= new LocationListener();
		 
		 //LocationListener.GetGPSDataState gpsstate=(GetGPSDataState) new LocationListener.GetGPSDataState().execute("");
		 //gpsstate.isCancelled();
		 
		 try{
			 listenerloc.setContext(getApplicationContext());		//Rvisar contexto
			// this.setGps(listenerloc.obtenerCoordenadas(this.getGps()));			//SI LE PASO EL OBJETO, NO HARIA FALTA QUE DEVUELVA NADA. 
			// this.setGps(listenerloc.obtenerCoordenadas());			
			 listenerloc.obtenerCoordenadas(this.getGps());
		 }catch(Exception e)
		 {
			 Log.e(TAG, "[onCreate] Exception: "+e);									//DEBUG
		 }
		 try
		 {
			 Log.d(TAG, "[onCreate] GPS ASYNCSTATE ");									//DEBUG
			 new GPSAsyncState().execute("");
		 }catch(Exception e)
		 {
			 Log.e(TAG, "[onCreate] Exception async: "+e);									//DEBUG
		 }
		 /////////////////////////////////////////////
		 txbuscando = (TextView) findViewById(R.id.buscandocoord);
		 mProgress = (ProgressBar) findViewById(R.id.progressBar1);
		 btreintentar = (Button) findViewById(R.id.btreintentar);
		 btreintentar.setVisibility(View.GONE);		 
		 /////////////////////////////////////////////////////////////
		 btreintentar.setOnClickListener(new OnClickListener()
		    {     
				public void onClick(View v) 
				{	
					try
					{
						if(txbuscando.getVisibility()!=View.VISIBLE)
						{txbuscando.setVisibility(View.VISIBLE);}
						if(mProgress.getVisibility()!=View.VISIBLE)
						 {mProgress.setVisibility(View.VISIBLE);}
						
						new GPSAsyncState().execute("");
					}catch(Exception ex)
					{
						Log.e(TAG, "[setOnClickListener] VISIBILITY EXCEPTION: "+ex);									//DEBUG
					}
					
				}
		    });
		 /////////////////////////////////////////////////////////////
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
	
////####################### OBTENCION DE COORDENADA ##############################################
		public class GPSAsyncState extends AsyncTask<String, Void, Boolean>	
		{
			protected void onPreExecute() {
		    }
			protected Boolean doInBackground(String... params) 
			{	 
				Log.i(TAG, "[doInBackground] GPSAsyncState");									//DEBUG
				
				  int x=1;
				try
				{					
					while((getGps().getLatitud()==0)&&(getGps().getLongitud()==0)&&(x<=100))
					{	
						mProgress.setProgress(x);
						Log.i(TAG, "[doInBackground] While wating gps");									//DEBUG}
						try
						{
							Thread.sleep(100);			//Tiempo *100
						}catch(Exception e)
						{
							Log.e(TAG, "[doInBackground] Exception sleep: "+e);									//DEBUG}
						}
						x++;
					}
				}catch(Exception e)
				{
					Log.i(TAG, "[doInBackground] Exception while  async: "+e);									//DEBUG}
					return false;
				}

				if(x>100)
				{
					Log.i(TAG, "[doInBackground] return false");
					return false;
				}
				
				
				Log.i(TAG, "[doInBackground] return true");	
				return true;
			}	
			
			 
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				Log.d(TAG, "[onPostExecute] termine");
				mProgress.setVisibility(View.GONE);
				if(result==false)
				{
					txbuscando.setVisibility(View.GONE);
					btreintentar.setVisibility(View.VISIBLE);
					Log.i(TAG, "[onPostExecute] VOLVER A ENVIAR - FALSE");
				}else
				{
					//TODO GO TO MAPA	
					try
				    {
				    	Log.i(TAG,"[onCreate]Voy a empezar Mapas");
				    	startActivity(new Intent(getApplicationContext(),ScreenMap.class));
				    }catch(Exception ex)
				    {
				    	Log.e(TAG,"[onCreate]Exception Start Mapas: "+ex);
				    }	
				}
				 
				 
		     }
			 
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
	
	