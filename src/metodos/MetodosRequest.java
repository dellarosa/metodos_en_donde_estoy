package metodos;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.util.Log;

import com.google.gson.Gson;

import domain.Category;
import domain.CategoryLocation;
import domain.Definiciones;
import domain.Gps;
import entities.wk.ServiceControl;

public class MetodosRequest {

	String TAG="metodosRequest";
	RequestTask objT;
	boolean TimerState;
	boolean boresponse=false;
	
	public boolean verificarUseryPass(String struser,String strpass,String imei)		//PUEDE SER Q ENVIE OBJETOS 
	{			    			   	 
   		final Gson gson = new Gson();
   		//final String json = gson.toJson();
   		//String urlCatLoc = new String("http://sharedpc.dnsalias.com:3001/location_points/user=cepita@gmail.com&pass=2345pepe");
   		String urlLoc = new String(Definiciones.Definicionesgenerales.servidor+"/location_points/user="+struser+"&pass="+strpass+"&imei="+imei);
   		
   		Log.i(TAG, "[verificarUseryPass] ENVIAR URL: "+urlLoc );		//DEBUG
        try
        {
        	objT = (RequestTask) new RequestTask().execute(urlLoc);
        }catch(Exception ex)
        {
        	Log.i(TAG, "[Handler] REQUEST EXCEPTION: "+ex );		//DEBUG
        	return false;
        }
       try
       { 
	        if(objT.getResponse()==null)
		   	 {
		   		 Log.i(TAG, "[verificarUseryPass] RESPONSE NULL");		//DEBUG
		   		 boresponse=false;
		   		 return boresponse;
		   	 }else		//////////////OBTUVE RESPUESTA DE ENVIO... CATEGORIAS ...
		   	 {			   		 
		   		 Log.i(TAG, "[verificarUseryPass] RESPONSE : "+objT.getResponse());		//DEBUG
		   		 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		   		
		   		 responseYesOrNot objresponseyesornot=gson.fromJson(objT.getResponse(), responseYesOrNot.class);
					if(objresponseyesornot.getResponseYesOrNot()==1) //LOGUEADO		    				
					{
						boresponse=true;
					}else	//NO LOGUEADO
					{
						boresponse=false;
					}	
		   	 }
        }catch(Exception ex)
        {
        	Log.i(TAG, "[Handler] Exception: "+ex);		//DEBUG
        	boresponse=false;
        }
        return boresponse;		//Podria devolver un objeto de determinada clase
	}
		
    public Collection<CategoryLocation> obtenerLocacionesCercanas(Gps gpsloc)
    {
    	try
		{   
			if((gpsloc!=null)&&((gpsloc.getLatitud()!=0)||(gpsloc.getLongitud()!=0)))
			{
				Log.i(TAG, "[Handler] HAY LATITUD Y/O LONGITUD");							//DEBUG
		   			    			   	 
		   		final Gson gson = new Gson();	    			   		
		   		//Gps objGps=new Gps("AndCYS",dbLat,dbLon,dbAlt,flSpeed,Imei,settings.getString("CodeActiv",null),strLevelbat);
		   		
		   		Log.i(TAG,"DATOS GPS: "+gpsloc.getLatitud()+" "+gpsloc.getLongitud());
		   		final String json = gson.toJson(gpsloc); 
		   		
		   		//String urlCatLoc = new String("http://192.168.252.129:3333/location_points/near_location_points.json?lat=-34.593968&lng=-58.413883");
		   		//String urlCatLoc = new String("http://192.168.252.129:3333/location_points/near_location_points.json?"+json);
		   		//String urlCatLoc = new String("http://sharedpc.dnsalias.com:3001/location_points/near_location_points.json?"+json);
		   		String urlCatLoc = new String(Definiciones.Definicionesgenerales.servidor+"/location_points/near_location_points.json?alt=0.0&battery=50&code=CYS172827&id=AndCYS&imei=000000000000003&lat=-34.593968&lng=-58.413882&vel=0.0");
		   		
		   		Log.i(TAG, "[Handler] ENVIAR URL: "+urlCatLoc );		//DEBUG
		        try
		        {
		        	objT = (RequestTask) new RequestTask().execute(urlCatLoc);
		        }catch(Exception ex)
		        {
		        	Log.i(TAG, "[Handler] REQUEST EXCEPTION: "+ex );		//DEBUG		        	
		        	return null;
		        }	      
		    		    	
		    	 if(objT.getResponse()==null)
		    	 {
		    		 Log.i(TAG, "[Handler] RESPONSE NULL");		//DEBUG
		    	 }else		//////////////OBTUVE RESPUESTA DE ENVIO... CATEGORIAS ...
		    	 {		    		    		 
		    		 Log.i(TAG, "[Handler] RESPONSE : "+objT.getResponse());		//DEBUG
		    		 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		    		 ///TODO: CATEGORYLocation deberian ser dos clases, pero todavia no probe el gson para dos objetos uno dentro de otro FD v15.3.13
	    			Collection<CategoryLocation> colcategorylocation =new ArrayList<CategoryLocation>();    			
	    			CategoryLocation[] arrcategorylocation = gson.fromJson(objT.getResponse(), CategoryLocation[].class);
	    			
	    			if(arrcategorylocation.length>0)
	    			{
	    				int x=0;
	    				while(x<arrcategorylocation.length)
	    				{
	    					colcategorylocation.add(arrcategorylocation[x]);	    					
	    				}
	    			}else
	    			{
	    				Log.i(TAG,"[Handler] NO HAY CATEGORIAS CERCA");
	    				return null;
	    			}		
	    			
	    			return colcategorylocation;
	    			
		    	 }
			}else
			{
				Log.i(TAG, "[Handler] NO HAY COORDENADADS PARA ENVIAR ");		//DEBUG				
			}
		}catch(Exception ex)
		{
			Log.e(TAG, "[Handler] Exploto todo: "+ex );		//DEBUG
		}
		return null;
	}
	
