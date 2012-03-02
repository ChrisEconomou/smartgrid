package com.smartgrid.messenger;
import java.util.ArrayList;
import java.lang.Class;
import java.util.Iterator;

public class Message<T> {
	private String methodName;
	private T content;
	private Class contentType;
	
	Message(String m, T c) {
		methodName = m;
		content = c;
		contentType = c.getClass();
	}
	
	public String getMethodName () {
		return methodName;
	}
	
	public T getContent () {
		return content; 
	}
	
	public Class getContentType () {
		return contentType;
	}
}