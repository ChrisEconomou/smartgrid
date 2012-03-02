package com.smartgrid.app;

import java.util.List;

public class Household {
	
	private Integer householdID;
	private Double electricityPrice;
	private HouseholdPolicy policy;
	
	
	public Household(Integer householdID) {
		super();
		this.householdID = householdID;
	}
	
	public Household(Integer householdID, Double electricityPrice) {
		super();
		this.householdID = householdID;
		this.electricityPrice = electricityPrice;
	}
	
	public Household(Integer householdID, Double electricityPrice,
			HouseholdPolicy policy) {
		super();
		this.householdID = householdID;
		this.electricityPrice = electricityPrice;
		this.policy = policy;
	}
	
	public Integer getHouseholdID() {
		return householdID;
	}
	
	public void setHouseholdID(Integer householdID) {
		this.householdID = householdID;
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
	
	public boolean turnOffAppliance(String applianceID) {
		return this.policy.turnOffAppliance(applianceID);
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
		return "Household ID: "+householdID+" running policy: <"+policy.getPolicyAuthor()+", "+policy.getPolicyVersion()+">" ;
	}
	
	

}
