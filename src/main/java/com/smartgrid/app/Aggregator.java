package com.smartgrid.app;
import com.smartgrid.messenger.MessengerBasic;
import java.lang.reflect.Array;
import java.util.HashMap;


public class Aggregator {
	
	private HashMap<Integer, Double> houseHoldMap;
	private Logger logger;
	private double electricitySupply;
	private long iteration;
	
	
	public HashMap<Integer,Double> getHouseHoldMap() {
		return houseHoldMap;
	}
	
	public void setHouseHoldMap(HashMap<Integer, Double> houseHoldMap) {
		this.houseHoldMap = houseHoldMap;
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
	
	//updates the damand of each household
	public void updateHouseHoldDemands(){
		
		
	}
	
	//returns an Array of Integers
    public Array getHouseholds(){
    	
		return null;
    
    }
    
   private  void notifyPriceChange(){
	   
	   
   }
   
   //turns off a specific appliance of a specific household
   public void turnOffAppliance(int inhouseholdID, Appliance appliance){
	   
	   
   }

}
