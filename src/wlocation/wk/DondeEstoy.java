package wlocation.wk;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import wlocation.wk.HTService;
import wlocation.wk.R;


public class DondeEstoy extends Activity {
    /** Called when the activity is first created. */

	final static String TAG="DondeEstoy"; 
	//public HTService objService=new HTService();
	//public SharedPreferences ses;//=null;
    //////////////////////////////////////////////////////////////////////

	 
	  public DondeEstoy()
	 {
		// ses.edit().clear();
		 
	 }
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {         
		 Log.i(TAG,"[onCreate] Oncreate");
         try
         {
        	 super.onCreate(savedInstanceState);	 
        	 Log.i(TAG,"[onCreate] Super");
           		
    	     //setContentView(R.layout.dondeestoy);
    	     
        	 SharedPreferences settings ; //= null;
        	// settings.edit().clear();        	 
        	 settings = getSharedPreferences("HT",MODE_PRIVATE);
        	 Log.i(TAG,"[onCreate] getsharedPreference");
        	 
        	 
		        if(!settings.contains("StartService"))			//FD PARA QUE PASE SIEMPRE POR LA PANTALLA  INICIAL.
		        {
		        	Log.i(TAG,"[onCreate] Not Contain StartService");
		        	try
		        	{
		        		Intent IntHow=null;
		        		try
		        		{
		        			IntHow=new Intent(this.getApplicationContext(),HowToUse.class);
		        		}catch(Exception exc)
		        		{		        			
		        			Log.e(TAG,"[onCreate] IntentHow Excp: "+exc);
		        		}
		        		try
		        		{
		        			startActivity(IntHow);
		        		}catch(Exception excp)
		        		{
		        			Log.e(TAG,"[onCreate] StartAct How Exception: "+excp);
		        		}
		        		//startActivity(new Intent(cont,HowToUseHt.class));
		        	}catch(Exception ex)
		        	{
		        		Log.e(TAG,"[onCreate] StartAct Exception: "+ex);		        	
		        	}
		        	//Thread.sleep(1000);
		        }else
		        {
		       
			        try
			        {
			        	Log.i(TAG,"[onCreate] Continues");
				        setContentView(R.layout.viewsender);
				        
				        Thread.sleep(100,10);
				        TextView txtImei = (TextView) findViewById(R.id.TextViewNuevoUsuario2);
				        TextView txtCode = (TextView) findViewById(R.id.TextViewCode2);
				        txtImei.setText(settings.getString("ImeiID",null));
				        txtCode.setText(txtCode.getText()+settings.getString("CodeActiv",null));
				        	       
				        Log.i(TAG,"[onCreate] Servicio comenzando...");
				        
				      	//startActivity(new Intent(cont,HTService.class));
				       	Intent myIntent=null;
				    	try
				       	{
				    		//myIntent = new Intent(cont, HTService.class);
				    		
				    		 myIntent=new Intent(this.getApplicationContext(),HTService.class);
				    		
				       	}catch(Exception ex)
				         {
				        	 Log.e(TAG,"[onCreate] ERROR I EX: "+ex);		        
				         }
				    		// objGPSData.updateCoordinates(objService.dbLat,objService.dbLon,objService.dbAlt,objService.flSpeed,objService.Imei,objService.strLevelbat);
				        //startService(myIntent);
				       	
				       	try
				       	{
				       		startActivity(myIntent);
				         }catch(Exception ex)
				         {
				        	 Log.e(TAG,"[onCreate] ERROR A EX: "+ex);
				         }
				       	
			        }catch(Exception Exc)
			        {
			       	 Log.e(TAG,"[onCreate] ERROR Try 2 EX: "+Exc);
			        }
		        }
         }catch(Exception ex)
         {
        	 Log.e(TAG,"[onCreate] ERROR EX: "+ex);
         }
        
    }    
    
	@Override
	protected void onStop() {
		
		super.onStop();
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////
@Override
public void onBackPressed() {
Log.i(TAG, "[onBackPressed] onBackPressed");
// do nothing.
}

}