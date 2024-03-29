package libreria;

import java.util.ArrayList;

import org.json.JSONObject;

import libreria.ResponseClass.*;

import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



public class MetodosRequest {

	/*
	String TAG="metodosRequest";
	RequestTaskAsync objT;
	boolean TimerState;
	boolean boresponse=false;
	boolean botermine;
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
	        ////////
	        CountDownTimer objContTime=  this.waitResponse(Definiciones.Definicionesgenerales.tiempoesperaenvio);	//OPTIMIZAR
	        while((!botermine)&&(objT.getResponse()==null))
	        {  	;      }
	        ////////
	        
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
   		//String urlLoc = new String(Definiciones.Definicionesgenerales.servidor+"/devices/create.json?name="+namedevice+"&cat="+categoria);
   		String urlLoc = new String(Definiciones.Definicionesgenerales.servidor+"/api/devices/create?name="+namedevice+"&cat="+categoria);		//INCOMPLETO
   		//data: {name: "Garrahan", description: "Hospital de ninios", category_id: 1, type_id: 1} }) 	//INCOMPLETO
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
	        ////////
	        CountDownTimer objContTime=  this.waitResponse(Definiciones.Definicionesgenerales.tiempoesperaenvio);	//OPTIMIZAR
	        while((!botermine)&&(objT.getResponse()==null))
	        {  	;      }
	        ////////
	        
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
	public ArrayList<CategoryPoints> obtenerLocacionesCercanas(Gps gpsloc,String strcategoria)
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
		   		//String urlCatLoc = new String(Definiciones.Definicionesgenerales.servidor+"/location_points/near_location_points.json?lat="+gpsloc.getLatitud()+"&lng="+gpsloc.getLongitud()+"&strcategoria");
		   		String urlCatLoc = new String(Definiciones.Definicionesgenerales.servidor+"/api/locations/find_near_locations/"+gpsloc.getLatitud()+"/"+gpsloc.getLongitud()+"/"+strcategoria);
		   		
		   		Log.i(TAG, "[obtenerLocacionesCercanas] ENVIAR URL: "+urlCatLoc );		//DEBUG
		        try
		        {
		        	objT = (RequestTaskAsync) new RequestTaskAsync().execute(urlCatLoc);
		        }catch(Exception ex)
		        {
		        	Log.i(TAG, "[obtenerLocacionesCercanas] REQUEST EXCEPTION: "+ex );		//DEBUG		        	
		        	return null;
		        }	      
		        
		        ////////
		        CountDownTimer objContTime=  this.waitResponse(Definiciones.Definicionesgenerales.tiempoesperaenvio);	//OPTIMIZAR
		        while((!botermine)&&(objT.getResponse()==null))
		        {  	;      }
		        ////////
		        
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
	
	///######################################## OBTIENE ULTIMO BY DISPOSITIVO ##################################################################///	
	//
	//############################################################################################################
	public LocationPointDate obtenerLocationPorDevice(String dispositivo)
    {
				
    	try
		{   
			if(dispositivo!=null)
			{
				Log.i(TAG, "[obtenerLocationPorDispositivo] HAY LATITUD Y/O LONGITUD");							//DEBUG
		   			    			   	 
		   		final Gson gson = new Gson();	    			   		
		   		//Gps objGps=new Gps("AndCYS",dbLat,dbLon,dbAlt,flSpeed,Imei,settings.getString("CodeActiv",null),strLevelbat);
	   				   		
		   		//final String json = gson.toJson(gpsloc); 
		   		
		   		//String urlCatLoc = new String(Definiciones.Definicionesgenerales.servidor+"/location_points/near_location_points.json?lat="+gpsloc.getLatitud()+"&lng="+gpsloc.getLongitud()+"&strcategoria");
		   		String urlCatLoc = new String(Definiciones.Definicionesgenerales.servidor+"/api/locations/"+dispositivo+"/get_location");
		   		
		   		Log.i(TAG, "[obtenerLocationPorDispositivo] ENVIAR URL: "+urlCatLoc );		//DEBUG
		        try
		        {
		        	objT = (RequestTaskAsync) new RequestTaskAsync().execute(urlCatLoc);
		        }catch(Exception ex)
		        {
		        	Log.i(TAG, "[obtenerLocationPorDispositivo] REQUEST EXCEPTION: "+ex );		//DEBUG		        	
		        	return null;
		        }
		        botermine=false;
		        CountDownTimer objContTime=  this.waitResponse(Definiciones.Definicionesgenerales.tiempoesperaenvio);	//OPTIMIZAR
		        while((!botermine)&&(objT.getResponse()==null))
		        {
		        	;
		        }
		        	
		        if(objT.getResponse()==null)
		    	 {
		    		 Log.i(TAG, "[obtenerLocationPorDispositivo] RESPONSE NULL");		//DEBUG
		    		 objT.cancel(true);
		    		 return null;
		    	 }else		//////////////OBTUVE RESPUESTA DE ENVIO... CATEGORIAS ...
		    	 {	 objContTime.cancel();	    		    		 
		    		 Log.i(TAG, "[obtenerLocationPorDispositivo] RESPONSE : "+objT.getResponse());		//DEBUG
		    		 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		    		 ///TODO: CATEGORYLocation deberian ser dos clases, pero todavia no probe el gson para dos objetos uno dentro de otro FD v15.3.13
	    			    			
		    		 Response_ObtenerLocacionDispositivo locbydevice = gson.fromJson(objT.getResponse(), Response_ObtenerLocacionDispositivo.class);
	    			
		    		if(locbydevice.getCode()==Definiciones.Definicionesgenerales.SUCCESS)
		    		{    				    			
		    			return locbydevice.getLocationPointDate();
		    		}
		    		return null;	    			
		    	 }
			}else
			{
				Log.i(TAG, "[obtenerLocationPorDispositivo] NO HAY COORDENADADS PARA ENVIAR ");		//DEBUG				
			}
		}catch(RuntimeException ex)
		{
			Log.e(TAG, "[obtenerLocationPorDispositivo] Exploto todo: "+ex );		//DEBUG
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
		        ////////
		        CountDownTimer objContTime=  this.waitResponse(Definiciones.Definicionesgenerales.tiempoesperaenvio);	//OPTIMIZAR
		        while((!botermine)&&(objT.getResponse()==null))
		        {  	;      }
		        ////////
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
	public Categoria[] descargarCategoriasDisponibles(String strversion)  
    {    	
    	try
		{   
		   		final Gson gson = new Gson();
		   		//final String json = gson.toJson(gpsloc); 
		   		strversion="01.01";
		   		//TODO ARMAR url de envio para actualizar
		   		//String urlCatLoc = new String(Definiciones.Definicionesgenerales.servidor+"/categorias_disponibles/categorias_disponibles.json?vers="+strversion);
		   		String urlCatLoc = new String(Definiciones.Definicionesgenerales.servidor+"/api/locations/get_all_categories");
		   			
		   		Log.i(TAG, "[actualizarCategoriasDisponibles] ENVIAR URL: "+urlCatLoc );		//DEBUG
		        try
		        {
		        	//objT = (RequestTaskAsync) new RequestTaskAsync().execute(urlCatLoc,Definiciones.Transacciones.actualizacioncategorias);
		        	Transaction trans=new Transaction();
		        	trans.setDatain(urlCatLoc);
		        	trans.setTransaction(Definiciones.Transacciones.actualizacioncategorias);
		        	objT = (RequestTaskAsync) new RequestTaskAsync().execute(trans);
		        	
		        }catch(Exception ex)
		        {
		        	Log.i(TAG, "[actualizarCategoriasDisponibles] REQUEST EXCEPTION: "+ex );		//DEBUG		        	
		        	return null;
		        }	      
		        	
		        //////
		        //botermine=false;
		        //CountDownTimer objContTime=  this.waitResponse(Definiciones.Definicionesgenerales.tiempoesperaenvio);	//OPTIMIZAR
		        //while((botermine!=true)&&(objT.getResponse()==null))
		        //{   Thread.sleep(1500);       } 
		        //////
		         
		        int j=0;
		        //objT .getStatus() == AsyncTask.Status.FINISHED
		        //!objT.getStatus().equals(AsyncTask.Status.FINISHED
		        while((objT.getStatus()!= AsyncTask.Status.FINISHED)&&(j<Definiciones.Definicionesgenerales.tiempoesperaenvio))
		        {
		        	Thread.sleep(1000);
		        	j++;
		        	Log.i(TAG, "[actualizarCategoriasDisponibles] CONDICION");		//DEBUG
		        }
		        
		        if(objT.getResponse()==null)		      
		    	 {
		    		 Log.i(TAG, "[actualizarCategoriasDisponibles] RESPONSE NULL");		//DEBUG
		    		 objT.cancel(true);
		    		 return null;
		    		 //Response_actualizarCategoriasDisponibles categoriaslist;//SI SE REQUIERE QUE NO TENGA UN WHILE, LE ENVIO UNA REFERENCIA AL OBJETO categorialist que lo van a pullear hasta que este lleno.
		    		 
		    	 }else		//////////////OBTUVE RESPUESTA DE ENVIO... CATEGORIAS ...
		    	 {		
		    		 //objContTime.cancel();
		    		 Log.i(TAG, "[actualizarCategoriasDisponibles] RESPONSE : "+objT.getResponse());		//DEBUG
		    		 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		    		 ///TODO: Corregir
		    		 //Type collectionType = new TypeToken<Collection<channelSearchEnum>>(){}.getType();
		    		 		    		 
		    		// String pruebaError="{"+ "\"categories\":[[{"+ "\"id\":\"1\"," + "\"name\":\"resto\"," + "\"description\":\"restourantes\"}],[{" + "\"id\":\"2\"," + "\"name\":\"Hospital\","+ "\"description\":\"Atencion sanitaria y hospitales\"}]]," + "\"code\":\"000\"" + "}";
		    		 String prueba="{"+ "\"categories\":[{"+ "\"id\":\"1\"," + "\"name\":\"resto\"," + "\"description\":\"restourantes\"},{" + "\"id\":\"2\"," + "\"name\":\"Hospital\","+ "\"description\":\"Atencion sanitaria y hospitales\"}]," + "\"code\":\"000\"" + "}";
		    		 Log.i(TAG, "[actualizarCategoriasDisponibles] PRUEBA : "+prueba);		//DEBUG
		    		 Response_actualizarCategoriasDisponibles categoriaslist = gson.fromJson(prueba, Response_actualizarCategoriasDisponibles.class);
		    		 //Response_actualizarCategoriasDisponibles categoriaslist = gson.fromJson(jsonObject.toString(), Response_actualizarCategoriasDisponibles.class);
	    			 
		    		 
		    		 Log.i(TAG,"[actualizarCategoriasDisponibles] CODIGO RESP: "+categoriaslist.code);
	    			if(categoriaslist.getCode()==Definiciones.Definicionesgenerales.SUCCESS)		    		 
	    			{
	    				int x=0;	    				
	    				while(x<categoriaslist.categories.length)
	    				{
	    					Log.i(TAG,"[actualizarCategoriasDisponibles] NOMBRECAT: "+categoriaslist.categories[x].getName());
	    					x++;
	    				}
	    				return categoriaslist.getCategorias();
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
	
    
	 ///######################################### ACTUALIZAR TIPOS DISPONIBLES #################################################################///
		//
		//############################################################################################################
		public ArrayList<Categoria> descargarTiposDisponibles(String strversion)
	    {
	    	
	    	try
			{   
			   		final Gson gson = new Gson();
			   		//final String json = gson.toJson(gpsloc); 
			   		strversion="01.01";
			   		//TODO ARMAR url de envio para actualizar
			   		//String urlCatLoc = new String(Definiciones.Definicionesgenerales.servidor+"/categorias_disponibles/categorias_disponibles.json?vers="+strversion);
			   		String urlCatLoc = new String(Definiciones.Definicionesgenerales.servidor+"/api/locations/get_all_types");
			   			
			   		Log.i(TAG, "[descargarTiposDisponibles] ENVIAR URL: "+urlCatLoc );		//DEBUG
			        try
			        {
			        	objT = (RequestTaskAsync) new RequestTaskAsync().execute(urlCatLoc);
			        	
			        	
			        }catch(Exception ex)
			        {
			        	Log.i(TAG, "[descargarTiposDisponibles] REQUEST EXCEPTION: "+ex );		//DEBUG		        	
			        	return null;
			        }	      
			        
			        ////////
			        CountDownTimer objContTime=  this.waitResponse(Definiciones.Definicionesgenerales.tiempoesperaenvio);	//OPTIMIZAR
			        while((!botermine)&&(objT.getResponse()==null))
			        {  	;      }
			        ////////
			        
			    	 if(objT.getResponse()==null)		      
			    	 {
			    		 Log.i(TAG, "[descargarTiposDisponibles] RESPONSE NULL");		//DEBUG
			    		 objT.cancel(true);
			    		 return null;
			    	 }else		//////////////OBTUVE RESPUESTA DE ENVIO... CATEGORIAS ...
			    	 {		    		    		 
			    		 Log.i(TAG, "[descargarTiposDisponibles] RESPONSE : "+objT.getResponse());		//DEBUG
			    		 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			    		 ///TODO: Corregir	    			    			
			    		 Response_actualizarTiposDisponibles tiposdisplist = gson.fromJson(objT.getResponse(), Response_actualizarTiposDisponibles.class);
		    			
		    			if(tiposdisplist.getCode()==Definiciones.Definicionesgenerales.SUCCESS)
		    			{
		    				//return tiposdisplist.getCategoryList();	///TODO return tipos disponibles
		    			}else
		    			{
		    				Log.i(TAG,"[descargarTiposDisponibles] NO HAY CATEGORIAS DISPONIBLES");	    				
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
	public CountDownTimer waitResponse(int inseconds)
	{
		CountDownTimer objcount=new CountDownTimer(inseconds*1000, 1000) {
		     public void onTick(long millisUntilFinished) {			    		         
		         Log.i(TAG, "[waitResponse] ONTICK: "+millisUntilFinished/1000);		//DEBUG
		     }
		     public void onFinish() {
		    	 Log.i(TAG, "[waitResponse] TIMER DONE");		//DEBUG
		    	 botermine=true;
		     }
		  }.start();
		  
		  return objcount;
	}
	*/
}
