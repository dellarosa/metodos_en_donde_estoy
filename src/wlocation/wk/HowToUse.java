package wlocation.wk;

import java.util.Random;

import metodos.RequestTask;

import com.google.gson.Gson;

import domain.CategoryLocation;
import domain.Definiciones;
import domain.Gps;
import domain.Definiciones.*;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import wlocation.wk.Files;
import wlocation.wk.R;

public class HowToUse extends Activity {
	
	public static String TAG = "HowToUse:";
	/** Called when the activity is first created. */
	public String strImeiID=null; 
	private String pathFiles="/CYS/";
    private String strNameFileCode="cyscod.txt";
	private Files objFiles = new Files();
	
	 private CheckBox checkBoxRastreo; 
	 private CheckBox checkBoxNear;
	 RequestTask objT;
     private EditText etxUser=(EditText) findViewById(R.id.EditTextUser);
     private EditText etxPass=(EditText) findViewById(R.id.EditTextPass);     
     private Button buttonstart;
     boolean TimerState;
     boolean bologin=false;
     
     /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getDeviceID() 
    {
    	  TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
    	  String DEVICE_ID = TelephonyMgr.getDeviceId();
    	  return DEVICE_ID;
    }

	
	private OnCheckedChangeListener chlistener =new OnCheckedChangeListener() {			 
		 public void onCheckedChanged(CompoundButton buttonview, boolean isChecked) {
			 if(isChecked){
				 Log.i(TAG,"[onCreate] IS CHECKED");
				 switch(buttonview.getId())
				 {
				     case R.id.checkBoxNearLocation:
				    	 Log.i(TAG,"[onCreate] NEAR IS CHECKED");
				    	 checkBoxNear.setChecked(true);
				    	 checkBoxRastreo.setChecked(false);
				          
				          break;
				     case R.id.checkBoxRastreo:
				    	 Log.i(TAG,"[onCreate] RASTREO IS CHECKED");
				          checkBoxRastreo.setChecked(true);
				          checkBoxNear.setChecked(false);				          
				          break;				        	 
				 }
			 }			  
		 }};
	public void onCreate(Bundle savedInstanceState) 
	{	    
		 super.onCreate(savedInstanceState);
	
	     setContentView(R.layout.howtouse);
	 

	     checkBoxRastreo = (CheckBox) findViewById(R.id.checkBoxRastreo);
	     checkBoxNear = (CheckBox) findViewById(R.id.checkBoxNearLocation);
		    
			 
			 checkBoxRastreo.setOnCheckedChangeListener(chlistener);
			 checkBoxNear.setOnCheckedChangeListener(chlistener);
	   	 		 
	  
	    buttonstart = (Button) findViewById(R.id.buttonEmpezarAhora);
	    buttonstart.setOnClickListener(new OnClickListener()
	    {     

			public void onClick(View v) {
				
				Log.i(TAG,"[onCreate]Dentro OnClick");
				
			   	if((etxUser.getText()!=null)&&(etxPass.getText()!=null)&&(etxUser.getText().toString().length()>5))
			   	{
			   		if(etxPass.getText().toString().length()==Definiciones.Definicionesgenerales.incantdigitosPass)
				   	{
			   			if((checkBoxRastreo.isChecked())||(checkBoxNear.isChecked()))
				   		{
				   			
							SharedPreferences settings = getSharedPreferences("HT",MODE_PRIVATE);			   
							SharedPreferences.Editor editor = settings.edit();
						    			    
						    	//	Toast.makeText(appContext,"Mail ingresado: "+mail.getText().toString(),Toast.LENGTH_SHORT).show();
							
							//TODO HACER LOGIN - ENVIAR GETREQUEST AL SERVIDOR
							if(verificarUseryPass(etxUser.getText().toString(),etxPass.getText().toString(),getDeviceID()))
							{
							    editor.putString("HT_Start", "OK" );					    					
							  //  editor.putString("CodeActiv",fnlstrCodeAct  );		//FD v19.3.13				    					
							    editor.putString("Imei",getDeviceID());
							    editor.putString("User",etxUser.getText().toString());
							    editor.putString("Pass",etxPass.getText().toString());
							    if(checkBoxRastreo.isChecked())
							    {
							    	try
								    {
								    	Log.e(TAG,"[onCreate]Voy a empezar Welcome Rastreo");
								    	startActivity(new Intent(getApplicationContext(),WelcomeActivity.class));
								    }catch(Exception ex)
								    {
								    	Log.e(TAG,"[onCreate]Exception startAct Welcome Rastreo: "+ex);
								    }	
							    }else
							    {
							    	//TODO Reemplazar por activity de near location
							    	try
								    {
								    	Log.e(TAG,"[onCreate]Voy a empezar Near Location");
								    	startActivity(new Intent(getApplicationContext(),WelcomeActivity.class));
								    }catch(Exception ex)
								    {
								    	Log.e(TAG,"[onCreate]Exception startAct How: "+ex);
								    }
							    }
							    		
							    editor.commit();
								
							    
							    //		startActivity(new Intent(appContext,PositionCYS.class));   
							}else
							{
								Log.i(TAG,"[onCreate]USUARIO NO EXISTE, O NO SE PUEDE CONECTAR O MAL INGRESO DE DATOS...");		//TODO discernir si se quiere estandarizar el error		
							}
				   		}else
				   		{
				   			
				   			Log.i(TAG,"[onCreate]CHECKBOX SIN SELECCION");
				   			return;
				   		}
			   		}else
			   		{
			   			Log.i(TAG,"[onCreate]Cantidad de DIGITOS PASSWORD INCORRECTA");
			   			return;
			   		}
			   		
			   	}else
			   	{
			   		Log.i(TAG,"[onCreate]USER O PASS VACIO");
			   		return;
			   	}		
			}
	    });
	    // TODO Auto-generated method stub
	}

