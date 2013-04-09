package entities.wk;


import libreria.Definiciones;

import libreria.MetodosGral;
import libreria.MetodosRequest;
import libreria.RequestTaskAsync;

//import domain.Files;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import entities.wk.R;

public class Loggin extends Activity {
	
	public static String TAG = "Loggin:";
	/** Called when the activity is first created. */
	public String strImeiID=null; 
//	private String pathFiles="/CYS/";
//    private String strNameFileCode="cyscod.txt";
//	private Files objFiles = new Files();
	
	  CheckBox checkBoxRastreo; 
	  CheckBox checkBoxNear;
	 RequestTaskAsync objT;
     //private EditText etxUser;
//     private EditText etxPass;     
      Button buttonstart;
     boolean TimerState;
     MetodosGral metgrl;
     MetodosRequest metreq;
     
     /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Loggin()
	{
		Log.i(TAG,"[Loggin] CONSTRUCTOR");
	}

	public void onCreate(Bundle savedInstanceState) 
	{	 
		
		 super.onCreate(savedInstanceState);
		 Log.i(TAG,"[onCreate] DENTRO ONCREATE");
	     setContentView(R.layout.howtouse);
	     metgrl=new MetodosGral(this);
	     metreq =new MetodosRequest();
 	     // ///
	     metgrl.actualizarCategorias();
	     //Log.i(TAG,"[onCreate] PASE ACT");				//DEBUG
	     	
	     /////////////////////////// DEBUG /////////////////////////////////////
	    		
	    	///////////////////////////////////////////////////////////////////
	    	
	     ///////////////////////////////////////////////////////////////////////////////////////////////
	     final EditText etxUser=(EditText) findViewById(R.id.EditTextUser);
	     final EditText etxPass=(EditText) findViewById(R.id.EditTextPass);
	     
	     
	     checkBoxRastreo = (CheckBox) findViewById(R.id.checkBoxRastreo);
	     checkBoxNear = (CheckBox) findViewById(R.id.checkBoxNearLocation);
		 	 
		 checkBoxRastreo.setOnCheckedChangeListener(chlistener);
		 checkBoxNear.setOnCheckedChangeListener(chlistener);
	   	 		 
		 Log.i(TAG,"[onCreate] PASE CHECKBOX");
	    buttonstart = (Button) findViewById(R.id.buttonEmpezarAhora);
	    
	    try
	    {
	    	Log.i(TAG,"[onCreate]Voy a empezar Near Location");
	    	startActivity(new Intent(getApplicationContext(),ServiceNear.class));
	    }catch(Exception ex)
	    {
	    	Log.e(TAG,"[onCreate]Exception startAct How: "+ex);
	    }	    
	    
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
			   				SharedPreferences.Editor editor= shareduser.edit();
						    			    
						    	//	Toast.makeText(appContext,"Mail ingresado: "+mail.getText().toString(),Toast.LENGTH_SHORT).show();
							
							//TODO HACER LOGIN - ENVIAR GETREQUEST AL SERVIDOR
							
							if(metreq.verificarUseryPass(etxUser.getText().toString(),etxPass.getText().toString(),metgrl.getDeviceID()))									    					
							{  //  editor.putString("CodeActiv",fnlstrCodeAct  );		//FD v19.3.13				    					
							    editor.putString("Imei",metgrl.getDeviceID());
							    editor.putString("User",etxUser.getText().toString());
							    editor.putString("Pass",etxPass.getText().toString());
							    editor.commit();
							    if(checkBoxRastreo.isChecked())
							    {
							    	try
								    {
								    	Log.i(TAG,"[onCreate]Voy a empezar Welcome Rastreo");
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
								    	Log.i(TAG,"[onCreate]Voy a empezar Near Location");
								    	startActivity(new Intent(getApplicationContext(),ServiceNear.class));
								    }catch(Exception ex)
								    {
								    	Log.e(TAG,"[onCreate]Exception startAct How: "+ex);
								    }
							    }
							    		
							   
								
							    
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
