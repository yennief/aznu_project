package org.apache.camel.example.springboot.model;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.camel.example.springboot.model.models.PlantsOrder;

public class OrderRequest {
    @JsonProperty("plantsOrder")
    private PlantsOrder plantsOrder;

    public PlantsOrder getPlantsOrder() {
        return plantsOrder;
    }

    public void setPlantsOrder(PlantsOrder plantsOrder) {
        this.plantsOrder = plantsOrder;
    }

}
