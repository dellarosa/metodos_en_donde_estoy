package libreria;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;


public class ResponseClass {
	 
	///############################ ACTUALIZAR POSICION - UPDATE LOCATION ############################################################3
	public class Response_actualizarPosicion
    {
    	private String message;
    	private int code;
    	
    	public Response_actualizarPosicion()
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

	///############################ POSICIONES CERCANAS - NEAR LOCATION ############################################################3
	public class Response_obtenerLocacionesCercanas {
		
		private String message;
		private int code;
		private ArrayList<CategoryPoints> CategoryPoints;
		
		public Response_obtenerLocacionesCercanas()
		{
			
		}
		public void setMessage(String msg)
		{
			this.message=msg;
		}
		public String getMessage()
		{
			return this.message;
		}
		public void setCode(int code)
		{
			this.code=code;
		}
		public int getCode()
		{
			return this.code;
		}
		public ArrayList<CategoryPoints> getCategoryPoints()
		{
			return this.CategoryPoints;
		}
		public void setCategoryPoints(ArrayList<CategoryPoints> catpoints)
		{
			this.CategoryPoints=catpoints;
		}
	}
	
	///############################ LOCACION POR DISPOSITIVO ############################################################3
	
	public class Response_ObtenerLocacionDispositivo
	{
		private String message;
    	private int code;
    	private LocationPointDate locpointdate;
    	
    	
    	public Response_ObtenerLocacionDispositivo()
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
    	public void setLocationPointDate(LocationPointDate locpointdate)
    	{
    		this.locpointdate=locpointdate;
    	}
    	public LocationPointDate getLocationPointDate()
    	{
    		return this.locpointdate;
    	}
    	
	}
	///############################ CATEGORY UPDATE ############################################################3
	public class Response_actualizarCategoriasDisponibles
	{
		@SerializedName("categories")
		public Categories[] categories;
		
		public class Categories
		{		@SerializedName("id")
				public int id;
				@SerializedName("name")
				public String name;
				@SerializedName("description")
				public String description;
			
		}
				
		@SerializedName("code")
		public int code;
	}
	
	///############################ TIPOS DISPONIBLES -UPDATE TIPOS ############################################################3
	public class Response_actualizarTiposDisponibles
	{
		private String message;
    	private int code;

    	
    	
    	public Response_actualizarTiposDisponibles()
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
	
	
	
	///############################ UPDATE YES OR NOT ############################################################3
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
