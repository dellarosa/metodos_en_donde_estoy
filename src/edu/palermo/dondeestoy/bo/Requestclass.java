package edu.palermo.dondeestoy.bo;

import com.google.gson.annotations.SerializedName;

public class Requestclass {

	public class Request_UpdateLocation
	{

		@SerializedName("latitude")
		private double latitude;
		
		@SerializedName("longitude")
		private double longitude;
		
		public double getLatitude() {
			return latitude;
		}
		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}
		public double getLongitude() {
			return longitude;
		}
		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}
		
		
	}
	
	///############################ CREAR NUEVO DEVICE ############################################################3
		public class Request_CrearNuevoDeviceCategory
	    {
	    	
	    	public Request_CrearNuevoDeviceCategory()
	    	{
	    		
	    	}
	    	
	    	
	    }
		
}