    public ArrayList<Category> actualizarCategoriasDisponibles()
    {
    	try
		{   
		   		final Gson gson = new Gson();
		   		//final String json = gson.toJson(gpsloc); 
		   		
		   		//TODO ARMAR url de envio para actualizar
		   		String urlCatLoc = new String(Definiciones.Definicionesgenerales.servidor+"/location_points/near_location_points.json?alt=0.0&battery=50&code=CYS172827&id=AndCYS&imei=000000000000003&lat=-34.593968&lng=-58.413882&vel=0.0");
		   		
		   		Log.i(TAG, "[Handler] ENVIAR URL: "+urlCatLoc );		//DEBUG
		        try
		        {
		        	objT = (RequestTask) new RequestTask().execute(urlCatLoc);
		        }catch(Exception ex)
		        {
		        	Log.i(TAG, "[Handler] REQUEST EXCEPTION: "+ex );		//DEBUG		        	
		        	return null;
		        }	      
		    		    	
		    	 if(objT.getResponse()==null)
		    	 {
		    		 Log.i(TAG, "[Handler] RESPONSE NULL");		//DEBUG
		    	 }else		//////////////OBTUVE RESPUESTA DE ENVIO... CATEGORIAS ...
		    	 {		    		    		 
		    		 Log.i(TAG, "[Handler] RESPONSE : "+objT.getResponse());		//DEBUG
		    		 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		    		 ///TODO: CATEGORYLocation deberian ser dos clases, pero todavia no probe el gson para dos objetos uno dentro de otro FD v15.3.13
	    			ArrayList<Category> arrlistcategory =new ArrayList<Category>();    			
	    			Category[] categorias = gson.fromJson(objT.getResponse(), Category[].class);
	    			
	    			if(categorias.length>0)
	    			{
	    				int x=0;
	    				while(x<categorias.length)
	    				{
	    					arrlistcategory.add(categorias[x]);	    					
	    				}
	    			}else
	    			{
	    				Log.i(TAG,"[Handler] NO HAY CATEGORIAS CERCA");
	    				return null;
	    			}		
	    			
	    			return arrlistcategory;
	    			
		    	 }			
		}catch(Exception ex)
		{
			Log.e(TAG, "[Handler] Exploto todo: "+ex );		//DEBUG
		}
		return null;
	}
	
    
	public class userLogged 
	{
		public userLogged()
		{}
		int inState;
		public int getUserLogged()
		{
			return this.inState;
		}
	}
	public class responseYesOrNot
	{
		public responseYesOrNot()
		{}
		int inState;
		public int getResponseYesOrNot()
		{
			return this.inState;
		}
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////7
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	//////// DEPRECATED ////////////////////
	private boolean verificarUseryPass_D(String struser,String strpass,String imei)		//PUEDE SER Q ENVIE OBJETOS 
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
    		    		 boresponse=false;
    		    		 return;
    		    	 }else		//////////////OBTUVE RESPUESTA DE ENVIO... CATEGORIAS ...
    		    	 {
    		    		 
    		    		 Log.i(TAG, "[verificarUseryPass] RESPONSE : "+objT.getResponse());		//DEBUG
    		    		 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    		    		
    		    		 responseYesOrNot objresponseyesornot=gson.fromJson(objT.getResponse(), responseYesOrNot.class);
						
		    			if(objresponseyesornot.getResponseYesOrNot()==1) //LOGUEADO		    				
		    			{
		    				boresponse=true;
		    			}else	//NO LOGUEADO
		    			{
		    				boresponse=false;
		    			}	    		    			
		    			
    		    	 }
    		    	 
    		     }
    		  }.start();

	       /// USO UN THREAD ASINCRONICO PERO NO QUIERO QUE REALICE OTRA COSA SI NOT ENGO LA RESPUESTA,
	       /// DE ESTE MODO MANEJO LA RESPUESTA 
        }catch(Exception ex)
        {
        	Log.i(TAG, "[Handler] Exception: "+ex);		//DEBUG
        	boresponse=false;
        }
        return boresponse;
	}
	
}
