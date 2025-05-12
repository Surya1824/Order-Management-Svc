package com.surya.order.management.svc.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class OrderMessage {

    private String messageId;
    private String orderStatus;
    private List<OrderedProduct> orderedProduct;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    public OrderMessage() {
    }

    public OrderMessage(String messageId,  String orderStatus, List<OrderedProduct> orderedProduct, LocalDateTime createdAt) {
        this.messageId = messageId;
        this.orderStatus = orderStatus;
        this.orderedProduct = orderedProduct;
        this.createdAt = createdAt;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderedProduct> getOrderedProduct() {
        return orderedProduct;
    }

    public void setOrderedProduct(List<OrderedProduct> orderedProduct) {
        this.orderedProduct = orderedProduct;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "OrderMessage{" +
                "messageId='" + messageId + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderedProduct=" + orderedProduct +
                ", createdAt=" + createdAt +
                '}';
    }
}
