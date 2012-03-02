package com.smartgrid.app;

import java.util.Date;
import java.util.List;

public interface HouseholdPolicy {

	public void tick(Date date);
	
	public String getPolicyAuthor();
	
	public String getPolicyVersion();
	
	public double getElectricityDemand();
	
	public List<Appliance> getAppliances();
	
	public boolean turnOffAppliance(String applianceID);
	
	public void notifyPrice(double newPrice);

	
	public Message handleMessage(Message msg);
}
