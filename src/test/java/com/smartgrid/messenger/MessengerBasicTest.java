package com.smartgrid.messenger;

import com.smartgrid.messenger.MessengerBasic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

class Person {
	public void anounce(String s) {
		System.out.printf("I have an anouncement: %s\n", s);
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

    public void testMessenger() {
    	Map<Integer, Object> members = new HashMap<Integer, Object> ();
    	
    	members.put(1, (Object) new Person());
    	
    	Messenger messenger = new MessengerBasic(members);

    	ArrayList p = new ArrayList();
    	p.add("Hello world!");
    	
    	messenger.message(1, new Message("anounce", p));
    	
        assertTrue( true );
    }
}
