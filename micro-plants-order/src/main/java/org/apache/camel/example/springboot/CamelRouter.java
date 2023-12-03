/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.example.springboot;

import static org.apache.camel.model.rest.RestParamType.body;
import static org.apache.camel.model.rest.RestParamType.path;

import java.math.BigDecimal;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.example.springboot.model.BookingIdentifierService;
import org.apache.camel.example.springboot.model.GateException;
import org.apache.camel.example.springboot.model.OrderRequest;
import org.apache.camel.example.springboot.model.OrderSummaryResponse;
import org.apache.camel.example.springboot.model.models.CancelPlantsOrder;
import org.apache.camel.example.springboot.model.models.GetPlantsOrderSummary;
import org.apache.camel.example.springboot.model.models.GetPlantsOrderSummaryResponse;
import org.apache.camel.example.springboot.model.models.OrderPlants;
import org.apache.camel.example.springboot.model.models.PaymentResponse;
import org.apache.camel.example.springboot.model.models.PaymentRequest;
import org.apache.camel.example.springboot.model.models.OrderPlantsResponse;
import org.apache.camel.example.springboot.state.ProcessingEvent;
import org.apache.camel.example.springboot.state.ProcessingState;
import org.apache.camel.example.springboot.state.StateService;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.saga.CamelSagaService;
import org.apache.camel.saga.InMemorySagaService;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * A simple Camel REST DSL route.
 */
@Component
public class CamelRouter extends RouteBuilder {

    @org.springframework.beans.factory.annotation.Autowired
    OrdersIdentifierService ordersIdentifierService;

    final String plantsOrderUrl = "http://localhost:8081/soap-api/service/orderPlants";

    @Bean
    public CamelSagaService getsaga() {
        return new InMemorySagaService();
    }

    @Autowired
    PaymentService paymentService;

    @Autowired
    StateService plantsStateService;

    @Override
    public void configure() throws Exception {
        gateway();
        plants();
        payment();
    }

