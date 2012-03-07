package com.smartgrid.policies;

import java.util.Date;
import java.util.List;
import com.smartgrid.policies.Appliance;
import com.smartgrid.app.CustomMessage;

public interface HouseholdPolicy {
	public void tick(Date date);
	
	public String getPolicyAuthor();
	
	public String getPolicyName();
	
	public Double getPolicyVersion();
	
	public Double getElectricityDemand();
	
	public List<Appliance> getAppliances();
	
	public Integer turnOffAppliance(Appliance appliance);
	
	public Integer notifyPrice(Double newPrice);
	
	public CustomMessage handleMessage(CustomMessage m) throws Exception;
}
