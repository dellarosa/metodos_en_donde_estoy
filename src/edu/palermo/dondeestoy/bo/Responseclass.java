package edu.palermo.dondeestoy.bo;

import java.util.ArrayList;


import com.google.gson.annotations.SerializedName;


public class Responseclass {
	 
	///############################ ACTUALIZAR POSICION - UPDATE LOCATION ############################################################3
	public class Response_actualizarPosicion
    {
		@SerializedName("message")
    	private String message;
		@SerializedName("code")
    	private String code;
    	
    	public Response_actualizarPosicion()
    	{
    		
    	}
    	
    	public String getMessage()
    	{
    		return this.message;
    		
    	}
    	public String getCode()
    	{
    		return this.code;
    	}
    	public void setMessage(String msg)
    	{
    		this.message=msg;
    	}
    	public void setCode(String code)
    	{
    		this.code=code;
    	}
    }
	///############################ CREAR NUEVO DEVICE ############################################################3
	public class Response_CrearNuevoDeviceCategory
    {
    	private String message;
    	private int code;
    	
    	public Response_CrearNuevoDeviceCategory()
    	{
    		
    	}
    	
    	public String getMessage()
    	{
    		return this.message;
    		
    	}
    	public int getCode()
    	{
    		return this.code;
    	}
    	public void setMessage(String msg)
    	{
    		this.message=msg;
    	}
    	public void setCode(int code)
    	{
    		this.code=code;
    	}
    }
	
	///TESTEADO OK - Modificar server############################ LOCACION POR DISPOSITIVO ############################################################3
	
	public class Response_GetLocacionDevice
	{
		
		//private String message;
		@SerializedName("code")
    	private String code;
		@SerializedName("location_point")
    	private LocationPointDate location_point;
    	
    	
    	public Response_GetLocacionDevice()
    	{
    		
    	}
    	
    	/*public String getMessage()
    	{
    		return this.message;
    		
    	}
    	public void setMessage(String msg)
    	{
    		this.message=msg;
    	}
    	*/
    	public String getCode()
    	{
    		return this.code;
    	}
    	
    	public void setCode(String code)
    	{
    		this.code=code;
    	}
    	public void setLocationPointDate(LocationPointDate location_point)
    	{
    		this.location_point=location_point;
    	}
    	public LocationPointDate getLocationPointDate()
    	{
    		return this.location_point;
    	}
    	
	}
	///TESTEADO OK ############################ CATEGORY UPDATE ############################################################3
	public class Response_CategoriasDisponibles
	{
		public Response_CategoriasDisponibles()
		{}
		public Response_CategoriasDisponibles(Category[] categories,String code)
		{
			this.code=code;
			this.categories=categories;
		}
		
		@SerializedName("categories")
		public Category[] categories;						
		@SerializedName("code")
		public String code;
		
		public String getCode()
		{
			return this.code;
		}
		public Category[] getCategorias()
		{
			return this.categories; 	
		}
	}
	
	///############################ TIPOS DISPONIBLES -UPDATE TIPOS ############################################################3
	public class Response_TiposDisponibles
	{
		@SerializedName("id")
		private String message;
		@SerializedName("code")
    	private int code;
		@SerializedName("types")
    	private Types[] types;
    	
    	
    	public Types[] getTypes() {
			return types;
		}

		public void setTypes(Types[] types) {
			this.types = types;
		}

		public Response_TiposDisponibles()
    	{
    		
    	}
    	
    	public String getMessage()
    	{
    		return this.message;
    		
    	}
    	public int getCode()
    	{
    		return this.code;
    	}
    	public void setMessage(String msg)
    	{
    		this.message=msg;
    	}
    	public void setCode(int code)
    	{
    		this.code=code;
    	}
    	///TODO: TIPOS DISPONIBLES
	}	
	
	
	
	///############################ CREAR NUEVO DEVICE ############################################################3
	public class Response_crearNuevoDevice
	{

	    	private String message;
	    	private int code;
	    	
	    	public Response_crearNuevoDevice()
	    	{
	    		
	    	}
	    	
	    	public String getMessage()
	    	{
	    		return this.message;
	    		
	    	}
	    	public int getCode()
	    	{
	    		return this.code;
	    	}
	    	public void setMessage(String msg)
	    	{
	    		this.message=msg;
	    	}
	    	public void setCode(int code)
	    	{
	    		this.code=code;
	    	}
	}
	///////////////////////////////////////////////////////////////////////////////////////////
	///############################ VERIFICAR USUARIO Y PASSWORD ############################################################3
	public class Response_verificarUseryPass
	{

	    	private String message;
	    	private int code;
	    	
	    	public Response_verificarUseryPass()
	    	{
	    		
	    	}
	    	
	    	public String getMessage()
	    	{
	    		return this.message;
	    		
	    	}
	    	public int getCode()
	    	{
	    		return this.code;
	    	}
	    	public void setMessage(String msg)
	    	{
	    		this.message=msg;
	    	}
	    	public void setCode(int code)
	    	{
	    		this.code=code;
	    	}
	}
	///////////////////////////////////////////////////////////////////////////////////////////
}
