package com.smartgrid.policies;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PolicyDay {

 private long startTime,endTime;
 int day,periodStart,periodFinish;
 PolicyDay(int day,String startTime,String endTime){
	 
	 this.day=day;
	 this.endTime=timeToMilliseconds(endTime);
	 this.startTime=timeToMilliseconds(startTime);
 }
 PolicyDay(int periodStart,int periodFinish,String startTime,String endTime){
	 
	 this.periodStart=periodStart;
	 this.periodFinish=periodFinish;
	 this.endTime=timeToMilliseconds(endTime);
	 this.startTime=timeToMilliseconds(startTime);
 }
 
 /**
  * 
  * getters and setters
  */
public long getEndTime() {
	return endTime;
}

public void setEndTime(long endTime) {
	this.endTime = endTime;
}

public long getStartTime() {
	return startTime;
}

public void setStartTime(long startTime) {
	this.startTime = startTime;
}

public int getDay() {
	return day;
}

public void setDay(int day) {
	this.day = day;
}
public int getPeriodStart() {
	return periodStart;
}
public void setPeriodStart(int periodStart) {
	this.periodStart = periodStart;
}
public int getPeriodFinish() {
	return periodFinish;
}
public void setPeriodFinish(int periodFinish) {
	this.periodFinish = periodFinish;
}

public long timeToMilliseconds(String time){
	
	
	Date tempDate = null;
	 SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
       try {
			tempDate = parser.parse(time);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return tempDate.getTime();
}
 
}