	public boolean verificarUseryPass(String struser,String strpass,String imei)
	{			    			   	 
   		final Gson gson = new Gson();
   		//final String json = gson.toJson();
   		//String urlCatLoc = new String("http://sharedpc.dnsalias.com:3001/location_points/user=cepita@gmail.com&pass=2345pepe");
   		String urlCatLoc = new String("http://sharedpc.dnsalias.com:3001/location_points/user="+struser+"&pass="+strpass+"&imei="+imei);
   		
   		Log.i(TAG, "[verificarUseryPass] ENVIAR URL: "+urlCatLoc );		//DEBUG
        try
        {
        	objT = (RequestTask) new RequestTask().execute(urlCatLoc);
        }catch(Exception ex)
        {
        	Log.i(TAG, "[Handler] REQUEST EXCEPTION: "+ex );		//DEBUG
        	if(buttonstart.isSelected())
        	{
        		buttonstart.setSelected(false);
        	}
        	return false;
        }
        /////////////////////////////////////////////////////////////////////////////
      
        try
        {	
	       TimerState = false;	    
	       
    	   new CountDownTimer(5000, 1000) {
    		     public void onTick(long millisUntilFinished) {			    		         
    		         Log.i(TAG, "[verificarUseryPass] ONTICK: "+millisUntilFinished/1000);		//DEBUG
    		     }
    		     public void onFinish() {
    		    	 Log.i(TAG, "[verificarUseryPass] TIMER DONE");		//DEBUG
    		    	 TimerState=true;    
    		    	 if(objT.getResponse()==null)
    		    	 {
    		    		 Log.i(TAG, "[verificarUseryPass] RESPONSE NULL");		//DEBUG
    		    		 bologin=false;
    		    		 return;
    		    	 }else		//////////////OBTUVE RESPUESTA DE ENVIO... CATEGORIAS ...
    		    	 {
    		    		 
    		    		 Log.i(TAG, "[verificarUseryPass] RESPONSE : "+objT.getResponse());		//DEBUG
    		    		 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    		    		
						Logged objlogin=gson.fromJson(objT.getResponse(), Logged.class);
						
		    			if(objlogin.getinlogin()==1) //LOGUEADO		    				
		    			{
		    				bologin=true;
		    			}else	//NO LOGUEADO
		    			{
		    				bologin=false;
		    			}	    		    			
		    			
    		    	 }
    		    	 buttonstart.setSelected(false);
    		     }
    		  }.start();

	       /// USO UN THREAD ASINCRONICO PERO NO QUIERO QUE REALICE OTRA COSA SI NOT ENGO LA RESPUESTA,
	       /// DE ESTE MODO MANEJO LA RESPUESTA 
        }catch(Exception ex)
        {
        	Log.i(TAG, "[Handler] Exception: "+ex);		//DEBUG
        	if(buttonstart.isSelected())
        	{
        		buttonstart.setSelected(false);
        	}bologin=false;
        	return bologin;
        }
        return bologin;
	}
	public class Logged
	{
		public Logged()
		{}
		int inlogged;
		public int getinlogin()
		{
			return this.inlogged;
		}
	}
	@Override
	public void onBackPressed() {
		Log.i("HowToUseHT", "[onBackPressed] onBackPressed");
	    // do nothing.
	}

	
}
