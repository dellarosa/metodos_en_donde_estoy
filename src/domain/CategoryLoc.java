package domain;

public class CategoryLoc {

	private Long idCategory;
	private String strCategoryName;
	private Local local;
	
	public CategoryLoc()
	{
		
	}
	public CategoryLoc(Long idCat,Long idLoc,String catname,String localname,String lat,String lng)
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
	public Local getLocal()
	{
		return this.local;
	}
	public void setLocal(Local local)
	{
		this.local=local;
	}



}
