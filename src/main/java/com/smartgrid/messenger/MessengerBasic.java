package com.smartgrid.messenger;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.smartgrid.messenger.Message;

public final class MessengerBasic<T> implements Messenger<T> {
	
	private Map<Integer, T> members;
	
	MessengerBasic(Map<Integer, T> m) {
		members = m;
	}
	
	public <ResponseType,MessageType> ResponseType message(Integer id, Message<MessageType> m) {
		T recepient = getMember(id);
		
		// retrieve method from target object
		Method target = null;
		try {
			if (m.getContentType() == null)
				target = recepient.getClass().getMethod(m.getMethodName());
			else
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
			if (m.getContent() == null)
				r = (ResponseType) target.invoke(recepient);
			else
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

	public <ResponseType, MessageType> Map<Integer, ResponseType> messageMany(Integer[] recipients, Message<MessageType> m) {
		Map<Integer, ResponseType> response = new HashMap<Integer, ResponseType> ();
		
		for (Integer id : recipients) {
			response.put(id, this.<ResponseType,MessageType>message(id, m));
		}
		
		return response;
	}
	
	private T getMember(int id) {
		return members.get(id);
	}
}
