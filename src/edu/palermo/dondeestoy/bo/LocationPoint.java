package edu.palermo.dondeestoy.bo;

public class LocationPoint {

	
	private double latitude;
	private double longitude;
    private String category;
    private String device;
    private String device_description;
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getDevice_description() {
		return device_description;
	}
	public void setDevice_description(String device_description) {
		this.device_description = device_description;
	}
	
}
