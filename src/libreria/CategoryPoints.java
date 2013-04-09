package libreria;

import java.util.Date;

public class CategoryPoints {
	private double latitud;
	private double longitud;
	private Date update_at;
	private String categoryname;
	private String categorydevice;
	//private Categoria categoria;
	
	/*
	public Categoria getCategoria()
	{
		return this.categoria;
	}
	public void setCategoria(Categoria categoria)
	{
		this.categoria=categoria;
	}*/
	public void setLatitud(double lat)
	{
		this.latitud=lat;
	}
	public double getLatitud()
	{
		return this.latitud;
	}
	public void setLongitud(double lon)
	{
		this.longitud=lon;
	}
	public double getLongitud()
	{
		return this.longitud;
	}
	public void setDate(Date updateat)
	{
		this.update_at=updateat;
	}
	public Date getUpdateAt()
	{
		return this.update_at;
	}
	public void setCategoryName(String categname)
	{
		this.categoryname=categname;
	}
	public String getCategoryName()
	{
		return this.categoryname;
	}
	public String getCategoryDevice()
	{
		return this.categorydevice;
	}
	public void setCategoryDevice(String categdevice)
	{
		this.categorydevice=categdevice;
	}

	
	
}
