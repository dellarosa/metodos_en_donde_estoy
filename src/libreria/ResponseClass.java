package libreria;

import java.util.ArrayList;


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
	///############################ CATEGORY UPDATE ############################################################3
	public class Response_actualizarCategoriasDisponibles
	{
		private String message;
    	private int code;
    	private ArrayList<Categoria> categorylist;
    	
    	
    	public Response_actualizarCategoriasDisponibles()
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
    	public void setCategoryList(ArrayList<Categoria> categ)
    	{
    		this.categorylist=categ;
    	}
    	public ArrayList<Categoria> getCategoryList()
    	{
    		return this.categorylist;
    	}
    	
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
