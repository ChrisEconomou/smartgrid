package com.smartgrid.messenger;

import com.smartgrid.messenger.MessengerBasic;

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
	
	public Integer whats_one_plus_one() {
		return 2;
	}
	
	public boolean am_i_happy () {
		return true;
	}
}

public class MessengerBasicTest extends TestCase {
	
	private Map<Integer, Object> members;
	Messenger<Person> messenger;
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
    
    public void setUp() {
    	// create new map of members in the form member id => member object
    	members = new HashMap<Integer, Object> ();
    	// add one person called bob to members
    	members.put(1, (Object) new Person("Bob"));
    	messenger = new MessengerBasic(members);
    }

    public void testMessageOne() {
    	// message a single object with message type string, response type integer
    	Integer i = messenger.<String,Integer>message(1, new Message<String>("anounce", "Hello World"));
        assertTrue( i == 1 );
    }

    public void testMessageNoParameters() {
    	// message a single object with message type string, response type integer
    	Integer i = messenger.<String,Integer>message(1, new Message<String>("anounce", "Hello World"));
    	
        assertTrue( i == 1 );
    }
    
    public void testMessageBooleanReturnType() {    	    	
    }
    
    public void testMapReturnType() {    	    	
    }
    
    public void testMessageMany() {
    	// create 200 member objects
    	Map<Integer, Person> many_members = new HashMap<Integer, Person> ();
    	
    	for (Integer i = 0; i < 200; i++) {
    		many_members.put(i, new Person(i.toString()));
    	}
    	// create new messenger object
    	Messenger<Person> messenger_with_many = new MessengerBasic<Person>(many_members);
    	
    	// get list of ids
    	Integer[] member_ids = many_members.keySet().<Integer>toArray(new Integer[0]);

    	// message many with String typed message and Integer response type.
    	Map<Integer, Integer> response = messenger_with_many.<String, Integer>messageMany(member_ids, new Message<String>("anounce", "Hello World"));
    
    	for (Integer i = 0; i < 200; i++) {
    		assertTrue(response.get(i) == 1);
    	}
    }
}
