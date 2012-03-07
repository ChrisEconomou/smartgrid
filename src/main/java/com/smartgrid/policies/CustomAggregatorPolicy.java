package com.smartgrid.policies;

import java.util.Date;
import java.util.Map;

import com.smartgrid.app.Aggregator;

public class CustomAggregatorPolicy implements AggregatorPolicy {

	private final String PolicyName = "CustomAggregatorPolicy";
	private final Double PolicyVersion = 1.0;
	private final String PolicyAuthor = "Farha";
	private Double supply;
	private Double price;
	
	public CustomAggregatorPolicy() {
		supply = 1000.0;
		price = 50.0;
	}

	public void tick(Date date, Aggregator aggregator) {
		Map<Integer, Double> demandMap = aggregator.getHouseholdDemandMap();
		
		double totalDemand = 0;
		for (Double e: demandMap.values() ) totalDemand +=e;
		
		System.out.printf("AggregatorPolicy: %s, Supply %f, Total Demand %f\n",
				PolicyName,
				aggregator.getElectricitySupply(),
				totalDemand
				);
		
		if(totalDemand >= 1000) {
			aggregator.setElectricitySupply(1100.0);
			aggregator.setElectricityPrice(50.0);
		
		}
		
		if(totalDemand >= 2000) {
			aggregator.setElectricitySupply(2100.0);
			aggregator.setElectricityPrice(100.0);
		
		}
		
		if(totalDemand >= 3000) {
			aggregator.setElectricitySupply(3100.0);
			aggregator.setElectricityPrice(150.0);
		
		}
		
		if(totalDemand >= 4000) {
			aggregator.setElectricitySupply(4100.0);
			aggregator.setElectricityPrice(200.0);
		
		}
		
		if(totalDemand >= 5000) {
			aggregator.setElectricitySupply(5100.0);
			aggregator.setElectricityPrice(250.0);
		}
	}

	public String getPolicyName() {
		return PolicyName;
	}

	public Double getPolicyVersion() {
		return PolicyVersion;
	}
	
	public String getPolicyAuthor() {
		return PolicyAuthor;
	}

	public Double getPrice() {
		return price;
	}

	public Double getSupply() {
		return supply;
	}

}
