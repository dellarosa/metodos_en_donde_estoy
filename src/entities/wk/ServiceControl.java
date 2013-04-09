package entities.wk;


import java.util.Collection;

import libreria.CategoryPoints;
import libreria.Gps;
import libreria.MetodosRequest;
import libreria.RequestTaskAsync;



import entities.wk.ServiceControl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.os.Handler;
import android.os.IBinder;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.content.BroadcastReceiver;
import entities.wk.R;



public class ServiceControl extends Activity  implements Runnable
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
	RequestTaskAsync objT;
	boolean flagNewLocation;
	Gps gpsnews=new Gps();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ServiceControl()
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
        	
        	//startUpdateCoordinates();
        		   		
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
    				///TODO OBTENER COORDENADAS PARA ENVIAR AL SERVIDOR
    				MetodosRequest metrequest=new MetodosRequest();
    				Collection<CategoryPoints> colcatloc=metrequest.obtenerLocacionesCercanas(gpsnews);
    				
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
	
	
	///////////////////////////////////////////////////// Set View //////////////////////////////
	public void voSetView()
	{
		 SharedPreferences settings ;
     	 settings = getSharedPreferences("HT",MODE_PRIVATE);
     	 
		setContentView(R.layout.servicecontrol);
		
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
    		
    		 myIntent=new Intent(this.getApplicationContext(),ServiceControl.class);
	    		
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
