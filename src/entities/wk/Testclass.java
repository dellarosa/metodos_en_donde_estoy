package entities.wk;

import java.util.ArrayList;

import edu.palermo.dondeestoy.bo.*;
import edu.palermo.dondeestoy.bo.Responseclass.Response_GetLocacionDevice;
import edu.palermo.dondeestoy.rest.ApiService;
import edu.palermo.dondeestoy.rest.ApiServiceException;




import libreria.Categoria;
import libreria.Gps;
import libreria.MetodosGral;
import libreria.MetodosRequest;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Testclass extends Activity{

	String TAG="TestClass";
	Button btlogin;
	Button btcarga;
	Button btgetnear;
	Button btupdateloc;
	Button btcreardev;
	Button btlocpointdev;
	Button bttiposdisp;
	
	MetodosRequest metreq;
	MetodosGral metg;
	ApiService apiserv;
	
	public Testclass()
	{
		
	}
	
	public void onCreate(Bundle savedInstanceState) 
	{	 
		
		 super.onCreate(savedInstanceState);
		 Log.i(TAG,"[onCreate] DENTRO ONCREATE");
	     setContentView(R.layout.test);
	     
	     metreq=new MetodosRequest();
			apiserv=new ApiService();
			
	     final Gps gps=new Gps();
			gps.setLat(-22.22);
			gps.setLong(23.23);
			

		//////////////////////	
	     btlogin=(Button)findViewById(R.id.bt_test_loggin);	     
	     btlogin.setOnClickListener(new OnClickListener()
		    {     
				public void onClick(View v) {
					try
					{
						String struser="pepe";
						String strpass="pepepass";
						String imei=metg.getDeviceID();
						
					//	metreq.verificarUseryPass(struser, strpass, imei);
					}catch(RuntimeException e)
					{
						Log.i(TAG,"[onClick] Exception Verificar: "+e);
					}
				}
		    });
	     
	     //////////////////////
	     btcarga=(Button)findViewById(R.id.bt_test_cargarcat);
	     btcarga.setOnClickListener(new OnClickListener()
		    {     
				public void onClick(View v) {
					try
					{
						new Thread()
						{
						    public void run() {
						
						    	Responseclass.Response_CategoriasDisponibles CategoriasDisponibles;
								try {
									CategoriasDisponibles = apiserv.getCategoriasDisponibles();
									Category[] categorias=CategoriasDisponibles.getCategorias();
									
									//Categoria[] categorias= metreq.descargarCategoriasDisponibles("00001");
									if(categorias!=null)
									{
										int i=0;
										while(i<categorias.length)
										{
											Log.i(TAG,"[onClick] CATEGORIA "+categorias[i].getName());
											i++;
										}
									}else
									{
									Log.i(TAG,"[onClick] Categorias null");
									}
								} catch (ApiServiceException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
						    }
						}.start();						
						
					}catch(RuntimeException e)
					{
						Log.i(TAG,"[onClick] Exception Cargar Categ: "+e);
					}
				}
		    });

	     /////////////////////
	     btgetnear=(Button)findViewById(R.id.bt_test_getnearloc);
	     btgetnear.setOnClickListener(new OnClickListener()
		    {     
				public void onClick(View v) {
					try
					{
						new Thread()
						{
						    public void run() {
								NearLocationPointsResponse nearLocationPoints=null;
								try {
									nearLocationPoints = apiserv.getNearLocationPoints(gps.getLatitud(),gps.getLongitud());
									if(nearLocationPoints.getCode().equals("000")) {
										for (LocationPoint locationPoint : nearLocationPoints.getList()) {
											Log.i("TEST", locationPoint.getLatitude() + " - " + locationPoint.getLongitude());
										}
									} else if(nearLocationPoints.getCode().equals("600")) {
										Log.i("Test","No encontre nada en un radio de 5 kilomestros");
									}else
									{}
								} catch (ApiServiceException e) {
									// TODO Auto-generated catch block
									Log.e("Test","EXCEPTION NEAR: "+e);
									//e.printStackTrace();
								}
								
							
							//metreq.obtenerLocacionesCercanas(gps,"all");
						    }
						}.start();
						
					}catch(RuntimeException e)
					{
						Log.e(TAG,"[onClick] Exception Get Near Loc: "+e);
					}
				}
		    });
	     ////////////////
	     btupdateloc=(Button)findViewById(R.id.bt_test_updateloc);
	     btupdateloc.setOnClickListener(new OnClickListener()
		    {     
				public void onClick(View v) {
					try
					{
						new Thread()
						{
						    public void run() {
						
						    	BaseResponse baseresp=apiserv.postUpdateLocation(gps.getLatitud(),gps.getLongitud(),1);
						    	Log.i("Update ","RESPUESTA UPDATE COORDINATE: "+baseresp.getCode()+" - ");

						    }
						}.start();						
						
					}catch(RuntimeException e)
					{
						Log.i(TAG,"[onClick] Exception Cargar Categ: "+e);
					}
				}
		    });
	     //////////////////
	     btcreardev=(Button)findViewById(R.id.bt_test_create);
	     btcreardev.setOnClickListener(new OnClickListener()
		    {     
				public void onClick(View v) {
					
					try
					{
							new Thread()
							{
							    public void run() {  	
									BaseResponse baseresp;
									try {
										baseresp = apiserv.postCreateDevice("pepe","celular de pepe",1,1);
										if(baseresp.getCode().equals("000")) {
											
											//LocacionByDevice.getLocationPointDate().getLatitud();
											//LocacionByDevice.getLocationPointDate().getLongitud();
											Log.i("TEST", "CODIGO RESP: "+baseresp.getCode());								
										
										} else if(baseresp.getCode().equals("600")) {
											Log.i("Test","Error, o no pude crear, o ya creado ");
										}else
										{
											
										}
									} catch (ApiServiceException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									
																		
								//	metreq.obtenerLocationPorDevice("lo de juan");
							    }
							}.start();					
					//	metreq.crearNuevoDevice("celular", "Celulares");
					}catch(RuntimeException e)
					{
						Log.i(TAG,"[onClick] Exception Create: "+e);
					}
					}
				}
		    );
	     ////////////////
	     btlocpointdev=(Button)findViewById(R.id.bt_test_locbydev);
	     btlocpointdev.setOnClickListener(new OnClickListener()
		    {     
				public void onClick(View v) {
					
						try
						{
							new Thread()
							{
							    public void run() {
							    	
							    
									Response_GetLocacionDevice LocacionByDevice;
									try {
										LocacionByDevice = apiserv.getLocationByDevice("lo de juan");
										if(LocacionByDevice.getCode().equals("000")) {
											
											//LocacionByDevice.getLocationPointDate().getLatitud();
											//LocacionByDevice.getLocationPointDate().getLongitud();
											Log.i("TEST", LocacionByDevice.getLocationPointDate().getLatitude() + " - " + LocacionByDevice.getLocationPointDate().getLongitude());								
										
										} else if(LocacionByDevice.getCode().equals("600")) {
											Log.i("Test","No encontre nada ");
										}else
										{}
									} catch (ApiServiceException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									
									
									Log.i(TAG,"[onClick] OBTENER LOC DEVICE");
								//	metreq.obtenerLocationPorDevice("lo de juan");
							    }
							}.start();
						}catch(Exception e)
						{
							Log.i(TAG,"[onClick] Exception Loc by Dev: "+e.getMessage());
							Log.i(TAG,"[onClick] Exception Loc by Dev: "+e);
						}
					}
				}
		    );
	     
	     
	     bttiposdisp=(Button)findViewById(R.id.bt_test_tiposdisp);
	     bttiposdisp.setOnClickListener(new OnClickListener()
		    {     
				public void onClick(View v) {
					try
					{
						new Thread()
						{
						    public void run() {
						
						    	Responseclass.Response_TiposDisponibles tiposDisponibles;
								try {
									tiposDisponibles = apiserv.getTiposDisponibles();
									Types[] types=tiposDisponibles.getTypes();
									
									//Categoria[] categorias= metreq.descargarCategoriasDisponibles("00001");
									if(types!=null)
									{
										int i=0;
										while(i<types.length)
										{
											Log.i(TAG,"[onClick] Tipos "+types[i].getName());
											i++;
										}
									}else
									{
									Log.i(TAG,"[onClick] Tipos null");
									}
								} catch (ApiServiceException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
						    }
						}.start();						
						
					}catch(RuntimeException e)
					{
						Log.i(TAG,"[onClick] Exception Cargar Categ: "+e);
					}
				}
		    });

	
	     
	     
	}
}
