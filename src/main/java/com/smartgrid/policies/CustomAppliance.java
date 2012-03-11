package com.smartgrid.policies;

import com.smartgrid.app.Appliance;



public class CustomAppliance implements Appliance {
	private String id;
	private Double	demand;
	private boolean state;
    private int priority;
	
	public CustomAppliance (String id,Double demand,int priority,boolean state) {
		this.id = id;
		this.demand = demand;
		this.priority=priority;
		this.state=state;
	}
	
	public Double getDemand() {
		return this.demand;
	}
	


	public String getId() {
		return this.id;
	}

	public boolean getState() {
		// TODO Auto-generated method stub
		return state;
	}

	public void setState(boolean state) {
		// TODO Auto-generated method stub
		this.state=state;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}
