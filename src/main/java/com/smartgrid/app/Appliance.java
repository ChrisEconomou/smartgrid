package com.smartgrid.app;

public class Appliance {
	private Integer id;
	private Double	demand;
	
	public Appliance (Integer id, Double demand) {
		this.id = id;
		this.demand = demand;
	}
	
	public Double getDemand() {
		return this.demand;
	}

	public Integer getId() {
		return this.id;
	}
}
