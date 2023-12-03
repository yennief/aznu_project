package com.example.storeui;

import com.example.storeui.model.GetOrderRequest;
import com.example.storeui.model.Fault;
import com.example.storeui.model.OrderRequest;
import com.example.storeui.model.OrderSummaryResponse;
import com.example.storeui.model.PaymentRequest;
import com.example.storeui.model.PaymentResponse;
import com.example.storeui.model.PlantFaultMsg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class StoreController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/")
    public String actionsForm(Model model) {
        return "index";
    }

    @GetMapping("/pay")
    public String paymentForm(@RequestParam(name = "id") String orderId, Model model) {
        System.out.println(orderId);
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrderId(orderId);
        model.addAttribute("paymentRequest", paymentRequest);
        return "payment";
    }

    @PostMapping("/pay")
    public String pay(@ModelAttribute PaymentRequest paymentRequest, Model model) {
        try {
            System.out.println(paymentRequest);

            ResponseEntity<PaymentResponse> response = restTemplate.postForEntity(
                    "http://localhost:8085/api/payment/pay",
                    paymentRequest,
                    PaymentResponse.class);
            model.addAttribute("paymentResponse", response.getBody());
            return "paymentSummary";
        } catch (Exception ex) {
            // System.err.print(ex.getMessage());
            model.addAttribute("fault", ex.getMessage());
            return "fault";
        }
    }

    @GetMapping("/orderPlants")
    public String orderPlantsForm(Model model) {
        model.addAttribute("orderRequest", new OrderRequest());
        return "orderPlants";
    }

    @PostMapping("/orderPlants")
    public String orderPlants(@ModelAttribute OrderRequest orderRequest, Model model) {
        try {
            ResponseEntity<OrderSummaryResponse> response = restTemplate.postForEntity(
                    "http://localhost:8085/api/orderPlants/order",
                    orderRequest,
                    OrderSummaryResponse.class);
            model.addAttribute("orderSummaryResponse", response.getBody());
            return "orderSummary";
        } catch (Exception ex) {
            // System.err.print(ex.getMessage());
            model.addAttribute("fault", ex.getMessage());
            return "fault";
        }
    }

    @GetMapping("/getOrder")
    public String findOrderForm(Model model) {
        model.addAttribute(new GetOrderRequest());
        return "getOrder";
    }

    // @PostMapping("/getOrder")
    // public String findOrder(@ModelAttribute GetOrderRequest getOrderRequest,
    // Model model) {
    // try {
    // ResponseEntity<OrderSummaryResponse> response = restTemplate.getForEntity(
    // String.format("http://localhost:8085/api/orderPlants/order/%s",
    // getOrderRequest.getId()),
    // OrderSummaryResponse.class);
    // model.addAttribute("orderSummaryResponse", response.getBody());
    // return "orderSummary";
    // } catch (HttpStatusCodeException ex) {
    // model.addAttribute("fault", ex);
    // return "fault";
    // }
    // }
}
