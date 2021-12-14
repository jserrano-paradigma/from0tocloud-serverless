package com.paradigmadigital.watto.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class OrderController {
	
	@Autowired private OrderService orderService;

	@PostMapping(value="/orders")
	public ResponseEntity<Order> creaateOrder(@RequestBody Order order) throws JsonMappingException, JsonProcessingException{
		
		Order orderResponse = orderService.createOrder(order);
		if (orderResponse != null) {
			return ResponseEntity.ok(orderResponse);	
		}
		return ResponseEntity.noContent().build();
	}

}