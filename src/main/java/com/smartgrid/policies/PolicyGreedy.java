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
import com.smartgrid.utilitymethods.UtilityMethods;


public class PolicyGreedy extends CustomPolicy implements HouseholdPolicy {
	
	/** these values define the sample policy we create for 
		our demonstrative simulation. GREEDY should not bother to switch
		off appliances when the price of electricity is high, ECONOMY should
		bother too much and MODERATE should apply a midway solution between the previous two */
	
	
	static final double TOLERANCE_PER=0.40;	//percentage that price can go over 0.1 -10% 0.2-20% ...1-100%
	//static final double TOLERANCE_PER_WEEKEND=0.00;	//percentage that price can go over during weekned
	
	static final double IDEAL_ELEC_PRICE=150;
	//1st parameter 0-Sunday 1-Monday...6-Saturday
	//2nd parameter time owners leave home
	//3rd parameter time owners come back home
	static final PolicyDay Monday=new PolicyDay(1 ,"10:00","14:00");
	static final PolicyDay Tuesday=new PolicyDay(2 ,"10:00","14:00");
	static final PolicyDay Wednesday=new PolicyDay(3 ,"10:00","14:00");
	static final PolicyDay Thursday=new PolicyDay(4 ,"10:00","14:00");
	static final PolicyDay Friday=new PolicyDay(5 ,"10:00","14:00");
	//static final PolicyDay Saturday=new PolicyDay(6 ,"00:00","23:59");
	//static final PolicyDay Sunday=new PolicyDay(0 ,"07:00","12:00");
	//CustomAppliance id-demand-priority(1 highest) state(true for open , false for closed)
	static final CustomAppliance APP1= new CustomAppliance("stereo", 51.,3,true);
	static final CustomAppliance APP2= new CustomAppliance("fridge", 50.0,1,true);
	static final CustomAppliance APP3= new CustomAppliance("tv", 56.,3,true);
	static final CustomAppliance APP4= new CustomAppliance("heart_machine", 100.0,1,true);
	static final CustomAppliance APP5= new CustomAppliance("toaster", 20.0,3,true);
	static final CustomAppliance APP6= new CustomAppliance("computer", 55.0,2,true);
	static final CustomAppliance APP7= new CustomAppliance("aircondition", 150.0,2,true);
	static final CustomAppliance APP8= new CustomAppliance("coffe-maker", 30.0,3,false);
	static final CustomAppliance APP9= new CustomAppliance("dvd-player", 35.0,3,false);
	static final CustomAppliance APP10= new CustomAppliance("mikrowave", 95.0,2,false);
	//ATTENTION Testing variable
	private double electricityPrice=130;
	
 
	List<PolicyDay> periods;
	List<Appliance> appliances; /** Contains all the electronic devices that are in the house */
	
	public PolicyGreedy() {
		super();
		this.setPolicyId("Greedy Policy");
		this.setPolicyAuthor("Christos Oikonomou");
		this.setPolicyVersion(1.0);
		this.setPolicyName("Greedy");
		initializeAppliances();
		initializePeriods();
		updateMinDemand(appliances);
		updateMaxDemand(appliances);
	  //this.setIdealElectricityPrice(100);

	}

  
     public void tick(Date  simulationDate) {
		// TODO Auto-generated method stub
		
    	System.out.println(getPolicyId());
    	//update the demand of the household
    	setOverallCurrentDemand( updateElectricityDemand(appliances,60 )) ;
    	System.out.println("current demand "+String.valueOf(getOverallCurrentDemand()));
    	
    	rule1(simulationDate,periods);
    	rule2(simulationDate,periods);
    	
    	//update the demand of the household
    	setOverallCurrentDemand( updateElectricityDemand(appliances,60 )) ;
    	System.out.println("current demand "+String.valueOf(getOverallCurrentDemand()));
		

	}


	public CustomMessage handleMessage(CustomMessage m) {
		// TODO Auto-generated method stub
		return null;
	}

	





    public void initializeAppliances(){
    	
		appliances =  new ArrayList<Appliance>();
		appliances.add(APP1);
		appliances.add(APP2);
		appliances.add(APP3);
		appliances.add(APP4);
		appliances.add(APP5);
		appliances.add(APP6);
		appliances.add(APP7);
		appliances.add(APP8);
		appliances.add(APP9);
		
    }
    
public void initializePeriods(){
    	
		periods =  new ArrayList<PolicyDay>();
		periods.add(Monday);
		periods.add(Tuesday);
		periods.add(Wednesday);
		periods.add(Thursday);
		periods.add(Friday);
//		periods.add(Saturday);
//		periods.add(Sunday);
		
		
		
    }
 
/**
 * Rule that handles devices
 * Checks whether is is the weekend and there is 
 * if the owners are  home
 * 
 * if electricity price is <= ideal electricity price then it turns on appliances
 * 
 * if electricity price is >ideal electricity but within the electricity tollerance range it
 * doesn't do anything 
 * 
 * if electricity price is >ideal electricity and not within the tollerance range it 
 * turns off appliances
 * 
 */