    private void gateway() {
        restConfiguration()
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
                .enableCORS(true)
                .corsAllowCredentials(true)
                .contextPath("/api")
                .apiContextPath("/api-doc");

        rest("/orderPlants").description("Order plants") // tu przyjmuje endpoint
                .post("/order").description("Plants store").type(OrderRequest.class)
                .consumes("application/json")
                .produces("application/json")
                // typ wchodzacy do post i typ wychodzacy z post
                .outType(OrderSummaryResponse.class)
                .param().name("body").type(body).description("Plants order param").endParam()
                // .to("kafka:PlantsOrderTopic?brokers=justynas-macbook-pro.home:9092");
                .to("direct:plantsOrder");

        // .responseMessage().code(200).message("Plants order ordered
        // successfully").endResponseMessage()
        // .responseMessage().code(400).responseModel(GateException.class).message("Post
        // order exception")
        // .endResponseMessage()


        rest("/payment").description("Finalize payment") 
                .post("/pay").description("Plants store").type(PaymentRequest.class)
                .consumes("application/json")
                .produces("application/json")
                .outType(PaymentResponse.class)
                .param().name("body").type(body).description("Payment param").endParam()
                // .to("kafka:PaymentTopic?brokers=justynas-macbook-pro.home:9092");
                .to("direct:payment");

        from("direct:payment")
        // from("kafka:PaymentTopic?brokers=justynas-macbook-pro.home:9092")
        .routeId("payment")
                .log("payment fired")
                .process((exchange) -> {
                    PaymentResponse paymentResponse = new PaymentResponse();
                    exchange.setProperty("paymentResponse", paymentResponse);
                })
                .saga()
                .multicast()
                .parallelProcessing()
                .aggregationStrategy(
                        (prevEx, currentEx) -> {
                            if (currentEx.getException() != null) {
                                return currentEx;
                            }

                            if (prevEx != null && prevEx.getException() != null) {
                                return prevEx;
                            }

                            PaymentResponse paymentSummary;
                            if (prevEx == null) {
                                paymentSummary = currentEx.getProperty("paymentResponse",
                                        PaymentResponse.class);
                            }

                        else {
                            paymentSummary = prevEx.getMessage().getBody(PaymentResponse.class);
                            }

                            Object body = currentEx.getMessage().getBody();                   

                            currentEx.getMessage().setBody(body);
                            return currentEx;
                        })
                        // .to("kafka:PaymentInfoTopic?brokers=justynas-macbook-pro.home:9092")
                .to("direct:paymentInfo") //change to kafka
               
                .end()
                .marshal().json()
                .log("order created").to("stream:out")
                .unmarshal().json(JsonLibrary.Jackson, PaymentResponse.class);

        from("direct:plantsOrder")
        // from("kafka:PlantsOrderTopic?brokers=justynas-macbook-pro.home:9092")
        .routeId("plantsOrder")
                .log("plantsOrder fired")
                .process((exchange) -> {
                    OrderSummaryResponse orderSummaryResponse = new OrderSummaryResponse();
                    String orderId = ordersIdentifierService.generateOrderId();
                    orderSummaryResponse.setId(orderId);
                    exchange.setProperty("orderId", orderId);
                    exchange.setProperty("orderSummaryResponse", orderSummaryResponse);
                })
                .saga()
                .multicast()
                .parallelProcessing()
                .aggregationStrategy(
                        (prevEx, currentEx) -> {
                            if (currentEx.getException() != null) {
                                return currentEx;
                            }

                            if (prevEx != null && prevEx.getException() != null) {
                                return prevEx;
                            }

                            OrderSummaryResponse orderSummary;
                            if (prevEx == null) {
                                orderSummary = currentEx.getProperty("orderSummaryResponse",
                                        OrderSummaryResponse.class);
                            }

                        else {
                                orderSummary = prevEx.getMessage().getBody(OrderSummaryResponse.class);
                            }

                            Object body = currentEx.getMessage().getBody();
                            if (body instanceof OrderPlantsResponse) {
                                orderSummary.setPlantsOrderSummary(((OrderPlantsResponse) body).getReturn());
                            }

                        else {
                                return prevEx;
                            }

                            currentEx.getMessage().setBody(orderSummary);
                            return currentEx;
                        })
                .to("direct:plants")
                // .to("kafka:PlantsTopic?brokers=justynas-macbook-pro.home:9092")
                .end()
                .marshal().json()
                .log("order created").to("stream:out")
                .unmarshal().json(JsonLibrary.Jackson, OrderSummaryResponse.class);

                

        // --------------------------Get
        // REST--------------------------------------------------------------------------//


        rest("/orderPlants").description("Get order summary")
                .produces("application/json")
                .get("/order/{orderId}")
                .outType(OrderSummaryResponse.class)
                .param().name("orderId").type(path).description("Order Id").endParam()
                // .to("kafka:getOrderPlantsTopic?brokers=justynas-macbook-pro.home:9092");
                .to("direct:getOrderPlants");
        // .responseMessage().code(200).message("The plants order get
        // successfully").endResponseMessage()
        // .responseMessage().code(400).responseModel(GateException.class).message("Get
        // order exception")
        // .endResponseMessage()

        from("direct:getOrderPlants")
        // from("kafka:getOrderPlantsTopic?brokers=justynas-macbook-pro.home:9092")
                .routeId("getOrderPlants")
                .log("getOrderPlants fired")
                .process(exchange -> {

                    String orderId = exchange.getMessage().getHeader("orderId", String.class);
                    ProcessingState previousState = plantsStateService.sendEvent(orderId,
                            ProcessingEvent.START);
                    if (previousState != ProcessingState.CANCELLED) {
                        OrderSummaryResponse orderSummaryResponse = new OrderSummaryResponse();
                        orderSummaryResponse.setId(orderId);
                        exchange.setProperty("orderSummaryResponse", orderSummaryResponse);
                        previousState = plantsStateService.sendEvent(orderId, ProcessingEvent.FINISH);

                    }
                    exchange.getMessage().setHeader("previousState", previousState);
                })
                .saga()
                .multicast()
                .parallelProcessing()
                .aggregationStrategy((prevEx, currentEx) -> {
                    if (currentEx.getException() != null) {
                        return currentEx;
                    }

                    if (prevEx != null && prevEx.getException() != null) {
                        return prevEx;
                    }

                    OrderSummaryResponse orderSummary;
                    if (prevEx == null) {
                        orderSummary = currentEx.getProperty("orderSummary",
                                OrderSummaryResponse.class);
                    }

                else {
                        orderSummary = prevEx.getMessage().getBody(OrderSummaryResponse.class);
                    }

                    Object body = currentEx.getMessage().getBody();
                    if (body instanceof GetPlantsOrderSummaryResponse) {
                        orderSummary.setPlantsOrderSummary(((GetPlantsOrderSummaryResponse) body).getReturn());
                    }

                else {
                        return prevEx;
                    }

                    currentEx.getMessage().setBody(orderSummary);

                    return currentEx;
                })
                .marshal().json()
                .to("stream:out")
                .choice()
                .when(header("previousState").isEqualTo(ProcessingState.CANCELLED))
                .to("direct:orderPlantsCompensationAction")
                // .to("kafka:orderPlantsCompensationActionTopic?brokers=justynas-macbook-pro.home:9092")
                .otherwise()
                // .to("kafka:getOrderTopic?brokers=justynas-macbook-pro.home:9092")
                .to("direct:getOrder") // change to kafka
                .endChoice();

        from("direct:orderPlantsCompensationAction")
        // from("kafka:orderPlantsCompensationActionTopic?brokers=justynas-macbook-pro.home:9092")
        .routeId("orderPlantsCompensationAction")
                .log("fired orderPlantsCompensationAction")
                .to("stream:out");

    }

