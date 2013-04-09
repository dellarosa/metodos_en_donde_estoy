package libreria;

public class Gps 
{
	
	public static String TAG = "Gps";
	
	private double lat;
	private double lng;
	private double vel;
	private double alt;
	private String imei;
	private String id;
	private String code;
	private String battery;
	
	public Gps()
	{
		this.lat=0;
		this.lng=0;
		this.vel=0;
		this.alt=0;
		this.imei=null;
		this.id=null;
		this.code=null;
		this.battery=null;
		
				
	}
	public Gps(String Idc,double Lat,double Long,double Alti,double Speed,String Imei,String Code,String Battery)
	{
		this.lat=Lat;
		this.lng=Long;
		this.vel=Speed;
		this.alt=Alti;
		this.imei=Imei;
		this.id=Idc;
		this.code=Code;
		this.battery=Battery;
	}
	
	 public double getLatitud()
	 {
		 return lat;
	 }
	 public double getLongitud()
	 {
		 return lng;
	 }
	 public double getAltitud()
	 {
		 return this.alt;
	 }
	 public double getVelocidad()
	 {
		 return this.vel;
	 }
	 
	 
	 public void setLat(double d)
	 {
			this.lat=d;

	 }
	 public void setLong(double Long)
	 {
			this.lng=Long;

	 }
	 public void setVeloc(double Speed)
	 {
			this.vel=Speed;

	 }
	 public void setAltit(double Alti)
	 {
			this.alt=Alti;
					 
	 }
	 public void setIdTipoCel(String IdTipocel)
	 {

			this.id=IdTipocel;

	 }
	 public void setBattery(String sBattery)
	 {
		
			this.battery=sBattery;
	 }
	 public void setCode(String Code)
	 {
			this.code=Code; 
	 }
}
