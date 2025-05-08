package com.surya.order.management.svc.controller;

import com.surya.order.management.svc.model.OrderDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    public ResponseEntity<String> createOrder(@RequestBody OrderDetails orderDetails){
        return null;
    }
}
