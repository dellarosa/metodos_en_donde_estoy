package entities.wk;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import entities.wk.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;
import java.util.*;
import android.widget.*;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.*;

import domain.CategoryPoints;

public class ScreenMap  extends android.support.v4.app.FragmentActivity{

	private String TAG = "MetodosMapas";
	Context objContext;
	public ScreenMap()
	{
		
	}
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{	    
		 super.onCreate(savedInstanceState);
	
	     setContentView(R.layout.mapapoints);

	     
	     //////////////////////////////////// RETURN ////////////////////////////////////////////////////////////
	    Button buttonReturnAct = (Button) findViewById(R.id.buttonNewSearch);
	    buttonReturnAct.setOnClickListener(new OnClickListener()
	    {     
			public void onClick(View v) 
			{	
				Intent IntHTService = null;				    				    
			    try
			    {
			    	IntHTService=new Intent(getApplicationContext(),ServiceControl.class);
			    }catch(Exception Ex)
			    {
			    	Log.e(TAG,"[onCreate] Exception MetodosMapas Intent: "+Ex);	
			    }
			    try
				{
			    	startActivity(IntHTService);
				}catch(Exception ex)
				{
					Log.i(TAG,"[onCreate] Exception MetodosMapas Start: "+ex);	
				}					
			}
	    });
	    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    
	    ///TODO: EN VERDAD DEBERIAN SER DOS CLASES DISTINTAS; UNA PARA LOCAL-Tipo Y LA OTRA PARA CATEGORIA, PERO TODAVIA NO PROBE getSerializable...para un objeto dentro de otro  //FD v15.3.13
	     CategoryPoints[] objCategoryLoc=(CategoryPoints[]) getIntent().getSerializableExtra("DatosCatLoc");
		
	     if(this.ColocarPuntosEnMapa(objCategoryLoc))
		{
	    	 Log.i(TAG, "[onCreate] Se mostraron y colocaron las coordenadas en el mapa");		//DEBUG
		}else
		{
			Log.i(TAG, "[onCreate] NO SE COLOCARON PUNTOS EN MAPA");		//DEBUG
		}  
		    
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean ColocarPuntosEnMapa(CategoryPoints[] objCatLoc)
	{
		TextView txViewAddress;
		
		Geocoder geocoder = new Geocoder(objContext.getApplicationContext(), Locale.getDefault());
		
   		int x=0;
   		while(x<objCatLoc.length)
   		{
   			List<Address> addresses = null;
   			try {
				addresses = geocoder.getFromLocation(Double.valueOf(objCatLoc[x].getLatitud()),Double.valueOf(objCatLoc[x].getLongitud()), 1);			
				
				
			} catch (NumberFormatException e) {
				Log.i(TAG, "[onCreate] Adresses Number format exception: "+e  );		//DEBUG
				
				e.printStackTrace();
			} catch (IOException e) {
				Log.i(TAG, "[onCreate] Adresses IO exception: "+e  );		//DEBUG
				e.printStackTrace();
			}
   			Log.i(TAG, "[onCreate] Category Id: "+objCatLoc[x].getCategory() + " - Category Name: "+objCatLoc[x].getCategoryName());		//DEBUG
   			
   			if (addresses.size() > 0) 
   			{
   				
   				StringBuilder strTextLocations = new StringBuilder();
   				for(int i = 0; i < addresses.size(); i++)
   				{
   			        Address address =  addresses.get(i);
   			        int maxIndex = address.getMaxAddressLineIndex();
   			        for (int j = 0; j <= maxIndex; j++ )
   			        {
   			        	strTextLocations.append(address.getAddressLine(j));
   			       		strTextLocations.append(",");
   			        }   
   			        
		   		    strTextLocations.append(address.getLocality());
		   		    strTextLocations.append(",");
		   			strTextLocations.append(address.getPostalCode());
		   			strTextLocations.append("\n\n");
   			    }
   				
   				 txViewAddress=(TextView)findViewById(R.id.TextViewAddress);
   				txViewAddress.setText(strTextLocations.toString());
   			}else
   			{
   				
   				Log.i(TAG, "[onCreate] No se consiguieron las direcciones: " );		//DEBUG
   			}
   		}
	
		return true;
	}
	
}




    
    
