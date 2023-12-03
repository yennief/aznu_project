package org.bp.plantsOrder;

import org.apache.cxf.service.model.FaultInfo;
import org.bp.types.Fault;
import org.bp.types.PlantsOrderSummary;
import org.bp.types.PlantsOrder;

import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

@javax.jws.WebService
@org.springframework.stereotype.Service
public class PlantsOrderService {

	HashMap<String, PlantsOrderSummary> plantsOrders = new HashMap<>();

	public PlantsOrderSummary orderPlants(PlantsOrder plantsOrder) throws Exception {
		PlantsOrderSummary plantsOrderSummary = new PlantsOrderSummary(plantsOrder);
		plantsOrderSummary.setId(UUID.randomUUID().toString());

		if (plantsOrder.getName().toLowerCase().equals("monstera")) {
			plantsOrderSummary.setCost(100 * plantsOrderSummary.getAmount());
		} else {
			plantsOrderSummary.setCost(70 * plantsOrderSummary.getAmount());
		}

		if (plantsOrder.getName().toLowerCase().equals("strange")) {
			throw new Exception("Strange plant model");
		}

		plantsOrderSummary.setName(plantsOrder.getName());
		plantsOrderSummary.setAmount(plantsOrder.getAmount());
		System.out.println(plantsOrder.getName());
		System.out.println(plantsOrder.getName());

		plantsOrders.put(plantsOrderSummary.getId(), plantsOrderSummary);

		return plantsOrderSummary;
	}

	public PlantsOrderSummary getPlantsOrderSummary(String id) throws Exception {
		if (!plantsOrders.containsKey(id)) {
			throw new Exception("Order " + id + " does not exists");
		}
		return plantsOrders.get(id);
	}

	public void cancelPlantsOrder(String id) throws Exception {
		if (!plantsOrders.containsKey(id)) {
			throw new Exception("Order " + id + " does not exists");
		}

		plantsOrders.remove(id);
	}
}