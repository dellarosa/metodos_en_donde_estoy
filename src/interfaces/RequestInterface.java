package interfaces;

import libreria.RequestTaskAsync;
import android.os.CountDownTimer;
import android.util.Log;


public interface RequestInterface {									//Por ahora no utilizado - TODO Ver mejora y migraci�n a futuro

	public String resultadoRequest();
	public void errorRequest(String status, int errorCode);
	
	
}
