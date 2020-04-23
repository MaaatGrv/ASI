package com.sp.model;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HeroTest {
	private List<String> stringList;
	private List<Integer> intList;

	@Before
	public void setUp() {
		System.out.println("[BEFORE TEST] -- Add Hero to test");
		stringList = new ArrayList<String>();
		intList = new ArrayList<Integer>();
		stringList.add("normalString1");
		stringList.add("normalString2");
		stringList.add(";:!;!::!;;<>");
		intList.add(5);
		intList.add(500);
		intList.add(-1);
	}

	@After
	public void tearDown() {
		System.out.println("[AFTER TEST] -- CLEAN hero list");
		stringList = null;
		intList = null;
	}

	@Test
	public void createHero() {
		for(String msg:stringList) {
			for(String msg2:stringList) {
				for(String msg3:stringList) {
					for(Integer msg4:intList) {
						Hero h=new Hero(msg4, msg, msg2, msg4, msg3);
						System.out.println("msg:"+msg+", msg2:"+msg2+", msg3:"+msg3+", msg4:"+msg4);
						assertTrue(h.getId().intValue() == msg4.intValue());
						assertTrue(h.getName() == msg);
						assertTrue(h.getSuperPowerName() == msg2);
						assertTrue(h.getSuperPowerValue() == msg4);
						assertTrue(h.getImgUrl() == msg3);
					}	
				}	
			}
		}

	}
	
	@Test
	public void displayHero() {
		Hero h=new Hero(1,"jdoe", "strong", 100, "https//url.com");
		String expectedResult="HERO [1]: name:jdoe, superPowerName:strong, superPowerValue:100 imgUrl:https//url.com";
		assertTrue(h.toString().equals(expectedResult));
		
	}

}
