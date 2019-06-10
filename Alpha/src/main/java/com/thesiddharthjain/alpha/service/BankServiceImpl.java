/**
 * 
 */
package com.thesiddharthjain.alpha.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.thesiddharthjain.alpha.dto.BaseRequest;
import com.thesiddharthjain.alpha.dto.BaseResponse;

/**
 * @author sjxpl
 *
 */

@Service
public class BankServiceImpl implements BankService {
	
	@Value("${api.key}")
	private String apiKey;
	
	@Value("${api.secret}")
	private String apiSecKey;
	
	@Value("${BASE_URL}")
	private String base_url;
	
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HmacSha1Signature hmac;
	

	@Override
	public String getApiStatus() {
		// TODO Auto-generated method stub
		return "API is working..";
	}

	@Override
	public String getBankDetails(BaseRequest request) throws InvalidKeyException, SignatureException, NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		
		
		
		  Date date = new Date();  
          Long l = date.getTime();
          int tounce = l.intValue();

		String url= base_url+"&api_token="+apiKey+"&tounce="+tounce+"¤cy="+request.getCurrency();
		System.out.println("calling api ");
		System.out.println(url);
		
		
		String data = "GET|"+base_url+"|"+"api_token="+apiKey+"¤cy="+request.getCurrency()+"&tounce="+tounce;
		
		String signature = HmacSha1Signature.calculateRFC2104HMAC(data, apiSecKey);
		url = url +"&signature="+signature;
		
		HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity<String> entity = new HttpEntity<String>(headers);
	      String rs = restTemplate.exchange(
		         url, HttpMethod.GET, entity, String.class).getBody();
	      
	      
	      
	      
		return rs;
	}
	
	/*
	 * public static String hashGen(String req) { String hash = null;
	 * 
	 * hash = HMAC-SHA256(req, secret_key).to_hex
	 * 
	 * 
	 * return null; }
	 */
	
	protected static JsonElement canonicalize(JsonElement src) {
		  if (src instanceof JsonArray) {
		    // Canonicalize each element of the array
		    JsonArray srcArray = (JsonArray)src;
		    JsonArray result = new JsonArray();
		    for (int i = 0; i < srcArray.size(); i++) {
		      result.add(canonicalize(srcArray.get(i)));
		    }
		    return result;
		  } else if (src instanceof JsonObject) {
		    // Sort the attributes by name, and the canonicalize each element of the object
		    JsonObject srcObject = (JsonObject)src;
		    JsonObject result = new JsonObject();
		    TreeSet<String> attributes = new TreeSet<>();
		    for (Map.Entry<String, JsonElement> entry : srcObject.entrySet()) {
		      attributes.add(entry.getKey());
		    }
		    for (String attribute : attributes) {
		      result.add(attribute, canonicalize(srcObject.get(attribute)));
		    }
		    return result;
		  } else {
		    return src;
		  }
		}

}
