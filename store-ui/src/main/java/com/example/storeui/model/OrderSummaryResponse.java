package com.example.storeui.model;

public class OrderSummaryResponse {
    private String id;
    private PlantsOrderSummary plantsOrderSummary;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PlantsOrderSummary getPlantsOrderSummary() {
        return plantsOrderSummary;
    }

    public void setPlantsOrderSummary(PlantsOrderSummary plantsOrderSummary) {
        this.plantsOrderSummary = plantsOrderSummary;
    }

}
