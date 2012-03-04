package com.smartgrid.policies;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.smartgrid.app.Appliance;
import com.smartgrid.app.CustomMessage;
import com.smartgrid.app.HouseholdPolicy;
import com.smartgrid.messenger.Message;
import com.smartgrid.utilitymethods.UtilityMethods;

public class GreedyPolicy implements HouseholdPolicy {
	
	/** these values define the sample policy we create for 
		our demonstrative simulation. GREEDY should not bother to switch
		off appliances when the price of electricity is high, ECONOMY should
		bother too much and MODERATE should apply a midway solution between the previous two */
	
	
	static final double PER_ABOVE=0.5;	
	static final double PER_ABOVE_WEEKEND=0.5;	
	static final String START_PERIOD="08:00";
	static final String END_PERIOD="13:00";
	
	
	private byte policyType; /** Defines the type of this policy */
								
	private String ID,Version,Author; /** The unique identifier of the household policy */
	
	private double overallCurrentDemand; /** At any time this value represents 
	 										the electricity demand in Watt per minutes*/
	private double idealElectricityPrice; /** This is the electricity price the household would be willing to pay**/
	
	Date simulationStartDate; /**The date the simulation starts. */
	
	List<Appliance> appliances; /** Contains all the electronic devices that are in the house */
	
	public GreedyPolicy(String iD,String author,String version,Double maxEP,Date simulationSD) {
		super();
		this.setID(iD);
		this.setAuthor(author);
		this.setVersion(version);
		this.setIdealElectricityPrice(maxEP);
		simulationStartDate=simulationSD;
	}

	 
//	public EconomyPolicy(String iD, byte type) throws IllegalArgumentException {
//		//TO COMPLETE
//	}



	public void setAppliances(List<Appliance> appliances) {
		this.appliances = appliances;
	}
	
// DO NOT CONSIDER THIS METHOD AT THE MOMENT
//	/** ADEOLA
//	 * Returns a list containing all the appliances that are turned on in a given minute of the day
//	 * 
//	 * @param minuteOfTheDay An integer value that belongs to the interval [1, 1440]
//	 * @return the list containing the valid appliances
//	 */
 	private List<Appliance> getTurnedOnAppliances( int minuteOfTheDay) {
 		
 		List<Appliance> tempList=new ArrayList<Appliance>();
 		
 		for(int i=0;i<appliances.size();i++){
 			
 			if(appliances.get(i).getState()){
 				
 				tempList.add(appliances.get(0));
 			}
 			
 		}
 		return tempList;
 	}
 	
 	/** ADEOLA
 	 * Add an appliance to the household
 	 * 
 	 * @param device The appliance to add
 	 */
 	public void addAppliance(Appliance device) {
 		
 		appliances.add(device);
 		
 	}
 	
 	/** ADEOLA
	 * Remove an appliance from the household
	 * 
	 * @param device The appliance identifier
	 */
	public void removeAppliance(String deviceID) {
		//TO COMPLETE
		
		for(int i=0;i<appliances.size();i++){
			
			if(appliances.get(i).getId().equals(deviceID)){
				
				appliances.remove(i);
				break;
			}
		}
	}

	/** ADEOLA
	 * This method is the core of the policy. Here the electricity demand of the household
	 * should be adapted to the price of the electricity and the time of the day (obtainable from tickValue and granularity).
	 * The adaptation must differ depending on the type of Policy (greedy, ...)
	 * 
	 * The method must first update the demand of the household by evaluating the appliances that are active; afterwards,
	 * according to the policy the instance of the class represents, it should turn off/on appliances
	 * 
	 * @param tickValue the value of the tick counter
	 * @param granularity the time granularity
	 * @param electricityPrice the price of the electricity in currency per watt
	 */

	public void tick(int tickValue, int granularity, double electricityPrice) {
		// TODO Auto-generated method stub
		
		Date currentSimulationDate,startDate = null,endDate = null;
		
       
        
        //the current duration of the simulation in minutes
        long currentTimeinMin=(tickValue*granularity);
        //the current duration of the simulation in milliseconds
        long currentTimeInMill=currentTimeinMin*60000;
        //the Date the simulation started in milliseconds
        long simStartDateInMill=simulationStartDate.getTime();
        //the current simulation Date
        currentSimulationDate= new Date(currentTimeInMill + simStartDateInMill);
        
     

        
  

		
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        try {
			startDate = parser.parse(START_PERIOD);
			endDate = parser.parse(END_PERIOD);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
       //if (time8:00-13:00 then close Appliance
        if( UtilityMethods.isTimeBetween(currentSimulationDate.getTime(), startDate.getTime(), endDate.getTime())){
        	
        	System.out.println("The time is between 08:00 and 13:00 close the fucking appliances");
        	turnOffAppliance(UtilityMethods.getMostDemandingApp(appliances).getId());
       
        }
       
        // gives the policy the opportunity to spend 10% more that the max electricity price the user is expecting 
        if(electricityPrice>getIdealElectricityPrice()+getIdealElectricityPrice()*PER_ABOVE){
        	
            System.out.println("It is the weekend and the price is more than what I want to pay close the fucking appliances");
            turnOffAppliance(UtilityMethods.getMostDemandingApp(appliances).getId());
         }
        
       //if  day=weekend AND electricityPrice>maxElectricityPrice then close Appliance
       if(UtilityMethods.isItWeekend(simulationStartDate, currentTimeinMin) && electricityPrice>getIdealElectricityPrice()*PER_ABOVE_WEEKEND){
        	
           System.out.println("It is the weekend and the price is more than what I wnat to pay close the fucking appliances");
           turnOffAppliance(UtilityMethods.getMostDemandingApp(appliances).getId());
        }
        
		
	}

	public List<Appliance> getAppliances() {
		
		return appliances;
	}



	public boolean turnOffAppliance(String applianceID) {
		for(int i=0;i<appliances.size();i++){
			
			if(appliances.get(i).getId().equals(applianceID)){
				
				if(appliances.get(i).getState()==true){
					
					//device on but will turn off
					appliances.get(i).setState(false);
					return true;
					
				}else{
					
			         System.out.println("device already off");
			         
					//device already off
					
				}
				
			}
		}
		//device wasn't turned off
		return false;
	}

	/*@Override
	public void notifyPrice(double newPrice) {
		// TODO Auto-generated method stub
		
	}*/

	

	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		//leave this method empty at the moment
	}




	public Integer notifyPrice(Double newPrice) {
		// TODO Auto-generated method stub
		return null;
	}

	public CustomMessage handleMessage(CustomMessage m) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		Version = version;
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}

	public double getOverallCurrentDemand() {
		return overallCurrentDemand;
	}

	public void setOverallCurrentDemand(double overallCurrentDemand) {
		this.overallCurrentDemand = overallCurrentDemand;
	}

	public Double getElectricityDemand() {
		// TODO Auto-generated method stub
		return null;
	}



	public double getIdealElectricityPrice() {
		return idealElectricityPrice;
	}

	public void setIdealElectricityPrice(double maxElecgricityPrice) {
		this.idealElectricityPrice = maxElecgricityPrice;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}



	


}
