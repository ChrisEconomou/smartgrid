package com.smartgrid.app;

import java.util.List;

public class Household {
	private Integer householdId;
	private Double electricityPrice;
	private HouseholdPolicy policy;
	
	public Household(Integer householdId, Double electricityPrice, HouseholdPolicy policy) {
		this.householdId = householdId;
		this.electricityPrice = electricityPrice;
		this.policy = policy;
	}
	
	public Integer getHouseholdId() {
		return householdId;
	}
	
	public Double getElectricityPrice() {
		return electricityPrice;
	}
	
	public void setElectricityPrice(Double electricityPrice) {
		this.electricityPrice = electricityPrice;
	}
	
	public HouseholdPolicy getPolicy() {
		return policy;
	}
	
	public void setPolicy(HouseholdPolicy policy) {
		this.policy = policy;
	}
	
	public Double getElectricityDemand() {
		return this.policy.getElectricityDemand();
	}
	
	public List<Appliance> getAppliances() {
		return this.policy.getAppliances();
	}
	
	public boolean turnOffAppliance(String applianceId) {
		return this.policy.turnOffAppliance(applianceId);
	}
	
	public void notifyPrice(Double newPrice) {
		this.electricityPrice=newPrice;
		this.policy.notifyPrice(newPrice);
	}
	
	public Message handleMessage(Message in) {
		return this.policy.handleMessage(in);
	}
	
	@Override
	public String toString() {
		return "Household Id: "+householdId+" running policy: <"+policy.getPolicyAuthor()+", "+policy.getPolicyVersion()+">" ;
	}
}
