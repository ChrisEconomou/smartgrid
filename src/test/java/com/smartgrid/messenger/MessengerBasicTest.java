package com.smartgrid.messenger;

import com.smartgrid.messenger.MessengerBasic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

class Person {
	private String name;
	
	public Person(String s) {
		name = s;
	}

	public Integer anounce(String s) {
		System.out.printf("I (%s) have an anouncement: %s\n", name, s);
		return 1;
	}
}

public class MessengerBasicTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MessengerBasicTest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( MessengerBasicTest.class );
    }

    public void testMessageOne() {
    	// create new map of members in the form member id => member object
    	Map<Integer, Object> members = new HashMap<Integer, Object> ();
    	// add one person called bob to members
    	members.put(1, (Object) new Person("Bob"));
    	
    	Messenger messenger = new MessengerBasic(members);
    	Integer i = messenger.message(1, new Message<String>("anounce", "Hello World"));
    	
        assertTrue( i == 1 );
    }
    
    public void testMessageMany() {
    	// create 200 member objects
    	Map<Integer, Object> members = new HashMap<Integer, Object> ();
    	
    	for (Integer i = 0; i < 200; i++) {
    		members.put(i, (Object) new Person(i.toString()));
    	}
    	
    	Messenger messenger = new MessengerBasic(members);
    	Map<Integer, Integer> response = null;
    	
    	Integer[] member_ids = members.keySet().<Integer>toArray(new Integer[0]);
    	
    	response = messenger.<String, Integer>messageMany(member_ids, new Message<String>("anounce", "Hello World"));
    	
    	for (Integer i = 0; i < 200; i++) {
    		assertTrue(response.get(i) == 1);
    	}
    }
}
