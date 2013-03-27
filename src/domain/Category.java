package domain;

public class Category {

	private Long idCategory;
	private String strCategoryName;
	public Local local;
	
	public Category()
	{
		
	}
	public Category(Long idCat,Long idLoc,String catname,String localname,String lat,String lng)
	{
		this.idCategory=idCat;	
		this.strCategoryName=catname;
		this.local=new Local(idLoc,localname,lat,lng);
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

	



}
