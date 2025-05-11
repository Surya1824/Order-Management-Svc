package com.surya.order.management.svc.controller;

import com.surya.order.management.svc.excpetion.InvalidInputException;
import com.surya.order.management.svc.model.OrderDetails;
import com.surya.order.management.svc.service.OrderService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place/order")
    public ResponseEntity<String> placeOrder(@RequestBody @Valid OrderDetails orderDetails){
        logger.info("Entry placeOrder>>>");

        ResponseEntity<String> orderResponse = orderService.placeOrder(orderDetails);
        logger.info("Exit placeOrder: {}", orderResponse);
        return orderResponse;
    }

    @GetMapping("/get/orderDetails/userId/{userId}")
    public ResponseEntity<OrderDetails> getOrderDetailsByUserId(@PathVariable(name = "userId") Long userId) throws InvalidInputException {
        logger.info("Entry getOrderDetailsByUserId>>> ");

        ResponseEntity<OrderDetails> orderResponse = orderService.getOrderByUserId(userId);
        logger.info("Exit getOrderDetailsByUserId: {}", orderResponse);
        return orderResponse;
    }

    @GetMapping("/get/orderDetails/orderId/{orderId}")
    public ResponseEntity<OrderDetails> getOrderDetailsByOrderId(@PathVariable(name = "orderId") Long orderId) throws InvalidInputException {
        logger.info("Entry getOrderDetailsByOrderId>>> ");

        ResponseEntity<OrderDetails> orderResponse = orderService.getOrderByOrderId(orderId);
        logger.info("Exit getOrderDetailsByOrderId: {}", orderResponse);
        return orderResponse;
    }

    @GetMapping("/cancel/order/{orderId}")
    public ResponseEntity<OrderDetails> cancelByOrderId(@PathVariable(name = "orderId") Long orderId) throws InvalidInputException {
        logger.info("Entry cancelByOrderId>>> ");

        ResponseEntity<OrderDetails> orderResponse = orderService.getOrderByOrderId(orderId);
        logger.info("Exit cancelByOrderId: {}", orderResponse);
        return orderResponse;
    }
}
