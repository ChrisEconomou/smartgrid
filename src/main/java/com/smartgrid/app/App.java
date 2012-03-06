package com.smartgrid.app;

import com.smartgrid.app.Simulator;
import com.smartgrid.policies.AggregatorPolicy;
import com.smartgrid.policies.CustomAggregatorPolicy;
import com.smartgrid.policies.CustomHouseholdPolicy;
import com.smartgrid.policies.HouseholdPolicy;

import java.util.HashMap;

public class App {
    public static void main( String[] args ) {
    	System.out.println("Setting up simulator");
    	
    	Integer population = 50;
    	
    	// TODO the wrapping of household policy within a policy object should happen in the simulator.
    	HashMap<Integer,Household> households = new HashMap<Integer,Household>();
    	for (Integer i = 1; i <= population; i++) {
    		Household h = new Household(i, new CustomHouseholdPolicy());
    		households.put(i, h);
    	}
    	
    	Integer granularity = 60 * 60; // in seconds
    	Long iterations = 10000L;
    	AggregatorPolicy aggregatorPolicy =  new CustomAggregatorPolicy();
    
    	Simulator simulator = new Simulator(households, iterations, granularity, aggregatorPolicy);
    	
        System.out.println("Running simulator");
        simulator.run();
        System.out.println("Simulation finished");
    }
}
