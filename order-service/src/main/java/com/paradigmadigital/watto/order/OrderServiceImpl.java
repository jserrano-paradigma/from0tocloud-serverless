package com.paradigmadigital.watto.order;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
    private OrderRepository orderRepository;
	
	@Value("${spring.service.orders.url}")
	String orderResourceUrl;
	
	@Value("${spring.service.orders.cognito}")
	String cognitoUrl;
	
	@Value("${spring.service.orders.cognito.clientId}")
	String username;
	
	@Value("${spring.service.orders.cognito.clientSecret}")
	String password;
	
	@Value("${spring.service.orders.apiKey}")
	String apiKey;

	@Override
	public Order createOrder(Order orderDTO) throws JsonMappingException, JsonProcessingException {
		
		Order order = orderRepository.save(orderDTO);
		
		//Rest Service
		RestTemplate restTemplate = new RestTemplate();
		
		//1. Request Token
		
		//Body Parameters token
		MultiValueMap<String, String> mapCognito= new LinkedMultiValueMap<>();
		mapCognito.add("grant_type", "client_credentials");
		mapCognito.add("scope", "dev-resource-server/cloudFriendly.write");
		
		//request token
		HttpEntity<MultiValueMap<String, String>> requestToken = new HttpEntity<>(mapCognito, createHeaders());
		ResponseEntity<String> tokenResponse = restTemplate.postForEntity(cognitoUrl, requestToken, String.class);
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode root = mapper.readTree(tokenResponse.getBody());
	    JsonNode accessToken = root.get("access_token");
	   
	    //2. Insert Order Status in AWS
	    
	    //Headers and body parameters
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	    headers.setBearerAuth(accessToken.textValue());
	    headers.add("x-api-key", apiKey);
		   
	    Map<String, String> mapOrders = new HashMap<>();
	    mapOrders.put("orderId", orderDTO.getId());
	    mapOrders.put("orderStatus", "RECIBIDO");
	    mapOrders.put("description", "El pedido ha sido recibido.");

	    // request insert in AWS
	    HttpEntity<Map<String, String>> request = new HttpEntity<>(mapOrders, headers);
	    restTemplate.postForObject(orderResourceUrl, request, String.class);
	  
		return order;
	}
	
	private HttpHeaders createHeaders(){
		
		   return new HttpHeaders() {{
		         String auth = username + ":" + password;
		         byte[] encodedAuth = Base64.encodeBase64( 
		            auth.getBytes(Charset.forName("US-ASCII")) );
		         String authHeader = "Basic " + new String( encodedAuth );
		         set( "Authorization", authHeader );
		         setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		      }};
		}
}
