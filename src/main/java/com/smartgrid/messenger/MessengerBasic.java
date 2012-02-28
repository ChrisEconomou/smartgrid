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
	
	public Message message(int id, Message m) {
		Object recepient = getMember(id);
		
		// retrieve method from target object
		Method target = null;
		try {
			target = recepient.getClass().getMethod(m.getMethodName(), m.getParameterTypes());
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		// call method
		try {
			target.invoke(recepient, m.getParameters().toArray());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO handle response 		
		return new Message("Foo", (new ArrayList<Integer>(1)));
	}

	public Map<Integer, Message> messageMany(int[] recipients, Message m) {
		
		Map<Integer, Message> response = new HashMap<Integer, Message> ();
		
		for (int id : recipients) {
			response.put(id, message(id, m));
		}
		
		return response;
	}
	
	private Object getMember(int id) {
		return members.get(id);
	}
}
