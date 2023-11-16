package org.bp.plantsOrder;

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

	public PlantsOrderSummary orderPlants(PlantsOrder plantsOrder) throws Fault {
		PlantsOrderSummary plantsOrderSummary = new PlantsOrderSummary(plantsOrder);
		plantsOrderSummary.setId(UUID.randomUUID().toString());

		// if (plantsOrder.getMaterial().toLowerCase().equals("wood"))
		// plantsOrderSummary.setCost(100 * plantsOrderSummary.getAmount());
		// else
		plantsOrderSummary.setCost(70 * plantsOrderSummary.getAmount());

		// if (plantsOrder.getModel().toLowerCase().equals("strange"))
		// throw new Fault("Strange Chair Model");

		plantsOrders.put(plantsOrderSummary.getId(), plantsOrderSummary);

		return plantsOrderSummary;
	}

	public PlantsOrderSummary getPlantsOrderSummary(String id) throws Fault {
		// if (!plantsOrders.containsKey(id))
		// throw new Fault("Order " + id + " does not exists");
		return plantsOrders.get(id);
	}

	public void cancelPlantsOrder(String id) throws Fault {
		// if (!plantsOrders.containsKey(id))
		// throw new Fault("Order " + id + " does not exists");
		plantsOrders.remove(id);
	}
}