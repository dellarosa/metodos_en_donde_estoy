package bo;

import com.google.gson.annotations.SerializedName;

public class Category {
	@SerializedName("id")
	private int id;
	@SerializedName("name")
	private String name;
	@SerializedName("description")
	private String description;
	
	public String getName()
	{
		return this.name;
	}
	public void setNombreCategoria(String nomcategoria)
	{
		this.name=nomcategoria;
	}
	public String getNombredevice()
	{
		return this.description;
	}
	public void setNombreDevice(String nomdevice)
	{
		this.description=nomdevice;
	}
	public void setCategoriaId(int idcategoria)
	{
		this.id=idcategoria;
	}
	public int getCategoriaId()
	{
		return this.id;
	}
}
