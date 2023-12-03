package org.apache.camel.example.springboot.model;

import org.apache.camel.example.springboot.model.models.PlantsOrderSummary;

public class OrderSummaryResponse {
    private String id;
    private PlantsOrderSummary plantsOrderSummary;

    public String getId() {
        return id;
    }

    public PlantsOrderSummary getPlantsOrderSummary() {
        return plantsOrderSummary;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPlantsOrderSummary(PlantsOrderSummary plantsOrderSummary) {
        this.plantsOrderSummary = plantsOrderSummary;
    }

}
