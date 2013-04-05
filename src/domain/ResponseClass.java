package domain;

import java.util.ArrayList;

public class ResponseClass {
	 
	///############################ UPDATE LOCATION ############################################################3
	public class Response_Update_Loc
    {
    	private String message;
    	private int code;
    	
    	public Response_Update_Loc()
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
	public class Response_CrearNuevoDevice
    {
    	private String message;
    	private int code;
    	
    	public Response_CrearNuevoDevice()
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

	///############################ NEAR LOCATION ############################################################3
	public class Response_NearLoc {
		
		private String message;
		private int code;
		private ArrayList<CategoryPoints> CategoryPoints;
		
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
	public class Response_CategoryUpdate
	{
		private String message;
    	private int code;
    	private ArrayList<String> Category;
    	
    	public Response_CategoryUpdate()
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
    	public void setCategory(ArrayList<String> categ)
    	{
    		this.Category=categ;
    	}
    	public ArrayList<String> getCategory()
    	{
    		return this.Category;
    	}
    	
	}
	///############################ UPDATE YES OR NOT ############################################################3
	public class Response_YesOrNot
	{

	    	private String message;
	    	private int code;
	    	
	    	public Response_YesOrNot()
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
