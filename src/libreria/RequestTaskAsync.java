package libreria;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import android.os.AsyncTask;
import android.util.Log;


public class RequestTaskAsync extends AsyncTask<String, String, String>{

	private String strResponse;
	
	
	String TAG="RequestTask";
	public RequestTaskAsync()
	{
		strResponse=null;
	}
	public String getResponse()
	{
		return strResponse;
	}
	public void SetResponse(String strResp)
	{
		this.strResponse=strResp;
	}
    @Override
    protected String doInBackground(String... uri) {
    	Log.i(TAG, "[doInBackground] DENTRO ");		//DEBUG
    	
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        Log.i(TAG, "[doInBackground] DATA TO SEND: "+uri[0]);		//DEBUG
        try {
        	//HttpGet htget= new HttpGet();
        	new BasicHttpParams();
        	
            response = httpclient.execute(new HttpGet(uri[0]));
            StatusLine statusLine = response.getStatusLine();
            Log.i(TAG, "[doInBackground] STATUS LINE OK" );		//DEBUG
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
                Log.i(TAG, "[doInBackground] Response string OK" );		//DEBUG
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                Log.e(TAG, "[doInBackground] ErrorException IOEXCEPT" );		//DEBUG
                //return null;
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            //TODO Handle problems..
        	Log.e(TAG, "[doInBackground] ErrorException:"+e );		//DEBUG
        	this.SetResponse(null);
        	return null;
        } catch (IOException e) {
            //TODO Handle problems..
        	Log.e(TAG, "[doInBackground] ErrorException:"+e );		//DEBUG
        	this.SetResponse(null);
        	return null;
        }
        
        Log.i(TAG, "[doInBackground] Response URL: "+responseString );		//DEBUG
        
        this.SetResponse(responseString);
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Do anything with response..
        
        
    }
}

