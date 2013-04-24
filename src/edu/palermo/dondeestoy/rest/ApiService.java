package edu.palermo.dondeestoy.rest;

import java.net.URI;
import java.util.*;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import edu.palermo.dondeestoy.bo.BaseResponse;
import edu.palermo.dondeestoy.bo.LocationPoint;
import edu.palermo.dondeestoy.bo.NearLocationPointsResponse;
import edu.palermo.dondeestoy.bo.Requestclass;
import edu.palermo.dondeestoy.bo.Requestclass.Request_UpdateLocation;
import edu.palermo.dondeestoy.bo.Responseclass;

import android.util.Log;


public class ApiService {

	public NearLocationPointsResponse getNearLocationPoints(double lat, double lng) {

		Map<String, String> variables = new HashMap<String, String>();
		
		variables.put("latitude", String.valueOf(lat));
        variables.put("longitude", String.valueOf(lng));
        variables.put("category", "all");
             
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
		
		String url = "http://192.168.0.28:3333/api/locations/find_near_locations/{latitude}/{longitude}/{category}";

		ResponseEntity<NearLocationPointsResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, NearLocationPointsResponse.class, variables);
		
		NearLocationPointsResponse nearLocationPointsResponse = responseEntity.getBody();
		
		return nearLocationPointsResponse;
	
	}
	

	 
	public Responseclass.Response_CategoriasDisponibles getCategoriasDisponibles() {

	
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
		
		//String urlCatLoc = new String(Definiciones.Definicionesgenerales.servidor+"/api/locations/get_all_categories");
		String url = "http://192.168.0.28:3333/api/locations/get_all_categories";

		ResponseEntity<Responseclass.Response_CategoriasDisponibles> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Responseclass.Response_CategoriasDisponibles.class);
		
		Responseclass.Response_CategoriasDisponibles response_CategoriasDisponibles = responseEntity.getBody();
		
		return response_CategoriasDisponibles;
	
	}
	
	public Responseclass.Response_GetLocacionDevice getLocationByDevice(String device) {

		Map<String, String> variables = new HashMap<String, String>();
		
        variables.put("device", device);
             
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
		
		//String urlCatLoc = new String(Definiciones.Definicionesgenerales.servidor+"/api/locations/get_all_categories");
		String url = "http://192.168.0.28:3333/api/locations/{device}/get_location";
		
		//String Prueba="{" +"/"+"location_point/"+":[{"+"/"+"latitude/"+":-22.22,"+"/"+"longitude/"+":23.23,"+"/"+"created_at/"+":" +"/"+"2013-03-24 18:06/"+"}],"+"/"+"code/"+":"+"/"+"000"+"/"+ "}";
		
		//ResponseEntity<Responseclass.Response_GetLocacionDevice> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Responseclass.Response_GetLocacionDevice.class, variables);
		ResponseEntity<Responseclass.Response_GetLocacionDevice> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Responseclass.Response_GetLocacionDevice.class, variables);
		Log.i("API","RESPONSE: "+responseEntity.toString());
		Responseclass.Response_GetLocacionDevice response_GetLocacionDevice = responseEntity.getBody();
		
		return response_GetLocacionDevice;
	
	}
	
	
	public Responseclass.Response_TiposDisponibles getTiposDisponibles() {

		Map<String, String> variables = new HashMap<String, String>();
		
       // variables.put("category", "all");
             
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
		
		//String urlCatLoc = new String(Definiciones.Definicionesgenerales.servidor+"/api/locations/get_all_categories");
		String url = "http://192.168.0.28:3333/api/locations/get_all_types";

		ResponseEntity<Responseclass.Response_TiposDisponibles> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Responseclass.Response_TiposDisponibles.class, variables);
		
		Responseclass.Response_TiposDisponibles response_TiposDisponibles = responseEntity.getBody();
		
		return response_TiposDisponibles;
	
	}
	
	public boolean postUpdateLocation(double latitude,double longitude,int id) {

		Map<String, Double> variables = new HashMap<String, Double>();
			
		 variables.put("latitude", latitude);
		 variables.put("longitude",longitude);
		 
		Requestclass rq=new Requestclass();		
		Requestclass.Request_UpdateLocation uplocation = rq.new Request_UpdateLocation();		 
		uplocation.setLatitude(latitude);
		uplocation.setLongitude(longitude);
		        
             
		HttpHeaders requestHeaders = new HttpHeaders();
		//requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
		requestHeaders.setContentType(new MediaType("application","json"));
		
		//HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		HttpEntity<Requestclass.Request_UpdateLocation> requestEntity = new HttpEntity<Requestclass.Request_UpdateLocation>(uplocation, requestHeaders);
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
		
		String url = "http://192.168.0.28:3333/api/devices/"+id+"/update_location";			
		
		ResponseEntity<BaseResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, BaseResponse.class,variables);
		BaseResponse result = responseEntity.getBody();
		
		return true;
	
	}
	
}
