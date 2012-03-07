package com.smartgrid.policies;

public class GenericAppliance implements Appliance {
	private Integer id;
	private Double	demand;
	
	public GenericAppliance (Integer id, Double demand) {
		this.id = id;
		this.demand = demand;
	}
	
	public Double getDemand() {
		return this.demand;
	}
	
	public String getName() {
		return "Generic Appliance";
	}

	public Integer getId() {
		return this.id;
	}
}
