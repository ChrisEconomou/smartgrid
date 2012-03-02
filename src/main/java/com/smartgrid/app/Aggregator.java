package com.smartgrid.app;

import com.smartgrid.messenger.Message;
import com.smartgrid.messenger.Messenger;
import com.smartgrid.messenger.MessengerBasic;


import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;


public class Aggregator {
	
	private HashMap<Integer, Double> houseHoldDemandMap;//
	private HashMap<Integer,Household> houses;//map with id and object Household
	private Logger logger;
	private double electricitySupply;
	private long iteration;
	
	Aggregator (HashMap<Integer,Household> households) {
		HashMap tmp = new HashMap(households);
		
		houses.putAll(tmp);
		
	}
	
	public void setHouseHoldMap(HashMap<Integer, Double> houseHoldMap) {
		this.houseHoldDemandMap = houseHoldMap;
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
	
	public long getIteration() {
		return iteration;
	}
	
	public void setIteration(long iteration) {
		this.iteration = iteration;
	}
	
	//returns a map of household ids to their demand values. 
	//It is up to developers to ensure that the Household policy can handle these messages.
	
	public HashMap<Integer,Double> getHouseHoldDemandMap() {
		
		
		
		return houseHoldDemandMap;
	}
	

	//updates the houseHoldMap attribute
	public void updateHouseHoldDemands(){
		// create 200 member objects
    	Map<Integer, Object> members = new HashMap<Integer, Object>(houses);
    	// create new messenger object
    	Messenger messenger = new MessengerBasic(members);
    	
    	// get list of ids
    	//Integer[] member_ids = members.keySet().<Integer>toArray(new Integer[0]);
    	Integer[] member_ids=members.keySet().<Integer>toArray(new Integer[0]);
    	// message many with String typed message and Integer response type.
    	Message<String> m =new Message("getElectricityDemand","null");
    	Map<Integer,Double> response = messenger.<String,Map<Integer,Double>>messageMany(member_ids,m);
        //setHouseHoldMap
    	houseHoldDemandMap.putAll(response);
    
	}

  // isn' this the same as the setElectricityPrice?  
   private  void notifyPriceChange(double newPrice){
	//Send message to all houselholds with the new price 
    //message type : NOTIFY
	Map<Integer, Household> members = new HashMap<Integer, Household>( houses);
   	
  
 
	 Messenger messenger = new MessengerBasic(members);
	 Message<Double> m = new Message<Double>("notifyPrice",newPrice);
	   
    
   	 Integer i = messenger.<Double,Integer>message(1,m);

	   
   }
   //Sends a message to the household requesting an appliance to be turned off. 
   //It is up to the developer of the household policy to decide whether to conform to this.

   public void turnOffAppliance(int inhouseholdID, Appliance appliance){
	   
	    Map<Integer,Household> members = new HashMap<Integer,Household>();
	    Household household;
	    members.put(inhouseholdID,household );
	    Integer[] member_ids = members.keySet().<Integer>toArray(new Integer[0]);
	    Message<Appliance> m = new Message<Double>("turnOffAppliance", appliance);
		   
	   	boolean respond = messenger.<Double,boolean>message(members_ids,m);
	   
   }
   
   //this sets the price of electricity and triggers a message to be sent to all 
   //households with the updated price. (Also logged by the logger)
   public void setElectricityPrice(double price){
	
   	Map<Integer, Household> members = new HashMap<Integer, Household> (houses);
   	
   	// create new messenger object
   	Messenger messenger = new MessengerBasic(members);
   	
   	// get list of ids
   	Integer[] member_ids = members.keySet().<Integer>toArray(new Integer[0]);
    Message<Double> m = new Message<Double>("setElectricityPrice", price);
   	// message many with String typed message and Integer response type.
   	boolean response = messenger.<Double, boolean>messageMany(member_ids,m);
   

	   
   } 
   //This allows the policy author to request a list of appliances that a home is using,
   //including their individual demands.
   public Array getAppliances(int householdId){
	   
	   //send a single message to the particulat household definie by the id 
	   //this messages contains an Array with the apppliances.
	   //message type: REQUEST
	   
	// create new map of members in the form member id => member object
   	Map<Integer, Object> members = new HashMap<Integer, Object> (houses);
  // 	Household household = null;
   	// add one person called bob to members
  // 	members.put(householdId, household);
   	//all the objects the messanger wnats to communicate with
   	Messenger messenger = new MessengerBasic(members);
   	// message a single object with message type string, response type integer
   	Array listOfAppliances = messenger.<Array>message_void(1, new Message<String>("getAppliances", void));
   	
   	return listOfAppliances;
     
	   

	   
	   
   }
   
  

}
