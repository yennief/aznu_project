//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.11.18 at 01:14:26 PM CET 
//


package org.apache.camel.example.springboot.model.models;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.exampl.plants package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CancelPlantsOrder_QNAME = new QName("http://plantsOrder.bp.org/", "cancelPlantsOrder");
    private final static QName _CancelPlantsOrderResponse_QNAME = new QName("http://plantsOrder.bp.org/", "cancelPlantsOrderResponse");
    private final static QName _GetPlantsOrderSummary_QNAME = new QName("http://plantsOrder.bp.org/", "getPlantsOrderSummary");
    private final static QName _GetPlantsOrderSummaryResponse_QNAME = new QName("http://plantsOrder.bp.org/", "getPlantsOrderSummaryResponse");
    private final static QName _OrderPlants_QNAME = new QName("http://plantsOrder.bp.org/", "orderPlants");
    private final static QName _OrderPlantsResponse_QNAME = new QName("http://plantsOrder.bp.org/", "orderPlantsResponse");
    private final static QName _Fault_QNAME = new QName("http://plantsOrder.bp.org/", "Fault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.exampl.plants
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CancelPlantsOrder }
     * 
     */
    public CancelPlantsOrder createCancelPlantsOrder() {
        return new CancelPlantsOrder();
    }

    /**
     * Create an instance of {@link CancelPlantsOrderResponse }
     * 
     */
    public CancelPlantsOrderResponse createCancelPlantsOrderResponse() {
        return new CancelPlantsOrderResponse();
    }

    /**
     * Create an instance of {@link GetPlantsOrderSummary }
     * 
     */
    public GetPlantsOrderSummary createGetPlantsOrderSummary() {
        return new GetPlantsOrderSummary();
    }

    /**
     * Create an instance of {@link GetPlantsOrderSummaryResponse }
     * 
     */
    public GetPlantsOrderSummaryResponse createGetPlantsOrderSummaryResponse() {
        return new GetPlantsOrderSummaryResponse();
    }

    /**
     * Create an instance of {@link OrderPlants }
     * 
     */
    public OrderPlants createOrderPlants() {
        return new OrderPlants();
    }

    /**
     * Create an instance of {@link OrderPlantsResponse }
     * 
     */
    public OrderPlantsResponse createOrderPlantsResponse() {
        return new OrderPlantsResponse();
    }

    /**
     * Create an instance of {@link Fault }
     * 
     */
    public Fault createFault() {
        return new Fault();
    }

    /**
     * Create an instance of {@link PlantsOrderSummary }
     * 
     */
    public PlantsOrderSummary createPlantsOrderSummary() {
        return new PlantsOrderSummary();
    }

    /**
     * Create an instance of {@link PlantsOrder }
     * 
     */
    public PlantsOrder createPlantsOrder() {
        return new PlantsOrder();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelPlantsOrder }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CancelPlantsOrder }{@code >}
     */
    @XmlElementDecl(namespace = "http://plantsOrder.bp.org/", name = "cancelPlantsOrder")
    public JAXBElement<CancelPlantsOrder> createCancelPlantsOrder(CancelPlantsOrder value) {
        return new JAXBElement<CancelPlantsOrder>(_CancelPlantsOrder_QNAME, CancelPlantsOrder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelPlantsOrderResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CancelPlantsOrderResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://plantsOrder.bp.org/", name = "cancelPlantsOrderResponse")
    public JAXBElement<CancelPlantsOrderResponse> createCancelPlantsOrderResponse(CancelPlantsOrderResponse value) {
        return new JAXBElement<CancelPlantsOrderResponse>(_CancelPlantsOrderResponse_QNAME, CancelPlantsOrderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPlantsOrderSummary }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetPlantsOrderSummary }{@code >}
     */
    @XmlElementDecl(namespace = "http://plantsOrder.bp.org/", name = "getPlantsOrderSummary")
    public JAXBElement<GetPlantsOrderSummary> createGetPlantsOrderSummary(GetPlantsOrderSummary value) {
        return new JAXBElement<GetPlantsOrderSummary>(_GetPlantsOrderSummary_QNAME, GetPlantsOrderSummary.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPlantsOrderSummaryResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetPlantsOrderSummaryResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://plantsOrder.bp.org/", name = "getPlantsOrderSummaryResponse")
    public JAXBElement<GetPlantsOrderSummaryResponse> createGetPlantsOrderSummaryResponse(GetPlantsOrderSummaryResponse value) {
        return new JAXBElement<GetPlantsOrderSummaryResponse>(_GetPlantsOrderSummaryResponse_QNAME, GetPlantsOrderSummaryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderPlants }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OrderPlants }{@code >}
     */
    @XmlElementDecl(namespace = "http://plantsOrder.bp.org/", name = "orderPlants")
    public JAXBElement<OrderPlants> createOrderPlants(OrderPlants value) {
        return new JAXBElement<OrderPlants>(_OrderPlants_QNAME, OrderPlants.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderPlantsResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OrderPlantsResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://plantsOrder.bp.org/", name = "orderPlantsResponse")
    public JAXBElement<OrderPlantsResponse> createOrderPlantsResponse(OrderPlantsResponse value) {
        return new JAXBElement<OrderPlantsResponse>(_OrderPlantsResponse_QNAME, OrderPlantsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Fault }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Fault }{@code >}
     */
    @XmlElementDecl(namespace = "http://plantsOrder.bp.org/", name = "Fault")
    public JAXBElement<Fault> createFault(Fault value) {
        return new JAXBElement<Fault>(_Fault_QNAME, Fault.class, null, value);
    }

}
