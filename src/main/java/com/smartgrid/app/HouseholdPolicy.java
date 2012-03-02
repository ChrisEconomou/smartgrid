package com.smartgrid.app;

import java.util.Date;
import java.util.List;
import com.smartgrid.app.Appliance;

public interface HouseholdPolicy {
	public void tick(Date date);
	
	public String getPolicyAuthor();
	
	public String getPolicyVersion();
	
	public Double getElectricityDemand();
	
	public List<Appliance> getAppliances();
	
	public Integer turnOffAppliance(Appliance appliance);
	
	public void notifyPrice(double newPrice);
}
