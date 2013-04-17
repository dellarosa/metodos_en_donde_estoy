package libreria;

import java.util.ArrayList;


import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MetodosGral {

	private Context mcontex;
	private String TAG="MetodosGrl";
	MetodosRequest metrequest;
	
	public MetodosGral(Context mcontex)
	{
		this.mcontex=mcontex;
	}
	public MetodosGral() {
		// TODO Auto-generated constructor stub
	}
	//	/////////////////////////////////////////////////////////////////////////////////
	///################################## ACTUALIZAR CATEGORIAS ####################################################
	public boolean actualizarCategorias()
	{
		metrequest =new MetodosRequest();	     		
		/////////////////////////////////////////////////////////////////////////////////////
		
		//TODO ACTUALIZAR CATEGORIAS DISPONIBLES
		SharedPreferences sharedvers = mcontex.getSharedPreferences("VERSION",Context.MODE_PRIVATE);
		
		ArrayList<Categoria> categorias=metrequest.descargarCategoriasDisponibles(sharedvers.getString("VERSION",null));
		
		if(categorias!=null)
		{
			SharedPreferences.Editor editorcat;
			SharedPreferences sharedcategorias = mcontex.getSharedPreferences("categorias",Context.MODE_PRIVATE);
		
			for(Categoria categ:categorias)
			{ 
				editorcat=sharedcategorias.edit();
				editorcat.putString(String.valueOf(categ.getCategoriaId()), categ.getNombreCategoria());
			}
		
		}else
		{
			Log.i(TAG,"[onCreate] Nuevas CATEGORIAS NULL");
			return false;
		}
		return true;
	}
	
	///####################################################### GET DEVICE ID - IMEI ################################
    public String getDeviceID() 
   {
   	  TelephonyManager TelephonyMgr = (TelephonyManager) mcontex.getSystemService(Context.TELEPHONY_SERVICE);
   	  String DEVICE_ID = TelephonyMgr.getDeviceId();
   	  return DEVICE_ID;
   }
}
