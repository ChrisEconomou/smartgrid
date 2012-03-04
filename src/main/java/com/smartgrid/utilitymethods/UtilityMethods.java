package com.smartgrid.utilitymethods;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.smartgrid.app.Appliance;

public class UtilityMethods {

//returns true if according to the starting Date and the current time of the simulation the simulated date is a weekend
  static public boolean isItWeekend(Date startSimulationDay,long currentTime){
		
		
		
		
		if(getDayOfWeek( startSimulationDay,currentTime)==0 || getDayOfWeek( startSimulationDay,currentTime)==6){
			
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
	  
	  if(time>=timeStart && time<=timeEnd){
		  
		  return true;
	  }
	  
	  return false;
	  
  }
  /**
   * 
   * @param appliances List of appliances
   * @return the most demanding appliance
   */
  //searches in the Appliance List and returns the most demanding appliance
  public static Appliance getMostDemandingApp(List<Appliance> appliances){
	  
	  Appliance mostDemandingApp = null;
	  double demand=0;
	  for(int i=0;appliances.size()<0;i++){
		//checks if the appliances demand is larger than the already largest and if it is on  
		if(appliances.get(i).getDemand()>demand && appliances.get(i).getState()==true) {
			
			demand= appliances.get(i).getDemand();
			mostDemandingApp=appliances.get(i);
			
		}
		
			
	  }
	  return mostDemandingApp;
  }
	


}
