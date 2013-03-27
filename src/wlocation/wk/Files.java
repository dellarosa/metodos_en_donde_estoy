package wlocation.wk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.os.Environment;
import android.util.Log;

//////////####################################READ ##########################////////////////////////////

public class Files
{
	public String ReadFile(String path, String name) 
	{
		Log.i("Files","[ReadFile] Dentro ReadFiles :");				//DEBUG
		
		String url = path + name;
		//String url= System.getProperty("file:///C:/") + name;
		String smail = null;
		
		//File fDiskDir = Environment.getRootDirectory();
		//System.out.println("[ReadFile] DIR2: "+fDiskDir.getAbsolutePath()+path);
		//File file = new File(fDiskDir.getAbsolutePath()+path,name);		
				        
		File fTarjeta = Environment.getExternalStorageDirectory();
		System.out.println("[ReadFile] DIR E: "+fTarjeta.getAbsolutePath()+path);
		File file = new File(fTarjeta.getAbsolutePath()+path,name);
				
		  try 
		  {
			    FileInputStream fIn = new FileInputStream(file);            
	            InputStreamReader archivo=new InputStreamReader(fIn);
	            
	            BufferedReader br=new BufferedReader(archivo);
	            String linea = null;
	            String todo="";
	            
				try 
				{
					linea = br.readLine();
					if(linea==null)
					{
						return null;
					}
					else
					{
			            while (linea!=null)
			            {
			                todo=todo+linea+"\n";			               
							linea=br.readLine();
			            }
					}
				} catch (IOException e) 
				{
					e.printStackTrace();
				}
	            smail=todo;
	            
	            try 
	            {
					br.close();
					archivo.close();					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}	           
		  } catch (java.io.FileNotFoundException e) 
		  {		
			  Log.i("Files","[ReadFile] ERROR READ: " + e+"\n");								//DEBUG
			  System.out.println("[ReadFile] ERROR READ: " + e+"\n");
		  }	  
		  
		  
	            
		System.out.println("[ReadFile] FILE LEIDO: " + smail+"\n");
		return smail;
	} 

//////////////////////////////////////////////////////////// SAVE FILE ////////////////////////////////
	public void SaveFile(String path, String name,String DataToSave) 
	{
		System.out.println("[saveFile]*********************************************************");
		String url = path + name;
	
		
		if(!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			System.out.println("External SD card not mounted");
			
			}
		
		File fDiskDirectory = new File(Environment.getExternalStorageDirectory () + path); 
		boolean success = false;
		if(!fDiskDirectory.exists()){
            success = fDiskDirectory.mkdirs();
        }
        if (!success){ 
            Log.d("[saveFile]","Folder not created.");
        }
        else{
            Log.d("[saveFile]","Folder created!");
        }
		
		File fDiskDir = Environment.getRootDirectory();
		System.out.println("[saveFile] DIR2: "+fDiskDir.getAbsolutePath()+path);
		File file = new File(fDiskDir.getAbsolutePath()+path,name);
		
				
		//File fTarjeta = Environment.getExternalStorageDirectory();
		//System.out.println("[saveFile] DIR E: "+fTarjeta.getAbsolutePath()+path);
		 //File file = new File(fTarjeta.getAbsolutePath()+path,name);

			try 
			{				
				System.out.println("[saveFile] DATOS A GRABAR: "+DataToSave);
			
				OutputStreamWriter osw =new OutputStreamWriter(new FileOutputStream(file));
	            
	            osw.write(DataToSave);
	            osw.flush();
	            osw.close();

	            System.out.println("[saveFile] DATOS GUARDADOS: "+DataToSave);	            			  
			  
			} catch (java.io.IOException e) {
				 System.out.println("[saveFile] ERROR: "+e);
			}
	
	} 

}