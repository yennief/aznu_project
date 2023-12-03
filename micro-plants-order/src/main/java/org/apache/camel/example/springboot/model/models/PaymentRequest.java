package org.apache.camel.example.springboot.model.models;

public class PaymentRequest {
    private org.apache.camel.example.springboot.PaymentCard paymentCard;
    private String orderId;

    public org.apache.camel.example.springboot.PaymentCard getPaymentCard() {
        return paymentCard;
    }

    public void setPaymentCard(org.apache.camel.example.springboot.PaymentCard pc) {
        this.paymentCard = pc;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
