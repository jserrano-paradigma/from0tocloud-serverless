package com.paradigmadigital.watto.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface OrderService {

   
    public Order createOrder(Order orderDTO) throws JsonMappingException, JsonProcessingException;
  
}
