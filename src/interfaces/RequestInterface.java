package interfaces;

import metodos.RequestTaskAsync;
import android.os.CountDownTimer;
import android.util.Log;


public interface RequestInterface {									//Por ahora no utilizado - TODO Ver mejora y migración a futuro

	public String resultadoRequest();
	public void errorRequest(String status, int errorCode);
	
	
}
