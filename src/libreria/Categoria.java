package libreria;

public class Categoria {
	private String name;
	private String device;
	private int id;
	
	
	public String getNombreCategoria()
	{
		return this.name;
	}
	public void setNombreCategoria(String nomcategoria)
	{
		this.name=nomcategoria;
	}
	public String getNombredevice()
	{
		return this.device;
	}
	public void setNombreDevice(String nomdevice)
	{
		this.device=nomdevice;
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
