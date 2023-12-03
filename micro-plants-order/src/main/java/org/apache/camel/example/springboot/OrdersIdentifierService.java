package org.apache.camel.example.springboot;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class OrdersIdentifierService {
	HashMap<String, OrderIds> ordersIds = new HashMap<>();

	public String generateOrderId() {
		String orderId = UUID.randomUUID().toString();
		ordersIds.put(orderId, new OrderIds());
		return orderId;
	}

	public void assignPlantsOrderId(String id, String orderId) {
		ordersIds.get(id).setPlantsOrderId(orderId);
	}

	public String getPlantsOrderId(String id) {
		return ordersIds.get(id).getPlantsOrderId();
	}

	public static class OrderIds {
		private String plantsOrderId;

		public String getPlantsOrderId() {
			return plantsOrderId;
		}

		public void setPlantsOrderId(String plantsOrderId) {
			this.plantsOrderId = plantsOrderId;
		}

	}
}