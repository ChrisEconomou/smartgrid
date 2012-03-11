package com.smartgrid.policies;

import java.util.Date;
import java.util.List;

import com.smartgrid.app.Appliance;
import com.smartgrid.utilitymethods.UtilityMethods;

abstract class CustomPolicy implements HouseholdPolicy {
	
	
	
	
	private String policyId,policyAuthor,policyName; /** The unique identifier of the household policy */
	
	private double overallCurrentDemand,minDemand,maxDemand,policyVersion; /** At any time this value represents 
	 										the electricity demand in Watt per minutes*/
	private double electricityPrice;/**The electricity price*/
	
	List<Appliance> appliances; /** Contains all the electronic devices that are in the house */
	
	
	public CustomPolicy() {
		
	//	this.setPolicyId("Economy Policy");
	//	this.setPolicyAuthor("Christos Oikonomou");
		
	//	this.setIdealElectricityPrice(100);
}
	
	
/**
 * getters and setters
 */
	public String getPolicyAuthor() {
		return policyAuthor;
	}

	public void setPolicyAuthor(String policyAuthor) {
		this.policyAuthor = policyAuthor;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public double getOverallCurrentDemand() {
		return overallCurrentDemand;
	}

	public void setOverallCurrentDemand(double overallCurrentDemand) {
		this.overallCurrentDemand = overallCurrentDemand;
	}

	public double getElectricityPrice() {
		return electricityPrice;
	}

	public void setElectricityPrice(double electricityPrice) {
		this.electricityPrice = electricityPrice;
	}

	public Double getPolicyVersion() {
		return policyVersion;
	}


	public void setPolicyVersion(double policyVersion) {
		this.policyVersion = policyVersion;
	}
	


	public void setAppliances(List<Appliance> appliances) {
		this.appliances = appliances;
	}
	
	public String getPolicyName() {
		return policyName;
	}


	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	/**
	 * 
	 */


/**
 * Inteface Implementations
 */


//
//	public Double getElectricityDemand() {
//		// TODO Auto-generated method stub
//		return null;
//	}

    //gets list with appliances
	public List<Appliance> getAppliances() {
		// TODO Auto-generated method stub
		return appliances;
	}

    //
	public Integer turnOffAppliance(Appliance appliance) {
		
        CustomAppliance app=(CustomAppliance)appliance; 
        app.setState(false);
           
           return 1;
	
	}

   //
	public Integer notifyPrice(Double newPrice) {
		
		 setElectricityPrice(newPrice);
		
		return 1;
	}


	
	
	/**
	 * 
	 */
	
	//add appliance to list
    public void addAppliance(Appliance device) {
 		
 		appliances.add(device);
 		
 	}
    
  //Removes an Appliance from the list
  	public void removeAppliance(String deviceID) {
  		//TO COMPLETE
  		
  		for(Appliance app : appliances){
  			
  			if(app.getId().equals(deviceID)){
  				
  				appliances.remove(app.getId());
  				break;
  			}
  		}
  	}
    //Turn on an Appliance
	public Integer turnOnAppliance(Appliance appliance){
		
		  CustomAppliance app=(CustomAppliance)appliance; 
	    	app.setState(true);
			//device wasn't turned off
			return 0;
		
		
	
	}


public  double updateElectricityDemand(List<Appliance> appliances,int granuality ){
		
    	double demand =0.0;
		for(Appliance app: appliances ){
			
			if(app.getState()==true){
				
				demand=demand + (app.getDemand()*granuality)/60;
			}
			
		}
		
		return demand;
		
	}

	
public Double getElectricityDemand() {

	return overallCurrentDemand;
}

//updates the minimum demand value 
public void updateMinDemand(List <Appliance> appliances){
	
	setMinDemand(UtilityMethods.calculateMinDemand(appliances));
}

//updates the maximum demand value 
public void updateMaxDemand(List <Appliance> appliances){
	
	setMaxDemand(UtilityMethods.calculateMaxDemand(appliances));
}

public double getMinDemand() {
	return minDemand;
}


public void setMinDemand(double minDemand) {
	this.minDemand = minDemand;
}


public double getMaxDemand() {
	return maxDemand;
}


public void setMaxDemand(double maxDemand) {
	this.maxDemand = maxDemand;
}

}
