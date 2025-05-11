package com.surya.order.management.svc.service;

import com.surya.order.management.svc.excpetion.InvalidInputException;
import com.surya.order.management.svc.model.OrderDetails;
import com.surya.order.management.svc.model.OrderMessage;
import com.surya.order.management.svc.model.OrderStatusEnum;
import com.surya.order.management.svc.repository.OrderRepository;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    private final RabbitMQProducer rabbitMQProducer;

    public OrderService(OrderRepository orderRepository, RabbitMQProducer rabbitMQProducer) {
        this.orderRepository = orderRepository;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    public ResponseEntity<String> placeOrder(@Valid OrderDetails orderDetails) {
        logger.info("Entry placeOrder>>>");

        orderDetails.setOrderStatus(OrderStatusEnum.PLACED);
        OrderDetails placedOrderDetails = orderRepository.save(orderDetails);

        rabbitMQProducer.updateInventory(new OrderMessage(UUID.randomUUID().toString(), orderDetails.getOrderStatus().toString(),
                orderDetails.getOrderedProduct(), new Date()));

        logger.info("Exit placeOrder<<<");
        return ResponseEntity.status(HttpStatus.CREATED).body("Thanks for Placing Order!!\n" + "Your OrderId : " + placedOrderDetails.getOrderId());
    }

    public ResponseEntity<OrderDetails> getOrderByUserId(Long userId) throws InvalidInputException {
        logger.info("Entry getOrderByUserId>>>");
        Optional<OrderDetails> getDetailsByUserId = orderRepository.findByUserId(userId);

        if(getDetailsByUserId.isPresent()){
            logger.info("Order Details found!!!");
            return ResponseEntity.status(HttpStatus.OK).body(getDetailsByUserId.get());
        }else{
            logger.error("Order Details Not found, Invalid User Id");
            throw new InvalidInputException("Order Details Not found, Invalid User Id");
        }

    }

    public ResponseEntity<OrderDetails> getOrderByOrderId(Long orderId) throws InvalidInputException {
        logger.info("Entry getOrderByOrderId>>>");
        Optional<OrderDetails> byOrderIdResponse = orderRepository.findById(orderId);

        if(byOrderIdResponse.isPresent()){
            logger.info("Order Details found by OrderId!!!");
            OrderDetails orderDetails = byOrderIdResponse.get();
            orderDetails.setOrderStatus(OrderStatusEnum.CANCELED);
            OrderDetails cancelledOrderDetails = orderRepository.save(orderDetails);
            return ResponseEntity.status(HttpStatus.OK).body(cancelledOrderDetails);
        }else{
            logger.error("Order Details Not found, Invalid Order Id");
            throw new InvalidInputException("Order Details Not found, Invalid Order Id");
        }

    }
}
