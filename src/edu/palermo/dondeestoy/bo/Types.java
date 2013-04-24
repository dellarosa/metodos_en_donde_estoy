package edu.palermo.dondeestoy.bo;

import com.google.gson.annotations.SerializedName;

public class Types {

	@SerializedName("id")
	private int id;
	@SerializedName("name")
	private String name;
	@SerializedName("dinamic")
	private boolean dinamic;
	@SerializedName("description")
	private String description;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isDinamic() {
		return dinamic;
	}
	public void setDinamic(boolean dinamic) {
		this.dinamic = dinamic;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
