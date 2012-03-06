package com.smartgrid.policies;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.smartgrid.app.Appliance;
import com.smartgrid.app.CustomMessage;

public class CustomHouseholdPolicy implements HouseholdPolicy {

	private Double demand = 0.0;
	private Double previousElectricityPrice = 0.0;
	private Double electricityPrice = 0.0;
	private boolean priceChange = false;
	private List<Appliance> appliances;
	
	public CustomHouseholdPolicy() {
		appliances = new ArrayList<Appliance>();
		appliances.add(new Appliance(1, 10.0));
		appliances.add(new Appliance(2, 5.0));
		appliances.add(new Appliance(3, 5.0));
	}
	
	public void tick(Date date) {
		demand = 20.0;
		
		if (priceChange) {
			priceChange = false;
			// handle price change.
			
			if (previousElectricityPrice > 0) {
				Double percentageChange = (electricityPrice / previousElectricityPrice) * 100;
				System.out.printf("Detected price change %f\n", percentageChange);
			}
		}
		
		// evening
		if (date.getHours() >= 17 && date.getHours() <= 23) {
			demand = 30.0;
			// TODO update appliances to represent change
		}
	}

	public String getPolicyAuthor() {
		return "Robin Edwards";
	}

	public String getPolicyVersion() {
		return "1.0";
	}

	public Double getElectricityDemand() {
		return demand;
	}

	public List<Appliance> getAppliances() {
		return appliances;
	}

	public Integer turnOffAppliance(Appliance appliance) {
		// handle turning off
		return 0;
	}

	public Integer notifyPrice(Double newPrice) {
		if (newPrice != electricityPrice) {
			previousElectricityPrice = electricityPrice;
			electricityPrice = newPrice;
			priceChange = true;
		}
		return 0;
	}

	public CustomMessage handleMessage(CustomMessage m) throws Exception {
		throw new Exception("Don't know how to handle message " + m.toString());
	}
}
