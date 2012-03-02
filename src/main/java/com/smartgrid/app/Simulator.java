package com.smartgrid.app;

import java.util.HashMap;

public class Simulator {
	private Long ticks;
	private Integer granularity;
	private HashMap<Integer,Household> households;
	
	Simulator(HashMap<Integer,Household> h, Long t, Integer g) {
		ticks = t;
		granularity = g;
		households = h;
	}
}
