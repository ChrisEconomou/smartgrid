package com.smartgrid.app;

import com.smartgrid.messenger.Message;
import com.smartgrid.messenger.Messenger;
import com.smartgrid.messenger.MessengerBasic;
import com.smartgrid.policies.Appliance;
import com.smartgrid.app.Household;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Aggregator {
	
	private Map<Integer, Double> householdDemandMap;
	private Logger logger;
	private Double electricitySupply;
	private Integer[] allHouseholdIds;
	private Messenger<Household> messenger;
	
	Aggregator (Messenger<Household> messenger, Logger logger) {
		this.messenger = messenger;
		allHouseholdIds = messenger.memberIds();
		this.logger = logger;
	}
		
	public Logger getLogger() {
		return logger;
	}
	
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	public double getElectricitySupply() {
		return electricitySupply;
	}
	
	public void setElectricitySupply(double electricitySupply) {
		this.electricitySupply = electricitySupply;
	}

	//returns a map of household ids to their demand values. 
	//It is up to developers to ensure that the Household policy can handle these messages.
	public Map<Integer,Double> getHouseHoldDemandMap() {		
		return householdDemandMap;
	}

	// updates the houseHoldMap attribute, returns total demand
	public Double updateHouseholdDemands(Date date){
    	Message<Void> m = new Message<Void>("getElectricityDemand", null);
    	householdDemandMap = messenger.<Double,Void>messageMany(allHouseholdIds, m);

    	Double total = 0.0;

    	for (Integer i: allHouseholdIds) {
    		total += householdDemandMap.get(i);
    		// TODO some how fetch number of appliances
    		logger.logHouseholdDemand(i, date, householdDemandMap.get(i), 3);
    	}
    	
    	return total;
	}

   //Sends a message to the household requesting an appliance to be turned off. 
   //It is up to the developer of the household policy to decide whether to conform to this.
   public Integer turnOffAppliance(Integer householdId, Appliance appliance){
	    Message<Appliance> m = new Message<Appliance>("turnOffAppliance", appliance);
	   	return messenger.<Integer,Appliance>message(householdId, m);
   }

   //this sets the price of electricity and triggers a message to be sent to all 
   //households with the updated price. (Also logged by the logger)
   public void setElectricityPrice(double price){
	   Message<Double> m = new Message<Double>("notifyPrice", price);
	   messenger.<Integer,Double>messageMany(allHouseholdIds, m);	   
   } 

   //This allows the policy author to request a list of appliances that a home is using,
   //including their individual demands.
   public Appliance getAppliances(Integer householdId) {
	   Message<Void> m = new Message<Void>("getAppliances", null);
	   return messenger.<Appliance,Void>message(householdId, m);
   }
}
