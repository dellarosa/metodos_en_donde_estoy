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
	//	/////////////////////////////////////////////////////////////////////////////////
	public boolean actualizarCategorias()
	{
		metrequest =new MetodosRequest();	     		
		/////////////////////////////////////////////////////////////////////////////////////
		
		//TODO ACTUALIZAR CATEGORIAS DISPONIBLES
		SharedPreferences sharedvers = mcontex.getSharedPreferences("VERSION",Context.MODE_PRIVATE);
		
		ArrayList<String> categorias=metrequest.actualizarCategoriasDisponibles(Integer.valueOf(getDeviceID()), sharedvers.getString("VERSION",null));
		
		if(categorias!=null)
		{
			SharedPreferences.Editor editorcat;
			SharedPreferences sharedcategorias = mcontex.getSharedPreferences("categorias",Context.MODE_PRIVATE);
		
			for(String categ:categorias)
			{ 
				editorcat=sharedcategorias.edit();
				editorcat.putString(categ, categ);
			}
		
		}else
		{
			Log.i(TAG,"[onCreate] Nuevas CATEGORIAS NULL");
			return false;
		}
		return true;
	}
	
    public String getDeviceID() 
   {
   	  TelephonyManager TelephonyMgr = (TelephonyManager) mcontex.getSystemService(Context.TELEPHONY_SERVICE);
   	  String DEVICE_ID = TelephonyMgr.getDeviceId();
   	  return DEVICE_ID;
   }
}
