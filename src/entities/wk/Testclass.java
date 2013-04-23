package entities.wk;

import java.util.ArrayList;

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
	MetodosRequest metreq;
	MetodosGral metg;
	
	public Testclass()
	{
		
	}
	
	public void onCreate(Bundle savedInstanceState) 
	{	 
		
		 super.onCreate(savedInstanceState);
		 Log.i(TAG,"[onCreate] DENTRO ONCREATE");
	     setContentView(R.layout.test);
	     
	     metreq=new MetodosRequest();
	     final Gps gps=new Gps();
			gps.setLat(-23.45);
			gps.setLong(-36.77);
			
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
						
						metreq.verificarUseryPass(struser, strpass, imei);
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
						ArrayList<Categoria> categorias= metreq.descargarCategoriasDisponibles("00001");
						if(categorias!=null)
						{
							for(Categoria categ:categorias)								
							{
								Log.i(TAG,"[onClick] CATEGORIA "+categ.getCategoriaId()+" : "+categ.getNombreCategoria());
							}
						}else
						{
							Log.i(TAG,"[onClick] Categorias null");
						}
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
						
						
						metreq.obtenerLocacionesCercanas(gps,"all");
						
					}catch(RuntimeException e)
					{
						Log.i(TAG,"[onClick] Exception Get Near Loc: "+e);
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
						metreq.actualizarPosicion(gps);
					}catch(RuntimeException e)
					{
						Log.i(TAG,"[onClick] Exception update Loc: "+e);
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
						metreq.crearNuevoDevice("celular", "Celulares");
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
							Log.i(TAG,"[onClick] OBTENER LOC DEVICE");
							metreq.obtenerLocationPorDevice("lo de juan");
						}catch(Exception e)
						{
							Log.i(TAG,"[onClick] Exception Loc by Dev: "+e.getMessage());
							Log.i(TAG,"[onClick] Exception Loc by Dev: "+e);
						}
					}
				}
		    );
	     
	}
}
