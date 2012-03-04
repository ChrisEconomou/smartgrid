package com.smartgrid.app;

public class Appliance {
	private String id;
	private Double	demand;
	private boolean state;
	
	public Appliance (String id, Double demand) {
		this.id = id;
		this.demand = demand;
		this.state=false;
	}
	
	public Double getDemand() {
		return this.demand;
	}

	public String getId() {
		return this.id;
	}

	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
}
