package domain;

public class Local {

	private Long idLocal;
	private String localname;
	private String latitudlocal;
	private String longitudlocal;
	
	public Local()
	{
		
	}
	public Local(Long idLoc,String localname,String latit,String longi)
	{
		this.idLocal=idLoc;
		this.localname=localname;
		this.latitudlocal=latit;
		this.longitudlocal=longi;
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
