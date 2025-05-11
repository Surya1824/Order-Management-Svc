package com.surya.order.management.svc.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class OrderMessage {

    private String messageId;
    private String orderStatus;
    private List<OrderedProduct> orderedProduct;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date date;

    public OrderMessage() {
    }

    public OrderMessage(String messageId,  String orderStatus, List<OrderedProduct> orderedProduct, Date date) {
        this.messageId = messageId;
        this.orderStatus = orderStatus;
        this.orderedProduct = orderedProduct;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "OrderMessage{" +
                "messageId='" + messageId + '\'' +
                ", orderedProduct=" + orderedProduct +
                ", date=" + date +
                '}';
    }
}
