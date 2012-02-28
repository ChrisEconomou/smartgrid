package com.smartgrid.messenger;
import java.util.ArrayList;
import java.lang.Class;
import java.util.Iterator;
@SuppressWarnings("rawtypes")

public class Message {
	private String methodName;
	private ArrayList parameters;
	private Class[] parameterTypes;
	
	Message(String m, ArrayList p) {	
		methodName = m;
		parameters = p;
		parameterTypes = new Class[p.size()];
	
	    int i = 0;
	    for(Object o: p.toArray())
	    	parameterTypes[i++] = o.getClass();
	}
	
	public String getMethodName () {
		return methodName;
	}
	
	public ArrayList getParameters () {
		return parameters;
	}
	
	public Class[] getParameterTypes () {
		return parameterTypes;
	}
}
