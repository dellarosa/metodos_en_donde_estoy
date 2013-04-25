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
		public class Request_CrearNuevoDevice
	    {
	    	
	    	public Request_CrearNuevoDevice()
	    	{
	    		
	    	}
			@SerializedName("name")
			private String name;
			
			@SerializedName("description")
			private String description;
			
			@SerializedName("category_id")
			private int category_id;
			
			@SerializedName("type_id")
			private int type_id;
			
	    	public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getDescription() {
				return description;
			}

			public void setDescription(String description) {
				this.description = description;
			}

			public int getCategory_id() {
				return category_id;
			}

			public void setCategory_id(int category_id) {
				this.category_id = category_id;
			}

			public int getType_id() {
				return type_id;
			}

			public void setType_id(int type_id) {
				this.type_id = type_id;
			}

			
			
	    	
	    }
		
}