    private void plants() {

        final JaxbDataFormat jaxbOrderPlantsResponse = new JaxbDataFormat(
                OrderPlantsResponse.class.getPackage().getName());

        // --------------------------Post
        // SOAP--------------------------------------------------------------------------//
        from("direct:plants")
        // from("kafka:PlantsTopic?brokers=justynas-macbook-pro.home:9092")
                .routeId("plants").log("plants fired")
                .saga()
                .propagation(SagaPropagation.MANDATORY)
                // .compensation("kafka:cancelPlantsOrderTopic?brokers=justynas-macbook-pro.home:9092")
                .option("orderSummary", simple("${exchangeProperty.orderSummaryResponse}"))
                .process((exchange) -> {
                    OrderRequest orderRequest = exchange.getMessage().getBody(OrderRequest.class);
                    OrderPlants orderPlants = new OrderPlants();
                    orderPlants.setArg0(orderRequest.getPlantsOrder());
                    System.out.println("SPRAWDZAM");
                    System.out.println(orderRequest.getPlantsOrder().getName());
                    System.out.println(orderRequest.getPlantsOrder().getAmount());
                    exchange.getMessage().setBody(orderPlants);

                })
                .marshal(jaxbOrderPlantsResponse)
                .to("spring-ws:" + plantsOrderUrl)
                .to("stream:out")
                .unmarshal(jaxbOrderPlantsResponse)
                .process((exchange) -> {
                    OrderPlantsResponse plantsOrderSummary = exchange.getMessage().getBody(OrderPlantsResponse.class);
                    String orderId = exchange.getProperty("orderId", String.class);
                    System.out.println("TUUUU");
                    System.out.println(orderId);
                    System.out.println(plantsOrderSummary.getReturn().getCost());
    
                    ordersIdentifierService.assignPlantsOrderId(orderId, plantsOrderSummary.getReturn().getId());

                });
                

        from("direct:cancelPlantsOrder")
        // from("kafka:cancelPlantsOrderTopic?brokers=justynas-macbook-pro.home:9092")
        .routeId("cancelPlantsOrder")
                .log("cancelPlantsOrder fired")
                .process(exchange -> {
                    OrderSummaryResponse orderSummaryResponse = exchange.getMessage().getHeader("orderSummary",
                            OrderSummaryResponse.class);
                    String plantOrderId = ordersIdentifierService.getPlantsOrderId(orderSummaryResponse.getId());
                    CancelPlantsOrder cancelPlantsOrder = new CancelPlantsOrder();
                    cancelPlantsOrder.setArg0(plantOrderId);
                    exchange.getMessage().setBody(cancelPlantsOrder);
                })
                .marshal(jaxbOrderPlantsResponse)
                .to("spring-ws:" + plantsOrderUrl + "/cancelPlantsOrder");

        // --------------------------Get
        // SOAP--------------------------------------------------------------------------//

        from("direct:getOrder")
        // from("kafka:getOrderTopic?brokers=justynas-macbook-pro.home:9092")
                .routeId("getOrder").log("getOrder fired")
                .process(exchange -> {
                    String mainOrderId = exchange.getMessage().getHeader("orderId", String.class);
                    System.out.println(exchange.getMessage());
                    String plantsOrderId = ordersIdentifierService.getPlantsOrderId(mainOrderId);

                    GetPlantsOrderSummary getPlantsOrderSummary = new GetPlantsOrderSummary();
                    getPlantsOrderSummary.setArg0(plantsOrderId);
                    exchange.getMessage().setBody(getPlantsOrderSummary);
                })
                .marshal(jaxbOrderPlantsResponse)
                .to("spring-ws:" + plantsOrderUrl + "/getPlantsOrderSummary")
                .unmarshal(jaxbOrderPlantsResponse);

    }

    private void payment() {

        from("direct:paymentInfo")
        // from("kafka:PaymentInfoTopic?brokers=justynas-macbook-pro.home:9092")
                .routeId("paymentInfo").log("paymentInfo fired")
                .saga()
                .propagation(SagaPropagation.MANDATORY)
                // .compensation("direct:cancelPayment")
                .option("paymentSummary", simple("${exchangeProperty.paymentResponse}"))
                .process(exchange -> {
                    PaymentRequest paymentRequest = exchange.getMessage().getBody(PaymentRequest.class);
                    exchange.getMessage().setBody(paymentRequest);
            
                })
                .marshal().json()
                .removeHeaders("CamelHttp*")
                .to("rest:post:payment?host=localhost:8083")
                .unmarshal().json(JsonLibrary.Jackson, PaymentResponse.class)
                .to("stream:out")
                .process((exchange) -> {
                    PaymentResponse paymentResponse = exchange.getMessage().getBody(PaymentResponse.class);
                    System.out.print(paymentResponse.getTransactionId());
                    System.out.print(paymentResponse.getTransactionDate());
                });

    }

}
