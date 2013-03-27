package domain;

public class CategoryLocation {

	private Long idCategory;
	private String strCategoryName;

	private Long idLocal;
	private String localname;
	private String latitudlocal;
	private String longitudlocal;
	
	public CategoryLocation()
	{
		
	}
	public CategoryLocation(Long idCat,Long idLoc,String catname,String localname,String lat,String lng)
	{
		this.idCategory=idCat;	
		this.strCategoryName=catname;
		this.idLocal=idLoc;
		this.localname=localname;
		this.latitudlocal=lat;
		this.longitudlocal=lng;
		
	}
	
	public void setIdCategory(Long idCat)
	{
		this.idCategory=idCat;
		
	}
	public Long getIdCategory()
	{
		return idCategory;
	}

	public String getCategoryName()
	{
		return strCategoryName;
	}
	public void setCategoryName(String CatName)
	{
		this.strCategoryName=CatName;
	}

	public void setIdLocal(Long idLoc)
	{
		this.idLocal=idLoc;
	}
	public Long getIdLocal()
	{
		return idLocal;
	}
	public String getLocalName()
	{
		return this.localname;
	}
	
	public String getLatitudLocal()
	{
		return latitudlocal;
	}
	public String getLongitudLocal()
	{
		return longitudlocal;
	}
	

	public void setLocalName(String LocName)
	{
		this.localname=LocName;
	}
	public void setLatitudLocal(String LatLoc)
	{
		this.latitudlocal=LatLoc;
	}
	public void setLongLocal(String LongLoc)
	{
		this.longitudlocal=LongLoc;
	}


}
