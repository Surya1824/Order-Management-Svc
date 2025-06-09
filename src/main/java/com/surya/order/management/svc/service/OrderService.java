package com.surya.order.management.svc.service;

import com.surya.order.management.svc.excpetion.InvalidInputException;
import com.surya.order.management.svc.model.OrderDetails;
import com.surya.order.management.svc.model.OrderMessage;
import com.surya.order.management.svc.model.OrderStatusEnum;
import com.surya.order.management.svc.model.PaymentMode;
import com.surya.order.management.svc.repository.OrderRepository;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("LoggingSimilarMessage")
@Service
public class OrderService {

    public static final String ORDER_DETAILS_FOUND = "Order Details found!!!";
    public static final String ORDER_DETAILS_NOT_FOUND_INVALID_ORDER_ID = "Order Details Not found, Invalid Order Id";
    private final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    private final RabbitMQProducer rabbitMQProducer;

    public OrderService(OrderRepository orderRepository, RabbitMQProducer rabbitMQProducer) {
        this.orderRepository = orderRepository;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    public ResponseEntity<String> placeOrder(@Valid OrderDetails orderDetails) {
        logger.info("Entry placeOrder>>>");
        OrderDetails placedOrderDetails;

        if(orderDetails.getPaymentMode().equals(PaymentMode.CASH_ON_DELIVERY)) {
            orderDetails.setOrderStatus(OrderStatusEnum.CONFIRMED);
        }else{
            orderDetails.setOrderStatus(OrderStatusEnum.PLACED);
            //TODO Call PaymentManagement service for Payment
        }
        placedOrderDetails = orderRepository.save(orderDetails);
//        rabbitMQProducer.updateInventory(new OrderMessage(UUID.randomUUID().toString(), orderDetails.getOrderStatus().toString(),
//                orderDetails.getOrderedProduct(), LocalDateTime.now()));

        logger.info("Exit placeOrder<<<");
        return ResponseEntity.status(HttpStatus.CREATED).body("Thanks for Placing Order!!\n" + "Your OrderId : " + placedOrderDetails.getOrderId());
    }

    public ResponseEntity<OrderDetails> getOrderByUserId(Long userId) throws InvalidInputException {
        logger.info("Entry getOrderByUserId>>>");
        Optional<OrderDetails> getDetailsByUserId = orderRepository.findByUserId(userId);

        if(getDetailsByUserId.isPresent()){
            logger.info(ORDER_DETAILS_FOUND);
            return ResponseEntity.status(HttpStatus.OK).body(getDetailsByUserId.get());
        }else{
            logger.error("Order Details Not found, Invalid User Id");
            throw new InvalidInputException("Order Details Not found, Invalid User Id");
        }

    }

    public ResponseEntity<OrderDetails> getByOrderId(Long orderId) throws InvalidInputException {
        logger.info("Entry getByOrderId>>>");
        Optional<OrderDetails> getDetailsByUserId = orderRepository.findById(orderId);

        if(getDetailsByUserId.isPresent()){
            logger.info(ORDER_DETAILS_FOUND);
            return ResponseEntity.status(HttpStatus.OK).body(getDetailsByUserId.get());
        }else{
            logger.error(ORDER_DETAILS_NOT_FOUND_INVALID_ORDER_ID);
            throw new InvalidInputException(ORDER_DETAILS_NOT_FOUND_INVALID_ORDER_ID);
        }

    }

    public ResponseEntity<OrderDetails> cancelByOrderId(Long orderId) throws InvalidInputException {
        logger.info("Entry cancelByOrderId>>>");
        Optional<OrderDetails> byOrderIdResponse = orderRepository.findById(orderId);

        if(byOrderIdResponse.isPresent()){
            logger.info("Order Details found by OrderId!!!");
            OrderDetails orderDetails = byOrderIdResponse.get();
            orderDetails.setOrderStatus(OrderStatusEnum.CANCELED);
            OrderDetails cancelledOrderDetails = orderRepository.save(orderDetails);

//            rabbitMQProducer.updateInventory(new OrderMessage(UUID.randomUUID().toString(), cancelledOrderDetails.getOrderStatus().toString(),
//                    cancelledOrderDetails.getOrderedProduct(), LocalDateTime.now()));

            logger.info("Exit cancelByOrderId<<<");
            return ResponseEntity.status(HttpStatus.OK).body(cancelledOrderDetails);
        }else{
            logger.error(ORDER_DETAILS_NOT_FOUND_INVALID_ORDER_ID);
            throw new InvalidInputException(ORDER_DETAILS_NOT_FOUND_INVALID_ORDER_ID);
        }

    }
}
