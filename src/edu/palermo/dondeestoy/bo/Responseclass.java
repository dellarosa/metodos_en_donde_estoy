package edu.palermo.dondeestoy.bo;

import java.util.ArrayList;


import com.google.gson.annotations.SerializedName;


public class Responseclass {
	 

	
	///TESTEADO OK - Modificar server############################ LOCACION POR DISPOSITIVO ############################################################3
	
	public class Response_GetLocacionDevice extends BaseResponse
	{
    	private LocationPointDate location_point;
    	
    	
    	public Response_GetLocacionDevice()
    	{
    		
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
	public class Response_CategoriasDisponibles extends BaseResponse
	{
		public Response_CategoriasDisponibles()
		{}
		
		
		@SerializedName("categories")
		public Category[] categories;						

		public Category[] getCategorias()
		{
			return this.categories; 	
		}
	}
	
	///############################ TIPOS DISPONIBLES -UPDATE TIPOS ############################################################3
	public class Response_TiposDisponibles extends BaseResponse
	{
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
		
	}	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	///POR AHORA NO USADO############################ VERIFICAR USUARIO Y PASSWORD ############################################################3
	public class Response_verificarUseryPass extends BaseResponse
	{

	    	public Response_verificarUseryPass()
	    	{
	    		
	    	}	    
	}
	///////////////////////////////////////////////////////////////////////////////////////////
}
