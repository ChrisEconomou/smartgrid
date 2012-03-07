package com.smartgrid.app;

import com.smartgrid.app.Simulator;
import com.smartgrid.app.Logger;
import com.smartgrid.policies.AggregatorPolicy;
import com.smartgrid.policies.CustomAggregatorPolicy;
import com.smartgrid.policies.CustomHouseholdPolicy;
import com.smartgrid.policies.HouseholdPolicy;

import java.util.HashMap;

public class App {
	
    public static void main( String[] args ) {
    	// setup logger
    	Logger logger = new Logger("localhost", "smartgrid", "rob", "test123");
    	
    	if (!logger.open()) {
    		System.out.println("Failed to connect to the database");
    		return;
    	}
    	else {
    		System.out.println("Connected to the database");
    	}
  	
    	// for each different household policy
    	HouseholdPolicy tmp = new CustomHouseholdPolicy();
    	logger.logHouseholdPolicy(tmp.getPolicyAuthor(), tmp.getPolicyName(), tmp.getPolicyVersion());
    	
    	Integer granularity = 60 * 60;
    	Long iterations = 100L;

    	// create aggregator policy
    	AggregatorPolicy aggregatorPolicy =  new CustomAggregatorPolicy();
    	logger.logAggregatorPolicy(aggregatorPolicy.getPolicyAuthor(), aggregatorPolicy.getPolicyName(), aggregatorPolicy.getPolicyVersion());
    	
		logger.logRun();
    	
    	// setup households
    	Integer population = 50;
    	HashMap<Integer,Household> households = new HashMap<Integer,Household>();
    	for (Integer i = 1; i <= population; i++) {
    		HouseholdPolicy p = new CustomHouseholdPolicy();
    		logger.logRunHouseholdConnection(i, p.getPolicyAuthor(), p.getPolicyName(), p.getPolicyVersion()); 
    		Household h = new Household(i, p);
    		households.put(i, h);
    	}
    	
    	Simulator simulator = new Simulator(households, iterations, granularity, aggregatorPolicy, logger);
    	
        System.out.println("Running simulator");
        simulator.run();
        System.out.println("Simulation finished");
        
        logger.close();
    }
}
