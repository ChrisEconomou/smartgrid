package com.smartgrid.app;

import java.util.Date;
import java.util.List;
import com.smartgrid.app.Appliance;
import com.smartgrid.app.CustomMessage;

public interface HouseholdPolicy {
	public void tick(int tickValue, int granularity, double electricityPrice);
	
	public String getAuthor();
	
	public String getVersion();
	
	public Double getElectricityDemand();
	
	public List<Appliance> getAppliances();
	
	public boolean turnOffAppliance(String applianceID);
	
	public Integer notifyPrice(Double newPrice);
	
	public CustomMessage handleMessage(CustomMessage m);
}
