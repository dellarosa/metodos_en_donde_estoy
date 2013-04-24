package bo;

import com.google.gson.annotations.SerializedName;


public class LocationPointDate {
	@SerializedName("latitude")
	double latitude;
	@SerializedName("longitude")
	double longitude;
	@SerializedName("created_at")
	String   created_at;
	
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitud) {
		this.latitude = latitud;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitud) {
		this.longitude = longitud;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	} 
	
	
}
