package libreria;

import java.util.ArrayList;

import libreria.ResponseClass.*;

import android.os.CountDownTimer;
import android.util.Log;

import com.google.gson.Gson;



public class MetodosRequest {

	String TAG="metodosRequest";
	RequestTaskAsync objT;
	boolean TimerState;
	boolean boresponse=false;
	
	public void setBooleanResponse(boolean response)
	{
		this.boresponse=response;
	}
	public boolean getBooleanResponse()
	{
		return this.boresponse;
	}
	public void setTimerstate(boolean timerst)
	{
		this.TimerState=timerst;
	}
	public boolean getTimerst()
	{
		return this.TimerState;
	}
	
	/////############################################ VERIFICAR USER Y PASS ##############################################################///
	public boolean verificarUseryPass(String struser,String strpass,String imei)		//PUEDE SER Q ENVIE OBJETOS 
	{			    			   	 
   		final Gson gson = new Gson();
   		//final String json = gson.toJson();
   		//String urlCatLoc = new String("http://sharedpc.dnsalias.com:3001/location_points/user=cepita@gmail.com&pass=2345pepe");
   		String urlLoc = new String(Definiciones.Definicionesgenerales.servidor+"/location_points/user="+struser+"&pass="+strpass+"&imei="+imei);
   		
   		Log.i(TAG, "[verificarUseryPass] ENVIAR URL: "+urlLoc );		//DEBUG
        try
        {
        	objT = (RequestTaskAsync) new RequestTaskAsync().execute(urlLoc);
        }catch(Exception ex)
        {
        	Log.i(TAG, "[verificarUseryPass] REQUEST EXCEPTION: "+ex );		//DEBUG
        	return false;
        }
       try
       { 
    	   this.waitResponse(Definiciones.Definicionesgenerales.tiempoesperaenvio);
	        if(objT.getResponse()==null)
		   	 {
		   		 Log.i(TAG, "[verificarUseryPass] RESPONSE NULL");		//DEBUG
		   		 boresponse=false;
		   		objT.cancel(true);
		   		 return boresponse;
		   	 }else		//////////////OBTUVE RESPUESTA DE ENVIO... CATEGORIAS ...
		   	 {			   		 
		   		 Log.i(TAG, "[verificarUseryPass] RESPONSE : "+objT.getResponse());		//DEBUG
		   		 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		   		
		   		Response_verificarUseryPass objresponseyesornot=gson.fromJson(objT.getResponse(), Response_verificarUseryPass.class);
					if(objresponseyesornot.getCode()==000) //LOGUEADO		    				
					{
						boresponse=true;
					}else	//NO LOGUEADO
					{
						boresponse=false;
					}	
		   	 }
        }catch(Exception ex)
        {
        	Log.i(TAG, "[verificarUseryPass] Exception: "+ex);		//DEBUG
        	boresponse=false;
        }
        return boresponse;		//Podria devolver un objeto de determinada clase
	}
	///########################################## CREATE ################################################################///
	//
	//################################################################################################################
	public boolean crearNuevoDevice(String namedevice,String categoria )		//PUEDE SER Q ENVIE OBJETOS 
	{			    			   	 
   		final Gson gson = new Gson();
   		//final String json = gson.toJson();
   		//String urlCatLoc = new String("http://sharedpc.dnsalias.com:3001/location_points/user=cepita@gmail.com&pass=2345pepe");
   		String urlLoc = new String(Definiciones.Definicionesgenerales.servidor+"/devices/create.json?name="+namedevice+"&cat="+categoria);
   		
   		Log.i(TAG, "[crearNuevoDevice] ENVIAR URL: "+urlLoc );		//DEBUG
        try
        {
        	objT = (RequestTaskAsync) new RequestTaskAsync().execute(urlLoc);
        }catch(Exception ex)
        {
        	Log.i(TAG, "[crearNuevoDevice] REQUEST EXCEPTION: "+ex );		//DEBUG
        	return false;
        }
       try
       { 
    	   this.waitResponse(Definiciones.Definicionesgenerales.tiempoesperaenvio);
	        if(objT.getResponse()==null)
		   	 {
		   		 Log.i(TAG, "[crearNuevoDevice] RESPONSE NULL");		//DEBUG
		   		objT.cancel(true);
		   		 boresponse=false;
		   		 return boresponse;
		   	 }else		//////////////OBTUVE RESPUESTA DE ENVIO... CATEGORIAS ...
		   	 {			   		 
		   		 Log.i(TAG, "[crearNuevoDevice] RESPONSE : "+objT.getResponse());		//DEBUG
		   		 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		   		
		   		Response_CrearNuevoDeviceCategory responsenuevodevicecat=gson.fromJson(objT.getResponse(), Response_CrearNuevoDeviceCategory.class);
					if(responsenuevodevicecat.getCode()==Definiciones.Definicionesgenerales.SUCCESS) //LOGUEADO		    				
					{
						boresponse=true;
					}else	//NO LOGUEADO
					{
						boresponse=false;
					}	
		   	 }
        }catch(Exception ex)
        {
        	Log.i(TAG, "[crearNuevoDevice] Exception: "+ex);		//DEBUG
        	boresponse=false;
        }
        return boresponse;		//Podria devolver un objeto de determinada clase
	}
	///######################################## NEAR LOCATION ##################################################################///	
	//
	//############################################################################################################
	public ArrayList<CategoryPoints> obtenerLocacionesCercanas(Gps gpsloc)
    {
    	try
		{   
			if((gpsloc!=null)&&((gpsloc.getLatitud()!=0)||(gpsloc.getLongitud()!=0)))
			{
				Log.i(TAG, "[obtenerLocacionesCercanas] HAY LATITUD Y/O LONGITUD");							//DEBUG
		   			    			   	 
		   		final Gson gson = new Gson();	    			   		
		   		//Gps objGps=new Gps("AndCYS",dbLat,dbLon,dbAlt,flSpeed,Imei,settings.getString("CodeActiv",null),strLevelbat);
		   		
		   		Log.i(TAG,"DATOS GPS: "+gpsloc.getLatitud()+" "+gpsloc.getLongitud());
		   		final String json = gson.toJson(gpsloc); 

		   		//String urlCatLoc = new String("Definiciones.Definicionesgenerales.servidor/locations/find_near_locations?"+json);
		   		//String urlCatLoc = new String(Definiciones.Definicionesgenerales.servidor+"/locations/find_near_locations?id=000000000000003&lat=-34.593968&lng=-58.413882");
		   		String urlCatLoc = new String(Definiciones.Definicionesgenerales.servidor+"/location_points/near_location_points.json?lat="+gpsloc.getLatitud()+"&lng="+gpsloc.getLongitud());
		   		
		   		Log.i(TAG, "[obtenerLocacionesCercanas] ENVIAR URL: "+urlCatLoc );		//DEBUG
		        try
		        {
		        	objT = (RequestTaskAsync) new RequestTaskAsync().execute(urlCatLoc);
		        }catch(Exception ex)
		        {
		        	Log.i(TAG, "[obtenerLocacionesCercanas] REQUEST EXCEPTION: "+ex );		//DEBUG		        	
		        	return null;
		        }	      
		        this.waitResponse(Definiciones.Definicionesgenerales.tiempoesperaenvio); 	
		    	 if(objT.getResponse()==null)
		    	 {
		    		 Log.i(TAG, "[obtenerLocacionesCercanas] RESPONSE NULL");		//DEBUG
		    		 objT.cancel(true);
		    		 return null;
		    	 }else		//////////////OBTUVE RESPUESTA DE ENVIO... CATEGORIAS ...
		    	 {		    		    		 
		    		 Log.i(TAG, "[obtenerLocacionesCercanas] RESPONSE : "+objT.getResponse());		//DEBUG
		    		 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		    		 ///TODO: CATEGORYLocation deberian ser dos clases, pero todavia no probe el gson para dos objetos uno dentro de otro FD v15.3.13
	    			    			
		    		 Response_obtenerLocacionesCercanas nearlocat = gson.fromJson(objT.getResponse(), Response_obtenerLocacionesCercanas.class);
	    			
		    		if(nearlocat.getCode()==Definiciones.Definicionesgenerales.SUCCESS)
		    		{    				    			
		    			return nearlocat.getCategoryPoints();
		    		}
		    		return null;	    			
		    	 }
			}else
			{
				Log.i(TAG, "[obtenerLocacionesCercanas] NO HAY COORDENADADS PARA ENVIAR ");		//DEBUG				
			}
		}catch(Exception ex)
		{
			Log.e(TAG, "[obtenerLocacionesCercanas] Exploto todo: "+ex );		//DEBUG
		}
		return null;
	}
  ///############################################## UPDATE LOCATION ############################################################///
	//
	//############################################################################################################
	public boolean actualizarPosicion(Gps gpsloc)
    {
    	try
		{   
			if((gpsloc!=null)&&((gpsloc.getLatitud()!=0)||(gpsloc.getLongitud()!=0)))
			{
				Log.i(TAG, "[updateLocation] HAY LATITUD Y/O LONGITUD");							//DEBUG
		   			    			   	 
		   		final Gson gson = new Gson();	    			   		
		   		//Gps objGps=new Gps("AndCYS",dbLat,dbLon,dbAlt,flSpeed,Imei,settings.getString("CodeActiv",null),strLevelbat);
		   		
		   		Log.i(TAG,"DATOS GPS: "+gpsloc.getLatitud()+" "+gpsloc.getLongitud());
		   		final String json = gson.toJson(gpsloc); 
		   		
		   		//String urlCatLoc = new String("http://192.168.252.129:3333/location_points/near_location_points.json?lat=-34.593968&lng=-58.413883");
		   		//String urlCatLoc = new String("http://192.168.252.129:3333/location_points/near_location_points.json?"+json);
		   		//String urlCatLoc = new String("http://sharedpc.dnsalias.com:3001/update_location/near_location_points.json?"+json);
		   		String urlGPSupdate = new String(Definiciones.Definicionesgenerales.servidor+"/update_location/update_location.json?alt=0.0&battery=50&code=CYS172827&id=AndCYS&imei=000000000000003&lat=-34.593968&lng=-58.413882&vel=0.0");
		   		
		   		Log.i(TAG, "[updateLocation] ENVIAR URL: "+urlGPSupdate );		//DEBUG
		        try
		        {
		        	objT = (RequestTaskAsync) new RequestTaskAsync().execute(urlGPSupdate);
		        }catch(Exception ex)
		        {
		        	Log.i(TAG, "[updateLocation] REQUEST EXCEPTION: "+ex );		//DEBUG		        	
		        	return false;
		        }	      
		        this.waitResponse(Definiciones.Definicionesgenerales.tiempoesperaenvio);
		    	 if(objT.getResponse()==null)
		    	 {
		    		 Log.i(TAG, "[updateLocation] RESPONSE NULL");		//DEBUG
		    		 objT.cancel(true);
		    		 return false;
		    	 }else		//////////////OBTUVE RESPUESTA DE ENVIO... CATEGORIAS ...
		    	 {		    		    		 
		    		 Log.i(TAG, "[updateLocation] RESPONSE : "+objT.getResponse());		//DEBUG
		    		 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		    		 ///TODO: CATEGORYLocation deberian ser dos clases, pero todavia no probe el gson para dos objetos uno dentro de otro FD v15.3.13
		    		 
		    		 ResponseClass.Response_actualizarPosicion responseupdate = gson.fromJson(objT.getResponse(), ResponseClass.Response_actualizarPosicion.class);
	    				    			
		    		 if(responseupdate.getCode()==Definiciones.Definicionesgenerales.SUCCESS)
		    		 {
		    			 return true;
		    		 }  			
		    	 }
			}else
			{
				Log.i(TAG, "[updateLocation] NO HAY COORDENADADS PARA ENVIAR ");		//DEBUG				
			}
		}catch(Exception ex)
		{
			Log.e(TAG, "[updateLocation] Exploto todo: "+ex );		//DEBUG
		}
		return false;
	}
   
