package com.smartgrid.messenger;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.smartgrid.messenger.Message;

public final class MessengerBasic implements Messenger {
	
	private Map<Integer, Object> members;
	
	MessengerBasic(Map<Integer, Object> m) {
		members = m;
	}
	
	public <MessageType,ResponseType> ResponseType message(int id, Message<MessageType> m) {
		Object recepient = getMember(id);
		
		// retrieve method from target object
		Method target = null;
		try {
			target = recepient.getClass().getMethod(m.getMethodName(), m.getContentType());
		} catch (SecurityException e) {
			System.out.printf("Target method '%s' isn't public?\n", m.getMethodName());
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.out.printf("Target method '%s' doesn't exist?\n", m.getMethodName());
			e.printStackTrace();
		}

		ResponseType r = null;
		
		// call method
		try {
			r = (ResponseType) target.invoke(recepient, m.getContent());
		} catch (IllegalArgumentException e) {
			System.out.printf("Invalid arguments to method %s", m.getMethodName());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.printf("Illegal access to method %s", m.getMethodName());
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			System.out.printf("Illegal access to method %s", m.getMethodName());
			e.printStackTrace();
		}
		
		return r;
	}

	public <MessageType, ResponseType> Map<Integer, ResponseType> messageMany(Integer[] recipients, Message<MessageType> m) {
		
		Map<Integer, ResponseType> response = new HashMap<Integer, ResponseType> ();
		
		for (Integer id : recipients) {
			response.put(id, this.<MessageType, ResponseType>message(id, m));
		}
		
		return response;
	}
	
	private Object getMember(int id) {
		return members.get(id);
	}
}
