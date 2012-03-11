package com.smartgrid.utilitymethods;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.smartgrid.app.Appliance;
import com.smartgrid.policies.CustomAppliance;
import com.smartgrid.policies.PolicyDay;



public class UtilityMethods {

//returns true if according to the starting Date and the current time of the simulation the simulated date is a weekend
  static public boolean isItWeekend(Date simulationDate){
		
		
		
		
		if(simulationDate.getDay()==0 || simulationDate.getDay()==6){
			
			return true;
		}
		
		return false;
				
	}
  //Sunday=0, Monday=1 ....Saturday=6
  //returns what day of the week is the simulation in
 static public int getDayOfWeek(Date startSimulationDay,long currentTime){
		
		long mlsDate=startSimulationDay.getTime();
		long mlsCurrentTime=currentTime*6000;
		long time=mlsDate+mlsCurrentTime;
		Date currentDate=new Date(time);
		
		return currentDate.getDay();
				
	}
  //returns true if a "time" is between the timeStart and the timeEnd
  public static boolean isTimeBetween(long time,long timeStart,long timeEnd){
	 
	 long twenteyFourHourMillisecond=86400000;
	 //for example if we have timestart=21:00 and timeEnd=02:00
	 if(timeEnd<timeStart){ 
		
		 if(time>=timeStart || time<=timeEnd){
			  
			  return true;
		  
		 }
	 }else{
	 
	 if(time>=timeStart && time<=timeEnd){
		  
		  return true;
	  
	 }
	 
	 }
	  return false;
	  
  }
  /**
   * 
   * @param appliances List of appliances
   * @return the most demanding appliance
   */
  //searches in the Appliance List and returns the most demanding appliance with the least priority
  public static Appliance getMostDemandingLowestPriorityOnApp(List<Appliance> appliances){
	  
	  Appliance mostDemandingApp = null;
	 
	  double demand=0;
	  int priority=2;//checks if priority equal or higher than 1, it can not close appliances with priority 1
	  for(Appliance app: appliances){
	     //checks whether the appliance is on
		 if(app.getState()==true){
		  //checks if the appliances demand is larger than the already largest and if it is on 	 
		   if(((CustomAppliance)app).getPriority()>priority){
			  
			
				 priority=((CustomAppliance)app).getPriority();
				 demand=app.getDemand();
				 mostDemandingApp=app;
		 //if two devices have same priority check there demands	 
		  }else if(((CustomAppliance)app).getPriority()==priority){
			  
			  if(app.getDemand()>=demand){
			
					demand=app.getDemand();
					mostDemandingApp=app; 
				 }
		  }
		   
		 }
		//if(app.getDemand()>demand ) {
			
//		if(mostDemandingApp!=null) System.out.println(mostDemandingApp.getDemand());
//		else System.out.println("cant close app");
//			
	  }
	 
	  return mostDemandingApp;
  }
// gets the appliance with the smallest demand and the highest priority  
public static Appliance getLeastDemandingHighestPriorityOffApp(List<Appliance> appliances){
	  
	  Appliance leastDemandingApp = null;
	 
	  double demand=0;
	  int priority=3;
	  for(Appliance app: appliances){
	     //checks whether the appliance is on
		 if(app.getState()==false){
		  //checks if the appliances demand is larger than the already largest and if it is on 	
			
		   if(((CustomAppliance)app).getPriority()<priority){
			  
			
				 priority=((CustomAppliance)app).getPriority();
				 demand=app.getDemand();
				 leastDemandingApp=app;
		 //if two devices have same priority check there demands	 
		  }else if(((CustomAppliance)app).getPriority()==priority){
			  
			 if(app.getDemand()<=demand || demand==0){
			
					demand=app.getDemand();
					leastDemandingApp=app; 
				 }
		  }
		   
		 }
		//if(app.getDemand()>demand ) {
			
//		if(mostDemandingApp!=null) System.out.println(mostDemandingApp.getDemand());
//		else System.out.println("cant close app");
//			
	  }
	 
	  return leastDemandingApp;
  }
	
  /**
   * 
   * @param date a date 
   * @return retrives the time an dreturns it in milliseconds
   */
 static public long TimeToMillisecond(Date date){
	  
	 int hours= date.getHours();
	 int minutes=date.getMinutes();
	 int seconds=date.getSeconds();
	 
	 long result=(hours*3600000)+(minutes*60000)+(seconds*1000);
	  
	 return result; 
  }

 static void printAppliances(List<Appliance> appliances){
	 for(Appliance app: appliances){
		 
		 System.out.println("\n"+app.getId()+": "+app.getDemand()+" - "+app.getState()+" - "+((CustomAppliance)app).getPriority()+"\n");
	 }
 }
 
 public static double calculateMinDemand(List<Appliance> appliances){
	 
	 double minDemand=0.0;
	 
	 for(Appliance app: appliances){
		 if(((CustomAppliance)app).getPriority()==1){
			 
			 minDemand=minDemand + app.getDemand();
		 }
	 }
	 
	
	 return minDemand;
 }
 
 public static double calculateMaxDemand(List<Appliance> appliances){
	 
	 double maxDemand=0.0;
	 for(Appliance app: appliances){
		 
			 maxDemand=maxDemand + app.getDemand();		 
	 }
	 
	 return maxDemand;
 }
 
/**
 *  
 * @param current Date of the sumulation
 * @return true f date is within a PolicyDAy
 */
 public static boolean areOwnersHome(Date currentDate,List<PolicyDay> periods){
	 
	 for(PolicyDay  pol:periods){
		 
		 if(pol.getDay()==currentDate.getDay()){
			 
			 if(isTimeBetween(UtilityMethods.TimeToMillisecond(currentDate),pol.getStartTime(),pol.getEndTime())) return false;
			
		 }
		 
	 }
	 
	 return true;
 }
}
