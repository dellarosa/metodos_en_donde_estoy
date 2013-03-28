package entities.wk;

import java.util.ArrayList;
import java.util.Random;

import metodos.MetodosRequest;
import metodos.RequestTask;

import com.google.gson.Gson;

import domain.Category;
import domain.CategoryLocation;
import domain.Definiciones;
import domain.Gps;
import domain.Definiciones.*;
import entities.wk.Files;
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
import entities.wk.R;

public class HowToUse extends Activity {
	
	public static String TAG = "HowToUse:";
	/** Called when the activity is first created. */
	public String strImeiID=null; 
	private String pathFiles="/CYS/";
    private String strNameFileCode="cyscod.txt";
	private Files objFiles = new Files();
	
	  CheckBox checkBoxRastreo; 
	  CheckBox checkBoxNear;
	 RequestTask objT;
     private EditText etxUser;
     private EditText etxPass;     
      Button buttonstart;
     boolean TimerState;
     
     
     /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public HowToUse()
	{
		Log.i(TAG,"[HowToUse] CONSTRUCTOR");
	}
     public String getDeviceID() 
    {
    	  TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
    	  String DEVICE_ID = TelephonyMgr.getDeviceId();
    	  return DEVICE_ID;
    }

	

	public void onCreate(Bundle savedInstanceState) 
	{	 
		
		 super.onCreate(savedInstanceState);
		 Log.i(TAG,"[onCreate] DENTRO ONCREATE");
	     setContentView(R.layout.howtouse);
	     
	    
	     final MetodosRequest metrequest=new MetodosRequest();
	     //TODO ACTUALIZAR CATEGORIAS DISPONIBLES
	     
	     ArrayList<Category> categorias=metrequest.actualizarCategoriasDisponibles();
	     if(categorias!=null)
	     {
	    	 SharedPreferences.Editor editorcat;
    		 SharedPreferences sharedcategorias = getSharedPreferences("categorias",MODE_PRIVATE);	;
    		 
	    	 for(Category categ:categorias)
	    	 { 
		    	 editorcat=sharedcategorias.edit();
		    	 editorcat.putString(categ.getCategoryName(), categ.getCategoryName());
	    	 }
	     
	     }
	     
	     
	     
	     
	     
	     ///////////////////////////////////////////////////////////////////////////////////////////////
	     final EditText etxUser=(EditText) findViewById(R.id.EditTextUser);
	     final EditText etxPass=(EditText) findViewById(R.id.EditTextPass);
	     
	     
	     checkBoxRastreo = (CheckBox) findViewById(R.id.checkBoxRastreo);
	     checkBoxNear = (CheckBox) findViewById(R.id.checkBoxNearLocation);
		 	 
		 checkBoxRastreo.setOnCheckedChangeListener(chlistener);
		 checkBoxNear.setOnCheckedChangeListener(chlistener);
	   	 		 
		 Log.i(TAG,"[onCreate] PASE CHECKBOX");
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
			   				SharedPreferences shareduser = getSharedPreferences("userdata",MODE_PRIVATE);	;
			   				SharedPreferences.Editor editor;
							editor = shareduser.edit();
						    			    
						    	//	Toast.makeText(appContext,"Mail ingresado: "+mail.getText().toString(),Toast.LENGTH_SHORT).show();
							
							//TODO HACER LOGIN - ENVIAR GETREQUEST AL SERVIDOR
							
							if(metrequest.verificarUseryPass(etxUser.getText().toString(),etxPass.getText().toString(),getDeviceID()))
							{				    					
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
								    	startActivity(new Intent(getApplicationContext(),ServiceNear.class));
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

	
	/*
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
	*/
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
		 

	@Override
	public void onBackPressed() {
		Log.i("HowToUseHT", "[onBackPressed] onBackPressed");
	    // do nothing.
	}

	
}
