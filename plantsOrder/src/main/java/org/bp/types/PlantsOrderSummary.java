package org.bp.types;

public class PlantsOrderSummary extends PlantsOrder {

	private String id;
	private int cost;

	public PlantsOrderSummary() {
	}

	public PlantsOrderSummary(PlantsOrder plantsOrder) {
		this.setAmount(plantsOrder.getAmount());
		this.setName(plantsOrder.getName());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
}
