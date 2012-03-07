package com.smartgrid.app;
import com.smartgrid.policies.Appliance;
import com.smartgrid.policies.HouseholdPolicy;
import java.util.Date;
import java.util.List;

public class Household {
	private Integer householdId;
	private HouseholdPolicy policy;
	
	public Household(Integer householdId, HouseholdPolicy policy) {
		this.householdId = householdId;
		this.policy = policy;
	}
	
	private void logMessage(String msg) {
		System.out.printf("Policy: %s Id: %d >> %s\n",
				policy.getClass().toString(),
				householdId,
				msg
				);
	}
	
	public void tick(Date date) {
		logMessage("tick");
		policy.tick(date);
	}
	
	public Integer getHouseholdId() {
		return householdId;
	}

	public HouseholdPolicy getPolicy() {
		return policy;
	}
	
	public Double getElectricityDemand() {
		Double demand = policy.getElectricityDemand();
		logMessage(String.format("demand %f", demand));
		return demand;
	}
	
	public List<Appliance> getAppliances() {
		return policy.getAppliances();
	}
	
	public Integer turnOffAppliance(Appliance appliance) {
		logMessage("Requested to turn off appliance");
		return policy.turnOffAppliance(appliance);
	}
	
	public void notifyPrice(Double newPrice) {
		logMessage(String.format("new price %f", newPrice));
		policy.notifyPrice(newPrice);
	}
	
	public CustomMessage handleMessage(CustomMessage in) throws Exception {
		return policy.handleMessage(in);
	}
	
	@Override
	public String toString() {
		return "Household Id: "+householdId+" running policy: <"+policy.getPolicyAuthor()+", "+policy.getPolicyVersion()+">" ;
	}
}
