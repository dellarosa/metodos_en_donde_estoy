package domain;

public class Gps 
{
	
	public static String TAG = "Gps";
	
	private String lat;
	private String lng;
	private String vel;
	private String alt;
	private String imei;
	private String id;
	private String code;
	private String battery;
	
	public Gps()
	{
		
	}
	public Gps(String Idc,String Lat,String Long,String Alti,String Speed,String Imei,String Code,String Battery)
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
	
	 public String GetLatitud()
	 {
		 return lat;
	 }
	 public String GetLongitud()
	 {
		 return lng;
	 }
	 public String GetAltitud()
	 {
		 return this.alt;
	 }
	 public String GetVelocidad()
	 {
		 return this.vel;
	 }
	 
	 public void SetLat(String Lat)
	 {
			this.lat=Lat;

	 }
	 public void SetLong(String Long)
	 {
			this.lng=Long;

	 }
	 public void SetVeloc(String Speed)
	 {
			this.vel=Speed;

	 }
	 public void SetAltit(String Alti)
	 {
			this.alt=Alti;
					 
	 }
	 public void SetIdTipoCel(String IdTipocel)
	 {

			this.id=IdTipocel;

	 }
	 public void SetImei(String sImei)
	 {		 
		 this.imei=sImei;
		 
	 }
	 public void SetBattery(String sBattery)
	 {
		
			this.battery=sBattery;
	 }
	 public void SetCode(String Code)
	 {
			this.code=Code; 
	 }
}
