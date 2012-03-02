package com.smartgrid.app;

import com.smartgrid.messenger.Message;
import com.smartgrid.messenger.Messenger;
import com.smartgrid.messenger.MessengerBasic;


import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class Aggregator {
	
	private HashMap<Integer, Double> householdDemandMap;//
	private Logger logger;
	private double electricitySupply;
	private HashMap<Integer,Household> households;
	private Messenger<Household> messenger;
	private Integer[] allHouseholdIds;
	
	Aggregator (HashMap<Integer,Household> households) {
		households = h;
		allHouseholdIds = households.keySet().<Integer>toArray(new Integer[0]);
		messenger = new MessengerBasic<Household>(households);
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
	public HashMap<Integer,Double> getHouseHoldDemandMap() {		
		return householdDemandMap;
	}

	//updates the houseHoldMap attribute
	public void updateHouseHoldDemands(){
    	Message<Void> m = new Message<Void>("getElectricityDemand", null);
    	householdDemandMap = messenger.<Double,Void>messageMany(allHouseholdIds, m);
	}

   //Sends a message to the household requesting an appliance to be turned off. 
   //It is up to the developer of the household policy to decide whether to conform to this.
   public Integer turnOffAppliance(Integer householdId, Appliance appliance){
	    Message<Appliance> m = new Message<Appliance>("turnOffAppliance", appliance);
	   	Integer respond = messenger.<Integer,Appliance>message(members_ids,m);
   }
   
   //this sets the price of electricity and triggers a message to be sent to all 
   //households with the updated price. (Also logged by the logger)
   public void setElectricityPrice(double price){
	   Message<Double> m = new Message<Double>("notifyPrice", price);
	   Integer i = messenger.<Integer,Double>messageMany(allHouseholdIds, m);	   
   } 

   //This allows the policy author to request a list of appliances that a home is using,
   //including their individual demands.
   public Appliance[] getAppliances(Integer householdId){
	   return messenger.<Appliance[],Void>message_void(1, new Message<String>("getAppliances", void));
   }
}