   //rule about handling devices during the weekend
   public void rule1(Date currentDate,List<PolicyDay> periods){
	   
	   if(UtilityMethods.isItWeekend(currentDate) && UtilityMethods.areOwnersHome(currentDate, periods)){   
		   System.out.println("Hurray it's the weekend!!!");
		   System.out.println("Owners are at home!");
		   if(getElectricityPrice()<=IDEAL_ELEC_PRICE){
	        	
	        	System.out.println("Electricity price is lower then the IDEAL PRICE");
	        	turnOnHighetPriorityAppliance();
	        
	        }else if(getElectricityPrice()>IDEAL_ELEC_PRICE){
	         	
	        	if(getElectricityPrice()<=IDEAL_ELEC_PRICE+IDEAL_ELEC_PRICE*TOLERANCE_PER){
	        		
	        		System.out.println("Electricity price is higher then the IDEAL PRICE but within the tollerance range"+IDEAL_ELEC_PRICE+"-"+(IDEAL_ELEC_PRICE+IDEAL_ELEC_PRICE*TOLERANCE_PER));
		            System.out.println("Appliances are idle");
	        		
	        	}else{
	        		
	        		System.out.println("Electricity price is higher than the tollerance range");
	        		turnOffMostDemandingAppliance();
	    	        
	        	}
	        }
	   }else{
   		
   		     System.out.println("Owners are not home!");
   		     turnOffMostDemandingAppliance();
	        
   	}
   }
   
    //rule about handling devices during the week
	public  void rule2(Date currentDate,List<PolicyDay> periods){
	
		
	    
		  
	       if( !UtilityMethods.areOwnersHome(currentDate, periods) ){
	          
	    	    System.out.println("The date is "+ currentDate.toString());
	        	System.out.println("Owners are not home");
	        	turnOffMostDemandingAppliance();
	           // UtilityMethods.printAppliances(appliances);
	        //	turnOffAppliance(UtilityMethods.getMostDemandingApp(appliances).getId());
	       
	        }else if(getElectricityPrice()<=IDEAL_ELEC_PRICE){
	        	
	        	System.out.println("Owners are at home");
	        	System.out.println("Electricity price is lower then the IDEAL PRICE");
	        	turnOnHighetPriorityAppliance();
	        
	       	
	        }else if(getElectricityPrice()>IDEAL_ELEC_PRICE){
	         	
	        	System.out.println("Owners are at home");
	        	if(getElectricityPrice()<=IDEAL_ELEC_PRICE+IDEAL_ELEC_PRICE*TOLERANCE_PER){
	        		
		        	System.out.println("Electricity price is higher then the IDEAL PRICE but within the tollerance range");
		            System.out.println("Appliances are idle");
	        		
	        	}else{
	        		
	        		System.out.println("Electricity price is higher then price tollerance range");   
	        		turnOffMostDemandingAppliance();
	    	        
	        	}
	        	
	        	
	        }
      	
        	 
        	
        
	       
	   
	   }


	 public double getElectricityPrice() {
			return electricityPrice;
		}

		public void setElectricityPrice(double electricityPrice) {
			this.electricityPrice = electricityPrice;
		}
		
	public void     turnOffMostDemandingAppliance(){
		
		CustomAppliance mostDemandingApp = (CustomAppliance) UtilityMethods.getMostDemandingLowestPriorityOnApp(appliances);
//        checks if ther is a actual Appliance that can be closed
    	if(mostDemandingApp!=null){
    		
    		for(Appliance app:appliances){
    		
    			if(app.getId().equals(mostDemandingApp.getId())){
    			
    				turnOffAppliance(app);
    				System.out.println(app.getId()+" turned off");
    			}
    		}
       }
    	
	};
	
	public void turnOnHighetPriorityAppliance(){
		
	CustomAppliance leastDemandingApp = (CustomAppliance) UtilityMethods.getLeastDemandingHighestPriorityOffApp(appliances);

   	 if(leastDemandingApp!=null){
   		
   		for(Appliance app:appliances){
   		
   			if(app.getId().equals(leastDemandingApp.getId())){
   			
   				turnOnAppliance(app);
   				System.out.println(app.getId()+" turned on");
   	        	
   			}
   		 }
      }
   	
		
	}
}
