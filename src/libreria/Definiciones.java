package libreria;

public class Definiciones {

	public class Definicionesgenerales
	{
		public final static int incantdigitosPass=8;
		//public final static String servidor= "http://localhost:4000";
		//public final static String servidor= "http://sharedpc.dnsalias.com:3001";
		
		//public final static String servidor= "http://190.16.186.133:3001";
		public final static String servidor= "http://192.168.0.28:3333";
		//public static final String strversion="00.00.01";
		/////////////////////////////////////////////////////
		/////////CODIGOS DE RESPUESTA //////////////////////
		public final static int SUCCESS=000;
		public final static int REPEAT_NAME=600;
		public final static int PARAMETER_ERROR=603;
		public final static int ERROR=999;
		public final static int DEVICE_NOT_FOUND=405;
		public final static int tiempoesperaenvio=5;
		
		
		
	}
	public class Transacciones
	{
		public final static int actualizacioncategorias=0x00;
		public final static int verificarlogin=0x01;
		public final static int creardevice=0x02;
	}
}
