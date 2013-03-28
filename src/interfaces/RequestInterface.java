package interfaces;

import metodos.RequestTask;
import android.os.CountDownTimer;
import android.util.Log;


public interface RequestInterface {

	public String resultadoRequest();
	public void errorRequest(String status, int errorCode);
	
	
}