    ///######################################### ACTUALIZAR CATEGORIAS DISPONIBLES #################################################################///
	//
	//############################################################################################################
	public ArrayList<Categoria> descargarCategoriasDisponibles(String strversion)
    {
    	
    	try
		{   
		   		final Gson gson = new Gson();
		   		//final String json = gson.toJson(gpsloc); 
		   		strversion="01.01";
		   		//TODO ARMAR url de envio para actualizar
		   		String urlCatLoc = new String(Definiciones.Definicionesgenerales.servidor+"/categorias_disponibles/categorias_disponibles.json?vers="+strversion);
		   		
		   		Log.i(TAG, "[actualizarCategoriasDisponibles] ENVIAR URL: "+urlCatLoc );		//DEBUG
		        try
		        {
		        	objT = (RequestTaskAsync) new RequestTaskAsync().execute(urlCatLoc);
		        	
		        	
		        }catch(Exception ex)
		        {
		        	Log.i(TAG, "[actualizarCategoriasDisponibles] REQUEST EXCEPTION: "+ex );		//DEBUG		        	
		        	return null;
		        }	      
		         this.waitResponse(Definiciones.Definicionesgenerales.tiempoesperaenvio);	
		    	 if(objT.getResponse()==null)
		       //  if(reqsync.Request(urlCatLoc)==null)
		    	 {
		    		 Log.i(TAG, "[actualizarCategoriasDisponibles] RESPONSE NULL");		//DEBUG
		    		 objT.cancel(true);
		    		 return null;
		    	 }else		//////////////OBTUVE RESPUESTA DE ENVIO... CATEGORIAS ...
		    	 {		    		    		 
		    		 Log.i(TAG, "[actualizarCategoriasDisponibles] RESPONSE : "+objT.getResponse());		//DEBUG
		    		 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		    		 ///TODO: Corregir	    			    			
		    		 Response_actualizarCategoriasDisponibles categoriaslist = gson.fromJson(objT.getResponse(), Response_actualizarCategoriasDisponibles.class);
	    			
	    			if(categoriaslist.getCode()==Definiciones.Definicionesgenerales.SUCCESS)
	    			{
	    				return categoriaslist.getCategoryList();
	    			}else
	    			{
	    				Log.i(TAG,"[actualizarCategoriasDisponibles] NO HAY CATEGORIAS DISPONIBLES");	    				
	    			}	    			
		    	 }			
		}catch(Exception ex)
		{
			Log.e(TAG, "[Handler] Exploto todo: "+ex );		//DEBUG
		}
		return null;
	}
	
    
  ///######################################### WAIT RESPONSE #################################################################///
	//
	//############################################################################################################
	public boolean waitResponse(int inseconds)
	{
		new CountDownTimer(inseconds*1000, 1000) {
		     public void onTick(long millisUntilFinished) {			    		         
		         Log.i(TAG, "[waitResponse] ONTICK: "+millisUntilFinished/1000);		//DEBUG
		     }
		     public void onFinish() {
		    	 Log.i(TAG, "[waitResponse] TIMER DONE");		//DEBUG
		    	 
		     }
		  }.start();
		  
		  return true;
	}
}
