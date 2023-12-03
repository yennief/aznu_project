package org.apache.camel.example.springboot;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.apache.camel.example.springboot.model.OrderRequest;
import org.apache.camel.example.springboot.model.OrderSummaryResponse;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private HashMap<String, PaymentData> payments;

    @PostConstruct
    void init() {
        payments = new HashMap<>();
    }

    public static class PaymentData {
  
        Integer cost;

        public boolean isReady() {
            return cost != null;
        }
    }

    public synchronized boolean addOrderInfo(String orderId, Integer cost) {
        PaymentData paymentData = getPaymentData(orderId);
        paymentData.cost = cost;
        return paymentData.isReady();
    }

    public synchronized PaymentData getPaymentData(String orderId) {
        PaymentData paymentData = payments.get(orderId);
        if (paymentData == null) {
            paymentData = new PaymentData();
            payments.put(orderId, paymentData);
        }
        return paymentData;
    }

}
